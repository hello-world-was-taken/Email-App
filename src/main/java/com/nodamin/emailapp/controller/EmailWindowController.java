package com.nodamin.emailapp.controller;

import javafx.stage.Stage;

import java.io.IOException;

public class EmailWindowController extends BaseWindowController {

    // constructor
    public EmailWindowController(String currentScene,
                                 String nextScene,
                                 Stage stage) {
        super(currentScene, nextScene, stage);
        this.currentScene = currentScene;
        this.stage = stage;
        this.nextScene = nextScene;
    }

    @Override
    public void changeScene(BaseWindowController currentObject) throws IOException {

    }
}
