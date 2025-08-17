module com.winnerx0.maka {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens com.winnerx0.maka to javafx.fxml;
    exports com.winnerx0.maka;
}