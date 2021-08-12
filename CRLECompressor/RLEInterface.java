/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRLECompressor;

/**
 *
 * @author Parthu
 */
public interface RLEInterface {
    	final int MAXCHARS = 256;
	final int ESCAPECHAR = 255;
	final String strExtension = ".rle";
	final String rleSignature = "RLE";
	final int toleranceFrequency = 4;
    
}
