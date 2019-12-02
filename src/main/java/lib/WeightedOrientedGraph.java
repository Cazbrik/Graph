package graph;

public interface WeightedOrientedGraph<V, C> extends OrientedGraph<V> {

    C getCost(V v1, V v2);

    int compare(C c1, C c2);

    Optional<Set<Pair<V>>> minSpanningTree();

    Optional<Set<V>> shortestPath();

}