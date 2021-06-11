package graph;

import java.util.*;

/**
 * @author priyamvora
 * @created 11/04/2021
 */
public class Graph {
    LinkedList<Integer>[] adj;
    boolean[] visited;
    int V;

    public Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
        visited = new boolean[V];
    }

    boolean[] recStack;

    public boolean detectCycleInDirectedGraph() {
        recStack = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visited[i] && detectCycleInDirectedGraphUtil(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean detectCycleInDirectedGraphUtil(int start) {
        visited[start] = true;
        recStack[start] = true;
        for (int i : adj[start]) {
            if (recStack[i]) {
                return true;
            }
            if (!visited[i]) {
                if (detectCycleInDirectedGraphUtil(i)) {
                    return true;
                }
            }
        }
        recStack[start] = false;
        return false;
    }

    public boolean detectCycleInUndirectedGraph() {
        for (int i = 0; i < V; i++) {
            if (!visited[i] && detectCycleInUndirectedGraphUtil(i, -1)) {
                return true;
            }
        }
        return false;
    }

    public boolean detectCycleInUndirectedGraphUtil(int start, int parent) {
        visited[start] = true;
        for (int i : adj[start]) {
            if (!visited[i] && detectCycleInUndirectedGraphUtil(i, start)) {
                return true;
            } else if (i != parent) {
                return true;
            }
        }
        return false;
    }

    public int MSTKruskal(int[][] edges, int V) {
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            Edge edge = new Edge(edges[i][0], edges[i][1], edges[i][2]);
            list.add(edge);
        }
        Collections.sort(list);
        DSU dsu = new DSU(V);
        int totalWeight = 0;
        for (Edge i : list) {
            int parent1 = dsu.find(i.from);
            int parent2 = dsu.find(i.to);
            if (parent1 != parent2) {
                totalWeight += i.weight;
                dsu.merge(i.from, i.to);
            }
        }
        return totalWeight;
    }

    public int MSTPrim(List<Edge>[] adj) {
        int totalWeight = 0;
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[V + 1];
        visited[0] = true;
        int totalNodes = 1;
        for (Edge i : adj[0]) {
            priorityQueue.add(i);
        }
        while (!priorityQueue.isEmpty()) {
            Edge e = priorityQueue.poll();
            totalWeight += e.weight;
            if (!visited[e.to]) {
                totalNodes += e.weight;
                totalNodes++;
            } else {
                continue;
            }
            if (totalNodes == V) {
                break;
            }
            visited[e.to] = true;
            for (Edge i : adj[e.to]) {
                if (!visited[i.to]) {
                    priorityQueue.add(i);
                }
            }
        }
        return totalWeight;
    }

    public void dijkstra(List<Edge>[] adj, int V) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        Arrays.fill(dist, Integer.MAX_VALUE / 10);
        dist[0] = 0;
        visited[0] = true;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(-1, 0, 0));
        int cnt = 0;
        while (cnt != V) {
            Edge top = pq.poll();
            visited[top.to] = true;
            cnt++;
            for (Edge edge : adj[top.to]) {
                if (!visited[edge.to] && dist[edge.to] > dist[top.to] + edge.weight) {
                    dist[edge.to] = dist[top.to] + edge.weight;
                    pq.add(new Edge(-1, edge.to, dist[edge.to]));
                }
            }
        }
    }

    public int minIterationsToPassInfoToNodes() {
        int[] minIter = new int[V];
        minIterationsToPassInfoToNodesUtil(0, minIter);
        return minIter[0];
    }

    public void minIterationsToPassInfoToNodesUtil(int start, int[] minIter) {
        if (adj[start].size() == 0) {
            return;
        }
        minIter[start] = adj[start].size();
        Integer[] minIterTemp = new Integer[minIter[start]];
        int k = 0;
        for (Integer end : adj[start]) {
            minIterationsToPassInfoToNodesUtil(end, minIter);
            minIterTemp[k++] = minIter[end];
        }
        Arrays.sort(minIterTemp, Collections.reverseOrder());
        for (int i = 0; i < minIterTemp.length; i++) {
            minIter[start] = Math.max(minIter[start], minIterTemp[i] + i + 1);
        }

    }


}

class Edge implements Comparable<Edge> {
    int from, to, weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge arg0) {
        return weight - arg0.weight;
    }
}

class DSU {
    int[] parent;
    int[] rank;
    int size;

    public DSU(int size) {
        this.size = size;
        makeSet();
    }

    public void makeSet() {
        parent = new int[this.size + 1];
        rank = new int[this.size + 1];
        for (int i = 0; i <= size; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            x = find(parent[x]);
        }
        return parent[x];
    }

    public void merge(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);
        if (rank[aRoot] < rank[bRoot]) {
            parent[aRoot] = bRoot;
        } else if (rank[aRoot] > rank[bRoot]) {
            parent[bRoot] = aRoot;
        } else {
            parent[bRoot] = aRoot;
            rank[aRoot]++;
        }
    }
}
