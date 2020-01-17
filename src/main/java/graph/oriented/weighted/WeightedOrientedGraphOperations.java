package graph.oriented.weighted;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import graph.oriented.unweighted.OrientedGraphOperations;


public class WeightedOrientedGraphOperations<V, C> {

    private OrientedGraphOperations<V> operator;
    
    public WeightedOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
        this.operator = new OrientedGraphOperations<>(vertices, children, parents);
    }

    public Set<V> reachable(V vertex){
        return this.operator.reachable(vertex);
    }

    public Set<V> reachableFrom(V vertex){
        return this.operator.reachableFrom(vertex);
    }

    public Set<V> reachableTo(V vertex){
        return this.operator.reachableTo(vertex);
    }

    public boolean isStronglyConnected(){
        return this.operator.isStronglyConnected();
    }

    public boolean isWeaklyConnected(){
        return this.operator.isWeaklyConnected();
    }

    public boolean hasCycle(){
        return this.operator.hasCycle();
    }
    
    public boolean hasCircuit(){
        return this.operator.hasCircuit();
    }

}