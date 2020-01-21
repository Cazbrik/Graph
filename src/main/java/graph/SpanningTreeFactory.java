package graph;

import java.util.Set;
import java.util.function.Function;

import utils.Pair;

public class SpanningTreeFactory<V> {

    private Function<V, Set<V>> related;
    private Function<Object, Set<V>> vertices;

    public SpanningTreeFactory(Function<Object, Set<V>> vertices, Function<V, Set<V>> related){
        this.related = related;
        this.vertices = vertices;
    }

    public Set<Pair<V>> create(Object graph){
        throw new RuntimeException("not implemented");
    }
    
}