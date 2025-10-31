package Punto4;

import java.util.*;

class Edge implements Comparable<Edge> {
    final int u, v;
    final int w;
    Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    @Override public int compareTo(Edge o) { return Integer.compare(this.w, o.w); }
    @Override public String toString() { return "(" + u + "-" + v + ":" + w + ")"; }
}

class DisjointSet {
    int[] parent, rank;
    DisjointSet(int n) { parent = new int[n]; rank = new int[n];
        for (int i=0;i<n;i++) parent[i]=i; }
    int find(int x){ return parent[x]==x? x : (parent[x]=find(parent[x])); }
    boolean union(int a,int b){
        int ra=find(a), rb=find(b); if(ra==rb) return false;
        if(rank[ra]<rank[rb]) parent[ra]=rb;
        else if(rank[ra]>rank[rb]) parent[rb]=ra;
        else { parent[rb]=ra; rank[ra]++; }
        return true;
    }
}

// --------- Kruskal (bosque si el grafo no es conexo) ----------
class Kruskal {
    static Result mstKruskal(int n, List<Edge> edges){
        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(n);
        List<Edge> T = new ArrayList<>();
        long cost = 0;
        for(Edge e: edges){
            if(ds.union(e.u, e.v)){
                T.add(e); cost += e.w;
            }
        }
        return new Result(T, cost);
    }
    static class Result {
        final List<Edge> mst; final long weight;
        Result(List<Edge> mst,long weight){ this.mst=mst; this.weight=weight; }
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

//mismo ejemplo para Kruskal y JPrim
public class JPrim {
    public static void main(String[] args) {
        int n = 7; // nodos: 0..6 (grafo con dos componentes: {0,1,2,3} y {4,5,6})

        // Componente A (0-1-2-3)
        //   0--(4)--2
        //   |      /|
        //  (1)   (3) (2)
        //   |   /    |
        //   1--(5)-- 3
        //
        // Componente B (4-5-6)
        //   4--(7)--5--(1)--6   y 4--(9)--6
        List<Edge> edges = List.of(
            // componente A
            new Edge(0,1,1),
            new Edge(0,2,4),
            new Edge(1,3,5),
            new Edge(2,3,2),
            new Edge(1,2,3),
            // componente B
            new Edge(4,5,7),
            new Edge(5,6,1),
            new Edge(4,6,9)
        );

        var rK = Kruskal.mstKruskal(n, new ArrayList<>(edges));
        System.out.println("Kruskal: peso=" + rK.weight + "  T=" + rK.mst);

        var rP = jarnickPrim.jPrim(n, new ArrayList<>(edges));
        System.out.println("JPrim  : peso=" + rP.weight + "  T=" + rP.mst);
    }
}
