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
	
	public ValueExpression getExcludes() {
		return excludes;
	}

	public void setExcludes(ValueExpression excludes) {
		this.excludes = excludes;
	}

	public ValueExpression getDebug() {
		return debug;
	}

	public void setDebug(ValueExpression debug) {
		this.debug = debug;
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

