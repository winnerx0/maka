package com.winnerx0.maka;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MakaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MakaApplication.class.getResource("video-player-view.fxml"));

        Pane root = fxmlLoader.load();

        MakaController controller = fxmlLoader.getController();

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Maka");



        scene.setOnKeyPressed(keyEvent -> {
            switch(keyEvent.getCode()){
                case SPACE:
                    controller.playPauseVideo();
                    break;
                case P:
                    controller.previousVideo();
                    break;
                case N:
                    controller.nextVideo();
                    break;
                case F:
                    stage.setFullScreen(!stage.isFullScreen());
                default:
                    break;
            }
        });

        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.show();
        stage.setFullScreen(true);
    }
}