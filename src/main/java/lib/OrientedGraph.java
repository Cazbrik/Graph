package lib;

import java.util.Optional;
import java.util.Set;

public interface OrientedGraph<V> {
    

    Set<V> childrenVertices(V vertex);

    Set<V> parentsVertices(V vertex);

    Set<V> relatedVertices(V vertex);


    Set<V> reachableVertices(V vertex);

    Set<V> reachableFrom(V vertex);

    Set<V> reachableTo(V vertex);

    boolean isStronglyConnected();

    boolean isWeaklyConnected();

    boolean hasCycle();
    
    boolean hasCircuit();

    Optional<Set<Pair<V>>> spanningTree();

}