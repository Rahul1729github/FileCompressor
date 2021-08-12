/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CHuffmanCompressor;

/**
 *
 * @author Parthu
 */
public class HuffmanNode {
    
	
	HuffmanNode left,right;
	public long freq;
	public char ch;
	
	//Code Words
	public String huffCode;
	
	
	public HuffmanNode(){
		freq = 0;
		ch = 0;
		huffCode = "";
		left = null;
		right = null;
		}
	public HuffmanNode(long xfreq,char xch,HuffmanNode lchild,HuffmanNode rchild){
		freq = xfreq;
		ch = xch;
		left = lchild;
		right = rchild;
		huffCode = "";
		}

	
    
}
