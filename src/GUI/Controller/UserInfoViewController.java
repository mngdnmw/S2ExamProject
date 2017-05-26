package GUI.Controller;

import BE.Day;
import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXPopup;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    private JFXButton btnAddHours;
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
    @FXML
    private JFXDatePicker datePickerInPop;
    @FXML
    private JFXButton btnIntDown;
    @FXML
    private JFXTextField txtfldHours;
    @FXML
    private JFXButton btnIntUp;
    @FXML
    private JFXComboBox<Guild> comboboxGuild;
    @FXML
    private Label lblDateInPop;
    @FXML
    private Label lblHoursInPop;
    @FXML
    private Label lblGuildInPop;
    @FXML
    private StackPane stckPaneAddHours;

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
    @FXML
    private JFXButton btnCancelPW;
    @FXML
    private JFXButton btnAddHoursPOP;
    @FXML
    private JFXButton btnCancelPOP;

    //Objects Used
    User currentUser;
    JFXPopup popup;
    JFXButton higherClearanceBtn = new JFXButton();
    JFXButton btnCancelEditInfo = new JFXButton();
    FilteredList<Day> filteredData = new FilteredList<>(FXCollections.observableArrayList());
    //JFXButton btnNewCancel = new JFXButton();

    //Variables Used
    boolean editing = false;
    boolean isIncorrect = false;
    boolean finishedService;
    boolean firstRun;
    File newImg;
    boolean editPopup = false;

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
                    MOD_FACADE.setAllAdminsIntoArray();
                    MOD_FACADE.getAllSavedUsers();
                    finishedService = true;
                    return null;

                }
            };
        }
    };
    private final Service serviceSavePicture = new Service()
    {
        @Override
        protected Task createTask()
        {
            return new Task()
            {
                @Override
                protected Object call() throws Exception
                {
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
                    return null;

                }
            };
        }
    };

    private final Service serviceInitializer = new Service()
    {
        @Override
        protected Task createTask()
        {
            return new Task()
            {
                @Override
                protected Object call() throws Exception
                {
                    if (firstRun)
                    {
                        setUserImage();
                    }
                    filteredData = new FilteredList<>(FXCollections.observableArrayList(MOD_FACADE.getWorkedDays(currentUser)), p -> true);
                    firstRun = false;
                    return null;

                }
            };
        }
    };
    @FXML
    private HBox hBoxBtnsInPOP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        setCurrentUser(MOD_FACADE.getCurrentUser());
        setUserInfo();
        checkTypeOfUser();
        createEditFields();
        setTextAll();
        setupGuildList();
        setupTableView("Looking For Data");
        serviceInitializer.start();

        serviceInitializer.setOnSucceeded(e
                -> setupTableView("Found Nothing :("));
        if (currentUser.getType() >= 1)
        {
            serviceAllVolunteers.start();
        }

    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    private void setupTableView(String str)
    {

        tableViewMain.setPlaceholder(new Label(str));
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colHours.setCellValueFactory(val -> val.getValue().hourProperty().asObject());
        colGuild.setCellValueFactory(cellData -> cellData.getValue().guildProperty());

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

        higherClearanceBtn.setPrefWidth(160);
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
                    StackPane stckPaneLoad = MOD_FACADE.getLoadingScreen();
                    serviceAllVolunteers.setOnSucceeded(e
                            -> 
                            {

                                MOD_FACADE.changeView(1);
                                stckPaneLoad.setVisible(false);

                    });

                    root.getChildren().add(stckPaneLoad);
                    AnchorPane.setTopAnchor(stckPaneLoad, 0.0);
                    AnchorPane.setBottomAnchor(stckPaneLoad, 0.0);
                    AnchorPane.setLeftAnchor(stckPaneLoad, 0.0);
                    AnchorPane.setRightAnchor(stckPaneLoad, 0.0);

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
        newImg = c.showOpenDialog(btnUpdatePhoto.getScene().getWindow());
        serviceSavePicture.start();
        serviceSavePicture.setOnSucceeded(e
                -> 
                {
                    firstRun = true;
                    serviceInitializer.restart();
        });

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
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) - 1); //moving save button one up

        btnCancelEditInfo.setText(MOD_FACADE.getLang("BTN_CANCEL")); //preparing cancel button
        btnCancelEditInfo.setButtonType(JFXButton.ButtonType.RAISED);
        btnCancelEditInfo.setStyle("-fx-background-color: #B0B0B0;");
        btnCancelEditInfo.setTextFill(Color.WHITE);
        btnCancelEditInfo.setPadding(btnEditSave.getPadding());

        gridEdit.add(btnCancelEditInfo, btnSavePosCol, btnSavePosRow); //adding to the old position of save btn
        GridPane.setValignment(btnEditSave, VPos.CENTER);
        GridPane.setValignment(btnCancelEditInfo, VPos.CENTER);
        btnCancelEditInfo.setOnAction(new EventHandler<ActionEvent>()
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

            }
        });
    }

    private void removeCancelButton()
    {
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) + 1); //moving save button one down
        gridEdit.getChildren().remove(btnCancelEditInfo); //deleting cancel button from gridpane
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
        if (txtOPassword.getText().equals(txtNPassword.getText()))
        {
            count= -1;
        }
        else if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
        {
            count = MOD_FACADE.changePassword(currentUser, txtOPassword.getText(), txtNPassword.getText());
        }
        else
        {
            count = -2;
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
            b.show("Old password is the same as new password", 2000);
        }
        else if (count == -2)
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Password do not match", 2000);
        }

        else
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Old password is incorrect", 2000);
        }

    }

    @FXML
    private void openAddHoursPopup(ActionEvent event)
    {
        editPopup = true;
        //Clears everything from previous
        datePickerInPop.setValue(null);
        buttonsLocking(false);
        txtfldHours.clear();

        setupAddHoursPopup();
        stckPaneAddHours.setVisible(true);
        MOD_FACADE.fadeInTransition(Duration.millis(750), stckPaneAddHours);

    }

    @FXML
    private void onTablePressed(MouseEvent click
    )
    {

        ContextMenu popupContext = new ContextMenu();
        MenuItem editDay = new MenuItem("Edit");
        popupContext.getItems().add(editDay);
        MenuItem deleteDay = new MenuItem("Delete");
        popupContext.getItems().add(deleteDay);

        tableViewMain.setContextMenu(popupContext);
        Day selectedDay = tableViewMain.getSelectionModel().getSelectedItem();

        EventHandler editDayEvent = new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                //May change to editible table later
//                tableViewMain.setEditable(true);
//                editingTable();

                setupAddHoursPopup();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(selectedDay.getDate(), formatter);

                datePickerInPop.setValue(date);
                txtfldHours.setText(String.valueOf(selectedDay.getHour()));
                Guild guild = MOD_FACADE.getGuild(selectedDay.getGuildId());
                comboboxGuild.getSelectionModel().select(guild);

                buttonsLocking(false);

                stckPaneAddHours.setVisible(true);
                MOD_FACADE.fadeInTransition(Duration.millis(750), stckPaneAddHours);

            }
        };
        editDay.setOnAction(editDayEvent);

        EventHandler deleteDayEvent = new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                //Might be able to select multiple days in the future
