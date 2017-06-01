package GUI.Controller;

import BE.User;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class EditGridController implements Initializable
{

    @FXML
    private AnchorPane root;
    @FXML
    private GridPane gridEdit;
    @FXML
    private JFXButton btnEditSave;
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
    

    boolean editing = false;
    boolean isIncorrect = false;
    User currentUser;
    JFXButton btnCancelEditInfo = new JFXButton();

    private final static ModelFacade MOD_FAC = ModelFacade.getModelFacade();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        currentUser = MOD_FAC.getCurrentUser();
        setUserInfo();
    }

    /**
     * Sets all text to the language they are meant to be in.
     */
    private void setTextAll()
    {
        btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));
    }

    /**
     * Sets the general information of the user.
     */
    private void setUserInfo()
    {
        txtName.setText(currentUser.getName());
        txtPhone.setText(String.valueOf(currentUser.getPhone()));
        txtEmail.setText(currentUser.getEmail());
        txtAddress.setText(currentUser.getResidence());
        txtAddress2.setText(currentUser.getResidence2());
    }

    /**
     * An additional cancel button is created along with the editSaveButton,
     * which has changed text to save rather than edit.
     *
     * @param event = when editSaveButton is pressed.
     */
    @FXML
    private void pressedEditSaveButton(ActionEvent event)
    {
        if (!editing)
        {

            editInfo(true);
            editing = true;
            btnEditSave.setText(MOD_FAC.getLang("BTN_SAVE"));
            checkTextFields();
            addCancelButton();

        }
        else
        {
            if (isIncorrect && btnEditSave.isDisabled())
            {
                MOD_FAC.timedSnackbarPopup(MOD_FAC.getLang("STR_INVALID_INPUT"), root, 5000);
            }
            saveInfo(currentUser);
            editing = false;
            btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));
            checkTextFields();
            removeCancelButton();

        }
    }

    /**
     * Adds an event handler to the phone number textfield to ensure only
     * numbers are inputted.
     */
    private void createEditFields()

    {

        txtPhone.setOnKeyReleased(new EventHandler<KeyEvent>()

        {
            @Override
            public void handle(KeyEvent event)
            {
                checkTextFields();

            }

        });
    }

    /**
     * Updates the user info in the database if it has been changed.
     *
     * @param user = currentUser.
     */
    private void saveInfo(User user)
    {
        MOD_FAC.updateUserInfo(user.getId(), txtName.getText(), txtEmail.getText(), user.getType(), Integer.parseInt(txtPhone.getText()), user.getNote(), txtAddress.getText(), txtAddress2.getText());
        MOD_FAC.logEvent(new BE.Event(new Timestamp(new Date().getTime()), MOD_FAC.getCurrentUser().getName() + " edited personal information."));
        currentUser = MOD_FAC.getUserInfo(user.getId());
        editInfo(false);
        setUserInfo(); //update labels

    }

    /**
     * Ensures all inputs of the general information edit text fields are
     * correct.
     */
    private void checkTextFields()
    {
        boolean success = false;
        try

        {
            Integer.parseInt(txtPhone.getText());
            success = true;
        }
        catch (NumberFormatException e)
        {
            success = false;
            txtPhone.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
        }
        if (success)
        {
            btnEditSave.setDisable(false);
            txtPhone.setStyle("");
            isIncorrect = false;
        }
        else
        {
            txtPhone.setStyle("-fx-background-color:red;");
            btnEditSave.setDisable(true);
            isIncorrect = true;

        }
    }

    /**
     * Formatting for the cancel button, which appears when the edit general
     * info button is pressed.
     */
    private void addCancelButton()
    {

        int btnSavePosCol = GridPane.getColumnIndex(btnEditSave); //Saving position
        int btnSavePosRow = GridPane.getRowIndex(btnEditSave);
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) - 1); //Moving save button one up

        btnCancelEditInfo.setText(MOD_FAC.getLang("BTN_CANCEL")); //Preparing cancel button
        btnCancelEditInfo.setId("btnCancelGrey");
        btnCancelEditInfo.setPrefHeight(25);
        btnCancelEditInfo.setPrefWidth(Double.MAX_VALUE);
        btnCancelEditInfo.setTextFill(Color.WHITE);
        btnCancelEditInfo.setPadding(btnEditSave.getPadding());

        gridEdit.add(btnCancelEditInfo, btnSavePosCol, btnSavePosRow); //Adding to the old position of save btn
        GridPane.setValignment(btnEditSave, VPos.CENTER);
        GridPane.setValignment(btnCancelEditInfo, VPos.CENTER);
        btnCancelEditInfo.setOnAction(new EventHandler<ActionEvent>()
        { //Setting onAction, nothing changed, just show old labels again
            @Override
            public void handle(ActionEvent event)
            {
                editInfo(false);

                removeCancelButton(); //if Cancel button clicked, it will disappear
                editing = false;
                btnEditSave.setText(MOD_FAC.getLang("BTN_EDIT"));

            }
        });
    }

    /**
     * Makes all the general information textfields editable.
     */
    private void editInfo(boolean editable)
    {
        txtName.setEditable(editable);
        txtEmail.setEditable(editable);
        txtPhone.setEditable(editable);
        txtAddress.setEditable(editable);
        txtAddress2.setEditable(editable);
        createEditFields();
    }

    /**
     * Removes the cancel button which was created as the result of clicking on
     * the edit general information button.
     */
    private void removeCancelButton()
    {
        GridPane.setRowIndex(btnEditSave, GridPane.getRowIndex(btnEditSave) + 1); //Moving save button one down
        gridEdit.getChildren().remove(btnCancelEditInfo); //Deleting cancel button from gridpane
        if (btnEditSave.isDisabled())
        {
            btnEditSave.setDisable(false);
        }
    }

}
