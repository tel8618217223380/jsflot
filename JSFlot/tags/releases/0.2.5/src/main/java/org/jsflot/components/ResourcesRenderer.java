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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpSession;

public class ResourcesRenderer extends Renderer {
	private static final String RENDERED_SCRIPT_KEY = "JSFLOT_SCRIPT_KEY";

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		boolean renderPrototype = true;
		boolean renderBase64 = true;
		boolean renderExcanvas = true;
		boolean renderCanvas2Image = true;
		boolean renderCanvastext = true;
		boolean renderFlotr = true;
		boolean renderBlackbird = false;
		boolean renderBlackbirdCss = false;

		String excludes = (String) get(component, context, "excludes");
		String debug = (String) get(component, context, "debug");

		if (excludes != null) {
			renderPrototype = !excludes.contains("prototype");
			renderBase64 = !excludes.contains("base64");
			renderExcanvas = !excludes.contains("excanvas");
			renderCanvas2Image = !excludes.contains("canvas2image");
			renderCanvastext = !excludes.contains("canvastext");
			renderFlotr = !excludes.contains("flotr");
		}
		
		if (debug != null && debug.equalsIgnoreCase("true")) {
			renderBlackbird = true;
			renderBlackbirdCss = true;
			System.getProperties().put("JSFlotDebug", "true");
		}

		renderScriptOnce(context, component, writer, renderPrototype, renderBase64, renderExcanvas, renderCanvas2Image, renderCanvastext, renderFlotr, renderBlackbird, renderBlackbirdCss);
	}

	private void renderScriptOnce(FacesContext context, UIComponent component, ResponseWriter writer, 
			boolean renderPrototype, boolean renderBase64, boolean renderExcanvas, boolean renderCanvas2Image, 
			boolean renderCanvastext, boolean renderFlotr, boolean renderBlackbird, boolean renderBlackbirdCss) throws IOException {

		Map requestMap = context.getExternalContext().getRequestMap();
		Boolean scriptRendered = (Boolean) requestMap.get(RENDERED_SCRIPT_KEY);

		if (scriptRendered != null && scriptRendered.equals(Boolean.TRUE)) {
			//Return if script is already rendered in this request
			return;
		}
		requestMap.put(RENDERED_SCRIPT_KEY, Boolean.TRUE);
		String contextRoot = context.getExternalContext().getRequestContextPath();
		String servletPath = context.getExternalContext().getRequestServletPath();
		
		
		//Assume suffix *.jsf
		String facesSuffix = ".jsf";
		String facesPrefix = "";
		
		if (context.getExternalContext().getRequestPathInfo() == null) {
			//Faces Servlet has a suffix
			String pathInfo = context.getExternalContext().getRequestServletPath();
			int lastPeriod = pathInfo.lastIndexOf(".");
			
			facesSuffix = pathInfo.substring(lastPeriod);
			facesPrefix = "";
		} else {
			//Faces Servlet has a prefix
			facesPrefix = servletPath;
			facesSuffix = "";
		}
		
		((HttpSession)context.getExternalContext().getSession(true)).setAttribute("facesPrefix", facesPrefix);
		((HttpSession)context.getExternalContext().getSession(true)).setAttribute("facesSuffix", facesSuffix);

		if (renderPrototype) {
			writer.startElement("script", component);
				writer.writeAttribute("type", "text/javascript", null);
				writer.writeAttribute("src", contextRoot + facesPrefix + "/jsflot/prototype-1.6.0.2.js" + facesSuffix, null); 
			writer.endElement("script"); 
			writer.write("\n");
		}
		
		if (renderBase64) {
			writer.write("<!--[if IE]><script language=\"javascript\" type=\"text/javascript\" src=\"" + contextRoot
					+ "/jsflot/base64.js" + facesSuffix + facesPrefix + "\"></script><![endif]-->");
		}
		if (renderExcanvas) {
			writer.write("<!--[if IE]><script language=\"javascript\" type=\"text/javascript\" src=\"" + contextRoot
				+ "/jsflot/excanvas.js" + facesSuffix + facesPrefix + "\"></script><![endif]-->");
		}

		if (renderCanvas2Image) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + facesPrefix + "/jsflot/canvas2image.js" + facesSuffix, null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		if (renderCanvastext) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + facesPrefix + "/jsflot/canvastext.js" + facesSuffix, null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		if (renderFlotr) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + facesPrefix + "/jsflot/flotr.js" + facesSuffix, null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", contextRoot + facesPrefix +"/jsflot/dateformat.js" + facesSuffix, null);
		writer.endElement("script");
		writer.write("\n");
		
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", contextRoot + facesPrefix +"/jsflot/jsflotAjaxscript.js" + facesSuffix, null);
		writer.endElement("script");
		writer.write("\n");
		
		if (renderBlackbird) {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + facesPrefix +"/jsflot/blackbird.js" + facesSuffix, null);
			writer.endElement("script");
			writer.write("\n");
		} else {
			writer.startElement("script", component);
			writer.writeAttribute("type", "text/javascript", null);
			writer.writeAttribute("src", contextRoot + facesPrefix +"/jsflot/logskeleton.js" + facesSuffix, null);
			writer.endElement("script");
			writer.write("\n");
		}
		
		if (renderBlackbirdCss) {
			writer.startElement("link", component);
			writer.writeAttribute("type", "text/css", null);
			writer.writeAttribute("rel", "Stylesheet", null);
			writer.writeAttribute("href",  contextRoot + facesPrefix +"/jsflot/blackbird.css" + facesSuffix, null);
			writer.endElement("link");
		}
	}

	private Object get(UIComponent component, FacesContext context, String name) {
		ValueBinding binding = component.getValueBinding(name);
		if (binding != null)
			return binding.getValue(context);
		else
			return component.getAttributes().get(name);
	}
}
