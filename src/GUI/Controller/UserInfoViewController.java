package GUI.Controller;

import BE.Day;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.skins.JFXDatePickerSkin;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class UserInfoViewController implements Initializable
  {
    
    @FXML
    private Label lblName;
    @FXML
    private Label lblPh;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblResidence;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private TextArea textAreaGuilds;
    @FXML
    private JFXButton btnUpdatePhoto;
    @FXML
    private HBox hBoxCalAll;
    @FXML
    private HBox hBoxCalMth;
    @FXML
    private HBox hBoxCalDay;
    @FXML
    private Label lblHrsAll;
    @FXML
    private AnchorPane anchorGraph;
    @FXML
    private GridPane gridEdit;
    @FXML
    private JFXButton btnEditSave;
    
    @FXML
    private AnchorPane root;
    
    @FXML
    private Tab tabAll;
    @FXML
    private Tab tabMonth;
    @FXML
    private Tab tabDay;
    @FXML
    private TabPane tabPaneOverview;
    @FXML
    private Tab tabGraphs;
    @FXML
    private HBox hBoxInvisBtn;
    
    @FXML
    private JFXTreeTableView<Day> treeViewAllHours;
    @FXML
    private JFXTextField txtFSearchDate;
    @FXML
    private Label lblHrsAll2;
    @FXML
    private Label lblHrsAll3;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnChangePassword;
    @FXML
    private StackPane stckPanePasswordChanger;
    @FXML
    private VBox loginWindow;
    @FXML
    private Label lblUsername;
    @FXML
    private JFXPasswordField txtOPassword;
    @FXML
    private JFXPasswordField txtNPassword;
    @FXML
    private JFXPasswordField txtNPasswordTwo;
    
    TextField txtName;
    TextField txtPh;
    TextField txtEmail;
    TextField txtResidence;
    TextField txtResidence2;

    User currentUser;
    
    boolean editing = false;
    boolean isIncorrect = false;
    
    private static Region POPUP_CAL;
    
    private final String STYLESHEET = "GUI/View/UserInfoCSS.css";
    
    private final static ModelFacade MOD_FACADE = ModelFacade.getModelFacade();
    
    private int GUIView;
    
    JFXTreeTableColumn<Day, String> dateCol = new JFXTreeTableColumn<>();
    JFXTreeTableColumn<Day, Integer> hoursCol = new JFXTreeTableColumn<>();
    JFXTreeTableColumn<Day, String> guildCol = new JFXTreeTableColumn<>();
    @FXML
    JFXButton btnCancel = new JFXButton();
    JFXButton higherClearanceBtn = new JFXButton();
    
    boolean finishedService;
    private final Service serviceAllVolunteers = new Service()
      {
        @Override
        protected Task createTask()
          {
            return new Task()
              {
                @Override
                protected Object call() throws Exception
                  {
                    finishedService = false;
                    MOD_FACADE.setAllVolunteersIntoArray();
                    finishedService = true;
                    return null;
                  }
              };
          }
      };
    @FXML
    private Label lblHrsAllText;
    @FXML
    private Label lblHrsAllText2;
    @FXML
    private JFXButton btnEditOnTabMonth;
    @FXML
    private Label lblHrsAllText3;
    @FXML
    private JFXButton btnEditOnTabDay;
    @FXML
    private Label lblGuilds;
    @FXML
    private JFXTextField txtFSearchDate2;
    @FXML
    private JFXTextField txtFSearchDate3;
    @FXML
    private Label lblResidence2;

    /**
     * Initializes the controller class.
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb)
      {
        createEditFields();
        setCurrentUser(MOD_FACADE.getCurrentUser());
        setUserInfo();
        showConstantCalendar();
        setUserImage();
        checkTypeOfUser();
        showTreeTable();
        if (currentUser.getType() >= 1)
          {
            serviceAllVolunteers.start();
          }
        
        setTextAll();
      }
    
    public void setCurrentUser(User currentUser)
      {
        this.currentUser = currentUser;
      }

    /**
     * Makes the calendar in the Month tab
     */
    private void showConstantCalendar()
      {
        JFXDatePicker calendar = new JFXDatePicker();
        
        JFXDatePickerSkin skin = new JFXDatePickerSkin(calendar);
        POPUP_CAL = (Region) skin.getPopupContent();
        
        POPUP_CAL.getStylesheets().add(STYLESHEET);
        hBoxCalMth.setPadding(new Insets(0, 10, 0, 0));
        hBoxCalMth.getChildren().add(POPUP_CAL);
        
      }

    /**
     * Initialises the tree table containing information about the User
     */
    private void showTreeTable()
      {
        //Need to do some threading for this method

        //Date column set up
        dateCol.prefWidthProperty().bind(treeViewAllHours.widthProperty().divide(3));
        dateCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Day, String>, ObservableValue<String>>()
          {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Day, String> param)
              {
                return param.getValue().getValue().dateProperty();
              }
          });

        //Hours column set up
        hoursCol.prefWidthProperty().bind(treeViewAllHours.widthProperty().divide(3));
        hoursCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Day, Integer> param) -> param.getValue().getValue().hourProperty().asObject());

        //Guild column set up
        guildCol.prefWidthProperty().bind(treeViewAllHours.widthProperty().divide(3));
        guildCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Day, String> param) -> param.getValue().getValue().guildProperty());
        
        treeViewAllHours.setPlaceholder(new Label("Nothing found"));
        
        ObservableList<Day> daysWorked = FXCollections.observableArrayList(MOD_FACADE.getWorkedDays(currentUser));
        
        final TreeItem<Day> rootOfTree = new RecursiveTreeItem<>(daysWorked, RecursiveTreeObject::getChildren);
        
        dateCol.getStyleClass().add("col");
        hoursCol.getStyleClass().add("col");
        guildCol.getStyleClass().add("col");
        
        treeViewAllHours.getColumns().setAll(dateCol, hoursCol, guildCol);
        treeViewAllHours.setRoot(rootOfTree);
        treeViewAllHours.setShowRoot(false);
        
        txtFSearchDate.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                -> 
          {
            treeViewAllHours.setPredicate((TreeItem<Day> day)
                    -> 
              {
                String regex = "[^a-zA-Z0-9\\s]";
                Boolean search
                        = day.getValue().dateProperty().getValue().replaceAll(regex, "")
                        .contains(newValue.replaceAll(regex, ""))
                        || day.getValue().guildProperty().getValue().toLowerCase().replaceAll(regex, "").
                        contains(newValue.toLowerCase().replaceAll(regex, ""));
                
                return search;
              });
          });
      }

    /**
     * Check what type of User this is, if it's a Manager or Administrator, a
     * button will be created.
     *
     * @param 1 = Manager, 2 = Admin
     */
    private void checkTypeOfUser()
      {
        switch (currentUser.getType())
          {
            case 0:
                break;
            case 1:
                createHighClearanceButton(1);
                break;
            
            case 2:
                createHighClearanceButton(2);
                break;
          }
      }

    /**
     * Displays additional button for Manager and Administrators.
     *
     * @param type 1 = Manager, 2 = Admin
     */
    private void createHighClearanceButton(int type)
      {
        //int GUIView;
        
        higherClearanceBtn.setId("higherClearanceBtn");
        higherClearanceBtn.toFront();
        higherClearanceBtn.setVisible(true);
        
        hBoxInvisBtn.setAlignment(Pos.CENTER);
        hBoxInvisBtn.getChildren().add(higherClearanceBtn);
        
        higherClearanceBtn.getStylesheets().add(STYLESHEET);
        
        if (type == 1)
          {
            higherClearanceBtn.setText(MOD_FACADE.getLang("BTN_HIGHER_CLEARANCE_1"));
            GUIView = 1;
            
          }
        else
          {
            higherClearanceBtn.setText(MOD_FACADE.getLang("BTN_HIGHER_CLEARANCE_2"));
            GUIView = 2;
            // GUIView =4; add when admin view has been made - for the moment it will go to managereditview
          }
        
        higherClearanceBtn.setOnAction(new EventHandler<ActionEvent>()
          {
            @Override
            public void handle(ActionEvent event)
              {
                if (finishedService)
                  {
                    MOD_FACADE.changeView(GUIView);
                  }
                else
                  {
                    serviceAllVolunteers.setOnSucceeded(e
                            -> 
                      {
                        MOD_FACADE.changeView(GUIView);
                        root.getChildren().remove(MOD_FACADE.getLoadingScreen());
                      });
                    
                    root.getChildren().add(MOD_FACADE.getLoadingScreen());
                    root.setTopAnchor(MOD_FACADE.getLoadingScreen(), 0.0);
                    
                    root.setBottomAnchor(MOD_FACADE.getLoadingScreen(), 0.0);
                    root.setLeftAnchor(MOD_FACADE.getLoadingScreen(), 0.0);
                    root.setRightAnchor(MOD_FACADE.getLoadingScreen(), 0.0);
                    
                  }
              }
          });
        
      }
    
    private void setUserInfo()
      {
        lblName.setText(currentUser.getName());
        lblPh.setText(String.valueOf(currentUser.getPhone()));
        lblEmail.setText(currentUser.getEmail());
        lblResidence.setText(currentUser.getResidence());
        lblResidence2.setText(currentUser.getResidence2());
    }

    //Need to finish
    @FXML
    private void handleClickTab(MouseEvent event)
      {
        tabPaneOverview.getSelectionModel().getSelectedIndex();
      }
    
    @FXML
    private void pressedEditSaveButton(ActionEvent event)
      {
        if (!editing)
          {
            editInfo();
            editing = true;
            btnEditSave.setText(MOD_FACADE.getLang("BTN_SAVE"));
            checkTextFields();
            addCancelButton();
          }
        else
          {
            if (isIncorrect && btnEditSave.isDisabled())
              {
                JFXSnackbar b = new JFXSnackbar(root);
                b.show("Please enter valid information in the fields!", 2000);
                return;
              }
            saveInfo(currentUser);
            editing = false;
            btnEditSave.setText(MOD_FACADE.getLang("BTN_EDIT"));
            checkTextFields();
            removeCancelButton();
          }
      }
    
    private void createEditFields()
      {
        txtName = new JFXTextField();
        txtPh = new JFXTextField();
        txtEmail = new JFXTextField();
        txtResidence = new JFXTextField();

        txtResidence2 = new JFXTextField();

        txtPh.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
              {
                checkTextFields();
              }
            
          });
        
        txtName.setVisible(false);
        txtPh.setVisible(false);
        txtEmail.setVisible(false);
        txtResidence.setVisible(false);
        txtResidence2.setVisible(false);

        gridEdit.add(txtName, 1, 0);
        gridEdit.add(txtPh, 1, 1);
        gridEdit.add(txtEmail, 1, 2);
        gridEdit.add(txtResidence, 1, 3);
        gridEdit.add(txtResidence2, 1,4);
    }

    private void editInfo() {
        txtName.setText(lblName.getText());
        
        txtPh.setText(lblPh.getText());
        
        txtEmail.setText(lblEmail.getText());
        
        txtResidence.setText(lblResidence.getText());
        
        lblName.setVisible(false);
        lblPh.setVisible(false);
        lblEmail.setVisible(false);
        lblResidence.setVisible(false);
        lblResidence2.setVisible(false);

        txtName.setVisible(true);
        txtPh.setVisible(true);
        txtEmail.setVisible(true);
        txtResidence.setVisible(true);
      }
    
  

    private void saveInfo(User user) {
        MOD_FACADE.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPh.getText()), user.getNote(), txtResidence.getText(), txtResidence2.getText()); //do things in db

        currentUser = MOD_FACADE.getUserInfo(user.getId());
        txtName.setVisible(false);
        txtPh.setVisible(false);
        txtEmail.setVisible(false);
        txtResidence.setVisible(false);
        txtResidence2.setVisible(false);
        lblName.setVisible(true);
        lblPh.setVisible(true);
        lblEmail.setVisible(true);
        lblResidence.setVisible(true);
        lblResidence2.setVisible(true);
        setUserInfo(); //update labels
      }
    
    private void checkTextFields()
      {
        boolean success = false;
        try
          {
            Integer.parseInt(txtPh.getText());
            success = true;
          }
        catch (NumberFormatException e)
          {
            success = false;
            txtPh.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
          }
        if (success)
          {
            btnEditSave.setDisable(false);
            txtPh.setStyle("");
            isIncorrect = false;
          }
        else
          {
            txtPh.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
            isIncorrect = true;
          }
      }
    
    @FXML
    private void pressedChangeImage(ActionEvent event)
      {
        FileChooser c = new FileChooser();
        c.setTitle("Select a new image");
        String[] extensions
                =
                  {
                    "jpg", "jpeg", "png", "gif"
                  };
        c.setSelectedExtensionFilter(new ExtensionFilter("Image files only", extensions));
        File newImg = c.showOpenDialog(btnUpdatePhoto.getScene().getWindow());
        
        if (newImg != null)
          {
            try
              {
                MOD_FACADE.updateUserImage(currentUser, newImg);
              }
            catch (FileNotFoundException e)
              {
                System.out.println(e);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Selected image is not found");
                a.setContentText("File not found!");
              }
          }
        setUserImage();
      }
    
    public void setUserImage()
      {
        if (MOD_FACADE.getUserImage(currentUser) != null)
          {
            imgVwProfilePic.setImage(new Image(MOD_FACADE.getUserImage(currentUser)));
          }
      }
    
    private void addCancelButton()
      {
        int btnSavePosCol = GridPane.getColumnIndex(btnEditSave); //saving position
        int btnSavePosRow = GridPane.getRowIndex(btnEditSave);
        btnEditSave.setStyle("-fx-background-color: #61B329;");
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) - 1); //moving save button one up

        btnCancel.setText("Cancel"); //preparing cancel button
        btnCancel.setButtonType(JFXButton.ButtonType.RAISED);
        btnCancel.setStyle("-fx-background-color: #ff0000;");
        btnCancel.setTextFill(Color.WHITE);
        btnCancel.setPadding(btnEditSave.getPadding());
        gridEdit.add(btnCancel, btnSavePosCol, btnSavePosRow); //adding to the old position of save btn
        btnCancel.setOnAction(new EventHandler<ActionEvent>()
          { //setting onAction, nothing changed, just show old labels again
            @Override
            public void handle(ActionEvent event)
              {
                txtName.setVisible(false);
                txtPh.setVisible(false);
                txtEmail.setVisible(false);
                txtResidence.setVisible(false);
                txtResidence2.setVisible(false);
                lblName.setVisible(true);
                lblPh.setVisible(true);
                lblEmail.setVisible(true);
                lblResidence.setVisible(true);
                lblResidence2.setVisible(true);

                removeCancelButton(); //if cancel button clicked, it will disappear
                editing = false;
                btnEditSave.setText("Edit");
                btnEditSave.setStyle("-fx-background-color:#00c4ad;");
              }
          });
      }
    
    private void removeCancelButton()
      {
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) + 1); //moving save button one down
        gridEdit.getChildren().remove(btnCancel); //deleting cancel button from gridpane
        if (btnEditSave.isDisabled())
          {
            btnEditSave.setDisable(false);
          }
      }
    
    private void setTextAll()
      {
        btnUpdatePhoto.setText(MOD_FACADE.getLang("BTN_UPDATEPHOTO"));
        btnChangePassword.setText(MOD_FACADE.getLang("BTN_CHANGEPASS"));
        btnEditSave.setText(MOD_FACADE.getLang("BTN_EDIT"));
        btnLogout.setText(MOD_FACADE.getLang("BTN_LOGOUT"));
        btnEditOnTabDay.setText(MOD_FACADE.getLang("BTN_EDIT"));
        btnEditOnTabMonth.setText(MOD_FACADE.getLang("BTN_EDIT"));
        btnCancel.setText(MOD_FACADE.getLang("BTN_CANCEL"));
        tabAll.setText(MOD_FACADE.getLang("TAB_ALL"));
        tabDay.setText(MOD_FACADE.getLang("TAB_DAY"));
        tabMonth.setText(MOD_FACADE.getLang("TAB_MONTH"));
        tabGraphs.setText(MOD_FACADE.getLang("TAB_GRAPHS"));
        
        dateCol.setText(MOD_FACADE.getLang("COL_DATE"));
        hoursCol.setText(MOD_FACADE.getLang("COL_HOURS"));
        guildCol.setText(MOD_FACADE.getLang("COL_GUILD"));
        //txtFSearchDate.setText(MOD_FACADE.getLang("PROMPT_SEARCH_DATE"));
        txtFSearchDate.setPromptText(MOD_FACADE.getLang("PROMPT_SEARCH"));
        //txtFSearchDate2.setText(MOD_FACADE.getLang("PROMPT_SEARCH_DATE"));
        //txtFSearchDate3.setText(MOD_FACADE.getLang("PROMPT_SEARCH_DATE"));
        lblHrsAll.setText(MOD_FACADE.getLang("LBL_HRS_ALL_TEXT"));
        lblHrsAll2.setText(MOD_FACADE.getLang("LBL_HRS_ALL_TEXT"));
        lblHrsAll3.setText(MOD_FACADE.getLang("LBL_HRS_ALL_TEXT"));
        lblGuilds.setText(MOD_FACADE.getLang("LBL_GUILDS"));
        
      }
    
    @FXML
    private void handleLogout(ActionEvent event) throws IOException
      {

        //Need to refactor this method next sprint - no time today!
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/HourLoginView.fxml"));
        StackPane page = (StackPane) loader.load();
        
        MOD_FACADE.changeView(3);
        Stage stage = (Stage) btnEditSave.getScene().getWindow();
        stage.close();
      }
    
    @FXML
    private void ChangePasswordEvent(ActionEvent event)
      {
        if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
          {
            MOD_FACADE.changePassword(currentUser, txtOPassword.getText(), txtNPassword.getText());
          }
      }
    
    @FXML
    private void OpenPasswordChangerEvent(ActionEvent event)
      {
        stckPanePasswordChanger.setVisible(true);
        MOD_FACADE.fadeInTransition(Duration.millis(750), stckPanePasswordChanger);
        
      }
    
    @FXML
    private void HidePasswordChangerEvent(ActionEvent event)
      {
        MOD_FACADE.fadeOutTransition(Duration.millis(750), stckPanePasswordChanger).setOnFinished(e -> stckPanePasswordChanger.setVisible(false));
      }
    
  }
