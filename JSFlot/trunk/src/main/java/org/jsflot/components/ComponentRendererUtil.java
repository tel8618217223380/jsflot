package org.jsflot.components;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;

public class ComponentRendererUtil {
	public static final String CLIENT_ID = "org.jsflot.CLIENT_ID";
	public static final String AJAX_REQUEST = "org.jsflot.AJAX_REQUEST";

	/**
	 * This method is copied from JBoss Richfaces 3.3.0.GA
	 * Find nested form for given component
	 * 
	 * @param component
	 * @return nested <code>UIForm</code> component, or <code>null</code>
	 */
	public static UIComponent getNestingForm(UIComponent component) {
		UIComponent parent = component;
		// Search enclosed UIForm or ADF UIXForm component
		while (parent != null
				&& !(parent instanceof UIForm)
				&& !("org.apache.myfaces.trinidad.Form".equals(parent
						.getFamily()))
				&& !("oracle.adf.Form".equals(parent.getFamily()))) {
			parent = parent.getParent();
		}

		return parent;
	}
}
