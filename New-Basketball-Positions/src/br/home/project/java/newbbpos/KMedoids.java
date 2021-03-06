package br.home.project.java.newbbpos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class KMedoids {
	
	private static ArrayList<Player> database = new ArrayList<>();
	
	private String path;
	private int numberOfClusters;
	private int[] centroids;
	private double[][] similarityMatrix;
	Cluster[] clusters;
	
	public KMedoids(String path, int numberOfClusters) {
		this.setPath(path);
		this.setNumberOfClusters(numberOfClusters);
		
		this.clusters = new Cluster[numberOfClusters];
		for (int i = 0; i < this.numberOfClusters; i++) {
			this.clusters[i] = new Cluster();
		}
	}
	
	public void loadDatabase() {
		
		String separator = ",";
		String result = "";
		BufferedReader content = null;
		
		try {
			
			content = new BufferedReader(new FileReader(this.path));			
			while ((result = content.readLine()) != null) {
				
				Player player = new Player();
				String[] features = result.split(separator);
				
				try {
					// get the player's name
					player.setName(features[1]);
					// get the player's position
					String auxPosition = features[2];
					if (auxPosition.equals("PG")) {
						player.setPosition(1.0);
					} else if (auxPosition.equals("SG")) {
						player.setPosition(2.0);
					} else if (auxPosition.equals("SF")) {
						player.setPosition(3.0);
					} else if (auxPosition.equals("PF")) {
						player.setPosition(4.0);
					} else {
						player.setPosition(5.0);
					}
					//get the player's gamesStarted
					player.setGamesStarted(Double.parseDouble(features[6]));
					//get the player's minutesPlayed
					player.setMinutesPlayed(Double.parseDouble(features[7]));
					//get the player's 2PTFieldGoalPercentage
					player.setTwoPointFG(Double.parseDouble("0".concat(features[16])));
					//get the player's 3PTFieldGoalPercentage
					player.setThreePointFG(Double.parseDouble("0".concat(features[13])));
					//get the player's points
					player.setPoints(Double.parseDouble(features[29]));
					//get the player's ofensiveRebounds
					player.setOffRebounds(Double.parseDouble(features[21]));
					//get the player's defensiveRebounds
					player.setDefRebounds(Double.parseDouble(features[22]));
					//get the player's assists
					player.setAssists(Double.parseDouble(features[24]));
					//get the player's blocks
					player.setBlocks(Double.parseDouble(features[26]));
					//get the player's steals
					player.setSteals(Double.parseDouble(features[25]));
					
				} catch (NumberFormatException e) {
					System.out.println("NumberFormatException!!!\n" + e.getMessage());
				}
				KMedoids.database.add(player);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!!!\n" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException!!!\n" + e.getMessage());
		} finally {
			if (content != null) {
				try {
					content.close();
				} catch (IOException e2) {
					System.out.println("Closing File Error!!!\n" + e2.getMessage());
				}
			}
		}
		
		normalizeDatabase();
		
	}
	
	private void normalizeDatabase() {
		// TODO Auto-generated method stub
		
	}

	public void fit() {
		
		this.centroids = this.generateCentroids();
		this.similarityMatrix = this.createSimilarityMatrix();
		
		boolean centroidsChanged = true;
		while (centroidsChanged) {
			centroidsChanged = false;
			
			// Allocating each object to a determine cluster.
			for (int i = 0; i < KMedoids.database.size(); i++) {
				int lowerDistanceIndex = 0;
				double lowerDistance = this.similarityMatrix[i][0];
				
				for (int j = 0; j < this.numberOfClusters; j++) {
					
					if (this.similarityMatrix[i][j] < lowerDistance) {
						lowerDistance = this.similarityMatrix[i][j];
						lowerDistanceIndex = j;
					}
					
				}
				
				this.clusters[lowerDistanceIndex].addObject(i);
			}
			
			// Generating new centroids for each cluster
			for (int i = 0; i < this.numberOfClusters; i++) {
				int indexNewCentroid = generateNewCentroid(this.clusters[i]);
				this.clusters[i].setCentroidIndex(indexNewCentroid);
			}
			
			// Verifying if any centroid has changed
			int[] newCentroids = new int[this.numberOfClusters];
			for (int i = 0; i < this.numberOfClusters; i++) {
				newCentroids[i] = this.clusters[i].getCentroidIndex();
			}
			
			for (int i = 0; i < this.centroids.length; i++) {
				if (newCentroids[i] != this.centroids[i]) {
					centroidsChanged = true;
				}
			}
			
			if (centroidsChanged) { // If yes, set the new centroids and create a new similarity matrix
				for (int i = 0; i < this.numberOfClusters; i++) {
					this.centroids[i] = newCentroids[i];
				}
				this.similarityMatrix = this.createSimilarityMatrix();
				for (int i = 0; i < this.clusters.length; i++) {
					this.clusters[i].clear();
				}
			}			
		}
		
	}
	
	public void generateReport(String filename) {
		
		String path = "reports\\".concat(filename);		
		BufferedWriter buffwriter;
		try {
			buffwriter = new BufferedWriter(new FileWriter(path));
			String content = "";
			
			for (int i = 0; i < this.numberOfClusters; i++) {
				content += "CLUSTER " + (i + 1) + "\n\n";
				for (int j = 0; j < this.clusters[i].clusterSize(); j++) {
					content += KMedoids.getDatabase(this.clusters[i].getObjectsIndexes(j)).getName() + "\n";
				}
				content += "\n";
			}
			
			buffwriter.append(content);
			buffwriter.close();
		} catch (IOException e) {
			System.out.println("IOException!!!\n" + e.getMessage());
		}		
		
	}

	private int generateNewCentroid(Cluster cluster) {
		
		double[] averageDistance = new double[cluster.clusterSize()];
		double sumOfDistances = 0;
		double distance = 0;		
		
		for (int i = 0; i < cluster.clusterSize(); i++) {
			for (int j = 0; j < cluster.clusterSize(); j++) {
				distance = this.euclideanDistance(KMedoids.database.get(cluster.getObjectsIndexes(i)), 
						                          KMedoids.database.get(cluster.getObjectsIndexes(j)));
				sumOfDistances += distance;
			}
			
			averageDistance[i] = sumOfDistances / cluster.clusterSize();
			distance = 0;
			sumOfDistances = 0;
		}
		
		double lowerDistance = averageDistance[0];
		int lowerDistanceIndex = cluster.getObjectsIndexes(0);
		for (int i = 0; i < averageDistance.length; i++) {
			if (averageDistance[i] < lowerDistance) {
				lowerDistance = averageDistance[i];
				lowerDistanceIndex = cluster.getObjectsIndexes(i);
			}
		}
		
		return lowerDistanceIndex;
	}

	private double[][] createSimilarityMatrix() {
		
		double[][] similarityMatrix = new double[KMedoids.database.size()][this.numberOfClusters];
		
		for (int i = 0; i < KMedoids.database.size(); i++) {
			for (int j = 0; j < this.numberOfClusters; j++) {
				
				if (i == j) {
					similarityMatrix[i][j] = 0;
				} else {
					double distance = euclideanDistance(KMedoids.database.get(i), KMedoids.database.get(this.centroids[j]));
					similarityMatrix[i][j] = distance;
				}
				
			}
		}
		
		return similarityMatrix;
	}

	private double euclideanDistance(Player player, Player centroid) {
		return Math.sqrt((Math.pow((player.getPoints() - centroid.getPoints()), 2) + 
				          Math.pow((player.getAssists() - centroid.getAssists()), 2) + 
				          Math.pow((player.getBlocks() - centroid.getBlocks()), 2) + 
				          Math.pow((player.getSteals() - centroid.getSteals()), 2) + 
				          Math.pow((player.getPosition() - centroid.getPosition()), 2) + 
				          Math.pow((player.getGamesStarted() - centroid.getGamesStarted()), 2) + 
				          Math.pow((player.getMinutesPlayed() - centroid.getMinutesPlayed()), 2) + 
				          Math.pow((player.getTwoPointFG() - centroid.getTwoPointFG()), 2) + 
				          Math.pow((player.getThreePointFG() - centroid.getThreePointFG()), 2) + 
				          Math.pow((player.getOffRebounds() - centroid.getOffRebounds()), 2) + 
				          Math.pow((player.getDefRebounds() - centroid.getDefRebounds()), 2)));
	}

	private int[] generateCentroids() {
		
		int[] centroids = new int[this.numberOfClusters];		
		Random random = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < this.numberOfClusters; i++) {
			int aux = random.nextInt(KMedoids.database.size());
			boolean verified = true;
			
			if (i > 0) {
				for (int j = 0; j < i; j++) {
					if (aux == centroids[j]) {
						verified = false;
					}
				}
			}
			
			if (verified == true) {
				centroids[i] = aux;
				this.clusters[i].setCentroidIndex(aux);
			} else {
				i--;
			}
		}
		
		return centroids;
	}

	public int getNumberOfClusters() {
		return numberOfClusters;
	}

	public void setNumberOfClusters(int numberOfClusters) {
		this.numberOfClusters = numberOfClusters;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int[] getCentroids() {
		return centroids;
	}

	public void setCentroids(int[] centroids) {
		this.centroids = centroids;
	}

	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	public void setSimilarityMatrix(double[][] similarityMatrix) {
		this.similarityMatrix = similarityMatrix;
	}

	public Cluster getClusters(int index) {
		return this.clusters[index];
	}

	public static Player getDatabase(int index) {
		return KMedoids.database.get(index);
	}

}
