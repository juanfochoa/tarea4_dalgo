package Punto4;

import java.util.*;

class jarnickPrim1 {

    static Kruskal.Result jPrim1(int n, List<Edge> edges) {

        @SuppressWarnings("unchecked")
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (Edge e : edges) {
            adj[e.u].add(new int[]{e.v, e.w});
            adj[e.v].add(new int[]{e.u, e.w});
        }

        boolean[] vis = new boolean[n];
        List<Edge> T = new ArrayList<>();
        long cost = 0;

        Edge minEdge = Collections.min(edges, Comparator.comparingInt(e -> e.w));

        vis[minEdge.u] = true;
        vis[minEdge.v] = true;
        T.add(minEdge);
        cost += minEdge.w;


        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int[] a : adj[minEdge.u]) if (!vis[a[0]]) pq.offer(new int[]{a[1], minEdge.u, a[0]});
        for (int[] a : adj[minEdge.v]) if (!vis[a[0]]) pq.offer(new int[]{a[1], minEdge.v, a[0]});

        while (!pq.isEmpty()) {
            int[] it = pq.poll();
            int w = it[0], u = it[1], v = it[2];
            if (vis[v]) continue;
            vis[v] = true;
            T.add(new Edge(u, v, w));
            cost += w;
            for (int[] a : adj[v]) if (!vis[a[0]]) pq.offer(new int[]{a[1], v, a[0]});
        }

        for (int s = 0; s < n; s++) {
            if (vis[s]) continue;
            vis[s] = true;
            for (int[] a : adj[s]) pq.offer(new int[]{a[1], s, a[0]});
            while (!pq.isEmpty()) {
                int[] it = pq.poll();
                int w = it[0], u = it[1], v = it[2];
                if (vis[v]) continue;
                vis[v] = true;
                T.add(new Edge(u, v, w));
                cost += w;
                for (int[] a : adj[v]) if (!vis[a[0]]) pq.offer(new int[]{a[1], v, a[0]});
            }
        }

        return new Kruskal.Result(T, cost);
    }
}

class jarnickPrim {

    //soporta grafos no conexos.
    @SuppressWarnings("unchecked")
    static Kruskal.Result jPrim(int n, List<Edge> edges){
        // Se construyen listas de adyacencia (no dirigido)
        List<int[]>[] adj = new ArrayList[n];
        for (int i=0;i<n;i++) adj[i] = new ArrayList<>();
        for (Edge e: edges){
            adj[e.u].add(new int[]{e.v, e.w});
            adj[e.v].add(new int[]{e.u, e.w});
        }

        boolean[] vis = new boolean[n];
        List<Edge> T = new ArrayList<>();
        long cost = 0;

        // Repetimos Prim por componente (si no es conexo)
        for (int s=0; s<n; s++){
            if (vis[s]) continue;

            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

            
            vis[s] = true;
            for (int[] a: adj[s]) pq.offer(new int[]{a[1], s, a[0]});

            while (!pq.isEmpty()){
                int[] it = pq.poll();
                int w = it[0], u = it[1], v = it[2];
                if (vis[v]) continue;            
                // Tomamos la arista más barata que cruza al no visitado
                vis[v] = true;
                T.add(new Edge(u, v, w));
                cost += w;

                // Expandimos el corte desde v
                for (int[] a: adj[v]){
                    int nv = a[0], nw = a[1];
                    if (!vis[nv]) pq.offer(new int[]{nw, v, nv});
                }
            }
        }
        return new Kruskal.Result(T, cost);
    }
}

public class JPrim1 {
    public static void main(String[] args) {
        int n = 7;
        List<Edge> edges = List.of(
            // Componente A
            new Edge(0,1,1),
            new Edge(0,2,4),
            new Edge(1,2,3),
            new Edge(1,3,5),
            new Edge(2,3,2),
            // Componente B
            new Edge(4,5,7),
            new Edge(5,6,1),
            new Edge(4,6,9)
        );

        var rPrim = jarnickPrim.jPrim(n, new ArrayList<>(edges)); 
        var rPrim1 = jarnickPrim1.jPrim1(n, new ArrayList<>(edges));

        System.out.println("jPrim  : peso=" + rPrim.weight + "  T=" + rPrim.mst);
        System.out.println("jPrim1 : peso=" + rPrim1.weight + "  T=" + rPrim1.mst);
    }
}

