package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollageBuilder {
    private int width = 500;
    private int height = 500;
    private List<Image> allExistingImages = new ArrayList<>();

    public CollageBuilder width(int width) {
        this.width = width;
        return this;
    }

    public CollageBuilder height(int height) {
        this.height = height;
        return this;
    }

    public CollageBuilder allExistingImages(List<ImageContainer> allExistingImagesContainers) {
        this.allExistingImages = allExistingImagesContainers.stream().map(Image::new).collect(Collectors.toList());
        return this;
    }

    public Collage build() {
        return new Collage(createPane(), allExistingImages);
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
        return pane;
    }
}
