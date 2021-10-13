package com.nodamin.emailapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseWindowController {
    // initializing values to be used across the subclasses
    protected String nextScene;
    protected String currentScene;
    protected Stage stage;

    // constructor


    public BaseWindowController(String currentScene,
                                String nextScene,
                                Stage stage) {
        this.currentScene = currentScene;
        this.stage = stage;
        this.nextScene = nextScene;
    }

    // scene initializer
    public void initializeScene() throws IOException {
        // log
        System.out.println("initializing " + currentScene + " scene...");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.currentScene));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load());
        this.stage.setTitle("Hello!");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public abstract void changeScene(BaseWindowController currentObject) throws IOException;
    public void prepareTableColumns() {

    }
}