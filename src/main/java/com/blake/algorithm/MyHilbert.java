package com.blake.algorithm;

import java.util.HashSet;

import com.blake.persistence.PersistanceHandler;
import com.blake.procedure.seti.tuple.Tuple;

public  class MyHilbert {	
		
	public static int GetHilbertValue(double latitude,double longtitude) { 
		
		return GetHilbertValue(latitude, longtitude, 512);
	}
	
	public static int GetHilbertValue(double latitude,double longtitude,int len)
	{
		int x,y;
		x=(int)(latitude+180);
		y=(int)(longtitude+90)*2;
		return GetHilbertValue(len,x,y);
	}	
	
	public static int GetHilbertValue(int len,int x,int y){
		
			int nextlen = 0;
			int tmp;
			if(len==1) {
				
				return 0;
				
			} else {
				
				nextlen = len / 2;
				
				tmp = nextlen * nextlen;
				
				if(x < nextlen) {
					
					if(y < nextlen) {
						
						return GetHilbertValue(nextlen, y, x);
						
					} else {
						
						return GetHilbertValue(nextlen, x, y - nextlen) + tmp;
						
					}
				} else {
					
					if(y < nextlen) {
						
						return GetHilbertValue(nextlen, nextlen - y - 1, 2 * nextlen - x - 1) + 3 * tmp;
						
					} else {
						
						return GetHilbertValue(nextlen, x - nextlen, y - nextlen) + 2 * tmp;
						
					}
				}
			}
		}	
	
	@SuppressWarnings("static-access")
	public static void FindHilbertCriticalValue(PersistanceHandler persistanceHandler, Tuple tupleInFront, Tuple tuple, String tid) {
		
		
		if(Math.floor(tupleInFront.x) > Math.floor(tuple.x)) {
			
			Tuple t1 = new Tuple(Math.floor(tupleInFront.x), tupleInFront.y, tupleInFront.getTime());
			persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
			tupleInFront = t1;
			
			if(Math.floor(tupleInFront.y) > Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.floor(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} else if(Math.floor(tupleInFront.y) < Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.ceil(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} 
			persistanceHandler.getInstance().addSegement(tupleInFront, tuple, MyHilbert.GetHilbertValue(tuple.x, tuple.y), tid);
			
		} else if(Math.floor(tupleInFront.x) < Math.floor(tuple.x)) {
			
			Tuple t1 = new Tuple(Math.ceil(tupleInFront.x), tupleInFront.y, tupleInFront.getTime());
			persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
			tupleInFront = t1;
			
			if(Math.floor(tupleInFront.y) > Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.floor(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} else if(Math.floor(tupleInFront.y) < Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.ceil(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} 
			persistanceHandler.getInstance().addSegement(tupleInFront, tuple, MyHilbert.GetHilbertValue(tuple.x, tuple.y), tid);
			
		} else {
			
			Tuple t1 = null;
			if(Math.floor(tupleInFront.y) > Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.floor(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} else if(Math.floor(tupleInFront.y) < Math.floor(tuple.y)) {
				
				t1 = new Tuple( tupleInFront.x, Math.ceil(tupleInFront.y), tupleInFront.getTime());
				persistanceHandler.getInstance().addSegement(tupleInFront, t1, MyHilbert.GetHilbertValue(tupleInFront.x, tupleInFront.y), tid);
				tupleInFront = t1;
				
			} 
			persistanceHandler.getInstance().addSegement(tupleInFront, tuple, MyHilbert.GetHilbertValue(tuple.x, tuple.y), tid);
			
		}
	}

	public static HashSet<Integer> BlockSearcher(double x1, double y1,
			double x2, double y2) {

		HashSet<Integer> blocks = new HashSet<Integer>();
		double floorx1 = Math.floor(x1);
		double floorx2 = Math.floor(x2);
		double floory1 = Math.floor(y1);
		double floory2 = Math.floor(y2); 
		
		if(floorx1 < floorx2) {
			
			while (floorx1 <= floorx2) {
				
				if(floory1 < floory2) {
					
					double floory1copy = floory1;
					while(floory1copy <= floory2) {
						
						blocks.add(MyHilbert.GetHilbertValue(floorx1, floory1copy));
						floory1copy++;
					}
				} else {
					
					double floory2copy = floory2;
					while(floory2copy <= floory1) {
						
						blocks.add(MyHilbert.GetHilbertValue(floorx1, floory2copy));
						floory2copy++;
					}
				}
				floorx1++;
			}
			
		} else {
			
			while (floorx2 <= floorx1 ) {
				
				if(floory1 < floory2) {
					
					double floory1copy = floory1;
					while(floory1copy <= floory2) {
						
						blocks.add(MyHilbert.GetHilbertValue(floorx2, floory1copy));
						floory1copy++;
					}
				} else {
					
					double floory2copy = floory2;
					while(floory2copy <= floory1) {
						
						blocks.add(MyHilbert.GetHilbertValue(floorx2, floory2copy));
						floory2copy++;
					}
				}
				floorx2++;
			}
		}
		return blocks;
	}

}
