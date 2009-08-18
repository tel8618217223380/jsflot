package org.jsflot.demo.managed;

import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.jsflot.components.ComponentRendererUtil;
import org.jsflot.components.FlotChartClickedEvent;
import org.jsflot.components.FlotChartDraggedEvent;
import org.jsflot.components.FlotChartRendererData;
import org.jsflot.xydata.BubbleDataPoint;
import org.jsflot.xydata.CandlestickDataPoint;
import org.jsflot.xydata.XYDataList;
import org.jsflot.xydata.XYDataPoint;
import org.jsflot.xydata.XYDataSetCollection;
import org.mortbay.log.Log;

public class ChartMBean {
	
	private XYDataList series1DataList = new XYDataList();
	private XYDataList series2DataList = new XYDataList();
	private XYDataList series3DataList = new XYDataList();
	
	private XYDataList candle1DataList = new XYDataList();
	
	private int minX = 0;
	private int maxX = 10;
	private FlotChartRendererData chartData;
	private String clickedString = "No Data Point Clicked Yet.";
	private String clickedDataPointLabel = "";
	private Integer clickedDataSeriesIndex = null;
	private XYDataPoint clickedDataPoint = null;
	
	public ChartMBean() {
		// TODO Auto-generated constructor stub
		chartData = new FlotChartRendererData();
		
		for (int i = 0; i <= 100; i++) {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(3);
			
			series1DataList.addDataPoint(new BubbleDataPoint(i, Math.random() * 10, Math.random() * 10,  "Point: " + i));
			series2DataList.addDataPoint(new BubbleDataPoint(i, Math.random() * 10, Math.random() * 10, "Point: " + i));
			series3DataList.addDataPoint(new BubbleDataPoint(i, Math.random() * 10, Math.random() * 10));
			
			candle1DataList.addDataPoint(new CandlestickDataPoint(i, Math.random() * 10, Math.random() * 10, Math.random() * 10, Math.random() * 10,  "Point: " + i));
		}
		series1DataList.setLabel("Series 1");
		series2DataList.setLabel("Series 2");
		series3DataList.setLabel("Series 3");
		
		
	}
	
	public XYDataSetCollection getChartSeries() {
		XYDataSetCollection collection = new XYDataSetCollection();
		XYDataList currentSeries1DataList = new XYDataList();
		XYDataList currentSeries2DataList = new XYDataList();
		XYDataList currentSeries3DataList = new XYDataList();
		
		XYDataList currentCandle1DataList = new XYDataList();
		
		for (int i = minX; i <= maxX; i++) {
			long startTime = 1196463600000l;
			if (chartData.getMode().equalsIgnoreCase("Time")) {
				BubbleDataPoint p1 = (BubbleDataPoint)series1DataList.get(i);
				BubbleDataPoint b1 = new BubbleDataPoint(p1.getX(), p1.getY(), p1.getRadius(),  p1.getPointLabel());
				b1.setX(startTime + (b1.getX().doubleValue() * 1000 * 60 ));
				
				BubbleDataPoint p2 = (BubbleDataPoint)series2DataList.get(i);
				BubbleDataPoint b2 = new BubbleDataPoint(p2.getX(), p2.getY(), p2.getRadius(), p2.getPointLabel());
				b2.setX(startTime + (b2.getX().doubleValue() * 1000 * 60 ));
				
				BubbleDataPoint p3 = (BubbleDataPoint)series3DataList.get(i);
				BubbleDataPoint b3 = new BubbleDataPoint(p3.getX(), p3.getY(), p3.getRadius(), p3.getPointLabel());
				b3.setX(startTime + (b3.getX().doubleValue() * 1000 * 60 ));
				
				currentSeries1DataList.addDataPoint(b1);
				currentSeries2DataList.addDataPoint(b2);
				currentSeries3DataList.addDataPoint(b3);
				
				CandlestickDataPoint c1 = (CandlestickDataPoint)candle1DataList.get(i);
				CandlestickDataPoint c2 = new CandlestickDataPoint(c1.getX(), c1.getMin(), c1.getMax(), c1.getOpen(), c1.getClose(), c1.getPointLabel());
				c2.setX(startTime + (c2.getX().doubleValue() * 1000 * 60 ));
				currentCandle1DataList.addDataPoint(c2);
			} else {
				currentSeries1DataList.addDataPoint(series1DataList.get(i));
				currentSeries2DataList.addDataPoint(series2DataList.get(i));
				currentSeries3DataList.addDataPoint(series3DataList.get(i));
				currentCandle1DataList.addDataPoint(candle1DataList.get(i));
			}
			
		}
		//Copy over the meta data for each series to the current viewed-series
		currentSeries1DataList.setLabel(series1DataList.getLabel());
		currentSeries1DataList.setFillLines(series1DataList.isFillLines());
		currentSeries1DataList.setMarkerPosition(series1DataList.getMarkerPosition());
		currentSeries1DataList.setMarkers(series1DataList.isMarkers());
		currentSeries1DataList.setShowDataPoints(series1DataList.isShowDataPoints());
		currentSeries1DataList.setShowLines(series1DataList.isShowLines());
		currentSeries1DataList.setColor(series1DataList.getColor());
		
		currentSeries2DataList.setLabel(series2DataList.getLabel());
		currentSeries2DataList.setFillLines(series2DataList.isFillLines());
		currentSeries2DataList.setMarkerPosition(series2DataList.getMarkerPosition());
		currentSeries2DataList.setMarkers(series2DataList.isMarkers());
		currentSeries2DataList.setShowDataPoints(series2DataList.isShowDataPoints());
		currentSeries2DataList.setShowLines(series2DataList.isShowLines());
		currentSeries2DataList.setColor(series2DataList.getColor());
		
		currentSeries3DataList.setLabel(series3DataList.getLabel());
		currentSeries3DataList.setFillLines(series3DataList.isFillLines());
		currentSeries3DataList.setMarkerPosition(series3DataList.getMarkerPosition());
		currentSeries3DataList.setMarkers(series3DataList.isMarkers());
		currentSeries3DataList.setShowDataPoints(series3DataList.isShowDataPoints());
		currentSeries3DataList.setShowLines(series3DataList.isShowLines());
		currentSeries3DataList.setColor(series3DataList.getColor());
		
		currentCandle1DataList.setLabel(series1DataList.getLabel());
		currentCandle1DataList.setFillLines(series1DataList.isFillLines());
		currentCandle1DataList.setMarkerPosition(series1DataList.getMarkerPosition());
		currentCandle1DataList.setMarkers(series1DataList.isMarkers());
		currentCandle1DataList.setShowDataPoints(series1DataList.isShowDataPoints());
		currentCandle1DataList.setShowLines(series1DataList.isShowLines());
		currentCandle1DataList.setColor(series1DataList.getColor());
		
		if (chartData.getChartType() != null && chartData.getChartType().equals("candles")) {
			collection.addDataList(currentCandle1DataList);
		} else {
			collection.addDataList(currentSeries1DataList);
			collection.addDataList(currentSeries2DataList);
			collection.addDataList(currentSeries3DataList);
		}
		
		return collection;
	}
	
