import java.util.LinkedList;

public class basicFordFulkerson {
    static final int V = 6;

    boolean dfs(int residualGraph[][], int s, int t, int parent[]){
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0){
            int u = queue.poll();

            for (int v = 0; v < V; v++){
                if (visited[v] == false && residualGraph[u][v] > 0){
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }


    int basicFordFulkerson(int graph[][], int s, int t){
        int u,v;
        int residualGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                residualGraph[u][v] = graph[u][v];
        
        int parent[] = new int[V];
        int max_flow = 0;
        while(dfs(residualGraph, s, t, parent)){
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]){
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }

            for (v = t; v != s; v = parent[v]){
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }

            max_flow += path_flow;
        }
        return max_flow;
    }

    public static void main(String[] args) throws Exception{
        int graph[][] = new int[][] {
            {0, 16, 13, 0, 0, 0},
            {0, 0, 10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0}
        };
        basicFordFulkerson m = new basicFordFulkerson();
        System.out.println("El flujo Máximo es " +
                           m.basicFordFulkerson(graph, 0, 5));
    } 

}
