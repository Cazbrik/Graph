package graph.nonoriented.unweighted;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import utils.Pair;

public class SpanningTree<V> {

    private Function<V, Set<V>> related;
    private Supplier<Set<V>> vertices;

    public SpanningTree(Supplier<Set<V>> vertices, Function<V, Set<V>> related){
        this.related = related;
        this.vertices = vertices;
    }

    public Optional<Set<Pair<V>>> get(){
        return Optional.empty();
    }
    
}