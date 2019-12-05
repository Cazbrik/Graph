package lib;

import java.util.Set;
import java.util.Optional;

public interface NonOrientedGraph<V> {

    Set<V> getVertices();

    Set<V> relatedVertices(V vertex);

    default Set<V> reachableVertices(V vertex) {}

    boolean isConnected();

    boolean hasCycle();

    Optional<Set<Pair<V>>> spanningTree();

}


public class NonOrientedGraphOperations<V> {

    Function<T, Set<V>> getVertices;
    Function<V, Set<V>> relatedVertices;

    NonOrientedGraphOperations(Function<V, Set<V>> relatedVertices) {

    }

    Set<V> reachableVertices(V vertex) {}

    boolean isConnected();

    boolean hasCycle();

    Optional<Set<Pair<V>>> spanningTree();

}

public class NonOrientedGraphOperations<V> {

    static Set<V> reachableVertices(Function<V, Set<V>> relatedVertices, V vertex) {}

    boolean isConnected();

    boolean hasCycle();

    Optional<Set<Pair<V>>> spanningTree();

}