package GUI.Controller;

import BE.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ManagerViewController implements Initializable
{

    @FXML
    private Label lblHrsAll;
    @FXML
    private Label lblHrsYr;
    @FXML
    private Label lblHrsMth;
    @FXML
    private Label lblHrsDay;
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
    private JFXButton btnEdit;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXTextArea txtGuilds;
    
    ManagerEditViewController mevController;
    boolean edit = false;
    
    
    private static User selectedUser;
 
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        boolean edit = false;
        setText();
    }    
    
    public void setController(ManagerEditViewController c)
    {
        txtName.setEditable(false);
        txtAddress.setEditable(false);
        txtPhone.setEditable(false);
        txtEmail.setEditable(false);
        this.mevController = c;
    }
    
    public void setText()
    {
        if (selectedUser != null)
        {
            txtName.setText(selectedUser.getName());
            txtAddress.setText(selectedUser.getAddress());
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
    private void onBtnEditPressed(ActionEvent event)
    {
        edit = !edit;
        
        if(edit == true)
        {
            txtName.setEditable(true);
            txtAddress.setEditable(true);
            txtPhone.setEditable(true);
            txtEmail.setEditable(true);
        }
        
        if(edit == false)
        {            
            txtName.setEditable(false);
            txtAddress.setEditable(false);
            txtPhone.setEditable(false);
            txtEmail.setEditable(false);
        }     
    }

    @FXML
    private void onBtnUpdatePhotoPressed(ActionEvent event)
    {
        //dataModel.addUser(name, email, password, type, phone, address, note);
        //dataModel.addUser(txtName.getText(), txtEmail.getText(), "asd123", 0, Integer.parseInt(txtPhone.getText()), txtAddress.getText(), JFXTxtAreaNotes.getText());
    }
    
    public static void setSelectedUser(User user)
    {
        selectedUser = user;
    }
    
    private void updateUserInfo()
    {
        //dataModel.updateUserInfo(txtName.getText(), txtEmail.getText(), "password", 0, Integer.parseInt(txtPhone.getText()), txtAddress.getText(), JFXTxtAreaNotes.getText(), selectedUser.getId());
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
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        stage.close();
    }
}
