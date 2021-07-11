package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Styler;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollageBuilder {
    private int width = 500;
    private int height = 500;
    private String color;
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
        this.color = color;
        return this;
    }

    public CollageBuilder editingMode(boolean editingMode) {
        this.editingMode = editingMode;
        return this;
    }

    public CollageBuilder allExistingImages(List<String> allExistingImagesUrls) {
        this.allExistingImages = allExistingImagesUrls.stream().map(Image::new).collect(Collectors.toList());
        return this;
    }

    public Collage build() {
        return new Collage(createPane(), editingMode, allExistingImages);
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
        if (color != null) {
            pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, color));
        }
        return pane;
    }
}
