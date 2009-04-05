package org.jsflot.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestJSONObject {

	public static void main(String[] args) {
		TestJSONObject t = new TestJSONObject();
		t.testJson();
	}
	
	public void testJson() {
		try {
			JSONObject jSonData = new JSONObject();
			jSonData.put("data", "d1");
		
			JSONObject lineJson = new JSONObject();
			lineJson.put("fill", "true");
			
			jSonData.put("lines", lineJson);
			jSonData.put("label", "LegendString");
			
			//System.out.println(jSonData.toString(1));
			
			
			JSONObject chartOptions = new JSONObject();
			
			JSONObject legendOptions = new JSONObject();
			legendOptions.put("position", "ne");
			legendOptions.put("backgroundColor", "#D2E8FF");
			chartOptions.put("legend", legendOptions);
			
			JSONObject mouseOptions = new JSONObject();
			mouseOptions.put("track", new Boolean(true));
			mouseOptions.put("color", "purple");
			mouseOptions.put("sensibility", 1);
			mouseOptions.put("trackDecimals", 2);
			mouseOptions.put("trackFormatter", "function(obj){ return 'x = ' + obj.x +', y = ' + obj.y; }");
			chartOptions.put("mouse", mouseOptions);
			
			JSONObject xaxisOptions = new JSONObject();
			xaxisOptions.put("tickFormatter", "function(n){ return dateFormat(new Date(n*1)); }");
			chartOptions.put("xaxis", xaxisOptions);
			
			JSONObject yaxisOptions = new JSONObject();
			yaxisOptions.put("tickFormatter", "function(n){ return yaxisConverter(n); }");
			chartOptions.put("yaxis", yaxisOptions);
			
			JSONObject lineOptions = new JSONObject();
			lineOptions.put("show", true);
			chartOptions.put("lines", lineOptions);
			
			JSONObject pointsOptions = new JSONObject();
			pointsOptions.put("show", true);
			chartOptions.put("points", pointsOptions);
			
			System.out.println(chartOptions.toString(3));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//{data:d1, lines:{fill: true }
		//{ legend:{ position: 'ne', backgroundColor: '#D2E8FF' }, mouse:{ track: true, color: 'purple', sensibility: 1, trackDecimals: 2, trackFormatter: function(obj){ return 'x = ' + obj.x +', y = ' + obj.y; } }
		//, xaxis: { tickFormatter: function(n){ return dateFormat(new Date(n*1)); } }, yaxis: { tickFormatter: function(n){ return yaxisConverter(n); } }
		//,  lines: { show: ").append(showLines.booleanValue()).append(" }, points: { show: ").append(showDataPoints.booleanValue()).append(" } }
	}
}
