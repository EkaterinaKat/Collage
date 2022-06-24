package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtceva.collage.logic.Constants.MIN_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtceva.collage.logic.Utils.setSizeByHeight;
import static com.katyshevtceva.collage.logic.Utils.setSizeByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getWidthByHeight;

public class Component {
    @Getter(AccessLevel.PACKAGE)
    private final Collage collage;
    @Getter(AccessLevel.PACKAGE)
    private Image frontImage;
    @Getter(AccessLevel.PACKAGE)
    private List<Image> images;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int z;
    private final SizeAdjuster sizeAdjuster;
    private final ImageSwitcher imageSwitcher;

    Component(Collage collage, Image frontImage, List<Image> images, int z) {
        this.collage = collage;
        this.frontImage = frontImage;
        this.images = images;
        this.z = z;
        sizeAdjuster = new SizeAdjuster(collage, this);
        imageSwitcher = new ImageSwitcher(collage, this);
        correctImageSizeAndPosIfNeeded();
        setContextMenuOnFrontImage();
    }

    public double getRelativeX() {
        return frontImage.getX() / collage.getWidth();
    }

    public double getRelativeY() {
        return frontImage.getY() / collage.getHeight();
    }

    public double getRelativeWidth() {
        return frontImage.getWidth() / collage.getWidth();
    }

    public List<ImageContainer> getImageContainers() {
        return images.stream().map(Image::getImageContainer).collect(Collectors.toList());
    }

    public ImageContainer getFrontImageContainer() {
        return frontImage.getImageContainer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    void updateButtonsPos() {
        sizeAdjuster.setPos();
        imageSwitcher.setPos();
    }

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > frontImage.getX()) && (point.getX() < (frontImage.getX() + frontImage.getWidth())))
                && ((point.getY() > frontImage.getY()) && (point.getY() < (frontImage.getY() + frontImage.getHeight())));
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return sizeAdjuster.containsPoint(point);
    }

    boolean imageSwitcherContainsPoint(Point point) {
        return imageSwitcher.containsPoint(point);
    }

    Point getPos() {
        return new Point(frontImage.getX(), frontImage.getY());
    }

    void switchImage(Image image) {
        image.setX(frontImage.getX());
        image.setY(frontImage.getY());
        Utils.setSizeByWidth(image, frontImage.getWidth());
        frontImage = image;
        correctImageSizeAndPosIfNeeded();
        sizeAdjuster.setPos();
        imageSwitcher.setPos();
        setContextMenuOnFrontImage();
        collage.refillPaneWithComponents();
    }

    void relocateIfAllowable(Point newPos) {
        if (newPos.getX() < 0) {
            frontImage.setX(0);
        } else if (newPos.getX() > collage.getWidth() - frontImage.getWidth()) {
            frontImage.setX(collage.getWidth() - frontImage.getWidth());
        } else {
            frontImage.setX(newPos.getX());
        }

        if (newPos.getY() < 0) {
            frontImage.setY(0);
        } else if (newPos.getY() > collage.getHeight() - frontImage.getHeight()) {
            frontImage.setY(collage.getHeight() - frontImage.getHeight());
        } else {
            frontImage.setY(newPos.getY());
        }
        sizeAdjuster.setPos();
        imageSwitcher.setPos();
    }

    void resizeIfAllowable(double newWidth) {
        double newHeight = getHeightByWidth(frontImage.getImageView(), newWidth);

        if (newWidth > collage.getWidth() - frontImage.getX()) {
            newWidth = collage.getWidth() - frontImage.getX();
            newHeight = getHeightByWidth(frontImage.getImageView(), newWidth);
        }

        if (newHeight > collage.getHeight() - frontImage.getY()) {
            newHeight = collage.getHeight() - frontImage.getY();
            newWidth = getWidthByHeight(frontImage.getImageView(), newHeight);
        }

        if (newWidth < collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH) {
            newWidth = collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH;
            newHeight = getHeightByWidth(frontImage.getImageView(), newWidth);
        }

        frontImage.setFitWidth(newWidth);
        frontImage.setFitHeight(newHeight);
        sizeAdjuster.setPos();
        imageSwitcher.setPos();

    }

    List<ImageView> getFrontImageWithButtons() {
        if (images.size() == 1)
            return Arrays.asList(frontImage.getImageView(), sizeAdjuster.getImageView());
        return Arrays.asList(frontImage.getImageView(), sizeAdjuster.getImageView(), imageSwitcher.getImageView());
    }

    void setImages(List<Image> images) {
        this.images = images;
        if (!images.contains(frontImage))
            switchImage(images.get(0));
        collage.refillPaneWithComponents();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setContextMenuOnFrontImage() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));
        contextMenu.getItems().add(deleteItem);

        MenuItem addImage = new MenuItem("Add image");
        addImage.setOnAction(event -> Utils.openImageSelectionDialog(collage.getImagesAvailableToAddToExistingOne(frontImage),
                imageContainer -> {
                    images.add((Image) imageContainer);
                    switchImage((Image) imageContainer);
                }));
        contextMenu.getItems().add(addImage);

        frontImage.setOnContextMenuRequested(e -> {
            contextMenu.show(frontImage.getImageView(), e.getScreenX(), e.getScreenY());
        });
    }

    private void correctImageSizeAndPosIfNeeded() {
        if (frontImage.getWidth() > collage.getWidth())
            setSizeByWidth(frontImage, collage.getWidth());

        if (frontImage.getHeight() > collage.getHeight())
            setSizeByHeight(frontImage, collage.getHeight());

        if (frontImage.getX() < 0)
            frontImage.setX(0);

        if (frontImage.getX() > collage.getWidth() - frontImage.getWidth())
            frontImage.setX(collage.getWidth() - frontImage.getWidth());

        if (frontImage.getY() < 0)
            frontImage.setY(0);

        if (frontImage.getY() > collage.getHeight() - frontImage.getHeight())
            frontImage.setY(collage.getHeight() - frontImage.getHeight());

        sizeAdjuster.setPos();
        imageSwitcher.setPos();
    }
}
