/**
 * 
 */
/**
 * @author cheung
 *
 */
package com.blake.algorithm.seti;

import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;

import com.blake.algorithm.Algorithm;
import com.blake.procedure.seti.tuple.Tuple;
import com.blake.util.Constants;

/**
 * 
 * @author cheung
 * 
 * if x = 1.076 and y = 2.024
 *    then the floor cell is (1.075,2.020) 
 *    
 *    we set cell boundries to be the minimum
 *
 */

public class SETILogicalAlgorithm implements Algorithm {
	
	public static String BlockChooser(double longitude, double latitude) {
		
		double longiCrtirial = floorCriterial(longitude);
		double latiCrtirial = floorCriterial(latitude);
		
		return formKey(longiCrtirial, latiCrtirial);
	}
	
	public static Tuple TupleChooser(Tuple tu) {

		double longitude = tu.x ;
		double latitude = tu.y ;
		
		double longiCrtirial = floorCriterial(longitude);
		double latiCrtirial = floorCriterial(latitude);
		return new Tuple(longiCrtirial, latiCrtirial, tu.getTime());
	}

	public static HashSet<String> BlockSearcher(double x1, double y1, double x2, double y2) {
		
		HashSet<String> set = new HashSet<String>();
		double floorx1 = floorCriterial(x1);
		double floorx2 = floorCriterial(x2);
		double floory1 = floorCriterial(y1);
		double floory2 = floorCriterial(y2);
		
		double minx ;
		double larx;
		double miny;
		double lary;
		if(floorx1 < floorx2) {
			
			minx = floorx1;
			larx = floorx2;
		} else {
			
			minx = floorx2;
			larx = floorx1;
		}
		
		if(floory1 < floory2) {
			
			miny = floory1;
			lary = floory2;
		} else {
			
			miny = floory2;
			lary = floory1;
		}
		
		while(minx < larx) {
			
			double minycopy = miny;
			while(miny < lary && minycopy < lary){
				
				set.add(SETILogicalAlgorithm.BlockChooser(minx, minycopy));
				minycopy += 5 / Math.pow(10, Constants.LEN);
			}
			minx += 5 / Math.pow(10, Constants.LEN);
			miny += 5 / Math.pow(10, Constants.LEN);
		}
		return set;
	}
	
	public static String formKey(double longiCrtirial, double latiCrtirial) {
		
		String key = longiCrtirial + "-" + latiCrtirial;
		return  DigestUtils.md5Hex(key);
	}

	private static double floorCriterial(double number) {

		double input = number;
		double generate = (Math.floor((Math.round(number * Math.pow(10, Constants.LEN)) / Math.pow(10, Constants.LEN)) * Math.pow(10, Constants.LEN-1)) + 0.5) / Math.pow(10, Constants.LEN-1);
		if(input < generate) {
			
			generate = (Math.floor((Math.round(number * Math.pow(10, Constants.LEN)) / Math.pow(10, Constants.LEN)) * Math.pow(10, Constants.LEN-1))) / Math.pow(10, Constants.LEN-1);
		}
		return generate;
	}

	
}