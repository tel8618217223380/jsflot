package org.jsflot.components;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

public class ResourcesTag extends UIComponentELTag {

	private ValueExpression excludes;
	private ValueExpression debug;

	@Override
	public String getComponentType() {
		return "org.jsflot.components.ResourcesComponent";
	}

	@Override
	public String getRendererType() {
		return "org.jsflot.components.ResourcesRenderer";
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
	
		component.setValueExpression("excludes", this.excludes);
		component.setValueExpression("debug", this.debug);
	}
	
	public void release() {
		super.release();
		
		this.excludes = null;
		this.debug = null;
	}
}

