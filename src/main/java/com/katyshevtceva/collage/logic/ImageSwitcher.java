package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.stream.Collectors;

import static com.katyshevtceva.collage.logic.Constants.BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;

class ImageSwitcher {
    private final Component component;
    @Getter
    private ImageView imageView;
    private final double buttonSize;

    ImageSwitcher(Collage collage, Component component) {
        this.component = component;
        buttonSize = collage.getWidth() * BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;
        initializeImageView();
        setPos();
    }

    private void initializeImageView() {
        imageView = new ImageView(ButtonImageFactory.getInstance().getImageSwitcherImage());
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
        imageView.setOnMouseClicked(event -> Utils.openEditableImageSelectionDialog(
                component.getImages(), component.getCollage().getFreeImages(),
                imageContainer -> component.switchImage((com.katyshevtceva.collage.logic.Image) imageContainer),
                imageContainers -> component.setImages(imageContainers.stream()
                        .map(imageContainer -> (com.katyshevtceva.collage.logic.Image) imageContainer).collect(Collectors.toList()))));
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
