package GUI.Controller;

import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class UserInfoViewController implements Initializable
{

    @FXML
    private Label lblName;
    @FXML
    private Label lblPh;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblResidence;
    @FXML
    private JFXButton btnNameEdit;
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
    @FXML
    private HBox hBoxCalAll;
    @FXML
    private HBox hBoxCalYr;
    @FXML
    private HBox hBoxCalMth;
    @FXML
    private HBox hBoxCalDay;
    @FXML
    private Label lblHrsAll;
    @FXML
    private Label lblHrsYr;
    @FXML
    private Label lblHrsMth;
    @FXML
    private Label lblHrsDay;
    @FXML
    private AnchorPane anchorGraph;

    User currentUser;

    private final static ModelFacade MOD_FACADE = new ModelFacade();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setCurrentUser(MOD_FACADE.getCurrentUser());
        setProperties();
        showConstantCalendar();
        
        
        
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    private void showConstantCalendar()
    {
        JFXDatePicker calendar = new JFXDatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(calendar);
        Region pop = (Region) datePickerSkin.getPopupContent();
        pop.setMaxSize(hBoxCalAll.getMaxWidth(), hBoxCalAll.getMaxHeight());
        //hBoxCalAll.setPadding(new Insets(5));
        hBoxCalAll.getChildren().add(pop);
        
        String css = this.getClass().getResource("/GUI/View/MainLayout.css").toExternalForm();
        pop.getStyleClass().add(css);
        
        //Trying to make calendar smaller by applying CSS
        pop.setId("calendar");
        pop.applyCss();
    }

    private void setProperties()
    {
        lblName.setText(currentUser.getName());
        lblPh.setText(String.valueOf(currentUser.getPhone()));
        lblEmail.setText(currentUser.getEmail());
        lblResidence.setText(currentUser.getResidence());
        //Need to get path to image on database
        //imgVwProfilePic.setImage(value);
    }

    private void updateProperties()
    {
        lblName.textProperty().addListener((observable, oldValue, newValue) ->
        {
            lblName.setText(newValue);
        });
        lblPh.textProperty().addListener((observable, oldValue, newValue) ->
        {
            lblPh.setText(newValue);
        });
        lblEmail.textProperty().addListener((observable, oldValue, newValue) ->
        {
            lblEmail.setText(newValue);
        });
        lblResidence.textProperty().addListener((observable, oldValue, newValue) ->
        {
            lblResidence.setText(newValue);
        });
        //Need to add 
    }

}
