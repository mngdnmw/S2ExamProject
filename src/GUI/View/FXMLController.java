package GUI.View;

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

/**
 * FXML Controller class
 *
 * @author meng
 */
public class FXMLController implements Initializable
{

    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private Label lblHrsAll;
    @FXML
    private Label lblHrsYr;
    @FXML
    private Label lblHrsMth;
    @FXML
    private Label lblHrsDay;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXButton JFXBtnAccept;
    @FXML
    private JFXButton JFXBtnCancel;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private JFXButton JFXBtnUpdatePhoto;
    @FXML
    private JFXTextArea txtGuilds;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void onBtnEditPressed(ActionEvent event)
    {
    }

    @FXML
    private void onBtnAcceptPressed(ActionEvent event)
    {
    }

    @FXML
    private void onBtnCancelPressed(ActionEvent event)
    {
    }

    @FXML
    private void onBtnUpdatePhotoPressed(ActionEvent event)
    {
    }
    
}
