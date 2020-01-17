package graph.oriented.unweighted;

import java.util.Set;
import java.util.function.Supplier;

public class HasCircuit<V> {

    private Supplier<Set<V>> vertices;
    private ReachableFrom<V> reachableFrom;

    public HasCircuit(Supplier<Set<V>> vertices, ReachableFrom<V> reachableFrom){
        this.vertices = vertices;
        this.reachableFrom = reachableFrom;
    }

    public boolean check(){
        return !this.vertices.get().stream().noneMatch(v -> this.reachableFrom.get(v).contains(v));
    }

}