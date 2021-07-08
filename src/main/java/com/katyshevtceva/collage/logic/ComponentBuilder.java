package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import static com.katyshevtceva.collage.logic.Constants.DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

public class ComponentBuilder {
    private Collage collage;
    private String frontImageUrl;
    private List<String> imageUrls;
    private Point relativePosition;
    private double relativeWidth = DEFAULT_INIT_COMPONENT_RELATIVE_WIDTH;
    private int z = 1;

    public ComponentBuilder(Collage collage, List<String> imageUrls) {
        if (imageUrls.size() == 0)
            throw new RuntimeException();

        this.collage = collage;
        this.imageUrls = imageUrls;
    }

    public ComponentBuilder frontImage(String url) {
        if (!imageUrls.contains(url))
            throw new RuntimeException();

        this.frontImageUrl = url;
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

    public ComponentBuilder z(int z) {
        this.z = z;
        return this;
    }

    public Component build() {
        List<Image> images = new ArrayList<>();
        Image frontImage = null;
        for (String url : imageUrls) {
            Image image = new Image(new ImageView(new javafx.scene.image.Image(url)), url);
            images.add(image);
            if (url.equals(frontImageUrl))
                frontImage = image;
        }
        if (frontImage == null)
            frontImage = images.get(0);

        double initWidth = relativeWidth * collage.getWidth();
        double initHeight = getHeightByWidth(frontImage.getImageView(), initWidth);

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

        return new Component(collage, frontImage, images, z);
    }

}
