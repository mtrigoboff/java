import graph.Graph;

public class Graphs {

	private static void shortestPath(Graph graph, String start, String end) {
		Graph.Path shortestPath = graph.shortestPath(start, end);
		System.out.printf("Shortest path from %s to %s:%n", start, end);
		System.out.println(shortestPath);
	}
	
	public static void main(String[] args) {
		Graph graph = new Graph("graphs/cities.txt");
		
		System.out.println(graph);
		
		shortestPath(graph, "Seattle", "Miami");
		shortestPath(graph, "Boston", "LosAngeles");
	}

}
