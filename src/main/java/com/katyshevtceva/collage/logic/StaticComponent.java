package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import com.katyshevtseva.fx.Size;
import com.katyshevtseva.image.ImageContainer;
import javafx.scene.image.ImageView;
import lombok.AccessLevel;
import lombok.Getter;

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

    public StaticComponent(ImageContainer imageContainer, Point relativePosition, double relativeWidth, int z) {
        this.image = new Image(imageContainer);
        this.relativePosition = relativePosition;
        this.relativeWidth = relativeWidth;
        this.z = z;

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
    }

    List<ImageView> getImageViews() {
        return Collections.singletonList(image.getImageView());
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
