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

package org.jsflot.renderkit.phaselistener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inconspicuous.jsmin.JSMin;

public class ResourceLoaderPhaseListener implements PhaseListener {

	public static final String RESOURCE_LOADER_VIEW_ID = "/jsflot/";
	public static final String AJAX_SUFFIX = "Ajax-request";

	private static final String RESOURCE_FOLDER = "/META-INF";

	private static final Logger log = Logger.getLogger(ResourceLoaderPhaseListener.class.getName());

	public void beforePhase(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
			log.info("Processing new  Request!");
		}

		log.info("before - " + event.getPhaseId().toString());
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public void afterPhase(PhaseEvent event) {
		log.info("after - " + event.getPhaseId().toString());

		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			log.info("Done with Request!\n");
		}

		FacesContext facesContext = event.getFacesContext();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		String viewRootId = facesContext.getViewRoot().getViewId();

		if (facesContext.getExternalContext().getRequest() instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		}

		if (viewRootId.startsWith(RESOURCE_LOADER_VIEW_ID)) {
			// If viewRootId starts with RESOURCE_LOADER_VIEW_ID, we are
			// assuming that we are
			// loading resources
			String resourceName = viewRootId.substring(RESOURCE_LOADER_VIEW_ID.length()); // remove
																							// prefix
			while (!fileEndsWithLegalCharacters(resourceName)) { // Process
																	// JavaScript
																	// and CSS
																	// files
				int endPoint = resourceName.lastIndexOf('.');
				if (endPoint == -1) {
					return;
				}
				resourceName = resourceName.substring(0, endPoint);
			}
			// Serve JavaScript file
			serveResource(facesContext, resourceName);
		}
	}

	private boolean fileEndsWithLegalCharacters(String filename) {
		boolean correctEnding = filename.endsWith(".js") || filename.endsWith(".css") || filename.endsWith(".png");
		return correctEnding;
	}

	private void serveResource(FacesContext facesContext, String resourceName) {
		log.info("serverResource(): resourceName: " + resourceName);

		String resourceType = getResourceType(resourceName);
		String contentType = getContentType(resourceType);

		int indice, tempIndice;
		byte tempArr[];
		byte mainArr[] = new byte[0];
		byte byteArr[] = new byte[65535];

		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

		try {
			String resourcePath = RESOURCE_FOLDER + "/" + resourceName;
			InputStream inputStream = ResourceLoaderPhaseListener.class.getResourceAsStream(resourcePath);
			URL url = ResourceLoaderPhaseListener.class.getResource(resourcePath);
			if (url == null) {
				// resource not found
				log.info("URL is NULL: " + resourcePath);
				facesContext.responseComplete();
				return;
			}

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			response.setContentType(contentType);
			response.setStatus(200);
			ServletOutputStream outputStream = response.getOutputStream();
			
			log.info("JSFlotDebug: " + System.getProperties().get("JSFlotDebug"));
			if (resourcePath.endsWith(".js") && 
					!(System.getProperties().get("JSFlotDebug") != null && System.getProperties().get("JSFlotDebug").equals("true"))) {
				//Javascript file
				JSMin jsmin = new JSMin(inputStream, outputStream);
				jsmin.jsmin();
			} else {
				for (indice = 0; (indice = inputStream.read(byteArr)) > 0;) {
					tempIndice = mainArr.length + indice;
					tempArr = new byte[tempIndice];
					System.arraycopy(mainArr, 0, tempArr, 0, mainArr.length);
					System.arraycopy(byteArr, 0, tempArr, mainArr.length, indice);
					mainArr = tempArr;
				}

				outputStream.write(mainArr);
				outputStream.flush();
				outputStream.close();
			}
			//Set response complete after this phase
			facesContext.responseComplete();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static String getResourceName(Map requestMap) {
		log.info("getResourceName()");
		String resourceName = (String) requestMap.get("name");
		return resourceName;
	}

	public static String getResourceType(String resourceName) {
		log.info("getResourceType()");
		String resourceType = resourceName.substring(resourceName.lastIndexOf('.') + 1, resourceName.length());
		return resourceType;
	}

	public static String getContentType(String resourceType) {
		log.info("getContentType()");
		String contentType = null;
		if (resourceType.equals("js"))
			contentType = "text/javascript";
		else if (resourceType.equals("css"))
			contentType = "text/css";
		else if (resourceType.equals("jpg"))
			contentType = "image/jpeg";
		else if (resourceType.equals("gif"))
			contentType = "image/gif";
		else if (resourceType.equals("png"))
			contentType = "image/png";

		return contentType;
	}
}