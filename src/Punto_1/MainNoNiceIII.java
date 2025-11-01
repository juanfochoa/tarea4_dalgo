package Punto_1;

import java.util.*;

public class MainNoNiceIII {

    static int[][] petersenEdges = {
        {0,1},{1,2},{2,3},{3,4},{4,0},
        {0,5},{1,6},{2,7},{3,8},{4,9},
        {5,7},{7,9},{9,6},{6,8},{8,5}
    };

    static Graph generateNoNiceIII() {
        Random rnd = new Random();
        Graph g = new Graph(10);

        // Aristas base
        for (int[] e : petersenEdges)
            g.addEdge(e[0], e[1], 1 + rnd.nextInt(1000));

        // Tres pares antiparalelos + desbalance δ⁺, δ⁻
        g.addEdge(0, 1, 800 + rnd.nextInt(200));
        g.addEdge(1, 0, 100 + rnd.nextInt(300));

        g.addEdge(3, 4, 600 + rnd.nextInt(300));
        g.addEdge(4, 3, 50 + rnd.nextInt(200));

        g.addEdge(5, 6, 700 + rnd.nextInt(300));
        g.addEdge(6, 5, 20 + rnd.nextInt(150));

        // desbalance explícito
        g.addEdge(0, 2, 500); // δ⁺(σ) > 0
        g.addEdge(8, 9, 400); // δ⁻(τ) > 0

        return g;
    }

    public static void main(String[] args) {
        Graph g1 = generateNoNiceIII();
        Graph g2 = generateNoNiceIII();

        System.out.println("GRAFO 1 (capacidad de cada arista):");
        for (int[] e : petersenEdges) {
            int w = g1.capacity[e[0]][e[1]];
            System.out.println("  " + e[0] + " -> " + e[1] + "  (" + w + ")");
        }

        System.out.println("\nGRAFO 2 (capacidad de cada arista):");
        for (int[] e : petersenEdges) {
            int w = g2.capacity[e[0]][e[1]];
            System.out.println("  " + e[0] + " -> " + e[1] + "  (" + w + ")");
        }
        System.out.println("---------");

        runBoth(g1, "Red 1 (no-nice III)");
        runBoth(g2, "Red 2 (no-nice III)");
    }

    static void runBoth(Graph g, String name) {
        Graph gFF = cloneGraph(g);
        Graph gEK = cloneGraph(g);
        int f1 = basicFordFulkerson.maxFlow(gFF, 0, 9);
        int f2 = edmondsKarp.maxFlow(gEK, 0, 9);
        System.out.println("\n" + name + ":");
        System.out.println("  Ford-Fulkerson flujo=" + f1 + "  ops=" + basicFordFulkerson.getOps());
        System.out.println("  Edmonds-Karp  flujo=" + f2 + "  ops=" + edmondsKarp.getOps());
    }

    static Graph cloneGraph(Graph g) {
        Graph copy = new Graph(g.n);
        for (int u = 0; u < g.n; u++)
            for (int v : g.adj.get(u))
                if (g.capacity[u][v] > 0)
                    copy.addEdge(u, v, g.capacity[u][v]);
        return copy;
    }
}
