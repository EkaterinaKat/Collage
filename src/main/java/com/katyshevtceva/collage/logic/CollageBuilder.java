package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageContainer;
import com.katyshevtseva.general.OneInOneOutKnob;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class CollageBuilder {
    private int width = 500;
    private int height = 500;
    private List<Image> allExistingImages = new ArrayList<>();
    private OneInOneOutKnob<ImageContainer, List<Image>> availableToAddToComponentImagesSupplier;

    public CollageBuilder availableToAddToComponentImagesSupplier(OneInOneOutKnob<ImageContainer, List<Image>> availableToAddToComponentImagesSupplier) {
        this.availableToAddToComponentImagesSupplier = availableToAddToComponentImagesSupplier;
        return this;
    }

    public CollageBuilder width(int width) {
        this.width = width;
        return this;
    }

    public CollageBuilder height(int height) {
        this.height = height;
        return this;
    }

    public CollageBuilder allExistingImages(List<Image> allExistingImages) {
        this.allExistingImages = allExistingImages;
        return this;
    }

    public Collage build() {
        return new Collage(createPane(), allExistingImages, availableToAddToComponentImagesSupplier);
    }

    private Pane createPane() {
        Pane pane = new Pane();
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setMinHeight(height);
        pane.setMaxHeight(height);
        return pane;
    }
}
