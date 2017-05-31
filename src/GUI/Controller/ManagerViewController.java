package GUI.Controller;

import BE.EnumCache.ExportType;
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
import java.util.Comparator;
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
import javafx.scene.chart.CategoryAxis;
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
    private CategoryAxis xAxis;

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

    private Boolean hasLoadedGuild = false;
    private static final ModelFacade MOD_FAC = ModelFacade.getModelFacade();
    private User selectedUser;
    private ArrayList<XYChart.Series<String, Number>> Temp = new ArrayList<>();
    private ObservableList<User> observableUsers = FXCollections.observableArrayList();
    private FilteredList<User> filteredData = new FilteredList<>(observableUsers);
    private SortedList<User> sortedData = new SortedList<>(filteredData);
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
                            List<XYChart.Series<String, Number>> thisList = MOD_FAC.graphSort(item, periodOne, periodTwo);
                            for (XYChart.Series<String, Number> series : thisList)
                            {
                                Temp.add(series);
                            }
                        }
                    }
                    else
                    {
                        List<XYChart.Series<String, Number>> thisList = MOD_FAC.graphSort(cmbGuildChooser.getSelectionModel().getSelectedItem(), periodOne, periodTwo);
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

                    MOD_FAC.setAllAdminsIntoArray();
                    MOD_FAC.setAllManagersIntoArray();
                    MOD_FAC.setAllVolunteersIntoArray();
                    setTableItems();
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
        xAxis.setLabel(MOD_FAC.getLang("TAB_MONTH"));
        yAxis.setLabel(MOD_FAC.getLang("STR_AXIS_HOURS"));
        setTextAll(); //this has to run before setting currently logged in username
        if (MOD_FAC.getCurrentUser() != null)
        {
            lblUserName.setText(MOD_FAC.getLang("LBL_USERNAME") + MOD_FAC.getCurrentUser().getName());
            cmbGuildChooser.setItems(FXCollections.observableArrayList(MOD_FAC.getCurrentUser().getGuildList()));
        }
        setTableProperties();
        setTableItems();
        setupTableView(MOD_FAC.getLang("TBL_LOADING"));
        serviceInitializer.start();
        serviceInitializer.setOnSucceeded(e -> setupTableView(MOD_FAC.getLang("TBL_NO_DATA")));
        cmbBoxListeners();
        if (MOD_FAC.getCurrentUser().getType() >= 2)
        {
            chkAdmins.setVisible(true);
            chkManagers.setVisible(true);
            chkVolunteers.setVisible(true);
            ObservableList guildList = FXCollections.observableArrayList();
            guildList.add(new Guild(-1, MOD_FAC.getLang("GUILD_ALL")));
            guildList.addAll(MOD_FAC.getAllSavedGuilds());

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
        observableUsers.clear();

        if (MOD_FAC.getCurrentUser().getType() == 1)
        {
            observableUsers.addAll(MOD_FAC.getAllSavedVolunteers());
        }
        if (MOD_FAC.getCurrentUser().getType() == 2)
        {

            observableUsers.addAll(MOD_FAC.getAllSavedUsers());
        }
        
        tblLog.setItems(FXCollections.observableArrayList(MOD_FAC.getAllEvents()));
        colLogEventId.setSortType(TableColumn.SortType.ASCENDING);
        tblLog.getSortOrder().add(colLogEventId);
        //tblLog.setSortPolicy(callback);getSortPolicy();
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
                            String currentValue = txtSearch.getText();
                            Boolean search = false;

                            String regex = "[^a-zA-Z0-9\\s]";
                            Boolean textFieldContains = user.emailProperty().getValue().replaceAll(regex, "")
                                    .contains(currentValue.replaceAll(regex, ""))
                                    || user.nameProperty().getValue().toLowerCase().replaceAll(regex, "").
                                    contains(currentValue.toLowerCase().replaceAll(regex, ""));
                            if (cmbGuildChooser.getSelectionModel().getSelectedItem().getId() == -1)
                            {
                                if (textFieldContains)
                                {
                                    search = true;
                                }
                            }
                            else
                            {
                                for (Guild guild : user.getGuildList())
                                {
                                    if (guild.getId() == cmbGuildChooser.getSelectionModel().getSelectedItem().getId())
                                    {
                                        if (textFieldContains)
                                        {
                                            search = true;
                                        }
                                    }
                                }
                            }

                            return search;

                });
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
            MOD_FAC.snackbarPopup(MOD_FAC.getLang("SNACK_SELECT_USER_FIRST"), root);
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
        MenuItem thisEmailItem = new MenuItem(MOD_FAC.getLang("MENU_COPY_SINGLE"));
        contextMenu.getItems().add(thisEmailItem);
        MenuItem allEmailItem = new MenuItem(MOD_FAC.getLang("MENU_COPY_ALL"));
        contextMenu.getItems().add(allEmailItem);
        MenuItem exportData = new MenuItem(MOD_FAC.getLang("MENU_EXPORT"));
        contextMenu.getItems().add(exportData);
        MenuItem exportHours = new MenuItem("Export user hours from table");
        contextMenu.getItems().add(exportHours);

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
                export(ExportType.DATA);
            }

        });
        
        exportHours.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                export(ExportType.HOURS);
            }
            
        });
    }

    @FXML
    private void onBtnClosePressed(ActionEvent event)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
    private void export(ExportType type)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter(MOD_FAC.getLang("CSV_CH_EXT_FILTER"), new ArrayList<String>()
        {
            {
                add("*.csv");
            }
        }));
        chooser.setTitle(MOD_FAC.getLang("CSV_CH_TITLE"));
        chooser.setInitialDirectory(new File("."));
        File chose = chooser.showSaveDialog(root.getScene().getWindow());
        if (chose != null)
        {
            if(type.equals(ExportType.DATA)) {
                MOD_FAC.writeExport(chose, MOD_FAC.parseExportUsers(tblUsers.getItems()));
            } else if(type.equals(ExportType.HOURS)) {
                MOD_FAC.writeExport(chose, MOD_FAC.parseExportHours(tblUsers.getItems()));
            }
        }
    }

    private void setTextAll()
    {
        btnAddUser.setText(MOD_FAC.getLang("BTN_ADD_USER"));
        btnClose.setText(MOD_FAC.getLang("BTN_CLOSE"));
        btnEditInfo.setText(MOD_FAC.getLang("BTN_EDIT_INFO"));
        chkManagers.setText(MOD_FAC.getLang("CHK_MANAGERS"));
        chkVolunteers.setText(MOD_FAC.getLang("CHK_VOLUNTEERS"));

        lblUserName.setText(MOD_FAC.getLang("LBL_USERNAME"));
        lblNotes.setText(MOD_FAC.getLang("LBL_NOTES"));
        txtSearch.setPromptText(MOD_FAC.getLang("PROMPT_SEARCH_USER"));
        cmbGuildChooser.setPromptText(MOD_FAC.getLang("PROMPT_CMB_GUILDCHOOSER"));
        colEmail.setText(MOD_FAC.getLang("COL_EMAIL"));
        colPhone.setText(MOD_FAC.getLang("COL_PHONE"));
        colName.setText(MOD_FAC.getLang("COL_NAME"));
        tabVolunInfo.setText(MOD_FAC.getLang("TAB_VOLUN_INFO"));
        tabGraphStats.setText(MOD_FAC.getLang("TAB_GRAPH_STATS"));
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
        observableUsers.clear();

        if (chkAdmins.selectedProperty().get() == false && chkManagers.selectedProperty().get() == false && chkVolunteers.selectedProperty().get() == false)
        {
            observableUsers.addAll(MOD_FAC.getAllSavedUsers());
        }

        if (chkAdmins.selectedProperty().get() == true)
        {
            observableUsers.addAll(MOD_FAC.getAllSavedAdmins());
        }
        if (chkManagers.selectedProperty().get() == true)
        {
            observableUsers.addAll(MOD_FAC.getAllSavedManagers());
        }
        if (chkVolunteers.selectedProperty().get() == true)
        {
            observableUsers.addAll(MOD_FAC.getAllSavedVolunteers());
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

                                if (cmbGuildChooser.getSelectionModel().getSelectedItem().getId() == -1)
                                {
                                    return search;
                                }
                                else
                                {
                                    for (Guild guild : user.getGuildList())
                                    {
                                        if (guild.getId() == cmbGuildChooser.getSelectionModel().getSelectedItem().getId())
                                        {
                                            return search;
                                        }
                                    }
                                }

                                return false;
                    });
        });

        sortedData.comparatorProperty().bind(tblUsers.comparatorProperty());
        tblUsers.setItems(sortedData);

    }

    @FXML
    private void updateLogTable(ActionEvent event) {
        tblLog.setItems(FXCollections.observableArrayList(MOD_FAC.getAllEvents()));
        colLogEventId.setSortType(TableColumn.SortType.ASCENDING);
        tblLog.getSortOrder().clear();
        tblLog.getSortOrder().add(colLogEventId);
    }
    @FXML
    private void refreshGraph(ActionEvent event)
    {
        Temp.clear();
        lineChartGuildHours.getData().clear();

        if (cmbGuildChooser.getSelectionModel().getSelectedItem() != null)
        {
            stckPaneGraphError.setVisible(false);
            StackPane stckPaneLoad = MOD_FAC.getLoadingScreen();
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
                        lineChartGuildHours.setTitle(MOD_FAC.getLang("CHART_TITLE") + cmbGuildChooser.getSelectionModel().getSelectedItem().getName() + " " + cal.get(Calendar.YEAR));
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
