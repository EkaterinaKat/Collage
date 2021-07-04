package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
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

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > frontImage.getX()) && (point.getX() < (frontImage.getX() + frontImage.getFitWidth())))
                && ((point.getY() > frontImage.getY()) && (point.getY() < (frontImage.getY() + frontImage.getFitHeight())));
    }

    Point getPos() {
        return new Point(frontImage.getX(), frontImage.getY());
    }

    void relocateIfAllowable(Point newPos) {
        boolean relocationAllowable = newPos.getX() > 0 && newPos.getY() > 0 &&
                newPos.getX() + frontImage.getFitWidth() < collage.getWidth()
                && newPos.getY() + frontImage.getFitHeight() < collage.getHeight();

        if (relocationAllowable) {
            frontImage.setX(newPos.getX());
            frontImage.setY(newPos.getY());
        }
    }

    int getZ() {
        return 1; //todo
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
