package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginWindowController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}