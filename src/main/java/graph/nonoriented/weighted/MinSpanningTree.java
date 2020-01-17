package graph.nonoriented.weighted;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import utils.Pair;

public class MinSpanningTree<V, C> {

    private BiFunction<V, V, Set<V>> cost;
    private Comparator<C> comparator;

    public MinSpanningTree(BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
        this.comparator = comparator;
        this.cost = cost;
    }

    public Optional<Set<Pair<V>>> get(){
        return Optional.empty();
    }

}