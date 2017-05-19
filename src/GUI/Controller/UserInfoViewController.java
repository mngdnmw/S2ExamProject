package GUI.Controller;

import BE.Day;
import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXPopup;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserInfoViewController implements Initializable

{

    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private JFXButton btnUpdatePhoto;
    @FXML
    private GridPane gridEdit;
    @FXML
    private JFXButton btnEditSave;
    @FXML
    private AnchorPane root;
    @FXML
    private HBox hBoxInvisBtn;
    @FXML
    private JFXTextField txtFSearchDate;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnChangePassword;
    @FXML
    private StackPane stckPanePasswordChanger;
    @FXML
    private JFXPasswordField txtOPassword;
    @FXML
    private JFXPasswordField txtNPassword;
    @FXML
    private JFXPasswordField txtNPasswordTwo;
    @FXML
    private Label lblGuilds;
    @FXML
    private TableView<Day> tableViewMain;
    @FXML
    private TableColumn<Day, String> colDate;
    @FXML
    private TableColumn<Day, String> colGuild;
    @FXML
    private TableColumn<Day, Integer> colHours;
    @FXML
    private JFXListView<Guild> listVwGuilds;
    //FXML Textfields
    @FXML
    TextField txtName;
    @FXML
    TextField txtPhone;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtAddress;
    @FXML
    TextField txtAddress2;
    //FXML Labels
    @FXML
    private Label lblOldPassword;
    @FXML
    private Label lblNewPassword;
    @FXML
    private Label lblNewPassword2;
    @FXML
    private JFXButton btnChangePWConfirm;
    //Objects Used
    User currentUser;
    JFXPopup popup;
    JFXButton higherClearanceBtn = new JFXButton();
    @FXML
    JFXButton btnCancel = new JFXButton();
    //Variables Used
    boolean editing = false;
    boolean isIncorrect = false;
    boolean finishedService;
    private final static ModelFacade MOD_FACADE = ModelFacade.getModelFacade();

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
                    MOD_FACADE.setAllManagersIntoArray();
                    finishedService = true;
                    return null;

                }
            };
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        setCurrentUser(MOD_FACADE.getCurrentUser());
        setUserInfo();
        setUserImage();
        checkTypeOfUser();
        createEditFields();
        setTextAll();
        setupTableView();
        setupGuildList();

        if (currentUser.getType() >= 1)
        {
            serviceAllVolunteers.start();
        }

    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    private void setupTableView()
    {

        tableViewMain.setPlaceholder(new Label("Nothing found :("));
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colHours.setCellValueFactory(val -> val.getValue().hourProperty().asObject());
        colGuild.setCellValueFactory(cellData -> cellData.getValue().guildProperty());

        FilteredList<Day> filteredData = new FilteredList<>(FXCollections.observableArrayList(MOD_FACADE.getWorkedDays(currentUser)), p -> true);

        txtFSearchDate.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                -> 
                {
                    filteredData.setPredicate(day
                            -> 
                            {
                                String regex = "[^a-zA-Z0-9\\s]";
                                Boolean search
                                        = day.dateProperty().getValue().replaceAll(regex, "")
                                        .contains(newValue.replaceAll(regex, ""))
                                        || day.guildProperty().getValue().toLowerCase().replaceAll(regex, "").
                                        contains(newValue.toLowerCase().replaceAll(regex, ""));

                                return search;

                    });
        });

        SortedList<Day> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewMain.comparatorProperty());
        tableViewMain.setItems(sortedData);

    }

    private void setupGuildList()
    {
        listVwGuilds.setItems(FXCollections.observableArrayList(currentUser.getGuildList()));
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

        higherClearanceBtn.setPrefWidth(140);
        higherClearanceBtn.setId("higherClearanceBtn");
        higherClearanceBtn.toFront();
        higherClearanceBtn.setVisible(true);

        hBoxInvisBtn.setAlignment(Pos.CENTER);
        hBoxInvisBtn.getChildren().add(higherClearanceBtn);

        higherClearanceBtn.getStylesheets().add("GUI/View/MainLayout.css");

        if (type == 1)
        {
            higherClearanceBtn.setText(MOD_FACADE.getLang("BTN_HIGHER_CLEARANCE_1"));          

        }
        else
        {
            higherClearanceBtn.setText(MOD_FACADE.getLang("BTN_HIGHER_CLEARANCE_2"));

        }

        higherClearanceBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (finishedService)
                {
                    MOD_FACADE.changeView(1);
                }
                else
                {
                    serviceAllVolunteers.setOnSucceeded(e
                            -> 
                            {

                                MOD_FACADE.changeView(1);
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
        txtName.setText(currentUser.getName());
        txtPhone.setText(String.valueOf(currentUser.getPhone()));
        txtEmail.setText(currentUser.getEmail());
        txtAddress.setText(currentUser.getResidence());
        txtAddress2.setText(currentUser.getResidence2());
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
            btnEditSave.setStyle("-fx-background-color:#00c4ad;");
        }
    }

    private void createEditFields()

    {

        txtPhone.setOnKeyReleased(new EventHandler<KeyEvent>()

        {
            @Override
            public void handle(KeyEvent event)
            {
                checkTextFields();

            }

        });
    }

    private void editInfo()
    {
        txtName.setEditable(true);
        txtEmail.setEditable(true);
        txtPhone.setEditable(true);
        txtAddress.setEditable(true);
        txtAddress2.setEditable(true);
    }

    private void saveInfo(User user)
    {
        MOD_FACADE.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPhone.getText()), user.getNote(), txtAddress.getText(), txtAddress2.getText());

        currentUser = MOD_FACADE.getUserInfo(user.getId());

        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhone.setEditable(false);
        txtAddress.setEditable(false);
        txtAddress2.setEditable(false);
        setUserInfo(); //update labels

    }

    private void checkTextFields()
    {
        boolean success = false;
        try

        {
            Integer.parseInt(txtPhone.getText());
            success = true;
        }
        catch (NumberFormatException e)
        {
            success = false;
            txtPhone.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
        }
        if (success)
        {
            btnEditSave.setDisable(false);
            txtPhone.setStyle("");
            isIncorrect = false;
        }
        else
        {
            txtPhone.setStyle("-fx-background-color:red;");
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

        btnCancel.setText(MOD_FACADE.getLang("BTN_CANCEL")); //preparing cancel button
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
                txtName.setEditable(false);
                txtEmail.setEditable(false);
                txtPhone.setEditable(false);
                txtAddress.setEditable(false);
                txtAddress2.setEditable(false);

                removeCancelButton(); //if cancel button clicked, it will disappear
                editing = false;
                btnEditSave.setText(MOD_FACADE.getLang("BTN_EDIT"));
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
        btnCancel.setText(MOD_FACADE.getLang("BTN_CANCEL"));

        colDate.setText(MOD_FACADE.getLang("COL_DATE"));
        colHours.setText(MOD_FACADE.getLang("COL_HOURS"));
        colGuild.setText(MOD_FACADE.getLang("COL_GUILD"));
        txtFSearchDate.setPromptText(MOD_FACADE.getLang("PROMPT_SEARCH"));
        lblGuilds.setText(MOD_FACADE.getLang("LBL_GUILDS"));

    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/HourLoginView.fxml"));
        StackPane page = (StackPane) loader.load();

        MOD_FACADE.changeView(3);
        Stage stage = (Stage) btnEditSave.getScene().getWindow();
        stage.close();

    }

    @FXML

    private void changePasswordEvent(ActionEvent event)
    {
        int count;
        if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
        {
            count = MOD_FACADE.changePassword(currentUser, txtOPassword.getText(), txtNPassword.getText());
        }
        else
        {
            count = -1;
        }
        if (count > 0)
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Password has succesfully changed", 2000);
            hidePasswordChangerEvent();
        }
        else if (count == -1)
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Password do not match", 2000);
        }
        else
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Old password is wrong", 2000);
        }

    }

    @FXML
    private void openPasswordChangerEvent(ActionEvent event)
    {
        stckPanePasswordChanger.setVisible(true);
        MOD_FACADE.fadeInTransition(Duration.millis(750), stckPanePasswordChanger);

    }

    @FXML
    private void hidePasswordChangerEvent()

    {
        MOD_FACADE.fadeOutTransition(Duration.millis(750), stckPanePasswordChanger)
                .setOnFinished(e -> stckPanePasswordChanger.setVisible(false));

    }

    private void initPopup()
    {
        popup = new JFXPopup();
        JFXButton btnEdit = new JFXButton(MOD_FACADE.getLang("BTN_EDIT"));
        JFXButton btnDelete = new JFXButton(MOD_FACADE.getLang("BTN_DELETE"));
        btnEdit.setPadding(new Insets(10));
        btnEdit.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setPadding(new Insets(10));
        VBox vBox = new VBox(btnEdit, btnDelete);
        popup.setPopupContent(vBox);
        
        ObservableList<Day> daysSelected, allDays;
        allDays = tableViewMain.getItems();
        daysSelected =tableViewMain.getSelectionModel().getSelectedItems();
        
        btnEdit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                popup.hide();
            }
        });
        
        btnDelete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                daysSelected.forEach(allDays::remove);
            }
            
        });

    }

    @FXML
    private void popupEditDelete(MouseEvent event)
    {
        MouseButton button = event.getButton();
        if (button == MouseButton.SECONDARY)
        {
            initPopup();
            popup.show((Node) event.getSource(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());

        }
    }

}
