package com.nodamin.emailapp.controller;

import javafx.stage.Stage;

public class EmailWindowController extends BaseWindowController {

    // constructor
    public EmailWindowController(String currentScene,
                                 String nextScene,
                                 Stage stage,
                                 GenericDisplayController genericDisplayController,
                                 BaseWindowController currentObjectReference) {
        super(currentScene, nextScene, stage, genericDisplayController, currentObjectReference);
        this.currentScene = currentScene;
        this.stage = stage;
        this.genericDisplayController = genericDisplayController;
        this.nextScene = nextScene;
        this.currentObjectReference = currentObjectReference;
    }

    @Override
    public void changeScene() {

    }
}
