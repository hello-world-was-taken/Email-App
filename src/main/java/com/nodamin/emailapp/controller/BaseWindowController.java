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
    public void initializeScene() throws IOException {
//        System.out.println(this.fxmlName);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.fxmlName));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.load());
        this.stage.setTitle("Hello!");
        this.stage.setScene(scene);
        this.stage.show();
    }
}

//    <?xml version="1.0" encoding="UTF-8"?>
//

//
//
//<AnchorPane fx:id="systemClose" prefHeight="500.0" prefWidth="700.0" xmlns="http://javafx.com/javafx/16" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.nodamin.emailapp.EmailWindow">

//</AnchorPane>
