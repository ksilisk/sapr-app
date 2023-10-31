module com.ksilisk.sapr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.slf4j;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires commons.math3;
    requires com.fasterxml.jackson.dataformat.xml;

    opens com.ksilisk.sapr to javafx.fxml;
    opens com.ksilisk.sapr.controller to javafx.fxml;

    exports com.ksilisk.sapr;
    exports com.ksilisk.sapr.controller;
    exports com.ksilisk.sapr.dto;
    exports com.ksilisk.sapr.payload;
}