package Punto_1;

public class basicFordFulkerson {

    static int ops = 0;

    private static int dfs(Graph g, int s, int t, int flow, boolean[] visited) {
        ops++;
        if (s == t) return flow;
        visited[s] = true;
        for (int v : g.adj.get(s)) {
            ops++;
            if (!visited[v] && g.capacity[s][v] > 0) {
                int bottleneck = dfs(g, v, t, Math.min(flow, g.capacity[s][v]), visited);
                ops++;
                if (bottleneck > 0) {
                    g.capacity[s][v] -= bottleneck;
                    g.capacity[v][s] += bottleneck;
                    return bottleneck;
                }
            }
        }
        return 0;
    }

    public static int maxFlow(Graph g, int s, int t) {
        ops = 0;
        int flow = 0;
        int new_flow;
        do {
            boolean[] visited = new boolean[g.n];
            new_flow = dfs(g, s, t, Integer.MAX_VALUE, visited);
            ops++;
            flow += new_flow;
        } while (new_flow != 0);
        return flow;
    }

    public static int getOps() { return ops; }
}
