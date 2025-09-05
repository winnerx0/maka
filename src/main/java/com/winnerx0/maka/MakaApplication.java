package com.winnerx0.maka;

import com.winnerx0.maka.controller.MakaController;
import com.winnerx0.maka.enums.Skip;
import com.winnerx0.maka.enums.Volume;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MakaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MakaApplication.class.getResource("video-player-view.fxml"));

        Pane root = fxmlLoader.load();

        MakaController controller = fxmlLoader.getController();

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Maka");


        PauseTransition volumeTransition = new PauseTransition(Duration.seconds(1.5));

        PauseTransition skipTransition = new PauseTransition(Duration.seconds(1.5));

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
                case UP:
                    controller.volumeControl(Volume.UP, volumeTransition);
                    break;
                case DOWN:
                    controller.volumeControl(Volume.DOWN, volumeTransition);
                    break;
                case RIGHT:
                    controller.skipControl(Skip.FORWARD, skipTransition);
                    break;
                case LEFT:
                    controller.skipControl(Skip.BACKWARD, skipTransition);
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