package org.jsflot.xydata;

import java.util.ArrayList;
import java.util.List;

public class XYDataSetCollection {
	private List<XYDataList> dataCollection;
	
	public XYDataSetCollection() {
		dataCollection = new ArrayList<XYDataList>();
	}
	
	public boolean addDataList(XYDataList list) {
		return dataCollection.add(list);
	}
	
	public boolean removeDataList(XYDataList list) {
		return dataCollection.remove(list);
	}
	
	public XYDataList removeDataList(int index) {
		return dataCollection.remove(index);
	}
	
	public List<XYDataList> getDataList() {
		return dataCollection;
	}
	
	public XYDataList get(int index) {
		return dataCollection.get(index);
	}
	
	public int size() {
		return dataCollection.size();
	}
}
