package com.katyshevtceva.collage.logic;

import lombok.AccessLevel;
import lombok.Getter;

class ButtonImageFactory {
    private static ButtonImageFactory instance;
    @Getter(AccessLevel.PACKAGE)
    private final javafx.scene.image.Image sizeAdjusterImage;
    @Getter(AccessLevel.PACKAGE)
    private final javafx.scene.image.Image imageSwitcherImage;

    static ButtonImageFactory getInstance() {
        if (instance == null)
            instance = new ButtonImageFactory();
        return instance;
    }

    private ButtonImageFactory() {
        sizeAdjusterImage = new javafx.scene.image.Image("/icons/resizing_arrow.png");
        imageSwitcherImage = new javafx.scene.image.Image("/icons/image_switcher.png");
    }


}
