package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.BackgroundLoadedImageAdjuster;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.image.ImageContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.katyshevtceva.collage.logic.Constants.DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

public class ComponentBuilder {
    private final Collage collage;
    private ImageContainer frontImageContainer;
    private final List<ImageContainer> imageContainers;
    private Point relativePosition;
    private double relativeWidth = DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
    private int z = 1;

    /**
     * @param imageContainers должен содержать url фронтального изображения, если таковое имеется.
     *                        Каждый url должен уже содержаться в collage в allExistingImages.
     */
    public ComponentBuilder(Collage collage, List<ImageContainer> imageContainers) {
        if (imageContainers.size() == 0)
            throw new RuntimeException();

        this.collage = collage;
        this.imageContainers = imageContainers;
    }

    public ComponentBuilder frontImage(ImageContainer imageContainer) {
        if (!imageContainers.stream().map(imageContainer1 -> imageContainer.getPath()).collect(Collectors.toList())
                .contains(imageContainer.getPath()))
            throw new RuntimeException();

        this.frontImageContainer = imageContainer;
        return this;
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
        List<Image> images = new ArrayList<>();
        Image frontImage = null;
        for (ImageContainer imageContainer : imageContainers) {
            Image image = new Image(imageContainer);
            images.add(image);
            if (frontImageContainer != null && imageContainer.getPath().equals(frontImageContainer.getPath()))
                frontImage = image;
            if (!collage.getAllExistingImages().contains(image))
                throw new RuntimeException();
        }
        if (frontImage == null)
            frontImage = images.get(0);

        Component component = new Component(collage, frontImage, images, z);

        startImageAdjuster(frontImage, component);

        return component;
    }

    private void startImageAdjuster(Image frontImage, Component component) {
        new BackgroundLoadedImageAdjuster(frontImage.getImageContainer().getImage(), () -> {
            double initWidth = relativeWidth * collage.getWidth();
            double initHeight = getHeightByWidth(frontImage.getImageView(), initWidth);

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

            frontImage.setFitWidth(initWidth);
            frontImage.setFitHeight(initHeight);
            frontImage.setX(initPosition.getX());
            frontImage.setY(initPosition.getY());

            component.updateButtonsPos();
        }).start();
    }
}
