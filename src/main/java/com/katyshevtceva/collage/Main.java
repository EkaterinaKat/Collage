package com.katyshevtceva.collage;

import com.katyshevtseva.fx.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.katyshevtceva.collage.CollageTestController.COLLAGE_HEIGHT;
import static com.katyshevtceva.collage.CollageTestController.COLLAGE_WIDTH;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        new WindowBuilder("/fxml/main.fxml").
                setController(new CollageTestController())
                .setSize(COLLAGE_HEIGHT + 200, COLLAGE_WIDTH + 150)
                .setTitle("Collage")
                .showWindow();
    }
}
