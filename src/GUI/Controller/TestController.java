/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BLL.Translation;
import DAL.LanguageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Kristof
 */
public class TestController implements Initializable {

    @FXML
    private Label lab1;
    @FXML
    private Label lab2;

    Translation translation = new Translation(LanguageManager.Lang.ENG);
    @FXML
    private Button btnDan;
    @FXML
    private Button btneng;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTransationText();
    }    
    
    @FXML
    private void btnPress(ActionEvent event) {
        if(event.getSource().equals(btneng))
            translation.set(LanguageManager.Lang.ENG);
        if(event.getSource().equals(btnDan))
            translation.set(LanguageManager.Lang.DAN);
        setTransationText();
    }
    
    private void setTransationText() {
        lab1.setText(translation.get("TEST"));
        lab2.setText(translation.get("TEST2"));
    }
}
