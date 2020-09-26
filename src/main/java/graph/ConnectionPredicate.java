package graph;

import java.util.Set;
import java.util.function.Function;

public class ConnectionPredicate<V> {

    private ReachableVerticesAccessor<V> reachable;
    private Function<Object, Set<V>> vertices;

    public ConnectionPredicate(Function<Object, Set<V>> vertices, Function<V, Set<V>> related){
        this(vertices, new ReachableVerticesAccessor<>(related));
    }

    public ConnectionPredicate(Function<Object, Set<V>> vertices, ReachableVerticesAccessor<V> reachable){
        this.reachable = reachable;
        this.vertices= vertices;
    }

    public boolean test(Object graph){
        Set<V> allVertices = this.vertices.apply(graph);
        if(allVertices == null || allVertices.isEmpty()) return false;
        Set<V> reachedVertices = this.reachable.get(allVertices.iterator().next());  
        return (reachedVertices == null || allVertices == null || reachedVertices.size() != allVertices.size()) ? false : reachedVertices.containsAll(allVertices);
    }

}