package GUI.Controller;

import BE.Guild;
import BE.User;
import GUI.Model.AutoCompleteComboBoxListener;
import GUI.Model.ModelFacade;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

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
    private JFXButton btnAddHours;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private LineChart<String, Number> lineChartGuildHours;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private ValueAxis xAxis;

    @FXML
    private Tab tabLog;
    @FXML
    private TableView<BE.Event> tblLog;
    @FXML
    private TableColumn<BE.Event, String> colLogEventId;
    @FXML
    private TableColumn<BE.Event, String> colLogEventDate;
    @FXML
    private TableColumn<BE.Event, String> colLogEventDesc;

    Boolean hasLoadedGuild = false;
    ModelFacade modelFacade = ModelFacade.getModelFacade();
    User selectedUser;
    ArrayList<XYChart.Series<String, Number>> Temp = new ArrayList<>();
    ObservableList<User> filteredList = FXCollections.observableArrayList();
    FilteredList<User> filteredData = new FilteredList<>(filteredList);
    @FXML
    private StackPane stckPaneGraphError;
    @FXML
    private JFXDatePicker datePickerPeriodOne;
    @FXML
    private JFXDatePicker datePickerPeriodTwo;
    @FXML
    private JFXButton btnRefreshLog;

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
                    LocalDate periodOne = datePickerPeriodOne.getValue();
                    LocalDate periodTwo = datePickerPeriodTwo.getValue();
                    if (periodOne.getYear() <= periodTwo.getYear())
                    {
                        if (periodOne.getMonth().getValue() <= periodTwo.getMonth().getValue())
                        {
                        }
                    }
                    if (cmbGuildChooser.getSelectionModel().getSelectedItem().getId() == -1)
                    {
                        for (Guild item : cmbGuildChooser.getItems())
                        {
                            List<XYChart.Series<String, Number>> thisList = modelFacade.graphSort(item, periodOne, periodTwo);
                            for (XYChart.Series<String, Number> series : thisList)
                            {
                                Temp.add(series);
                            }
                        }
                    }
                    else
                    {
                        List<XYChart.Series<String, Number>> thisList = modelFacade.graphSort(cmbGuildChooser.getSelectionModel().getSelectedItem(), periodOne, periodTwo);
                        for (XYChart.Series<String, Number> series : thisList)
                        {
                            Temp.add(series);
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

                    modelFacade.setAllAdminsIntoArray();
                    modelFacade.setAllManagersIntoArray();
                    modelFacade.setAllVolunteersIntoArray();
                    filteredList.addAll(modelFacade.getAllSavedUsers());
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
        setTextAll(); //this has to run before setting currently logged in username
        if (modelFacade.getCurrentUser() != null)
        {
            lblUserName.setText(modelFacade.getLang("LBL_USERNAME") + modelFacade.getCurrentUser().getName());
            cmbGuildChooser.setItems(FXCollections.observableArrayList(modelFacade.getCurrentUser().getGuildList()));
        }
        setTableProperties();
        setTableItems();
        setupTableView("Loading Information");
        serviceInitializer.start();
        serviceInitializer.setOnSucceeded(e -> setupTableView("No Data :("));
        cmbBoxListeners();
        if (modelFacade.getCurrentUser().getType() >= 2)
        {
            chkAdmins.setVisible(true);
            chkManagers.setVisible(true);
            chkVolunteers.setVisible(true);
            ObservableList guildList = FXCollections.observableArrayList();
            guildList.add(new Guild(-1, "All Guilds"));
            guildList.addAll(modelFacade.getAllSavedGuilds());

            cmbGuildChooser.setItems(guildList);

            cmbGuildChooser.setEditable(true);
            new AutoCompleteComboBoxListener(cmbGuildChooser);
            formatCalendar(datePickerPeriodOne);
            formatCalendar(datePickerPeriodTwo);
        }
        cmbGuildChooser.getSelectionModel().selectFirst();

    }

    private void formatCalendar(DatePicker datePicker)
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
        datePicker.setConverter(converter);

        // Create a day cell factory
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>()
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

    private void setTableProperties()
    {
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));

        colLogEventId.setCellValueFactory(new PropertyValueFactory("id"));
        colLogEventDate.setCellValueFactory(new PropertyValueFactory("time"));
        colLogEventDesc.setCellValueFactory(new PropertyValueFactory("description"));
    }

    public void setTableItems()
    {
        filteredList.clear();

        if (modelFacade.getCurrentUser().getType() == 1)
        {
            filteredList.addAll(modelFacade.getAllSavedVolunteers());
        }
        if (modelFacade.getCurrentUser().getType() == 2)
        {

            filteredList.addAll(modelFacade.getAllSavedUsers());
        }

        tblLog.setItems(FXCollections.observableArrayList(modelFacade.getAllEvents()));
    }

    @FXML
    private void onBtnAddUserClicked(ActionEvent event)
    {
        addUserPopup();
    }

    public void cmbBoxListeners()
    {
        cmbGuildChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                cmbGuildChooser.getSelectionModel().select(new_value.intValue());
                filteredData.setPredicate(user
                        -> 
                        {
                            Boolean search = false;
                            if (cmbGuildChooser.getSelectionModel().getSelectedItem().getId() == -1)
                            {
                                search = true;
                            }
                            else
                            {
                                for (Guild guild : user.getGuildList())
                                {
                                    if (guild.getId() == cmbGuildChooser.getSelectionModel().getSelectedItem().getId())
                                    {
                                        search = true;
                                    }
                                }
                            }
                            return search;

                });
                SortedList<User> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(tblUsers.comparatorProperty());
                tblUsers.setItems(sortedData);
            }

        });
        cmbGuildChooser.setConverter(new StringConverter<Guild>()
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
                for (Guild guild : cmbGuildChooser.getItems())
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
                if (selectedUser.getEmail().contains("@"))
                {
                    content.putString(selectedUser.getEmail());
                }

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
                    String stringToAdd = colEmail.getCellObservableValue(item).getValue();
                    if (stringToAdd.contains("@"))
                    {
                        columnData.add(colEmail.getCellObservableValue(item).getValue());
                    }
                }
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                String columnDataString = columnData.toString().replaceAll("[\\[\\](){}]", "");
                content.putString(columnDataString);
                clipboard.setContent(content);
                System.out.println(columnDataString);
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

    @FXML
    private void onBtnClosePressed(ActionEvent event)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void snackBarPopup(String str)
    {
        int time = 6000;
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

        }
    }

    @FXML
    private void onCheckBoxAction(ActionEvent event)
    {
        filteredList.clear();

        if (chkAdmins.selectedProperty().get() == false && chkManagers.selectedProperty().get() == false && chkVolunteers.selectedProperty().get() == false)
        {
            filteredList.addAll(modelFacade.getAllSavedUsers());
        }

        if (chkAdmins.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllSavedAdmins());
        }
        if (chkManagers.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllSavedManagers());
        }
        if (chkVolunteers.selectedProperty().get() == true)
        {
            filteredList.addAll(modelFacade.getAllSavedVolunteers());
        }
    }

    private void setupTableView(String str)
    {

        tblUsers.setPlaceholder(new Label(str));
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colPhone.setCellValueFactory(val -> val.getValue().phoneProperty().asObject());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

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

    @FXML
    private void updateLogTable(ActionEvent event)
    {
        tblLog.setItems(FXCollections.observableArrayList(modelFacade.getAllEvents()));
    }

    @FXML
    private void refreshGraph(ActionEvent event)
    {
        Temp.clear();
        lineChartGuildHours.getData().clear();
        xAxis.setLabel("Month");

        if (cmbGuildChooser.getSelectionModel().getSelectedItem() != null)
        {
            stckPaneGraphError.setVisible(false);
            StackPane stckPaneLoad = modelFacade.getLoadingScreen();
            AnchorPane.setBottomAnchor(stckPaneLoad, 20.0);
            AnchorPane.setTopAnchor(stckPaneLoad, 0.0);
            AnchorPane.setLeftAnchor(stckPaneLoad, 0.0);
            AnchorPane.setRightAnchor(stckPaneLoad, 0.0);
            rootGraph.getChildren().add(stckPaneLoad);
            serviceGraphStats.restart();
            serviceGraphStats.setOnSucceeded(e
                    -> 
                    {
                        for (XYChart.Series<String, Number> series : Temp)
                        {
                            lineChartGuildHours.getData().add(series);
                        }
                        Calendar cal = Calendar.getInstance();
                        lineChartGuildHours.setTitle("Work contribution graph for " + cmbGuildChooser.getSelectionModel().getSelectedItem().getName() + " " + cal.get(Calendar.YEAR));
                        stckPaneLoad.setVisible(false);
            });
            serviceGraphStats.setOnFailed(e -> stckPaneLoad.setVisible(false));

        }
        else
        {
            stckPaneGraphError.setVisible(true);
        }
    }
}
