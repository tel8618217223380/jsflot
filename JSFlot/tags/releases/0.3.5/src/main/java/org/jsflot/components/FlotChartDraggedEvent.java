package org.jsflot.components;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

public class FlotChartDraggedEvent extends ActionEvent {
	private Double dragValue;

	public FlotChartDraggedEvent(UIComponent component, Double dragValue) {
		super(component);
		this.dragValue = dragValue;
	}

	public Double getDragValue() {
		return dragValue;
	}
	
	@Override
	public void processListener(FacesListener listener) {
		// TODO Auto-generated method stub
		super.processListener(listener);
	}
	

}
