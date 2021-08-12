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
public class CGZipEncoder {
    
	
	private String fileName,outputFilename;
	private long fileLen,outputFilelen;
	private String gSummary;
        public DoubleProperty progress = new SimpleDoubleProperty(0.0);
        public StringProperty progressString = new SimpleStringProperty("");

		
	public CGZipEncoder(){
		loadFile("","");
		}
	public CGZipEncoder(String txt){
		loadFile(txt);
		}
	public CGZipEncoder(String txt,String txt2){
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = txt + ".gz";
		gSummary = "";
		}
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		gSummary = "";
		}
		
	public boolean encodeFile() throws Exception{
		
		
		if(fileName.length() == 0) return false;
		try{
		FileInputStream in = new FileInputStream(fileName);
		GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(outputFilename));
		fileLen = in.available();
		if(fileLen == 0 ) throw new Exception("Source File Empty!");
		gSummary += "Original Size : " + fileLen + "\n";

		byte[] buf = new byte[1024];
		int len;
                long counter = 0;
                long limiter = fileLen/100;
                long temp = counter;
                Platform.runLater(() -> progressString.setValue("Compressing......."));
		while ((len = in.read(buf)) > 0) {
		out.write(buf, 0, len);
                counter += len;
                if( counter - temp >= limiter){
                                    temp = counter;
                                    final double x = ((double)counter / fileLen);
                                    Platform.runLater(() -> progress.setValue(x));
//                                    progress.set((double)(i / fileLen));

				
                                }

		}
		in.close();
		out.finish();
		out.close();
                
                Platform.runLater(() -> progress.setValue(1.0));
                Platform.runLater(() -> progressString.setValue("Completed"));
		outputFilelen =  new File(outputFilename).length();
		float cratio = (float)(((outputFilelen)*100)/(float)fileLen);
		gSummary += ("Compressed File Size : " + outputFilelen + "\n");
		gSummary += ("Compression Ratio : " + cratio + "%" + "\n");

		}catch(Exception e){throw e; }
		return true;
		}
		
			
	public String getSummary(){
		return gSummary;
		}

	}
