package org.jsflot.components;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

public class ResourcesRenderer extends Renderer {
	private static final String RENDERED_SCRIPT_KEY = "JSFLOW_SCRIPT_KEY";

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		boolean renderPrototype = true;
		boolean renderBase64 = true;
		boolean renderExcanvas = true;
		boolean renderCanvas2Image = true;
		boolean renderCanvastext = true;
		boolean renderFlotr = true;

		String excludes = (String) get(component, context, "excludes");

		if (excludes != null) {
			renderPrototype = !excludes.contains("prototype");
			renderBase64 = !excludes.contains("base64");
			renderExcanvas = !excludes.contains("excanvas");
			renderCanvas2Image = !excludes.contains("canvas2image");
			renderCanvastext = !excludes.contains("canvastext");
			renderFlotr = !excludes.contains("flotr");
		}

		renderScriptOnce(context, component, writer, renderPrototype, renderBase64, renderExcanvas, renderCanvas2Image, renderCanvastext, renderFlotr);
	}

	private void renderScriptOnce(FacesContext context, UIComponent component, ResponseWriter writer, 
			boolean renderPrototype, boolean renderBase64, boolean renderExcanvas, boolean renderCanvas2Image, 
			boolean renderCanvastext, boolean renderFlotr) throws IOException {

		Map requestMap = context.getExternalContext().getRequestMap();
		Boolean scriptRendered = (Boolean) requestMap.get(RENDERED_SCRIPT_KEY);

		if (scriptRendered != null && scriptRendered.equals(Boolean.TRUE)) {
			return;
		}
		requestMap.put(RENDERED_SCRIPT_KEY, Boolean.TRUE);
		String contextRoot = context.getExternalContext().getRequestContextPath();

		if (renderPrototype) {
			writer.startElement("script", component);
				writer.writeAttribute("type", "text/javascript", null);
				writer.writeAttribute("src", contextRoot + "/jsflot/prototype-1.6.0.2.js.jsf", null); 
			writer.endElement("script"); 
			writer.write("\n");
		}
		
		if (renderBase64) {
			writer.write("<!--[if IE]><script language=\"javascript\" type=\"text/javascript\" src=\"" + contextRoot
					+ "/jsflot/base64.js.jsf\"></script><![endif]-->");
		}
		if (renderExcanvas) {
			writer.write("<!--[if IE]><script language=\"javascript\" type=\"text/javascript\" src=\"" + contextRoot
				+ "/jsflot/excanvas.js.jsf\"></script><![endif]-->");
		}

		if (renderCanvas2Image) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + "/jsflot/canvas2image.js.jsf", null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		if (renderCanvastext) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + "/jsflot/canvastext.js.jsf", null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		if (renderFlotr) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + "/jsflot/flotr.js.jsf", null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", contextRoot + "/jsflot/dateformat.js.jsf", null);
		writer.endElement("script");
		writer.write("\n");
	}

	private Object get(UIComponent component, FacesContext context, String name) {
		ValueBinding binding = component.getValueBinding(name);
		if (binding != null)
			return binding.getValue(context);
		else
			return component.getAttributes().get(name);
	}
}
