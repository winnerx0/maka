# Maka Video Player

Maka is a simple JavaFX-based video player application that allows users to play, pause, and navigate between videos in a given directory. It also features a progress bar with interactive seeking, fullscreen mode, and auto-hide controls.

## Features

* **Video Playback**: Play, pause, and resume videos.
* **Keyboard Shortcuts**:

    * `SPACE` → Play/Pause video
    * `P` → Previous video
    * `N` → Next video
    * `F` → Toggle fullscreen
* **Auto-hide Controls**: Video controls (`VBox`) appear when the mouse moves and auto-hide after 30 seconds of inactivity.
* **Progress Bar**:

    * Displays video progress.
    * Click and drag to seek within the video.
* **Video Playlist**: Automatically loads all video files from the `~/Videos` directory.
* **Responsive MediaView**: Scales video to fit the window while maintaining aspect ratio.

## Installation & Usage

Clone the repository and run with Maven:

```bash
git clone https://github.com/winnerx0/maka
cd maka
mvn clean javafx:run
```

## Requirements

* **Java 17+** (or compatible with JavaFX version used)
* **JavaFX SDK**
* A valid directory containing video files (default: `~/Videos`).

## Possible Improvements

* Add support for playlists and folders selection.
* Add volume controls and mute functionality.
* Support subtitles.
* Show video thumbnails in the navigation.
* Add double-click fullscreen toggle.
* Support for playing videos in any directory

## License

This project is licensed under the **MIT License**. See the LICENSE file for details.
