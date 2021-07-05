package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;

class SizeAdjuster {
    private Component component;
    @Getter
    private ImageView imageView;
    private double buttonSize;

    SizeAdjuster(Collage collage, Component component) {
        this.component = component;
        buttonSize = collage.getWidth() * 0.03;
        initializeImageView();
        setPos();
    }

    private void initializeImageView() {
        imageView = new ImageView(new Image("/icons/resizing_arrow.png"));
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
    }

    boolean containsPoint(Point point) {
        return ((point.getX() > imageView.getX()) && (point.getX() < (imageView.getX() + buttonSize))) &&
                ((point.getY() > imageView.getY()) && (point.getY() < (imageView.getY() + buttonSize)));
    }

    void setPos() {
        imageView.setX(component.getPos().getX() + component.getFrontImage().getWidth() - imageView.getFitWidth() / 2);
        imageView.setY(component.getPos().getY() + component.getFrontImage().getHeight() - imageView.getFitHeight() / 2);
    }
}
