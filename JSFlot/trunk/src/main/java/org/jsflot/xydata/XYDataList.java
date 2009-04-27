package org.jsflot.xydata;

import java.util.ArrayList;
import java.util.List;

public class XYDataList {
	private List<XYDataPoint> dataPointList;
	private boolean showDataPoints = false;
	private boolean fillLines = false;
	private boolean showLines = false;
	private String label = "";
	
	public XYDataList() {
		dataPointList = new ArrayList<XYDataPoint>();
	}
	
	public boolean isShowDataPoints() {
		return showDataPoints;
	}

	public void setShowDataPoints(boolean showDataPoints) {
		this.showDataPoints = showDataPoints;
	}

	public boolean isFillLines() {
		return fillLines;
	}

	public void setFillLines(boolean fillLines) {
		this.fillLines = fillLines;
	}

	public boolean isShowLines() {
		return showLines;
	}

	public void setShowLines(boolean showLines) {
		this.showLines = showLines;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean addDataPoint(XYDataPoint point) {
		return dataPointList.add(point);
	}
	
	public boolean addDataPoint(Number x, Number y) {
		return dataPointList.add(new XYDataPoint(x, y));
	}
	
	public boolean removeDataPoint(XYDataPoint point) {
		return dataPointList.remove(point);
	}
	
	public XYDataPoint removeDataPoint(int index) {
		return dataPointList.remove(index);
	}
	
	public List<XYDataPoint> getDataPointList() {
		return dataPointList;
	}
	
	public int size() {
		return dataPointList.size();
	}
	
	public XYDataPoint get(int index) {
		return dataPointList.get(index);
	}
}
