package com.katyshevtceva.collage.logic;

import javafx.scene.image.ImageView;

import java.util.List;

import static com.katyshevtceva.collage.logic.Utils.setSizeByHeight;
import static com.katyshevtceva.collage.logic.Utils.setSizeByWidth;

public class Component {
    private Collage collage;
    private ImageView frontImage;
    private List<ImageView> images;

    Component(Collage collage, ImageView frontImage, List<ImageView> images) {
        this.collage = collage;
        this.frontImage = frontImage;
        this.images = images;
        correctImageSizeAndPosIfNeeded();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    ImageView getFrontImage() {
        return frontImage;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private void correctImageSizeAndPosIfNeeded() {
        if (frontImage.getFitWidth() > collage.getWidth())
            setSizeByWidth(frontImage, collage.getWidth());

        if (frontImage.getFitHeight() > collage.getHeight())
            setSizeByHeight(frontImage, collage.getHeight());

        if (frontImage.getX() < 0)
            frontImage.setX(0);

        if (frontImage.getX() > collage.getWidth() - frontImage.getFitWidth())
            frontImage.setX(collage.getWidth() - frontImage.getFitWidth());

        if (frontImage.getY() < 0)
            frontImage.setY(0);

        if (frontImage.getY() > collage.getHeight() - frontImage.getFitHeight())
            frontImage.setY(collage.getHeight() - frontImage.getFitHeight());
    }
}
