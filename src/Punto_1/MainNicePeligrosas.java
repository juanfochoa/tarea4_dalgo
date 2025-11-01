package Punto_1;

import java.util.*;

public class MainNicePeligrosas {

    static int[][] petersenEdges = {
        {0,1},{1,2},{2,3},{3,4},{4,0},
        {0,5},{1,6},{2,7},{3,8},{4,9},
        {5,7},{7,9},{9,6},{6,8},{8,5}
    };

    static Graph generateNiceDangerous() {
        Random rnd = new Random();
        Graph g = new Graph(10);

        // Escoge al azar 7 aristas “grandes” y 2 “pequeñas”
        Set<Integer> grandes = new HashSet<>();
        while (grandes.size() < 7) grandes.add(rnd.nextInt(15));

        Set<Integer> pequeñas = new HashSet<>();
        while (pequeñas.size() < 2) {
            int e = rnd.nextInt(15);
            if (!grandes.contains(e)) pequeñas.add(e);
        }

        for (int i = 0; i < 15; i++) {
            int w;
            if (grandes.contains(i)) w = 1000 + rnd.nextInt(9001);
            else if (pequeñas.contains(i)) w = 1 + rnd.nextInt(5);
            else w = 10 + rnd.nextInt(90);
            g.addEdge(petersenEdges[i][0], petersenEdges[i][1], w);
        }
        return g;
    }

    public static void main(String[] args) {
        Graph g1 = generateNiceDangerous();
        Graph g2 = generateNiceDangerous();

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

        runBoth(g1, "Red 1");
        runBoth(g2, "Red 2");
    }

    static void runBoth(Graph g, String name) {
        Graph gFF = cloneGraph(g);
        Graph gEK = cloneGraph(g);

        int f1 = basicFordFulkerson.maxFlow(gFF, 0, 9);
        int f2 = edmondsKarp.maxFlow(gEK, 0, 9);

        System.out.println("\n" + name + ":");
        System.out.println("  Ford-Fulkerson flujo=" + f1 + " ops=" + basicFordFulkerson.getOps());
        System.out.println("  Edmonds-Karp  flujo=" + f2 + " ops=" + edmondsKarp.getOps());
    }

    static Graph cloneGraph(Graph g) {
        Graph copy = new Graph(g.n);
        for (int u = 0; u < g.n; u++) {
            for (int v : g.adj.get(u))
                if (g.capacity[u][v] > 0)
                    copy.addEdge(u, v, g.capacity[u][v]);
        }
        return copy;
    }
}
