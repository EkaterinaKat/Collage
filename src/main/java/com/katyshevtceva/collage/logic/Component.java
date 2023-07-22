package com.katyshevtceva.collage.logic;


import com.katyshevtseva.fx.Point;
import com.katyshevtseva.image.ImageContainer;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtceva.collage.logic.Constants.MIN_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtceva.collage.logic.Utils.setSizeByHeight;
import static com.katyshevtceva.collage.logic.Utils.setSizeByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;
import static com.katyshevtseva.fx.ImageSizeUtil.getWidthByHeight;

public class Component {
    @Getter(AccessLevel.PACKAGE)
    private final Collage collage;
    @Getter(AccessLevel.PACKAGE)
    private final Image image;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int z;
    private final SizeAdjuster sizeAdjuster;

    Component(Collage collage, Image image, int z) {
        this.collage = collage;
        this.image = image;
        this.z = z;
        sizeAdjuster = new SizeAdjuster(collage, this);
        correctImageSizeAndPosIfNeeded();
        setContextMenuOnFrontImage();
    }

    public double getRelativeX() {
        return image.getX() / collage.getWidth();
    }

    public double getRelativeY() {
        return image.getY() / collage.getHeight();
    }

    public double getRelativeWidth() {
        return image.getWidth() / collage.getWidth();
    }

    public ImageContainer getImageContainer() {
        return image.getImageContainer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    void updateButtonsPos() {
        sizeAdjuster.setPos();
    }

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > image.getX()) && (point.getX() < (image.getX() + image.getWidth())))
                && ((point.getY() > image.getY()) && (point.getY() < (image.getY() + image.getHeight())));
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return sizeAdjuster.containsPoint(point);
    }

    Point getPos() {
        return new Point(image.getX(), image.getY());
    }

    void relocateIfAllowable(Point newPos) {
        if (newPos.getX() < 0) {
            image.setX(0);
        } else image.setX(Math.min(newPos.getX(), collage.getWidth() - image.getWidth()));

        if (newPos.getY() < 0) {
            image.setY(0);
        } else image.setY(Math.min(newPos.getY(), collage.getHeight() - image.getHeight()));

        sizeAdjuster.setPos();
    }

    void resizeIfAllowable(double newWidth) {
        double newHeight = getHeightByWidth(image.getImageView(), newWidth);

        if (newWidth > collage.getWidth() - image.getX()) {
            newWidth = collage.getWidth() - image.getX();
            newHeight = getHeightByWidth(image.getImageView(), newWidth);
        }

        if (newHeight > collage.getHeight() - image.getY()) {
            newHeight = collage.getHeight() - image.getY();
            newWidth = getWidthByHeight(image.getImageView(), newHeight);
        }

        if (newWidth < collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH) {
            newWidth = collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH;
            newHeight = getHeightByWidth(image.getImageView(), newWidth);
        }

        image.setFitWidth(newWidth);
        image.setFitHeight(newHeight);
        sizeAdjuster.setPos();
    }

    List<ImageView> getImageWithButtons() {
        return Arrays.asList(image.getImageView(), sizeAdjuster.getImageView());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void setContextMenuOnFrontImage() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> collage.deleteComponent(this));
        contextMenu.getItems().add(deleteItem);

        image.setOnContextMenuRequested(e -> contextMenu.show(image.getImageView(), e.getScreenX(), e.getScreenY()));
    }

    private void correctImageSizeAndPosIfNeeded() {
        if (image.getWidth() > collage.getWidth())
            setSizeByWidth(image, collage.getWidth());

        if (image.getHeight() > collage.getHeight())
            setSizeByHeight(image, collage.getHeight());

        if (image.getX() < 0)
            image.setX(0);

        if (image.getX() > collage.getWidth() - image.getWidth())
            image.setX(collage.getWidth() - image.getWidth());

        if (image.getY() < 0)
            image.setY(0);

        if (image.getY() > collage.getHeight() - image.getHeight())
            image.setY(collage.getHeight() - image.getHeight());

        sizeAdjuster.setPos();
    }
}
