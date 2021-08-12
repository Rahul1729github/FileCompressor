/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRLECompressor;
import java.io.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Parthu
 */
public class CRLEDecoder implements RLEInterface{
	
	private String inputFilename,outputFilename;
	private long fileLen,outputFilelen;
        public DoubleProperty progress = new SimpleDoubleProperty(0.0);
        public StringProperty progressString = new SimpleStringProperty("");
	
	private FileInputStream fin;
	private FileOutputStream fout;
	private BufferedInputStream in;
	private BufferedOutputStream out;
	private String gSummary = "";
	
	
	public CRLEDecoder(){
		inputFilename = "";
		outputFilename = "";
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEDecoder(String txt){
		inputFilename = txt;
		outputFilename = txt + strExtension;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
		
	public CRLEDecoder(String txt,String txt2){
		inputFilename = txt;
		outputFilename = txt2;
		fileLen = 0;
		outputFilelen = 0;
		gSummary = "";
		}
	
	public boolean decodeFile() throws Exception {
		
		if(inputFilename.length() == 0) return false;
		
		try{
			fin = new FileInputStream(inputFilename);
			in = new BufferedInputStream(fin);
			
			fout = new FileOutputStream(outputFilename);
			out = new BufferedOutputStream(fout);
			
		}catch(Exception e){
			throw e;
		}
		
		try{
		fileLen = in.available();
		if(fileLen == 0) throw new Exception("\nFile is Empty!");
		
		gSummary += "Compressed File Size : " + fileLen + "\n";
		
		byte[] sig = new byte[rleSignature.length()];
		String buf = "";
		long i = 0;
		int ch,count;
		
		in.read(sig,0,rleSignature.length());
		buf = new String(sig);
		
		if(!rleSignature.equals(buf)) return false;
		
		i = rleSignature.length();
		 Platform.runLater(() -> progressString.setValue("Decompressing....."));
                
                long limiter = fileLen/100;
                long temp1 = i; 
                
		while(i < fileLen){
			ch = in.read(); 
			i++;
                        if( i - temp1 >= limiter){
                                    temp1 = i;
                                    final double x = ((double)i / fileLen);
                                    Platform.runLater(() -> progress.setValue(x));

				
                                }
				
			if(ch == ESCAPECHAR && i < fileLen){
				ch = in.read();
				count = in.read();
				i += 2;
				for(int k=0;k<count;k++) out.write((char)ch);
			}else{
				out.write((char)ch);
			}
						
		}
		out.close();
		
                Platform.runLater(() -> progress.setValue(1.0));
                
                Platform.runLater(() -> progressString.setValue("Completed"));
		outputFilelen = new File(outputFilename).length();
		gSummary += "Original File Size : " + outputFilelen + "\n";
		
		}catch(Exception e){
			throw e;
		}
		
		return true;
		
		}//encode file
		
	public String getSummary(){
		return gSummary;
		}
	
	}


