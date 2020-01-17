package graph.nonoriented.unweighted;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class HasCycle<V> {

    private Reachable<V> reachable;
    private Supplier<Set<V>> vertices;

    public HasCycle(Supplier<Set<V>> vertices, Function<V, Set<V>> related){
        this(vertices, new Reachable<>(related));
    }

    public HasCycle(Supplier<Set<V>> vertices, Reachable<V> reachable){
        this.vertices = vertices;
        this.reachable = reachable;
    }

    public boolean check() {
        return !this.vertices.get().stream().noneMatch(v -> this.reachable.get(v).contains(v));
    }

}