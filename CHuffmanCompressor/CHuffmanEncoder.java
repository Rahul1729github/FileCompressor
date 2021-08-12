/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHuffmanCompressor;

import java.io.*;
import FileBitIO.CFileBitWriter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import filecompressor.ActualWork;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Parthu
 */
public class CHuffmanEncoder implements huffmanSignature{
    
	
	
	private String fileName,outputFilename;
	private HuffmanNode root = null;
	private long[] freq  = new long[MAXCHARS];
	private String[] hCodes = new String[MAXCHARS];
	private int distinctChars = 0;
	private long fileLen=0,outputFilelen;
	private FileInputStream fin;
	private BufferedInputStream in;	
	private String gSummary;
        public DoubleProperty progress = new SimpleDoubleProperty(0.0);
        public StringProperty progressString = new SimpleStringProperty("");
        

    public DoubleProperty getProgress() {
        return progress;
    }

    public void setProgress(DoubleProperty progress) {
        this.progress = progress;
    }

    public StringProperty getProgressString() {
        return progressString;
    }

    public void setProgressString(StringProperty progressString) {
        this.progressString = progressString;
    }

	
	void resetFrequency(){
		for(int i=0;i<MAXCHARS;i++)
		freq[i] = 0;
		
		distinctChars = 0;
		fileLen=0;
		gSummary ="";
		}
	
	public CHuffmanEncoder(){
		loadFile("","");
		}
	public CHuffmanEncoder(String txt){
		loadFile(txt);
		}
	public CHuffmanEncoder(String txt,String txt2){
            
		loadFile(txt,txt2);
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = txt + strExtension;
		resetFrequency();
		}
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		resetFrequency();
		}
	public boolean encodeFile() throws Exception{
		
		if(fileName.length() == 0) return false;
		try{
		fin = new FileInputStream(fileName);
		in = new BufferedInputStream(fin);
		}catch(Exception e){ throw e; }
		
		//Frequency Analysis
                Platform.runLater(() -> progressString.setValue("Performing Frequency Analysis"));
//                progressString.set("Performing Frequency Analysis");

//                progress.set(0.0);
                Platform.runLater(() -> progress.setValue(0.0));
  		try
		{
			fileLen = in.available();
			if(fileLen == 0) throw new Exception("File is Empty!");
			gSummary += ("Original File Size : "+ fileLen + "\n");
			
			long i=0;

			in.mark((int)fileLen);
			distinctChars = 0;
			long limiter = fileLen/100;
                        long temp = i;  
			while (i < fileLen)
			{		
				int ch = in.read();			
				i++;
				if(freq[ch] == 0) distinctChars++;
				freq[ch]++;
                                
                                if( i - temp >= limiter){
                                    temp = i;
                                    final double x = ((double)i / fileLen);
                                    Platform.runLater(() -> progress.setValue(x));
//                                    progress.set((double)(i / fileLen));

				
                                }
			}
			in.reset();			
		}
		catch(IOException e)
		{
			throw e;
			//return false;
		}
		gSummary += ("Distinct Chars " + distinctChars + "\n");
		/*
		System.out.println("distinct Chars " + distinctChars);
		 //debug
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0)
			System.out.println(i + ")" + (char)i + " : " + freq[i]);
		}
		*/
		
		CPriorityQueue  cpq = new CPriorityQueue (distinctChars+1);
                
		Platform.runLater(() -> progressString.setValue("Creating Huffman Codes"));
//                progressString.set("Creating Huffman Codes");
		int count  = 0 ;
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0){
				count ++;
				//System.out.println("ch = " + (char)i + " : freq = " + freq[i]);
				HuffmanNode np = new HuffmanNode(freq[i],(char)i,null,null);
				cpq.Enqueue(np);
				}
		}
		
		//cpq.displayQ();
		
		HuffmanNode low1,low2;
		
		while(cpq.totalNodes() > 1){
			low1 = cpq.Dequeue();
			low2 = cpq.Dequeue();
			if(low1 == null || low2 == null) { throw new Exception("PQueue Error!"); }
			HuffmanNode intermediate = new HuffmanNode((low1.freq+low2.freq),(char)0,low1,low2);
			if(intermediate == null) { throw new Exception("Not Enough Memory!"); }
			cpq.Enqueue(intermediate);

		}
		
		//cpq.displayQ();
		//root = new HuffmanNode();
		root = cpq.Dequeue();
		buildHuffmanCodes(root,"");
		
		for(int i=0;i<MAXCHARS;i++) hCodes[i] = "";
		getHuffmanCodes(root);
		
		/*
		//debug		
		for(int i=0;i<MAXCHARS;i++){
		if(hCodes[i] != ""){ 
		System.out.println(i + " : " + hCodes[i]);
		}
		}
		*/
		
		CFileBitWriter hFile = new CFileBitWriter(outputFilename);
		
		hFile.putString(hSignature);
		String buf;
		buf = leftPadder(Long.toString(fileLen,2),32); //fileLen
		hFile.putBits(buf);
		buf = leftPadder(Integer.toString(distinctChars-1,2),8); //No of Encoded Chars
		hFile.putBits(buf);
		
		for(int i=0;i<MAXCHARS;i++){
			if(hCodes[i].length() != 0){
				buf = leftPadder(Integer.toString(i,2),8);
				hFile.putBits(buf);
				buf = leftPadder(Integer.toString(hCodes[i].length(),2),5);
				hFile.putBits(buf);
				hFile.putBits(hCodes[i]);
				}
			}
		
		long lcount = 0;
                Platform.runLater(() -> progressString.setValue("Compressing....."));
//                progressString.setValue("Compressing.....");
                
			long limiter = fileLen/100;
                        long temp = lcount;  
		while(lcount < fileLen){
			int ch = in.read();
			hFile.putBits(hCodes[(int)ch]);
			lcount++;
                        if( lcount - temp >= limiter){
                            temp = lcount;
                            final double x = ((double)lcount / fileLen);
                            Platform.runLater(() -> progress.setValue(x));
//                            progress.set((double)(lcount / fileLen));
                        }
		}
		hFile.closeFile();
		outputFilelen =  new File(outputFilename).length();
		float cratio = (float)(((outputFilelen)*100)/(float)fileLen);
		gSummary += ("Compressed File Size : " + outputFilelen + "\n");
		gSummary += ("Compression Ratio : " + cratio + "%" + "\n");
                
                Platform.runLater(() -> progress.setValue(1.0));
                
                Platform.runLater(() -> progressString.setValue("Completed"));
//                progressString.set("Completed");
		return true;
		
		}
	
	void buildHuffmanCodes(HuffmanNode parentNode,String parentCode){

		parentNode.huffCode = parentCode;
		if(parentNode.left != null)
			buildHuffmanCodes(parentNode.left,parentCode + "0");
		
		if(parentNode.right != null)
			buildHuffmanCodes(parentNode.right,parentCode + "1");
		}
	
	void getHuffmanCodes(HuffmanNode parentNode){
		
		if(parentNode == null) return;
		
		int asciiCode = (int)parentNode.ch;
		if(parentNode.left == null || parentNode.right == null)
		hCodes[asciiCode] = parentNode.huffCode;
		
		if(parentNode.left != null ) getHuffmanCodes(parentNode.left);
		if(parentNode.right != null ) getHuffmanCodes(parentNode.right);
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
