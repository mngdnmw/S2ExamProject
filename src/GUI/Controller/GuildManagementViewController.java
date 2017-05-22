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
    
    ModelFacade modelFacade = ModelFacade.getModelFacade();
    
    Guild selectedGuild = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setListItems();
        
        if(modelFacade.getCurrentUser().getType() != 2)
        {
            btnAdd.setDisable(true);
            btnRemove.setDisable(true);
        }
    }    
    
    void setListItems()
    {
        listGuilds.setItems(FXCollections.observableArrayList(modelFacade.getAllGuilds()));
    }

    @FXML
    private void btnAddPressed(ActionEvent event)
    {
        stckPaneNew.setVisible(true);
        modelFacade.fadeInTransition(Duration.millis(750), stckPaneNew);
        btnAddNew.setVisible(true);
    }

    @FXML
    private void btnModifyPressed(ActionEvent event)
    {
        if(listGuilds.getSelectionModel().getSelectedItem() != null)
        {
            stckPaneNew.setVisible(true);
            modelFacade.fadeInTransition(Duration.millis(750), stckPaneNew);
            txtName.setText(listGuilds.getSelectionModel().getSelectedItem().getName());
            btnAddNew.setVisible(false);
        }
        
        else
        {
            snackBarPopup("Guild to modify not selected");
        }
        
    }

    @FXML
    private void btnRemovePressed(ActionEvent event)
    {
        modelFacade.deleteGuild(selectedGuild.getId());
        setListItems();
    }

    @FXML
    private void btnChangePressed(ActionEvent event)
    {
        modelFacade.updateGuild(selectedGuild.getId(), txtName.getText());
        modelFacade.fadeOutTransition(Duration.millis(750), stckPaneNew);
        stckPaneNew.setVisible(false);
        setListItems();
    }

    @FXML
    private void btnCancelPressed(ActionEvent event)
    {
        modelFacade.fadeOutTransition(Duration.millis(750), stckPaneNew);
        stckPaneNew.setVisible(false);
        txtName.clear();
    }

    @FXML
    private void btnAddNewPressed(ActionEvent event)
    {
        modelFacade.addGuild(txtName.getText());
        setListItems();
        modelFacade.fadeOutTransition(Duration.millis(750), stckPaneNew);
        stckPaneNew.setVisible(false);
    }
    
    public void snackBarPopup(String str)
    {
        int time = 3000;
        JFXSnackbar snackbar = new JFXSnackbar(rootPane);
        snackbar.show(str, time);
        PauseTransition pause = new PauseTransition(Duration.millis(time));
        pause.play();
    }

    @FXML
    private void onListViewPressed(MouseEvent event)
    {
        selectedGuild = listGuilds.getSelectionModel().getSelectedItem();
    }
}
