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

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class UserInfoViewController implements Initializable

{

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane gridEdit;
    @FXML
    private JFXDatePicker datePickerInPop;
    @FXML
    private StackPane stackPdeleteHours;
    @FXML
    private StackPane stckPaneAddHours;
    @FXML
    private StackPane stckPanePasswordChanger;
    @FXML
    private JFXButton btnEditSave;
    @FXML
    private JFXButton btnUpdatePhoto;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnAddHours;
    @FXML
    private JFXButton btnChangePassword;
    @FXML
    private JFXButton btnChangePWConfirm;
    @FXML
    private JFXButton btnCancelPW;
    @FXML
    private JFXButton btnAddHoursPOP; //Okay does not need translating
    @FXML
    private JFXButton btnCancelPOP;
    @FXML
    private JFXButton btnIntDown;
    @FXML
    private JFXButton btnIntUp;
    @FXML
    private JFXTextField txtFSearchDate;
    @FXML
    private JFXTextField txtfldHoursInPop;
    @FXML
    private JFXPasswordField txtOPassword;
    @FXML
    private JFXPasswordField txtNPassword;
    @FXML
    private JFXPasswordField txtNPasswordTwo;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private ImageView imgVwDel;
    @FXML
    private ImageView imgVwEdit;
    @FXML
    private Label lblOldPassword;
    @FXML
    private Label lblNewPassword;
    @FXML
    private Label lblNewPassword2;
    @FXML
    private Label lblGuilds;
    @FXML
    private Label lblDateInPop;
    @FXML
    private Label lblHoursInPop;
    @FXML
    private Label lblGuildInPop;
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
    private JFXComboBox<Guild> comboboxGuildInPop;
    @FXML
    private HBox hBoxBtnsInPOP;
    @FXML
    private HBox hBoxInvisBtn;

    //FXML editable textfields
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

    //Objects used
    User currentUser;
    JFXPopup popup;
    File newImg;
    Day dayToEdit = null;
    JFXButton btnHighClearance = new JFXButton();
    JFXButton btnCancelEditInfo = new JFXButton();
    FilteredList<Day> filteredData = new FilteredList(FXCollections.observableArrayList());
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
    private final static ModelFacade MOD_FAC = ModelFacade.getModelFacade();

    //Variables ssed
    boolean editing = false;
    boolean isIncorrect = false;
    boolean finishedService = true;

    /**
     * Creates a service that runs in the background which contacts the database
     * to update the user image to one that the user has chosen. The service
     * also records the updating of the image into the event logs stored on the
     * database.
     */
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
                            MOD_FAC.updateUserImage(currentUser, newImg);
                            MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " changed his/her image."));
                        }
                        catch (FileNotFoundException e)
                        {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setHeaderText(MOD_FAC.getLang("STR_ERROR_FILE_NOT_FOUND_HEAD"));
                            a.setContentText(MOD_FAC.getLang("STR_ERROR_FILE_NOT_FOUND"));
                        }
                    }
                    return null;

                }
            };
        }
    };

    /**
     * Creates a service that runs in the background which contacts the database
     * to load the days the user has worked into the filtered list.
     */
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
                    filteredData = new FilteredList<>(MOD_FAC.getWorkedDays(currentUser), p -> true);
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

        setCurrentUser(MOD_FAC.getCurrentUser());
        setUserInfo();
        checkTypeOfUser();
        createEditFields();
        setTextAll();
        setupGuildList();
        setupTableView(MOD_FAC.getLang("TBL_LOADING"));
        searchListener();
        setUserImage();
        setupDragDrop();
        serviceInitializer.start();
        serviceInitializer.setOnFailed(e
                -> System.err.println("Error with service initializer"));

        serviceInitializer.setOnSucceeded(e
                ->
        {
            setupTableView(MOD_FAC.getLang("STR_SEARCH_EMPTY"));

        });

    }

    /**
     * Sets all text to the language they are meant to be in.
     */
    private void setTextAll()
    {
        btnUpdatePhoto.setText(MOD_FAC.getLang("BTN_UPDATEPHOTO"));
        btnChangePassword.setText(MOD_FAC.getLang("BTN_CHANGEPASS"));
        btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));
        btnLogout.setText(MOD_FAC.getLang("BTN_LOGOUT"));

        colDate.setText(MOD_FAC.getLang("COL_DATE"));
        colHours.setText(MOD_FAC.getLang("COL_HOURS"));
        colGuild.setText(MOD_FAC.getLang("COL_GUILD"));
        txtFSearchDate.setPromptText(MOD_FAC.getLang("PROMPT_SEARCH"));
        lblGuilds.setText(MOD_FAC.getLang("LBL_GUILDS"));

        lblNewPassword.setText(MOD_FAC.getLang("LBL_NEW_PW"));
        lblNewPassword2.setText(MOD_FAC.getLang("LBL_NEW_PW2"));
        lblOldPassword.setText(MOD_FAC.getLang("LBL_OLD_PW"));
        lblDateInPop.setText(MOD_FAC.getLang("COL_DATE"));
        lblGuildInPop.setText(MOD_FAC.getLang("COL_GUILD"));
        lblHoursInPop.setText(MOD_FAC.getLang("COL_HOURS"));
        btnCancelPW.setText(MOD_FAC.getLang("BTN_CANCEL"));
        btnChangePWConfirm.setText(MOD_FAC.getLang("BTN_EDIT"));
        btnAddHours.setText(MOD_FAC.getLang("BTN_ADD_HOURS"));
        btnCancelPOP.setText(MOD_FAC.getLang("BTN_CANCEL"));

    }

    /**
     * Sets up the drag and drop functionality of editing or deleting a certain
     * day worked. If the day dropped on the delete image, then it will delete
     * that day. If the day is dropped on the edit image then the edit day
     * worked pop up will appear.
     */
    private void setupDragDrop()
    {
        imgVwDel.setOnDragOver(event
                ->
        {
            Dragboard db = event.getDragboard();
            if (db.hasContent(SERIALIZED_MIME_TYPE))
            {

                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            }
            event.consume();
        });

        imgVwDel.setOnDragDropped(new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent event)
            {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE))
                {
                    Day dayToDelete = tableViewMain.getSelectionModel().getSelectedItem();
                    MOD_FAC.deleteWorkedDay(currentUser, dayToDelete);
                    event.setDropCompleted(true);
                    MOD_FAC.fadeOutTransition(Duration.millis(250), stackPdeleteHours).setOnFinished(ez -> stackPdeleteHours.setVisible(false));

                    event.consume();
                }
            }
        });

        imgVwEdit.setOnDragOver(event
                ->
        {
            Dragboard db = event.getDragboard();
            if (db.hasContent(SERIALIZED_MIME_TYPE))
            {

                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            }
            event.consume();
        });

        imgVwEdit.setOnDragDropped(new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent event)
            {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE))
                {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    dayToEdit = tableViewMain.getItems().get(draggedIndex);

                    event.setDropCompleted(true);
                    MOD_FAC.fadeOutTransition(Duration.millis(250), stackPdeleteHours).setOnFinished(ez -> stackPdeleteHours.setVisible(false));
                    handleOpenAddHoursPopup();
                    event.consume();
                }
            }
        });

    }

    /**
     * Handles the popup that appears for both editing and adding hours worked.
     */
    private void handleOpenAddHoursPopup()
    {

        stckPaneAddHours.setVisible(true);
        MOD_FAC.fadeInTransition(Duration.millis(750), stckPaneAddHours);

        //Sets up the popup depending if it is editing or adding hours worked
        setupAddHoursPopup();

    }

    /**
     * Locks combobox and calendar in the popup if set to true.
     *
     * @param dis = boolean
     */
    public void fieldsLocking(Boolean dis)
    {
        datePickerInPop.setDisable(dis);
        comboboxGuildInPop.setDisable(dis);

    }

    /**
     * Sets up the calendar (so user cannot choose beyond today's date); the
     * combobox (guilds are loaded into there); adds a listener to the hour
     * butons; adds search functionality to combobox.
     */
    private void setupAddHoursPopup()
    {

        //Sets all the guilds in the combobox
        comboboxGuildInPop.setItems(MOD_FAC.getAllSavedGuilds());
        //If editing rather than adding new day worked
        if (dayToEdit != null)
        {

            //Sets calendar to date of editing day's
            LocalDate date = LocalDate.parse(dayToEdit.getDate());
            datePickerInPop.setValue(date);
            //Sets hours worked to editing day's
            txtfldHoursInPop.setText(String.valueOf(dayToEdit.getHour()));
            //Sets guild worked to editing day's
            for (Guild guild : comboboxGuildInPop.getItems())
            {
                if (guild.getId() == dayToEdit.getGuildId())
                {

                    comboboxGuildInPop.getSelectionModel().select(guild);
                    break;
                }
            }
            //Locks fields
            fieldsLocking(true);

        }
        //Cannot pick dates beyond today's date
        MOD_FAC.formatCalendar(datePickerInPop);

        //Adds a listener to the hour text field to ensure that input does not 
        //exceed are less than hours in a day 
        txtfldHoursInPop.textProperty().addListener(new ChangeListener<String>()
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
                            MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_MAX_HOUR"), root);

                            txtfldHoursInPop.setText(oldValue);
                        }
                        else if (Integer.parseInt(newValue) <= 0)
                        {
                            MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_MIN_HOUR"), root);
                            txtfldHoursInPop.setText(oldValue);
                        }
                    }
                    else
                    {
                        txtfldHoursInPop.setText(oldValue);
                    }
                }
                catch (NumberFormatException ex)
                {
                    System.err.println("NumberFormatException");
                }
            }

        });

        //Search function of the combobox
        new GUI.Model.AutoCompleteComboBoxListener<>(comboboxGuildInPop);

        comboboxGuildInPop.setConverter(
                new StringConverter<Guild>()
        {

            @Override
            public String toString(Guild object
            )
            {
                if (object == null)
                {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Guild fromString(String string
            )
            {
                Guild findGuild = null;
                for (Guild guild : comboboxGuildInPop.getItems())
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

    /**
     * Changes the text field when the up or down button has been pressed.
     *
     * @param event = when up or down button has been pressed.
     */
    @FXML
    private void setNumberOfHoursEvent(ActionEvent event)
    {

        if ((event.getSource().equals(btnIntUp)))
        {
            if (txtfldHoursInPop.getText().isEmpty())
            {
                txtfldHoursInPop.setText("1");
            }
            else
            {

                int currentHours = Integer.parseInt(txtfldHoursInPop.getText());
                currentHours++;
                txtfldHoursInPop.setText(currentHours + "");
            }
        }
        if ((event.getSource().equals(btnIntDown)))
        {

            if (txtfldHoursInPop.getText().isEmpty())
            {

                MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_INVALID_ACTION"), root);
            }
            else
            {
                int hours = Integer.parseInt(txtfldHoursInPop.getText());
                hours--;
                txtfldHoursInPop.setText(hours + "");

            }
        }
    }

    /**
     * Sets the local variable currentUser to the user that was passed through
     * from the login view.
     *
     * @param currentUser = currentUser.
     */
    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    /**
     * Sets up the main table view in the view which stores day objects of the
     * days the user has worked.
     *
     * @param str = string placeholder in the table if there are no days loaded.
     */
    private void setupTableView(String str)
    {

        tableViewMain.setPlaceholder(new Label(str));
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colHours.setCellValueFactory(val -> val.getValue().hourProperty().asObject());
        colGuild.setCellValueFactory(cellData -> cellData.getValue().guildProperty());

        SortedList<Day> sortedData = new SortedList<>(filteredData);

        searchListener();

        sortedData.comparatorProperty().bind(tableViewMain.comparatorProperty());
        tableViewMain.setItems(sortedData);

        tableViewMain.setRowFactory(tv
                ->
        {
            TableRow<Day> row = new TableRow<>();

            row.setOnDragDetected(event
                    ->
            {
                if (!row.isEmpty())
                {
                    stackPdeleteHours.setVisible(true);
                    MOD_FAC.fadeInTransition(Duration.millis(250), stackPdeleteHours);

                    int selectedDayIndex = tableViewMain.getSelectionModel().getSelectedIndex();

                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();

                    // Store row ID in order to know what is dragged.
                    cc.put(SERIALIZED_MIME_TYPE, selectedDayIndex);
                    db.setContent(cc);

                    event.consume();
                }
            });
            row.setOnDragDone(new EventHandler<DragEvent>()
            {
                @Override
                public void handle(DragEvent e)
                {
                    System.out.println("removes stackpane");
                    MOD_FAC.fadeOutTransition(Duration.millis(250), stackPdeleteHours).setOnFinished(ez -> stackPdeleteHours.setVisible(false));

                    e.consume();
                }
            });

            return row;
        });

    }

    /**
     * Adds a search listener to the tableview so the user can search days
     * worked based on date or guild.
     */
    private void searchListener()
    {
        txtFSearchDate.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                ->
        {
            filteredData.setPredicate(new Predicate<Day>()
            {
                @Override
                public boolean test(Day day)
                {
                    String regex = "[^a-zA-Z0-9\\s]";
                    Boolean search
                            = day.dateProperty().getValue().replaceAll(regex, "")
                                    .contains(newValue.replaceAll(regex, ""))
                            || day.guildProperty().getValue().toLowerCase().replaceAll(regex, "").
                                    contains(newValue.toLowerCase().replaceAll(regex, ""));
                    
                    return search;
                }
            });
        });
    }

    /**
     * Fills the list view with a list of guilds the user is a part of.
     */
    private void setupGuildList()
    {
        listVwGuilds.setItems(FXCollections.observableArrayList(currentUser.getGuildList()));
    }

    /**
     * Check what type of user this is, if it's a manager or administrator, a
     * button will be created which will give them access to other views. If the
     * user is an administrator, the list view is removed because they have
     * access to all guilds.
     *
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
                listVwGuilds.setVisible(false);
                lblGuilds.setVisible(false);
                break;
        }
    }

    /**
     * Displays additional button for managers and administrators.
     *
     * @param type 1 = Manager, 2 = Admin.
     */
    private void createHighClearanceButton(int type)

    {

        btnHighClearance.setMinWidth(175);
        btnHighClearance.setId("btnConfirmTeal");
        btnHighClearance.toFront();
        btnHighClearance.setVisible(true);
        btnCancelEditInfo.setPrefHeight(25);

        hBoxInvisBtn.setAlignment(Pos.CENTER);
        hBoxInvisBtn.getChildren().add(btnHighClearance);

        btnHighClearance.getStylesheets().add("GUI/View/MainLayout.css");
        btnHighClearance.prefHeightProperty().set(btnChangePassword.getPrefHeight());

        btnHighClearance.prefWidthProperty().set(btnChangePassword.getPrefWidth());

        if (type == 1)
        {
            btnHighClearance.setText(MOD_FAC.getLang("BTN_HIGHER_CLEARANCE_1"));

        }
        else
        {
            btnHighClearance.setText(MOD_FAC.getLang("BTN_HIGHER_CLEARANCE_2"));

        }

        btnHighClearance.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                MOD_FAC.changeView(1);

            }
        });

    }

    /**
     * Sets the general information of the user.
     */
    private void setUserInfo()
    {
        txtName.setText(currentUser.getName());
        txtPhone.setText(String.valueOf(currentUser.getPhone()));
        txtEmail.setText(currentUser.getEmail());
        txtAddress.setText(currentUser.getResidence());
        txtAddress2.setText(currentUser.getResidence2());
    }

    /**
     * An additional cancel button is created along with the editSaveButton,
     * which has changed text to save rather than edit.
     *
     * @param event = when editSaveButton is pressed.
     */
    @FXML
    private void pressedEditSaveButton(ActionEvent event)

    {
        if (!editing)
        {

            editInfo(true);
            editing = true;
            btnEditSave.setText(MOD_FAC.getLang("BTN_SAVE"));
            checkTextFields();
            addCancelButton();

        }
        else
        {
            if (isIncorrect && btnEditSave.isDisabled())
            {
                MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_INVALID_INPUT"), root, 5000);
            }
            saveInfo(currentUser);
            editing = false;
            btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));
            checkTextFields();
            removeCancelButton();

        }
    }

    /**
     * Adds an event handler to the phone number textfield to ensure only
     * numbers are inputted.
     */
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

    /**
     * Updates the user info in the database if it has been changed.
     *
     * @param user = currentUser.
     */
    private void saveInfo(User user)
    {
        MOD_FAC.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPhone.getText()), user.getNote(), txtAddress.getText(), txtAddress2.getText());
        MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " edited personal information."));
        currentUser = MOD_FAC.getUserInfo(user.getId());
        editInfo(false);
        setUserInfo(); //update labels

    }

    /**
     * Ensures all inputs of the general information edit text fields are
     * correct.
     */
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

    /**
     * Formatting for the cancel button, which appears when the edit general
     * info button is pressed.
     */
    private void addCancelButton()
    {

        int btnSavePosCol = GridPane.getColumnIndex(btnEditSave); //Saving position
        int btnSavePosRow = GridPane.getRowIndex(btnEditSave);
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) - 1); //Moving save button one up

        btnCancelEditInfo.setText(MOD_FAC.getLang("BTN_CANCEL")); //Preparing cancel button
        btnCancelEditInfo.setId("btnCancelGrey");
        btnCancelEditInfo.setPrefHeight(25);
        btnCancelEditInfo.setPrefWidth(Double.MAX_VALUE);
        btnCancelEditInfo.setTextFill(Color.WHITE);
        btnCancelEditInfo.setPadding(btnEditSave.getPadding());

        gridEdit.add(btnCancelEditInfo, btnSavePosCol, btnSavePosRow); //Adding to the old position of save btn
        GridPane.setValignment(btnEditSave, VPos.CENTER);
        GridPane.setValignment(btnCancelEditInfo, VPos.CENTER);
        btnCancelEditInfo.setOnAction(new EventHandler<ActionEvent>()
        { //Setting onAction, nothing changed, just show old labels again
            @Override
            public void handle(ActionEvent event)
            {
                editInfo(false);

                removeCancelButton(); //if Cancel button clicked, it will disappear
                editing = false;
                btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));

            }
        });
    }

    /**
     * Makes all the general information textfields editable.
     */
    private void editInfo(boolean editable)
    {
        txtName.setEditable(editable);
        txtEmail.setEditable(editable);
        txtPhone.setEditable(editable);
        txtAddress.setEditable(editable);
        txtAddress2.setEditable(editable);
    }

    /**
     * Removes the cancel button which was created as the result of clicking on
     * the edit general information button.
     */
    private void removeCancelButton()
    {
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) + 1); //Moving save button one down
        gridEdit.getChildren().remove(btnCancelEditInfo); //Deleting cancel button from gridpane
        if (btnEditSave.isDisabled())
        {
            btnEditSave.setDisable(false);
        }
    }

    /**
     * Navigates to and saves a new user image.
     *
     * @param event = when change image button is pressed.
     */
    @FXML
    private void pressedChangeImage(ActionEvent event)

    {
        FileChooser c = new FileChooser();
        c.setTitle(MOD_FAC.getLang("IMG_CH_TITLE"));
        String[] extensions
                =

                {
                    "*.jpg", "*.jpeg", "*.png"
                };
        c.setSelectedExtensionFilter(new ExtensionFilter(MOD_FAC.getLang("IMG_CH_EXT_FILTER"), extensions));
        newImg = c.showOpenDialog(btnUpdatePhoto.getScene().getWindow());
        serviceSavePicture.restart();
        serviceSavePicture.setOnSucceeded(e
                ->
        {
            setUserImage();
        });

    }

    /**
     * Sets the user image according to what is stored on the database. A thread
     * is created to ensure user can still use the application while the image
     * ie being retrieved from the database.
     */
    public void setUserImage()
    {

        Runnable r = ()
                ->
        {
            Image img = new Image(MOD_FAC.getUserImage(currentUser));
            if (img != null)
            {
                imgVwProfilePic.setImage(img);
            }

        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Opens the add hours worked stackpane.
     */
    @FXML
    private void openAddHoursPopup(ActionEvent event)
    {
        handleOpenAddHoursPopup();
    }

    /**
     * Saves the information in the edit or add hours worked stackpane.
     *
     * @param event = when add or edit button is pressed in the edit or add
     * hours stackpane is clicked.
     */
    @FXML
    private void handleAddEditHours(ActionEvent event)
    {

        StackPane stckLoadScreen = MOD_FAC.getLoadingScreen();
        root.getChildren().add(stckLoadScreen);

        if (datePickerInPop.getValue() != null && !txtfldHoursInPop.getText().isEmpty() && !comboboxGuildInPop.getSelectionModel().isEmpty())
        {

            String date = datePickerInPop.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int hours = Integer.parseInt(txtfldHoursInPop.getText());
            int guildID = comboboxGuildInPop.getSelectionModel().getSelectedItem().getId();

            if (currentUser.getEmail() != null)
            {
                if (dayToEdit == null)
                {
                    MOD_FAC.logWorkDay(currentUser.getEmail(), date, hours, guildID);
                    MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " added " + hours + " working hours to guild " + MOD_FAC.getGuild(guildID).getName() + " on " + date + "."));

                }
                else
                {

                    MOD_FAC.editWorkedDay(currentUser.getEmail(), date, hours, guildID);
                    MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " edited his/her working hours to " + hours + " in guild " + MOD_FAC.getGuild(guildID).getName() + " on " + date + "."));

                }
                stckLoadScreen.setVisible(false);

                MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_NO_ERROR_CONTRIBUTION"), root);

            }
            else if (currentUser.getPhone() != 0)

            {
                if (dayToEdit == null)
                {
                    MOD_FAC.logWorkDay(currentUser.getPhone() + "", date, hours, guildID);
                    MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " added " + hours + " working hours to guild " + MOD_FAC.getGuild(guildID).getName() + " on " + date + "."));

                }
                else
                {

                    MOD_FAC.editWorkedDay(currentUser.getPhone() + "", date, hours, guildID);
                    MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " edited his/her working hours to " + hours + " in guild " + MOD_FAC.getGuild(guildID).getName() + " on " + date + "."));

                }
                stckLoadScreen.setVisible(false);
                MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_NO_ERROR_CONTRIBUTION"), root);

            }
        }

        else
        {
            MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_INVALID_INPUT"), root, 5000);
        }

        closeAddHoursPopup();

    }

    /**
     * Closes the add or edit hours worked stackpane and clears all information.
     */
    @FXML
    private void closeAddHoursPopup()
    {
        fieldsLocking(false);
        dayToEdit = null;
        //Clears everything from popup
        datePickerInPop.setValue(null);
        txtfldHoursInPop.clear();
        comboboxGuildInPop.setValue(null);
        MOD_FAC.fadeOutTransition(Duration.millis(750), stckPaneAddHours).setOnFinished(e -> stckPaneAddHours.setVisible(false));

    }

    /**
     * Opens the password changer stackpane.
     */
    @FXML
    private void openPasswordChangerEvent(ActionEvent event)
    {
        stckPanePasswordChanger.setVisible(true);
        MOD_FAC.fadeInTransition(Duration.millis(750), stckPanePasswordChanger);

    }

    /**
     * Saves new password into the database.
     *
     * @param event = when save is pressed in the password changer stackpane.
     */
    @FXML
    private void changePasswordEvent(ActionEvent event)
    {
        int count;
        if (txtOPassword.getText().equals(txtNPassword.getText()))
        {
            count = -1;
        }
        else if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
        {
            count = MOD_FAC.changePassword(currentUser, txtOPassword.getText(), txtNPassword.getText());
        }
        else
        {
            count = -2;
        }
        if (count > 0)
        {
            MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_PASSWORD_CHANGE"), root, 5000);
            MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), currentUser.getName() + " changed his/her password."));
            hidePasswordChangerEvent();
        }

        else if (count == -1)
        {
            MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_OLD_PW_MATCH_NEW"), root, 5000);
        }
        else if (count == -2)
        {
            MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_PW_DOESNT_MATCH"), root, 5000);
        }

        else
        {
            MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_OLD_PW_WRONG"), root, 5000);
        }

    }

    /**
     * Closes the password changer stackpane.
     */
    @FXML
    private void hidePasswordChangerEvent()

    {
        MOD_FAC.fadeOutTransition(Duration.millis(750), stckPanePasswordChanger)
                .setOnFinished(e
                        ->
                {
                    stckPanePasswordChanger.setVisible(false);
                    txtOPassword.clear();
                    txtNPassword.clear();
                    txtNPasswordTwo.clear();
                });

    }

    /**
     * Returns to the hour login view if log out button is pressed.
     *
     * @param event = logout button is pressed.
     * @throws IOException = cannot load the right resource/view.
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException
    {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/HourLoginView.fxml"));
        StackPane page = (StackPane) loader.load();

        MOD_FAC.changeView(3);
        Stage stage = (Stage) btnEditSave.getScene().getWindow();
        stage.close();

    }
}
