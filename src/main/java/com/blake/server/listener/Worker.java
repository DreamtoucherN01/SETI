package com.blake.server.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.blake.index.Index;
import com.blake.input.InputFromFile;
import com.blake.persistence.PersistanceHandler;

public class Worker implements ServletContextListener{

	public static boolean dataInsert =  true;
	Thread dataimport ;
	
	public void contextDestroyed(ServletContextEvent event) {
		
		Worker.dataInsert = false;
		PersistanceHandler.getInstance().persistMemoryData(dataimport);
	}

	public void contextInitialized(ServletContextEvent event) {
		
		System.out.println("here");
		PersistanceHandler.getInstance().init();
		Index.getInstance().init();
		dataimport = new Thread(new Runnable() {

			public void run() {

				InputFromFile iff = new InputFromFile();
				try {
					
					iff.fileReader();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			
		});
		dataimport.start();
	}

}
