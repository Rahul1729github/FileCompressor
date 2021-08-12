/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecompressor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Parthu
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField TFSource;
    @FXML
    private TextField TFDestination;
    @FXML
    private RadioButton RBCompress;
    @FXML
    private ToggleGroup RBOptions;
    @FXML
    private RadioButton RBDecompress;
    @FXML
    private Button RBSourceChooser;
    @FXML
    private Button RBDestinationChooser;
    
    private String SSourceFileName = "" ;
    private String SExt = ".comp";
    private String SOptionSelect = "" ;
    private StringProperty SSummary = new SimpleStringProperty("");
    
    @FXML
    private ComboBox CBTypeSelected;
    @FXML
    private Button BTNext;
    @FXML
    private Button BTCancel;
    @FXML
    private TextArea THSummary;
    
    @FXML
    public void btSourceActionHandler(ActionEvent e){
       FileChooser fc = new FileChooser();
        File sourceFile = fc.showOpenDialog(null);
        if( sourceFile != null ){
            System.out.println("File Name: " + sourceFile.getName());
            System.out.println("File Path: " + sourceFile.getAbsolutePath());
            TFSource.setText(sourceFile.getAbsolutePath());
            TFDestination.setText(sourceFile.getAbsolutePath() + SExt);
            SSourceFileName = sourceFile.getName();
            
            
        }
        else{
            System.out.println("Their is no such File");
        }
   }
   
   @FXML
   public void btDestinationActionHandler(ActionEvent event) throws IOException{
        
        FileChooser fc = new FileChooser();      
        fc.setInitialFileName( SSourceFileName + SExt );
        File destinationFile = fc.showSaveDialog(null);
        if(destinationFile != null){
            System.out.println("File Name: " + destinationFile.getName());
            System.out.println("File Path: " + destinationFile.getAbsolutePath());
            TFDestination.setText(destinationFile.getAbsolutePath());
            
            
            
        }
        else{
            System.out.println("Their is no such File");
        }
    }
   
   @FXML
    private void rbActionHandler(ActionEvent event) {
        if(RBCompress.isSelected()){
            SOptionSelect = "Compress";
        }
        if(RBDecompress.isSelected()){
            SOptionSelect = "Decompress";
        }
        String s;  
            s = "Source File : " + TFSource.getText() + "\n" + "Source File : " + SSourceFileName + "\n" + "Destination File: " + TFDestination.getText() + "\nAlgorithm : " + CBTypeSelected.getValue().toString() + "\nOption : " + SOptionSelect;
            SSummary.setValue(s);
            
    }
   
    @FXML
    private void btCancelActionHandler(ActionEvent event) {
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CBTypeSelected.getItems().addAll( "LZW", "GZIP", "HUFFMAN", "RUNLENGTH" );
        TFSource.setText("");
        TFDestination.setText("");
        CBTypeSelected.setValue("");
        
        CBTypeSelected.valueProperty().addListener( (V ,oldValue , newValue )-> {
            switch(CBTypeSelected.getValue().toString()){
                case "LZW": 
                    SExt = ".lzw";
                    break;
                case "GZIP": 
                    SExt = ".gz";
                    break;
                case "HUFFMAN": 
                    SExt = ".huf";
                    break;
                case "RUNLENGTH": 
                    SExt = ".rle";
                    break;
                default:    SExt = ".comp";
                    
            }
            TFDestination.setText(TFSource.getText() + SExt);
            String s;  
            s = "Source File : " + TFSource.getText() + "\n" + "Source File : " + SSourceFileName + "\n" + "Destination File: " + TFDestination.getText() + "\nAlgorithm : " + CBTypeSelected.getValue().toString() + "\nOption : " + SOptionSelect;
            SSummary.setValue(s);
            
                                                                                                                                                                  
        });
        TFSource.textProperty().addListener( (V ,oldValue , newValue )-> {
            TFDestination.setText(TFSource.getText() + SExt);
            String s;  
             TFDestination.setText(TFSource.getText() + SExt);
            s = "Source File : " + TFSource.getText() + "\n" + "Source File : " + SSourceFileName + "\n" + "Destination File: " + TFDestination.getText() + "\nAlgorithm : " + CBTypeSelected.getValue().toString() + "\nOption : " + SOptionSelect;
            SSummary.setValue(s);
            
                                                                                                                                                                  
        });
        TFDestination.textProperty().addListener( (V ,oldValue , newValue )-> {
            String s;  
            s = "Source File : " + TFSource.getText() + "\n" + "Source File : " + SSourceFileName + "\n" + "Destination File: " + TFDestination.getText() + "\nAlgorithm : " + CBTypeSelected.getValue().toString() + "\nOption : " + SOptionSelect;
            SSummary.setValue(s);
            
                                                                                                                                                                  
        });
        
        THSummary.textProperty().bind(SSummary);
        
    }    

    @FXML
    private void btNextActionHandler(ActionEvent event) throws IOException {
        String Error = "Error:";
        boolean BallOkFlage = false;
        if(TFSource.getText().length() < 1 )
            Error += "\nPlease Select Source File";
        if(TFSource.getText().length() >= 0 ){
            if(!(new File(TFSource.getText()).exists())){
                Error += "\nSource File Does not Exists";
            }
                    
        }
        if(SOptionSelect == ""){
            Error += "\nPlease Select Option";
        }
        if(CBTypeSelected.getValue().toString().length() <= 2 ){
            Error += "\nPlease Select Compresson Type";
        }
        if(TFDestination.getText().length() < 1 )
            Error += "\nPlease Select Destination File";
        
        if(Error == "Error:"){
            BallOkFlage = true;
            Error = "Error:";
        }
        if(BallOkFlage){
            System.out.println("allOk");
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLNEXTWINDOW.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("allOk");
            stage.setScene(scene);
            FXMLNEXTWINDOWController controller = loader.getController();
            controller.initData(TFSource.getText(),TFDestination.getText(),CBTypeSelected.getValue().toString(),SOptionSelect);
            stage.show();
        }
        else{
            System.out.println(Error);
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLERROR.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Error");
            stage.setScene(scene);
            FXMLERRORController controller = loader.getController();
            controller.init(stage, Error);
            stage.show();
        }
        
        
    }

   
    
}
