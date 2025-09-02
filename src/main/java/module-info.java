module com.winnerx0.maka {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;
    requires org.slf4j;

    opens com.winnerx0.maka to javafx.fxml;
    exports com.winnerx0.maka;
    exports com.winnerx0.maka.controller;
    opens com.winnerx0.maka.controller to javafx.fxml;
}