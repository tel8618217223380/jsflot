package org.jsflot.xydata;

public class BubbleDataPoint extends XYDataPoint {
	private Number radius;
	
	public BubbleDataPoint() {
		super();
	}
	
	public BubbleDataPoint(Number x, Number y, Number radius) {
		setX(x);
		setY(y);
		this.radius = radius;
	}
	
	public BubbleDataPoint(Number x, Number y, Number radius, String pointLabel) {
		setX(x);
		setY(y);
		this.radius = radius;
		setPointLabel(pointLabel);
	}
	
	public Number getRadius() {
		return radius;
	}
	
	public void setRadius(Number radius) {
		this.radius = radius;
	}
}
