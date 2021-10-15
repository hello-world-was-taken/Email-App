package com.nodamin.emailapp.controller;

import com.nodamin.emailapp.model.EmailContent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
import java.util.Objects;

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
    Folder[] folders;
    TreeItem<String> treeItem;
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

    public void setFolders() throws Exception{
        this.folders = store.getDefaultFolder().list();
    }

    public void setTreeView(TreeItem<String> tr) {
        this.treeItem = tr;
    }

    @Override
    public void changeScene(BaseWindowController currentObject) throws IOException {
        throw new IOException("An implemented change scene method in EmailWindowController");
    }

    // one time functions
    public TreeItem<String> setTreeRoot() {
        TreeItem<String> root = new TreeItem<>("Emails");
        this.emailFolderTreeView.setRoot(root);
        return  root;
    }

    private ObservableList<EmailContent> returnEachFolder(Folder[] folders) throws MessagingException {
        for(Folder folder: folders) {
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                returnEachFolder(folder.list());
            }
//            displayPartialMessage(folder.getName());
            if(folder.getType() != Folder.HOLDS_FOLDERS) {
                folder.open(Folder.READ_WRITE);
            }else {
                System.out.println("THIS FOLDER IS NOT OPENED" + folder.getName());
            }
            Message[] messages = folder.getMessages();

            //  NEW THREAD THAT RETURNS A TABLE LIST FOR EACH MESSAGE ON THE CURRENT FOLDER
            GetObservableTableItems getObservableTableItems = new GetObservableTableItems(messages, this.tableList);
            Thread collectMail = new Thread(getObservableTableItems, "collectMail");
            collectMail.start();
            getObservableTableItems.setOnSucceeded(e -> this.tableList = getObservableTableItems.getValue());
            getObservableTableItems.setOnRunning(e-> System.out.println("collect mail running..."));
            if(this.tableList.isEmpty()) {
                System.out.println("EMPTY");
            }else {
                System.out.println("NOT EMPTY");
            }
            emailTableView.setItems(tableList);
            return this.tableList;
        }
        return this.tableList;
    }

    private ObservableList<EmailContent> displayPartialMessage(String folderName) throws MessagingException {
        System.out.println("HERE IS THE ERROR" + ": " + folderName);
        Folder folder = this.store.getFolder(folderName);
        if(folder.getType() != Folder.HOLDS_FOLDERS) {
            folder.open(Folder.READ_WRITE);
        }else {
            System.out.println("THIS FOLDER IS NOT OPENED" + folderName);
        }
        Message[] messages = folder.getMessages();

        //  NEW THREAD THAT RETURNS A TABLE LIST FOR EACH MESSAGE ON THE CURRENT FOLDER
        GetObservableTableItems getObservableTableItems = new GetObservableTableItems(messages, this.tableList);
        Thread collectMail = new Thread(getObservableTableItems, "collectMail");
        collectMail.start();
        getObservableTableItems.setOnSucceeded(e -> this.tableList = getObservableTableItems.getValue());
        getObservableTableItems.setOnRunning(e-> System.out.println("collect mail running..."));
        emailTableView.setItems(tableList);
//        tableList.clear();
        return this.tableList;
    }

    // sets the property value factories for the columns
    public void prepareTableColumns() {
        this.fromColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, String>("From"));
        this.subjectColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, String>("Subject"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<EmailContent, Date>("DateSent"));
    }

    // initializes and displays the emails
    @Override
    public void initializeScene() throws IOException {
        super.initializeScene();
        System.out.println("initializeScene()");

        // getting a reference to the root set to the TreeView which shows the email folders
        TreeItem<String> root = setTreeRoot();
        try {
            setFolders();
            //NEW THREAD TO BIND THE FOLDERS
            BindFolderToTreeView bd = new BindFolderToTreeView(this.folders, root);
            Thread bindFolderToTree = new Thread(bd, "bindFolderToTree");
            bindFolderToTree.start();
            bd.setOnSucceeded(e-> {
                this.treeItem = bd.getValue();
                // this thread must firs finish since we use the tree items in the coming thread
                try {
                    returnEachFolder(this.store.getDefaultFolder().list());
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * By iterating through each of the messages in the folder that was passed in, it will populate the object's
     * observable list (which is used to populate the Table view) with the Email Content objects. These objects are
     * the once that the tree item's property value factory use.
     */
    private class GetObservableTableItems extends Task<ObservableList<EmailContent>> {
        Message [] messages;
        ObservableList<EmailContent> tableList;
        public GetObservableTableItems(Message [] messages, ObservableList<EmailContent> tableList) {
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
            return tableList;
        }
    }

    /**
     * By taking the folder that was passed, it will populate the Tree View with the name of each folder
     */
    private class BindFolderToTreeView extends Task<TreeItem<String>> {
        Folder[] folders;
        TreeItem<String> root;

        public BindFolderToTreeView(Folder[] folders, TreeItem<String> root) {
            this.folders = folders;
            this.root = root;
        }

        private TreeItem<String> bindFolderNameInner(Folder[] folders, TreeItem<String> root) throws MessagingException {
            for(Folder folder: folders) {
                TreeItem<String> folderTreeItem = new TreeItem<>(folder.getName());
                root.getChildren().add(folderTreeItem);
                if(folder.getType() == Folder.HOLDS_FOLDERS) {
                    // if it is a type Folder.HOLDS_FOLDERS, it will have list() method which returns array of folders
                    bindFolderNameInner(folder.list(), folderTreeItem);
                }
//            displayMessageToTable(folder);
            }
            return root;
        }

        @Override
        protected TreeItem<String> call() throws Exception {
           return bindFolderNameInner(this.folders, this.root);
//            return null;
        }
    }
}
