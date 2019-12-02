package graph;

import java.util.optional;

public interface OrientedGraph<V> {

    Set<V> childrenVertices(V vertex);

    Set<V> parentVertices(V vertex);

    Set<V> reachableFrom(V vertex);

    Set<V> reachableTo(V vertex);

    boolean isStronglyConnected();

    boolean isWeaklyConnected();

    boolean hasCycle();
    
    boolean hasCircuit();

    Optional<Set<Pair<V>>> spanningTree();

}