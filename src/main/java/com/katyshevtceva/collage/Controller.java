package com.katyshevtceva.collage;

import com.katyshevtceva.collage.logic.Collage;
import com.katyshevtceva.collage.logic.CollageBuilder;
import com.katyshevtceva.collage.logic.Component;
import com.katyshevtceva.collage.logic.ComponentBuilder;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Controller implements FxController {
    private static final boolean DEFAULT_EDITING_MODE = true;
    static final int COLLAGE_WIDTH = 1000;
    static final int COLLAGE_HEIGHT = 780;
    @FXML
    private Pane collagePane;
    @FXML
    private CheckBox editModeCheckBox;

    @FXML
    private void initialize() {
        Collage collage = new CollageBuilder()
                .height(COLLAGE_HEIGHT)
                .width(COLLAGE_WIDTH)
                .color("#F08080")
                .editingMode(DEFAULT_EDITING_MODE)
                .allExistingImages(getAllExistingImages())
                .build();
        collagePane.getChildren().add(collage.getPane());

        Component component1 = new ComponentBuilder(collage, Stream.of("1.jpg", "2.jpg").map(this::getUrlByFileName).collect(Collectors.toList()))
                .frontImage(getUrlByFileName("1.jpg"))
                .relativeWidth(0.5)
                .relativePosition(new Point(0.1, 0.1))
                .z(2)
                .build();
        collage.addComponent(component1);

        Component component2 = new ComponentBuilder(collage, Stream.of("3.png").map(this::getUrlByFileName).collect(Collectors.toList()))
                .frontImage(getUrlByFileName("3.png"))
                .relativeWidth(0.3)
                .relativePosition(new Point(0.05, 0.05))
                .z(3)
                .build();
        collage.addComponent(component2);

        Component component3 = new ComponentBuilder(collage, Stream.of("5.png", "6.jpg").map(this::getUrlByFileName).collect(Collectors.toList()))
                .frontImage(getUrlByFileName("5.png"))
                .relativeWidth(0.4)
                .relativePosition(new Point(0, 0))
                .z(1)
                .build();
        collage.addComponent(component3);

        editModeCheckBox.setSelected(DEFAULT_EDITING_MODE);
        editModeCheckBox.setOnAction(event -> collage.setEditingMode(editModeCheckBox.isSelected()));
    }

    private List<String> getAllExistingImages() {
        return Stream.of("1.jpg", "2.jpg", "3.png", "4.jpg", "5.png", "6.jpg", "7.png", "8.jpg", "9.jpg",
                "10.jpg", "11.jpg", "12.jpg", "13.jpg", "14.png", "15.png", "16.png", "17.jpg")
                .map(this::getUrlByFileName).collect(Collectors.toList());
    }

    private String getUrlByFileName(String fileName) {
        return "D:\\Code\\Collage\\src\\main\\resources\\images\\" + fileName;
    }
}
