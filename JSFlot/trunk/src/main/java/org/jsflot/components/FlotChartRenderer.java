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
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.render.Renderer;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		String id = (String) get(component, context, "id");
		if (id == null) {
			id = "testID";
		}

		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		String ajaxRequest = request.getParameter(ComponentRendererUtil.AJAX_REQUEST);
		if (ajaxRequest != null && ajaxRequest.equalsIgnoreCase("true")) {
			String ajaxClientId = request.getParameter("clientId");
			if (ajaxClientId == null || !ajaxClientId.equals(clientId)) {
				//This is not the correct component. Return
				return;
			}
			String componentValue = request.getParameter("componentValue");
		
			ValueChangeEvent changeEvent = new ValueChangeEvent(((UIComponent)component), "0", componentValue);
			((UIComponent)component).queueEvent(changeEvent);
		}
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		String clientId = component.getClientId(context);
		String id = (String) get(component, context, "id");
		if (id == null) {
			id = "testID";
		}

		Boolean rendered = (Boolean) get(component, context, "rendered");
		if (rendered == null) {
			rendered = new Boolean("true");
		}

		if (rendered) {
			ResponseWriter writer = context.getResponseWriter();
			// renderScriptOnce(context, component, writer);
			writeComponentContents(context, writer, component);
		}
	}
	
	private void writeComponentContents(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
		String clientId = component.getClientId(context);
		String id = (String) get(component, context, "id");
		if (id == null) {
			id = "testID";
		}
		
		
		FlotChartRendererData chartData = getChartData(component, context); 

		XYDataSetCollection xyCollection = (XYDataSetCollection) get(component, context, "value");
		String functionBody = generateFunctionBody(xyCollection, id, chartData);
		
		writer.startElement("div", component);
		writer.writeAttribute("id", id + "_enclosingDiv", null);

		writer.startElement("div", component);
		writer.writeAttribute("id", id, null);
		writer.writeAttribute("style", "width:" + chartData.getWidth() + "px;height:" + chartData.getHeight() + "px;", null);
		writer.endElement("div");
		writer.write("\n");

		writer.startElement("script", component);
		writer.writeAttribute("id", id + "_source", null);
		writer.writeAttribute("language", "javascript", null);
		writer.writeAttribute("type", "text/javascript", null);
		writer.write(functionBody);
		writer.endElement("script");
		writer.write("\n");
		
		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("id", id + "_hiddenValue", null);
		writer.writeAttribute("value", "", null);
		writer.endElement("input");
		writer.write("\n");

		writeDraggableContents(chartData.getChartDraggable(), writer, context, id, clientId, component);

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
	
	public void writeDraggableContents(Boolean chartDraggable, ResponseWriter writer, FacesContext context, String id, String clientId, UIComponent component)  throws IOException {
		if (chartDraggable != null && chartDraggable) {
			//If the chart is draggable, include JavaScript to listen to the drag event and to fire off AJAX request to update the chart. 
			StringBuilder observeFunctionBodyBuilder = new StringBuilder();
			observeFunctionBodyBuilder.append("$('" + id + "').observe('flotr:select', function(evt){\n");
			observeFunctionBodyBuilder.append("\tvar area = evt.memo[0];\n");
			observeFunctionBodyBuilder.append("\tvar areaMin = parseInt(area.xfirst);\n");
			observeFunctionBodyBuilder.append("\tvar areaMax = parseInt(area.xsecond);\n");
			observeFunctionBodyBuilder.append("\tvar areaRange = areaMax - areaMin;\n");
	
			String facesPrefix = (String) ((HttpSession) context.getExternalContext().getSession(true)).getAttribute("facesPrefix");
			if (facesPrefix == null) {
				facesPrefix = "";
			}
			String facesSuffix = (String) ((HttpSession) context.getExternalContext().getSession(true)).getAttribute("facesSuffix");
			if (facesSuffix == null) {
				facesSuffix = "";
			}
	
			String pathinfo = context.getExternalContext().getRequestPathInfo();
			String url = ((HttpServletRequest)context.getExternalContext().getRequest()).getRequestURI();
	
			//Fire off AJAX request
			observeFunctionBodyBuilder.append("\tdocument.getElementById('" + id + "_hiddenValue').value = areaRange; \n");
			observeFunctionBodyBuilder.append("var options = new JSFlot.Options('" + clientId + "', areaRange, '" + id + "', '" + id + "_enclosingDiv');");
			observeFunctionBodyBuilder.append("JSFlot.AJAX.Submit('" + ComponentRendererUtil.getNestingForm(component).getId() + "', 'drag', '" + url + "', options);\n");
			observeFunctionBodyBuilder.append("});");
	
			writer.startElement("script", component);
			writer.writeAttribute("id", id + "_selection", null);
			writer.writeAttribute("language", "javascript", null);
			writer.writeAttribute("type", "text/javascript", null);
			writer.write(observeFunctionBodyBuilder.toString());
			writer.endElement("script");
			writer.write("\n");
		}
	}

	private String generateFunctionBody(XYDataSetCollection xyCollection, String id, FlotChartRendererData chartData) {
		StringBuilder sb = new StringBuilder();

		String dataArrayString = generateDataOptions(xyCollection, chartData);
		String chartOptions = generateChartOptions(chartData);

		sb.append("\n").append("var f = Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],").append(chartOptions).append(");");

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
		//Set ChartDraggedListener
		String chartDraggedListener = (String)get(component, context, "chartDraggedListener");
		setMethodBinding(component, "chartDraggedListener", chartDraggedListener, new Class[] {ActionEvent.class});
		
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

			if (chartData.getChartDraggable() != null && chartData.getChartDraggable().booleanValue()) {
				JSONObject selectionOptions = new JSONObject();
				selectionOptions.put("mode", "'x'");
				chartOptions.put("selection", selectionOptions);
			}

			if (chartData.getShowTooltip().booleanValue()) {
				JSONObject mouseOptions = new JSONObject();
				mouseOptions.put("track", true);
				mouseOptions.put("relative", chartData.getTooltipFollowMouse().booleanValue());
				if (chartData.getTooltipPosition() != null) {
					mouseOptions.put("position", "'" + chartData.getTooltipPosition() + "'");
				}
				if (chartData.getMode().equalsIgnoreCase("Time")) {
					mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + dateFormat(new Date(obj.x*1)) +'<br/>y = ' + yaxisConverter(obj.y); }");
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
			if (chartData.getMode().equalsIgnoreCase("Time")) {
				xaxisOptions.put("tickFormatter", "function(n){ return dateFormat(new Date(n*1)); }");
			}
			if (chartData.getXaxisLabelRotation() != null && !chartData.getXaxisLabelRotation().equals(0)) {
				xaxisOptions.put("labelsAngle", chartData.getXaxisLabelRotation());
			}
			if (chartData.getXaxisTitleRotation() != null && !chartData.getXaxisTitleRotation().equals(0)) {
				xaxisOptions.put("titleAngle", chartData.getXaxisTitleRotation());
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
				if (chartData.getMode().equalsIgnoreCase("Time")) {
					markersOptions.put("labelFormatter", "function(obj){ return dateFormat(new Date(obj.x*1), 'HH:MM:ss'); }");
				}
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
				//Calculate this bars offset
				offset += (indexOf * barWidth);
			}
		}

		sb.append(" [");
		if (list != null) {
			for (int i = 0; i < list.size() - 1; i++) {
				XYDataPoint p = list.get(i);
				if (chartData.getChartType().equalsIgnoreCase("bar")) {
					sb.append("[").append(p.getX().doubleValue() + offset).append(",").append(p.getY()).append("]").append(", ");
				} else {
					sb.append("[").append(p.getX()).append(",").append(p.getY()).append("]").append(", ");
				}

			}
			// Last Row
			XYDataPoint p = list.get(list.size() - 1);
			if (chartData.getChartType().equalsIgnoreCase("bar")) {
				sb.append("[").append(p.getX().doubleValue() + offset).append(",").append(p.getY()).append("]");
			} else {
				sb.append("[").append(p.getX()).append(",").append(p.getY()).append("]");
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