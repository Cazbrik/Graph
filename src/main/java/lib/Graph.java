package lib;

import java.util.Set;
import java.util.Optional;

public interface Graph<V> {

    Set<V> relatedVertices(V vertex);

    Set<V> reachableVertices(V vertex);


    boolean isConnected();

    boolean hasCycle();

    Optional<Set<Pair<V>>> spanningTree();

}