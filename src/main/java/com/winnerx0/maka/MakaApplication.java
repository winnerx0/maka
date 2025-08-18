package com.winnerx0.maka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MakaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MakaApplication.class.getResource("video-player-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(),960, 540);
        stage.setTitle("Maka");
        stage.setScene(scene);

        stage.show();
    }
}
