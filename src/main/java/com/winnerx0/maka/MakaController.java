package com.winnerx0.maka;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MakaController implements Initializable {


    private Media media;

    private MediaPlayer player;

    @FXML
    private MediaView mediaView;

    private File file;

    private LinkedList<File> files;

    @FXML
    private Button playPauseButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane buttonAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try(Stream<Path> walk = Files.walk(Path.of("/home/winner/Videos"));) {


            files = new LinkedList<>(walk.map(Path::toFile).filter(File::isFile).toList());

            System.out.println(files);

            file = files.get(0);

            stackPane.setOnMouseEntered(event -> {
                buttonAnchorPane.setVisible(true);
                buttonAnchorPane.setManaged(true);
            });

            stackPane.setOnMouseExited(event -> {
                buttonAnchorPane.setVisible(false);
                buttonAnchorPane.setManaged(false);
            });

            playPauseVideo(file);

        } catch (Exception e) {
            System.err.println("Failed to initialize media");
            e.printStackTrace();
        }
    }

    @FXML
    public void playPauseVideo(){

        if(player.getStatus() ==  MediaPlayer.Status.PLAYING || player.getStatus() ==  MediaPlayer.Status.UNKNOWN){
            player.pause();
            playPauseButton.setText("Play");
        } else {
            player.play();
            playPauseButton.setText("Pause");
        }
    }

    public void playPauseVideo(File file){

        playPauseButton.setText("Pause");

        media = new Media(file.toURI().toString());

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

        mediaView.setPreserveRatio(true);

        mediaView.setMediaPlayer(player);

        player.play();

    }

    @FXML
    public void previousVideo(){

        String currentVideo = player.getMedia().getSource();

        System.out.println(currentVideo);

        int previousVideoIndex = files.indexOf(Path.of(currentVideo.replace("file:", "")).toFile()) - 1;

        File previousVideo;

        if(previousVideoIndex == -1){
            previousVideo = files.peekLast();
        }else {
            previousVideo = files.get(previousVideoIndex);
        }

        System.out.println(previousVideo.getAbsoluteFile());

        playPauseVideo(previousVideo);
    }

    @FXML
    public void nextVideo(){
        String currentVideo = player.getMedia().getSource();

        System.out.println("current video " + currentVideo);

        int nextVideoIndex = files.indexOf(Path.of(currentVideo.replace("file:", "")).toFile()) + 1;

        File nextVideo;

        if(nextVideoIndex == files.size()){
            nextVideo = files.peekFirst();
        } else {
            nextVideo = files.get(nextVideoIndex);
        }

        System.out.println(nextVideo.getAbsoluteFile());


        playPauseVideo(nextVideo);
    }
}