//                List selectedDay = new ArrayList(tableViewMain.getSelectionModel().getSelectedItems());
//                for (Object day : selectedDay)
//                {
//                    MOD_FACADE.deleteWorkedDay(currentUser, (Day) day);
//                }

                MOD_FACADE.deleteWorkedDay(currentUser, selectedDay);

                snackBarPopup("Contribution for " + selectedDay.getGuild() + " on the " + selectedDay.getDate() + " has been deleted.");
                serviceInitializer.restart();
            }

        };
        deleteDay.setOnAction(deleteDayEvent);

    }

    public void snackBarPopup(String str)
    {
        int time = 3000;
        JFXSnackbar snackbar = new JFXSnackbar(root);
        snackbar.show(str, time);
        PauseTransition pause = new PauseTransition(Duration.millis(time - 2000));
        pause.setOnFinished(
                e -> buttonsLocking(false)
        );
        pause.play();

    }

    public void buttonsLocking(Boolean dis)
    {
        datePickerInPop.setDisable(dis);
        btnIntUp.setDisable(dis);
        btnIntDown.setDisable(dis);
        comboboxGuild.setDisable(dis);

    }

    private void formatCalendar()
    {
        StringConverter converter = new StringConverter<LocalDate>()
        {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date)
            {
                if (date != null)
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string)
            {
                if (string != null && !string.isEmpty())
                {
                    return LocalDate.parse(string, dateFormatter);
                }
                else
                {
                    return null;
                }
            }
        };
        datePickerInPop.setConverter(converter);

        // Create a day cell factory
        datePickerInPop.setDayCellFactory(new Callback<DatePicker, DateCell>()
        {

            @Override
            public DateCell call(final DatePicker datepicker)
            {
                return new DateCell()
                {
                    @Override

                    public void updateItem(LocalDate item, boolean empty)

                    {

                        // Must call super
                        super.updateItem(item, empty);

                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now()))

                        {

                            this.setDisable(true);

                        }

                    }
                };
            }

        });
    }

    private void setupAddHoursPopup()
    {
        formatCalendar();
        comboboxGuild.setItems(FXCollections.observableArrayList(MOD_FACADE.getAllSavedGuilds()));

        txtfldHours.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                try
                {
                    if (newValue.matches("\\d*") && newValue.length() < 3)
                    {
                        if (Integer.parseInt(newValue) >= 25)
                        {
                            snackBarPopup("You cannot exceed 24 hours");

                            txtfldHours.setText(oldValue);
                        }
                        else if (Integer.parseInt(newValue) <= 0)
                        {
                            snackBarPopup("You cannot log 0 hours");
                            txtfldHours.setText(oldValue);
                        }
                        else
                        {
                            int value = Integer.parseInt(newValue);
                        }
                    }
                    else
                    {
                        txtfldHours.setText(oldValue);
                    }
                }
                catch (NumberFormatException ex)
                {
                    //do nothing
                }
            }

        });
        new GUI.Model.AutoCompleteComboBoxListener<>(comboboxGuild);

        comboboxGuild.setConverter(new StringConverter<Guild>()
        {

            @Override
            public String toString(Guild object)
            {
                if (object == null)
                {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Guild fromString(String string)
            {
                Guild findGuild = null;
                for (Guild guild : comboboxGuild.getItems())
                {
                    if (guild.getName().equals(string))
                    {
                        return guild;
                    }

                }
                return findGuild;
            }
        });
    }

    @FXML
    private void setNumberOfHoursEvent(ActionEvent event)
    {

        if ((event.getSource().equals(btnIntUp)))
        {
            if (txtfldHours.getText().isEmpty())
            {
                txtfldHours.setText("1");
            }
            else
            {
                int hours = Integer.parseInt(txtfldHours.getText());

                int currentHours = Integer.parseInt(txtfldHours.getText());
                currentHours++;
                txtfldHours.setText(currentHours + "");
            }
        }
        if ((event.getSource().equals(btnIntDown)))
        {

            if (txtfldHours.getText().isEmpty())
            {
                snackBarPopup("Invalid Action");
            }
            else
            {
                int hours = Integer.parseInt(txtfldHours.getText());
                hours--;
                txtfldHours.setText(hours + "");

            }
        }
    }

    @FXML
    private void handleAddEditHours(ActionEvent event)
    {

        StackPane stckLoadScreen = MOD_FACADE.getLoadingScreen();
        root.getChildren().add(stckLoadScreen);

        buttonsLocking(true);

        if (datePickerInPop.getValue() != null && !txtfldHours.getText().isEmpty() && !comboboxGuild.getSelectionModel().isEmpty())
        {

            String date = datePickerInPop.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int hours = Integer.parseInt(txtfldHours.getText());

            int guildID = comboboxGuild.getSelectionModel().getSelectedItem().getId();

            int errorCode = 1;

            if (currentUser.getEmail() != null)
            {

                if (editPopup = true)
                {

                    errorCode = MOD_FACADE.logHours(currentUser.getEmail(), date, hours, guildID);
                }
                else
                {

                    errorCode = MOD_FACADE.editHours(currentUser.getEmail(), date, hours, guildID);
                }
                stckLoadScreen.setVisible(false);

                contributionSnackBarHandler(errorCode);

            }
            else if (currentUser.getPhone() != 0)

            {
                if (editPopup = true)
                {
                    errorCode = MOD_FACADE.logHours(currentUser.getPhone() + "", date, hours, guildID);
                }
                else
                {

                    errorCode = MOD_FACADE.editHours(currentUser.getPhone() + "", date, hours, guildID);
                }
                stckLoadScreen.setVisible(false);
                contributionSnackBarHandler(errorCode);

            }
        }

        else
        {
            snackBarPopup("Please input information in all fields");
        }

    }

    public void contributionSnackBarHandler(int errorCode)
    {
        switch (errorCode)
        {
            case 0:
                snackBarPopup(MOD_FACADE.getLang("STR_NO_ERROR_CONTRIBUTION"));
                closeAddHoursPopup();
                serviceInitializer.restart();
                break;
            case 1:
                System.out.println("Error code was not correctly initialised");
                break;
            case 2627:
                snackBarPopup(MOD_FACADE.getLang("STR_ERROR_2627"));
                break;
            default:
                snackBarPopup(MOD_FACADE.getLang("STR_FIRST_TIME_ERROR" + errorCode));
                break;
        }
    }

    @FXML
    private void closeAddHoursPopup()
    {
        MOD_FACADE.fadeOutTransition(Duration.millis(750), stckPaneAddHours)
                .setOnFinished(e -> stckPaneAddHours.setVisible(false));
        editPopup = false;
    }

    @FXML
    private void openPasswordChangerEvent(ActionEvent event
    )
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

    private void editingTable()
    {
        colDate.setCellFactory(TextFieldTableCell.forTableColumn());
        colDate.setOnEditCommit(new EventHandler<CellEditEvent<Day, String>>()
        {
            @Override
            public void handle(CellEditEvent<Day, String> event)
            {
                ((Day) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDate(event.getNewValue());
            }
        });

        colHours.setCellFactory(TextFieldTableCell.<Day, Integer>forTableColumn(new IntegerStringConverter()));
        colHours.setOnEditCommit(new EventHandler<CellEditEvent<Day, Integer>>()
        {
            @Override
            public void handle(CellEditEvent<Day, Integer> event)
            {
                ((Day) event.getTableView().getItems().get(event.getTablePosition().getRow())).setHour(event.getNewValue());
            }
        });

        colGuild.setCellFactory(TextFieldTableCell.forTableColumn());
        colGuild.setOnEditCommit(new EventHandler<CellEditEvent<Day, String>>()
        {
            @Override
            public void handle(CellEditEvent<Day, String> event)
            {
                ((Day) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDate(event.getNewValue());
            }
        });
    }

}
