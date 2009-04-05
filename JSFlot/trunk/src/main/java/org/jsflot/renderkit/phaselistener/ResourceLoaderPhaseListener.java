package org.jsflot.renderkit.phaselistener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class ResourceLoaderPhaseListener implements PhaseListener {

	private static final String RESOURCE_LOADER_VIEW_ID = "/jsflot/";

	private static final String RESOURCE_FOLDER = "/META-INF";
	
	private static final Logger log = Logger.getLogger(ResourceLoaderPhaseListener.class.getName());

	public void beforePhase(PhaseEvent event) {
		// No-op
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public void afterPhase(PhaseEvent event) {
		//log.info("afterPhase()");
		FacesContext facesContext = event.getFacesContext();
		String viewRootId = facesContext.getViewRoot().getViewId();
		log.info("viewRootId: " + viewRootId);
		if (viewRootId.startsWith("/jsflot/") && viewRootId.endsWith(".jsf")) {
			int endPoint = viewRootId.length() -4;
			String resourceName = viewRootId.substring("/jsflot/".length(), endPoint);
			serveResource(facesContext, resourceName);
		}		
	}

	private void serveResource(FacesContext facesContext, String resourceName) {
		log.info("serverResource(): resourceName: " + resourceName);
		Map requestMap = facesContext.getExternalContext().getRequestParameterMap();

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
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			response.setContentType(contentType);
			response.setStatus(200);
			ServletOutputStream outputStream = response.getOutputStream();

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

		return contentType;
	}
}