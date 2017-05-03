package GUI.Controller;

import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.sun.deploy.util.StringUtils;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private JFXButton btnEditSave;
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
    @FXML
    private GridPane gridEdit;

    TextField txtName;
    TextField txtPh; 
    TextField txtEmail;
    TextField txtResidence;
    User currentUser;
    boolean editing = false;

    private final static ModelFacade MOD_FACADE = new ModelFacade();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        createEditFields();
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

    @FXML
    private void pressedEditSaveButton(ActionEvent event) {
        if(!editing) {
            editInfo();
            editing = true;
            btnEditSave.setText("Save");
        } else {
            saveInfo(currentUser);
            editing = false;
            btnEditSave.setText("Edit");
        }
    }
    
    private void createEditFields() {
        txtName = new TextField();
        txtPh = new TextField();
        txtEmail = new TextField();
        txtResidence = new TextField();
        
        txtPh.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                checkTextFields();
            }
            
        });
        
        txtName.setVisible(false);
        txtPh.setVisible(false);
        txtEmail.setVisible(false);
        txtResidence.setVisible(false);
        
        gridEdit.add(txtName, 1, 0);
        gridEdit.add(txtPh, 1, 1);
        gridEdit.add(txtEmail, 1, 2);
        gridEdit.add(txtResidence, 1, 3);
    }
    
    private void editInfo() {
        txtName.setText(lblName.getText());
        
        txtPh.setText(lblPh.getText());
        
        txtEmail.setText(lblEmail.getText());
        
        txtResidence.setText(lblResidence.getText());
        
        lblName.setVisible(false);
        lblPh.setVisible(false);
        lblEmail.setVisible(false);
        lblResidence.setVisible(false);
        
        txtName.setVisible(true);
        txtPh.setVisible(true);
        txtEmail.setVisible(true);
        txtResidence.setVisible(true);
    }
    
    private void saveInfo(User user) {
        MOD_FACADE.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPh.getText()), user.getNote(), txtResidence.getText()); //do things in db
        
        currentUser = MOD_FACADE.getUserInfo(user.getId());
        
        txtName.setVisible(false);
        txtPh.setVisible(false);
        txtEmail.setVisible(false);
        txtResidence.setVisible(false);
        
        lblName.setVisible(true);
        lblPh.setVisible(true);
        lblEmail.setVisible(true);
        lblResidence.setVisible(true);
        
        setProperties(); //update labels
    }
    
    private void checkTextFields() {
        boolean success = false;
        try {
            Integer.parseInt(txtPh.getText());
            success = true;
        } catch(NumberFormatException e) {
            success = false;
            txtPh.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);    
        }
        if(success) {
            btnEditSave.setDisable(false);
            txtPh.setStyle("");
        } else {
            txtPh.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
        }
    }
}
