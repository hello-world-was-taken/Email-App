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

    // scene initializer
    public void sceneInitializer(Stage stage, String string) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(string));
        fxmlLoader.setController(this);
        Parent parent = null;
        try{
            parent = fxmlLoader.load();
        }catch(IOException e) {
            System.out.println("Error loading scene! Check if file is corrupted or erred!");
            return;
        }
        Scene scene = new Scene(parent);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}