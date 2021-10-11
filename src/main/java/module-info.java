module com.nodamin.emailapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires activation;
    requires java.mail;

    opens com.nodamin.emailapp to javafx.fxml;
    exports com.nodamin.emailapp;
    exports com.nodamin.emailapp.controller;
    opens com.nodamin.emailapp.controller to javafx.fxml;
}