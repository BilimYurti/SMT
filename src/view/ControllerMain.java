package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import datenbank.beans.HashtagsBean;

import datenbank.beans.PostEintragBean;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.HashtagsEintrag;
import model.PostEintrag;
import model.SocialmediaAccount;
import model.UserEintrag;
import socialmedia.PostToFacebook;
import socialmedia.SocialMediaWorker;
import datenbank.beans.SocialmediaAccountBean;
import utils.Helper;

/**
 * The Controller Class for the FXML file 'fxMain'
 */
public class ControllerMain {
    @FXML
    public Label lblSaveTWAccountStatus;
    @FXML
    public Label lblSaveFBAccountStatus;
    @FXML
    public Tab tabEinstellungen; // Tab name id
    @FXML
    public Button btnRandmDateTime;
    @FXML
    public Tab tabAllePosts;
    @FXML
    public TableColumn<PostEintrag, String> tcPid;
    @FXML
    public TableColumn<PostEintrag, String> tcMedia;
    @FXML
    public Tab tabHashtags;
    @FXML
    public Tab tabNeuerPost;
    @FXML
    public MenuItem miCopyPost;
    @FXML
    public TableColumn tcHID;
    @FXML
    public ComboBox cbFBGruppen;
    @FXML
    public Label lblfb1;
    @FXML
    public Label lblfb11;
    @FXML
    public Label lblfb111;
    @FXML
    public Label lblfb1111;
    @FXML
    public Label lbltw1;
    @FXML
    public Label lbltw2;
    @FXML
    public Label lbltw3;
    @FXML
    public Label lbltw4;
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
    private Button btnRandomDateTime;
    @FXML
    private Button btnSavePost;
    @FXML
    private Button btnResetFields;
    @FXML
    private TextArea taMessage;
    @FXML
    private TextArea taHashtags;
    @FXML
    private ListView<String> lvFBGruppen;
    @FXML
    private Label lbRestChar;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField tfTime;
    @FXML
    private ComboBox<String> cbFBPage;
    @FXML
    private ToggleButton tbActivate;
    @FXML
    private Button btnPicture;
    @FXML
    private Label lbMessageStatus;
    @FXML
    private Label lbFilename;
    @FXML
    private TableView<PostEintrag> tvPosts;
    @FXML
    private TableColumn<PostEintrag, String> tcText;
    @FXML
    private TableColumn<PostEintrag, String> tcDate;
    @FXML
    private TableColumn<PostEintrag, String> tcPlatform;
    @FXML
    private TableColumn<PostEintrag, String> tcReaction;
    @FXML
    private TableColumn<PostEintrag, String> tcPostAction;
    @FXML
    private TableView<HashtagsEintrag> tvHashtags;
    @FXML
    private TableColumn<HashtagsEintrag, String> tcTheme;
    @FXML
    private TableColumn<HashtagsEintrag, String> tcHashtags;
    @FXML
    private TableColumn<HashtagsEintrag, String> tcHashAction;
    @FXML
    private Button btnNewList;
    @FXML
    private Button btnLogSave;
    @FXML
    private TextField tfFBUserAccessToken;
    @FXML
    private TextField tfFBPageAccessToken;
    @FXML
    private TextField tfFBAppID;
    @FXML
    private TextField tfFBAppSecret;
    @FXML
    private TextField ConsumerKey;
    @FXML
    private TextField ConsumerSecret;
    @FXML
    private TextField AccessToken;
    @FXML
    private TextField AccessTokenSecret;
    @FXML
    private Button btnSaveSettingsTW;
    @FXML
    private Button btnSaveSettingsFB;
    @FXML
    private TextArea taLog;
    @FXML
    private Label lbLogSavedFeedback;
    @FXML
    private ContextMenu cmPosts;
    @FXML
    private MenuItem miDeletePost;
    @FXML
    private ContextMenu cmHashtags;
    @FXML
    private MenuItem miUpdateHashList;
    @FXML
    private MenuItem miDeleteHashList;

