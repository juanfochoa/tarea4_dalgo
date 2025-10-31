import java.util.*;

class Edge implements Comparable<Edge> {
    int u, v;
    int w;

    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.w, other.w);
    }

    @Override public String toString() {
        return "(" + u + ", " + v + ", " + w + ")";
    }
}

class DisjointSet {
    private int[] parent, height; //height es para la altura aproximada del árbol

    DisjointSet(int n) {
        parent = new int[n];
        height = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            height[i] = 0;
        }
    }

    int find(int u) {
        while (u != parent[u]) {
            u = parent[u];
        }
        return u;
    }

    int smartFind(int u){
        int r = u;
        while (r != parent[r]) {
            r = parent[r];
        }

        while (u != r) {
            int next = parent[u];
            parent[u] = r;
            u = next;
        }
        return r;
    }

    void unionByHeight(int u, int v, boolean useSmartFind){
        int ra = useSmartFind ? smartFind(u) : find(u);
        int rb = useSmartFind ? smartFind(v) : find(v); 
        if (ra == rb) return;

        if (height[ra] < height[rb]) {
            parent[ra] = rb;
        } else if (height[ra] > height[rb]) {
            parent[rb] = ra;
        } else {
            parent[rb] = ra;
            height[ra]++;
        }
    }

    boolean sameSet(int u, int v, boolean useSmartFind){
        int ra = useSmartFind ? smartFind(u) : find(u);
        int rb = useSmartFind ? smartFind(v) : find(v); 
        return ra == rb;
    }
}

class Kruskal {
    static Result kruskalBasic(int n, List<Edge> edges){
        Collections.sort(edges); // O(m log m)
        DisjointSet ds = new DisjointSet(n);

        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        for (Edge edge : edges) {
            if (!ds.sameSet(edge.u, edge.v, false)) {
                ds.unionByHeight(edge.u, edge.v, false); // SIN path compression
                mst.add(edge);
                totalWeight += edge.w;

                if (mst.size() == n - 1) {
                    break;
                }
            }
        }
        return new Result(mst, totalWeight);
    }

    static Result kruskalSmartFind(int n, List<Edge> edges){
        Collections.sort(edges); 
        DisjointSet ds = new DisjointSet(n);

        List<Edge> mst = new ArrayList<>();
        int totalWeight = 0;

        for (Edge edge : edges) {
            if (!ds.sameSet(edge.u, edge.v, true)) {
                ds.unionByHeight(edge.u, edge.v, true); // CON path compression
                mst.add(edge);
                totalWeight += edge.w;

                if (mst.size() == n - 1) {
                    break;
                }
            }
        }
        return new Result(mst, totalWeight);
    }

    static class Result {
        final List<Edge> mst;
        final int totalWeight;

        Result(List<Edge> mst, int totalWeight) {
            this.mst = mst;
            this.totalWeight = totalWeight;
        }
    }
}

public class kruskalSmart {
    public static void main(String[] args) {
        List<Edge> edges = Arrays.asList(
            new Edge(0,1,1),
            new Edge(0, 2,4),
            new Edge(1,2,2),
            new Edge(1,3,5),
            new Edge(2,3,1),
            new Edge(3, 4, 3),
            new Edge(2,4,7)
        );
        
        int n = 5; // Número de vértices

        Kruskal.Result resultBasic = Kruskal.kruskalBasic(n, edges);
        System.out.println("Kruskal Basic MST Weight: " + resultBasic.totalWeight);
        System.out.println("Edges in MST: " + resultBasic.mst);

        Kruskal.Result resultSmart = Kruskal.kruskalSmartFind(n, edges);
        System.out.println("Kruskal Smart MST Weight: " + resultSmart.totalWeight);
        System.out.println("Edges in MST: " + resultSmart.mst);
    }
}

