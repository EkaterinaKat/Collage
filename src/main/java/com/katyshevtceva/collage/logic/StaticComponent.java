package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Point;
import lombok.AccessLevel;
import lombok.Getter;

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
