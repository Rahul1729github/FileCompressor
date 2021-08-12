/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLZWCompressor;

/**
 *
 * @author Parthu
 */
public interface LZWInterface {
    
    	final int MAXCHARS = 256;
	final String strExtension = ".lzw";
	final String lzwSignature = "LZW";
	final int MAXCODES = 4096;
	final int MAXBITS = 12;
    
}

