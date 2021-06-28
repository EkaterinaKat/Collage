package com.katyshevtceva.collage;

import com.katyshevtseva.fx.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        new WindowBuilder("/fxml/main.fxml").
                setController(new Controller())
                .setHeight(900).setWidth(1200)
                .setTitle("Collage")
                .showWindow();
    }
}
