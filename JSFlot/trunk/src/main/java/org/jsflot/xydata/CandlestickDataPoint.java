package org.jsflot.xydata;

public class CandlestickDataPoint extends XYDataPoint {
	Number min;
	Number max;
	Number open;
	Number close;
	
	public CandlestickDataPoint(Number x, Number min, Number max, Number open, Number close) {
		super();
		setX(x);
		this.min = min;
		this.max = max;
		this.open = open;
		this.close = close;
	}
	
	public CandlestickDataPoint(Number x, Number min, Number max, Number open, Number close, String pointLabel) {
		super();
		setX(x);
		this.min = min;
		this.max = max;
		this.open = open;
		this.close = close;
		setPointLabel(pointLabel);
	}
	
	public Number getMin() {
		return min;
	}
	public void setMin(Number min) {
		this.min = min;
	}
	
	public Number getMax() {
		return max;
	}
	public void setMax(Number max) {
		this.max = max;
	}
	
	public Number getOpen() {
		return open;
	}
	public void setOpen(Number open) {
		this.open = open;
	}
	
	public Number getClose() {
		return close;
	}
	public void setClose(Number close) {
		this.close = close;
	}
	
	

}
