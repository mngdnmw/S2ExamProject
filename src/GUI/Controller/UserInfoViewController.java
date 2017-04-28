package GUI.Controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class UserInfoViewController implements Initializable
{

    @FXML
    private Label lblName;
    @FXML
    private Label lblPh;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private JFXButton btnNameEdit;
    @FXML
    private Label lblHrsAll;
    @FXML
    private Label lblHrsYr;
    @FXML
    private Label lblHrsMth;
    @FXML
    private Label lblHrsDay;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private TextArea textAreaGuilds;
    @FXML
    private JFXButton JFXBtnUpdatePhoto;
    @FXML
    private JFXButton btnNameEdit1;
    @FXML
    private TextArea TextAreaBenefits;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}
