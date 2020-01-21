package graph.oriented;

import java.util.Set;
import java.util.function.Function;

import graph.ReachableVerticesAccessor;

public class StrongConnectionPredicate<V> {

    private Function<Object, Set<V>> vertices;
    private ReachableVerticesAccessor<V> reachableFrom;

    public StrongConnectionPredicate(Function<Object, Set<V>> vertices, ReachableVerticesAccessor<V> reachableFrom){
        this.vertices = vertices;
        this.reachableFrom = reachableFrom;
    }
    
    public boolean test(Object graph) {

        Set<V> vertices = this.vertices.apply(graph);

        if(vertices.isEmpty()) return false;

        for(V v : vertices) {
            Set<V> reached  = this.reachableFrom.get(v);
            reached.add(v);
            if(reached == null || vertices == null || reached.size() != vertices.size()) return false;
            if(reached.containsAll(vertices)) return true;
        }

        return false;
    }
    
}