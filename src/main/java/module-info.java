module com.ksilisk.sapr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.ksilisk.sapr to javafx.fxml;
    exports com.ksilisk.sapr;
    exports com.ksilisk.sapr.controller;
    exports com.ksilisk.sapr.dto;
    opens com.ksilisk.sapr.controller to javafx.fxml;
}