package Punto_1;

import java.util.Random;

public class MainFlow {
    static int[][] petersenEdges = {
        {0,1},{1,2},{2,3},{3,4},{4,0},
        {0,5},{1,6},{2,7},{3,8},{4,9},
        {5,7},{7,9},{9,6},{6,8},{8,5}
    };

    static Graph generateNiceNetwork() {
        Random rnd = new Random();
        Graph g = new Graph(10);
        for (int[] e : petersenEdges) {
            int w = 1 + rnd.nextInt(50);
            g.addEdge(e[0], e[1], w);
        }
        return g;
    }

    public static void main(String[] args) {
        // Generar dos redes “nice”
        Graph g1 = generateNiceNetwork();
        Graph g2 = generateNiceNetwork();

        // Clonar las redes (porque los algoritmos modifican capacities)
        Graph g1_ff = cloneGraph(g1);
        Graph g1_ek = cloneGraph(g1);
        Graph g2_ff = cloneGraph(g2);
        Graph g2_ek = cloneGraph(g2);

        // Flujo máximo con ambos algoritmos
        int maxFlowG1_FF = basicFordFulkerson.maxFlow(g1_ff, 0, 9);
        int maxFlowG1_EK = edmondsKarp.maxFlow(g1_ek, 0, 9);
        int maxFlowG2_FF = basicFordFulkerson.maxFlow(g2_ff, 0, 9);
        int maxFlowG2_EK = edmondsKarp.maxFlow(g2_ek, 0, 9);

        System.out.println("Grafo 1:");
        System.out.println("  Ford–Fulkerson flujo = " + maxFlowG1_FF + "  ops=" + basicFordFulkerson.getOps());
        System.out.println("  Edmonds–Karp  flujo = " + maxFlowG1_EK + "  ops=" + edmondsKarp.getOps());

        System.out.println("\nGrafo 2:");
        System.out.println("  Ford–Fulkerson flujo = " + maxFlowG2_FF + "  ops=" + basicFordFulkerson.getOps());
        System.out.println("  Edmonds–Karp  flujo = " + maxFlowG2_EK + "  ops=" + edmondsKarp.getOps());
    }

    static Graph cloneGraph(Graph g) {
        Graph copy = new Graph(g.n);
        for (int u = 0; u < g.n; u++) {
            for (int v : g.adj.get(u)) {
                if (g.capacity[u][v] > 0)
                    copy.addEdge(u, v, g.capacity[u][v]);
            }
        }
        return copy;
    }
    
}
