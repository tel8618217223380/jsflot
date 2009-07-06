/*
Copyright (c) 2009 Joachim Haagen Skeie

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package org.jsflot.components;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

public class FlotChartTag extends UIComponentELTag {

	private ValueExpression value; 
	private ValueExpression valueChangeListener;
	private ValueExpression actionListener;
	private ValueExpression action;
	private ValueExpression chartDraggable;
	private ValueExpression chartClickable;
	private ValueExpression showLines;
	private ValueExpression fillLines;
	private ValueExpression showDataPoints;
	private ValueExpression legendColumns;
	private ValueExpression legendOpacity;
	private ValueExpression legendPosition;
	private ValueExpression legendColor;
	private ValueExpression height;
	private ValueExpression width;
	private ValueExpression showTooltip;
	private ValueExpression tooltipPosition;
	private ValueExpression tooltipFollowMouse;
	private ValueExpression mode;
	private ValueExpression timeFormat;
	private ValueExpression title;
	private ValueExpression subtitle;
	private ValueExpression chartType;
	private ValueExpression showXaxisLabels;
	private ValueExpression xaxisTitle;
	private ValueExpression xaxisTitleRotation;
	private ValueExpression xaxisLabelRotation;
	private ValueExpression showYaxisLabels;
	private ValueExpression yaxisTitle; 
	private ValueExpression yaxisTitleRotation;
	private ValueExpression yaxisLabelRotation;
	private ValueExpression numberOfXAxisTicks; 
	private ValueExpression xaxisMinValue;
	private ValueExpression xaxisMaxValue;
	private ValueExpression numberOfYAxisTicks;
	private ValueExpression yaxisMinValue; 
	private ValueExpression markers;
	private ValueExpression markerPosition;
	private ValueExpression yaxisMaxValue;
	private ValueExpression crosshair;
	private ValueExpression ajaxSingle;
	private ValueExpression reRender;
	
	@Override
	public String getComponentType() {
		return "org.jsflot.components.FlotChartComponent";
	}

	@Override
	public String getRendererType() {
		return "org.jsflot.components.FlotChartRenderer";
	}

	
	public ValueExpression getValue() {
		return value;
	}

	public void setValue(ValueExpression value) {
		this.value = value;
	}

	public ValueExpression getValueChangeListener() {
		return valueChangeListener;
	}

	public void setValueChangeListener(ValueExpression valueChangeListener) {
		this.valueChangeListener = valueChangeListener;
	}
	
	public ValueExpression getActionListener() {
		return actionListener;
	}
	
	public void setActionListener(ValueExpression actionListener) {
		this.actionListener = actionListener;
	}
	
	public ValueExpression getAction() {
		return action;
	}
	
	public void setAction(ValueExpression action) {
		this.action = action;
	}

	public ValueExpression getChartDraggable() {
		return chartDraggable;
	}

	public void setChartDraggable(ValueExpression chartDraggable) {
		this.chartDraggable = chartDraggable;
	}
	
	public ValueExpression getChartClickable() {
		return chartClickable;
	}
	
	public void setChartClickable(ValueExpression chartClickable) {
		this.chartClickable = chartClickable;
	}

	public ValueExpression getShowLines() {
		return showLines;
	}

	public void setShowLines(ValueExpression showLines) {
		this.showLines = showLines;
	}

	public ValueExpression getFillLines() {
		return fillLines;
	}

	public void setFillLines(ValueExpression fillLines) {
		this.fillLines = fillLines;
	}

	public ValueExpression getShowDataPoints() {
		return showDataPoints;
	}

	public void setShowDataPoints(ValueExpression showDataPoints) {
		this.showDataPoints = showDataPoints;
	}

	public ValueExpression getLegendColumns() {
		return legendColumns;
	}

	public void setLegendColumns(ValueExpression legendColumns) {
		this.legendColumns = legendColumns;
	}

	public ValueExpression getLegendOpacity() {
		return legendOpacity;
	}

	public void setLegendOpacity(ValueExpression legendOpacity) {
		this.legendOpacity = legendOpacity;
	}

	public ValueExpression getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(ValueExpression legendPosition) {
		this.legendPosition = legendPosition;
	}

	public ValueExpression getLegendColor() {
		return legendColor;
	}

	public void setLegendColor(ValueExpression legendColor) {
		this.legendColor = legendColor;
	}

	public ValueExpression getHeight() {
		return height;
	}

	public void setHeight(ValueExpression height) {
		this.height = height;
	}

	public ValueExpression getWidth() {
		return width;
	}

	public void setWidth(ValueExpression width) {
		this.width = width;
	}

	public ValueExpression getShowTooltip() {
		return showTooltip;
	}

	public void setShowTooltip(ValueExpression showTooltip) {
		this.showTooltip = showTooltip;
	}

	public ValueExpression getTooltipPosition() {
		return tooltipPosition;
	}

	public void setTooltipPosition(ValueExpression tooltipPosition) {
		this.tooltipPosition = tooltipPosition;
	}

	public ValueExpression getTooltipFollowMouse() {
		return tooltipFollowMouse;
	}

	public void setTooltipFollowMouse(ValueExpression tooltipFollowMouse) {
		this.tooltipFollowMouse = tooltipFollowMouse;
	}

	public ValueExpression getMode() {
		return mode;
	}

	public void setMode(ValueExpression mode) {
		this.mode = mode;
	}
	
	public ValueExpression getTimeFormat() {
		return timeFormat;
	}
	
	public void setTimeFormat(ValueExpression timeFormat) {
		this.timeFormat = timeFormat;
	}

	public ValueExpression getTitle() {
		return title;
	}

	public void setTitle(ValueExpression title) {
		this.title = title;
	}

	public ValueExpression getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(ValueExpression subtitle) {
		this.subtitle = subtitle;
	}

	public ValueExpression getChartType() {
		return chartType;
	}

	public void setChartType(ValueExpression chartType) {
		this.chartType = chartType;
	}

	public ValueExpression getShowXaxisLabels() {
		return showXaxisLabels;
	}

	public void setShowXaxisLabels(ValueExpression showXaxisLabels) {
		this.showXaxisLabels = showXaxisLabels;
	}

	public ValueExpression getXaxisTitle() {
		return xaxisTitle;
	}

	public void setXaxisTitle(ValueExpression xaxisTitle) {
		this.xaxisTitle = xaxisTitle;
	}

	public ValueExpression getXaxisTitleRotation() {
		return xaxisTitleRotation;
	}

	public void setXaxisTitleRotation(ValueExpression xaxisTitleRotation) {
		this.xaxisTitleRotation = xaxisTitleRotation;
	}

	public ValueExpression getXaxisLabelRotation() {
		return xaxisLabelRotation;
	}

	public void setXaxisLabelRotation(ValueExpression xaxisLabelRotation) {
		this.xaxisLabelRotation = xaxisLabelRotation;
	}

	public ValueExpression getShowYaxisLabels() {
		return showYaxisLabels;
	}

	public void setShowYaxisLabels(ValueExpression showYaxisLabels) {
		this.showYaxisLabels = showYaxisLabels;
	}

	public ValueExpression getYaxisTitle() {
		return yaxisTitle;
	}

	public void setYaxisTitle(ValueExpression yaxisTitle) {
		this.yaxisTitle = yaxisTitle;
	}

	public ValueExpression getYaxisTitleRotation() {
		return yaxisTitleRotation;
	}

	public void setYaxisTitleRotation(ValueExpression yaxisTitleRotation) {
		this.yaxisTitleRotation = yaxisTitleRotation;
	}

	public ValueExpression getYaxisLabelRotation() {
		return yaxisLabelRotation;
	}

	public void setYaxisLabelRotation(ValueExpression yaxisLabelRotation) {
		this.yaxisLabelRotation = yaxisLabelRotation;
	}

	public ValueExpression getNumberOfXAxisTicks() {
		return numberOfXAxisTicks;
	}

	public void setNumberOfXAxisTicks(ValueExpression numberOfXAxisTicks) {
		this.numberOfXAxisTicks = numberOfXAxisTicks;
	}

	public ValueExpression getXaxisMinValue() {
		return xaxisMinValue;
	}

	public void setXaxisMinValue(ValueExpression xaxisMinValue) {
		this.xaxisMinValue = xaxisMinValue;
	}

	public ValueExpression getXaxisMaxValue() {
		return xaxisMaxValue;
	}

	public void setXaxisMaxValue(ValueExpression xaxisMaxValue) {
		this.xaxisMaxValue = xaxisMaxValue;
	}

	public ValueExpression getNumberOfYAxisTicks() {
		return numberOfYAxisTicks;
	}

	public void setNumberOfYAxisTicks(ValueExpression numberOfYAxisTicks) {
		this.numberOfYAxisTicks = numberOfYAxisTicks;
	}

	public ValueExpression getYaxisMinValue() {
		return yaxisMinValue;
	}

	public void setYaxisMinValue(ValueExpression yaxisMinValue) {
		this.yaxisMinValue = yaxisMinValue;
	}

	public ValueExpression getMarkers() {
		return markers;
	}

	public void setMarkers(ValueExpression markers) {
		this.markers = markers;
	}

	public ValueExpression getMarkerPosition() {
		return markerPosition;
	}

	public void setMarkerPosition(ValueExpression markerPosition) {
		this.markerPosition = markerPosition;
	}

	public ValueExpression getYaxisMaxValue() {
		return yaxisMaxValue;
	}

	public void setYaxisMaxValue(ValueExpression yaxisMaxValue) {
		this.yaxisMaxValue = yaxisMaxValue;
	}

	public ValueExpression getCrosshair() {
		return crosshair;
	}

	public void setCrosshair(ValueExpression crosshair) {
		this.crosshair = crosshair;
	}
	
	public ValueExpression getAjaxSingle() {
		return ajaxSingle;
	}
	
	public void setAjaxSingle(ValueExpression ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}
	
	public ValueExpression getReRender() {
		return reRender;
	}
	
	public void setReRender(ValueExpression reRender) {
		this.reRender = reRender;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);
	
		component.setValueExpression("value", this.value); 
		component.setValueExpression("valueChangeListener", this.valueChangeListener);
		component.setValueExpression("actionListener", this.actionListener);
		component.setValueExpression("action", this.action);
		component.setValueExpression("chartDraggable", this.chartDraggable);
		component.setValueExpression("chartClickable", this.chartClickable);
		component.setValueExpression("showLines", this.showLines);
		component.setValueExpression("fillLines", this.fillLines);
		component.setValueExpression("showDataPoints", this.showDataPoints);
		component.setValueExpression("legendColumns", this.legendColumns);
		component.setValueExpression("legendOpacity", this.legendOpacity);
		component.setValueExpression("legendPosition", this.legendPosition);
		component.setValueExpression("legendColor", this.legendColor);
		component.setValueExpression("height", this.height);
		component.setValueExpression("width", this.width);
		component.setValueExpression("showTooltip", this.showTooltip);
		component.setValueExpression("tooltipPosition", this.tooltipPosition);
		component.setValueExpression("tooltipFollowMouse", this.tooltipFollowMouse);
		component.setValueExpression("mode", this.mode);
		component.setValueExpression("timeFormat", this.timeFormat);
		component.setValueExpression("title", this.title);
		component.setValueExpression("subtitle", this.subtitle);
		component.setValueExpression("chartType", this.chartType);
		component.setValueExpression("showXaxisLabels", this.showXaxisLabels);
		component.setValueExpression("xaxisTitle", this.xaxisTitle);
		component.setValueExpression("xaxisTitleRotation", this.xaxisTitleRotation);
		component.setValueExpression("xaxisLabelRotation", this.xaxisLabelRotation);
		component.setValueExpression("showYaxisLabels", this.showYaxisLabels);
		component.setValueExpression("yaxisTitle", this.yaxisTitle); 
		component.setValueExpression("yaxisTitleRotation", this.yaxisTitleRotation);
		component.setValueExpression("yaxisLabelRotation", this.yaxisLabelRotation);
		component.setValueExpression("numberOfXAxisTicks", this.numberOfXAxisTicks); 
		component.setValueExpression("xaxisMinValue", this.xaxisMinValue);
		component.setValueExpression("xaxisMaxValue", this.xaxisMaxValue);
		component.setValueExpression("numberOfYAxisTicks", this.numberOfYAxisTicks);
		component.setValueExpression("yaxisMinValue", this.yaxisMinValue); 
		component.setValueExpression("markers", this.markers);
		component.setValueExpression("markerPosition", this.markerPosition);
		component.setValueExpression("yaxisMaxValue", this.yaxisMaxValue);
		component.setValueExpression("crosshair", this.crosshair);
		component.setValueExpression("ajaxSingle", this.ajaxSingle);
		component.setValueExpression("reRender", this.reRender);
	};
	
	public void release() {
		super.release();
		
		this.value= null; 
		this.valueChangeListener= null;
		this.actionListener = null;
		this.action = null;
		this.chartDraggable= null;
		this.chartClickable = null;
		this.showLines= null;
		this.fillLines= null;
		this.showDataPoints= null;
		this.legendColumns= null;
		this.legendOpacity= null;
		this.legendPosition= null;
		this.legendColor= null;
		this.height= null;
		this.width= null;
		this.showTooltip= null;
		this.tooltipPosition= null;
		this.tooltipFollowMouse= null;
		this.mode= null;
		this.timeFormat = null;
		this.title= null;
		this.subtitle= null;
		this.chartType= null;
		this.showXaxisLabels= null;
		this.xaxisTitle= null;
		this.xaxisTitleRotation= null;
		this.xaxisLabelRotation= null;
		this.showYaxisLabels= null;
		this.yaxisTitle= null; 
		this.yaxisTitleRotation= null;
		this.yaxisLabelRotation= null;
		this.numberOfXAxisTicks= null; 
		this.xaxisMinValue= null;
		this.xaxisMaxValue= null;
		this.numberOfYAxisTicks= null;
		this.yaxisMinValue= null; 
		this.markers= null;
		this.markerPosition= null;
		this.yaxisMaxValue= null;
		this.crosshair= null;
		this.ajaxSingle = null;
		this.reRender = null;
	};
}
