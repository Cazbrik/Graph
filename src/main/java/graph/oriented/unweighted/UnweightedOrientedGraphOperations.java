package graph.oriented.unweighted;

import java.util.Set;
import java.util.function.Function;

import graph.ConnectionPredicate;
import graph.CyclePredicate;
import graph.ReachableVerticesAccessor;
import graph.SpanningTreeFactory;
import graph.oriented.StrongConnectionPredicate;
import utils.Pair;

public class UnweightedOrientedGraphOperations<V> {

    private CyclePredicate<V> hasCycle;
    private ConnectionPredicate<V> isConnected;
    private ReachableVerticesAccessor<V> reachable;
    private SpanningTreeFactory<V> spanningTree;
    private ReachableVerticesAccessor<V> reachableFrom;
    private ReachableVerticesAccessor<V> reachableTo;
    private StrongConnectionPredicate<V> isStronglyConnected;
    private CyclePredicate<V> hasCircuit;

    public UnweightedOrientedGraphOperations(Function<Object, Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents){
        Function<V, Set<V>> related = x -> { Set<V> rel = children.apply(x); rel.addAll(parents.apply(x)); return rel; };
        this.hasCycle = new CyclePredicate<>(vertices, related);
        this.isConnected = new ConnectionPredicate<>(vertices, related);
        this.reachable = new ReachableVerticesAccessor<>(related);
        this.spanningTree = new SpanningTreeFactory<>(vertices, related);
        this.reachableFrom = new ReachableVerticesAccessor<>(children);
        this.reachableTo = new ReachableVerticesAccessor<>(parents);
        this.isStronglyConnected = new StrongConnectionPredicate<>(vertices, this.reachableFrom);
        this.hasCircuit = new CyclePredicate<>(vertices, this.reachableFrom);
    }

    public Set<V> reachable(V vertex) {
        return this.reachable.get(vertex);
    }
    
    public Set<V> reachableFrom(V vertex){
        return this.reachableFrom.get(vertex);
    }

    public Set<V> reachableTo(V vertex){
        return this.reachableTo.get(vertex);
    }
    
    public boolean isStronglyConnected(Object graph){
        return isStronglyConnected.test(graph);
    }

    public boolean isWeaklyConnected(Object graph){
    	return this.isConnected.test(graph);
    }
    
    public boolean hasCycle(Object graph){
    	return this.hasCycle.test(graph);
    }
    
    public boolean hasCircuit(Object graph){
        return this.hasCircuit.test(graph);
    }
    
    public Set<Pair<V>> spanningTree(Object graph) {
    	return this.spanningTree.create(graph);
    }

}