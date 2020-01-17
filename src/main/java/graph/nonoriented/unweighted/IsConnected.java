package graph.nonoriented.unweighted;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class IsConnected<V> {

    private Reachable<V> reachable;
    private Supplier<Set<V>> vertices;

    public IsConnected(Supplier<Set<V>> vertices, Function<V, Set<V>> related){
        this(vertices, new Reachable<>(related));
    }

    public IsConnected(Supplier<Set<V>> vertices, Reachable<V> reachable){
        this.reachable = reachable;
        this.vertices= vertices;
    }

    public boolean check(){
        Set<V> allVertices = this.vertices.get();
        if(allVertices == null || allVertices.isEmpty()) return false;
        Set<V> reachedVertices = this.reachable.get(allVertices.iterator().next());  
        return (reachedVertices == null || allVertices == null || reachedVertices.size() != allVertices.size()) ? false : reachedVertices.containsAll(allVertices);
    }

}