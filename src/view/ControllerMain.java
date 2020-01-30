package view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Control class for the FXML file fxMain
 */
public class ControllerMain {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private CheckBox cbFacebook;

    @FXML
    private CheckBox cbTwitter;

    @FXML
    private Button btnChooseHashtag;

    @FXML
    private Button btnRandomHashtag;

    @FXML
    private Button btnRandmDateTime;

    @FXML
    private Button btnSavePost;

    @FXML
    private Button btnResetFields;

    @FXML
    private Button btnCopyLog;

    @FXML
    private Button btnSaveLog;

    @FXML
    private TextArea taMessage;

    @FXML
    private TextArea taHashtags;

    @FXML
    private ListView<?> lvFBGruppen;

    @FXML
    private Label lbRestChar;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfTime;

    @FXML
    private ComboBox<?> cbFBGruppen;

    @FXML
    private ToggleButton tbActivate;

    @FXML
    private Button btnPicture;

    @FXML
    private Label lbStatus;

    @FXML
    private Label lbFilename;

    @FXML
    private TableView<?> tvPosts;

    @FXML
    private TableColumn<?, ?> tcText1;

    @FXML
    private TableColumn<?, ?> tcDate1;

    @FXML
    private TableColumn<?, ?> tcPlatform1;

    @FXML
    private TableColumn<?, ?> tcReaction1;

    @FXML
    private TableColumn<?, ?> tcPostAction1;

    @FXML
    private Button btnPostPrev;

    @FXML
    private Button btnPostNext;

    @FXML
    private TableView<?> tvHashtags;

    @FXML
    private TableColumn<?, ?> tcTheme1;

    @FXML
    private TableColumn<?, ?> tcList1;

    @FXML
    private TableColumn<?, ?> tcHashAction1;

    @FXML
    private Button btnHashPrev;

    @FXML
    private Button btnHashNext;

    @FXML
    private Button btnNewList;

    @FXML
    private TextField btnFBUsername;

    @FXML
    private TextField btnFBPasswort;

    @FXML
    private TextField btnFBToken;

    @FXML
    private TextField btnFBKey;

    @FXML
    private TextField ConsumerKey;

    @FXML
    private TextField ConsumerSecret;

    @FXML
    private TextField AccessToken;

    @FXML
    private TextField AccessTokenSecret;

    @FXML
    private Button btnSaveSettings;

    File selectedFile;


