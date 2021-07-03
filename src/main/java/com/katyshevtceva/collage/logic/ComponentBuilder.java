package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.katyshevtceva.collage.logic.Constants.DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtceva.collage.logic.Utils.getHeightByWidth;

public class ComponentBuilder {
    private Collage collage;
    private String frontImagePath;
    private List<String> imagePaths;
    private Point relativePosition;
    private double relativeWidth = DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;

    public ComponentBuilder(Collage collage, List<String> imagePaths) {
        if (imagePaths.size() == 0)
            throw new RuntimeException();

        this.collage = collage;
        this.imagePaths = imagePaths;
    }

    public ComponentBuilder frontImage(String path) {
        if (!imagePaths.contains(path))
            throw new RuntimeException();

        this.frontImagePath = path;
        return this;
    }

    public ComponentBuilder relativePosition(Point relativePosition) {
        if (relativePosition.getX() < 0
                || relativePosition.getX() > 1
                || relativePosition.getY() < 0
                || relativePosition.getY() > 1)
            throw new RuntimeException();

        this.relativePosition = relativePosition;
        return this;
    }

    public ComponentBuilder relativeWidth(double relativeWidth) {
        if (relativeWidth < Constants.MIN_COMPONENT_RELATIVE_WIDTH || relativeWidth > 1)
            throw new RuntimeException();

        this.relativeWidth = relativeWidth;
        return this;
    }

    public Component build() {
        List<ImageView> imageViews = new ArrayList<>();
        ImageView frontImage = null;
        for (String path : imagePaths) {
            ImageView imageView = new ImageView(new Image(path));
            imageViews.add(imageView);
            if (path.equals(frontImagePath))
                frontImage = imageView;
        }
        if (frontImage == null)
            frontImage = imageViews.get(0);

        double initWidth = relativeWidth * collage.getWidth();
        double initHeight = getHeightByWidth(frontImage, initWidth);

        Point initPosition;
        if (relativePosition != null) {
            initPosition = new Point(
                    relativePosition.getX() * collage.getWidth(),
                    relativePosition.getY() * collage.getHeight());
        } else {
            initPosition = new Point(
                    collage.getWidth() / 2.0 - initWidth / 2.0,
                    collage.getHeight() / 2.0 - initHeight / 2.0
            );
        }

        frontImage.setFitWidth(initWidth);
        frontImage.setFitHeight(initHeight);
        frontImage.setX(initPosition.getX());
        frontImage.setY(initPosition.getY());

        return new Component(collage, frontImage, imageViews);
    }

}
