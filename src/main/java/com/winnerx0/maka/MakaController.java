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
    private Button playButton;

    @FXML
    private MediaView mediaView;

    private File file;

    private List<File> files;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try(Stream<Path> walk = Files.walk(Path.of("/home/winner/Videos"));) {


            files = new LinkedList<>(walk.map(Path::toFile).filter(File::isFile).toList());

            System.out.println(files);

            file = files.get(0);

            playVideo(file);
        } catch (Exception e) {
            System.err.println("Failed to initialize media");
            e.printStackTrace();
        }
    }

    public void playVideo(File file){

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

        mediaView.setPreserveRatio(true);

        mediaView.setMediaPlayer(player);

    }

    @FXML
    public void previousVideo(){

        String currentVideo = player.getMedia().getSource();

        System.out.println(currentVideo);

        int previousVideoIndex = files.indexOf(Path.of(currentVideo.replace("file:", "")).toFile()) - 1;

        File previousVideo;

        if(previousVideoIndex == -1){
            previousVideo = files.getLast();
        }else {
            previousVideo = files.get(previousVideoIndex);
        }

        System.out.println(previousVideo.getAbsoluteFile());

        playVideo(previousVideo);
    }

    @FXML
    public void nextVideo(){
        String currentVideo = player.getMedia().getSource();

        System.out.println("current video " + currentVideo);

        int nextVideoIndex = files.indexOf(Path.of(currentVideo.replace("file:", "")).toFile()) + 1;

        File nextVideo;

        if(nextVideoIndex == files.size()){
            nextVideo = files.getFirst();
        } else {
            nextVideo = files.get(nextVideoIndex);
        }

        System.out.println(nextVideo.getAbsoluteFile());


        playVideo(nextVideo);
    }
}
