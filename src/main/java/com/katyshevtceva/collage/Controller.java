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

import java.util.Arrays;

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
                .build();
        collagePane.getChildren().add(collage.getPane());

        Component component1 = new ComponentBuilder(collage, Arrays.asList("/images/1.jpg", "/images/2.jpg"))
                .frontImage("/images/1.jpg")
                .relativeWidth(0.5)
                .relativePosition(new Point(0.1, 0.1))
                .z(2)
                .build();
        collage.addComponent(component1);

        Component component2 = new ComponentBuilder(collage, Arrays.asList("/images/3.png", "/images/4.jpg"))
                .frontImage("/images/3.png")
                .relativeWidth(0.3)
                .relativePosition(new Point(0.05, 0.05))
                .z(3)
                .build();
        collage.addComponent(component2);

        Component component3 = new ComponentBuilder(collage, Arrays.asList("/images/5.png", "/images/6.jpg"))
                .frontImage("/images/5.png")
                .relativeWidth(0.4)
                .relativePosition(new Point(0, 0))
                .z(1)
                .build();
        collage.addComponent(component3);

        editModeCheckBox.setSelected(DEFAULT_EDITING_MODE);
        editModeCheckBox.setOnAction(event -> collage.setEditingMode(editModeCheckBox.isSelected()));
    }
}
