package com.nodamin.emailapp;

import com.nodamin.emailapp.controller.BaseWindowController;
import com.nodamin.emailapp.controller.LoginWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.IOException;

public class EmailApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        BaseWindowController baseWindowController = new LoginWindowController("/com/nodamin/emailapp/email_window.fxml", stage);
        baseWindowController.initializeScene();
    }

    public static void main(String[] args) {
        launch();
    }
}