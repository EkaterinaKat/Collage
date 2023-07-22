package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.BackgroundLoadedImageAdjuster;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.image.ImageContainer;

import static com.katyshevtceva.collage.logic.Constants.DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

public class ComponentBuilder {
    private final Collage collage;
    private final ImageContainer imageContainer;
    private Point relativePosition;
    private double relativeWidth = DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
    private int z = 1;

    /**
     * @param imageContainer должен содержаться в collage в allExistingImages.
     */
    public ComponentBuilder(Collage collage, ImageContainer imageContainer) {
        this.collage = collage;
        this.imageContainer = imageContainer;
    }

    public ComponentBuilder relativePosition(Point relativePosition) {
        if (relativePosition.getX() < 0
                || relativePosition.getX() > 1
                || relativePosition.getY() < 0
                || relativePosition.getY() > 1)
            throw new RuntimeException();

        this.relativePosition = relativePosition;
        return this;
    }

    public ComponentBuilder relativeWidth(double relativeWidth) {
        if (relativeWidth < Constants.MIN_COMPONENT_RELATIVE_WIDTH || relativeWidth > 1)
            throw new RuntimeException();

        this.relativeWidth = relativeWidth;
        return this;
    }

    public ComponentBuilder z(int z) {
        this.z = z;
        return this;
    }

    public Component build() {
        Image image = new Image(imageContainer);
        Component component = new Component(collage, image, z);
        startImageAdjuster(image, component);
        return component;
    }

    private void startImageAdjuster(Image image, Component component) {
        new BackgroundLoadedImageAdjuster(image.getImageContainer().getImage(), () -> {
            double initWidth = relativeWidth * collage.getWidth();
            double initHeight = getHeightByWidth(image.getImageView(), initWidth);

            Point initPosition;
            if (relativePosition != null) {
                initPosition = new Point(
                        relativePosition.getX() * collage.getWidth(),
                        relativePosition.getY() * collage.getHeight());
            } else {
                initPosition = new Point(
                        collage.getWidth() / 2.0 - initWidth / 2.0,
                        collage.getHeight() / 2.0 - initHeight / 2.0
                );
            }

            image.setFitWidth(initWidth);
            image.setFitHeight(initHeight);
            image.setX(initPosition.getX());
            image.setY(initPosition.getY());

            component.updateButtonsPos();
        }).start();
    }
}
