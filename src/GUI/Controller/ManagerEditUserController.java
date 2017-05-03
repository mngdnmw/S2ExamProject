/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Desmoswal
 */
public class ManagerEditUserController implements Initializable
{

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblUserName;
    @FXML
    private JFXButton btnAddUser;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXButton btnAddHours;
    @FXML
    private JFXButton btnEditInfo;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private TableView<?> tblVolunteers;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colPhone;
    @FXML
    private TableColumn<?, ?> colEmail;
    @FXML
    private TableColumn<?, ?> colGuild;
    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void onBtnAddUserClicked(ActionEvent event)
    {
    }

    @FXML
    private void onEditInfoPressed(ActionEvent event)
    {
    }

    @FXML
    private void onBtnLogOutPressed(ActionEvent event)
    {
    }

    @FXML
    private void onTablePressed(MouseEvent event)
    {
    }
    
}
