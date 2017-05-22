package GUI.Controller;

import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ManagerViewController implements Initializable
{

    @FXML
    private Label lblUserName;
    @FXML
    private JFXButton btnAddUser;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXButton btnAddHours;
    @FXML
    private JFXButton btnEditInfo;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnClose;
    @FXML
    private TableView<User> tblUsers;
    @FXML
    private JFXComboBox<Guild> cmbGuildChooser;
    @FXML
    private Label lblNotes;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, Integer> colPhone;
    @FXML
    private TableColumn<User, String> colEmail;
    ModelFacade modelFacade = ModelFacade.getModelFacade();
    User selectedUser;
    @FXML
    private Tab tabVolunInfo;
    @FXML
    private JFXCheckBox chkVolunteers;
    @FXML
    private JFXCheckBox chkManagers;
    @FXML
    private Tab tabGraphStats;
    @FXML
    private Tab tabGuildManagement;
    @FXML
    private AnchorPane anchorPaneGuild;
    @FXML
    private JFXTabPane tabPane;
    
    Boolean hasLoadedGuild= false ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setTextAll(); //this has to run before setting currently logged in username
        if (modelFacade.getCurrentUser() != null)
        {
            lblUserName.setText(modelFacade.getLang("LBL_USERNAME") + modelFacade.getCurrentUser().getName());
            cmbGuildChooser.setItems(FXCollections.observableArrayList(modelFacade.getCurrentUser().getGuildList()));
        }
        setTableProperties();
        setTableItems();
    }

    private void setTableProperties()
    {
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
    }

    public void setTableItems()
    {
        if (modelFacade.getCurrentUser().getType() == 1)
        {
            tblUsers.setItems(FXCollections.observableArrayList(modelFacade.getAllSavedVolunteers()));
        }
        if (modelFacade.getCurrentUser().getType() == 2)

        {
            tblUsers.setItems(FXCollections.observableArrayList(modelFacade.getAllUsers()));
        }
    }

    @FXML
    private void onBtnAddUserClicked(ActionEvent event)
    {
        addUserPopup();
    }

    public void addUserPopup()
    {
        selectedUser = null;

        try
        {
            Stage primStage = (Stage) tblUsers.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/ManagerAddUserView.fxml"));

            //ManagerViewController.setSelectedUser(selectedUser);
            Parent root = loader.load();

            // Fetches controller from view
            //ManagerViewController controller = loader.getController();
            //controller.setController(this);
            // Sets new stage as modal window
            Stage stageView = new Stage();
            stageView.setScene(new Scene(root));

            stageView.setOnHiding(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    System.out.println("Stage on Hiding");

                    setTableItems();
                }
            });

            stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    System.out.println("Stage is closing");
                    setTableItems();
                }
            });

            stageView.initModality(Modality.WINDOW_MODAL);
            stageView.initOwner(primStage);

            stageView.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    @FXML
    private void onEditInfoPressed(ActionEvent event)
    {
        selectedUser = null;

        if (tblUsers.getSelectionModel().getSelectedItem() != null)
        {
            try
            {
                selectedUser = tblUsers.getSelectionModel().getSelectedItem();
                Stage primStage = (Stage) tblUsers.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/ManagerEditView.fxml"));
                ManagerEditViewController.setSelectedUser(selectedUser);

                Parent root = loader.load();

                // Fetches controller from view
                ManagerEditViewController controller = loader.getController();
                controller.setController(this);

                // Sets new stage as modal window
                Stage stageView = new Stage();
                stageView.setScene(new Scene(root));

                stageView.setOnHiding(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {

                        setTableItems();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {

                        setTableItems();
                    }
                });

                stageView.initModality(Modality.WINDOW_MODAL);
                stageView.initOwner(primStage);

                stageView.show();
            }
            catch (Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        else
        {
            snackBarPopup("You need to select a user first.");
            System.out.println("Selected user missing");
        }
    }

    @FXML
    private void onTablePressed(MouseEvent event)
    {
        selectedUser = null;

        if (event.isPrimaryButtonDown() && event.getClickCount() == 1)
        {
            selectedUser = tblUsers.getSelectionModel().getSelectedItem();

            txtNotes.setText(selectedUser.getNote());
        }
        else if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            try
            {
                selectedUser = tblUsers.getSelectionModel().getSelectedItem();
                Stage primStage = (Stage) tblUsers.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/View/ManagerEditView.fxml"));

                ManagerEditViewController.setSelectedUser(selectedUser);

                Parent root = loader.load();

                // Fetches controller from view
                ManagerEditViewController controller = loader.getController();
                controller.setController(this);

                // Sets new stage as modal window
                Stage stageView = new Stage();
                stageView.setScene(new Scene(root));

                stageView.setOnHiding(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        System.out.println("Stage on Hiding");
                        setTableItems();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        System.out.println("Stage is closing");
                        setTableItems();
                    }
                });

                stageView.initModality(Modality.WINDOW_MODAL);
                stageView.initOwner(primStage);

                stageView.show();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        selectedUser = tblUsers.getSelectionModel().getSelectedItem();

        ContextMenu contextMenu = new ContextMenu();
        MenuItem thisEmailItem = new MenuItem("Copy this email to clipboard");
        contextMenu.getItems().add(thisEmailItem);
        MenuItem allEmailItem = new MenuItem("Copy all emails to clipboard");
        contextMenu.getItems().add(allEmailItem);

        tblUsers.setContextMenu(contextMenu);

        EventHandler thisEmailEvent = new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(selectedUser.getEmail());
                clipboard.setContent(content);
                System.out.println("This email to clipboard");
            }
        };
        thisEmailItem.setOnAction(thisEmailEvent);

        EventHandler allEmailEvent = new EventHandler()
        {
            @Override
            public void handle(Event event)
            {
                //TableColumn<User, String> column = colEmail;

                List<String> columnData = new ArrayList<>();
                for (User item : tblUsers.getItems())
                {
                    columnData.add(colEmail.getCellObservableValue(item).getValue());
                    System.out.println(columnData);
                }
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(columnData.toString());
                clipboard.setContent(content);
                System.out.println("All Emails to Clipboard");
            }
        };

        allEmailItem.setOnAction(allEmailEvent);
    }

    @FXML
    private void onBtnClosePressed(ActionEvent event)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void snackBarPopup(String str)
    {
        int time = 3000;
        JFXSnackbar snackbar = new JFXSnackbar(root);
        snackbar.show(str, time);
        PauseTransition pause = new PauseTransition(Duration.millis(time));
        pause.play();

    }

    private void setTextAll()
    {

        btnAddHours.setText(modelFacade.getLang("BTN_ADD_HOURS"));
        btnAddUser.setText(modelFacade.getLang("BTN_ADD_USER"));
        btnClose.setText(modelFacade.getLang("BTN_CLOSE"));
        btnEditInfo.setText(modelFacade.getLang("BTN_EDIT_INFO"));
        chkManagers.setText(modelFacade.getLang("CHK_MANAGERS"));
        chkVolunteers.setText(modelFacade.getLang("CHK_VOLUNTEERS"));

        lblUserName.setText(modelFacade.getLang("LBL_USERNAME"));
        lblNotes.setText(modelFacade.getLang("LBL_NOTES"));
        txtSearch.setPromptText(modelFacade.getLang("PROMPT_SEARCH_USER"));
        cmbGuildChooser.setPromptText(modelFacade.getLang("PROMPT_CMB_GUILDCHOOSER"));
        colEmail.setText(modelFacade.getLang("COL_EMAIL"));
        colPhone.setText(modelFacade.getLang("COL_PHONE"));
        colName.setText(modelFacade.getLang("COL_NAME"));
        tabVolunInfo.setText(modelFacade.getLang("TAB_VOLUN_INFO"));
        tabGraphStats.setText(modelFacade.getLang("TAB_GRAPH_STATS"));
    }

    @FXML
    private void loadGuildView(Event event) throws IOException
    {
        if(tabPane.getSelectionModel().getSelectedItem() == tabGuildManagement && !hasLoadedGuild)
        {
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/GUI/View/GuildManagementView.fxml"));           
            anchorPaneGuild.getChildren().add(newLoadedPane);
            hasLoadedGuild = true;
        }
        System.out.println(tabPane.getSelectionModel().getSelectedItem().getId());
    }
}