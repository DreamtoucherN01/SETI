package com.blake.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class FileUtils {

	static Logger logger = Logger.getLogger(FileUtils.class);
	
	public static Object read(File file) {

		if(!file.exists() || file.length() == 1) {
			
			return null;
		}
		
		try {
			
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
			Object temp =  is.readObject();
			is.close();
			return temp;
			
		}  catch (Exception e) {

			e.printStackTrace();
			System.out.println("catch exception in reading object  " + file.getName() + " " + file.length());
			if(logger.isDebugEnabled()) {
				
				logger.debug("catch exception in reading object  " + file.getName() + " " + file.length());
			}
		} 
		return null;
	}
	
	public static void write(File file, Object data) {
		
		if(data == null) {
			
			return;
		}
		
		if(file.exists()) {
			
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(data);
			os.flush();
			os.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
