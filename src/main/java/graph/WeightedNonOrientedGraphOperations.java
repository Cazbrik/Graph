package graph;

import java.util.Set;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Comparator;

/** Not finished */
public class WeightedNonOrientedGraphOperations<V, C>  {

    public NonOrientedGraphOperations<V> operator;
    public BiFunction<V, V, Set<V>> cost;
    public Comparator<C> comparator;
    
    public WeightedNonOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> related, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
        this.operator = new NonOrientedGraphOperations<>(vertices, related);
        this.cost = cost;
        this.comparator = comparator;
    }

    public Set<V> reachable(V vertex) {
        return this.operator.reachable(vertex);
    }

    public boolean isConnected(){
        return this.operator.isConnected();    
    }

    public boolean hasCycle(){
        return this.operator.hasCycle();
    }

    public Optional<Set<Pair<V>>> spanningTree(){
        return this.operator.spanningTree();
    }



    public Optional<Set<Pair<V>>> minSpanningTree(){
        return Optional.empty();
    }

    public Optional<Set<V>> shortestPath(){
        return Optional.empty();
    }

}