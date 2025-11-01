package Punto_1;

import java.util.*;

public class MainNoNiceI {

    static int[][] petersenEdges = {
        {0,1},{1,2},{2,3},{3,4},{4,0},
        {0,5},{1,6},{2,7},{3,8},{4,9},
        {5,7},{7,9},{9,6},{6,8},{8,5}
    };

    static Graph generateUnbalanced(String tipo) {
        Random rnd = new Random();
        Graph g = new Graph(10);

        for (int[] e : petersenEdges)
            g.addEdge(e[0], e[1], 10 + rnd.nextInt(91));

        // Manipular algunas aristas para crear el desequilibrio
        if (tipo.equals("deltaPlus")) { // más salida desde source
            g.addEdge(0, 1, 500);
            g.addEdge(0, 2, 400);
        } else if (tipo.equals("deltaMinus")) { // más llegada a sink
            g.addEdge(8, 9, 600);
            g.addEdge(5, 9, 500);
        } else { // ambos
            g.addEdge(0, 1, 800);
            g.addEdge(8, 9, 800);
        }
        return g;
    }

    public static void main(String[] args) {
        Graph gA = generateUnbalanced("deltaPlus");
        Graph gB = generateUnbalanced("deltaMinus");
        Graph gC = generateUnbalanced("ambos");

        System.out.println("GRAFO 1 (capacidad de cada arista):");
        for (int[] e : petersenEdges) {
            int w = gA.capacity[e[0]][e[1]];
            System.out.println("  " + e[0] + " -> " + e[1] + "  (" + w + ")");
        }

        System.out.println("\nGRAFO 2 (capacidad de cada arista):");
        for (int[] e : petersenEdges) {
            int w = gB.capacity[e[0]][e[1]];
            System.out.println("  " + e[0] + " -> " + e[1] + "  (" + w + ")");
        }
        System.out.println("---------");

        runBoth(gA, "Red A (δ⁺)");
        runBoth(gB, "Red B (δ⁻)");
        runBoth(gC, "Red C (ambas)");

    }

    static void runBoth(Graph g, String name) {
        Graph gFF = cloneGraph(g);
        Graph gEK = cloneGraph(g);
        int f1 = basicFordFulkerson.maxFlow(gFF, 0, 9);
        int f2 = edmondsKarp.maxFlow(gEK, 0, 9);
        System.out.println("\n" + name + ":");
        System.out.println("  Ford-Fulkerson flujo=" + f1);
        System.out.println("  Edmonds-Karp  flujo=" + f2);
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
