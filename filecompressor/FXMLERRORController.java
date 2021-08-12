/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecompressor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Parthu
 */
public class FXMLERRORController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label LBError;
    @FXML
    private Button BTOk;
    
    Stage stage = null;
    
    
    public void init(Stage stage ,String Error){
        LBError.setText(Error);
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btOkHandler(ActionEvent event) {
        stage.close();
    }
    
}
