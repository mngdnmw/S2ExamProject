package GUI.Controller;

import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private JFXTextField txtAddress2;
    @FXML
    private ImageView imgVwProfilePic;
    @FXML
    private JFXButton btnAccept;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXListView<Guild> listViewGuilds;
    @FXML
    private JFXButton JFXBtnAddPhoto;
    @FXML
    private JFXCheckBox chkVolunteer;
    @FXML
    private JFXCheckBox chkManager;
    @FXML
    private JFXTextField txtPassword;

    ModelFacade modelFacade = new ModelFacade();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setListView();

    }

    private void setListView()
    {
        ObservableList<Guild> items = FXCollections.observableArrayList(modelFacade.getAllGuilds());
        listViewGuilds.setItems(items);
    }

    @FXML
    private void onBtnAcceptPressed(ActionEvent event)
    {
        if (!txtName.getText().isEmpty())
        {
            if (txtPhone.getText().isEmpty() && txtEmail.getText().isEmpty())
            {
                snackBarPopup("Phone number OR Email required");
                System.out.println("User not added: missing phone or email");
            } else
            {
                if (txtPhone.getText().equals(""))
                {
                    txtPhone.setText("0");
                }

                try
                {
                    Integer.parseInt(txtPhone.getText());
                } catch (NumberFormatException e)
                {
                    snackBarPopup("Phone number needs to contain only numbers");
                    System.out.println("Phone number contains letters or special characters");
                }

                modelFacade.addUser(txtName.getText(), txtEmail.getText(), txtPassword.getText(), 0, Integer.parseInt(txtPhone.getText()), txtAddress.getText(), txtAddress2.getText(), txtNotes.getText());
                Stage stage = (Stage) btnAccept.getScene().getWindow();
                stage.close();
                System.out.println("New user added: " + txtName.getText());
            }
        } else
        {
            snackBarPopup("Name required");
            System.out.println("User not added: missing name");
        }

    }

    @FXML
    private void onBtnCancelPressed(ActionEvent event)
    {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void snackBarPopup(String str)
    {
        int time = 3000;
        JFXSnackbar snackbar = new JFXSnackbar(rootPane);
        snackbar.show(str, time);
        PauseTransition pause = new PauseTransition(Duration.millis(time));
        pause.play();
    }

    /*@FXML
    private void pressedChangeImage(ActionEvent event)
    {
        FileChooser c = new FileChooser();
        c.setTitle("Select a new image");
        String[] extensions =
        {
            "jpg", "jpeg", "png", "gif"
        };
        c.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image files only", extensions));
        File newImg = c.showOpenDialog(JFXBtnAddPhoto.getScene().getWindow());

        if (newImg != null)
        {
            try
            {
                modelFacade.updateUserImage(modelFacade.getAllUsers().get(modelFacade.getAllUsers().size()-1), newImg);
            } catch (FileNotFoundException e)
            {
                System.out.println(e);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Selected image is not found");
                a.setContentText("File not found!");
            }
        }
        setUserImage();
    }

    public void setUserImage()
    {
        if (modelFacade.getUserImage(modelFacade.getAllUsers().get(modelFacade.getAllUsers().size()-1)) != null)
        {
            imgVwProfilePic.setImage(new Image(modelFacade.getUserImage(modelFacade.getAllUsers().get(modelFacade.getAllUsers().size()-1))));
        }
    }*/
    
    @FXML
    private void pressedChangeImage(ActionEvent e)
    {
        
    }
}
