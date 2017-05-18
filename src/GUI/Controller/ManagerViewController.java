package GUI.Controller;

import BE.Day;
import BE.Guild;

import BE.User;
import GUI.Model.ModelFacade;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
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
    private JFXButton btnStats;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXTreeTableView<User> tblUsers;

    ModelFacade modelFacade = ModelFacade.getModelFacade();
    User selectedUser;
    @FXML
    private JFXComboBox<?> cmbGuildChooser;
    @FXML
    private Label lblNotes;

    JFXTreeTableColumn<User, String> colName = new JFXTreeTableColumn<>();
    JFXTreeTableColumn<User, Integer> colPhone = new JFXTreeTableColumn<>();
    JFXTreeTableColumn<User, String> colEmail = new JFXTreeTableColumn<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //setTableProperties();
        setTextAll(); //this has to run before setting currently logged in username
        if (modelFacade.getCurrentUser() != null)
        {
            lblUserName.setText(modelFacade.getLang("LBL_USERNAME") + modelFacade.getCurrentUser().getName());
        }

        showTreeTable();
    }

    /**
     * Initialises the tree table containing information about the User
     */
    private void showTreeTable()
    {
        //Need to do some threading for this method

        //Name column set up
        colName.prefWidthProperty().bind(tblUsers.widthProperty().divide(3));
        colName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>()
        {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param)
            {
                return param.getValue().getValue().nameProperty();
            }
        });

        //Phone column set up
        colPhone.prefWidthProperty().bind(tblUsers.widthProperty().divide(3));
        colPhone.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, Integer> param) -> param.getValue().getValue().phoneProperty().asObject());

        //Email column set up
        colEmail.prefWidthProperty().bind(tblUsers.widthProperty().divide(3));
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<User, String> param) -> param.getValue().getValue().emailProperty());

        /*//Guild column set up
        JFXTreeTableColumn<Guild, String> colGuild = new JFXTreeTableColumn<>("Guild");
        colGuild.prefWidthProperty().bind(tblUsers.widthProperty().divide(3));
        colGuild.setCellValueFactory((TreeTableColumn.CellDataFeatures<Guild, String> param) -> param.getValue().getValue().getName());*/
        tblUsers.setPlaceholder(new Label("Nothing found"));

        ObservableList<User> volunteers = FXCollections.observableArrayList(modelFacade.getAllSavedVolunteers());

        final TreeItem<User> rootOfTree = new RecursiveTreeItem<>(volunteers, RecursiveTreeObject::getChildren);

        colName.getStyleClass().add("col");
        colPhone.getStyleClass().add("col");
        colEmail.getStyleClass().add("col");

        tblUsers.getColumns().setAll(colName, colPhone, colEmail);
        tblUsers.setRoot(rootOfTree);
        tblUsers.setShowRoot(false);

        txtSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                ->
        {
            tblUsers.setPredicate((TreeItem<User> user)
                    ->
            {
                String regex = "[^a-zA-Z0-9\\s]";
                Boolean search
                        = user.getValue().nameProperty().getValue().toLowerCase().replaceAll(regex, "")
                                .contains(newValue.toLowerCase().replaceAll(regex, ""))
                        || user.getValue().emailProperty().getValue().toLowerCase().replaceAll(regex, "").
                                contains(newValue.toLowerCase().replaceAll(regex, ""))
                        || user.getValue().phoneProperty().getValue().toString().replaceAll(regex, "")
                                .contains(newValue.toLowerCase().replaceAll(regex, ""));

                return search;
            });
        });
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

                    showTreeTable();
                }
            });

            stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    System.out.println("Stage is closing");
                    //setTableItems();
                    showTreeTable();

                }
            });

            stageView.initModality(Modality.WINDOW_MODAL);
            stageView.initOwner(primStage);

            stageView.show();
        } catch (Exception e)
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
                selectedUser = tblUsers.getSelectionModel().getSelectedItem().getValue();
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
                        //setTableItems();
                        showTreeTable();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        System.out.println("Stage is closing");
                        //setTableItems();
                        showTreeTable();
                    }
                });

                stageView.initModality(Modality.WINDOW_MODAL);
                stageView.initOwner(primStage);

                stageView.show();
            } catch (Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        } else
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
            selectedUser = tblUsers.getSelectionModel().getSelectedItem().getValue();

            txtNotes.setText(selectedUser.getNote());
        } else if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            try
            {
                selectedUser = tblUsers.getSelectionModel().getSelectedItem().getValue();
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
                        //setTableItems();
                        showTreeTable();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        System.out.println("Stage is closing");
                        //setTableItems();
                        showTreeTable();
                    }
                });

                stageView.initModality(Modality.WINDOW_MODAL);
                stageView.initOwner(primStage);

                stageView.show();
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
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
        btnStats.setText(modelFacade.getLang("BTN_STATS"));

        lblUserName.setText(modelFacade.getLang("LBL_USERNAME"));
        lblNotes.setText(modelFacade.getLang("LBL_NOTES"));
        txtSearch.setPromptText(modelFacade.getLang("PROMPT_SEARCH_USER"));
        cmbGuildChooser.setPromptText(modelFacade.getLang("PROMPT_SEARCH_USER"));
        colEmail.setText(modelFacade.getLang("COL_EMAIL"));
        colPhone.setText(modelFacade.getLang("COL_PHONE"));
        colName.setText(modelFacade.getLang("COL_NAME"));
    }
}
