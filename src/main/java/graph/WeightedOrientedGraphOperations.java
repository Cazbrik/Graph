package graph;

import java.util.Set;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.Comparator;

/** Not finished */
public class WeightedOrientedGraphOperations<V, C> {

    private BiFunction<V, V, Set<V>> cost;
    private Comparator<C> comparator;
    
    public WeightedOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
        
    }

    public Optional<Set<Pair<V>>> minSpanningTree(){
        return Optional.empty();
    }

    public Optional<Set<V>> shortestPath(){
        return Optional.empty();
    }

}