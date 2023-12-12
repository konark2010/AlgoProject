import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FordFulkersonAlgorithmRunner {
    static int V = 0,E=0;
    static int graph[][];

    static int sinkNode,sourceNode;

    static int N, upperCap;
    static float r;

    private static class Vertex implements Comparable<Vertex> {
        private int node;
        private int capacity;

        public Vertex(int vertex, int capacity) {
            this.node = vertex;
            this.capacity = capacity;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.capacity, other.capacity);
        }
    }

    //main method that reads data from the generated graph csv file and extracts necessary graph data
    public void readDataFromCsv() {
        String csvFile = "SourceSinkGraph10.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Reads the first line to get the number of vertices and edges
            String[] headerValues = br.readLine().split(",");
            int numEdges = Integer.parseInt(headerValues[1].trim());
            V = Integer.parseInt(headerValues[0].trim());
            E = numEdges;
            graph = new int[V][V];

            // Reads edge information and populate the graph
            for (int i = 0; i < numEdges; i++) {
                String[] edgeValues = br.readLine().split(",");
                int fromVertex = Integer.parseInt(edgeValues[0].trim());
                int toVertex = Integer.parseInt(edgeValues[3].trim());
                int capacity = Integer.parseInt(edgeValues[6].trim());
                graph[fromVertex][toVertex] = capacity;
            }

            // Reads source and sink node information
            String[] sourceSinkValues = br.readLine().split(",");
            sourceNode = Integer.parseInt(sourceSinkValues[0].trim());
            sinkNode = Integer.parseInt(sourceSinkValues[1].trim());

            // Reads the last line for additional graph parameters
            String[] parameterValues = br.readLine().split(",");
            N = Integer.parseInt(parameterValues[0].trim());
            r = Float.parseFloat(parameterValues[1].trim());
            upperCap = Integer.parseInt(parameterValues[2].trim());
        }
        //catch block to handle exceptions occured
        catch (IOException e) {
            throw new RuntimeException("Error reading data from CSV", e);
        }
    }


    // implementation of the ford fulkerson algorithm with SAP variation
    private static int fordFulkersonSAPVariation(int graph[][], int s, int t){
        int u,v;
        int residualGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                residualGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];
        int maxFlow = 0;
        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        // checks if there are any more augmenting paths
        while(AugmentingPathCheckerSAPVariation(residualGraph,s,t,parent)){
            int pathLength = 0;
            int pathFlow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
            totalPathLength += pathLength;
            paths++;
        }

        // calculates the metrics of this method to print in the console table
        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printMetrics("SAP", N, r, upperCap, paths, meanLength, meanProportionalLength, E);

        return maxFlow;
    }

    // implementation of the ford fulkerson algorithm with DFS variation
    private static int fordFulkersonDFSLikeVariation(int graph[][], int s, int t){
        int u,v;
        int residualGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                residualGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];
        int maxFlow = 0;
        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        // checks if there are any more augmenting paths
        while(AugmentingPathCheckerDFSLikeVariation(residualGraph,s,t,parent)){
            int pathLength = 0;
            int pathFlow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
            totalPathLength += pathLength;
            paths++;
        }

        // calculates the metrics of this method to print in the console table
        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printMetrics("DFS-Like", N, r, upperCap, paths, meanLength, meanProportionalLength, E);

        return maxFlow;
    }

    // implementation of the ford fulkerson algorithm with Random DFS variation
    private static int fordFulkersonRandomDFSVariation(int graph[][], int s, int t){
        int u,v;
        int residualGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                residualGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];
        int maxFlow = 0;
        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        // checks if there are any more augmenting paths
        while(AugmentingPathCheckerRandomDFSVariation(residualGraph,s,t,parent)){
            int pathLength = 0;
            int pathFlow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
            totalPathLength += pathLength;
            paths++;
        }

        // calculates the metrics of this method to print in the console table
        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printMetrics("Random DFS", N, r, upperCap, paths, meanLength, meanProportionalLength, E);

        return maxFlow;
    }

    // implementation of the ford fulkerson algorithm with Max Cap variation
    private static int fordFulkersonMaxCapVariation(int graph[][], int s, int t){
        int u,v;
        int residualGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                residualGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];
        int maxFlow = 0;
        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        // checks if there are any more augmenting paths
        while(AugmentingPathCheckerMaxCapacityVariation(residualGraph,s,t,parent)){
            int pathLength = 0;
            int pathFlow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
            totalPathLength += pathLength;
            paths++;
        }

        // calculates the metrics of this method to print in the console table
        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printMetrics("Max Cap", N, r, upperCap, paths, meanLength, meanProportionalLength, E);

        return maxFlow;
    }

    // returns a boolean for if augmenting path exists
    private static boolean AugmentingPathCheckerRandomDFSVariation(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> minHeap = new PriorityQueue<>();
        minHeap.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;

        boolean pathExists = false;

        while (!minHeap.isEmpty()) {
            Vertex currentNode = minHeap.poll();
            int u = currentNode.node;

            if (u == sinkNode) {
                // Path from source to sink exists
                pathExists = true;
                break;
            }

            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0 && dist[v] == Integer.MAX_VALUE) {
                    int distance = new Random().nextInt();
                    dist[v] = distance;
                    minHeap.add(new Vertex(v, distance));
                    parent[v] = u; // Update the parent of vertex v
                }
            }
        }

        return pathExists;
    }

    // returns a boolean for if augmenting path exists
    public static boolean AugmentingPathCheckerMaxCapacityVariation(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[sourceNode] = Integer.MAX_VALUE;

        PriorityQueue<Vertex> meanHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.capacity));
        meanHeap.add(new Vertex(sourceNode, Integer.MAX_VALUE));

        boolean pathExists = false;

        while (!meanHeap.isEmpty()) {
            int u = meanHeap.poll().node;

            if (u == sinkNode) {
                // Path from source to sink exists
                pathExists = true;
                break;
            }

            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0) {
                    int edgeCapacity = graph[u][v];

                    int minCapacity = Math.min(dist[u], edgeCapacity);

                    if (minCapacity > dist[v]) {
                        dist[v] = minCapacity;
                        meanHeap.add(new Vertex(v, dist[v]));
                        parent[v] = u; // Update the parent of vertex v
                    }
                }
            }
        }


        return pathExists;
    }

    // returns a boolean for if augmenting path exists
    public static boolean AugmentingPathCheckerSAPVariation(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int[] dist = new int[graph.length];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> minHeap = new PriorityQueue<>();
        minHeap.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;
        parent[sourceNode] = -1;

        while (!minHeap.isEmpty()) {
            Vertex currentNode = minHeap.poll();
            int u = currentNode.node;

            if (u == sinkNode) {
                // Path from source to sink exists
                return true;
            }

            for (int v = 0; v < graph.length; v++) {
                if (graph[u][v] > 0) {
                    int newDistance = dist[u] + 1;

                    if (newDistance < dist[v]) {
                        dist[v] = newDistance;
                        minHeap.add(new Vertex(v, newDistance));
                        parent[v] = u; // Update the parent of vertex v
                    }
                }
            }
        }
        // No path from source to sink
        return false;
    }

    // returns a boolean for if augmenting path exists
    public static boolean AugmentingPathCheckerDFSLikeVariation(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> minHeap = new PriorityQueue<>();
        minHeap.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;
        int decreasingCounter = 0;

        boolean pathExists = false;

        while (!minHeap.isEmpty()) {
            Vertex currentNode = minHeap.poll();
            int u = currentNode.node;

            if (u == sinkNode) {
                // Path from source to sink exists
                pathExists = true;
                break;
            }

            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0 && dist[v] == Integer.MAX_VALUE) {
                    decreasingCounter--;
                    dist[v] = decreasingCounter;
                    minHeap.add(new Vertex(v, decreasingCounter));
                    parent[v] = u; // Update the parent of vertex v
                }
            }
        }

        return pathExists;
    }

    //prints all the obtained metrics from all the 4 variations of the ford fulkerson method
    private static void printMetrics(String algorithm, int n, float r, int upperCap, int paths, double ml, double mpl, int totalEdges) {
        System.out.printf("%-10s|%5d|%5.1f|%10d|%7d|%5.1f|%20.15f|%12d\n", algorithm, n, r, upperCap, paths, ml, mpl, totalEdges);
    }

    public static void main(String[] args){
        new FordFulkersonAlgorithmRunner().readDataFromCsv();

        System.out.println("Algorithm | n   | r   | upperCap | paths | ML  | MPL                 | total edges");
        System.out.println("----------|-----|-----|----------|-------|-----|---------------------|------------");

        fordFulkersonSAPVariation(graph, sourceNode, sinkNode);
        fordFulkersonDFSLikeVariation(graph, sourceNode, sinkNode);
        fordFulkersonRandomDFSVariation(graph, sourceNode, sinkNode);
        fordFulkersonMaxCapVariation(graph, sourceNode, sinkNode);

    }

}
