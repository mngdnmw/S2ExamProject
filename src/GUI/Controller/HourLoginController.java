package GUI.Controller;

import BE.EnumCache.*;
import BE.Guild;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import sun.plugin2.jvm.RemoteJVMLauncher.CallBack;

public class HourLoginController implements Initializable
  {

    @FXML
    private JFXButton btnLogHours;
    @FXML
    private Label lblUsernameTag;
    @FXML
    private JFXTextField txtUser;
    @FXML
    private Label lblHourTag;
    @FXML
    private JFXTextField txtHours;
    @FXML
    private Label lblHourTagTwo;
    @FXML
    private Label lblGuildTag;
    @FXML
    private JFXComboBox<Guild> cmbGuildChooser;
    @FXML
    private Label lblGuildTagTwo;
    @FXML
    private JFXButton btnLanguage;
    @FXML
    private JFXButton btnSeeInfo;
    @FXML
    private AnchorPane root;

    private String strLogThanks = "Thanks!";
    private String strContribution = "Your hours have been logged. Thank you!";
    private String strLogin = "Log In";
    private String strCancel = "Cancel";
    private String strLanguage = "English";

    private Image iconDK, iconENG;
    private ImageView imgViewLngBut = new ImageView();
    //Models used by this Controller
    private final static ModelFacade MOD_FACADE = new ModelFacade();

    @Override
    public void initialize(URL url, ResourceBundle rb)
      {
        addListener();
        preloadImages();
        imgViewLngBut.setImage(iconENG);
        btnLanguage.setGraphic(imgViewLngBut);
        cmbGuildChooser.setItems(FXCollections.observableArrayList(MOD_FACADE.getAllGuilds()));
      }

    @FXML
    private void LogHoursAction(ActionEvent event)
      {
        lockButtons();
        if (!txtUser.getText().isEmpty() && !txtHours.getText().isEmpty() && !cmbGuildChooser.getSelectionModel().isEmpty())
          {
            MOD_FACADE.logHours(
                    txtUser.getText(), Integer.parseInt(txtHours.getText()),
                    cmbGuildChooser.getSelectionModel().getSelectedItem().getId()
            );
            contributionPopup(strContribution);
          }
        else
          {
            contributionPopup("Please input information in all fields");
          }
      }

    @FXML
    private void ChangeLanguageAction(ActionEvent event)
      {
        lockButtons();
        languagePopup();

      }

    @FXML
    private void LogInAction(ActionEvent event)
      {
        lockButtons();
        loginPopup();

      }

    public void addListener()
      {
        txtHours.textProperty().addListener(new ChangeListener<String>()
          {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
              {
                try
                  {
                    if (newValue.matches("\\d*") && newValue.length() < 3)
                      {
                        int value = Integer.parseInt(newValue);
                      }
                    else
                      {
                        txtHours.setText(oldValue);
                      }
                  }
                catch (NumberFormatException ex)
                  {
                    //do nothing
                  }
              }

          });
      }

    /**
     * pops up a bordered VBox that disappear after a short moment.
     */
    public void contributionPopup(String str)
      {
        int time = 3000;
        JFXSnackbar snackbar = new JFXSnackbar(root);
        snackbar.show(str, time);
        PauseTransition pause = new PauseTransition(Duration.millis(time));
        pause.setOnFinished(
                e -> unlockButtons()
        );
        pause.play();
//        //popup window that we are making
//        VBox popup = new VBox();
//        popup.setSpacing(5);
//        popup.setPadding(new Insets(20));
//        popup.setAlignment(Pos.CENTER);
//        popup.getStyleClass().add("popup");
//        popup.setStyle("-fx-background-color: #00c4ad;");
//
//        //CSS to be added to both labels
//        String styleText = "-fx-font:italic bold 20px/30px System;"
//                + "-fx-text-fill: #FFFFFF;" + "";
//
//        //First Label
//        Label lblThanks = new Label();
//        lblThanks.setStyle(styleText);
//        lblThanks.setText(strLogThanks);
//
//        //Next Label
//        Label lblContribution = new Label();
//        lblContribution.setStyle(styleText);
//        lblContribution.setText(strContribution);
//        //Imageview that contains a star
//        ImageView imgStar = new ImageView();
//        Image img = new Image("/GUI/Images/star.png");
//        imgStar.setImage(img);
//        imgStar.setFitHeight(imgStar.getImage().getHeight() / 3);
//        imgStar.setPreserveRatio(true);
//        //Add all nodes to the popup window in order
//        popup.getChildren().add(lblThanks);
//        popup.getChildren().add(lblContribution);
//        popup.getChildren().add(imgStar);
//
//        root.getChildren().add(popup);
//        popup.setTranslateY((root.getHeight() / 3));
//        popup.setTranslateX(root.getWidth() / 4.2);
//        MOD_FACADE.fadeInTransition(Duration.millis(500), popup);
//
//        PauseTransition pause = new PauseTransition(Duration.millis(1500));
//        pause.setOnFinished(
//                e -> MOD_FACADE.fadeOutTransition(Duration.millis(500), popup).setOnFinished(ev -> removePopup(popup))
//        );
//        pause.play();
      }

    /**
     * Pops up a login view that plays a short fade in transition. Contains
     * event for the buttons that are within.
     */
    public void loginPopup()
      {
        //popup for the login
        VBox popup = new VBox();
        //Styles that will be used
        popup.setSpacing(20);
        popup.setPadding(new Insets(20, 20, 20, 20));
        popup.getStyleClass().add("popup");
        popup.setStyle("-fx-background-color: #BBBBBB;");

        //CSS to be added to both labels
        String styleText = "-fx-font:italic bold 20px/30px System;"
                + "-fx-text-fill: #FFFFFF;";
        String styleTextField = "-fx-font:Bold 18px/30px System;"
                + "-fx-text-fill: #FFFFFF;";
        String styleButtons = "-fx-background-color: #00e2c7;";
        //Username Area made here a label and a textfield
        HBox usernameArea = new HBox();
        usernameArea.setSpacing(10);
        usernameArea.alignmentProperty().set(Pos.BOTTOM_RIGHT);
        Label lblUsername = new Label("Username:");
        lblUsername.setStyle(styleText);

        JFXTextField txtUsername = new JFXTextField();

        txtUsername.setStyle(styleTextField);
        //txtUsername.promptTextProperty().set("Username");

        if (!txtUser.getText().isEmpty())
          {
            txtUsername.setText(txtUser.getText());
          }
        txtUsername.widthProperty().add(50);
        txtUsername.alignmentProperty().set(Pos.CENTER);
        usernameArea.getChildren().addAll(lblUsername, txtUsername);

        //password Area made here a label and a passwordfield
        HBox passwordArea = new HBox();
        passwordArea.setSpacing(10);
        passwordArea.alignmentProperty().set(Pos.BOTTOM_RIGHT);

        Label lblPassword = new Label("Password:");
        lblPassword.setStyle(styleText);
        JFXPasswordField txtPassword = new JFXPasswordField();
        //txtPassword.promptTextProperty().set("Password");
        txtPassword.alignmentProperty().set(Pos.CENTER);
        txtPassword.widthProperty().add(50);
        txtPassword.setStyle(styleTextField);

        passwordArea.getChildren().addAll(lblPassword, txtPassword);
        //A checkbox with the word Remember Me!
        JFXCheckBox bxRemPassword = new JFXCheckBox("Remember Me");
        bxRemPassword.setStyle(styleText);
        //Two buttons to confirm wether or not to log in and a label to say if it is wrong password

        HBox confirmArea = new HBox();
        confirmArea.alignmentProperty().set(Pos.CENTER_RIGHT);
        confirmArea.setSpacing(10);
        Label lblWrongPw = new Label();
        lblWrongPw.setStyle("-fx-font:Bold 15px/30px System;"
                + "-fx-text-fill: #FFFFFF;");
        JFXButton btnLogin = new JFXButton(strLogin);
        btnLogin.setStyle(styleText + styleButtons);
        JFXButton btnCancel = new JFXButton(strCancel);
        btnCancel.setStyle(styleText + styleButtons);
        confirmArea.getChildren().addAll(lblWrongPw, btnLogin, btnCancel);

        popup.getChildren().addAll(usernameArea, passwordArea, bxRemPassword, confirmArea);
        root.getChildren().add(popup);
        popup.setTranslateY((root.getHeight() / 3));
        popup.setTranslateX(root.getWidth() / 4.2);
        MOD_FACADE.fadeInTransition(Duration.millis(500), popup);
        btnCancel.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override
            public void handle(ActionEvent e)
              {
                MOD_FACADE.fadeOutTransition(Duration.millis(500), popup)
                        .setOnFinished(
                                ev -> removePopup(popup)
                        );
              }
          });
        btnLogin.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override
            public void handle(ActionEvent e)
              {
                MOD_FACADE.getUserFromLogin(txtUsername.getText());
                if (MOD_FACADE.getCurrentUser() != null)
                  {
                  }
                else
                  {
                    lblWrongPw.setText("Wrong Password");
                  }
              }

          });
      }

    /**
     * Pops up with a language selector
     */
    public void languagePopup()
      {
        //contains buttons = languages
        int size = 75;
        HBox popup = new HBox();
        popup.getStyleClass().add("popup");
        popup.setStyle("-fx-background-color: #00c4ad;");
        popup.setPadding(new Insets(20));
        popup.setSpacing(20);
        JFXButton btnDanish = new JFXButton();
        JFXButton btnEnglish = new JFXButton();

        btnDanish.getStyleClass().add("JFXRoundedButton");
        btnDanish.setStyle("-fx-background-color:#FFFFFF");
        btnDanish.setMinSize(size, size);
        btnDanish.setPrefSize(size, size);
        btnDanish.setMaxSize(size, size);
        btnDanish.setGraphic(new ImageView(iconDK));
        btnEnglish.getStyleClass().add("JFXRoundedButton");
        btnEnglish.setStyle(btnDanish.getStyle());
        btnEnglish.setMinSize(size, size);
        btnEnglish.setPrefSize(size, size);
        btnEnglish.setMaxSize(size, size);
        btnEnglish.setGraphic(new ImageView(iconENG));
        root.getChildren().add(popup);
        popup.getChildren().addAll(btnDanish, btnEnglish);
        popup.setTranslateX(0);
        MOD_FACADE.fadeInTransition(Duration.millis(500), popup);
        popup.setTranslateY((root.getHeight() / 1.5));
        popup.setTranslateX(root.getWidth() / 6);

        EventHandler changeLanguageHandler = new EventHandler<ActionEvent>()
          {
            @Override
            public void handle(ActionEvent event)
              {
                if (event.getSource().equals(btnDanish))
                  {
                    changeLanguage("Dansk");
                  }
                else if (event.getSource().equals(btnEnglish))
                  {
                    changeLanguage("English");
                  }
                setTextAll();
                MOD_FACADE.fadeOutTransition(Duration.millis(500), popup)
                        .setOnFinished(
                                ev -> removePopup(popup)
                        );
              }
          };

        btnDanish.setOnAction(changeLanguageHandler);
        btnEnglish.setOnAction(changeLanguageHandler);

      }

    public void unlockButtons()
      {
        Boolean dis = false;
        btnLanguage.setDisable(dis);
        btnSeeInfo.setDisable(dis);
        btnLogHours.setDisable(dis);
        txtUser.setDisable(dis);
        txtHours.setDisable(dis);
        cmbGuildChooser.setDisable(dis);
      }

    public void lockButtons()
      {
        Boolean dis = true;
        btnLanguage.setDisable(dis);
        btnSeeInfo.setDisable(dis);
        btnLogHours.setDisable(dis);
        txtUser.setDisable(dis);
        txtHours.setDisable(dis);
        cmbGuildChooser.setDisable(dis);
      }

    public void removePopup(Node popup)
      {
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(
                e -> unlockButtons()
        );
        pause.play();
        root.getChildren().removeAll(popup);

      }

    public void changeLanguage(String str)
      {
        if (!str.equals(btnLanguage.getText()))
          {
            strLanguage = str;
            btnLanguage.setText(strLanguage);
            if (strLanguage.equals("Dansk"))
              {
                imgViewLngBut.setImage(iconDK);
                MOD_FACADE.setLang(Lang.DAN);
              }
            else if (strLanguage.equals("English"))
              {
                imgViewLngBut.setImage(iconENG);
                MOD_FACADE.setLang(Lang.ENG);
              }
          }
      }

    public void preloadImages()
      {
        iconDK = new Image(getClass().getResourceAsStream("/GUI/Images/danish.png"));
        iconENG = new Image(getClass().getResourceAsStream("/GUI/Images/english.png"));
      }

    private void setTextAll()
      {
        lblUsernameTag.setText(MOD_FACADE.getLang("USERNAME_TAG"));
        txtUser.setPromptText(MOD_FACADE.getLang("TXT_USERNAME_PROMPT"));
        lblHourTag.setText(MOD_FACADE.getLang("HOUR_TAG"));
        txtHours.setPromptText(MOD_FACADE.getLang("TXT_HOURS_PROMPT"));
        lblHourTagTwo.setText(MOD_FACADE.getLang("HOUR_TAG_TWO"));
        lblGuildTag.setText(MOD_FACADE.getLang("GUILD_TAG"));
        cmbGuildChooser.setPromptText(MOD_FACADE.getLang("CMB_GUILD_CHOOSER_PROMPT"));
        lblGuildTagTwo.setText(MOD_FACADE.getLang("GUILD_TAG_TWO"));
        btnLogHours.setText(MOD_FACADE.getLang("BTN_LOG_HOURS"));
        btnSeeInfo.setText(MOD_FACADE.getLang("BTN_SEE_INFO"));

      }

  }
