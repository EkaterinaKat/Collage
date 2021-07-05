package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

import static com.katyshevtceva.collage.logic.Constants.MIN_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtceva.collage.logic.Utils.*;

public class Component {
    private Collage collage;
    @Getter
    private ImageView frontImage;
    private List<ImageView> images;
    @Getter
    @Setter
    private int z;
    private SizeAdjuster sizeAdjuster;

    Component(Collage collage, ImageView frontImage, List<ImageView> images, int z) {
        this.collage = collage;
        this.frontImage = frontImage;
        this.images = images;
        this.z = z;
        correctImageSizeAndPosIfNeeded();
        sizeAdjuster = new SizeAdjuster(collage, this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    boolean imageContainsPoint(Point point) {
        return ((point.getX() > frontImage.getX()) && (point.getX() < (frontImage.getX() + frontImage.getFitWidth())))
                && ((point.getY() > frontImage.getY()) && (point.getY() < (frontImage.getY() + frontImage.getFitHeight())));
    }

    Point getPos() {
        return new Point(frontImage.getX(), frontImage.getY());
    }

    void relocateIfAllowable(Point newPos) {
        if (newPos.getX() < 0) {
            frontImage.setX(0);
        } else if (newPos.getX() > collage.getWidth() - frontImage.getFitWidth()) {
            frontImage.setX(collage.getWidth() - frontImage.getFitWidth());
        } else {
            frontImage.setX(newPos.getX());
        }

        if (newPos.getY() < 0) {
            frontImage.setY(0);
        } else if (newPos.getY() > collage.getHeight() - frontImage.getFitHeight()) {
            frontImage.setY(collage.getHeight() - frontImage.getFitHeight());
        } else {
            frontImage.setY(newPos.getY());
        }
        sizeAdjuster.setPos();
    }

    void resizeIfAllowable(double newWidth) {
        double newHeight = getHeightByWidth(frontImage, newWidth);

        boolean resizeAllowable = frontImage.getX() + newWidth < collage.getWidth()
                && frontImage.getY() + newHeight < collage.getHeight()
                && newWidth > collage.getWidth() * MIN_COMPONENT_RELATIVE_WIDTH;

        if (resizeAllowable) {
            frontImage.setFitWidth(newWidth);
            frontImage.setFitHeight(newHeight);
            sizeAdjuster.setPos();
        }
    }

    List<ImageView> getFrontImageWithButtons() {
        return Arrays.asList(frontImage, sizeAdjuster.getImageView());
    }

    boolean sizeAdjusterContainsPoint(Point point) {
        return sizeAdjuster.containsPoint(point);
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
