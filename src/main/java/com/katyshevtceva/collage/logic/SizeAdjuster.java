package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.ImageView;
import lombok.Getter;

import static com.katyshevtceva.collage.logic.Constants.BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;

class SizeAdjuster {
    private final Component component;
    @Getter
    private ImageView imageView;
    private final double buttonSize;

    SizeAdjuster(Collage collage, Component component) {
        this.component = component;
        buttonSize = collage.getWidth() * BUTTON_SIZE_TO_COLLAGE_WIDTH_RATIO;
        initializeImageView();
        setPos();
    }

    private void initializeImageView() {
        imageView = new ImageView(ButtonImageFactory.getInstance().getSizeAdjusterImage());
        imageView.setFitWidth(buttonSize);
        imageView.setFitHeight(buttonSize);
    }

    boolean containsPoint(Point point) {
        return ((point.getX() > imageView.getX()) && (point.getX() < (imageView.getX() + buttonSize))) &&
                ((point.getY() > imageView.getY()) && (point.getY() < (imageView.getY() + buttonSize)));
    }

    void setPos() {
        imageView.setX(component.getPos().getX() + component.getImage().getWidth() - imageView.getFitWidth() / 2);
        imageView.setY(component.getPos().getY() + component.getImage().getHeight() - imageView.getFitHeight() / 2);
    }
}
