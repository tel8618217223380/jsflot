package org.jsflot.components;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

import org.jsflot.xydata.XYDataPoint;

public class FlotChartClickedEvent extends ActionEvent {
	private XYDataPoint clickedDataPoint;
	private Integer clickedDataPointIndex;
	private Integer clickedSeriesIndex;
	private String clickedSeriesLabel;
	
	
	
	public FlotChartClickedEvent(UIComponent component, XYDataPoint clickedDataPoint, Integer clickedDataPointIndex, Integer clickedSeriesIndex,
			String clickedSeriesLabel) {
		super(component);
		this.clickedDataPoint = clickedDataPoint;
		this.clickedDataPointIndex = clickedDataPointIndex;
		this.clickedSeriesIndex = clickedSeriesIndex;
		this.clickedSeriesLabel = clickedSeriesLabel;
	}

	public XYDataPoint getClickedDataPoint() {
		return clickedDataPoint;
	}
	
	public Integer getClickedDataPointIndex() {
		return clickedDataPointIndex;
	}
	
	public Integer getClickedSeriesIndex() {
		return clickedSeriesIndex;
	}
	
	public String getClickedSeriesLabel() {
		return clickedSeriesLabel;
	}
	
	@Override
	public void processListener(FacesListener listener) {
		// TODO Auto-generated method stub
		super.processListener(listener);
	}

}
