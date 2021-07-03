package com.katyshevtceva.collage.logic;

import javafx.scene.image.ImageView;

import java.util.List;

public class Component {
    private Collage collage;
    private ImageView frontImage;
    private List<ImageView> images;

    Component(Collage collage, ImageView frontImage, List<ImageView> images) {
        this.collage = collage;
        this.frontImage = frontImage;
        this.images = images;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    ImageView getFrontImage() {
        return frontImage;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
}
