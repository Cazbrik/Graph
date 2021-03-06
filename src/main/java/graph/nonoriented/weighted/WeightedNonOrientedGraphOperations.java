package graph.nonoriented.weighted;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import graph.ConnectionPredicate;
import graph.CyclePredicate;
import graph.ReachableVerticesAccessor;
import graph.SpanningTreeFactory;
import utils.Pair;

/** Not finished */
public class WeightedNonOrientedGraphOperations<V, C>  {

    private CyclePredicate<V> hasCycle;
    private ConnectionPredicate<V> isConnected;
    private ReachableVerticesAccessor<V> reachable;
    private SpanningTreeFactory<V> spanningTree;
    private ShortestPathFactory<V, C> shortestPath;
    private MinSpanningTreeFactory<V, C> minSpanningTree;

    public WeightedNonOrientedGraphOperations(Function<Object, Set<V>> vertices, Function<V, Set<V>> related, BiFunction<V, V, C> cost, Comparator<C> comparator){
        this.hasCycle = new CyclePredicate<>(vertices, related);
        this.isConnected = new ConnectionPredicate<>(vertices, related);
        this.reachable = new ReachableVerticesAccessor<>(related);
        this.spanningTree = new SpanningTreeFactory<>(vertices, related);
        this.shortestPath = new ShortestPathFactory<>(cost, comparator);
        this.minSpanningTree = new MinSpanningTreeFactory<>(cost, comparator);
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

    public Set<Pair<V>> minSpanningTree(Object graph){
        return this.minSpanningTree.create(graph);
    }

    public List<V> shortestPath(V start, V end){
        return this.shortestPath.create(start, end);
    }

}