package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginWindowController extends BaseWindowController {
    // ids from the fxml file
    @FXML
    private Label welcomeText;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    // constructor
    public LoginWindowController(String fxmlName, Stage stage) {
        super(fxmlName, stage);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}