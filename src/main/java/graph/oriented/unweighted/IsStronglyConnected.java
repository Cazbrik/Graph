package graph.oriented.unweighted;

import java.util.Set;
import java.util.function.Supplier;

public class IsStronglyConnected<V> {

    private Supplier<Set<V>> vertices;
    private ReachableFrom<V> reachableFrom;

    public IsStronglyConnected(Supplier<Set<V>> vertices, ReachableFrom<V> reachableFrom){
        this.vertices = vertices;
        this.reachableFrom = reachableFrom;
    }
    
    public boolean check() {

        Set<V> vertices = this.vertices.get();

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