package graph;

import java.util.Set;
import java.util.function.Function;

public class CyclePredicate<V> {

    private ReachableVerticesAccessor<V> accessor;
    private Function<Object, Set<V>> vertices;

    public CyclePredicate(Function<Object, Set<V>> vertices, Function<V, Set<V>> related){
        this(vertices, new ReachableVerticesAccessor<>(related));
    }

    public CyclePredicate(Function<Object, Set<V>> vertices, ReachableVerticesAccessor<V> accessor){
        this.vertices = vertices;
        this.accessor = accessor;
    }

    public boolean test(Object graph) {
        return !this.vertices.apply(graph).stream().noneMatch(v -> this.accessor.get(v).contains(v));
    }

}