    private UserEintrag user;  // The Main Loggedin User (this is set during successfull Login Phase)
    Helper helper = new Helper();  // Helper object with functions
    private SocialmediaAccount socialmediaAccount; // the users socialmedia account data
    File selectedFile;
    String hashtags = "";
    Timeline socialMediaWorkerTimer; // controls the SocialMediaWorker object
    SocialMediaWorker socialMediaWorker; // checks what/when to post to Social Media

    // ------ check posts every x seconds to send them to social media channels! ------------------
    int workerWaitSeconds = 10; //  here use later 100 or more seconds, 3 sec only for tests ------------------
    //---------------------------------------------------------------------------------------------

    Date date = new Date();

    /**
     * This Method sets the userObject here, after login of the main user
     *
     * @param userObject userObject with uid,email,pw
     */
    public void setUser(UserEintrag userObject) {
        this.user = userObject;
        System.out.println("Uid: " + this.user.getId());
    }


    /**
     * This method opens the FXML file "fxTableHashtags" in a small window
     */
    @FXML
    void ShowHashtags() {
        try {
            Stage MainStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("fxTableHashtags.fxml").openStream());
            root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            //Get controller of fxTableHashtags
            ControllerTableHashtags hashCon = loader.getController();

            Scene scene = new Scene(root);

            MainStage.setTitle("SMT - Social Media Tool: Hashtags");
            MainStage.setScene(scene);
            MainStage.showAndWait();

            hashtags = hashtags + hashCon.getData();
        } catch (IOException ex) {
            ex.getStackTrace();
        }

