package graph;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {

	public static class Path {

		ArrayList<String> vertices = new ArrayList<>();
		double distance;

		public String toString() {
			DecimalFormat df = new DecimalFormat("#,###");
			StringBuilder sb = new StringBuilder();
			boolean first = true;

			for (String vertex : vertices) {
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(vertex);
			}
			sb.append("\n");
			sb.append(df.format(distance));
			sb.append(" miles\n");
			return sb.toString();
		}
	}

	private HashMap<String, HashMap<String, Double>> edges = new HashMap<>();

	public Graph(String filePath) {
		try {
			File citiesFile = new File(filePath);
			Scanner scanner = new Scanner(citiesFile);
			scanner.useDelimiter("[ \n]");
			while (scanner.hasNext()) {
				String startCity = scanner.next();
				double cost = scanner.nextDouble();
				String endCity = scanner.next();
				addEdge(startCity, cost, endCity);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	public final void addEdge(String start, double cost, String end) {
		if (!edges.containsKey(start)) {
			edges.put(start, new HashMap<>());
		}
		edges.get(start).put(end, cost);
	}

	public Path shortestPath(String start, String end) {	// Dijkstra's Algorithm

		class VertexInfo {

			double distFromStart = Double.POSITIVE_INFINITY;
			boolean visited = false;
			String prevVertex = null;
		}

		HashMap<String, VertexInfo> vertexInfo = new HashMap<>();
		for (String vertex : edges.keySet()) {
			vertexInfo.put(vertex, new VertexInfo());
		}

		VertexInfo startInfo = vertexInfo.get(start);
		startInfo.distFromStart = 0;
		startInfo.visited = true;
		ArrayList<String> unvisited = new ArrayList<>();
		unvisited.add(start);
		while (!unvisited.isEmpty()) {
			String current = unvisited.remove(0);
			VertexInfo currentInfo = vertexInfo.get(current);
			HashMap<String, Double> neighbors = edges.get(current);
			for (Map.Entry neighborEntry : neighbors.entrySet()) {
				String neighbor = (String) neighborEntry.getKey();
				double neighborDist = (double) neighborEntry.getValue();
				VertexInfo neighborInfo = vertexInfo.get(neighbor);
				if (!neighborInfo.visited) {
					double neighborNewDistFromStart = currentInfo.distFromStart + neighborDist;
					if (neighborNewDistFromStart < neighborInfo.distFromStart) {
						neighborInfo.distFromStart = neighborNewDistFromStart;
						neighborInfo.prevVertex = current;
					}
					unvisited.add(neighbor);
				}
			}
			currentInfo.visited = true;
		}

		Path path = new Path();

		String vertex = end;
		while (!vertex.equals(start)) {
			path.vertices.add(0, vertex);
			vertex = vertexInfo.get(vertex).prevVertex;
		}
		path.vertices.add(0, start);
		path.distance = vertexInfo.get(end).distFromStart;

		return path;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry start : edges.entrySet()) {
			for (Map.Entry edge : ((HashMap<String, Double>) start.getValue()).entrySet()) {
				sb.append(start.getKey());
				sb.append(" ");
				sb.append(String.format("%.0f", edge.getValue()));
				sb.append(" ");
				sb.append(edge.getKey());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
