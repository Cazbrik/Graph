package graph.nonoriented.weighted;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiFunction;

import utils.Pair;

public class MinSpanningTreeFactory<V, C> {

    private BiFunction<V, V, C> cost;
    private Comparator<C> comparator;

    public MinSpanningTreeFactory(BiFunction<V, V, C> cost, Comparator<C> comparator){
        this.comparator = comparator;
        this.cost = cost;
    }

    public Set<Pair<V>> create(Object graph){
        throw new RuntimeException("not implemented");
    }

}