package GUI.Controller;

import BE.User;
import GUI.Model.ModelFacade;
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


public class ManagerEditViewController implements Initializable
{

    @FXML
    private Label lblHrsAll;
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
    private JFXTextArea txtNotes;
    
    ManagerViewController mevController;
    boolean edit = false;
    
    private static ModelFacade modelFacade = new ModelFacade();
    private static User selectedUser;
 
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        boolean edit = false;
        setText();
    }    
    
    public void setController(ManagerViewController c)
    {
        this.mevController = c;
    }
    
    public void setText()
    {
        if (selectedUser != null)
        {
            txtName.setText(selectedUser.getName());
            txtAddress.setText(selectedUser.getResidence());
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
    private void onBtnUpdatePhotoPressed(ActionEvent event)
    {
        
    }
    
    public static void setSelectedUser(User user)
    {
        selectedUser = user;
    }
    
    private void updateUserInfo()
    {
        modelFacade.updateUserInfo(selectedUser.getId(), txtName.getText(), txtEmail.getText(), selectedUser.getType(), Integer.parseInt(txtPhone.getText()), txtNotes.getText(), txtAddress.getText());
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
        Stage stage = (Stage) JFXBtnCancel.getScene().getWindow();
        stage.close();
    }
}
