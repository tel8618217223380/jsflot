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
	
	public Integer getNumberOfYAxisTicks() {
		return numberOfYAxisTicks;
	}

	public void setNumberOfYAxisTicks(Integer numberOfYAxisTicks) {
		this.numberOfYAxisTicks = numberOfYAxisTicks;
	}

	public Double getXaxisMinValue() {
		return xaxisMinValue;
	}

	public void setXaxisMinValue(Double xaxisMinValue) {
		this.xaxisMinValue = xaxisMinValue;
	}

	public Double getXaxisMaxValue() {
		return xaxisMaxValue;
	}

	public void setXaxisMaxValue(Double xaxisMaxValue) {
		this.xaxisMaxValue = xaxisMaxValue;
	}

	public Double getYaxisMinValue() {
		return yaxisMinValue;
	}

	public void setYaxisMinValue(Double yaxisMinValue) {
		this.yaxisMinValue = yaxisMinValue;
	}

	public Double getYaxisMaxValue() {
		return yaxisMaxValue;
	}

	public void setYaxisMaxValue(Double yaxisMaxValue) {
		this.yaxisMaxValue = yaxisMaxValue;
	}

	
}
