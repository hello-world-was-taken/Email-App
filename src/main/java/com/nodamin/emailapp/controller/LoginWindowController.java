package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginWindowController extends BaseWindowController {
    @FXML
    private Label welcomeText;

    // constructor
    public LoginWindowController(String fxmlName, Stage stage) {
        super(fxmlName, stage);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}