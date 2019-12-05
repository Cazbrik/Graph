package lib;

import java.util.Set;
import java.util.Optional;

public interface WeightedNonOrientedGraph<V, C> extends NonOrientedGraph<V> {

    C getCost(V v1, V v2);

    int compare(C c1, C c2);

    Optional<Set<Pair<V>>> minSpanningTree();

    Optional<Set<V>> shortestPath();

}