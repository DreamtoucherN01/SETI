package com.blake.algorithm.seti;

import java.util.HashSet;

import com.blake.index.Index;
import com.blake.persistence.PersistanceHandler;
import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.query.QueryObject;

public class QueryTest{
	
	public static void main(String args[]) {
		
//		TimeInterval ti = new TimeInterval(2012110103l, 2012110104l);
//		QueryObject qo = new QueryObject(ti, 116.320986, 39.361769, 116.564177, 40.020933);
//		HashSet<Segement> hs = qo.searchTrajectory();
//		System.out.println(hs.size());
//		String polygon = "POLYGON ((116.3209986 39.361769, 116.564177 39.361769, 116.564177 40.020933, 116.3209986 39.361769))";
//		String time = "2012110103,2012110104";
		PersistanceHandler.getInstance().init();
		Index.getInstance().init();

//		String key = (String) jo.get("key");
//		String mode = (String) jo.get("mode");
		TimeInterval ti = new TimeInterval(20121102016252l,20121102016253l);
		QueryObject qo = new QueryObject(ti, 130.3209986,35.361769 ,116.564177 ,40.020933);
		
		long begin = System.currentTimeMillis();
		int num = qo.getTrajectoryNum();
//		HashSet<Segement> segements = qo.searchTrajectory();
		long end = System.currentTimeMillis();
		System.out.println("Node Size : " + num + " time cost : " + (end - begin)/1000);
	}

}
