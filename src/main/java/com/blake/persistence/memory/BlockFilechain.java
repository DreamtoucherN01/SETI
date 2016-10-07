package com.blake.persistence.memory;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.blake.util.Constants;

public class BlockFilechain{

	static Logger logger = Logger.getLogger(BlockFilechain.class);
	
	static ConcurrentHashMap<Integer, HashSet<File>> fileChain =  new ConcurrentHashMap<Integer, HashSet<File>>();
	
	static HashMap<Integer, Integer> fileToUse =  new HashMap<Integer, Integer>();

	public static File getUsefulFile(int blockId) {
		
		if(!fileToUse.containsKey(blockId)) {
			
			File ret = new File(Constants.DATA, blockId + "-1");
			fileToUse.put(blockId, 1);
			return ret;
		}
		int id = fileToUse.get(blockId);
		File ret = new File(Constants.DATA, blockId + "-" + id);
		if(ret.length() > Constants.FILELEN) {
			
			System.out.println("file is full change " + ret.getName() + " ,filelength : " + ret.length());
			if(logger.isDebugEnabled()) {
				
				logger.debug("file is full change " + ret.getName() + " ,filelength : " + ret.length());
			}
			if(null == fileChain.get(blockId)) {
				
				HashSet<File> files = new HashSet<File>();
				files.add(ret);
				fileChain.put(blockId, files);
			} else {
			
				fileChain.get(blockId).add(ret);
			}
			ret = new File(Constants.DATA, blockId + "-" + id + 1);
			fileToUse.put(blockId, id + 1);
		}
		return ret;
	}
	
	public static ConcurrentHashMap<Integer, HashSet<File>> getFileChain() {
		return fileChain;
	}

	public static void setFileChain(ConcurrentHashMap<Integer, HashSet<File>> fileChain) {
		BlockFilechain.fileChain = fileChain;
	}

	public static HashMap<Integer, Integer> getFileToUse() {
		return fileToUse;
	}

	public static void setFileToUse(HashMap<Integer, Integer> fileToUse) {
		BlockFilechain.fileToUse = fileToUse;
	}

	@Override
	public String toString() {
		
		return "BlockFilechain [fileChain=" + fileChain + ", fileToUse="
				+ fileToUse + "]";
	}
	
}
