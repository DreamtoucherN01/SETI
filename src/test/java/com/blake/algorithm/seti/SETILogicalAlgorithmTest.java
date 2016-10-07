package com.blake.algorithm.seti;

import java.io.IOException;

import com.blake.input.InputFromFile;

import junit.framework.TestCase;

public class SETILogicalAlgorithmTest extends TestCase {

	public void testSETILogicalAlgorithm() {
		
		System.out.println(SETILogicalAlgorithm.BlockChooser(30.7700940000,114.0175000000));
		InputFromFile iff = new InputFromFile();
		try {
			iff.fileReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
