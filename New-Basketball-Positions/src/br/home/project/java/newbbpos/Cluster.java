package br.home.project.java.newbbpos;

import java.util.ArrayList;

public class Cluster {
	
	private ArrayList<Integer> objectsIndexes = new ArrayList<>();
	private int centroidIndex;
	
	public Cluster() {
		
	}
	
	public int clusterSize() {
		return this.objectsIndexes.size();
	}
	
	public int getObjectsIndexes(int index) {
		return this.objectsIndexes.get(index);
	}

	public void addObject(int index) {
		this.objectsIndexes.add(index);
	}
	
	public void clear() {
		this.objectsIndexes.clear();
	}
	
	public int getCentroidIndex() {
		return centroidIndex;
	}
	
	public void setCentroidIndex(int centroidIndex) {
		this.centroidIndex = centroidIndex;
	}

	@Override
	public String toString() {
		return "Cluster [objectsIndexes=" + objectsIndexes + ", centroidIndex=" + centroidIndex + "]";
	}

}
