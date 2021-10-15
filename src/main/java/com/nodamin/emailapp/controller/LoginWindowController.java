package com.nodamin.emailapp.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


public class LoginWindowController extends BaseWindowController {
    // ids from the fxml file which will all be initialized once the scene has been loaded
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button loginBtn;

    // non fxml fields
    Store store = null;

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
    public void changeScene(BaseWindowController currentObject) {
        try {
            BaseWindowController nextWindow = new EmailWindowController(nextScene,
                    currentScene, this.stage, this.store);
            nextWindow.initializeScene();
            nextWindow.prepareTableColumns();
        }catch (IOException e){
            System.out.println("Inside the change scene function");
        }
        // TODO: NOT NULLIFYING THE REFERENCE, ONLY THE OBJECT
//        currentObject = null;
    }

    @FXML
    void login() {
        try{
            this.getStore(this.usernameTextField.getText(), this.passwordTextField.getText());
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("AUTHENTICATION ERROR!");
        }

    }

    // Get the messages
    public Store getStore(String userName, String password) {
        GetStore getStore = new GetStore(userName, password);
        Thread getStoreThread = new Thread(getStore);
        getStoreThread.start();
        getStore.setOnRunning(e-> System.out.println("it is running"));
        getStore.setOnSucceeded(e -> {
            this.store = getStore.getValue();
            if(this.store == null) {
                System.out.println("Null value returned on succeeded");
            }else{
                System.out.println("NOT NULL");
            }
            this.changeScene(this);
//            return this.store;
        });
        return this.store;
    }

    private class GetStore extends Task<Store> {
        String userName;
        String password;
        Store innerStore;

        public GetStore(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected Store call() throws Exception {
            Properties properties = new Properties();
            properties.put("incomingHost", "imap.gmail.com");
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtps.host", "smtps.gmail.com");
            properties.put("mail.smtps.auth", "true");
            properties.put("outgoingHost", "smtp.gmail.com");

            Session session = Session.getDefaultInstance(properties);
            try{
                innerStore = session.getStore("imaps");
                innerStore.connect("imap.gmail.com", this.userName, this.password);

            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Message not found!");
            }

            return innerStore;
        }
    }
}