package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Styler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Collage {
    private Pane pane;

    public Collage(int width, int height) {
        initializePane(width, height);
    }

    public Node getNode() {
        return pane;
    }

    public void setColor(String color) {
        pane.setStyle(Styler.getColorfullStyle(Styler.ThingToColor.BACKGROUND, color));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private void initializePane(int width, int height) {
        pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
    }

}
