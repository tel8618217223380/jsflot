package org.jsflot.xydata;

public class XYDataPoint {
	
	private Number x;
	private Number y;
	
	public XYDataPoint() {
		// TODO Auto-generated constructor stub
	}
	
	public XYDataPoint(Number x, Number y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Number getX() {
		return x;
	}
	
	public void setX(Number x) {
		this.x = x;
	}
	
	public Number getY() {
		return y;
	}
	
	public void setY(Number y) {
		this.y = y;
	}

}
