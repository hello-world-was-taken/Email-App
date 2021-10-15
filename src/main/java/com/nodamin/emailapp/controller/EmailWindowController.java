package com.nodamin.emailapp.controller;

import com.nodamin.emailapp.model.EmailContent;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class EmailWindowController extends BaseWindowController {

    // // ids from the fxml file which will all be initialized once the scene has been loaded
    @FXML
    private MenuBar menuBar;
    @FXML
    private TreeView<String> emailFolderTreeView;
    @FXML
    private WebView webView;
    @FXML
    private TableView<EmailContent> emailTableView;
    @FXML
    private TableColumn<EmailContent, String> fromColumn;
    @FXML
    private TableColumn<EmailContent, String> subjectColumn;
    @FXML
    private TableColumn<EmailContent, Date> dateColumn;

    // non fxml fields
    Store store;
    ObservableList<EmailContent> tableList = FXCollections.observableArrayList();
    // constructor
    public EmailWindowController(String currentScene,
                                 String nextScene,
                                 Stage stage,
                                 Store store) {
        super(currentScene, nextScene, stage);
        System.out.println("EmailWindowController()");
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
            System.out.println("presentData()");
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
            System.out.println("bindFolderName()");
            TreeItem<String> folderTreeItem = new TreeItem<>(folder.getName());
            root.getChildren().add(folderTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                // if it is a type Folder.HOLDS_FOLDERS, it will have list() method which returns array of folders
                bindFolderName(folder.list(), folderTreeItem);
            }
            displayMessageToTable(folder);
        }
    }

    private void displayMessageToTable(Folder folder) throws MessagingException {
        if(folder.getType() != Folder.HOLDS_FOLDERS) {
            System.out.println("displayMesasgesToTable()");
            folder.open(Folder.READ_WRITE);
        } else {
            return;
        }

        // TODO: IMPLEMENT A THREAD
        Message[] messages = folder.getMessages();
//        for(Message message: messages) {
//            EmailContent emailContent = new EmailContent(Arrays.toString(message.getFrom()),
//                    message.getSubject(), message.getSentDate());
//            tableList.add(emailContent);
//        }
        ImplementTask implementTask = new ImplementTask(messages, this.tableList);
        Thread collectMail = new Thread(implementTask);
        collectMail.start();
        implementTask.setOnSucceeded(e -> tableList = implementTask.getValue());
        emailTableView.setItems(tableList);
    }

    // sets the property value factories for the columns
    public void prepareTableColumns() {
        this.fromColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, String>("From"));
        this.subjectColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, String>("Subject"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, Date>("DateSent"));
    }

    @Override
    public void initializeScene() throws IOException {
        super.initializeScene();
        presentData(this.store);
    }


    private class ImplementTask extends Task<ObservableList<EmailContent>> {
        Message [] messages;
        ObservableList<EmailContent> tableList;
        public ImplementTask(Message [] messages, ObservableList<EmailContent> tableList) {
            this.messages = messages;
            this.tableList = tableList;
        }

        @Override
        protected ObservableList<EmailContent> call() throws Exception {
            System.out.println("Inside call()");
            for(Message message: messages) {
                EmailContent emailContent = new EmailContent(Arrays.toString(message.getFrom()),
                        message.getSubject(), message.getSentDate());
                this.tableList.add(emailContent);
            }
            System.out.println(tableList.isEmpty());
            return tableList;
        }
    }
}
