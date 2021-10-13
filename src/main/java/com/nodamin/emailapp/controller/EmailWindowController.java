package com.nodamin.emailapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.io.IOException;
import java.util.Arrays;

public class EmailWindowController extends BaseWindowController {

    // // ids from the fxml file which will all be initialized once the scene has been loaded
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<String> emailFolderTreeView;
    @FXML
    private WebView webView;
    @FXML
    private TableView<?> emailTableView;

    // non fxml fields
    Store store;
    // constructor
    public EmailWindowController(String currentScene,
                                 String nextScene,
                                 Stage stage,
                                 Store store) {
        super(currentScene, nextScene, stage);
        this.currentScene = currentScene;
        this.stage = stage;
        this.nextScene = nextScene;
        this.store = store;
    }

    @Override
    public void changeScene(BaseWindowController currentObject) throws IOException {

    }

    public void presentData(Store store) {
        try {
            Folder folder = store.getFolder("INBOX");
//            Message[] message = folder.getMessages();
            folder.open(Folder.READ_ONLY);
            System.out.println(folder.getName());
            TreeItem<String> folderTreeItem = new TreeItem<String>(folder.getName());
            TreeItem<String> root = new TreeItem<>("Emails");
            root.getChildren().add(folderTreeItem);
            this.emailFolderTreeView.setRoot(root);

        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println("WHAT");
        }
    }

    @Override
    public void initializeScene() throws IOException {
        super.initializeScene();
        presentData(this.store);
    }
}