        System.out.println(hashtags);
        if (hashtags.isEmpty()) {
            hashtags = "";
        } else if (taHashtags.getText().isEmpty()) {
            taHashtags.appendText(hashtags);
            countHashtag();
            hashtags = "";
        } else {
            taHashtags.appendText(" " + hashtags);
            countHashtag();
            hashtags = "";
        }
    }

    /**
     * This method clear all fields and the selected file on the FXML file fxMain
     */
    @FXML
    void clearFields() {
        this.taMessage.setText("");
        this.taHashtags.setText("");
        this.dpDate.getEditor().clear();
        this.tfTime.setText("");
        countHashtag();
        selectedFile = null;
        lbFilename.setText("Keine Bild oder Film ausgewählt");
    }

    /**
     * This method opens a File Chooser for Pictures and Video Files
     */
    @FXML
    void MediaChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Bild oder Video anhängen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Bild Formate", "*.jpg", "*.jpeg", "*.gif", "*.png"),
                new FileChooser.ExtensionFilter("Video Formate", "*.avi", "*.mov", "*.mp4", "*.mpeg", "*.wmv", "*.ogg")
        );
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            //System.out.println(selectedFile.getAbsolutePath());
            lbFilename.setText("Datei: " + selectedFile.getName());
        } else {
            lbFilename.setText("Keine Bild oder Film ausgewählt");
        }

    }

    /**
     * This method opens the FXML file fxAddHashtagListe in a new Window
     */
    @FXML
    void createNewHashList() {
        try {
            Stage MainStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("fxAddHashtagListe.fxml").openStream());
            root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            //Get controller of fxAddHashtagListe
            ControllerAddHashtagListe newListCon = loader.getController();

            Scene scene = new Scene(root);

            MainStage.setTitle("SMT - Social Media Tool: Hashtagliste Hinzufügen");
            MainStage.setScene(scene);
            MainStage.showAndWait();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
        getHashTable();
    }

    /**
     * This method enable or disable the automatic posting of Messages
     */
    @FXML
    void postAutomatic() {

        if (this.tbActivate.isSelected()) {

            // Todo: Checke ob User seine SocialMedia Account daten in Einstellungen eingegeben hat:

            // socialMediaWorker mit uid und kanalinfos füttern:
            this.socialMediaWorker.init(this.user.getId(),
                    tfFBAppID.getText(), tfFBAppSecret.getText(), tfFBUserAccessToken.getText(), tfFBPageAccessToken.getText(),
                    ConsumerKey.getText(), ConsumerSecret.getText(), AccessToken.getText(), AccessTokenSecret.getText(), taLog);

            // worker starten:
            this.socialMediaWorkerTimer.play();
            this.tbActivate.setStyle("-fx-background-color:cyan");
            System.out.println("Automatisierung ist aktiviert");
            LocalDateTime lt = LocalDateTime.now().withNano(0); // ohne millisecs
            taLog.appendText(lt + ": Automatisierung ist aktiviert.\n");
        } else {
            this.socialMediaWorkerTimer.stop();
            this.tbActivate.setStyle("-fx-background-color:lightgrey");
            LocalDateTime lt = LocalDateTime.now().withNano(0); // ohne millisecs
            System.out.println("Automatisierung ist deaktiviert");
            taLog.appendText(lt + ": Automatisierung ist deaktiviert.\n");
        }
    }

    /**
     * This method add generated Social Media Posts in the Database
     *
     * @param event
     */
    @FXML
    void postMessage(ActionEvent event) {
        // Speichert einen neuen Social Media Post in die Datenbank
        // check ob Postzeit ausgewählt und in der Zukunft liegt:
        String postTime = this.helper.checkIfLocalDateTimeIsNotNullAndInFuture(dpDate.getValue(), tfTime.getText());
        if (postTime != null && !postTime.isEmpty()) {
            // message,hashtags und selectedfile können nicht alle empty sein, da sonst kein Post content:
            if (!(taMessage.getText().isEmpty() && taHashtags.getText().isEmpty() && selectedFile == null)) {
                // check if any socialmedia platform is selected:
                if ((cbFacebook.isSelected() && cbFBPage.getValue() != null) || cbTwitter.isSelected()) {
                    // Speichere Post hier in die SocialmediaPosts table
                    String posttext = taMessage.getText() + " " + taHashtags.getText();
                    String mediafile = "";
                    if (selectedFile != null) {  // check if a mediafile like image or video is selected
                        mediafile = selectedFile.getAbsolutePath();
                    }

                    ArrayList<Integer> selectedPlatformsArr = new ArrayList<>(); // 0 = no platform selected, will not be postet
                    // checke ob auf Twitter gepostet werden soll:
                    if (cbTwitter.isSelected()) selectedPlatformsArr.add(1); // 1 = post on twitter

                    // checke ob auf Facebook Profil gepostet werden soll:
                    if (cbFacebook.isSelected()) selectedPlatformsArr.add(2); // 2 = post on facebook profile

                    for (int i = 0; i < selectedPlatformsArr.size(); i++) {

                        if (selectedPlatformsArr.get(i) != 0 || selectedPlatformsArr.get(i) != null) { // nichts ausgewählt
                            int platform = selectedPlatformsArr.get(i);                  //platform    // use fbsite String if needed
                            PostEintrag newPostEintrag = new PostEintrag(this.user.getId(), platform, cbFBGruppen.getId(), posttext, mediafile, postTime, 0);  // 0  bedeutet neuer post
                            boolean postInsertOK = PostEintragBean.insertNewPost(newPostEintrag);

                            if (postInsertOK) {
                                lbMessageStatus.setText("Post wurde in DB gespeichert!");
                                taLog.appendText("Post für PlatformID:" + platform + " wurde geplant.\n");
                                selectedFile = null;  // nach dem post, den selected File
                                lbFilename.setText("Keine Bild oder Film Datei ausgewählt.");
                            } else {
                                lbMessageStatus.setText("DB Insert Fehler, Post konnte nicht gespeichert werden!");
                                taLog.appendText("DB Insert Fehler bei Post für PlatformID:" + platform + ".\n");
                            }
                        } else {
                            lbMessageStatus.setText("Es ist keine Social Media Platform ausgewählt!");
                        }
                    }
                } else {
                    lbMessageStatus.setText("Es ist keine Social Media Platform ausgewählt! Min. 1 auswählen!");
                }
            } else {
                lbMessageStatus.setText("Kein Text/Content zum Posten eingegeben oder ausgewählt!");
            }
        } else {
            lbMessageStatus.setText("Datum und Zeit muss in der Zukunft liegen!");
        }

        resetText(lbMessageStatus);
    }

    /**
     * Method to count the Content of the Hashtag TextArea + Message TextArea for the Label lbRestChar, while writing/entering Hashtags
     */
    @FXML
    void countHashtag() {
        countTotalChar();
    }

    /**
     * Method to count the Content of the Message TextArea + Hashtag TextArea for the Label lbRestChar,while writing the post
     */
    @FXML
    void countPost() {
        countTotalChar();
    }

    /**
     * This method counts the chars of the Hashtag TextArea + Message TextArea
     */
    private void countTotalChar() {
        String post = taMessage.getText();
        String tag = taHashtags.getText();
        String tlen = post + tag;

        String msg = (post.length() + tag.length()) + " / 480 Zeichen";
        lbRestChar.setText(msg);

        //The text in post and hashtag must turn Red when the total characters limit exceeds
        if (tlen.length() > 63206) {
            taMessage.setStyle("-fx-text-inner-color: red;");
            taHashtags.setStyle("-fx-text-inner-color: red;");
            //The check box for twitter must be unchecked and disabled when the total characters limit exceeds 280 characters
        } else if (tlen.length() > 480) {
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
     * This method generates and sets a random date in DatePicker and generate and sets a random time in TextField Time
     *
     * @param event fired from btnRandmDateTime button
     */
    @FXML
    void randomDateTime(ActionEvent event) {
        //When clicked ,must generate Random Date and Time
        Instant jetzt = Instant.now();
        Instant einWoche = Instant.now().plus(Duration.ofDays(7));
        Instant randomInstant = zwischen(jetzt, einWoche);
        Date randomDate = Date.from(randomInstant);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String randomTime = timeFormat.format(randomDate);
        dpDate.setValue(randomInstant.atZone(ZoneId.systemDefault()).toLocalDate());
        tfTime.setText(randomTime);
    }

    /**
     * Method to generate Instant of the random date time in next 7 days
     *
     * @param jetzt    Instant current date time.
     * @param einWoche Instant date time after 7days
     * @return Instant of random date time in next 7 days
     */
    private Instant zwischen(Instant jetzt, Instant einWoche) {
        long anfang = jetzt.getEpochSecond();
        long ende = einWoche.getEpochSecond();
        long randomSeconds = ThreadLocalRandom.current().nextLong(anfang, ende);
        return Instant.ofEpochSecond(randomSeconds);
    }

    /**
     * This method saves the Content of the TextArea 'taLog' in a txt File and clears the 'taLog' textarea
     */
    @FXML
    void saveLog() {
        try {
            ObservableList<CharSequence> paragraph = taLog.getParagraphs();
            String dt = new SimpleDateFormat("dd-MM-yyy").format(new Date());
            Iterator<CharSequence> iter = paragraph.iterator();
            File file = new File("SMT_Log_Report_" + dt + ".txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter bf = new BufferedWriter(fr);

            while (iter.hasNext()) {
                CharSequence seq = iter.next();
                bf.append(seq);
                bf.newLine();
            }

            bf.flush();
            bf.close();
            fr.close();
            // after saving to logfile reset the old log in taLog textarea:
            taLog.setText("");
            lbLogSavedFeedback.setText("Log als Datei gespeichert!");
        } catch (IOException e) {
            lbLogSavedFeedback.setText("Log konnte nicht gespeichert werden!");
            e.printStackTrace();
        }
        resetText(lbLogSavedFeedback);
    }

    /**
     * This method save the Facebook Account Details from the tab 'Einstellungen' in the Database and to socialmediaAccount object
     *
     * @param event clickevent
     */
    @FXML
    void saveSettingsFB(ActionEvent event) {
        // Speichert die Facebook Accountdaten des users, abhaengig von seiner uid
        Integer uid = this.user.getId();
        String ai = this.tfFBAppID.getText();
        String as = this.tfFBAppSecret.getText();
        String at = this.tfFBUserAccessToken.getText();
        String ud = this.tfFBPageAccessToken.getText();
        if (uid != null && !ai.trim().equals("") && !as.trim().equals("") && !at.trim().equals("") && !ud.trim().equals("")) {
            if (SocialmediaAccountBean.insertOrUpdateFacebookAccount(uid, ai, as, at, ud)) {  // insertOrUpdate db true
                this.socialmediaAccount.setUid(uid);
                this.socialmediaAccount.setFbAppID(ai);
                this.socialmediaAccount.setFbAppSecret(as);
                this.socialmediaAccount.setFbUserAccessToken(at);
                this.socialmediaAccount.setFbPageAccessToken(ud);
                this.lblSaveFBAccountStatus.setText("Facebook Accountdaten gespeichert/aktualisiert.");
            } else {
                System.out.println("An Error occured while insert or update FBAccountdata into SocialMediaAccounts Table!");
                this.lblSaveFBAccountStatus.setText("Konnte nicht gespeichert werden, Eingaben ueberpruefen!");
            }
        } else {
            this.lblSaveFBAccountStatus.setText("Konnte nicht gespeichert werden, Felder duerfen nicht leer sein!");
        }
        resetText(this.lblSaveFBAccountStatus);
    }

    /**
     * This method inserts or updates the Twitter Account Details from the tab 'Einstellungen' in the Database and to socialmediaAccount object
     *
     * @param event clickevent
     */
    @FXML
    void saveSettingsTW(ActionEvent event) {
        // Speichert die Twitter Accountdaten des users, abhaengig von seiner uid
        Integer uid = this.user.getId();
        String ck = this.ConsumerKey.getText();
        String cs = this.ConsumerSecret.getText();
        String at = this.AccessToken.getText();
        String ats = this.AccessTokenSecret.getText();
        if (uid != null && !ck.trim().equals("") && !cs.trim().equals("") && !at.trim().equals("") && !at.trim().equals("")) {
            //SocialmediaAccountBean socialmediaAccountBean = new SocialmediaAccountBean();  // used static
            if (SocialmediaAccountBean.insertOrUpdateTwitterAccount(uid, ck, cs, at, ats)) {  // insertOrUpdate db true
                this.socialmediaAccount.setUid(uid);
                this.socialmediaAccount.setTwConsumerKey(ck);
                this.socialmediaAccount.setTwConsumerSecret(cs);
                this.socialmediaAccount.setTwAccessToken(at);
                this.socialmediaAccount.setTwAccessTokenSecret(ats);
                this.lblSaveTWAccountStatus.setText("Twitter Accountdaten gespeichert/aktualisiert.");
            } else {
                System.out.println("An Error occured while insert or update TwitterAccountdata into SocialMediaAccounts Table!");
                this.lblSaveTWAccountStatus.setText("Konnte nicht gespeichert werden, Eingaben ueberpruefen!");
            }
        } else {
            this.lblSaveTWAccountStatus.setText("Konnte nicht gespeichert werden, Felder duerfen nicht leer sein!");
        }
        resetText(this.lblSaveTWAccountStatus);
    }

    /**
     * loads the SocialmediaAccount Data of the active user with uid from user object (=UserEintrag object)
     */
    private void loadSocialMediaAccountDataIntoTwitterAndFacebookFields() {

        if (this.user.getId() != null) {

            this.socialmediaAccount = SocialmediaAccountBean.getSocialMediaAccountsByUid(this.user.getId());
            if (this.socialmediaAccount == null) {
                // user has not yet set any Socialmedia Account informations , no row in table
                // only set the available uid from loggedin userobject in new socialmediaAccount Object
                this.socialmediaAccount = new SocialmediaAccount();
                this.socialmediaAccount.setUid(this.user.getId());

            } else {
                // show socialmedia account data  in Einstellungs textfields, so user can update and see what is actually set
                String fbAppID = this.socialmediaAccount.getFbAppID();
                String fbAppSecret = this.socialmediaAccount.getFbAppSecret();
                String fFBUserAccessToken = this.socialmediaAccount.getFbUserAccessToken();
                String fFBPageAccessToken = this.socialmediaAccount.getFbPageAccessToken();


                if (fbAppID != null && fbAppSecret != null && fFBUserAccessToken != null && fFBPageAccessToken != null) {
                    this.tfFBAppID.setText(fbAppID);
                    this.tfFBAppSecret.setText(fbAppSecret);
                    this.tfFBUserAccessToken.setText(fFBUserAccessToken);
                    this.tfFBPageAccessToken.setText(fFBPageAccessToken);
                }

                String twck = this.socialmediaAccount.getTwConsumerKey();
                String twcs = this.socialmediaAccount.getTwConsumerSecret();
                String twat = this.socialmediaAccount.getTwAccessToken();
                String twats = this.socialmediaAccount.getTwAccessTokenSecret();

                if (twck != null && twcs != null && twat != null && twats != null) {
                    this.ConsumerKey.setText(twck);
                    this.ConsumerSecret.setText(twcs);
                    this.AccessToken.setText(twat);
                    this.AccessTokenSecret.setText(twats);
                }
            }
        } else {
            System.out.println("Es gibt keine User ID, die geladen werden kann!");
        }
    }

    /**
     * This method reset the content of a Label Component after 2,5 seconds
     *
     * @param label Label in which the text should be reset
     */
    void resetText(Label label) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                label.setText("");
            }
        });
        new Thread(sleeper).start();
    }

    /**
     * This method initialalizes the processes like the SocialMediaWorkerTimer etc.
     */
    @FXML
    void initialize() {
        /* -------- Automate Posts Section ------------------------------- */
        this.tbActivate.setSelected(false);  // on start set automated posting to false.
        this.tbActivate.setStyle("-fx-background-color:lightgrey");
        this.socialMediaWorker = new SocialMediaWorker();
        this.socialMediaWorkerTimer = new Timeline(new KeyFrame(javafx.util.Duration.seconds(workerWaitSeconds), this.socialMediaWorker));
        this.socialMediaWorkerTimer.setCycleCount(Timeline.INDEFINITE);

        /* --------- Twitter and Facebook Account Einstellungen ----------- */
        this.socialmediaAccount = new SocialmediaAccount();

        // if we select the Einstellungen tab, it loads newest SocialMediaAccounts Data from DB
        tabEinstellungen.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (tabEinstellungen.isSelected()) {
                    loadSocialMediaAccountDataIntoTwitterAndFacebookFields();
                }
            }
        });


        /* --------- Tab AllePosts Section loaders ----------- */
        tabAllePosts.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (tabAllePosts.isSelected()) {
                    getPostTable();
                }
            }
        });

        /* --------- Tab Hashtags Section loaders ----------- */
        tabHashtags.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (tabHashtags.isSelected()) {
                    getHashTable();
                }
            }
        });

        /* --------- Tab Neue Post Section loaders ----------- */
        tabNeuerPost.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (tabNeuerPost.isSelected()) {
                    checkIfSocMediaAccountsAvailableAndEnableNewPostButton();
                }
            }
        });

        // wait 3 sec till uid etc. loaded and set by ContollerLogin, put everything in here, if it needs to start short time after fxinits
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(3));
        pause.setOnFinished(event -> {
                    checkIfSocMediaAccountsAvailableAndEnableNewPostButton();
                }
        );
        pause.play();

        getHashTable();
    }

    /**
     * This Method loads SocialMediaAccount informations, and sets the New Post Button to enabled if user has
     * Accounts in the Einstellungen and DB
     */
    public void checkIfSocMediaAccountsAvailableAndEnableNewPostButton() {
        loadSocialMediaAccountDataIntoTwitterAndFacebookFields();

        if (tfFBAppID.getText().isEmpty() && ConsumerKey.getText().isEmpty()) { // socialmediaAccount
            btnSavePost.setDisable(true); // disable the save post button, because user has not set TW or FB account.
            tbActivate.setDisable(true);  // disable the automatic post button, because user has not set TW or FB account.
            lbMessageStatus.setText("Bitte zuerst in den Einstellungen Twitter oder Facebook Informationen eingeben!");
        } else {
            if(tfFBAppID.getText() != "" && tfFBAppID.getText() != "" && tfFBAppSecret.getText() != "" && tfFBUserAccessToken.getText() != "") {
                PostToFacebook userPost = new PostToFacebook(tfFBAppID.getText(), tfFBAppSecret.getText(), tfFBUserAccessToken.getText(), "user");
                HashMap<String, String> pageResult = userPost.getUserAdminPages();
                for (String key : pageResult.keySet()) {
                    this.cbFBGruppen.getItems().add(key);
                }
                HashMap<String, String> groupResult = userPost.getUserJoinedGroups();
                for (String key : groupResult.keySet()) {
                    this.lvFBGruppen.getItems().add(key);
                }
            }
            btnSavePost.setDisable(false);
            tbActivate.setDisable(false);
            lbMessageStatus.setText("");
        }
    }

    /**
     * This method load the saved Hashtag Lists from the Database in the Hashtag Tableview
     */
    void getHashTable() {
        ObservableList<HashtagsEintrag> entries = FXCollections.observableArrayList(HashtagsBean.getThemes());
        this.tcTheme.setCellValueFactory(new PropertyValueFactory<HashtagsEintrag, String>("theme"));
        this.tcHashtags.setCellValueFactory(new PropertyValueFactory<HashtagsEintrag, String>("hashtags"));

        this.tvHashtags.setItems(entries);
    }

    /**
     * This method load the saved post entries from the Database in the Post Tableview
     */
    void getPostTable() {
        ObservableList<PostEintrag> entries = FXCollections.observableArrayList(PostEintragBean.selectAllPostsWithUid(this.user.getId()));
        this.tcPid.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("pid"));
        this.tcText.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("posttext"));
        this.tcMedia.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("dateiname"));  // letzter teil nach mediafile link, der dateiname ist.
        this.tcDate.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("posttime"));
        this.tcPlatform.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("platform"));
        this.tcReaction.setCellValueFactory(new PropertyValueFactory<PostEintrag, String>("poststatus"));
        this.tvPosts.setItems(entries);
    }

    /**
     * This method delete a Post entry in the database
     */
    @FXML
    void deletePost() {

        PostEintrag selectedEntry = tvPosts.getSelectionModel().getSelectedItem();

        PostEintragBean.delete(selectedEntry);
        System.out.println("Post entfernt");

        getPostTable();

    }

    /**
     * This method update a Hashtag list entry in the database
     */
    @FXML
    void updateHashEntry() {
        System.out.println("Liste aktualisiert");

        HashtagsEintrag oldEntry = tvHashtags.getSelectionModel().getSelectedItem();

        try {
            Stage MainStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("fxUpdateHashtagListe.fxml").openStream());
            root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            //Get controller of fxAddHashtagListe
            ControllerUpdateHashtagListe updateList = loader.getController();

            updateList.setOldEntry(oldEntry);

            Scene scene = new Scene(root);

            MainStage.setTitle("SMT - Social Media Tool: Hashtagliste Hinzufügen");
            MainStage.setScene(scene);
            MainStage.showAndWait();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
        getHashTable();

    }

    /**
     * This method delete the selected Hashtag list in the database
     */
    @FXML
    void deleteHashList() {

        HashtagsEintrag selectedEntry = tvHashtags.getSelectionModel().getSelectedItem();

        HashtagsBean.delete(selectedEntry);
        System.out.println("Liste entfernt");

        getHashTable();
    }

    /**
     * This method copies the text from an selected post entry and pastes it into the "New Post" tab textarea
     *
     * @param actionEvent with the click of the menuitem miCopyPost
     */
    public void copyPost(ActionEvent actionEvent) {
        PostEintrag selectedEntry = tvPosts.getSelectionModel().getSelectedItem();

        taMessage.setText(selectedEntry.getPosttext());
        countPost();
    }

}
