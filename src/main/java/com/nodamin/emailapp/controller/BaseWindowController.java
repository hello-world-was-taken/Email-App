package com.nodamin.emailapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseWindowController {
    // initializing values to be used across the subclasses
    String fxmlName = null;
    Stage stage = null;

    // constructor


    public BaseWindowController(String fxmlName, Stage stage) {
        this.fxmlName = fxmlName;
        this.stage = stage;
    }

    // scene initializer
    public void initializeScene() {
//        System.out.println(this.fxmlName);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.fxmlName));
        fxmlLoader.setController(this);
        Scene scene;
        try{
            scene = new Scene(fxmlLoader.load());
        }catch(IOException e) {
            System.out.println("Error loading scene! Check if file is corrupted or erred!");
            return;
        }
        this.stage.setTitle("Hello!");
        this.stage.setScene(scene);
        this.stage.show();
    }
}