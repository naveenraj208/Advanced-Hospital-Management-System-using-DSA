package services;

import java.util.*;

public class ShortestPath {


    private static final String[] LOCATIONS = {
            "K R Puram", "Whitefield", "Marathalli", "M G Road", "HSR Layout", "Kasavanahalli Hospital"
    };

    private static final int INF = Integer.MAX_VALUE;
    private static final int[][] COST_MATRIX = {

            {0, 10, 15, INF, INF, INF},
            {10, 0, 5, 20, INF, 30},   // Whitefield
            {15, 5, 0, 10, 25, 20},    // Marathalli
            {INF, 20, 10, 0, 5, 10},   // MG Road
            {INF, INF, 25, 5, 0, 5},   // HSR Layout
            {INF, 30, 20, 10, 5, 0}    // Kasavanahalli Hospital
    };


    private static void dijkstra(int start) {
        int n = LOCATIONS.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int[] prev = new int[n];

        Arrays.fill(dist, INF);
        Arrays.fill(visited, false);
        Arrays.fill(prev, -1);

        dist[start] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = findMinDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && COST_MATRIX[u][v] != INF && dist[u] != INF && dist[u] + COST_MATRIX[u][v] < dist[v]) {
                    dist[v] = dist[u] + COST_MATRIX[u][v];
                    prev[v] = u;
                }
            }
        }

        printPath(start, n - 1, prev, dist);
    }


    private static int findMinDistance(int[] dist, boolean[] visited) {
        int minDist = INF;
        int minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= minDist) {
                minDist = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // Method to print the path from start to destination
    private static void printPath(int start, int dest, int[] prev, int[] dist) {
        if (dist[dest] == INF) {
            System.out.println("No path from " + LOCATIONS[start] + " to " + LOCATIONS[dest]);
            return;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = dest; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);

        System.out.print("Shortest path from " + LOCATIONS[start] + " to " + LOCATIONS[dest] + ": ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(LOCATIONS[path.get(i)]);
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nTotal cost: " + dist[dest]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the starting location (1 for K R Puram, 2 for Whitefield, 3 for Marathalli, 4 for M G Road, 5 for HSR Layout): ");
        int startLocation = scanner.nextInt() - 1;

        dijkstra(startLocation);
    }
}
