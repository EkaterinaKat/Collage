package com.katyshevtceva.collage.logic;

import com.katyshevtseva.image.ImageContainer;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import lombok.Getter;

import java.util.Objects;

public class Image implements ImageContainer {
    @Getter
    private final ImageView imageView;
    @Getter
    private final ImageContainer imageContainer;

    public Image(ImageContainer imageContainer) {
        this.imageView = new ImageView(imageContainer.getImage());
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
        return imageContainer.getPath().equals(image.getImageContainer().getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageContainer.getPath());
    }

    @Override
    public javafx.scene.image.Image getImage() {
        return imageContainer.getImage();
    }

    @Override
    public String getPath() {
        return imageContainer.getPath();
    }

    @Override
    public String getFileName() {
        return imageContainer.getFileName();
    }
}
