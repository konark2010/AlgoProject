import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FordFulkersonAlgorithmRunner {

    static int V = 0,E=0;
    static int graph[][];

    static int sinkNode,sourceNode;

    static int N, upperCap;
    static float r;


    public void read_data_from_csv() {
        String csvFile = "SourceSinkGraph.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            String[] values = br.readLine().split(",");
            int n = Integer.parseInt(values[1].trim());
            V = Integer.parseInt(values[0].trim());
            E = Integer.parseInt(values[1].trim());
            graph = new int[V][V];
            for(int i=0; i<n; i++){
                values = br.readLine().split(",");
                graph[Integer.parseInt(values[0].trim())][Integer.parseInt(values[3].trim())]= Integer.parseInt(values[6].trim());
            }
            values = br.readLine().split(",");
            sourceNode = Integer.parseInt(values[0].trim());
            sinkNode = Integer.parseInt(values[1].trim());
            values = br.readLine().split(",");
            N = Integer.parseInt(values[0].trim());
            r = Float.parseFloat(values[1].trim());
            upperCap = Integer.parseInt(values[2].trim());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static int fordFulkerson_SAP(int graph[][], int s, int t){
        int u,v;

        int rGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];

        int max_flow = 0;

        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        while(dijkstra_SAP(rGraph,s,t,parent)){
            int pathLength = 0;
            int path_flow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
            totalPathLength += pathLength;
            paths++;
        }

        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printTableEntry("SAP", N, r, upperCap, paths, meanLength, meanProportionalLength, E);

        return max_flow;
    }

    private static int fordFulkerson_DFS(int graph[][], int s, int t){
        int u,v;

        int rGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];

        int max_flow = 0;

        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        while(dijkstra_DFS(rGraph,s,t,parent)){
            int pathLength = 0;
            int path_flow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
            totalPathLength += pathLength;
            paths++;
        }

        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printTableEntry("DFS-Like", N, r, upperCap, paths, meanLength, meanProportionalLength, E);
        return max_flow;
    }

    private static int fordFulkerson_RandomDFS(int graph[][], int s, int t){
        int u,v;

        int rGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];

        int max_flow = 0;

        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        while(dijkstra_RandomDFS(rGraph,s,t,parent)){
            int pathLength = 0;
            int path_flow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
            totalPathLength += pathLength;
            paths++;
        }

        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printTableEntry("Random DFS", N, r, upperCap, paths, meanLength, meanProportionalLength, E);
        return max_flow;
    }

    private static int fordFulkerson_MaxCap(int graph[][], int s, int t){
        int u,v;

        int rGraph[][] = new int[V][V];

        for(u=0;u<V;u++){
            for(v=0;v<V;v++){
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];

        int max_flow = 0;

        int paths = 0;
        int totalPathLength = 0;
        int maxLength = -1;

        while(dijkstra_MaxCapacity(rGraph,s,t,parent)){
            int pathLength = 0;
            int path_flow = Integer.MAX_VALUE;
            for(v=t; v!=s; v = parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
                pathLength++;
            }

            if(pathLength > maxLength){
                maxLength = pathLength;
            }

            for(v = t; v != s; v=parent[v]){
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
            totalPathLength += pathLength;
            paths++;
        }

        double meanLength = (double) totalPathLength / paths;
        double meanProportionalLength = (double) meanLength / maxLength;

        printTableEntry("Max Cap", N, r, upperCap, paths, meanLength, meanProportionalLength, E);
        return max_flow;
    }

    private static boolean dijkstra_RandomDFS(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;

        boolean pathExists = false;

        while (!queue.isEmpty()) {
            Vertex currentNode = queue.poll();
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
                    queue.add(new Vertex(v, distance));
                    parent[v] = u; // Update the parent of vertex v
                }
            }
        }

        return pathExists;
    }

    public static boolean dijkstra_MaxCapacity(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MIN_VALUE);
        dist[sourceNode] = Integer.MAX_VALUE; // Initialize source with maximum capacity

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.capacity));
        priorityQueue.add(new Vertex(sourceNode, Integer.MAX_VALUE));

        boolean pathExists = false;

        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll().node;

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
                        priorityQueue.add(new Vertex(v, dist[v]));
                        parent[v] = u; // Update the parent of vertex v
                    }
                }
            }
        }


        return pathExists;
    }

    public static boolean dijkstra_SAP(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int[] dist = new int[graph.length];  // Use graph.length instead of V
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;
        parent[sourceNode] = -1; // Set parent of source node to -1

        while (!queue.isEmpty()) {
            Vertex currentNode = queue.poll();
            int u = currentNode.node;

            if (u == sinkNode) {
                // Path from source to sink exists
                return true;
            }

            for (int v = 0; v < graph.length; v++) {  // Use graph.length instead of V
                if (graph[u][v] > 0) {
                    int newDistance = dist[u] + 1;

                    if (newDistance < dist[v]) {
                        dist[v] = newDistance;
                        queue.add(new Vertex(v, newDistance));
                        parent[v] = u; // Update the parent of vertex v
                    }
                }
            }
        }

        // No path from source to sink
        return false;
    }

    public static boolean dijkstra_DFS(int[][] graph, int sourceNode, int sinkNode, int[] parent) {
        int V = graph.length;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(sourceNode, 0));
        dist[sourceNode] = 0;
        int decreasingCounter = 0;

        boolean pathExists = false;

        while (!queue.isEmpty()) {
            Vertex currentNode = queue.poll();
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
                    queue.add(new Vertex(v, decreasingCounter));
                    parent[v] = u; // Update the parent of vertex v
                }
            }
        }

        return pathExists;
    }






    public static void main(String[] args){
        new FordFulkersonAlgorithmRunner().read_data_from_csv();
        //  System.out.println(fordFulkerson(graph,sourceNode,sinkNode));

        // System.out.println("Shortest Augmenting Path : ");

        System.out.println("Algorithm | n   | r   | upperCap | paths | ML  | MPL                 | total edges");
        System.out.println("----------|-----|-----|----------|-------|-----|---------------------|------------");

        fordFulkerson_SAP(graph, sourceNode, sinkNode);
        fordFulkerson_DFS(graph, sourceNode, sinkNode);
        fordFulkerson_RandomDFS(graph, sourceNode, sinkNode);
        fordFulkerson_MaxCap(graph, sourceNode, sinkNode);

    }

    private static void printTableEntry(String algorithm, int n, float r, int upperCap, int paths, double ml, double mpl, int totalEdges) {
        System.out.printf("%-10s|%5d|%5.1f|%10d|%7d|%5.1f|%20.15f|%12d\n", algorithm, n, r, upperCap, paths, ml, mpl, totalEdges);
    }

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

}
