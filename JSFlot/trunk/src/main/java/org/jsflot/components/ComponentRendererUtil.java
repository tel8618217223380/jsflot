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
