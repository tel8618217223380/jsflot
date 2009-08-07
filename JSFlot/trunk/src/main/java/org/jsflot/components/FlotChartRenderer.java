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

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.render.Renderer;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.http.HttpServletRequest;

import org.jsflot.xydata.XYDataList;
import org.jsflot.xydata.XYDataPoint;
import org.jsflot.xydata.XYDataSetCollection;
import org.json.JSONException;
import org.json.JSONObject;

public class FlotChartRenderer extends Renderer {

	private static final Logger log = Logger.getLogger(FlotChartRenderer.class.getName());

	@Override
	public void decode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub

		String clientId = component.getClientId(context);

		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String ajaxRequest = request.getParameter(ComponentRendererUtil.AJAX_REQUEST);
		if (ajaxRequest != null && ajaxRequest.equalsIgnoreCase("true")) {
			String ajaxClientId = request.getParameter("clientId");
			if (ajaxClientId == null || !ajaxClientId.equals(clientId)) {
				// This is not the correct component. Return
				return;
			} 
			
			String event = request.getParameter(ComponentRendererUtil.JSFLOT_EVENT);
			if (event != null && event.equalsIgnoreCase("drag")) {
				String componentValue = request.getParameter("componentValue");
				Double dragValue = null;
				try {
					dragValue = Double.parseDouble(componentValue);
				} catch (NumberFormatException nfe) {
					//If drag value cannot be found, return
					return;
				}
				//ValueChangeEvent changeEvent = new ValueChangeEvent(((UIComponent) component), "0", componentValue);
				//((UIComponent) component).queueEvent(changeEvent);
				FlotChartDraggedEvent dragEvent = new FlotChartDraggedEvent(component, dragValue);
				dragEvent.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
				dragEvent.queue();
			} else if (event != null && event.equalsIgnoreCase("click")) {
				String clickX = request.getParameter(ComponentRendererUtil.JSFLOT_CLICK_X);
				String clickY = request.getParameter(ComponentRendererUtil.JSFLOT_CLICK_Y);
				String clickIndex = request.getParameter(ComponentRendererUtil.JSFLOT_CLICK_INDEX);
				String clickSeriesLabel = request.getParameter(ComponentRendererUtil.JSFLOT_SERIES_LABEL);
				String clickSeriesIndex = request.getParameter(ComponentRendererUtil.JSFLOT_SERIES_INDEX);
				
				XYDataPoint clickedPoint = null;
				Integer clickIndexInt = null;
				Integer clickSeriesIndexInt = null;
				try {
					clickedPoint = new XYDataPoint(Double.parseDouble(clickX), Double.parseDouble(clickY));
					clickIndexInt = Integer.parseInt(clickIndex);
					clickSeriesIndexInt = Integer.parseInt(clickSeriesIndex);
				} catch (NumberFormatException nfe) {
					//No valid point clicked. Return
					return;
				}
				
				FlotChartClickedEvent clickEvent = new FlotChartClickedEvent(component, clickedPoint, clickIndexInt, clickSeriesIndexInt, clickSeriesLabel);
				clickEvent.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
				clickEvent.queue();
			}
		}
	}
	
	private boolean isAjaxRequest(FacesContext context, UIComponent component) {
		boolean ajaxRequest = false;
		
		String clientId = component.getClientId(context);
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String requestedWith = request.getHeader("X-Requested-With");
		if (requestedWith !=  null && request.equals("XMLHttpRequest")) {
			//Prototype and JQuery sets this header variable
			ajaxRequest = true;
		}
		
		if (!ajaxRequest) {
			//JBoss Richfaces uses the AJAXREQUEST parameter to determine AJAX Requests
			if (request.getParameter("AJAXREQUEST") != null) {
				ajaxRequest = true;
			}
		}
		
		if (!ajaxRequest) {
			//JSFlot uses the ComponentRendererUtil.AJAX_REQUEST parameter
			String ajaxRequestParam = request.getParameter(ComponentRendererUtil.AJAX_REQUEST);
			if (ajaxRequestParam != null && ajaxRequestParam.equalsIgnoreCase("true")) {
				String ajaxClientId = request.getParameter("clientId");
					ajaxRequest = true;
			}
		}
		
		return ajaxRequest;
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		boolean ajaxRequest = isAjaxRequest(context, component);
		
		Boolean rendered = (Boolean) get(component, context, "rendered");
		if (rendered == null) {
			rendered = new Boolean("true");
		}

		if (rendered) {
			ResponseWriter writer = context.getResponseWriter();
			// renderScriptOnce(context, component, writer);
			writeComponentContents(context, writer, component, ajaxRequest);
		}
	}

	private void writeComponentContents(FacesContext context, ResponseWriter writer, UIComponent component, boolean ajaxRequest) throws IOException {
		String clientId = component.getClientId(context);
		String id = (String) get(component, context, "id");
		if (id == null) {
			id = "testID";
		}
		if (id.equals(clientId)) {
			id = id + "_chart";
		}

		FlotChartRendererData chartData = getChartData(component, context);
		String reRenderIDs = chartData.getReRender();
		String newReRenderIDs = "";
		if (reRenderIDs != null) {
			//Only try to find the real reRenderId if an id to reRender is supplied
			if (reRenderIDs.indexOf(",") != -1) {
				String[] idArray = reRenderIDs.split(",");
				for (String compId : idArray) {
					UIComponent foundComponent = ComponentRendererUtil.findComponent(context.getViewRoot(), compId.trim());
					if (foundComponent != null) {
						if (!newReRenderIDs.equals("")) {
							//Prepend a comma
							newReRenderIDs = newReRenderIDs + ",";
						} 
						newReRenderIDs += foundComponent.getClientId(context);
					}
				}
				chartData.setReRender(newReRenderIDs);
			} else {
				// Single reRender
				UIComponent comp = ComponentRendererUtil.findComponent(context.getViewRoot(), reRenderIDs);
				if (comp != null) {
					String compClientId = comp.getClientId(context);
					chartData.setReRender(compClientId);
				}
			}
		}

		XYDataSetCollection xyCollection = (XYDataSetCollection) get(component, context, "value");
		String functionBody = generateFunctionBody(xyCollection, id, clientId, chartData, context, component, ajaxRequest);

		writer.startElement("div", component);
		writer.writeAttribute("id", clientId, null);
		
			int height = 600;
			int zoomHeight = 75;
			try {
				height = Integer.parseInt(chartData.getHeight());
			} catch (NumberFormatException nfe) {
				//Nothing to do...
			}
			
			if (chartData.getChartZoomable() != null && chartData.getChartZoomable().booleanValue()) {
				height -= zoomHeight;
				writer.startElement("div", component);
				writer.writeAttribute("id", id + "_zoomable", null);
				writer.writeAttribute("height", zoomHeight, null);
				writer.writeAttribute("width", chartData.getWidth(), null);
				writer.writeAttribute("style", "width:" + chartData.getWidth() + "px;height:" + zoomHeight + "px;", null);
				writer.endElement("div");
				writer.write("\n");
			}
		
			writer.startElement("div", component);
			writer.writeAttribute("id", id, null);
			writer.writeAttribute("height", height, null);
			writer.writeAttribute("width", chartData.getWidth(), null);
			writer.writeAttribute("style", "width:" + chartData.getWidth() + "px;height:" + height + "px;", null);
			writer.endElement("div");
			writer.write("\n");
	
			writer.startElement("script", component);
			writer.writeAttribute("id", clientId + "_source", null);
			writer.writeAttribute("language", "javascript", null);
			writer.writeAttribute("type", "text/javascript", null);
			writer.write(functionBody);
			writer.endElement("script");
			writer.write("\n");
	
			writer.startElement("input", component);
			writer.writeAttribute("type", "hidden", null);
			writer.writeAttribute("id", clientId + "_hiddenValue", null);
			writer.writeAttribute("value", "", null);
			writer.endElement("input");
			writer.write("\n");

		writer.endElement("div");
		writer.write("\n");
	}

	private Object get(UIComponent component, FacesContext context, String name) {
		ValueBinding binding = component.getValueBinding(name);
		if (binding != null)
			return binding.getValue(context);
		else
			return component.getAttributes().get(name);
	}

	public String writeDraggableContents(FlotChartRendererData chartData, FacesContext context, String id, String clientId, UIComponent component) {
		String jsflotId = id + "_jsflot";
		StringBuffer sb = new StringBuffer();
		if (chartData.getChartDraggable() != null && chartData.getChartDraggable().booleanValue()) {
			/** Fired on mouse down */
			sb.append("function " + jsflotId + "drag(e){\n");
				sb.append("dragstart = " + jsflotId + ".getEventPosition(e);\n");
				sb.append("document.observe('mousemove', " + jsflotId + "move);\n");
				sb.append("document.observe('mouseup', " + jsflotId + "release);\n");
			sb.append("}\n");
			
			
			/** Fired on mouse move */
			sb.append("function " + jsflotId + "move(e) {\n");
				sb.append("dragend = " + jsflotId + ".getEventPosition(e),\n");
				sb.append("xaxis = " + jsflotId + ".axes.x,\n");
				sb.append("offset = dragstart.x - dragend.x;\n");
				
				sb.append("var moveOptions = Object.extend(Object.clone(options), { xaxis: {min: xaxis.min + offset, max: xaxis.max + offset }} || {});");
				sb.append("moveOptions = Flotr.merge((moveOptions || {}), options);");
				sb.append(jsflotId + " = " + jsflotId + "drawGraph(moveOptions);\n");
				sb.append(jsflotId + ".overlay.observe('mousedown', " + jsflotId + "drag);\n");
			sb.append("}\n");
			
			
			/** Fired on mouse up */
			sb.append("function " + jsflotId + "release(e){\n");
				sb.append("document.stopObserving('mousemove', " + jsflotId + "move);\n");
				sb.append("endingx = " + jsflotId + ".axes.x.min;\n");
				sb.append("offset = endingx - startingx;\n");
				sb.append("startingx = endingx;\n");
				
				String url = ((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURI();

				// Fire off AJAX request
				sb.append("if (offset != 0) {\n");
				sb.append("var ajaxoptions = new JSFlot.Options('" + clientId + "', offset, '" + clientId + "', '" + clientId + "');\n");
				sb.append("ajaxoptions._ajaxSingle = " + chartData.getAjaxSingle().booleanValue() + ";\n");
					if (chartData.getReRender() != null && !chartData.getReRender().equals("")) {
						sb.append("ajaxoptions._otherRerenderIDs = '" + chartData.getReRender() + "';\n");
					}
					sb.append("JSFlot.AJAX.Submit('" + ComponentRendererUtil.getNestingForm(component).getId() + "', 'drag', '" + url
						+ "', ajaxoptions);\n");
					sb.append("}\n");
			sb.append("}\n");
			
			sb.append(jsflotId + ".overlay.observe('mousedown', " + jsflotId + "drag);\n");
		}
		
		return sb.toString();
	}
	
	private String writeClickableContents(FlotChartRendererData chartData, FacesContext context, String id, String clientId, UIComponent component) {
		StringBuffer sb = new StringBuffer();
		String url = ((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURI();
		if (chartData.getChartClickable() != null && chartData.getChartClickable().booleanValue()) {	
			sb.append("$('" + id + "').observe('flotr:clickHit', function(event){\n");
			sb.append("var clickPos = event.memo[0];\n");
			sb.append("if (clickPos != null) {\n");
				sb.append("var ajaxoptions = new JSFlot.Options('" + clientId + "', 1, '" + clientId + "', '" + clientId + "');\n");
				sb.append("ajaxoptions._ajaxSingle = " + chartData.getAjaxSingle().booleanValue() + ";\n");
				sb.append("ajaxoptions._clickedPosition = clickPos;\n");
				if (chartData.getReRender() != null && !chartData.getReRender().equals("")) {
					sb.append("ajaxoptions._otherRerenderIDs = '" + chartData.getReRender() + "';\n");
				}
				sb.append("JSFlot.AJAX.Submit('" + ComponentRendererUtil.getNestingForm(component).getId() + "', 'click', '" + url
					+ "', ajaxoptions);\n");
			sb.append("}\n");
			sb.append("});\n");
		}
		
		return sb.toString();
	}

	private String generateFunctionBody(XYDataSetCollection xyCollection, String id, String clientId, FlotChartRendererData chartData, FacesContext context, UIComponent component, 
			boolean ajaxRequest) {
		String jsflotId = id + "_jsflot";
		StringBuilder sb = new StringBuilder();

		String dataArrayString = generateDataOptions(xyCollection, chartData);
		String chartOptions = generateChartOptions(chartData);

		if (ajaxRequest) {
				sb.append("drawJSFlotChart();\n");
		} else {
			sb.append("document.observe('dom:loaded', function() {\n");
				sb.append("drawJSFlotChart();\n");
			sb.append("});\n\n");
		}
		
		sb.append("function drawJSFlotChart() {\n");
			sb.append("var options = " + chartOptions + ";\n");
			sb.append("function " + jsflotId + "drawGraph(opts){\n");
				sb.append("var o = Object.extend(Object.clone(options), opts || {});\n");
				sb.append("return Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],o);\n");
			sb.append("}\n");
			
			if (chartData.getChartZoomable() != null && chartData.getChartZoomable().booleanValue()) {
				sb.append("function " + jsflotId + "ZoomDrawGraph(opts){\n");
					sb.append("var o = { xaxis: {showLabels: false}, yaxis: {showLabels: false}, legend: {show: false}, lines: { show: true, lineWidth: 1 }, shadowSize: 0, grid: { color: '#999', outlineWidth: 1 }, selection: { mode: 'xy' } };\n");
					sb.append("return Flotr.draw($('" + id + "_zoomable'), [ " + dataArrayString + " ],o);\n");
				sb.append("}\n");
				
				sb.append("var " + jsflotId + "_zoomable = " + jsflotId + "ZoomDrawGraph();\n");
			}
			sb.append("var " + jsflotId + " = " + jsflotId + "drawGraph();\n");
			sb.append("var startingx = " + jsflotId + ".axes.x.min;\n");
			sb.append("var endingx = startingx;\n");
			sb.append("var dragstart;\n");
			sb.append("var dragend;\n");
			
			if (chartData.getChartZoomable() != null && chartData.getChartZoomable().booleanValue()) {
				sb.append("$('" + id + "_zoomable').observe('flotr:select', function(evt){");
					sb.append("var o = Object.extend(Object.clone(options), { xaxis: {min: evt.memo[0].x1, max: evt.memo[0].x2 } }, { yaxis: {min: evt.memo[0].y1, max: evt.memo[0].y2 } } || {});\n");
					sb.append(jsflotId = "Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],o);\n");
				sb.append("});");
				
				sb.append("$('" + id + "_zoomable').observe('flotr:click', function(evt){");
				sb.append("var o = Object.extend(Object.clone(options), {} || {});\n");
				sb.append(jsflotId = "Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],o);\n");
			sb.append("});");
			}
			
			
			sb.append(writeDraggableContents(chartData, context, id, clientId, component));
			
			sb.append(writeClickableContents(chartData, context, id, clientId, component));
			
			//sb.append(writeZoomableContents(chartData, context, id, clientId, component));
		sb.append("}\n");
		return sb.toString();
	}

	private FlotChartRendererData getChartData(UIComponent component, FacesContext context) {
		FlotChartRendererData chartData = new FlotChartRendererData();
		chartData.setShowDataPoints(get(component, context, "showDataPoints"));
		chartData.setShowLines(get(component, context, "showLines"));
		chartData.setWidth((String) get(component, context, "width"));
		chartData.setHeight((String) get(component, context, "height"));
		chartData.setLegend((String) get(component, context, "legend"));
		chartData.setFillLines(get(component, context, "fillLines"));
		chartData.setLegendColor((String) get(component, context, "legendColor"));
		chartData.setMode((String) get(component, context, "mode"));
		chartData.setTimeFormat((String) get(component, context, "timeFormat"));
		chartData.setLegendPosition((String) get(component, context, "legendPosition"));
		chartData.setCrosshair((String) get(component, context, "crosshair"));
		chartData.setSpreadsheet(get(component, context, "spreadsheet"));
		chartData.setTitle((String) get(component, context, "title"));
		chartData.setSubtitle((String) get(component, context, "subtitle"));
		chartData.setLegendColumns(get(component, context, "legendColumns"));
		chartData.setShowTooltip(get(component, context, "showTooltip"));
		chartData.setTooltipPosition((String) get(component, context, "tooltipPosition"));
		chartData.setTooltipFollowMouse(get(component, context, "tooltipFollowMouse"));
		chartData.setLegendOpacity(get(component, context, "legendOpacity"));
		chartData.setChartType((String) get(component, context, "chartType"));
		chartData.setXaxisTitle((String) get(component, context, "xaxisTitle"));
		chartData.setShowXaxisLabels(get(component, context, "showXaxisLabels"));
		chartData.setYaxisTitle((String) get(component, context, "yaxisTitle"));
		chartData.setShowYaxisLabels(get(component, context, "showYaxisLabels"));
		chartData.setNumberOfXAxisTicks(get(component, context, "numberOfXAxisTicks"));
		chartData.setXaxisMinValue(get(component, context, "xaxisMinValue"));
		chartData.setXaxisMaxValue(get(component, context, "xaxisMaxValue"));
		chartData.setNumberOfYAxisTicks(get(component, context, "numberOfYAxisTicks"));
		chartData.setYaxisMinValue(get(component, context, "yaxisMinValue"));
		chartData.setYaxisMaxValue(get(component, context, "yaxisMaxValue"));
		chartData.setMarkers(get(component, context, "markers"));
		chartData.setMarkerPosition((String) get(component, context, "markerPosition"));
		chartData.setXaxisLabelRotation(get(component, context, "xaxisLabelRotation"));
		chartData.setXaxisTitleRotation(get(component, context, "xaxisTitleRotation"));
		chartData.setYaxisLabelRotation(get(component, context, "yaxisLabelRotation"));
		chartData.setYaxisTitleRotation(get(component, context, "yaxisTitleRotation"));
		chartData.setChartDraggable(get(component, context, "chartDraggable"));
		chartData.setChartClickable(get(component, context, "chartClickable"));
		chartData.setAjaxSingle(get(component, context, "ajaxSingle"));
		chartData.setReRender((String) get(component, context, "reRender"));
		chartData.setChartZoomable(get(component, context, "chartZoomable"));

		// Set ChartDraggedListener
		String chartDraggedListener = (String) get(component, context, "chartDraggedListener");
		setMethodBinding(component, "chartDraggedListener", chartDraggedListener, new Class[] { FlotChartClickedEvent.class });

		return chartData;
	}

	private String generateChartOptions(FlotChartRendererData chartData) {
		String retVal = "";
		try {
			JSONObject chartOptions = new JSONObject();

			JSONObject legendOptions = new JSONObject();
			legendOptions.put("position", "'" + chartData.getLegendPosition() + "'");
			legendOptions.put("backgroundColor", "'" + chartData.getLegendColor() + "'");
			legendOptions.put("noColumns", chartData.getLegendColumns());
			legendOptions.put("backgroundOpacity", chartData.getLegendOpacity());
			legendOptions.put("show", true);
			chartOptions.put("legend", legendOptions);

			chartOptions.put("HtmlText", false);

			if (chartData.getShowTooltip().booleanValue()) {
				JSONObject mouseOptions = new JSONObject();
				mouseOptions.put("track", true);
				mouseOptions.put("relative", chartData.getTooltipFollowMouse().booleanValue());
				if (chartData.getTooltipPosition() != null) {
					mouseOptions.put("position", "'" + chartData.getTooltipPosition() + "'");
				}
				if (chartData.getMode().equalsIgnoreCase("Time")) {
					String timeFormat = chartData.getTimeFormat();
					if (chartData.getTimeFormat() != null && chartData.getTimeFormat().length() > 1) {
						mouseOptions .put("trackFormatter", "function(obj){ return 'x = ' + Flotr.Date.format((new Date(obj.x*1)), '" + chartData.getTimeFormat() + "') +'<br/>y = ' + yaxisConverter(obj.y); }");
					//} else {
					//	mouseOptions .put("trackFormatter", "function(obj){ return 'x = ' + Flotr.Date.format((new Date(obj.x*1)), '') +'<br/>y = ' + yaxisConverter(obj.y); }");
					//	
					//	Flotr.Date.getFormat(time, span)
					}

				} else {
					mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + obj.x +'<br/>y = ' + yaxisConverter(obj.y); }");
				}
				chartOptions.put("mouse", mouseOptions);
			}

			JSONObject xaxisOptions = new JSONObject();
			xaxisOptions.put("showLabels", chartData.getShowXaxisLabels().booleanValue());

			if (chartData.getNumberOfXAxisTicks() != null) {
				xaxisOptions.put("noTicks", chartData.getNumberOfXAxisTicks());
			}
			if (chartData.getXaxisMinValue() != null) {
				xaxisOptions.put("min", chartData.getXaxisMinValue());
			}
			if (chartData.getXaxisMaxValue() != null) {
				xaxisOptions.put("max", chartData.getXaxisMaxValue());
			}

			if (chartData.getXaxisTitle() != null && chartData.getXaxisTitle().length() > 0) {
				xaxisOptions.put("title", "'" + chartData.getXaxisTitle() + "'");
			}
			/*
			 * if (chartData.getMode().equalsIgnoreCase("Time")) {
			 * xaxisOptions.put("tickFormatter",
			 * "function(n){ return dateFormat(new Date(n*1)); }"); }
			 */
			if (chartData.getXaxisLabelRotation() != null && !chartData.getXaxisLabelRotation().equals(0)) {
				xaxisOptions.put("labelsAngle", chartData.getXaxisLabelRotation());
			}
			if (chartData.getXaxisTitleRotation() != null && !chartData.getXaxisTitleRotation().equals(0)) {
				xaxisOptions.put("titleAngle", chartData.getXaxisTitleRotation());
			}

			if (chartData.getMode().equalsIgnoreCase("Time")) {
				xaxisOptions.put("mode", "'time'");
				if (chartData.getTimeFormat() != null && chartData.getTimeFormat().length() > 1) {
					xaxisOptions.put("timeFormat", "'" + chartData.getTimeFormat() + "'");
				}
			}

			if (xaxisOptions.length() > 0) {
				chartOptions.put("xaxis", xaxisOptions);
			}

			JSONObject yaxisOptions = new JSONObject();
			yaxisOptions.put("tickFormatter", "function(n){ return yaxisConverter(n); }");
			yaxisOptions.put("showLabels", chartData.getShowYaxisLabels().booleanValue());
			if (chartData.getNumberOfYAxisTicks() != null) {
				yaxisOptions.put("noTicks", chartData.getNumberOfYAxisTicks());
			}
			if (chartData.getYaxisMinValue() != null) {
				yaxisOptions.put("min", chartData.getYaxisMinValue());
			}
			if (chartData.getYaxisMaxValue() != null) {
				yaxisOptions.put("max", chartData.getYaxisMaxValue());
			}
			if (chartData.getYaxisTitle() != null && chartData.getYaxisTitle().length() > 0) {
				yaxisOptions.put("title", "'" + chartData.getYaxisTitle() + "'");
			}

			if (chartData.getYaxisLabelRotation() != null && !chartData.getYaxisLabelRotation().equals(0)) {
				yaxisOptions.put("labelsAngle", chartData.getYaxisLabelRotation());
			}
			if (chartData.getYaxisTitleRotation() != null && !chartData.getYaxisTitleRotation().equals(0)) {
				yaxisOptions.put("titleAngle", chartData.getYaxisTitleRotation());
			}

			chartOptions.put("yaxis", yaxisOptions);

			// Lines makes only sense in a XY Plot
			JSONObject lineOptions = new JSONObject();
			lineOptions.put("show", chartData.getShowLines().booleanValue());
			lineOptions.put("fill", chartData.getFillLines().booleanValue());
			chartOptions.put("lines", lineOptions);

			// Select chart type
			JSONObject chartTypeOptions = new JSONObject();
			if (chartData.getChartType().equals("bar")) {
				chartTypeOptions.put("show", true);
				chartTypeOptions.put("barWidth", chartData.getBarWidth());
				chartOptions.put("bars", chartTypeOptions);
			} else if (chartData.getChartType().equals("stackedBar")) {
				chartTypeOptions.put("show", true);
				chartTypeOptions.put("stacked", true);
				chartTypeOptions.put("barWidth", chartData.getBarWidth());
				chartOptions.put("bars", chartTypeOptions);
			} else if (chartData.getChartType().equals("candles")) {
				chartTypeOptions.put("fill", true);
				chartTypeOptions.put("show", true);
				chartOptions.put("candles", chartTypeOptions);
			} else if (chartData.getChartType().equals("pie")) {
				chartTypeOptions.put("show", true);
				chartOptions.put("pie", chartTypeOptions);
			}

			JSONObject pointsOptions = new JSONObject();
			pointsOptions.put("show", chartData.getShowDataPoints().booleanValue());
			chartOptions.put("points", pointsOptions);

			if (chartData.getCrosshair() != null
					&& (chartData.getCrosshair().equals("x") || chartData.getCrosshair().equals("y") || chartData.getCrosshair().equals("xy"))) {
				JSONObject crosshairOptions = new JSONObject();
				crosshairOptions.put("mode", "'" + chartData.getCrosshair() + "'");
				chartOptions.put("crosshair", crosshairOptions);
			}

			if (chartData.getSpreadsheet() != null && chartData.getSpreadsheet().booleanValue()) {
				JSONObject spreadsheetOptions = new JSONObject();
				spreadsheetOptions.put("show", "true");
				chartOptions.put("spreadsheet", spreadsheetOptions);
			}

			if (chartData.getTitle() != null && chartData.getTitle().length() >= 0) {
				chartOptions.put("title", "'" + chartData.getTitle() + "'");
			}

			if (chartData.getSubtitle() != null && chartData.getSubtitle().length() >= 0) {
				chartOptions.put("subtitle", "'" + chartData.getSubtitle() + "'");
			}

			if (chartData.getMarkers() != null && chartData.getMarkers().booleanValue()) {
				JSONObject markersOptions = new JSONObject();
				markersOptions.put("show", true);
				// if (chartData.getMode().equalsIgnoreCase("Time")) {
				// markersOptions.put("labelFormatter",
				// "function(obj){ return dateFormat(new Date(obj.x*1), 'HH:MM:ss'); }");
				// }
				markersOptions.put("position", "'" + chartData.getMarkerPosition() + "'");
				chartOptions.put("markers", markersOptions);
			}

			retVal = chartOptions.toString(3).replace("\"", "");
		} catch (JSONException je) {

		}

		return retVal;
	}

	private String generateData(XYDataSetCollection xyCollection, XYDataList list, FlotChartRendererData chartData) {
		StringBuilder sb = new StringBuilder();

		// Calculate the bar width to support clustered bar charts
		double barWidth = 0.5d;
		double offset = 0d;
		if (xyCollection.getDataList().size() > 0) {
			XYDataList tempList = xyCollection.getDataList().get(0);
			if (chartData.getChartType().equals("bar")) {
				barWidth = list.calculateAvgPointDistance() / (xyCollection.getDataList().size() + 1);
			} else if (chartData.getChartType().equals("stackedBar")) {
				barWidth = list.calculateAvgPointDistance() * 0.7;
			}
			chartData.setBarWidth(barWidth);
			// Calculate the start offset
			offset = (xyCollection.getDataList().size() / 2) * barWidth * -1;
			int indexOf = xyCollection.indexOf(list);
			if (indexOf > -1) {
				// Calculate this bars offset
				offset += (indexOf * barWidth);
			}
		}

		sb.append(" [");
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		nf.setMaximumFractionDigits(3);
		nf.setGroupingUsed(false);
		if (list != null) {
			for (int i = 0; i < list.size() - 1; i++) {
				XYDataPoint p = list.get(i);
				if (chartData.getChartType().equalsIgnoreCase("bar")) {
					sb.append("[").append(nf.format(p.getX().doubleValue() + offset)).append(",").append(nf.format(p.getY())).append("]").append(", ");
				} else {
					sb.append("[").append(nf.format(p.getX())).append(",").append(nf.format(p.getY())).append("]").append(", ");
				}

			}
			// Last Row
			XYDataPoint p = list.get(list.size() - 1);
			if (chartData.getChartType().equalsIgnoreCase("bar")) {
				sb.append("[").append(nf.format(p.getX().doubleValue() + offset)).append(",").append(nf.format(p.getY())).append("]");
			} else {
				sb.append("[").append(nf.format(p.getX())).append(",").append(nf.format(p.getY())).append("]");
			}
		}
		sb.append("]");

		return sb.toString();

	}

	private String generateDataOptions(XYDataSetCollection xyCollection, FlotChartRendererData chartData) {
		String retVal = "";

		try {
			int index = 0;
			for (XYDataList list : xyCollection.getDataList()) {

				JSONObject seriesOptions = new JSONObject();
				seriesOptions.put("data", generateData(xyCollection, list, chartData));

				if (list.isFillLines() || list.isShowLines()) {
					JSONObject lineJson = new JSONObject();
					if (list.isFillLines()) {
						lineJson.put("fill", list.isFillLines());
					}
					if (list.isShowLines()) {
						lineJson.put("lines", list.isShowLines());
					}
					seriesOptions.put("lines", lineJson);
				}

				if (list.isShowDataPoints()) {
					JSONObject pointsOptions = new JSONObject();
					pointsOptions.put("show", list.isShowDataPoints());
					seriesOptions.put("points", pointsOptions);
				}

				if (list.isMarkers()) {
					JSONObject markersOptions = new JSONObject();
					markersOptions.put("show", true);
					markersOptions.put("position", "'" + list.getMarkerPosition() + "'");
					seriesOptions.put("markers", markersOptions);
				}

				if (list.getLabel() != null) {
					seriesOptions.put("label", "'" + list.getLabel() + "'");
				}

				retVal += seriesOptions.toString(3).replace("\"", "");
				if (index != xyCollection.size() - 1) {
					// No comma separator for the last row
					retVal += ",";
				}
				index++;
			}
		} catch (JSONException je) {

		}

		return retVal;
	}

	private static void setMethodBinding(UIComponent component, String attributeName, String attributeValue, Class[] paramTypes) {
		if (attributeValue == null)
			return;
		if (UIComponentTag.isValueReference(attributeValue)) {
			FacesContext context = FacesContext.getCurrentInstance();
			Application app = context.getApplication();
			MethodBinding mb = app.createMethodBinding(attributeValue, paramTypes);
			component.getAttributes().put(attributeName, mb);
		}
	}
}