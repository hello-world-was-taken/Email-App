package com.nodamin.emailapp.controller;

import javafx.stage.Stage;

public class EmailWindowController extends BaseWindowController {

    // constructor
    public EmailWindowController(String currentScene, String nextScene, Stage stage, GenericDisplayController genericDisplayController) {
        super(currentScene, nextScene, stage, genericDisplayController);
        this.currentScene = currentScene;
        this.stage = stage;
        this.genericDisplayController = genericDisplayController;
        this.nextScene = nextScene;
    }

    @Override
    public void changeScene() {

    }
}
