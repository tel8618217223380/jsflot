package org.jsflot.components;

import javax.faces.context.FacesContext;

public interface JSFlotAjaxInterface {
	public void handleAjaxRequest(FacesContext context, JSFlotAjaxInterface component);
	public void handleAjaxDecode(FacesContext context, JSFlotAjaxInterface component);
}
