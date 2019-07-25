package br.home.project.java.newbbpos;

public class Main {

	public static void main(String[] args) {
		
		KMedoids clusters = new KMedoids("dataset18-19.csv", 13);
		
		clusters.loadDatabase();
		clusters.fit();
		//clusters.generateReport();
		
		for (int i = 0; i < clusters.getNumberOfClusters(); i++) {
			System.out.println("CLUSTER " + (i + 1) + "\n");
			for (int j = 0; j < clusters.getClusters(i).clusterSize(); j++) {
				System.out.println(KMedoids.getDatabase(clusters.getClusters(i).getObjectsIndexes(j)).getName());
			}
			System.out.println();
		}
		
	}

}
