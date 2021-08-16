package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Size;
import javafx.scene.layout.Pane;

import java.util.Comparator;
import java.util.List;

import static com.katyshevtseva.fx.ImageSizeUtil.getHeightByWidth;

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
            Image image = component.getImage();

            double imageWidth = component.getRelativeWidth() * collageSize.getWidth();
            double imageHeight = getHeightByWidth(image.getImageView(), imageWidth);

            image.setFitWidth(imageWidth);
            image.setFitHeight(imageHeight);
            image.setX(component.getRelativePosition().getX() * collageSize.getWidth());
            image.setY(component.getRelativePosition().getY() * collageSize.getHeight());
        }
    }

    private static void fillPaneWithComponents(Pane pane, List<StaticComponent> components) {
        components.sort(Comparator.comparing(StaticComponent::getZ));
        for (StaticComponent component : components) {
            pane.getChildren().add(component.getImage().getImageView());
        }
    }
}
