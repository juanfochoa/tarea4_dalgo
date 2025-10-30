import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class edmondsKarp {
    static final int V = 6;

    static class Edge {
        int to, rev;
        int capacity;
        int flow;

        Edge(int to, int rev, int capacity) {
            this.to = to;
            this.rev = rev;
            this.capacity = capacity;
            this.flow = 0;
        }
        int residualCapacity() {
            return capacity - flow;
        }
    }

    boolean bfs(Edge graph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int i = 0; i < graph[u].length; i++) {
                Edge e = graph[u][i];
                if (!visited[e.to] && e.residualCapacity() > 0) {
                    queue.add(e.to);
                    parent[e.to] = u;
                    visited[e.to] = true;
                }
            }
        }
        return visited[t];
    }

    public static int edmondsKarp(Edge graph, int s, int t){
        int maxflow = 0;
        int n = graph.length;

        while (true){
            int[] parentV = new int[n];
            int[] parentE = new int[n];
            Arrays.fill(parentV, -1);

            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(s);
            parentV[s] = s;

            while (!queue.isEmpty() && parentV[t] == -1){
                int u = queue.poll();
                List<Edge> edges = graph.adj[u];
                for (int i = 0; i < edges.length; i++){
                    Edge e = edges[i];
                    if (parentV[e.to] == -1 && e.residualCapacity() > 0){
                        parentV[e.to] = u;
                        parentE[e.to] = i;
                        if (e.to == t) break;
                        queue.add(e.to);
                    }
                }
            }

            if(parentV[t] == -1) break;

            int bottleneck = Integer.MAX_VALUE;
            for (int v = t; v != s; v = parentV[v]){
                Edge e = graph.adj[parentV[v]][parentE[v]];
                bottleneck = Math.min(bottleneck, e.residualCapacity());
            }

            for (int v = t; v != s; v = parentV[v]){
                Edge e = graph.adj[parentV[v]][parentE[v]];
                e.flow += bottleneck;
                graph.adj[e.to][e.rev].flow -= bottleneck;
            }

            maxflow += bottleneck;
        }
        return maxflow;
    }
}
