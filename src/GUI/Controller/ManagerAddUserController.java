/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Guild;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Desmoswal
 */
public class ManagerAddUserController implements Initializable
{

    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private JFXButton btnUpdatePhoto;
    @FXML
    private JFXButton btnAccept;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private TableView<Guild> tblGuilds;
    @FXML
    private TableColumn<Guild, String> colGuilds;
    @FXML
    private StackPane stackPane;

    ModelFacade modelFacade = new ModelFacade();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setTableProperties();
    }    

    private void setTableProperties()
    {
        colGuilds.setCellValueFactory(new PropertyValueFactory("name"));
    }
    
    @FXML
    private void onBtnUpdatePhotoPressed(ActionEvent event)
    {
    }

    @FXML
    private void onBtnAcceptPressed(ActionEvent event)
    {
        if(!txtName.getText().isEmpty())
        {
            if(txtPhone.getText().isEmpty() && txtEmail.getText().isEmpty())
            {
                showMissingInfoDialog();
                System.out.println("User not added: missing phone or email");
            }
            
            else
            {
                if(txtPhone.getText().equals(""))
                {
                    txtPhone.setText("0");
                }

                modelFacade.addUser(txtName.getText(), txtEmail.getText(), "", 0, Integer.parseInt(txtPhone.getText()), txtAddress.getText(), txtNotes.getText());
                Stage stage = (Stage) btnAccept.getScene().getWindow();
                stage.close();
                System.out.println("New user added: " + txtName.getText());
            }
        }
        else
        {
            showMissingInfoDialog();
            System.out.println("User not added: missing name");
        }  
    }

    @FXML
    private void onBtnCancelPressed(ActionEvent event)
    {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    
    private void showMissingInfoDialog()
    {
        stackPane.toFront();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Missing information"));
            content.setBody(new Text("Required information: \n"
            +"-Name\n"
            +"-Email or Phone number"));

            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("Okay");
            button.setStyle("-fx-background-color:  #00c4ad; -fx-text-fill: white;");
            
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event)
                    {
                        dialog.close();
                        stackPane.toBack();
                    }
            });
            content.setActions(button);
            dialog.show();
    }
    
}
