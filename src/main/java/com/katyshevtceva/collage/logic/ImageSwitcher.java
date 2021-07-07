package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

import static com.katyshevtceva.collage.logic.Constants.BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;

class ImageSwitcher {
    private Component component;
    @Getter
    private ImageView imageView;
    private double buttonSize;

    ImageSwitcher(Collage collage, Component component) {
        this.component = component;
        buttonSize = collage.getWidth() * BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;
        initializeImageView();
        setPos();
    }

    private void initializeImageView() {
        imageView = new ImageView(new Image("/icons/image_switcher.png"));
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
    }

    boolean containsPoint(Point point) {
        return ((point.getX() > imageView.getX()) && (point.getX() < (imageView.getX() + buttonSize))) &&
                ((point.getY() > imageView.getY()) && (point.getY() < (imageView.getY() + buttonSize)));
    }

    void setPos() {
        imageView.setX(component.getPos().getX() + component.getFrontImage().getWidth() - imageView.getFitWidth() / 2);
        imageView.setY(component.getPos().getY() + component.getFrontImage().getHeight() - imageView.getFitHeight() * 3 / 2);
    }
}