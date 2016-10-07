package com.blake.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.blake.persistence.PersistanceHandler;
import com.blake.procedure.seti.tuple.Tuple;
import com.blake.server.listener.Worker;
import com.blake.util.Constants;

public class InputFromFile {
	
	Logger logger = Logger.getLogger(InputFromFile.class);
	
	public void fileReader() throws IOException {
		
		File file = new File(Constants.PATH);
		if(!file.isDirectory()) {
			
			System.out.println("path is not right");
		}
		int dealedSize = PersistanceHandler.getInstance().getMemory().getFileSet().size();
		System.out.println("we got {} files " + file.listFiles().length + " we have dealed " + dealedSize + " file");
		if(logger.isDebugEnabled()) {
			
			logger.debug("we got {} files " + file.listFiles().length + " we have dealed " + dealedSize + " file");
		}
		
		for(File f : file.listFiles()) {
			
			if(PersistanceHandler.getInstance().getMemory().getFileSet().contains(f)) {
				
				if(logger.isDebugEnabled()) {
					
					logger.debug("already deal with such file : " + f.getName());
				}
				continue;
			}
			
			if(f.isFile()) {
				
				PersistanceHandler.getInstance().getMemory().getFileSet().add(f);
				System.out.println("deal with file " + f.getName() + " left " 
						+ (file.listFiles().length - PersistanceHandler.getInstance().getMemory().getFileSet().size()) );
				if(logger.isDebugEnabled()) {
					
					logger.debug("deal with file " + f.getName() + " left " 
							+ (file.listFiles().length - PersistanceHandler.getInstance().getMemory().getFileSet().size()) );
				}
				
				FileReader fr = new FileReader(f);
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(fr);
				String line = null;
				while((line = reader.readLine()) != null) {
					
					String [] arr = line.split(",");
					if(arr.length < 6) {
						
						continue;
					}
					persistData(arr[0], arr[3], arr[4], arr[5]);
				}
			}
			if(!Worker.dataInsert) {
				
				break;
			}
		}
		
		System.out.println("finished deal with file");
		if(logger.isDebugEnabled()) {
			
			logger.debug("finished deal with file");
		}
		
	}

	private void persistData(String tid, String time, String longi, String lati) {

		Tuple tuple = new Tuple(Double.valueOf(longi), Double.valueOf(lati), Long.valueOf(time));
		PersistanceHandler.getInstance().persist(tid, tuple);
	}

}
