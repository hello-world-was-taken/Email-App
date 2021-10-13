package com.nodamin.emailapp.controller;

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
                    currentScene, this.stage, getStore(this.usernameTextField.getText(), this.passwordTextField.getText()));
            nextWindow.initializeScene();

        }catch (IOException e){
            System.out.println("Inside the change scene function");
        }
        // TODO: NOT NULLIFYING THE REFERENCE, ONLY THE OBJECT
//        currentObject = null;
    }

    @FXML
    void login(){
        try{
            this.changeScene(this);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("AUTHENTICATION ERROR!");
        }

    }

    // Get the messages
    public Store getStore(String userName, String password) {

        Properties properties = new Properties();
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtps.host", "smtps.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");

//        Authenticator authenticator = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                PasswordAuthentication pa = new PasswordAuthentication("masreshatebabal18@gmail.com", "tanahaik2011");
//                return pa;
//            }
//        };

        Session session = Session.getDefaultInstance(properties);
        try{
            store = session.getStore("imaps");
            store.connect("imap.gmail.com",userName, password);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();

//            for(Message message: messages) {
//                System.out.println("FROM: " + Arrays.toString(message.getFrom()));
//                System.out.println("SUBJECT: " + message.getSubject().toString());
//                System.out.println("CONTENT" + message.getContent());
//                break;
//            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Message not found!");
        }
        
        return store;
    }
}