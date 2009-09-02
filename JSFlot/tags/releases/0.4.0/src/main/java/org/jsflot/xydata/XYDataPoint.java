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

package org.jsflot.xydata;

public class XYDataPoint {
	private Number x;
	private Number y;
	private String pointLabel;
	
	public XYDataPoint() {
		// TODO Auto-generated constructor stub
	}
	
	public XYDataPoint(Number x, Number y) {
		super();
		this.x = x;
		this.y = y;
		this.pointLabel = null;
	}
	
	public XYDataPoint(Number x, Number y, String pointLabel) {
		super();
		this.x = x;
		this.y = y;
		this.pointLabel = pointLabel;
	}
	
	public Number getX() {
		return x;
	}
	
	public void setX(Number x) {
		this.x = x;
	}
	
	public Number getY() {
		return y;
	}
	
	public void setY(Number y) {
		this.y = y;
	}
	
	public String getPointLabel() {
		return pointLabel;
	}
	
	public void setPointLabel(String pointLabel) {
		this.pointLabel = pointLabel;
	}

}
