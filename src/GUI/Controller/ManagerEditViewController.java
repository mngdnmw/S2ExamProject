package GUI.Controller;

import BE.Day;
import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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

        serviceInitializer.setOnSucceeded(e
                -> setupTableView("Found Nothing :("));
        if (selectedUser.getType() >= 1)
        {
            serviceAllVolunteers.start();
        }
    }
    
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
        newImg = c.showOpenDialog(JFXBtnUpdatePhoto.getScene().getWindow());
        serviceSavePicture.start();
        serviceSavePicture.setOnSucceeded(e
                ->
        {
            firstRun = true;
            serviceInitializer.restart();
        });

    }

    public static void setSelectedUser(User user)
    {
        selectedUser = user;
    }

    private void updateUserInfo()
    {
        MOD_FACADE.updateUserInfo(selectedUser.getId(), txtName.getText(), txtEmail.getText(), selectedUser.getType(), Integer.parseInt(txtPhone.getText()), txtNotes.getText(), txtAddress.getText(), txtAddress2.getText());
    }

    @FXML
    private void onBtnAcceptPressed(ActionEvent event)
    {
        updateUserInfo();

        System.out.println("Updated info saved. ");
        Stage stage = (Stage) JFXBtnAccept.getScene().getWindow();
        stage.close();
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
}
