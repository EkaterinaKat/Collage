package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtceva.collage.logic.Constants.MIN_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtceva.collage.logic.Utils.setSizeByHeight;
import static com.katyshevtceva.collage.logic.Utils.setSizeByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

public class Component {
    private Collage collage;
    @Getter
    private Image frontImage;
    @Getter
    private List<Image> images;
    @Getter
    @Setter
    private int z;
    private SizeAdjuster sizeAdjuster;
    private ImageSwitcher imageSwitcher;

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////

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

        boolean resizeAllowable = frontImage.getX() + newWidth < collage.getWidth()
                && frontImage.getY() + newHeight < collage.getHeight()
                && newWidth > collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH;

        if (resizeAllowable) {
            frontImage.setFitWidth(newWidth);
            frontImage.setFitHeight(newHeight);
            sizeAdjuster.setPos();
            imageSwitcher.setPos();
        }
    }

    List<ImageView> getFrontImageWithButtons() {
        if(images.size()==1)
            return Arrays.asList(frontImage.getImageView(), sizeAdjuster.getImageView());
        return Arrays.asList(frontImage.getImageView(), sizeAdjuster.getImageView(), imageSwitcher.getImageView());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setContextMenuOnFrontImage() {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(deleteItem);

        frontImage.setOnContextMenuRequested(e -> {
            if (collage.isEditingMode())
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
