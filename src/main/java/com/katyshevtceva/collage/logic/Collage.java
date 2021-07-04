package com.katyshevtceva.collage.logic;

import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Collage {
    private List<Component> components = new ArrayList<>();
    private Pane pane;
    @Getter
    private boolean editingMode = true;

    Collage(Pane pane) {
        this.pane = pane;
        new ModificationEngine(this);
    }

    public Pane getPane() {
        return pane;
    }

    public void addComponent(Component component) {
        components.add(component);
        moveComponentToFirstPlanAndRefillPane(component);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    double getWidth() {
        return pane.getMinWidth();
    }

    double getHeight() {
        return pane.getMinHeight();
    }

    List<Component> getComponents() {
        return components;
    }

    void moveComponentToFirstPlanAndRefillPane(Component component) {
        component.setZ(getImagesMaxZ() + 1);
        normalizeZCoordinates();
        clearAndRefillPaneWithComponents();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private void clearAndRefillPaneWithComponents() {
        pane.getChildren().clear();
        components.sort(Comparator.comparing(Component::getZ));
        for (Component component : components) {
            if (editingMode)
                pane.getChildren().addAll(component.getFrontImageWithButtons());
            else
                pane.getChildren().add(component.getFrontImage());
        }
    }

    private int getImagesMaxZ() {
        return components.stream().max(Comparator.comparing(Component::getZ)).get().getZ();
    }

    private void normalizeZCoordinates() {
        components.sort(Comparator.comparing(Component::getZ));
        for (int i = 0; i < components.size(); i++)
            components.get(i).setZ(i + 1);
    }
}
