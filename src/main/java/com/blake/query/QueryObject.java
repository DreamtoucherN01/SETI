package com.blake.query;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.blake.algorithm.MyHilbert;
import com.blake.index.Index;
import com.blake.persistence.PersistanceHandler;
import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;

public class QueryObject {

	Logger logger = Logger.getLogger(QueryObject.class);
	
	public TimeInterval timeInterval; 
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	
	HashSet<Segement> h;
	
	public QueryObject(TimeInterval timeInterval, double x1, double y1,
			double x2, double y2) {
		
		super();
		this.timeInterval = timeInterval;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public HashSet<Segement> searchTrajectory() {
		
		HashSet<Segement> ret = new HashSet<Segement>();
		ret = blockFilter();
		return ret;
	}
	
	public HashSet<Segement> blockFilter() {
		
		HashSet<Integer> blocks = MyHilbert.BlockSearcher(x1, y1, x2, y2);
		System.out.println("get {} items after spatial filter " + blocks.size());
		if(logger.isDebugEnabled()) {
			
			logger.debug("get {} items after spatial filter " + blocks.size());
		}
		return timeFilter(blocks);
	}

	private HashSet<Segement> timeFilter(HashSet<Integer> blocks) {

		HashSet<Integer> filterByTimeInterval = filterByTimeInterval(blocks, timeInterval, false);
		System.out.println("blocks : " + blocks);
		System.out.println("filterByTimeInterval : " + filterByTimeInterval);
		filterByTimeInterval.retainAll(blocks);
		System.out.println("get {} items after temporary filter " + filterByTimeInterval.size());
		if(logger.isDebugEnabled()) {
			
			logger.debug("get {} items after temporary filter " + filterByTimeInterval.size());
		}
		
		HashSet<Segement> ret = new HashSet<Segement>();
		Iterator<Integer> it = filterByTimeInterval.iterator();
		while(it.hasNext()) {
			
			Integer block = it.next();
			if(block == 134862) {
				
				System.out.println("ignore large block " + block);
				if(logger.isDebugEnabled()) {
					
					logger.debug("ignore large block " + block);
				}
				continue;
			}
			HashSet<Segement> hashSetInMemory = PersistanceHandler.getInstance().getMemory().getBlockToSegement().get(block);
			if(null != hashSetInMemory) {
				
				ret.addAll(hashSetInMemory);
			}
			
			HashSet<Segement> hashSetInDisk = PersistanceHandler.getInstance().getMemory().getDiskHandler().fetchDataByBlock(String.valueOf(block));
			if(null != hashSetInDisk) {
				
				ret.addAll(hashSetInDisk);
			}
		}
		
		return ret;
	}

	public int getTrajectoryNum() {

		HashSet<Integer> blocks = MyHilbert.BlockSearcher(x1, y1, x2, y2);
		System.out.println("get {} items after spatial filter " + blocks.size());
		if(logger.isDebugEnabled()) {
			
			logger.debug("get {} items after spatial filter " + blocks.size());
		}

		long t1 = System.currentTimeMillis();
		HashSet<Integer> filterByTimeInterval = filterByTimeInterval(blocks, timeInterval, false);
		filterByTimeInterval.retainAll(blocks);
		long t2 = System.currentTimeMillis();
		System.out.println("get " + filterByTimeInterval.size() + " items after temporary filter, time cost : " + (t2 - t1) + "\n");
		if(logger.isDebugEnabled()) {
			
			logger.debug("get " + filterByTimeInterval.size() + " items after temporary filter, time cost : " + (t2 - t1));
		}
		Iterator<Integer> it = filterByTimeInterval.iterator();
		int num = 0;
		while(it.hasNext()) {
			
			Integer block = it.next();
			System.out.println("deal with block " + block);
			if(logger.isDebugEnabled()) {
				
				logger.debug("deal with block " + block);
			}
			HashSet<Segement> hashSetInMemory = PersistanceHandler.getInstance().getMemory().getBlockToSegement().get(block);
			if(null != hashSetInMemory) {
				
				num += hashSetInMemory.size();
			}
			
			num += PersistanceHandler.getInstance().getMemory().getDiskHandler().fetchNumberByBlock(String.valueOf(block));
		}
		
		return num;
	}
	
	private HashSet<Integer> filterByTimeInterval(HashSet<Integer> blocks, TimeInterval ti, boolean sequence) {

		if(blocks.isEmpty()) {
			
			return null;
		}
		HashSet<Integer> hs = new HashSet<Integer>();
		if(sequence) {
			
			Iterator<Integer> it = blocks.iterator();
			while(it.hasNext()){
				
				Integer block = it.next();
				if(PersistanceHandler.getInstance().getMemory().getBlockTimeInterval().get(block).checkDuplicate(ti)){
					
					hs.add(block);
				}
			}
		} else {
			
			List<Integer> blockByTimes = Index.getInstance().find(ti);
			hs.addAll(blockByTimes);
		}
		return hs;
	}

}
