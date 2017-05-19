package GUI.Controller;

import BE.Guild;
import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private JFXTextField txtAddress2;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXListView<Guild> listGuilds;
    @FXML
    private StackPane stckPanePasswordChanger;
    @FXML
    private JFXPasswordField txtOPassword;
    @FXML
    private JFXPasswordField txtNPassword;
    @FXML
    private JFXPasswordField txtNPasswordTwo;
    @FXML
    private AnchorPane root;
    ManagerViewController mevController;
    boolean edit = false;

    private static ModelFacade modelFacade = new ModelFacade();
    private static User selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        edit = false;
        setText();
        listGuilds.setItems(FXCollections.observableArrayList(selectedUser.getGuildList()));
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
            txtAddress2.setText(selectedUser.getResidence2());
            txtPhone.setText(String.valueOf(selectedUser.getPhone()));
            txtEmail.setText(selectedUser.getEmail());
            txtNotes.setText(selectedUser.getNote());
        } else
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
        modelFacade.updateUserInfo(selectedUser.getId(), txtName.getText(), txtEmail.getText(), selectedUser.getType(), Integer.parseInt(txtPhone.getText()), txtNotes.getText(), txtAddress.getText(), txtAddress2.getText());
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
    @FXML
    private void changePasswordEvent(ActionEvent event)
    {
        int count;
        if (txtNPassword.getText().equals(txtNPasswordTwo.getText()))
        {
            count = modelFacade.changePassword(selectedUser, txtOPassword.getText(), txtNPassword.getText());
        }
        else
        {
            count = -1;
        }
        if (count > 0)
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Password has succesfully changed", 2000);
            hidePasswordChangerEvent();
            
        }
        else if (count == -1)
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Password do not match", 2000);
        }
        else
        {
            JFXSnackbar b = new JFXSnackbar(root);
            b.show("Old password is wrong", 2000);
        }

    }

    @FXML
    private void openPasswordChangerEvent(ActionEvent event)
    {
        stckPanePasswordChanger.setVisible(true);
        modelFacade.fadeInTransition(Duration.millis(750), stckPanePasswordChanger);

    }
    @FXML
    private void hidePasswordChangerEvent()

    {
        modelFacade.fadeOutTransition(Duration.millis(750), stckPanePasswordChanger)
                .setOnFinished(e -> stckPanePasswordChanger.setVisible(false));

    }
}
