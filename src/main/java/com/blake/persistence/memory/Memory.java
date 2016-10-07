/**
 * 
 */
/**
 * @author cheung
 *
 */
package com.blake.persistence.memory;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import com.blake.persistence.disk.Disk;
import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.procedure.seti.tuple.Tuple;

public class Memory implements Serializable{
	
	Disk diskHandler = new Disk();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2862794497255695736L;

	/**
	 * block holder
	 */
	HashMap<Integer, HashSet<Segement>> blockToSegement = new HashMap<Integer, HashSet<Segement>>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public HashSet<Segement> get(final Object key) {

			if(super.get(key) == null) {
				
				HashSet<Segement> set = new HashSet<Segement>();
				blockToSegement.put((Integer) key, set);
			}
			final HashSet<Segement> temp = super.get(key);
			if(temp.size() > 1000) {
				
				diskHandler.persistOnDisk((Integer) key, temp);
				HashSet<Segement> set = new HashSet<Segement>();
				blockToSegement.put((Integer) key, set);
			}
			return super.get(key);
		}
		
		
	};
	
	/**
	 * block time interval
	 */
	HashMap<Integer, TimeInterval> blockTimeInterval = new HashMap<Integer, TimeInterval>();
	
	/**
	 * block time interval
	 */
	HashMap<TimeInterval, HashSet<Integer>> timeIntervalBlock = new HashMap<TimeInterval, HashSet<Integer>>();
	
	/**
	 * file instance dealed
	 */
	HashSet<File> fileSet = new HashSet<File>();
	
	/**
	 * front line
	 */
	HashMap<String, Tuple> frontLine = new HashMap<String, Tuple>();
	
	/**
	 * trajectory points holder
	 */
	HashMap<Integer, HashSet<Tuple>> trajectoryToPoints = new HashMap<Integer, HashSet<Tuple>>();

	public HashMap<Integer, HashSet<Segement>> getBlockToSegement() {
		
//		System.out.println("blockToSegement : " + blockToSegement.size());
//		if(blockToSegement.size() > 100 ) {
//			
//			Iterator<Entry<Integer, HashSet<Segement>>> it = blockToSegement.entrySet().iterator();
//			while(it.hasNext()) {
//				
//				Entry<Integer, HashSet<Segement>> entry = it.next();
//				diskHandler.persistOnDisk(String.valueOf(entry.getKey()), entry.getValue());
//			}
//			blockToSegement = new HashMap<Integer, HashSet<Segement>>();
//		}
		return blockToSegement;
	}

	public void setBlockToSegement(
			HashMap<Integer, HashSet<Segement>> blockToSegement) {
		this.blockToSegement = blockToSegement;
	}

	public HashMap<Integer, TimeInterval> getBlockTimeInterval() {
		return blockTimeInterval;
	}

	public void setBlockTimeInterval(HashMap<Integer, TimeInterval> blockTimeInterval) {
		this.blockTimeInterval = blockTimeInterval;
	}

	public HashMap<Integer, HashSet<Tuple>> getTrajectoryToPoints() {
		return trajectoryToPoints;
	}

	public void setTrajectoryToPoints(
			HashMap<Integer, HashSet<Tuple>> trajectoryToPoints) {
		this.trajectoryToPoints = trajectoryToPoints;
	}

	public HashMap<String, Tuple> getFrontLine() {
		return frontLine;
	}

	public void setFrontLine(HashMap<String, Tuple> frontLine) {
		this.frontLine = frontLine;
	}

	public HashMap<TimeInterval, HashSet<Integer>> getTimeIntervalBlock() {
		return timeIntervalBlock;
	}

	public void setTimeIntervalBlock(HashMap<TimeInterval, HashSet<Integer>> timeIntervalBlock) {
		this.timeIntervalBlock = timeIntervalBlock;
	}

	public Disk getDiskHandler() {
		return diskHandler;
	}

	public void setDiskHandler(Disk diskHandler) {
		this.diskHandler = diskHandler;
	}

	public HashSet<File> getFileSet() {
		return fileSet;
	}

	public void setFileSet(HashSet<File> fileSet) {
		this.fileSet = fileSet;
	}
	
}