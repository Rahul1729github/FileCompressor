/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CGZipCompressor;

import java.io.*;
import java.util.zip.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Parthu
 */
public class CGZipDecoder {
    
    /** Creates a new instance of CGZipDecoder */
  
        
     	private String fileName,outputFilename;
	private long fileLen,outputFilelen;
	private String gSummary;
        public DoubleProperty progress = new SimpleDoubleProperty(0.0);
        public StringProperty progressString = new SimpleStringProperty("");

		
	public CGZipDecoder(){
		loadFile("","");
		}
	public CGZipDecoder(String txt){
		loadFile(txt);
		}
	public CGZipDecoder(String txt,String txt2){
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = stripExtension(txt,".gz");
		gSummary = "";
		}
		
	String stripExtension(String ff,String ext){
		ff = ff.toLowerCase();
		if(ff.endsWith(ext.toLowerCase())){
			return ff.substring(0,ff.length()-ext.length());
			}
		return ff + ".dat";
		}
		
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		gSummary = "";
		}
	
	
	public boolean decodeFile()throws Exception{
		// Ripped Code Follows
		//Code By Kulvir Singh Bhogal and Javid Jamae
		
		try{
			
		fileLen = new File(fileName).length();
		
		GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(fileName));
		// Open the output file
		
		OutputStream out = new FileOutputStream(outputFilename);
 

		// Transfer bytes from the compressed file to the output file
		byte[] buf = new byte[1024];
		int len;
                long counter = 0;
                long limiter = fileLen/100;
                long temp = counter;
                Platform.runLater(() -> progressString.setValue("Decompressing......."));
		while ((len = gzipInputStream.read(buf)) > 0) {
		out.write(buf, 0, len);
                counter += len;
                if( counter - temp >= limiter){
                                    temp = counter;
                                    final double x = ((double)counter / fileLen);
                                    Platform.runLater(() -> progress.setValue(x));
//                                    progress.set((double)(i / fileLen));

				
                                }
		}
 

		// Close the file and stream
		gzipInputStream.close();
		out.close();
		outputFilelen  = new File(outputFilename).length();
		gSummary  += ("Compressed File Size : "+ fileLen + "\n");
		gSummary  += ("Original   File Size : "+ outputFilelen + "\n");
		
                Platform.runLater(() -> progress.setValue(1.0));
                Platform.runLater(() -> progressString.setValue("Completed"));

		}catch(Exception e){throw e;}
		
		return true;
		}
	
	public String getSummary(){
		return gSummary;
		}

	}
   
    
    

