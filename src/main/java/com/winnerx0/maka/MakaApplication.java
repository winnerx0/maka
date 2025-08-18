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

//        Player player = new Player("src/main/resources/AnimePahe_Grand_Blue_Season_2_-_06_720p_SubsPlease.mp4");
        Scene scene = new Scene(fxmlLoader.load(),800, 500);
        stage.setTitle("Maka");
        stage.setScene(scene);

        stage.show();
    }
}
