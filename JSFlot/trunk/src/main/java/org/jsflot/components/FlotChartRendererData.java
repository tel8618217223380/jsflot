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

public class FlotChartRendererData {
	private Boolean showDataPoints = false;
	private Boolean showLines = true;
	private String width = "600";
	private String height = "300";
	private String legend;
	private Boolean fillLines = false;
	private String legendColor;
	private String mode = "Series";
	private String timeFormat = null;
	private String legendPosition = "ne";
	private String crosshair;
	private Boolean spreadsheet;
	private String title;
	private String subtitle;
	private Integer legendColumns = 1;
	private Boolean showTooltip = false;
	private String tooltipPosition = "se";
	private Double legendOpacity = 0.85d;
	private Boolean tooltipFollowMouse = false;
	private String chartType = "line";
	private Boolean showXaxisLabels = true;
	private String xaxisTitle = null;
	private Boolean showYaxisLabels = true;
	private String yaxisTitle;
	private Integer numberOfXAxisTicks = null;
	private Double xaxisMinValue = null;
	private Double xaxisMaxValue = null;
	private Integer numberOfYAxisTicks = null;
	private Double yaxisMinValue = null;
	private Double yaxisMaxValue = null;
	private Double barWidth = 0.5;
	private Boolean markers = false;
	private String markerPosition = "ct";
	private Integer xaxisLabelRotation = 45;
	private Integer yaxisLabelRotation = 0;
	private Integer xaxisTitleRotation = 0;
	private Integer yaxisTitleRotation = 90;
	private Boolean chartDraggable = false;
	private Boolean ajaxSingle = false;
	private String reRender = null;
	
	public FlotChartRendererData() {
		// TODO Auto-generated constructor stub
	}

	public Boolean getShowDataPoints() {
		return showDataPoints;
	}

	public void setShowDataPoints(Boolean showDataPoints) {
		this.showDataPoints = showDataPoints;
	}
	
	public void setShowDataPoints(Object obj) {
		if (obj instanceof String) {
			showDataPoints = new Boolean((String) obj);
		} else if (obj instanceof Boolean) {
			showDataPoints = (Boolean) obj;
		}
		
	}

	public Boolean getShowLines() {
		return showLines;
	}

	public void setShowLines(Boolean showLines) {
		this.showLines = showLines;
	}
	
