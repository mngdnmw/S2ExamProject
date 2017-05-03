package GUI.Controller;

import BE.User;
import BE.Volunteer;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.skins.JFXDatePickerSkin;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.util.StringUtils;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private ImageView imgVwProfilePic;
    @FXML
    private TextArea textAreaGuilds;
    @FXML
    private JFXButton JFXBtnUpdatePhoto;
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
    @FXML

    private JFXButton btnEditSave;
    

    private AnchorPane root;

    TextField txtName;
    TextField txtPh; 
    TextField txtEmail;
    TextField txtResidence;

    JFXButton btnCancel;
    User currentUser;
    boolean editing = false;
    boolean isIncorrect = false;

    private final static ModelFacade MOD_FACADE = ModelFacade.getModelFacade();
    @FXML
    private JFXTextArea JFXTxtAreaBenefits;
    @FXML
    private Pane paneCalAll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        createEditFields();
        setCurrentUser(MOD_FACADE.getCurrentUser());
        setUserInfo();
        showConstantCalendar();
        setUserImage();
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    private void showConstantCalendar()
    {
        JFXDatePicker calendar = new JFXDatePicker();
        
        JFXDatePickerSkin skin = new JFXDatePickerSkin(calendar);
        Region pop = (Region) skin.getPopupContent();
        
        //String s = "S2ExamProject.class.getResource(\"/GUI/View/MainLayout.css\").toExternalForm()";
        //pop.getStylesheets().add(s);
        //pop.setMaxSize(hBoxCalAll.getMaxWidth(), hBoxCalAll.getMaxHeight());
        //hBoxCalAll.setPadding(new Insets(10));
        //pop.setMinSize(paneCalAll.getWidth(), paneCalAll.getHeight());
        //paneCalAll.getChildren().add(pop);  
        hBoxCalAll.getChildren().add(pop);
          
        
    }

    private void setUserInfo()
    {
        lblName.setText(currentUser.getName());
        lblPh.setText(String.valueOf(currentUser.getPhone()));
        lblEmail.setText(currentUser.getEmail());
        lblResidence.setText(currentUser.getResidence());
    }

    @FXML
    private void pressedEditSaveButton(ActionEvent event) {
        if(!editing) {
            editInfo();
            editing = true;
            btnEditSave.setText("Save");
            checkTextFields();
            addCancelButton();
        } else {
            if(isIncorrect && btnEditSave.isDisabled()) {
                JFXSnackbar b = new JFXSnackbar(root);
                b.show("Please enter valid information in the fields!", 2000);
                return;
            }
            saveInfo(currentUser);
            editing = false;
            btnEditSave.setText("Edit");
            checkTextFields();
            removeCancelButton();
        }
    }
    
    private void createEditFields() {
        txtName = new JFXTextField();
        txtPh = new JFXTextField();
        txtEmail = new JFXTextField();
        txtResidence = new JFXTextField();
        
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
        //MOD_FACADE.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPh.getText()), user.getNote(), txtResidence.getText()); //do things in db
        
        //currentUser = MOD_FACADE.getUserInfo(user.getId());
        
        txtName.setVisible(false);
        txtPh.setVisible(false);
        txtEmail.setVisible(false);
        txtResidence.setVisible(false);
        
        lblName.setVisible(true);
        lblPh.setVisible(true);
        lblEmail.setVisible(true);
        lblResidence.setVisible(true);
        
        setUserInfo(); //update labels
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
            isIncorrect = false;
        } else {
            txtPh.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
            isIncorrect = true;
        }
    }
    
    @FXML
    private void pressedChangeImage(ActionEvent event) {
        FileChooser c = new FileChooser();
        c.setTitle("Select a new image");
        String[] extensions = {"jpg","jpeg","png","gif"};
        c.setSelectedExtensionFilter(new ExtensionFilter("Image files only", extensions));
        File newImg = c.showOpenDialog(JFXBtnUpdatePhoto.getScene().getWindow());
        
//        if(newImg != null) {
//            try {
//                MOD_FACADE.updateUserImage(currentUser, newImg);
//            } catch(FileNotFoundException e) {
//                System.out.println(e);
//                Alert a = new Alert(Alert.AlertType.ERROR);
//                a.setHeaderText("Selected image is not found");
//                a.setContentText("File not found!");
//            } 
//        }
        setUserImage();
    }
    
    public void setUserImage() {

        System.out.println(MOD_FACADE.getUserImage(currentUser));
        if(MOD_FACADE.getUserImage(currentUser) != null)
            imgVwProfilePic.setImage(new Image(MOD_FACADE.getUserImage(currentUser)));
    }
    
    private void addCancelButton() {
        int btnSavePosCol = GridPane.getColumnIndex(btnEditSave); //saving position
        int btnSavePosRow = GridPane.getRowIndex(btnEditSave);
        //GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave)-1); //moving save button one up
        btnCancel = new JFXButton();
        btnCancel.setText("Cancel"); //preparing cancel button
        btnCancel.setTextFill(Color.WHITE);
        btnCancel.setStyle(btnEditSave.getStyle());
        btnCancel.setPadding(btnEditSave.getPadding());
        btnCancel.setAlignment(btnEditSave.getAlignment());
        
        gridEdit.add(btnCancel, btnSavePosCol, btnSavePosRow+1); //adding to the old position of save btn
        btnCancel.setOnAction(new EventHandler<ActionEvent>() { //setting onAction, nothing changed, just show old labels again
            @Override
            public void handle(ActionEvent event) {
                txtName.setVisible(false);
                txtPh.setVisible(false);
                txtEmail.setVisible(false);
                txtResidence.setVisible(false);
        
                lblName.setVisible(true);
                lblPh.setVisible(true);
                lblEmail.setVisible(true);
                lblResidence.setVisible(true);
                
                removeCancelButton(); //if cancel button clicked, it will disappear
                editing = false;
                btnEditSave.setText("Edit");
            }
        });
    }
    
    private void removeCancelButton() {
        //GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave)+1); //moving save button one down
        gridEdit.getChildren().remove(btnCancel); //deleting cancel button from gridpane
        if(btnEditSave.isDisabled())
            btnEditSave.setDisable(false);
    }
}
