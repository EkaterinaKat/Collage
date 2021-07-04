package com.katyshevtceva.collage;

import com.katyshevtceva.collage.logic.Collage;
import com.katyshevtceva.collage.logic.CollageBuilder;
import com.katyshevtceva.collage.logic.Component;
import com.katyshevtceva.collage.logic.ComponentBuilder;
import com.katyshevtseva.fx.Point;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.Arrays;

class Controller implements FxController {
    static final int COLLAGE_WIDTH = 1000;
    static final int COLLAGE_HEIGHT = 780;
    @FXML
    private Pane collagePane;

    @FXML
    private void initialize() {
        Collage collage = new CollageBuilder()
                .height(COLLAGE_HEIGHT)
                .width(COLLAGE_WIDTH)
                .color("#F08080")
                .build();
        collagePane.getChildren().add(collage.getPane());

        Component component1 = new ComponentBuilder(collage, Arrays.asList("/images/1.jpg", "/images/2.jpg"))
                .frontImage("/images/1.jpg")
                .relativeWidth(0.5)
                .relativePosition(new Point(0.1, 0.1))
                .build();
        collage.addComponent(component1);

        Component component2 = new ComponentBuilder(collage, Arrays.asList("/images/3.png", "/images/4.jpg"))
                .frontImage("/images/3.png")
                .relativeWidth(0.3)
                .relativePosition(new Point(0.05, 0.05))
                .build();
        collage.addComponent(component2);
    }
}
