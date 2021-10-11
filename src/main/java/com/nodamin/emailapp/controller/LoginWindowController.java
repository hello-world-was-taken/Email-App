package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginWindowController extends BaseWindowController {
    // ids from the fxml file which will all be initialized once the scene has been loaded
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginBtn;

    // constructor
    public LoginWindowController(String currentScene,
                                 String nextScene,
                                 Stage stage) {
        super(currentScene, nextScene, stage);
        this.currentScene = currentScene;
        this.stage = stage;
        this.nextScene = nextScene;
    }

    @Override
    public void changeScene(BaseWindowController currentObject) throws IOException {
        BaseWindowController nextWindow = new EmailWindowController(nextScene,
                currentScene, this.stage);
        nextWindow.initializeScene();
        // TODO: NOT NULLIFYING THE REFERENCE, ONLY THE OBJECT
        currentObject = null;
    }

    @FXML
    void login(){
        try{
            this.changeScene(this);
        }catch(Exception e) {
            e.printStackTrace();
            return;
        }

    }
}