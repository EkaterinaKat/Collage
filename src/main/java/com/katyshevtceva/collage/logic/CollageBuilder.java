package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Styler;
import javafx.scene.layout.Pane;

public class CollageBuilder {
    private int width = 500;
    private int height = 500;
    private String color;
    private boolean editingMode = true;

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

    public Collage build() {
        return new Collage(createPane(), editingMode);
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
