package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.dialog.controller.ImageSelectDialogController.ImageContainer;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import lombok.Getter;

class Image implements ImageContainer {
    @Getter
    private ImageView imageView;
    @Getter
    private String url;

    Image(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    double getX() {
        return imageView.getX();
    }

    double getY() {
        return imageView.getY();
    }

    void setX(double x) {
        imageView.setX(x);
    }

    void setY(double y) {
        imageView.setY(y);
    }

    double getWidth() {
        return imageView.getFitWidth();
    }

    double getHeight() {
        return imageView.getFitHeight();
    }

    void setFitWidth(double width) {
        imageView.setFitWidth(width);
    }

    void setFitHeight(double height) {
        imageView.setFitHeight(height);
    }

    void setOnContextMenuRequested(EventHandler<? super ContextMenuEvent> value) {
        imageView.setOnContextMenuRequested(value);
    }
}
