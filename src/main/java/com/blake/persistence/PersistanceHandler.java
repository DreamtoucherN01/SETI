package com.blake.persistence;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.blake.algorithm.MyHilbert;
import com.blake.index.Index;
import com.blake.persistence.memory.BlockFilechain;
import com.blake.persistence.memory.Memory;
import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.procedure.seti.tuple.Tuple;
import com.blake.util.Constants;
import com.blake.util.FileUtils;

public class PersistanceHandler {
	
	Logger logger = Logger.getLogger(PersistanceHandler.class);
	
	public static PersistanceHandler persistanceHandler;
	
	private Memory memory;
	
	public static PersistanceHandler getInstance() {
		
        if (persistanceHandler == null) {    
        	
            synchronized (PersistanceHandler.class) { 
            	
               if (persistanceHandler == null) {
            	   
            	   persistanceHandler = new PersistanceHandler();   
               }    
            }    
        }    
        
        return persistanceHandler;   
    }  
	
	public PersistanceHandler() {
		
		memory = new Memory();
		
	}

	@SuppressWarnings("unchecked")
	public void init() {

		HashSet<File>  file =  (HashSet<File>) FileUtils.read(new File(Constants.DATA, "fileDealed"));
		if(file == null) {
			
			memory.setFileSet(new HashSet<File>());
		} else {
			
			memory.setFileSet(file);
		}
		
		ConcurrentHashMap<Integer, HashSet<File>> fileChain = (ConcurrentHashMap<Integer, HashSet<File>>) FileUtils.read(new File(Constants.DATA, Constants.FILECHAIN));
		if(fileChain != null) {
			
			BlockFilechain.setFileChain(fileChain);
		} 
		
		HashMap<Integer, Integer> filetouse = (HashMap<Integer, Integer>) FileUtils.read(new File(Constants.DATA, Constants.FILETOUSE));
		if(filetouse != null) {
			
			BlockFilechain.setFileToUse(filetouse);
		}
		
		System.out.println("PersistanceHandler inited");
	}
	

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public HashSet<Segement> query(long time) {
		return null;
		
	}

	public void persist(String tid, Tuple tuple) {

		if(memory.getFrontLine().get(tid) == null) {
			
			memory.getFrontLine().put(tid, tuple);
			
		} else {
			
			formSegement(tid, tuple);
		}
		
	}

	private void formSegement(String tid, Tuple tuple) {

		int blockX = MyHilbert.GetHilbertValue(tuple.x, tuple.y);
		Tuple tupleInFront = memory.getFrontLine().get(tid);
		int blockY = MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y);
		
		if(blockX != blockY) {

			MyHilbert.FindHilbertCriticalValue(this, tupleInFront, tuple, tid);
			
		} else {
			
			addSegement(tupleInFront, tuple, blockX, tid);
		}
		
	}
	
	public void addSegement(Tuple first, Tuple add ,int block, String tid) {
		
		Segement segement = new Segement();
		
		segement.setU1(add);
		segement.setU0(first);
		segement.setSid(block);
		segement.setTid(tid);
		
		if(memory.getBlockToSegement().get(block) == null) {
			
			HashSet<Segement> hashset = new HashSet<Segement>();
			hashset.add(segement);
			memory.getBlockToSegement().put(block, hashset);
			
		} else {
			
			memory.getBlockToSegement().get(block).add(segement);
			
		}
		
		TimeInterval ti = new TimeInterval(first.getTime(), add.getTime());
		if(memory.getBlockTimeInterval().get(block) == null) {
			
			memory.getBlockTimeInterval().put(block, ti);
			Index.getInstance().insert(block, ti);
			
		} else {
			
			memory.getBlockTimeInterval().get(block).updateTimeInteval(first.getTime(), add.getTime());
			Index.getInstance().insert(block, memory.getBlockTimeInterval().get(block));
		}
		
		memory.getFrontLine().put(tid, add);
	}

	public void persistMemoryData(Thread dataimport) {

		try {
			
			dataimport.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		System.out.println("persistMemoryData ");
		if(logger.isDebugEnabled()) {
			
			logger.debug("persistMemoryData ");
		}
		 HashMap<Integer, HashSet<Segement>> memo = memory.getBlockToSegement();
		 Iterator<Entry<Integer, HashSet<Segement>>> it = memo.entrySet().iterator();
		 while(it.hasNext()) {
			 
				Entry<Integer, HashSet<Segement>> entry = it.next();
				memory.getDiskHandler().persistOnDisk(entry.getKey(), entry.getValue());
		 }
		 
		 memory.getDiskHandler().persistOnDisk(Constants.INDEX, memory.getBlockTimeInterval());
		 memory.getDiskHandler().persistOnDisk(Constants.FILEDEALED, memory.getFileSet(), 0);
		 synchronized(BlockFilechain.getFileChain()) {
			 
			 memory.getDiskHandler().persistOnDisk(Constants.FILECHAIN, BlockFilechain.getFileChain(), 0);
		 }
		 memory.getDiskHandler().persistOnDisk(Constants.FILETOUSE, BlockFilechain.getFileToUse(), "");
		 System.out.println("backup finished");
		 if(logger.isDebugEnabled()) {
			 
			 logger.debug("backup finished");
		 }
	}

}
