package lib;

import java.util.Set;
import java.util.Optional;

public interface WeightedGraph<V, C> extends Graph<V> {

    C getCost(V v1, V v2);

    int compare(C c1, C c2);

    Optional<Set<Pair<V>>> minSpanningTree();

    Optional<Set<V>> shortestPath();

}