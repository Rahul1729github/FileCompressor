/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecompressor;

import CGZipCompressor.CGZipDecoder;
import CGZipCompressor.CGZipEncoder;
import CHuffmanCompressor.CHuffmanDecoder;
import CHuffmanCompressor.CHuffmanEncoder;
import CLZWCompressor.CLZWDecoder;
import CLZWCompressor.CLZWEncoder;
import CRLECompressor.CRLEDecoder;
import CRLECompressor.CRLEEncoder;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Parthu
 */
public class FXMLNEXTWINDOWController implements Initializable {

    @FXML
    private Label LBTimeElapsed;
    //Thread dateThread ;
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    Date Starting = new Date();
    boolean isRunning = true;
    private final StringProperty twoWayInput = new SimpleStringProperty("");
    @FXML
    private Label LBSourcePath;
    @FXML
    private Label LBDestinationPath;
    @FXML
    private Label LBFileSize;
    @FXML
    private Label LBAlgo;
    @FXML
    private Label LBAction;
    @FXML
    private ProgressBar PBProgress;
    @FXML
    private Label LBProgress;
    private String optionSelect = "";
    private boolean success  = false;
    @FXML
    private ProgressIndicator PIIndicator;
    

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        // TODO
        LBTimeElapsed.textProperty().bindBidirectional(twoWayInput);
//        new TimeElapsed(isRunning,LBTimeElapsed,PBProgress,LBProgress).start();
//        ActualWork aw = new ActualWork(LBSourcePath.getText(),LBAlgo.getText(),LBDestinationPath.getText(),LBAction,PBProgress,success);
//        LBProgress.textProperty().bind(aw.progressString);
//        aw.start();
        
        /*
        dateThread = new Thread(this :: handleThread); 
        dateThread.start();*/
        
        
        
    }    
    
    public void initData(String sourceFileName , String DestinationFileName , String Algo ,String SOptionSelect){
         LBSourcePath.setText(sourceFileName);
         LBDestinationPath.setText(DestinationFileName);
         LBAlgo.setText(Algo);
         optionSelect = SOptionSelect;
        try {
            LBFileSize.setText(Integer.toString(new BufferedInputStream(new FileInputStream(sourceFileName)).available()));
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
        
        //algorithm
        /*String gSummary = "";
        try{
        switch(LBAlgo.getText()){
            case "HUFFMAN" :
                if(optionSelect =="Compress"){
                    CHuffmanEncoder he = new	CHuffmanEncoder(LBSourcePath.getText(),LBDestinationPath.getText());
//                    PBProgress.progressProperty().bind(he.progress);
//                    he.progressString.addListener((V ,oldValue , newValue ) -> {
//                    LBProgress.setText(newValue);
//                });
                    
                            
                    success = he.encodeFile();
                    gSummary += he.getSummary();
                }
                else if(optionSelect == "Decompress"){
                    CHuffmanDecoder hde = new	CHuffmanDecoder(LBSourcePath.getText(),LBDestinationPath.getText());             
                    success = hde.decodeFile();
                    gSummary += hde.getSummary();
                }
                break;
            case "GZIP" : 
                if(optionSelect == "Compress"){
                    CGZipEncoder gze = new	CGZipEncoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    
                    success  = gze.encodeFile();
                    gSummary += gze.getSummary();
                }else if(optionSelect == "Decompress"){
                    CGZipDecoder gzde = new	CGZipDecoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    success = gzde.decodeFile();
                    gSummary += gzde.getSummary();
                }
                break;
            case "RUNLENGTH":
                if(optionSelect == "Compress"){
                    CRLEEncoder rle = new	CRLEEncoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    success = rle.encodeFile();
                    gSummary += rle.getSummary();
                }else if(optionSelect == "Deccompress"){
                    CRLEDecoder unrle = new	CRLEDecoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    success = unrle.decodeFile();
                    gSummary += unrle.getSummary();
                }
                break;
            case "LZW":
                if(optionSelect == "Compress"){
                    CLZWEncoder lzwe = new	CLZWEncoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    success = lzwe.encodeFile();
                    gSummary += lzwe.getSummary();
                }
                else if(optionSelect == "Decompress"){
                    CLZWDecoder lzwd = new	CLZWDecoder(LBSourcePath.getText(),LBDestinationPath.getText());
                    success = lzwd.decodeFile();
                    gSummary += lzwd.getSummary();
                }
                break;
        }
        System.out.println(gSummary);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Success : " + success);
         
         */
         System.out.println("daata arrived");
          new TimeElapsed(isRunning,LBTimeElapsed,PBProgress,LBProgress).start();
         ActualWork aw = new ActualWork(LBSourcePath.getText(),LBAlgo.getText(),LBDestinationPath.getText(),optionSelect,LBProgress,PBProgress,success,LBAction);
        Platform.runLater(() -> LBProgress.textProperty().bind(aw.progressString));
        Platform.runLater(() -> PBProgress.progressProperty().bind(aw.progress));        
        Platform.runLater(() -> PIIndicator.progressProperty().bind(aw.progress));
//        LBProgress.textProperty().bind(aw.progressString);

        aw.start();
    }
   

}


class TimeElapsed extends Service<Integer>{
    
    DateFormat df = new SimpleDateFormat("HH:mm:ss");
    Date Starting = new Date();
    boolean isRunning = true;
    private Label LBTimeElapsed;
    ProgressBar PBProgress;
    Label LBProgress;
    public TimeElapsed(boolean IR,Label sp, ProgressBar PB,Label p){
        
        isRunning = IR;
        LBTimeElapsed = sp;
        Integer i = this.getValue();
        PBProgress = PB;
        LBProgress = p;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>(){
            @Override
            protected Integer call() throws Exception {
                long startingTime = new Date().getTime();
                double d = 0;
                DoubleProperty dp  = new SimpleDoubleProperty( d );
//                PBProgress.progressProperty().bind(dp);
//                StringProperty sp = new SimpleStringProperty(Double.toString(100*d) + "%");
                
        while(isRunning){
            String datestr = (df.format((System.currentTimeMillis() - startingTime - 19800000  )));
            String s = Integer.toString((int)(100*d)) + "%";
             Platform.runLater(() -> {
                 LBTimeElapsed.setText(datestr);
                 
                 });
             d = d + 0.01;
//            dp.set(d);
           
             
             
             
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Exception");
            }   
            if(d >= 1.01){
                break;
            }
    }
        return 0;
            }
     
    };
}
}
