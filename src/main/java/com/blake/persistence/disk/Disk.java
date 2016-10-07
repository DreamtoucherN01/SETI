/**
 * 
 */
/**
 * @author cheung
 *
 */
package com.blake.persistence.disk;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.blake.persistence.memory.BlockFilechain;
import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.util.Constants;
import com.blake.util.FileUtils;

public class Disk{
	
	Logger logger = Logger.getLogger(Disk.class);
	
	public void persistOnDisk(int blockid, HashSet<Segement> data) {
		
		File file = new File(Constants.DATA);
		if(!file.exists()) {
			
			file.mkdir();
		}
		file = BlockFilechain.getUsefulFile(blockid);
		if(!file.exists()) {
			
			try {
				
				file.createNewFile();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			FileUtils.write(file, data);
		} else {
			
			@SuppressWarnings("unchecked")
			HashSet<Segement> set = (HashSet<Segement>) FileUtils.read(file);
			if(set == null) {
				
				set = new HashSet<Segement>();
			}
			set.addAll(data);
			FileUtils.write(file, set);
		}
	}
	
	public void persistOnDisk(String name,
			HashMap<Integer, TimeInterval> blockTimeInterval) {

		File file = new File(Constants.DATA);
		if(!file.exists()) {
			
			file.mkdir();
		}
		file = new File(Constants.DATA, name);
		if(!file.exists()) {
			
			try {
				
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FileUtils.write(file, blockTimeInterval);
		} else {
			
			@SuppressWarnings("unchecked")
			HashMap<Integer, TimeInterval> set = (HashMap<Integer, TimeInterval>) FileUtils.read(file);
			if(set == null) {
				
				set = new HashMap<Integer, TimeInterval>();
			}
			set.putAll(blockTimeInterval);
			FileUtils.write(file, set);
		}
	}

	public void persistOnDisk(String name,
			ConcurrentHashMap<Integer, HashSet<File>> fileChain, int type) {

		File file = new File(Constants.DATA);
		if(!file.exists()) {
			
			file.mkdir();
		}
		file = new File(Constants.DATA, name);
		if(!file.exists()) {
			
			try {
				
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FileUtils.write(file, fileChain);
		} else {
			
			@SuppressWarnings("unchecked")
			ConcurrentHashMap<Integer, HashSet<File>> set = (ConcurrentHashMap<Integer, HashSet<File>>) FileUtils.read(file);
			if(set == null) {
				
				set = new ConcurrentHashMap<Integer, HashSet<File>>();
			}
			set.putAll(fileChain);
			FileUtils.write(file, set);
		}
	}

	public void persistOnDisk(String name, HashSet<File> fileSet, int type) {

		File file = new File(Constants.DATA);
		if(!file.exists()) {
			
			file.mkdir();
		}
		file = new File(Constants.DATA, name);
		if(!file.exists()) {
			
			try {
				
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FileUtils.write(file, fileSet);
		} else {
			
			@SuppressWarnings("unchecked")
			HashSet<File> set = (HashSet<File>) FileUtils.read(file);
			if(set == null) {
				
				set = new HashSet<File>();
			}
			set.addAll(fileSet);
			FileUtils.write(file, set);
		}
	}
	
	public void persistOnDisk(String name, HashMap<Integer, Integer> fileToUse, String type) {

		File file = new File(Constants.DATA);
		if(!file.exists()) {
			
			file.mkdir();
		}
		file = new File(Constants.DATA, name);
		if(!file.exists()) {
			
			try {
				
				file.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			FileUtils.write(file, fileToUse);
		} else {
			
			@SuppressWarnings("unchecked")
			HashMap<Integer, Integer> set = (HashMap<Integer, Integer>) FileUtils.read(file);
			if(set == null) {
				
				set = new HashMap<Integer, Integer>();
			}
			set.putAll(fileToUse);
			FileUtils.write(file, set);
		}
	}
	
	public HashSet<Segement> fetchDataByBlock(String blockid) {
	
		HashSet<Segement> set = new HashSet<Segement>();
		
		int id = BlockFilechain.getFileToUse().get(Integer.valueOf(blockid));
		File filetouse = new File(Constants.DATA,  blockid + "-" + id);
			
		@SuppressWarnings("unchecked")
		HashSet<Segement> segements = (HashSet<Segement>) FileUtils.read(filetouse);
		if(segements != null) {
			
			set.addAll(segements);
		}
		
		synchronized(BlockFilechain.getFileChain()) {
			
			final HashSet<File>  files = BlockFilechain.getFileChain().get(Integer.valueOf(blockid));
			
			if(null != files) {
				
				System.out.println("we get " + files.size() + " files" );
				if(logger.isDebugEnabled()) {
					
					logger.debug("we get " + files.size() + " files" );
				}
				
				for(File f : files) {
					
					synchronized(f){
						
						@SuppressWarnings("unchecked")
						HashSet<Segement> segements1 = (HashSet<Segement>) FileUtils.read(f);
						if(segements1 != null) {
							
							set.addAll(segements1);
						}
					}
				}
			}
		}
		

		return set;
	}

	public int fetchNumberByBlock(String blockid) {

		int num = 0;
		
		int id = BlockFilechain.getFileToUse().get(Integer.valueOf(blockid));
		File filetouse = new File(Constants.DATA,  blockid + "-" + id);
			
		@SuppressWarnings("unchecked")
		HashSet<Segement> segements = (HashSet<Segement>) FileUtils.read(filetouse);
		if(segements != null) {
			
			num += segements.size();
		}
		
		synchronized(BlockFilechain.getFileChain()) {
			
			final HashSet<File>  files = BlockFilechain.getFileChain().get(Integer.valueOf(blockid));
			
			if(null != files) {
				
				System.out.println("we get " + files.size() + " files" );
				if(logger.isDebugEnabled()) {
					
					logger.debug("we get " + files.size() + " files" );
				}
				
				for(File f : files) {
					
					synchronized(f){
						
						@SuppressWarnings("unchecked")
						HashSet<Segement> segements1 = (HashSet<Segement>) FileUtils.read(f);
						if(segements1 != null) {
							
							num += segements1.size();
						}
					}
				}
			}
		}
		
		System.out.println("	got " + num + " segements");
		return num;
	}
	
}