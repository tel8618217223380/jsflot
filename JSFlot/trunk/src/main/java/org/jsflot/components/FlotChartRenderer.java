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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import org.jsflot.xydata.XYDataList;
import org.jsflot.xydata.XYDataPoint;
import org.jsflot.xydata.XYDataSetCollection;
import org.json.JSONException;
import org.json.JSONObject;

public class FlotChartRenderer extends Renderer {

	private static final Logger log = Logger.getLogger(FlotChartRenderer.class.getName());

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
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
			chartData.setTooltipPosition((String)get(component, context, "tooltipPosition"));
			chartData.setTooltipFollowMouse(get(component, context, "tooltipFollowMouse"));
			chartData.setLegendOpacity(get(component, context, "legendOpacity"));
			chartData.setChartType((String)get(component, context, "chartType"));
			chartData.setXaxisTitle((String)get(component, context, "xaxisTitle"));
			chartData.setShowXaxisLabels(get(component, context, "showXaxisLabels"));
			chartData.setYaxisTitle((String)get(component, context, "yaxisTitle"));
			chartData.setShowYaxisLabels(get(component, context, "showYaxisLabels"));
			chartData.setNumberOfXAxisTicks(get(component, context, "numberOfXAxisTicks"));
			chartData.setXaxisMinValue(get(component, context, "xaxisMinValue"));
			chartData.setXaxisMaxValue(get(component, context, "xaxisMaxValue"));
			chartData.setNumberOfYAxisTicks(get(component, context, "numberOfYAxisTicks"));
			chartData.setYaxisMinValue(get(component, context, "yaxisMinValue"));
			chartData.setYaxisMaxValue(get(component, context, "yaxisMaxValue"));
			
			XYDataSetCollection xyCollection = (XYDataSetCollection) get(component, context, "value");
			String functionBody = generateFunctionBody(xyCollection, id, chartData);

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

		}
	}

	private Object get(UIComponent component, FacesContext context, String name) {
		ValueBinding binding = component.getValueBinding(name);
		if (binding != null)
			return binding.getValue(context);
		else
			return component.getAttributes().get(name);
	}

	private String generateFunctionBody(XYDataSetCollection xyCollection, String id, FlotChartRendererData chartData) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		
		//Calculate the bar width to support clustered bar charts
		double barWidth = 0.5d;
		double offset = 0d;
		if (xyCollection.getDataList().size() > 0) {
			XYDataList list = xyCollection.getDataList().get(0);
			barWidth = list.calculateAvgPointDistance() / (xyCollection.getDataList().size() + 1);
			chartData.setBarWidth(barWidth);
			//Calculate the start offset
			offset = (xyCollection.getDataList().size() / 2) * barWidth * -1;
		}
		
		for (XYDataList list : xyCollection.getDataList()) {
			String label = id + index;
			
			sb.append("var ").append(label).append("JSONData").append(" = [");
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
			sb.append("];");
			index++;
			offset += barWidth;
		}

		String dataArrayString = generateDataOptions(xyCollection, id, chartData.getLegend(), chartData.getFillLines());
		String chartOptions = generateChartOptions(chartData);

		sb.append("\n").append("var f = Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],").append(chartOptions).append(");");

		return sb.toString();
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
					mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + dateFormat(new Date(obj.x*1)) +'<br/>y = ' + yaxisConverter(obj.y); }");
				} else {
					mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + obj.x +'<br/>y = ' + yaxisConverter(obj.y); }");
				}
				chartOptions.put("mouse", mouseOptions);
			}

			JSONObject xaxisOptions = new JSONObject();
			xaxisOptions.put("showLabels", chartData.getShowXaxisLabels().booleanValue());
			xaxisOptions.put("labelsAngle", 45);
			
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
				yaxisOptions.put("titleAngle", "90");
			}
			chartOptions.put("yaxis", yaxisOptions);

			//Lines makes only sense in a XY Plot
			JSONObject lineOptions = new JSONObject();
			lineOptions.put("show", chartData.getShowLines().booleanValue());
			lineOptions.put("fill", chartData.getFillLines().booleanValue());
			chartOptions.put("lines", lineOptions);

			//Select chart type
			JSONObject chartTypeOptions = new JSONObject();
			if (chartData.getChartType().equals("bar")) {
				chartTypeOptions.put("show", true);
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

			if (chartData.getCrosshair().equals("x") || chartData.getCrosshair().equals("y") || chartData.getCrosshair().equals("xy")) {
				JSONObject crosshairOptions = new JSONObject();
				crosshairOptions.put("mode", chartData.getCrosshair());
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
			
			

			retVal = chartOptions.toString(3).replace("\"", "");
		} catch (JSONException je) {

		}

		return retVal;
	}

	private String generateDataOptions(XYDataSetCollection xyCollection, String id, String legend, Boolean fillLines) {
		String retVal = "";

		try {
			int index = 0;
			for (XYDataList list : xyCollection.getDataList()) {

				JSONObject jSonData = new JSONObject();
				String label = id + index;
				jSonData.put("data", label + "JSONData");

				if (list.isFillLines() || list.isShowLines()) {
					JSONObject lineJson = new JSONObject();
					if (list.isFillLines()) {
						lineJson.put("fill", list.isFillLines());
					}
					if (list.isShowLines()) {
						lineJson.put("lines", list.isShowLines());
					}
					jSonData.put("lines", lineJson);
				}

				if (list.isShowDataPoints()) {
					JSONObject pointsOptions = new JSONObject();
					pointsOptions.put("show", list.isShowDataPoints());
					jSonData.put("points", pointsOptions);
				}

				if (list.getLabel() != null) {
					jSonData.put("label", "'" + list.getLabel() + "'");
				}

				retVal += jSonData.toString(3).replace("\"", "");
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
}