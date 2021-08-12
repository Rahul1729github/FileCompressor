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
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Parthu
 */
public class ActualWork extends Service<Boolean>{

    String optionSelect = "";
    String LBAlgo;
    boolean isRunning = true;
    private String LBSourcePath;
    private String LBDestinationPath;
    private Label LBProgress;
    private Label LBAction;
    private ProgressBar PBProgress;
    private boolean success  = false;
    StringProperty progressString = new SimpleStringProperty("");
    
    DoubleProperty progress = new SimpleDoubleProperty(0.0);
    public ActualWork(String LBSourcePath,String LBAlgo, String LBDestinationPath,String optionSelect,Label LBProgress, ProgressBar PBProgress,boolean success,Label LBAction){
        this.LBProgress = LBProgress;
        this.LBAlgo = LBAlgo;
        this.LBDestinationPath = LBDestinationPath;
        this.LBSourcePath = LBSourcePath;
        this.PBProgress = PBProgress;
        this.isRunning = isRunning;
        this.optionSelect = optionSelect;
        this.success = success;
        this.LBAction = LBAction;
        System.out.println("optionselect " + optionSelect);
        
    }
    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>(){
             @Override
            protected Boolean call() throws Exception {
                 
                String gSummary = "";
        try{
        switch(LBAlgo){
            case "HUFFMAN" :
                if(optionSelect =="Compress"){
                    CHuffmanEncoder he = new	CHuffmanEncoder(LBSourcePath,LBDestinationPath);
                    progressString = he.progressString;
                    progress = he.progress;
                    
                            
                    success = he.encodeFile();
                    gSummary += he.getSummary();
                }
                else if(optionSelect == "Decompress"){
                    CHuffmanDecoder hde = new	CHuffmanDecoder(LBSourcePath,LBDestinationPath);
                    progressString = hde.progressString;
                    progress = hde.progress;
                    success = hde.decodeFile();
                    gSummary += hde.getSummary();
                }
                break;
            case "GZIP" : 
                if(optionSelect == "Compress"){
                    CGZipEncoder gze = new	CGZipEncoder(LBSourcePath,LBDestinationPath);
                    progressString = gze.progressString;
                    progress = gze.progress;
                    
                    success  = gze.encodeFile();
                    gSummary += gze.getSummary();
                }else if(optionSelect == "Decompress"){
                    CGZipDecoder gzde = new	CGZipDecoder(LBSourcePath,LBDestinationPath);
                    progressString = gzde.progressString;
                    progress = gzde.progress;
                    
                    success = gzde.decodeFile();
                    gSummary += gzde.getSummary();
                }
                break;
            case "RUNLENGTH":
                if(optionSelect == "Compress"){
                    CRLEEncoder rle = new	CRLEEncoder(LBSourcePath,LBDestinationPath);
                    progressString = rle.progressString;
                    progress = rle.progress;
                    
                    success = rle.encodeFile();
                    gSummary += rle.getSummary();
                }else if(optionSelect == "Decompress"){
                    CRLEDecoder unrle = new	CRLEDecoder(LBSourcePath,LBDestinationPath);
                    progressString = unrle.progressString;
                    progress = unrle.progress;
                    
                    success = unrle.decodeFile();
                    gSummary += unrle.getSummary();
                }
                break;
            case "LZW":
                if(optionSelect == "Compress"){
                    CLZWEncoder lzwe = new	CLZWEncoder(LBSourcePath,LBDestinationPath);
                     progressString = lzwe.progressString;
                    progress = lzwe.progress;
                    
                    success = lzwe.encodeFile();
                    gSummary += lzwe.getSummary();
                }
                else if(optionSelect == "Decompress"){
                    CLZWDecoder lzwd = new	CLZWDecoder(LBSourcePath,LBDestinationPath);
                     progressString = lzwd.progressString;
                    progress = lzwd.progress;
                    
                    success = lzwd.decodeFile();
                    gSummary += lzwd.getSummary();
                }
                break;
        }
        LBAction.setText(gSummary);
        System.out.println(gSummary);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Success : " + success);
         
                
                return success;
            }
        };
    }
    
    
}