package graph.nonoriented.weighted;

import java.util.Set;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Comparator;

import graph.nonoriented.unweighted.NonOrientedGraphOperations;
import utils.Pair;

/** Not finished */
public class WeightedNonOrientedGraphOperations<V, C>  {

    private NonOrientedGraphOperations<V> operator;
    private ShortestPath<V, C> shortestPath;
    private MinSpanningTree<V, C> minSpanningTree;

    public WeightedNonOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> related, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
        this(new NonOrientedGraphOperations<>(vertices, related), cost, comparator);
    }

    public WeightedNonOrientedGraphOperations(NonOrientedGraphOperations<V> operator, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator) {
        this.operator = operator;
        this.shortestPath = new ShortestPath<>(cost, comparator);
        this.minSpanningTree = new MinSpanningTree<>(cost, comparator);
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
        return this.minSpanningTree.get();
    }

    public Optional<Set<Pair<V>>> shortestPath(){
        return this.shortestPath.get();
    }

}