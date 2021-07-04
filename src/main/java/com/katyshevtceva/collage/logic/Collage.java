package com.katyshevtceva.collage.logic;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Collage {
    private List<Component> components = new ArrayList<>();
    private Pane pane;

    Collage(Pane pane) {
        this.pane = pane;
        new ModificationEngine(this);
    }

    public Pane getPane() {
        return pane;
    }

    public void addComponent(Component component) {
        components.add(component);
        pane.getChildren().add(component.getFrontImage());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    double getWidth() {
        return pane.getMinWidth();
    }

    double getHeight() {
        return pane.getMinHeight();
    }

    boolean editingMode() {
        return true; //todo
    }

    List<Component> getComponents() {
        return components;
    }

    void moveComponentToFirstPlan(Component component) {
        //todo
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


}
