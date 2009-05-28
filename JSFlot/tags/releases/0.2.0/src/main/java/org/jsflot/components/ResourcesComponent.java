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
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class ResourcesComponent extends UIOutput {

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
