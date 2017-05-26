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
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class ManagerEditViewController implements Initializable
{

    @FXML
    private JFXButton JFXBtnAccept;
    @FXML
    private JFXButton JFXBtnCancel;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private JFXButton JFXBtnUpdatePhoto;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtAddress2;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXListView<Guild> listGuilds;
    @FXML
    private StackPane stckPanePasswordChanger;
    @FXML
    private JFXPasswordField txtOPassword;
    @FXML
    private JFXPasswordField txtNPassword;
    @FXML
    private JFXPasswordField txtNPasswordTwo;
    @FXML
    private AnchorPane root;
    boolean editPopup = false;
    ManagerViewController mevController;
    File newImg;
    private static ModelFacade MOD_FACADE = new ModelFacade();
    FilteredList<Day> filteredData = new FilteredList<>(FXCollections.observableArrayList());
    private static User selectedUser;

    boolean firstRun;

    boolean finishedService;
    @FXML
    private HBox hBoxCalAll;
    @FXML
    private JFXTextField txtFSearchDate;
    @FXML
    private TableView<Day> tblMain;
    @FXML
    private TableColumn<Day, String> colDate;
    @FXML
    private TableColumn<Day, Integer> colHours;
    @FXML
    private TableColumn<Day, String> colGuild;
    @FXML
    private JFXButton btnChangePassword;
    @FXML
    private Label lblOldPassword;
    @FXML
    private Label lblNewPassword;
    @FXML
    private Label lblNewPassword2;
    @FXML
    private JFXButton btnChangePWConfirm;
    @FXML
    private JFXButton btnCancel;

    private boolean isIncorrect;

    @FXML
    private StackPane stckPaneAddHours;
    @FXML
    private Label lblDateInPop;
    @FXML
    private JFXDatePicker datePickerInPop;
    @FXML
    private Label lblHoursInPop;
    @FXML
    private JFXButton btnIntDown;
    @FXML
    private JFXTextField txtfldHours;
    @FXML
    private JFXButton btnIntUp;
    @FXML
    private Label lblGuildInPop;
    @FXML
    private JFXComboBox<Guild> comboboxGuild;
    @FXML
    private HBox hBoxBtnsInPOP;
    @FXML
    private JFXButton btnAddHoursPOP;
    @FXML
    private JFXButton btnCancelPOP;
    @FXML
    private JFXButton btnAddHours;

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

                    MOD_FACADE.updateUserInfo(selectedUser.getId(), txtName.getText(), txtEmail.getText(), selectedUser.getType(), Integer.parseInt(txtPhone.getText()), txtNotes.getText(), txtAddress.getText(), txtAddress2.getText());
                    MOD_FACADE.setAllVolunteersIntoArray();
                    MOD_FACADE.setAllManagersIntoArray();
                    MOD_FACADE.setAllAdminsIntoArray();

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
                  
                    filteredData = new FilteredList<>(FXCollections.observableArrayList(MOD_FACADE.getWorkedDays(selectedUser)), p -> true);
                    firstRun = false;
                    return null;

                }
            };
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setText();
        listGuilds.setItems(FXCollections.observableArrayList(selectedUser.getGuildList()));
        setupTableView("Looking for data");

        serviceInitializer.start();
        setUserImage();
        serviceInitializer.setOnSucceeded(e
                -> setupTableView("Found Nothing :("));
