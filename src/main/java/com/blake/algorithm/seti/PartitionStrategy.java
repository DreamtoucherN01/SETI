package com.blake.algorithm.seti;

import com.blake.algorithm.MyHilbert;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class PartitionStrategy {
	
	static GeometryFactory geoFactory = new GeometryFactory();
	static String STR_FORMAT = "00000000";
	
	public static int getBlockGeom(String longi, String lati) {
		
		int blockId = MyHilbert.GetHilbertValue(Double.valueOf(longi), Double.valueOf(lati));
		return blockId;
	}
	
	public static boolean checkExist(String longi, String lati, String area) {
		
		try {
			
			Geometry polygon = new WKTReader(geoFactory).read(area);
			
			String point = "POINT(" + longi + " " + lati + ")";
			Geometry ge = new WKTReader(geoFactory).read(point);
			if(polygon.contains(ge)) {
				
				return true;
			}
			return false;
			
		} catch (ParseException e) {
			
			return false;
		}
	}

}
