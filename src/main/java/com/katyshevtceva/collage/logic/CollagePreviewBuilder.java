package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.BackgroundLoadedImageAdjuster;
import com.katyshevtseva.fx.Size;
import javafx.scene.layout.Pane;

import java.util.Comparator;
import java.util.List;

public class CollagePreviewBuilder {

    public static Pane buildPreview(Size size, List<StaticComponent> components) {
        Pane pane = createPane(size);
        tuneComponentsImages(components, size);
        fillPaneWithComponents(pane, components);
        return pane;
    }

    private static Pane createPane(Size size) {
        Pane pane = new Pane();
        pane.setMinWidth(size.getWidth());
        pane.setMaxWidth(size.getWidth());
        pane.setMinHeight(size.getHeight());
        pane.setMaxHeight(size.getHeight());
        return pane;
    }

    private static void tuneComponentsImages(List<StaticComponent> components, Size collageSize) {
        for (StaticComponent component : components) {
            new BackgroundLoadedImageAdjuster(component.getImage().getImageContainer().getImage(), () -> {
                component.tuneSizeAndPos(collageSize);
            }).start();
        }
    }

    private static void fillPaneWithComponents(Pane pane, List<StaticComponent> components) {
        components.sort(Comparator.comparing(StaticComponent::getZ));
        for (StaticComponent component : components) {
            pane.getChildren().addAll(component.getImageViews());
        }
    }
}