//        if (selectedUser.getType() >= 1)
//        {
//            serviceAllVolunteers.start();
//        }
    }

    public void setUserImage()
    {
        if (MOD_FACADE.getUserImage(selectedUser) != null)
        {
            imgVwProfilePic.setImage(new Image(MOD_FACADE.getUserImage(selectedUser)));

        }
    }

    public void setController(ManagerViewController c)
    {
        this.mevController = c;
    }

    public void setText()
    {
        if (selectedUser != null)
        {
            txtName.setText(selectedUser.getName());
            txtAddress.setText(selectedUser.getResidence());
            txtAddress2.setText(selectedUser.getResidence2());
            txtPhone.setText(String.valueOf(selectedUser.getPhone()));
            txtEmail.setText(selectedUser.getEmail());
            txtNotes.setText(selectedUser.getNote());
        }
        else
        {
            System.out.println("selected user is null");
        }
    }

    @FXML
    private void onBtnUpdatePhotoPressed(ActionEvent event)
    {
        FileChooser c = new FileChooser();
        c.setTitle("Select a new image");
        String[] extensions
                =

                {
                    "jpg", "jpeg", "png", "gif"
                };
        c.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image files only", extensions));
        File newImg = c.showOpenDialog(JFXBtnUpdatePhoto.getScene().getWindow());

        if (newImg != null)
        {
            try
            {
                MOD_FACADE.updateUserImage(selectedUser, newImg);
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

    public static void setSelectedUser(User user)
    {
        selectedUser = user;
    }

    private void updateUserInfo()
    {
        StackPane stckPaneLoad = MOD_FACADE.getLoadingScreen();
        root.getChildren().add(stckPaneLoad);
        AnchorPane.setTopAnchor(stckPaneLoad, 0.0);
        AnchorPane.setBottomAnchor(stckPaneLoad, 0.0);
        AnchorPane.setLeftAnchor(stckPaneLoad, 0.0);
        AnchorPane.setRightAnchor(stckPaneLoad, 0.0);
        serviceAllVolunteers.restart();
        serviceAllVolunteers.setOnSucceeded(e
                -> 
                {
                    System.out.println("Updated info saved. ");
                    Stage stage = (Stage) JFXBtnAccept.getScene().getWindow();
                    stage.close();
        });
    }

    @FXML
    private void onBtnAcceptPressed(ActionEvent event)
    {
        updateUserInfo();

    }

    @FXML
    private void onBtnCancelPressed(ActionEvent event)
    {
        Stage stage = (Stage) JFXBtnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void changePasswordEvent(ActionEvent event)
    {
        int count;
        if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
        {
            count = MOD_FACADE.changePassword(selectedUser, txtOPassword.getText(), txtNPassword.getText());
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

    private void setupTableView(String str)
    {

        tblMain.setPlaceholder(new Label(str));
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

        sortedData.comparatorProperty().bind(tblMain.comparatorProperty());
        tblMain.setItems(sortedData);

    }

    @FXML
    private void checkTextFields(KeyEvent event)
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
            JFXBtnAccept.setDisable(true);
        }
        if (success)
        {
            JFXBtnAccept.setDisable(false);
            txtPhone.setStyle("");
            isIncorrect = false;
        }
        else
        {
            txtPhone.setStyle("-fx-background-color:red;");
            JFXBtnAccept.setDisable(true);
            isIncorrect = true;

        }
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

        tblMain.setContextMenu(popupContext);
        Day selectedDay = tblMain.getSelectionModel().getSelectedItem();

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

                MOD_FACADE.deleteWorkedDay(selectedUser, selectedDay);

                snackBarPopup("Contribution for " + selectedDay.getGuild() + " on the " + selectedDay.getDate() + " has been deleted.");
                serviceInitializer.restart();
            }

        };
        deleteDay.setOnAction(deleteDayEvent);

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
        StackPane stckPaneLoad = MOD_FACADE.getLoadingScreen();
        root.getChildren().add(stckPaneLoad);

        buttonsLocking(true);

        if (datePickerInPop.getValue() != null && !txtfldHours.getText().isEmpty() && !comboboxGuild.getSelectionModel().isEmpty())
        {

            String date = datePickerInPop.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int hours = Integer.parseInt(txtfldHours.getText());

            int guildID = comboboxGuild.getSelectionModel().getSelectedItem().getId();

            int errorCode = 1;

            if (selectedUser.getEmail() != null)
            {

                if (editPopup = true)
                {

                    errorCode = MOD_FACADE.logHours(selectedUser.getEmail(), date, hours, guildID);
                }
                else
                {

                    errorCode = MOD_FACADE.editHours(selectedUser.getEmail(), date, hours, guildID);
                }
                stckPaneLoad.setVisible(false);
                contributionSnackBarHandler(errorCode);

            }
            else if (selectedUser.getPhone() != 0)

            {
                if (editPopup = true)
                {
                    errorCode = MOD_FACADE.logHours(selectedUser.getPhone() + "", date, hours, guildID);
                }
                else
                {

                    errorCode = MOD_FACADE.editHours(selectedUser.getPhone() + "", date, hours, guildID);
                }
                stckPaneLoad.setVisible(false);
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
}
