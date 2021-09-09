package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.fx.Size;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

public class StaticComponent {
    @Getter(AccessLevel.PACKAGE)
    private final Image image;
    @Getter(AccessLevel.PACKAGE)
    private final Point relativePosition;
    @Getter(AccessLevel.PACKAGE)
    private final double relativeWidth;
    @Getter(AccessLevel.PACKAGE)
    private final int z;
    private final boolean severalImages;
    private ImageView severalImagesMarker;

    public StaticComponent(ImageContainer imageContainer, Point relativePosition, double relativeWidth, int z, boolean severalImages) {
        this.image = new Image(imageContainer);
        this.relativePosition = relativePosition;
        this.relativeWidth = relativeWidth;
        this.z = z;
        this.severalImages = severalImages;

        if (severalImages)
            severalImagesMarker = new ImageView(ButtonImageFactory.getInstance().getImageSwitcherImage());

        validate();
    }

    void tuneSizeAndPos(Size collageSize) {
        double imageWidth = relativeWidth * collageSize.getWidth();
        double imageHeight = getHeightByWidth(image.getImageView(), imageWidth);
        image.setFitWidth(imageWidth);
        image.setFitHeight(imageHeight);

        double x = relativePosition.getX() * collageSize.getWidth();
        double y = relativePosition.getY() * collageSize.getHeight();
        image.setX(x);
        image.setY(y);

        if (severalImages) {
            severalImagesMarker.setFitWidth(collageSize.getWidth() * 0.07);
            severalImagesMarker.setFitHeight(collageSize.getWidth() * 0.07);
            severalImagesMarker.setX(x);
            severalImagesMarker.setY(y);
        }
    }

    List<ImageView> getImageViews() {
        return severalImages ? Arrays.asList(image.getImageView(), severalImagesMarker) : Collections.singletonList(image.getImageView());
    }

    private void validate() {
        if (relativePosition.getX() < 0
                || relativePosition.getX() > 1
                || relativePosition.getY() < 0
                || relativePosition.getY() > 1)
            throw new RuntimeException();

        if (relativeWidth < Constants.MIN_COMPONENT_RELATIVE_WIDTH || relativeWidth > 1)
            throw new RuntimeException();
    }
}
