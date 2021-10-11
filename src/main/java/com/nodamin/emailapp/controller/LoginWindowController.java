package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginWindowController extends BaseWindowController {
    // ids from the fxml file which will all be initialized once the scene has been loaded
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginBtn;

    // constructor
    public LoginWindowController(String currentScene, String nextScene, Stage stage, GenericDisplayController genericDisplayController) {
        super(currentScene, nextScene, stage, genericDisplayController);
        this.currentScene = currentScene;
        this.stage = stage;
        this.genericDisplayController = genericDisplayController;
        this.nextScene = nextScene;
    }

    @Override
    public void changeScene() {

    }

    @FXML
    void login() {

    }
}