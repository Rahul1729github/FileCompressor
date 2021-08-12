/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLZWCompressor;

import java.io.*;
import java.util.*;

import FileBitIO.CFileBitReader;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Parthu
 */
public class CLZWDecoder implements LZWInterface{
    
    private String inputFilename,outputFilename;
	private long inputFilelen,outputFilelen;
	private String gSummary = "";
	
        public DoubleProperty progress = new SimpleDoubleProperty(0.0);
        public StringProperty progressString = new SimpleStringProperty("");
	
	private FileOutputStream fout;
	private BufferedOutputStream out;
	
	
    
    /** Creates a new instance of CLZWDecoder */
   	public CLZWDecoder(){
		gSummary = "";
		inputFilename = "";
		outputFilename = "";
		inputFilelen = 0;
		outputFilelen = 0;
		}

	public CLZWDecoder(String txt){
		gSummary = "";
		inputFilename = txt;
		outputFilename = txt + strExtension;
		inputFilelen = 0;
		outputFilelen = 0;
		}
		
	public CLZWDecoder(String txt,String txt2){
		gSummary = "";
		inputFilename = txt;
		outputFilename = txt2;
		inputFilelen = 0;
		outputFilelen = 0;
		}
		
	public boolean decodeFile() throws Exception{
		
		if(inputFilename.length() == 0) return false;
		CFileBitReader in;
		
		try{
			in = new CFileBitReader(inputFilename);
			
			fout = new FileOutputStream (outputFilename);
			out = new BufferedOutputStream(fout);
		}catch(Exception e){
			throw e;
		}
		
		try{
			gSummary = "";		
		
		inputFilelen = in.available();
		if(inputFilelen == 0 ) throw new Exception("\nFile is Empty!");
		gSummary += "Compressed File Size : " + inputFilelen +"\n";
		
		
        Hashtable <Integer,String> table = new Hashtable<Integer,String>();
   		
   		
   		for(int k=0;k<MAXCHARS;k++){
        	String buf = "" + (char)k;
        	table.put(new Integer(k),buf);
        }
        
		String sig = in.getBytes(lzwSignature.length());
		if(!sig.equals(lzwSignature)) return false;
		outputFilelen = Long.parseLong(in.getBits(32),2);
		//System.out.println("Original Size : " + outputFilelen + "\n");
		
		long i = 0;
		int codesUsed = MAXCHARS;
		int encodedCodeword = 0;
		String prevStr,codeWord = "";
		prevStr = "";

		//first byte		
		encodedCodeword = Integer.parseInt(in.getBits(MAXBITS),2);
		String encodedString = table.get(encodedCodeword );
		out.write(encodedString.getBytes());
		i += encodedString.length();
		codeWord = encodedString;
		
                long limiter = outputFilelen/100;
                long temp1 = i; 
                
                Platform.runLater(() -> progressString.setValue("Decompressing....."));
                
		while(i < outputFilelen){
			encodedCodeword = Integer.parseInt(in.getBits(MAXBITS),2);
			encodedString = table.get(encodedCodeword );
                        if( i - temp1 >= limiter){
                                    temp1 = i;
                                    final double x = ((double)i / outputFilelen);
                                    Platform.runLater(() -> progress.setValue(x));				
                                }


			if(encodedString != null){
					prevStr = encodedString;
			}else{
					prevStr = codeWord;
					prevStr = prevStr + codeWord.charAt(0);
			}
			
			//for(int j=0;j<prevStr.length();j++) System.out.print((int)prevStr.charAt(j) + ",");
			//System.out.println("");
			
			for(int j=0;j<prevStr.length();j++)
			out.write(prevStr.charAt(j));
			
			i += prevStr.length();
			if(codesUsed < MAXCODES) table.put(codesUsed++,codeWord + prevStr.charAt(0));
			codeWord = prevStr;
			}
		
		out.close();
                
                Platform.runLater(() -> progress.setValue(1.0));
                
                Platform.runLater(() -> progressString.setValue("Completed"));
		}catch(Exception e){
			
			throw e;
		}

			outputFilelen =  new File(outputFilename).length();
			gSummary += ("Original File Size : " + outputFilelen + "\n");

			return true;		
		}

		
	String leftPadder(String txt,int n){
		while(txt.length() < n )
			txt =  "0" + txt;
		return txt;
		}
	
	String rightPadder(String txt,int n){
		while(txt.length() < n )
			txt += "0";
		return txt;
		}

	public String getSummary(){
		return gSummary;
		}
    
}

