package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Collage {
    @Getter
    private List<Component> components = new ArrayList<>();
    @Getter
    private List<Image> allExistingImages;
    private Pane pane;
    @Getter
    private boolean editingMode = true;

    Collage(Pane pane, boolean editingMode, List<Image> allExistingImages) {
        this.pane = pane;
        this.editingMode = editingMode;
        this.allExistingImages = allExistingImages;
        new ModificationEngine(this);
    }

    public Pane getPane() {
        return pane;
    }

    public void createComponent() {
        new StandardDialogBuilder()
                .openImageSelectionDialog(new ArrayList<>(getFreeImages()), imageContainer -> {
                    addComponent(new ComponentBuilder(this, Collections.singletonList(((Image) imageContainer).getImageContainer())).build());
                });
    }

    public void addComponent(Component component) {
        if (components.stream().anyMatch(component1 -> component1.getZ() == component.getZ()))
            component.setZ(getImagesMaxZ() + 1);
        components.add(component);
        refillPaneWithComponents();
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
        refillPaneWithComponents();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    double getWidth() {
        return pane.getMinWidth();
    }

    double getHeight() {
        return pane.getMinHeight();
    }

    void moveComponentToFirstPlan(Component component) {
        component.setZ(getImagesMaxZ() + 1);
        normalizeZCoordinates();
        refillPaneWithComponents();
    }

    void deleteComponent(Component component) {
        components.remove(component);
        refillPaneWithComponents();
    }

    void refillPaneWithComponents() {
        pane.getChildren().clear();
        components.sort(Comparator.comparing(Component::getZ));
        for (Component component : components) {
            if (editingMode)
                pane.getChildren().addAll(component.getFrontImageWithButtons());
            else
                pane.getChildren().add(component.getFrontImage().getImageView());
        }
    }

    List<Image> getFreeImages() {
        return allExistingImages.stream().filter(image -> {
            for (Component component : components)
                if (component.getImages().contains(image))
                    return false;
            return true;
        }).collect(Collectors.toList());
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    private int getImagesMaxZ() {
        return components.stream().max(Comparator.comparing(Component::getZ)).get().getZ();
    }

    private void normalizeZCoordinates() {
        components.sort(Comparator.comparing(Component::getZ));
        for (int i = 0; i < components.size(); i++)
            components.get(i).setZ(i + 1);
    }
}
