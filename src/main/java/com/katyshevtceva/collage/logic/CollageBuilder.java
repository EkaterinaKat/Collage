package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.fx.Styler;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollageBuilder {
    private int width = 500;
    private int height = 500;
    private String color = "#FFFFFF";
    private boolean editingMode = true;
    private List<Image> allExistingImages = new ArrayList<>();

    public CollageBuilder width(int width) {
        this.width = width;
        return this;
    }

    public CollageBuilder height(int height) {
        this.height = height;
        return this;
    }

    public CollageBuilder color(String color) {
        if (color != null)
            this.color = color;
        return this;
    }

    public CollageBuilder editingMode(boolean editingMode) {
        this.editingMode = editingMode;
        return this;
    }

    public CollageBuilder allExistingImages(List<ImageContainer> allExistingImagesContainers) {
        this.allExistingImages = allExistingImagesContainers.stream().map(Image::new).collect(Collectors.toList());
        return this;
    }

    public Collage build() {
        return new Collage(createPane(), editingMode, allExistingImages, color);
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, color));
        return pane;
    }
}
