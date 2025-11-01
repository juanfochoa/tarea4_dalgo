package Punto_1;
import java.util.*;

public class Graph {
    public int n;
    public int[][] capacity;
    public List<List<Integer>> adj;
    
    public Graph(int n) {
        this.n = n;
        capacity = new int[n][n];
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        capacity[u][v] = w;
        adj.get(u).add(v);
        adj.get(v).add(u); 
    }
}