	public void setShowLines(Object obj) {
		if (obj instanceof String) {
			showLines = new Boolean((String) obj);
		} else if (obj instanceof Boolean) {
			showLines = (Boolean) obj;
		}
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
		if (this.width == null) {
			this.width = "600";
		}
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
		if (this.height == null) {
			this.height = "300";
		}
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public Boolean getFillLines() {
		return fillLines;
	}

	public void setFillLines(Boolean fillLines) {
		this.fillLines = fillLines;
	}
	
	public void setFillLines(Object obj) {
		if (obj instanceof String) {
			fillLines = new Boolean((String) obj);
		} else if (obj instanceof Boolean) {
			fillLines = (Boolean) obj;
		}
	}

	public String getLegendColor() {
		return legendColor;
	}

	public void setLegendColor(String legendColor) {
		// Revert to default color if none is selected
		if (legendColor == null || (legendColor != null && legendColor.equals("#"))) {
			legendColor = "#D2E8FF";
		}
		
		
		if (!legendColor.startsWith("#")) {
			legendColor = "#" + legendColor;
		}
		
		this.legendColor = legendColor;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
		if (this.mode == null || this.mode.equals("")) {
			this.mode = "Series";
		}
	}
	
	public String getTimeFormat() {
		return timeFormat;
	}
	
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(String legendPosition) {
		if (legendPosition == null) {
			legendPosition = "ne";
		}
		this.legendPosition = legendPosition;
	}

	public String getCrosshair() {
		return crosshair;
	}

	public void setCrosshair(String crosshair) {
		this.crosshair = crosshair;
		if (this.crosshair == null) {
			this.crosshair = "none";
		}
	}

	public Boolean getSpreadsheet() {
		return spreadsheet;
	}

	public void setSpreadsheet(Boolean spreadsheet) {
		this.spreadsheet = spreadsheet;
	}
	
	public void setSpreadsheet(Object spreadsheetObj) {
		if (spreadsheetObj instanceof String) {
			spreadsheet = new Boolean((String) spreadsheetObj);
		} else if (spreadsheetObj instanceof Boolean) {
			spreadsheet = (Boolean) spreadsheetObj;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Integer getLegendColumns() {
		return legendColumns;
	}

	public void setLegendColumns(Integer legendColumns) {
		this.legendColumns = legendColumns;
	}
	
	public void setLegendColumns(Object legendColumnsObj) {
		if (legendColumnsObj instanceof Integer) {
			setLegendColumns((Integer)legendColumnsObj);
		} else if (legendColumnsObj instanceof String) {
			try {
				this.legendColumns = Integer.parseInt((String)legendColumnsObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do. Reset to 1 column;
				this.legendColumns = 1;
			}
		}
	}

	public Boolean getShowTooltip() {
		return showTooltip;
	}

	public void setShowTooltip(Boolean showTooltip) {
		this.showTooltip = showTooltip;
	}
	
	public void setShowTooltip(Object tooltipObj) {
		if (tooltipObj instanceof String) {
			showTooltip = new Boolean((String) tooltipObj);
		} else if (tooltipObj instanceof Boolean) {
			showTooltip = (Boolean) tooltipObj;
		}
	}

	public String getTooltipPosition() {
		return tooltipPosition;
	}

	public void setTooltipPosition(String tooltipPosition) {
		this.tooltipPosition = tooltipPosition;
	}

	public Double getLegendOpacity() {
		return legendOpacity;
	}

	public void setLegendOpacity(Double legendOpacity) {
		this.legendOpacity = legendOpacity;
	}
	
	public void setLegendOpacity(Object legendOpacityObj) {
		if (legendOpacityObj instanceof Double) {
			setLegendOpacity((Double)legendOpacityObj);
		} else if (legendOpacityObj instanceof String) {
			try {
				this.legendOpacity = Double.parseDouble((String)legendOpacityObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do. Reset to 1 column;
				this.legendOpacity = 0.85d;
			}
		}
	}
	
	public Boolean getTooltipFollowMouse() {
		return tooltipFollowMouse;
	}
	
	public void setTooltipFollowMouse(Boolean tooltipFollowMouse) {
		this.tooltipFollowMouse = tooltipFollowMouse;
	}
	
	public void setTooltipFollowMouse(Object tooltipFollowMouseObj) {
		if (tooltipFollowMouseObj instanceof String) {
			this.tooltipFollowMouse = new Boolean((String) tooltipFollowMouseObj);
		} else if (tooltipFollowMouseObj instanceof Boolean) {
			this.tooltipFollowMouse = (Boolean) tooltipFollowMouseObj;
		}
	}
	
	public String getChartType() {
		return chartType;
	}
	
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public Boolean getShowXaxisLabels() {
		return showXaxisLabels;
	}

	public void setShowXaxisLabels(Boolean showXaxisLabels) {
		this.showXaxisLabels = showXaxisLabels;
	}
	
	public void setShowXaxisLabels(Object showXaxisLabelsObj) {
		if (showXaxisLabelsObj instanceof String) {
			this.showXaxisLabels = new Boolean((String) showXaxisLabelsObj);
		} else if (showXaxisLabelsObj instanceof Boolean) {
			this.showXaxisLabels = (Boolean) showXaxisLabelsObj;
		}
	}

	public String getXaxisTitle() {
		return xaxisTitle;
	}

	public void setXaxisTitle(String xaxisTitle) {
		this.xaxisTitle = xaxisTitle;
	}

	public Boolean getShowYaxisLabels() {
		return showYaxisLabels;
	}

	public void setShowYaxisLabels(Boolean showYaxisLabels) {
		this.showYaxisLabels = showYaxisLabels;
	}

	public void setShowYaxisLabels(Object showYaxisLabelsObj) {
		if (showYaxisLabelsObj instanceof String) {
			this.showYaxisLabels = new Boolean((String) showYaxisLabelsObj);
		} else if (showYaxisLabelsObj instanceof Boolean) {
			this.showYaxisLabels = (Boolean) showYaxisLabelsObj;
		}
	}
	
	public String getYaxisTitle() {
		return yaxisTitle;
	}

	public void setYaxisTitle(String yaxisTitle) {
		this.yaxisTitle = yaxisTitle;
	}

	public Integer getNumberOfXAxisTicks() {
		return numberOfXAxisTicks;
	}

	public void setNumberOfXAxisTicks(Integer numberOfXAxisTicks) {
		this.numberOfXAxisTicks = numberOfXAxisTicks;
	}
	
	public void setNumberOfXAxisTicks(Object numberofXAxisTicksObj) {
		if (numberofXAxisTicksObj instanceof Integer) {
			setNumberOfXAxisTicks((Integer)numberofXAxisTicksObj);
		} else if (numberofXAxisTicksObj instanceof String) {
			try {
				this.numberOfXAxisTicks = Integer.parseInt((String)numberofXAxisTicksObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
				
			}
		}
	}
	
	public Integer getNumberOfYAxisTicks() {
		return numberOfYAxisTicks;
	}

	public void setNumberOfYAxisTicks(Integer numberOfYAxisTicks) {
		this.numberOfYAxisTicks = numberOfYAxisTicks;
	}
	
	public void setNumberOfYAxisTicks(Object numberofYAxisTicksObj) {
		if (numberofYAxisTicksObj instanceof Integer) {
			setNumberOfYAxisTicks((Integer)numberofYAxisTicksObj);
		} else if (numberofYAxisTicksObj instanceof String) {
			try {
				this.numberOfYAxisTicks = Integer.parseInt((String)numberofYAxisTicksObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
			}
		}
	}

	public Double getXaxisMinValue() {
		return xaxisMinValue;
	}

	public void setXaxisMinValue(Double xaxisMinValue) {
		this.xaxisMinValue = xaxisMinValue;
	}
	
	public void setXaxisMinValue(Object xaxisMinValueObj) {
		if (xaxisMinValueObj instanceof Double) {
			setXaxisMinValue((Double)xaxisMinValueObj);
		} else if (xaxisMinValueObj instanceof String) {
			try {
				this.xaxisMinValue = Double.parseDouble((String)xaxisMinValueObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
				
			}
		}
	}

	public Double getXaxisMaxValue() {
		return xaxisMaxValue;
	}

	public void setXaxisMaxValue(Double xaxisMaxValue) {
		this.xaxisMaxValue = xaxisMaxValue;
	}
	
	public void setXaxisMaxValue(Object xaxisMaxValueObj) {
		if (xaxisMaxValueObj instanceof Double) {
			setXaxisMaxValue((Double)xaxisMaxValueObj);
		} else if (xaxisMaxValueObj instanceof String) {
			try {
				this.xaxisMaxValue = Double.parseDouble((String)xaxisMaxValueObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
				
			}
		}
	}
		
	public Double getYaxisMinValue() {
		return yaxisMinValue;
	}

	public void setYaxisMinValue(Double yaxisMinValue) {
		this.yaxisMinValue = yaxisMinValue;
	}
	
	public void setYaxisMinValue(Object yaxisMinValueObj) {
		if (yaxisMinValueObj instanceof Double) {
			setYaxisMinValue((Double)yaxisMinValueObj);
		} else if (yaxisMinValueObj instanceof String) {
			try {
				this.yaxisMinValue = Double.parseDouble((String)yaxisMinValueObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
				
			}
		}
	}

	public Double getYaxisMaxValue() {
		return yaxisMaxValue;
	}

	public void setYaxisMaxValue(Double yaxisMaxValue) {
		this.yaxisMaxValue = yaxisMaxValue;
	}
	
	public void setYaxisMaxValue(Object yaxisMaxValueObj) {
		if (yaxisMaxValueObj instanceof Double) {
			setYaxisMaxValue((Double)yaxisMaxValueObj);
		} else if (yaxisMaxValueObj instanceof String) {
			try {
				this.yaxisMaxValue = Double.parseDouble((String)yaxisMaxValueObj);
			} catch (NumberFormatException nfe) {
				//Nothing to do.
				
			}
		}
	}
	
	public Double getBarWidth() {
		return barWidth;
	}
	
	public void setBarWidth(Double barWidth) {
		this.barWidth = barWidth;
	}

	public Boolean getMarkers() {
		return markers;
	}
	
	public void setMarkers(Boolean markers) {
		this.markers = markers;
	}
	
	public void setMarkers(Object markersObj) {
		if (markersObj instanceof String) {
			this.markers = new Boolean((String) markersObj);
		} else if (markersObj instanceof Boolean) {
			this.markers = (Boolean) markersObj;
		}
	}

	public String getMarkerPosition() {
		return markerPosition;
	}
	
	public void setMarkerPosition(String markerPosition) {
		this.markerPosition = markerPosition;
	}

	public Integer getXaxisLabelRotation() {
		return xaxisLabelRotation;
	}

	public void setXaxisLabelRotation(Integer xaxisLabelRotation) {
		this.xaxisLabelRotation = xaxisLabelRotation;
	}
	
	public void setXaxisLabelRotation(Object xaxisLabelRotationObj) {
		if (xaxisLabelRotationObj instanceof String) {
			this.xaxisLabelRotation = Integer.parseInt((String)xaxisLabelRotationObj);
		} else if (xaxisLabelRotationObj instanceof Integer) {
			this.xaxisLabelRotation = (Integer)xaxisLabelRotationObj;
		}
	}

	public Integer getYaxisLabelRotation() {
		return yaxisLabelRotation;
	}

	public void setYaxisLabelRotation(Integer yaxisLabelRotation) {
		this.yaxisLabelRotation = yaxisLabelRotation;
	}
	
	public void setYaxisLabelRotation(Object yaxisLabelRotationObj) {
		if (yaxisLabelRotationObj instanceof String) {
			this.yaxisLabelRotation = Integer.parseInt((String)yaxisLabelRotationObj);
		} else if (yaxisLabelRotationObj instanceof Integer) {
			this.yaxisLabelRotation = (Integer)yaxisLabelRotationObj;
		}
	}

	public Integer getXaxisTitleRotation() {
		return xaxisTitleRotation;
	}

	public void setXaxisTitleRotation(Integer xaxisTitleRotation) {
		this.xaxisTitleRotation = xaxisTitleRotation;
	}
	
	public void setXaxisTitleRotation(Object xaxisTitleRotationObj) {
		if (xaxisTitleRotationObj instanceof String) {
			this.xaxisTitleRotation = Integer.parseInt((String)xaxisTitleRotationObj);
		} else if (xaxisTitleRotationObj instanceof Integer) {
			this.xaxisTitleRotation = (Integer)xaxisTitleRotationObj;
		}
	}

	public Integer getYaxisTitleRotation() {
		return yaxisTitleRotation;
	}

	public void setYaxisTitleRotation(Integer yaxisTitleRotation) {
		this.yaxisTitleRotation = yaxisTitleRotation;
	}
	
	public void setYaxisTitleRotation(Object yaxisTitleRotationObj) {
		if (yaxisTitleRotationObj instanceof String) {
			this.yaxisTitleRotation = Integer.parseInt((String)yaxisTitleRotationObj);
		} else if (yaxisTitleRotationObj instanceof Integer) {
			this.yaxisTitleRotation = (Integer)yaxisTitleRotationObj;
		}
	}
	
	public Boolean getChartDraggable() {
		return chartDraggable;
	}
	
	public void setChartDraggable(Boolean chartDraggable) {
		this.chartDraggable = chartDraggable;
	}
	
	public void setChartDraggable(Object chartDraggableObj) {
		if (chartDraggableObj instanceof String) {
			this.chartDraggable = new Boolean((String) chartDraggableObj);
		} else if (chartDraggableObj instanceof Boolean) {
			this.chartDraggable = (Boolean) chartDraggableObj;
		}
	}
	
	public Boolean getAjaxSingle() {
		return ajaxSingle;
	}
	
	public void setAjaxSingle(Boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}
	
	public void setAjaxSingle(Object ajaxSingleObj) {
		if (ajaxSingleObj instanceof String) {
			this.ajaxSingle = new Boolean((String) ajaxSingleObj);
		} else if (ajaxSingleObj instanceof Boolean) {
			this.ajaxSingle = (Boolean) ajaxSingleObj;
		}
	}
	
	public String getReRender() {
		return reRender;
	}
	
	public void setReRender(String reRender) {
		this.reRender = reRender;
	}
}
