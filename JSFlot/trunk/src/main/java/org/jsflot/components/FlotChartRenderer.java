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

import org.jsflot.xydata.XYDataPoint;
import org.json.JSONException;
import org.json.JSONObject;

public class FlotChartRenderer extends Renderer {

	private static final Logger log = Logger.getLogger(FlotChartRenderer.class.getName());
	private static final String RENDERED_SCRIPT_KEY = "JSFLOW_SCRIPT_KEY";

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
			//renderScriptOnce(context, component, writer);
			Boolean showDataPoints = new Boolean((String)get(component, context, "showDataPoints"));
			if (showDataPoints == null) {
				showDataPoints = new Boolean("false");
			}
			Boolean showLines = new Boolean((String)get(component, context, "showLines"));
			if (showLines == null) {
				showLines = new Boolean("false");
			}
			String width = (String)get(component, context, "width");
			if (width == null) {
				width = "600";
			}
			String height = (String)get(component, context, "height");
			if (height == null) {
				height = "300";
			}
			String legend = (String)get(component, context, "legend");
			
			Boolean fillLines = new Boolean((String)get(component, context, "fillLines"));
			if (fillLines == null) {
				fillLines = new Boolean(false);
			}
			String legendColor = (String)get(component, context, "legendColor");
			String mode= (String)get(component, context, "mode");
			if (mode == null) {
				mode = "Series";
			}

			List<XYDataPoint> xyList = (List<XYDataPoint>) get(component, context, "value");
			String functionBody = generateFunctionBody(xyList, id, showDataPoints, showLines, legend, fillLines, legendColor, mode);

			writer.startElement("div", component);
			writer.writeAttribute("id", id, null);
			writer.writeAttribute("style", "width:" + width + "px;height:" + height + "px;", null);
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

	private String generateFunctionBody(List<XYDataPoint> xyList, String id, Boolean showDataPoints, Boolean showLines, String legend, Boolean fillLines, String legendColor, String mode) {
		StringBuilder sb = new StringBuilder();
		sb.append("var d1 = [");
		if (xyList != null) {
			for (int i = 0; i < xyList.size() - 1; i++) {
				XYDataPoint p = xyList.get(i);
				sb.append("[").append(p.getX()).append(",").append(p.getY()).append("]").append(", ");
			}
			// Last Row
			XYDataPoint p = xyList.get(xyList.size() - 1);
			sb.append("[").append(p.getX()).append(",").append(p.getY()).append("]");
		}
		sb.append("];");
		
		String dataArrayString = generateDataOptions(legend, fillLines);
		String chartOptions = generateChartOptions(showDataPoints, showLines, legendColor, mode);
		
		sb.append("\n").append("var f = Flotr.draw($('" + id + "'), [ " + dataArrayString + " ],").append(chartOptions).append(");");
		
		return sb.toString();
	}
	
	private String generateChartOptions(Boolean showDataPoints, Boolean showLines, String legendColor, String mode) {
		String retVal = "";
		try {
			JSONObject chartOptions = new JSONObject();
		
			//Revert to default color if none is selected
			if (legendColor == null) {
				legendColor = "#D2E8FF";
			}
			if (!legendColor.startsWith("#")) {
				legendColor = "#" + legendColor;
			}
			
			JSONObject legendOptions = new JSONObject();
			legendOptions.put("position", "'ne'");
			legendOptions.put("backgroundColor", "'" + legendColor + "'");
			chartOptions.put("legend", legendOptions);
			
			JSONObject mouseOptions = new JSONObject();
			mouseOptions.put("track", true);
			mouseOptions.put("relative", true);
			mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + obj.x +', y = ' + obj.y; }");
			chartOptions.put("mouse", mouseOptions);
			
			
			JSONObject xaxisOptions = new JSONObject();
			
			if (mode.equalsIgnoreCase("Time")) {
				xaxisOptions.put("tickFormatter", "function(n){ return dateFormat(new Date(n*1)); }");
			}
			
			if (xaxisOptions.length() > 0) {
				chartOptions.put("xaxis", xaxisOptions);
			}
			
			JSONObject yaxisOptions = new JSONObject();
			yaxisOptions.put("tickFormatter", "function(n){ return yaxisConverter(n); }");
			chartOptions.put("yaxis", yaxisOptions);
			
			JSONObject lineOptions = new JSONObject();
			lineOptions.put("show", showLines.booleanValue());
			chartOptions.put("lines", lineOptions);
			
			JSONObject pointsOptions = new JSONObject();
			pointsOptions.put("show", showDataPoints.booleanValue());
			chartOptions.put("points", pointsOptions);
			retVal = chartOptions.toString(3).replace("\"", ""); 
		} catch (JSONException je) {
			
		}
		
		return retVal;
	}
	
	private String generateDataOptions(String legend, Boolean fillLines) {
		String retVal = "";
		
		try {
			JSONObject jSonData = new JSONObject();
			jSonData.put("data", "d1");
		
			JSONObject lineJson = new JSONObject();
			lineJson.put("fill", fillLines.booleanValue());
			
			jSonData.put("lines", lineJson);
			if (legend != null) {
				jSonData.put("label", "'" + legend + "'");
			}
			retVal = jSonData.toString(3).replace("\"", "");;
		} catch (JSONException je) {
			
		}
		
		return retVal;
	}
}