package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.security.KeyPair;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    // reference to the menu controller
    public MenuBar MainMenu;

    @FXML
    // reference to the file menu
    public Menu File;

    @FXML
    // reference to the close menuItem
    public MenuItem Close;

    @FXML
    // reference to the edit menu
    public Menu Edit;

    @FXML
    // reference to the delete menuItem
    public MenuItem Delete;

    @FXML
    // reference to the about menuItem
    public MenuItem About;

    @FXML
    // reference to the splitPane object
    public SplitPane SplitPane;

    @FXML
    // reference to the anchor pane containing input
    public AnchorPane AnchorPaneWithInput;

    @FXML
    // reference to the anchorpane containing input
    public TextArea TextAreaInput;

    @FXML
    // reference to the anchor pane containing output
    public AnchorPane AnchorPaneWithOutput;

    @FXML
    // reference to the output field
    public TextArea OutputField;

    @FXML
    // reference to save
    public MenuItem Save;

    @FXML
    // reference to save as
    public MenuItem SavePath;

    @FXML
    // reference to open
    public MenuItem Open;
    public Menu ActionsMenu;
    public MenuItem genKeys;
    public MenuItem LoadKeyFile;
    public MenuItem Encrypt;
    public MenuItem Decrypt;

    private File ioFile;
    private KeyPair keys;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onCloseClick() {
        Platform.exit();
    }

    @FXML
    public void onSaveAsClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setTitle("Save As");
        saveFile(fileChooser.showSaveDialog(MainMenu.getScene().getWindow()));
    }

    public void onSave() {
        if (this.ioFile == null) {
            this.ioFile = new File("output.txt");
        }
        saveFile(ioFile);
    }

    public void saveFile(File file) {
        try {
            if (file != null) {
                BufferedWriter dataWriter = new BufferedWriter(new FileWriter(file));
                dataWriter.write(TextAreaInput.getText());
                dataWriter.close();
                this.ioFile = file;
            } else {
                System.out.println("The passed file value is not valid");
            }
        } catch (IOException exception) {
            System.out.println("IO Error");
        }
    }

    public void openFile(File file) {
        try {
            if (file != null) {
                BufferedReader dataReader = new BufferedReader(new FileReader(file));
                TextAreaInput.setText(dataReader.lines().collect(
                        Collectors.joining(System.lineSeparator())));
                dataReader.close();
                this.ioFile = file;
            } else {
                System.out.println("The passed file value is not valid");
            }
        } catch (IOException exception) {
            System.out.println("IO Error");
        }
    }

    public void onOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        openFile(fileChooser.showOpenDialog(MainMenu.getScene().getWindow()));
    }

    public void onLoadKeyClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        KeyLoader loader = new KeyLoader(fileChooser.showOpenDialog(MainMenu.getScene().getWindow()));
        this.keys = loader.getKeys();
    }

    public void onGenKeyClick() throws Exception {
        keyGenerator keygen = new keyGenerator();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Key files (*.key)", "*.key"));
        keygen.makeKeyFile(fileChooser.showSaveDialog(MainMenu.getScene().getWindow()));
        this.keys = keygen.getKeys();
    }

    public void onEncryptClick() {
        if (keys != null) {
            TextEncryptor encryptor = new TextEncryptor(keys);
            OutputField.setText(encryptor.encrypt(TextAreaInput.getText()));
        }
    }

    public void onDecryptClick() {
        if (keys != null) {
            TextDecryptor decryptor = new TextDecryptor(keys);
            OutputField.setText(decryptor.decrypt(TextAreaInput.getText()));
        }
    }
}
