package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.general.OneArgKnob;
import com.katyshevtseva.general.OneInOneOutKnob;
import com.katyshevtseva.image.ImageContainer;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Collage {
    @Getter
    private final List<Component> components = new ArrayList<>();
    @Getter(AccessLevel.PACKAGE)
    private final List<Image> allExistingImages;
    private final Pane pane;
    private final OneInOneOutKnob<ImageContainer, List<Image>> availableToAddToComponentImagesSupplier;

    Collage(Pane pane, List<Image> allExistingImages, OneInOneOutKnob<ImageContainer, List<Image>> availableToAddToComponentImagesSupplier) {
        this.pane = pane;
        this.allExistingImages = allExistingImages;
        this.availableToAddToComponentImagesSupplier = availableToAddToComponentImagesSupplier;
        new ModificationEngine(this);
    }

    public Pane getPane() {
        return pane;
    }

    public void openImageToAddSelectionDialog() {
        new StandardDialogBuilder().openImageSelectionDialog(new ArrayList<>(getFreeImages()), getComponentAdder());
    }

    public void openImageToAddSelectionDialog(List<Image> imagesToSelectFrom) {
        new StandardDialogBuilder().openImageSelectionDialog(new ArrayList<>(imagesToSelectFrom), getComponentAdder());
    }

    public OneArgKnob<ImageContainer> getComponentAdder() {
        return imageContainer -> addComponent(
                new ComponentBuilder(this, Collections.singletonList(((Image) imageContainer).getImageContainer()))
                        .build());
    }

    public void addComponent(Component component) {
        if (components.stream().anyMatch(component1 -> component1.getZ() == component.getZ()))
            component.setZ(getImagesMaxZ() + 1);
        components.add(component);
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
            pane.getChildren().addAll(component.getFrontImageWithButtons());
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

    List<Image> getImagesAvailableToAddToExistingOne(Image image) {
        if (availableToAddToComponentImagesSupplier != null) {
            return availableToAddToComponentImagesSupplier.execute(image.getImageContainer());
        } else {
            return getFreeImages();
        }
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
