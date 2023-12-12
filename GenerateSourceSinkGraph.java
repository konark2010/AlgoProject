import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// main class for graph generation
public class GenerateSourceSinkGraph {

    // Definition of the Edge class
    public static class Edge {
        int u;
        int v;
        int capacity;

        // Constructor for the Edge class
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
            this.capacity = 0; // Initialize capacity to 0
        }

        // Overrides equals and hashCode for proper set operations
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge edge = (Edge) obj;
            return (u == edge.u && v == edge.v) || (u == edge.v && v == edge.u);
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(u) + Integer.hashCode(v);
        }
    }

    // Set to store edges
    public static Set<Edge> E;

    // method that generates the source-sink graph
    public static void sourceSinkGraphGenerator(int n, double r, int upperCap, String filePath) {
        Set<Integer> V = new HashSet<>();
        for (int i = 0; i < n; i++) {
            V.add(i);
        }

        // Assign random (x, y) coordinates to each vertex
        double[][] coordinates = new double[n][2];
        Random random = new Random();
        for (int u : V) {
            coordinates[u][0] = random.nextDouble();
            coordinates[u][1] = random.nextDouble();
        }

        // Randomly assigns edges of length ≤ r without creating parallel edges
        E = new HashSet<>();
        for (int u : V) {
            for (int v : V) {
                if (u != v && (Math.pow(coordinates[u][0] - coordinates[v][0], 2)
                        + Math.pow(coordinates[u][1] - coordinates[v][1], 2) <= Math.pow(r, 2))) {
                    double rand = random.nextDouble();
                    if (rand < 0.5) {
                        // Add edge if not already present
                        edgeAdder(u, v);
                    } else {
                        // Add edge if not already present
                        edgeAdder(v, u);
                    }
                }
            }
        }

        // Assigns randomly selected integer-value capacity in the range [1...upperCap] for each edge
        int[][] capacities = new int[n][n];
        for (Edge edge : E) {
            capacities[edge.u][edge.v] = random.nextInt(upperCap) + 1;
        }

        // finds the source node randomly from all the nodes
        int sourceNode = new Random().nextInt(n);

        // finds the sink node from the found source node which is the farthest from it on an acyclic path
        int sinkNode = getSinkNode(n, E, sourceNode);

        System.out.println("Source Node: " + sourceNode + " Sink Node: " + sinkNode);

        writeGraphToCSV(filePath, n, coordinates, capacities, sourceNode, sinkNode, r, upperCap);
    }

    // stores the graph and associated edge capacities in an ASCII-readable format
    //the first line stores number_of_nodes,number_of_edges
    //then stores each edges(u,v)'s x and y coordinates for the vertex u and vertex v and its capacity
    //from_vertex_number, x coordinate of vertex, y coordinate of vertex, to_vertex_number, x coordinate of vertex, y coordinate of vertex, capacity of edge
    //the second last line stores source node number, sink node number
    //the last line of the csv stores n, r, maxCap
    private static void writeGraphToCSV(String filePath, int n, double[][] coordinates, int[][] capacities,
                                        int sourceNode, int sinkNode, double r, int upperCap) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(n + "," + E.size() + "\n");
            for (Edge edge : E) {
                writer.write(edge.u + "," + coordinates[edge.u][0] + "," + coordinates[edge.u][1] + "," + edge.v + "," +
                        coordinates[edge.v][0] + "," + coordinates[edge.v][1] + "," + capacities[edge.u][edge.v] + "\n");
            }
            writer.write(sourceNode + "," + sinkNode + "\n");
            writer.write(n + "," + r + "," + upperCap + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adds an edge to the set if not already present
    private static void edgeAdder(int u, int v) {
        Edge newEdge = new Edge(u, v);
        if (!E.contains(newEdge) && !E.contains(new Edge(v, u))) {
            E.add(newEdge);
        }
    }

    // Finds the node with the maximum distance (sink node) using topological sorting
    public static int getSinkNode(int n, Set<Edge> E, int source) {
        @SuppressWarnings("unchecked")
        List<Integer>[] adjacencyList = new List[n];

        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (Edge edge : E) {
            adjacencyList[edge.u].add(edge.v);
        }

        List<Integer> topologicalOrder = topologicalSorter(adjacencyList);

        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MIN_VALUE);
        distance[source] = 0;

        for (int u : topologicalOrder) {
            for (int v : adjacencyList[u]) {
                if (distance[v] < distance[u] + 1) {
                    distance[v] = distance[u] + 1;
                }
            }
        }

        // Find the node with the maximum distance (sink node)
        int sinkNode = source;
        for (int i = 0; i < n; i++) {
            if (distance[i] > distance[sinkNode]) {
                sinkNode = i;
            }
        }

        return sinkNode;
    }

    // Performs topological sorting of the graph
    private static List<Integer> topologicalSorter(List<Integer>[] adjacencyList) {
        int n = adjacencyList.length;
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                topologicalSortHelper(adjacencyList, i, visited, stack);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }

    // Utility function for topological sorting
    private static void topologicalSortHelper(List<Integer>[] adjacencyList, int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (int neighbor : adjacencyList[v]) {
            if (!visited[neighbor]) {
                topologicalSortHelper(adjacencyList, neighbor, visited, stack);
            }
        }

        stack.push(v);
    }

    // Main method to test the graph generation
    public static void main(String[] args) {
        int n = 300;
        float r = 0.4F;
        int upperCap = 100;
        sourceSinkGraphGenerator(n, r, upperCap, "SourceSinkGraph10.csv");
    }
}
