package com.blake.procedure.seti.tuple;

import java.io.Serializable;

public class TimeInterval implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8296466868622533852L;

	Long begin;
	
	Long end;

	public TimeInterval(Long begin, Long end) {
		
		super();
		this.begin = begin;
		this.end = end;
	}

	public Long getBegin() {
		return begin;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}
	
	public void updateTimeInteval(long begin, long end) {
		
		if(this.begin == null || this.begin > begin) {
			
			this.begin = begin; 
		}
		
		if(this.end == null || this.end < end) {
			
			this.end = end; 
		}
	}
	

	public boolean checkDuplicate(TimeInterval ti) {

		if(this.end > ti.getBegin() && this.end < ti.getEnd()) {
			
			return true;
			
		} else if(this.begin > ti.getBegin() && this.begin < ti.getEnd()) {
			
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		
		return "TimeInterval [begin=" + begin + ", end=" + end + "]";
	}
	
}
