package Punto_1;

import java.util.*;

public class edmondsKarp {

    static int ops = 0;

    public static int maxFlow(Graph g, int s, int t) {
        ops = 0;
        int flow = 0;
        int[] parent = new int[g.n];

        while (true) {
            Arrays.fill(parent, -1);
            parent[s] = s;
            Queue<Integer> q = new LinkedList<>();
            q.add(s);

            // BFS para encontrar camino aumentante
            while (!q.isEmpty() && parent[t] == -1) {
                int u = q.poll();
                for (int v : g.adj.get(u)) {
                    ops++;
                    if (parent[v] == -1 && g.capacity[u][v] > 0) {
                        parent[v] = u;
                        q.add(v);
                    }
                }
            }

            if (parent[t] == -1) break; // no hay más caminos

            // calcular cuello de botella
            int bottleneck = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                bottleneck = Math.min(bottleneck, g.capacity[u][v]);
                ops++;
            }

            // actualizar capacidades
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                g.capacity[u][v] -= bottleneck;
                g.capacity[v][u] += bottleneck;
                ops++;
            }

            flow += bottleneck;
        }
        return flow;
    }

    public static int getOps() { return ops; }
}