    /**
     * This method opens a new window, where the user can pick stored hashtags for the new posts
     *
     * @param event Buttonclick
     * @throws IOException if the fxml file can't be opened
     */
    @FXML
    void ShowHashtags(ActionEvent event) throws IOException {
        try {
            Stage MainStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("fxTableHashtags.fxml").openStream());
            Scene scene = new Scene(root);
            MainStage.setTitle("SMT - Social Media Tool: Hashtags");
            MainStage.setScene(scene);
            MainStage.setX(400);
            MainStage.setY(300);
            MainStage.show();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    /**
     * This method will clean all fields and selected files
     */
    @FXML
    void clearFields() {
        // Leere die gesamten Felder
        taMessage.clear();
        taHashtags.clear();
        dpDate.getEditor().clear();
        tfTime.clear();

        // Entferne die Datei aus dem File Objekt
        selectedFile = null;
        lbFilename.setText("Keine Bild oder Film ausgewählt");
    }

    /**
     * This method select a picture or video file from the Hard Drive and add it to the post
     */
    @FXML
    void MediaChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bild oder Video anhängen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Format", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG Format", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF Format", "*.gif"),
                new FileChooser.ExtensionFilter("PNG Format", "*.png"),
                new FileChooser.ExtensionFilter("AVI (AVI-Video)", "*.avi"),
                new FileChooser.ExtensionFilter("MOV (QuickTime-Video)", "*.mov"),
                new FileChooser.ExtensionFilter("MP4 (MPEG-4-Video)", "*.mp4"),
                new FileChooser.ExtensionFilter("MPEG (MPEG-Video)", "*.mpeg"),
                new FileChooser.ExtensionFilter("WMV (Windows Media-Video)", "*.wmv"),
                new FileChooser.ExtensionFilter("OGG (Ogg Media-Format)", "*.ogg")
        );
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            lbFilename.setText(selectedFile.getName());
        } else {
            lbFilename.setText("Keine Bild oder Film ausgewählt");
        }

    }

    @FXML
    void createNewHashList(ActionEvent event) {
        // Neue Hashliste einfügen
    }

    @FXML
    void postAutomatic(ActionEvent event) {

        if (tbActivate.isSelected()) {
            System.out.println("Automatisierung ist aktiviert");
        } else {
            System.out.println("Automatisierung ist deaktiviert");
        }
    }

    @FXML
    void postEntry(ActionEvent event) {
        // Speichert einen neuen Post in die Datenbank
    }

    @FXML
    void countHashtag(KeyEvent event) {
        //While writing/entering the Tags, it counts the total character length of post and tags
        String post = taMessage.getText();
        String tag = taHashtags.getText();
        String tlen = taMessage.getText() + taHashtags.getText();

        int len = post.length() + tag.length();
        String msg = String.valueOf(len) + " / 280 zeichen";
        lbRestChar.setText(msg);

        //The text in post and hashtag must turn Red when the total characters limit exceeds
        if (tlen.length() > 63206) {
            taMessage.setStyle("-fx-text-inner-color: red;");
            taHashtags.setStyle("-fx-text-inner-color: red;");

            //The check box for twitter must be unchecked and disabled when the total characters limit exceeds 280 characters
        } else if (tlen.length() > 280) {
            taMessage.setStyle("-fx-text-inner-color: black;");
            taHashtags.setStyle("-fx-text-inner-color: black;");
            cbTwitter.setSelected(false);
            cbTwitter.setDisable(true);

            //The text must set to default color(black) when the total characters is reduced to the limit-
        } else {
            taMessage.setStyle("-fx-text-inner-color: black;");
            taHashtags.setStyle("-fx-text-inner-color: black;");
            cbTwitter.setDisable(false);
            //Syamala
        }
    }

    @FXML
    void countPost(KeyEvent event) {
        //While writing the Post, it counts the total character length of post and tags
        String post = taMessage.getText();
        String tag = taHashtags.getText();
        String tlen = taMessage.getText() + taHashtags.getText();

        int len = post.length() + tag.length();
        String msg = String.valueOf(len) + " / 280 zeichen";
        lbRestChar.setText(msg);

        //The text in post and hashtag must turn Red when the total characters limit exceeds
        if (tlen.length() > 63206) {
            taMessage.setStyle("-fx-text-inner-color: red;");
            taHashtags.setStyle("-fx-text-inner-color: red;");

            //The check box for twitter must be unchecked and disabled when the total characters limit exceeds 280 characters
        } else if (tlen.length() > 280) {
            taMessage.setStyle("-fx-text-inner-color: black;");
            taHashtags.setStyle("-fx-text-inner-color: black;");
            cbTwitter.setSelected(false);
            cbTwitter.setDisable(true);
            //The text must set to default color(black) when the total characters is reduced to the limit 255 charcaters
        } else {
            taMessage.setStyle("-fx-text-inner-color: black;");
            taHashtags.setStyle("-fx-text-inner-color: black;");
            cbTwitter.setDisable(false);
        }
    }

    /**
     * This class randomly creates the date and time for the associated input fields
     */
    @FXML
    void randomDateTime() {
        //When clicked ,must generate Random Date and Time
        Instant jetzt = Instant.now();
        Instant einWoche = Instant.now().plus(Duration.ofDays(7));
        Instant randomInstant = zwischen(jetzt, einWoche);
        Date randonDate = Date.from(randomInstant);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String randomTime = timeFormat.format(randonDate);
        dpDate.setValue(randomInstant.atZone(ZoneId.systemDefault()).toLocalDate());
        tfTime.setText(randomTime);
    }


    private Instant zwischen(Instant jetzt, Instant einWoche) {
        long anfang = jetzt.getEpochSecond();
        long ende = einWoche.getEpochSecond();
        long randomSeconds = ThreadLocalRandom.current().nextLong(anfang, ende);
        return Instant.ofEpochSecond(randomSeconds);
    }

    @FXML
    void copyLog(ActionEvent event) {
        // Idee: Kopiert die gesamten Log in den Zwischenspeicher
    }

    @FXML
    void saveLog(ActionEvent event) {
        // Idee: Speichert die gesamte Log in eine txt Datei

    }

    @FXML
    void randomHashtags(ActionEvent event) {
        // Holt sich Hashtags aus der Datenbank. Anzahl undefiniert.
    }

    @FXML
    void saveSettings(ActionEvent event) {
        // Speichert die Accountdaten der Profile
    }

    @FXML
    void initialize() {
        // DB starten?

    }
}
