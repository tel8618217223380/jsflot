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

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ComponentRendererUtil {
	public static final String CLIENT_ID = "org.jsflot.CLIENT_ID";
	public static final String AJAX_REQUEST = "org.jsflot.AJAX_REQUEST";
	public static final String DEBUGVAR = "JSFlotDebug";

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
	
	/**
	 * findComponent method is taken from http://illegalargumentexception.googlecode.com/svn/trunk/code/java/JsfClientId/src/demo/faces/ClientIdUtils.java
	 * 
			Copyright (c) 2009 McDowell
			
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
	public static UIComponent findComponent(UIComponent component, String id) {
        //If either component of id is null, the component cannot be found
		if (component == null || id == null) {
        	return null;
        }
        
        if (id.equals(component.getId())) {
            return component;
        }

        Iterator<UIComponent> kids = component.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = kids.next();
            UIComponent found = findComponent(kid, id);
            if (found != null) {
                return found;
            }
        }

        return null;
    }
	
	public static String getFacesPrefix(FacesContext context) {
		String facesPrefix = (String) ((HttpSession) context.getExternalContext().getSession(true)).getAttribute("facesPrefix");
		if (facesPrefix == null) {
			facesPrefix = "";
		}
		
		return facesPrefix;
	}
	
	public static String getFacesSuffix(FacesContext context) {
		String facesSuffix = (String) ((HttpSession) context.getExternalContext().getSession(true)).getAttribute("facesSuffix");
		if (facesSuffix == null) {
			facesSuffix = "";
		}
		
		return facesSuffix;
	}
	
	public static void setDebug(boolean debug) {
		if (debug) {
			System.getProperties().put(DEBUGVAR, "true");
		} else {
			System.getProperties().put(DEBUGVAR, "false");
		}
	}
	
	public static boolean getDebug() {
		String debug = System.getProperties().getProperty(DEBUGVAR);
		return debug != null && debug.equalsIgnoreCase("true");
	}
	
}
