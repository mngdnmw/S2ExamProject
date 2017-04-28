package GUI.Controller;

import BE.User;
import GUI.Model.DataModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ManagerEditViewController implements Initializable
{

    @FXML
    private Label lblUserName;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnCopyEmail;
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

    
    DataModel dataModel = new DataModel();
    
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

    private void onLogOutClicked(ActionEvent event)
    {
         tblVolunteers.setItems(FXCollections.observableArrayList(dataModel.getAllUsers()));
    }
}