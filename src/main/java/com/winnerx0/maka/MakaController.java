package com.winnerx0.maka;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MakaController implements Initializable {


    private Media media;

    private MediaPlayer player;

    @FXML
    private Button playButton;

    @FXML
    private MediaView mediaView;

    private File file;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            file = new File("/home/winner/Videos/AnimePahe_Grand_Blue_Season_2_-_06_720p_SubsPlease.mp4");
            if (!file.exists()) {
                System.err.println("Video file not found!");
                return;
            }

            media = new Media(file.toURI().toString());

            // Add error handler before creating MediaPlayer
            media.setOnError(() -> {
                System.err.println("Media error: " + media.getError());
            });

            player = new MediaPlayer(media);

            player.setOnError(() -> {
                System.err.println("Player error: " + player.getError());
            });

            player.setOnReady(() -> {
                System.out.println("Duration: " + media.getDuration());
                mediaView.fitWidthProperty().bind(mediaView.getScene().widthProperty());
                mediaView.fitHeightProperty().bind(mediaView.getScene().heightProperty());
                mediaView.setPreserveRatio(true);

                player.play();
            });

            mediaView.setMediaPlayer(player);

        } catch (Exception e) {
            System.err.println("Failed to initialize media");
            e.printStackTrace();
        }
    }

    @FXML
    public void playVideo(){
        player.play();
    }
}
