package graph.nonoriented.unweighted;

import java.util.Set;
import java.util.function.Function;

import graph.ConnectionPredicate;
import graph.CyclePredicate;
import graph.ReachableVerticesAccessor;
import graph.SpanningTreeFactory;
import utils.Pair;

public class UnweightedNonOrientedGraphOperations<V> {

    private CyclePredicate<V> hasCycle;
    private ConnectionPredicate<V> isConnected;
    private ReachableVerticesAccessor<V> reachable;
    private SpanningTreeFactory<V> spanningTree;

    public UnweightedNonOrientedGraphOperations(Function<Object, Set<V>> vertices, Function<V, Set<V>> related){
        this.hasCycle = new CyclePredicate<>(vertices, related);
        this.isConnected = new ConnectionPredicate<>(vertices, related);
        this.reachable = new ReachableVerticesAccessor<>(related);
        this.spanningTree = new SpanningTreeFactory<>(vertices, related);
    }

    public Set<V> reachable(V vertex) {
        return this.reachable.get(vertex);
    }

    public boolean isConnected(Object graph){
        return this.isConnected.test(graph);
    }

    public boolean hasCycle(Object graph){
        return this.hasCycle.test(graph);
    }

	public Set<Pair<V>> spanningTree(Object graph) {
		return this.spanningTree.create(graph);
	}


  
}