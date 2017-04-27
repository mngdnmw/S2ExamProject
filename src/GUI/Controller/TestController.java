/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;

import BE.EnumCache.Lang;
import GUI.Model.LanguageModel;
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

    LanguageModel translation = new LanguageModel(Lang.ENG);
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
            translation.set(Lang.ENG);
        if(event.getSource().equals(btnDan))
            translation.set(Lang.DAN);
        setTransationText();
    }
    
    private void setTransationText() {
        lab1.setText(translation.get("TEST"));
        lab2.setText(translation.get("TEST2"));
    }
}
