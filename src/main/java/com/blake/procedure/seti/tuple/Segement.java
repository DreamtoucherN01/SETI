package com.blake.procedure.seti.tuple;

import java.io.Serializable;

public class Segement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String tid;
	
	int sid;
	
	Tuple u0;
	
	Tuple u1;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Tuple getU0() {
		return u0;
	}

	public void setU0(Tuple u0) {
		this.u0 = u0;
	}

	public Tuple getU1() {
		return u1;
	}

	public void setU1(Tuple u1) {
		this.u1 = u1;
	}

	@Override
	public String toString() {
		return "Segement [tid=" + tid + ", sid=" + sid + ", u0=" + u0 + ", u1="
				+ u1 + "]";
	}
	
}
