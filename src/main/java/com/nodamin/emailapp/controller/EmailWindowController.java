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
        throw new IOException("An implemented change scene method in EmailWindowController");
    }

    public void presentData(Store store) {
        try {
            Folder[] folders = store.getDefaultFolder().list();
            TreeItem<String> root = new TreeItem<>("Emails");
            this.emailFolderTreeView.setRoot(root);
            bindFolderName(folders, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind the name of the tree items to the tree view
    private void bindFolderName(Folder[] folders, TreeItem<String> root) throws Exception {
        for(Folder folder: folders) {
            TreeItem<String> folderTreeItem = new TreeItem<>(folder.getName());
            root.getChildren().add(folderTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                // if it is a type Folder.HOLDS_FOLDERS, it will have list() method which returns array of folders
                bindFolderName(folder.list(), folderTreeItem);
            }
        }
    }

    @Override
    public void initializeScene() throws IOException {
        super.initializeScene();
        presentData(this.store);
    }
}
