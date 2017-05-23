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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javax.swing.JFileChooser;

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
    @FXML
    private Tab tabVolunInfo;
    @FXML
    private JFXCheckBox chkVolunteers;
    @FXML
    private JFXCheckBox chkManagers;
    @FXML
    private JFXCheckBox chkAdmins;
    @FXML
    private Tab tabGraphStats;
    @FXML
    private Tab tabGuildManagement;
    @FXML
    private AnchorPane anchorPaneGuild;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private AnchorPane rootGraph;
    @FXML
    private LineChart<Number, Number> lineChartGuildHours;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;

    Boolean hasLoadedGuild = false;
    ModelFacade modelFacade = ModelFacade.getModelFacade();
    User selectedUser;
    List<XYChart.Series<Number, Number>> Temp;
    List<User> filteredList = new ArrayList<>();
    private final Service serviceGraphStats = new Service()
    {
        @Override
        protected Task createTask()
        {
            return new Task()
            {
                @Override
                protected Object call() throws Exception
                {
                    Temp = modelFacade.graphSort(cmbGuildChooser.getSelectionModel().getSelectedItem());

                    return null;
                }
            };
        }
    };
    @FXML
    private StackPane stckPaneGraphError;

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
        setupTableView();

        if (modelFacade.getCurrentUser().getType() < 2)
        {
            chkAdmins.setVisible(false);
            chkManagers.setVisible(false);
            chkVolunteers.setVisible(false);
        }

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
            ObservableList<User> users = FXCollections.observableArrayList(modelFacade.getAllSavedVolunteers());
            chkManagers.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    if (chkManagers.isSelected())
                    {
                        users.addAll(FXCollections.observableArrayList(modelFacade.getAllSavedManagers()));
                    }
                    else
                    {
                        users.removeAll(FXCollections.observableArrayList(modelFacade.getAllSavedManagers()));
                    }
                    tblUsers.setItems(FXCollections.observableArrayList(users));
                }

            });
            chkVolunteers.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    if (chkVolunteers.isSelected())
                    {
                        users.addAll(FXCollections.observableArrayList(modelFacade.getAllSavedVolunteers()));
                    }
                    else
                    {
                        users.removeAll(FXCollections.observableArrayList(modelFacade.getAllSavedVolunteers()));
                    }
                    tblUsers.setItems(FXCollections.observableArrayList(users));
                }

            });
            tblUsers.setItems(FXCollections.observableArrayList(users));
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
                    modelFacade.setAllVolunteersIntoArray();
                    setTableItems();
                }
            });

            stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                public void handle(WindowEvent we)
                {
                    System.out.println("Stage is closing");
                    modelFacade.setAllVolunteersIntoArray();
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
                        modelFacade.setAllVolunteersIntoArray();
                        setTableItems();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        modelFacade.setAllVolunteersIntoArray();
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
                        modelFacade.setAllVolunteersIntoArray();
                        setTableItems();
                    }
                });

                stageView.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    public void handle(WindowEvent we)
                    {
                        System.out.println("Stage is closing");
                        modelFacade.setAllVolunteersIntoArray();
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
        MenuItem exportData = new MenuItem("Export users in table (except notes)");
        contextMenu.getItems().add(exportData);

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

        exportData.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                exportUsers();
            }

        });
    }

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

    private void exportUsers()
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("Comma separated files", new ArrayList<String>()
        {
            {
                add("*.csv");
            }
        }));
        chooser.setTitle("Choose where to export CSV file");
        chooser.setInitialDirectory(new File("."));
        File chose = chooser.showSaveDialog(root.getScene().getWindow());
        if (chose != null)
        {
            modelFacade.writeExport(chose, modelFacade.parseExportUsers(tblUsers.getItems()));
        }

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
        if (tabPane.getSelectionModel().getSelectedItem() == tabGuildManagement && !hasLoadedGuild)
        {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/GUI/View/GuildManagementView.fxml"));
            anchorPaneGuild.getChildren().add(newLoadedPane);
            hasLoadedGuild = true;
        }
        else if (tabPane.getSelectionModel().getSelectedItem() == tabGraphStats)
        {
            xAxis.setLabel("Month number");
            xAxis.setAutoRanging(false);
            xAxis.setLowerBound(1);
            xAxis.setUpperBound(12);
            xAxis.setTickUnit(1);
            if (cmbGuildChooser.getSelectionModel().getSelectedItem() != null)
            {
                stckPaneGraphError.setVisible(false);
                StackPane stckPaneLoad = modelFacade.getLoadingScreen();
                AnchorPane.setBottomAnchor(stckPaneLoad, 20.0);
                AnchorPane.setTopAnchor(stckPaneLoad, 0.0);
                AnchorPane.setLeftAnchor(stckPaneLoad, 0.0);
                AnchorPane.setRightAnchor(stckPaneLoad, 0.0);
                rootGraph.getChildren().add(stckPaneLoad);
                serviceGraphStats.start();
                serviceGraphStats.setOnSucceeded(e
                        -> 
                        {
                            for (XYChart.Series<Number, Number> series : Temp)
                            {
                                lineChartGuildHours.getData().add(series);
                            }
                            Calendar cal = Calendar.getInstance();
                            lineChartGuildHours.setTitle("Work contribution graph for " + cmbGuildChooser.getSelectionModel().getSelectedItem().getName() + " " + cal.get(Calendar.YEAR));
                            rootGraph.getChildren().remove(stckPaneLoad);
                });

            }
            else
            {
                stckPaneGraphError.setVisible(true);
            }
        }
    }

    @FXML
    private void onCheckBoxAction(ActionEvent event)
    {
        filteredList.clear();

        if (chkAdmins.selectedProperty().get() == false && chkManagers.selectedProperty().get() == false && chkVolunteers.selectedProperty().get() == false)
        {
            filteredList.clear();
            tblUsers.setItems(FXCollections.observableArrayList(filteredList));
        }

        if (chkAdmins.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllAdmins());
            tblUsers.setItems(FXCollections.observableArrayList(filteredList));
        }
        if (chkManagers.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllManagers());
            tblUsers.setItems(FXCollections.observableArrayList(filteredList));
        }
        if (chkVolunteers.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllVolunteers());
            tblUsers.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    private void setupTableView()
    {

        tblUsers.setPlaceholder(new Label("Nothing found :("));
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPhone.setCellValueFactory(val -> val.getValue().phoneProperty().asObject());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        FilteredList<User> filteredData = new FilteredList<>(FXCollections.observableArrayList(modelFacade.getAllUsers()), p -> true);

        txtSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue)
                -> 
                {
                    filteredData.setPredicate(user
                            -> 
                            {
                                String regex = "[^a-zA-Z0-9\\s]";
                                Boolean search
                                        = user.emailProperty().getValue().replaceAll(regex, "")
                                        .contains(newValue.replaceAll(regex, ""))
                                        || user.nameProperty().getValue().toLowerCase().replaceAll(regex, "").
                                        contains(newValue.toLowerCase().replaceAll(regex, ""));

                                return search;

                    });
        });

        SortedList<User> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tblUsers.comparatorProperty());
        tblUsers.setItems(sortedData);

    }
}
