package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.ImageUtils;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import lombok.Getter;

import java.util.Objects;

class Image implements ImageContainer {
    @Getter
    private ImageView imageView;
    @Getter
    private ImageContainer imageContainer;

    Image(ImageContainer imageContainer) {
        this.imageView = ImageUtils.getImageViewByAbsolutePath(imageContainer.getUrl());
        this.imageContainer = imageContainer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return imageContainer.getUrl().equals(image.getImageContainer().getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageContainer.getUrl());
    }

    @Override
    public String getUrl() {
        return imageContainer.getUrl();
    }
}
