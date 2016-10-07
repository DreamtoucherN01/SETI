package com.blake.algorithm.seti;

import junit.framework.TestCase;

import com.blake.algorithm.MyHilbert;

public class HilbertTest extends TestCase  {
	
	public void testHilbert() {
		
		double x1=116.536499, y1=40.2440338;
		double x2=116.4302673, y2=39.8338928;
		double x3=116.8302673, y3=39.8338928;
		
		int hilbert = MyHilbert.GetHilbertValue(x1, y1);
		int hilbert2 = MyHilbert.GetHilbertValue(x2, y2);
		int hilbert3 = MyHilbert.GetHilbertValue(x3, y3);
		
		System.out.println(hilbert + " " + hilbert2 + " " + hilbert3);
	}

}
