package com.katyshevtceva.collage;

import com.katyshevtceva.collage.logic.Collage;
import com.katyshevtseva.fx.WindowBuilder.FxController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import static com.katyshevtceva.collage.Constants.COLLAGE_HEIGHT;
import static com.katyshevtceva.collage.Constants.COLLAGE_WIDTH;

class Controller implements FxController {
    @FXML
    private Pane collagePane;

    @FXML
    private void initialize() {
        Collage collage = new Collage(COLLAGE_WIDTH, COLLAGE_HEIGHT);
        collage.setColor("#F08080");
        collagePane.getChildren().add(collage.getNode());
    }
}
