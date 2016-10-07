package com.blake.index;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.blake.persistence.PersistanceHandler;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.util.Constants;
import com.blake.util.FileUtils;
import com.vividsolutions.jts.index.bintree.Bintree;
import com.vividsolutions.jts.index.bintree.Interval;

public class Index {
	
	Logger logger = Logger.getLogger(Index.class);
	
	public static Index index;
	
	private Bintree binTree;
	
	public static Index getInstance() {
		
        if (index == null) {    
        	
            synchronized (PersistanceHandler.class) { 
            	
               if (index == null) {
            	   
            	   index = new Index();   
               }    
            }    
        }    
        
        return index;   
    }  
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		File file = new File(Constants.DATA, "index");
		HashMap<Integer, TimeInterval> hs = (HashMap<Integer, TimeInterval>) FileUtils.read(file);
		if(null != hs && !hs.isEmpty()) {
			
			Iterator<Entry<Integer, TimeInterval>> it = hs.entrySet().iterator();
			while(it.hasNext()) {
				
				Entry<Integer, TimeInterval> entry = it.next();
				Index.getInstance().insert(entry.getKey(), entry.getValue());
			}
		}
		System.out.println("Index inited");
	}

	public Index() {
		
		super();
		this.binTree = new Bintree();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> find(TimeInterval ti) {
		
		Interval interval = new Interval(ti.getBegin(), ti.getEnd());
		System.out.println("find item in index " + binTree.nodeSize());
		if(logger.isDebugEnabled()) {
			
			logger.debug("find item in index " + binTree.nodeSize());
		}
		
		return binTree.query(interval);
		
	}
	
	public void insert(Integer block, TimeInterval ti) {
		
		Interval interval = new Interval(ti.getBegin(), ti.getEnd());
		binTree.insert(interval, block);
	}

	public Bintree getBinTree() {
		return binTree;
	}
	
}
