/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.Guild;
import GUI.Model.ModelFacade;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Desmoswal
 */
public class GuildManagementViewController implements Initializable
{

    @FXML
    private JFXListView<Guild> listGuilds;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnModify;
    @FXML
    private JFXButton btnRemove;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private StackPane stckPaneNew;
    @FXML
    private Label lblGuildName;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXButton btnChange;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnAddNew;

    ModelFacade MOD_FAC = ModelFacade.getModelFacade();
    Guild selectedGuild = null;
    private final Service servicegetGuilds = new Service()
    {
        @Override
        protected Task createTask()
        {
            return new Task()
            {
                @Override
                protected Object call() throws Exception
                {

                    MOD_FAC.setAllGuildsIntoArray();
                    return null;

                }
            };
        }
    };
    @FXML
    private Label lblGuilds;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        servicegetGuilds.setOnSucceeded(e
                -> 
                {
                    setListItems();
        });
        setListItems();
        setTextAll();
        if (MOD_FAC.getCurrentUser().getType() != 2)
        {
            btnAdd.setVisible(false);
            btnRemove.setVisible(false);
        }
    }

    void setListItems()
    {
        listGuilds.setItems(MOD_FAC.getAllSavedGuilds());
    }

    @FXML
    private void btnAddPressed(ActionEvent event)
    {
        stckPaneNew.setVisible(true);
        MOD_FAC.fadeInTransition(Duration.millis(750), stckPaneNew);
        btnAddNew.setVisible(true);
        btnChange.setVisible(false);
    }

    @FXML
    private void btnModifyPressed(ActionEvent event)
    {
        btnChange.setVisible(true);
        if (listGuilds.getSelectionModel().getSelectedItem() != null)
        {
            stckPaneNew.setVisible(true);
            MOD_FAC.fadeInTransition(Duration.millis(750), stckPaneNew);
            txtName.setText(listGuilds.getSelectionModel().getSelectedItem().getName());
            btnAddNew.setVisible(false);
        }

        else
        {

            MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_GUILD_NOT_SELECTED"), rootPane);
        }

    }

    @FXML
    private void btnRemovePressed(ActionEvent event)
    {
        MOD_FAC.deleteGuild(selectedGuild.getId());
        servicegetGuilds.restart();
    }

    @FXML
    private void btnChangePressed(ActionEvent event)
    {
        String name = txtName.getText().trim();
        if (!name.equals(""))
        {
            MOD_FAC.updateGuild(selectedGuild.getId(), txtName.getText());
            MOD_FAC.fadeOutTransition(Duration.millis(750), stckPaneNew).setOnFinished(
                    e -> stckPaneNew.setVisible(false)
            );
            servicegetGuilds.restart();
        }
        else
        {
            MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_EMPTY_ERROR"), rootPane);
        }
    }

    @FXML
    private void btnCancelPressed(ActionEvent event)
    {
        MOD_FAC.fadeOutTransition(Duration.millis(750), stckPaneNew);
        stckPaneNew.setVisible(false);
        txtName.clear();
    }

    @FXML
    private void btnAddNewPressed(ActionEvent event)
    {
        String name = txtName.getText().trim();
        if (!name.equals(""))
        {
            MOD_FAC.addGuild(name);

            servicegetGuilds.restart();
            MOD_FAC.fadeOutTransition(Duration.millis(750), stckPaneNew);
            stckPaneNew.setVisible(false);
        }
         else
        {
            MOD_FAC.snackbarPopup(MOD_FAC.getLang("STR_EMPTY_ERROR"), rootPane);
        }
    }

    @FXML
    private void onListViewPressed(MouseEvent event
    )
    {
        selectedGuild = listGuilds.getSelectionModel().getSelectedItem();
    }

    private void setTextAll()
    {
        btnModify.setText(MOD_FAC.getLang("BTN_EDIT"));
        btnAdd.setText(MOD_FAC.getLang("BTN_ADD_GUILD"));
        btnRemove.setText(MOD_FAC.getLang("BTN_REMOVE_GUILD"));
        lblGuilds.setText(MOD_FAC.getLang("GUILD_TAG_TWO"));
        lblGuildName.setText(MOD_FAC.getLang("LBL_GUILD_NAME"));
        btnChange.setText(MOD_FAC.getLang("BTN_EDIT"));
        btnCancel.setText(MOD_FAC.getLang("BTN_CANCEL"));
    }
}
