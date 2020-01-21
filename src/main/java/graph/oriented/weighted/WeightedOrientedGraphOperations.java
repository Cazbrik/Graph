package graph.oriented.weighted;

import java.util.Comparator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import graph.oriented.unweighted.UnweightedOrientedGraphOperations;


public class WeightedOrientedGraphOperations<V, C> {

    private UnweightedOrientedGraphOperations<V> operator;
    
    public WeightedOrientedGraphOperations(Function<Object, Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents, BiFunction<V, V, Set<V>> cost, Comparator<C> comparator){
    	// Use the unweighted one because it is too much pain in the a*s to rewrite everything
        this.operator = new UnweightedOrientedGraphOperations<>(vertices, children, parents);
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

    public boolean isStronglyConnected(Object graph){
        return this.operator.isStronglyConnected(graph);
    }

    public boolean isWeaklyConnected(Object graph){
        return this.operator.isWeaklyConnected(graph);
    }

    public boolean hasCycle(Object graph){
        return this.operator.hasCycle(graph);
    }
    
    public boolean hasCircuit(Object graph){
        return this.operator.hasCircuit(graph);
    }

}