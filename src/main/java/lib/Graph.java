package graph;

import java.util.Set;
import java.util.Optional;

public interface Graph<V> {

    Set<V> relatedVertices(V vertex);

    Set<V> reachableFrom(V vertex);

    boolean isConnected();

    boolean hasCycle();

    Optional<Set<Pair<V>>> spanningTree();

}