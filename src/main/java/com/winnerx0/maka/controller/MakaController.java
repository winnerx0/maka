package com.winnerx0.maka.controller;

import com.winnerx0.maka.enums.Volume;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
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
    private Button playPauseButton, previousButton, nextButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private ProgressBar progressBar;

    private IntegerBinding volumeBinding;

    @FXML
    private Text progressTitle, volumeIndicator;

    private static final Logger logger = LoggerFactory.getLogger(MakaController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try(Stream<Path> walk = Files.walk(Path.of("/home/winner/Videos"));) {

            nextButton.setFocusTraversable(false);

            previousButton.setFocusTraversable(false);

            playPauseButton.setFocusTraversable(false);

            files = new LinkedList<>(walk.map(Path::toFile).filter(File::isFile).toList());

            System.out.println(files);

            file = files.get(0);

            PauseTransition idleTimer = new PauseTransition(Duration.seconds(1.5));

            idleTimer.setOnFinished(event -> {
                vBox.setVisible(false);
                vBox.setManaged(false);
            });

            stackPane.setOnMouseMoved(event -> {
                vBox.setVisible(true);
                vBox.setManaged(true);
                idleTimer.playFromStart();
            });

            stackPane.setOnMouseExited(event -> {
                vBox.setVisible(false);
                vBox.setManaged(false);
            });

            playPauseVideo(file);

            progressBar.setOnMouseClicked(event -> {
                double mouseX = event.getX();
                double barWidth = progressBar.getWidth();
                double seekTimeRatio = mouseX / barWidth;
                Duration seekTime = player.getTotalDuration().multiply(seekTimeRatio);
                player.seek(seekTime);
            });

            progressBar.setOnMouseDragged(event -> {
                vBox.setVisible(true);
                vBox.setManaged(true);
                double mouseX = event.getX();
                double barWidth = progressBar.getWidth();
                double seekTimeRatio = mouseX / barWidth;
                Duration seekTime = player.getTotalDuration().multiply(seekTimeRatio);
                player.seek(seekTime);
            });

        } catch (Exception e) {
            System.err.println("Failed to initialize media");
            e.printStackTrace();
        }
    }

    @FXML
    public void playPauseVideo(){

        if(player.getStatus() == MediaPlayer.Status.PLAYING || player.getStatus() == MediaPlayer.Status.UNKNOWN){
            player.pause();
            playPauseButton.setText("Play");
        } else {
            player.play();
            playPauseButton.setText("Pause");
        }
    }

    public void playPauseVideo(File file){

        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }

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
            volumeIndicator.setText(String.format("%d%%", (int) Math.floor(player.getVolume() * 100)));
            setupProgressBinding();
            player.play();
        });

        mediaView.setPreserveRatio(true);
        volumeIndicator.setVisible(false);

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

        previousButton.setFocusTraversable(false);
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

    private void setupProgressBinding() {
        // Create new binding for this player
        DoubleBinding progressBinding = Bindings.createDoubleBinding(
                () -> {
                    Duration currentTime = player.getCurrentTime();
                    Duration totalDuration = player.getTotalDuration();
                    if (totalDuration == null || totalDuration.toSeconds() == 0) {
                        return 0.0;
                    }
                    return currentTime.toSeconds() / totalDuration.toSeconds();
                },
                player.currentTimeProperty(),
                player.totalDurationProperty()
        );

        StringBinding progressTitleBinding = Bindings.createStringBinding(() -> {
            Duration currentTime = player.getCurrentTime();
            Duration totalDuration = player.getTotalDuration();

            if (totalDuration == null || totalDuration.toSeconds() == 0) {
                return String.format("%f / %f", currentTime.toSeconds(), 0.0);
            }

            String current = String.format("%02d:%02d:%02d",
                    Double.valueOf(Math.floor(currentTime.toHours() % 60)).intValue(),
                    Double.valueOf(Math.floor(currentTime.toMinutes() % 60)).intValue(),
                    Double.valueOf(Math.floor(currentTime.toSeconds() % 60)).intValue());

            String total = String.format("%02d:%02d:%02d",
                    Double.valueOf(Math.floor(totalDuration.toHours() % 60)).intValue(),
                    Double.valueOf(Math.floor(totalDuration.toMinutes() % 60)).intValue(),
                    Double.valueOf(Math.floor(totalDuration.toSeconds() % 60)).intValue());

            return String.format("%s / %s", current, total);
        },
                player.currentTimeProperty(),
                player.totalDurationProperty());

        progressBar.progressProperty().bind(progressBinding);
        progressTitle.textProperty().bind(progressTitleBinding);
    }

    public void volumeControl(Volume volume, PauseTransition idleTimer) {
        double volumeInfo = player.getVolume();

        volumeIndicator.setVisible(true);
        if (volume.equals(Volume.UP)) {
            player.setVolume(Math.min(volumeInfo + 0.01, 1.0));
        } else {
            player.setVolume(Math.max(volumeInfo - 0.01, 0.0));
        }

        idleTimer.playFromStart();

        idleTimer.setOnFinished(event -> {
            volumeIndicator.setVisible(false);
            volumeIndicator.setManaged(false);
        });

        double newVolume = player.getVolume();
        volumeIndicator.setText(String.format("%d%%", (int) Math.floor(newVolume * 100)));
    }

}
