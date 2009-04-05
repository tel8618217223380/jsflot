package org.jsflot.components;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class FlotChartComponent extends UIOutput {

	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return getClass().getName();
	}
	
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void processDecodes(FacesContext context) {
		// TODO Auto-generated method stub
		Iterator<UIComponent> kids = getFacetsAndChildren();
		while (kids.hasNext()) {
			kids.next().processDecodes(context);
		}
		
		try {
			decode(context);
		} catch (RuntimeException re) {
			context.renderResponse();
			throw re;
		}
	}
}