	public XYDataList getSeries1DataList() {
		return series1DataList;
	}
	
	public void setSeries1DataList(XYDataList series1DataList) {
		this.series1DataList = series1DataList;
	}
	
	public XYDataList getSeries2DataList() {
		return series2DataList;
	}
	
	public void setSeries2DataList(XYDataList series2DataList) {
		this.series2DataList = series2DataList;
	}
	
	public XYDataList getSeries3DataList() {
		return series3DataList;
	}
	
	public void setSeries3DataList(XYDataList series3DataList) {
		this.series3DataList = series3DataList;
	}

	public FlotChartRendererData getChartData() {
		return chartData;
	}
	
	public void setChartData(FlotChartRendererData chartData) {
		this.chartData = chartData;
	}
	
	public String getClickedString() {
		return clickedString;
	}
	
	public void setClickedString(String clickedString) {
		this.clickedString = clickedString;
	}
	
	public XYDataPoint getClickedDataPoint() {
		return clickedDataPoint;
	}
	
	public void setClickedDataPoint(XYDataPoint clickedDataPoint) {
		this.clickedDataPoint = clickedDataPoint;
	}
	
	public String getClickedDataPointLabel() {
		return clickedDataPointLabel;
	}
	
	public void setClickedDataPointLabel(String clickedDataPointLabel) {
		this.clickedDataPointLabel = clickedDataPointLabel;
	}

	public void chartDraggedListener(ValueChangeEvent event) {
		
	}
	
	public void changeDataPointLabelActionListener(ActionEvent event) {
		//Not much to do really...
	}
	
	public void chartActionListener(ActionEvent event) {
		if (event instanceof FlotChartClickedEvent) {
			FlotChartClickedEvent flotEvent = (FlotChartClickedEvent)event;
			setClickedString("Chart Last Clicked at point (" + flotEvent.getClickedDataPoint().getX() + "," + flotEvent.getClickedDataPoint().getY() + "). Data Point index: " + flotEvent.getClickedDataPointIndex() 
						+ " on series: " + flotEvent.getClickedSeriesLabel() + ", which is series index: " + flotEvent.getClickedSeriesIndex());
			clickedDataSeriesIndex = flotEvent.getClickedSeriesIndex();
			Integer clickedDataPointIndex = flotEvent.getClickedDataPointIndex();
			XYDataList clickedList = null;
			XYDataPoint clickedPoint = null;
			
			if (clickedDataSeriesIndex != null && clickedDataSeriesIndex.equals(0)) {
				clickedList = series1DataList;
			} else if (clickedDataSeriesIndex != null && clickedDataSeriesIndex.equals(1)) {
				clickedList = series2DataList;
			} else if (clickedDataSeriesIndex != null && clickedDataSeriesIndex.equals(2)) {
				clickedList = series3DataList;
			}
			
			if (clickedDataPointIndex != null) {
				clickedPoint = clickedList.get(clickedDataPointIndex + minX);
			}
			
			if (clickedPoint != null) {
				clickedDataPoint = clickedPoint;
			}
			
		} else if (event instanceof FlotChartDraggedEvent) {
			FlotChartDraggedEvent dragEvent = (FlotChartDraggedEvent)event;
			Double chartMoved = dragEvent.getDragValue();

			if (chartData.getMode().equalsIgnoreCase("Time")) {
				chartMoved /= 60000;
			}
			
			//Move the chart according to the drag-motion
			minX += chartMoved;
			maxX += chartMoved;
			
			//Ensure that minX and maxX does not go out of bounds
			if (minX < 0) { minX = 0; maxX = 10; }
			if (maxX > 100) { maxX = 100; minX = 90;}
		}
	}
	
	public void testChartDraggedAction(FlotChartDraggedEvent dragEvent) {
		Log.info("chartDraggedAction");
	}
}
