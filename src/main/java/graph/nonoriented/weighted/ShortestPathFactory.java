package graph.nonoriented.weighted;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

public class ShortestPathFactory<V, C> {

    private BiFunction<V, V, C> cost;
    private Comparator<C> comparator;

    public ShortestPathFactory(BiFunction<V, V, C> cost, Comparator<C> comparator){
        this.comparator = comparator;
        this.cost = cost;
    }

    public List<V> create(V start, V end){
        throw new RuntimeException("not implemented");
    }

}