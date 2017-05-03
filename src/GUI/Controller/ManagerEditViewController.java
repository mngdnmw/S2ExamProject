package GUI.Controller;

import BE.User;
import GUI.Model.GeneralInfoModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ManagerEditViewController implements Initializable
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
    private JFXButton btnLogOut;
    @FXML
    private TableView<User> tblVolunteers;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, Integer> colPhone;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colGuild;

    
    GeneralInfoModel dataModel = new GeneralInfoModel();
    User selectedUser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setTableProperties();
        tblVolunteers.setItems(FXCollections.observableArrayList(dataModel.getAllUsers()));
        //System.out.println(dataModel.getAllUsers().toString());

    }

    /**
     * Sets table's properties
     */
    private void setTableProperties()
    {
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colGuild.setCellValueFactory(new PropertyValueFactory("guildId"));
    }

    @FXML
    private void onBtnAddUserClicked(ActionEvent event)
    {
        //dataModel.addUser("IT", "FUCKING", "WORKS", 0, 48615, "EEEYYYYY");
        addUserPopup();
    }

    public void addUserPopup()
    {
        selectedUser = null;
        
        try
        {
            Stage primStage = (Stage) tblVolunteers.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/ManagerView.fxml"));
            
            ManagerViewController.setSelectedUser(selectedUser);
            
            Parent root = loader.load();
            
            // Fetches controller from view
            ManagerViewController controller = loader.getController();
            
            //controller.setController(this);
            controller.setController(this);
            // Sets new stage as modal window
            Stage stageView = new Stage();
            stageView.setScene(new Scene(root));
            
            stageView.setOnHiding(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Stage on Hiding");
                        setTableItems();
                    }
                });
            
            stageView.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
              System.out.println("Stage is closing");
              setTableItems();
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
 
        if(tblVolunteers.getSelectionModel().getSelectedItem() != null)
        {
            try
            {
                
                
                selectedUser = tblVolunteers.getSelectionModel().getSelectedItem();
                Stage primStage = (Stage) tblVolunteers.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/ManagerView.fxml"));
                System.out.println("Selected User: " + selectedUser.getName());
                ManagerViewController.setSelectedUser(selectedUser);

                Parent root = loader.load();

                // Fetches controller from view
                ManagerViewController controller = loader.getController();
                controller.setController(this);
                //controller.setTableItems(selected.getClassId());
                //controller.setLabels(selected.getClassId(), selected.getSubject());
                //controller.setDateText();
                //controller.setCurrentTeacher(currentTeacher);
                // Sets new stage as modal window
                Stage stageView = new Stage();
                stageView.setScene(new Scene(root));

                stageView.setOnHiding(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Stage on Hiding");
                        setTableItems();
                    }
                });
                
                stageView.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                  System.out.println("Stage is closing");
                  setTableItems();
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
        }
        else
        {
            System.out.println("Selected user missing");
        }
    }

    @FXML
    private void onTablePressed(MouseEvent event)
    {
        selectedUser = null;

        if (event.isPrimaryButtonDown() && event.getClickCount() == 1)
        {
            selectedUser = tblVolunteers.getSelectionModel().getSelectedItem();

            System.out.println("Selected User: " + selectedUser.getName());
            
            txtNotes.setText(selectedUser.getNote());
        } else if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            try
            {
                selectedUser = tblVolunteers.getSelectionModel().getSelectedItem();
                Stage primStage = (Stage) tblVolunteers.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/ManagerView.fxml"));

                ManagerViewController.setSelectedUser(selectedUser);

                Parent root = loader.load();

                // Fetches controller from view
                ManagerViewController controller = loader.getController();
                controller.setController(this);
                //controller.setTableItems(selected.getClassId());
                //controller.setLabels(selected.getClassId(), selected.getSubject());
                //controller.setDateText();
                //controller.setCurrentTeacher(currentTeacher);
                // Sets new stage as modal window
                Stage stageView = new Stage();
                stageView.setScene(new Scene(root));

                stageView.setOnHiding(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Stage on Hiding");
                        setTableItems();
                    }
                });
                
                stageView.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                  System.out.println("Stage is closing");
                  setTableItems();
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
    private void onBtnLogOutPressed(ActionEvent event)
    {

    }
    
    private void setTableItems()
    {
        tblVolunteers.setItems(FXCollections.observableArrayList(dataModel.getAllUsers()));
        System.out.println("Table items set/refreshed");
    }
}
