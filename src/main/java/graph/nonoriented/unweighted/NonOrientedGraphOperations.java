package graph.nonoriented.unweighted;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import utils.Pair;

import java.util.function.Function;

public class NonOrientedGraphOperations<V> {

    private HasCycle<V> hasCycle;
    private IsConnected<V> isConnected;
    private Reachable<V> reachable;
    private SpanningTree<V> spanningTree;

    public NonOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> related){
        this.hasCycle = new HasCycle<>(vertices, related);
        this.isConnected = new IsConnected<>(vertices, related);
        this.reachable = new Reachable<>(related);
        this.spanningTree = new SpanningTree<>(vertices, related);
    }

    public Set<V> reachable(V vertex) {
        return this.reachable.get(vertex);
    }

    public boolean isConnected(){
        return this.isConnected.check();
    }

    public boolean hasCycle(){
        return this.hasCycle.check();
    }

	public Optional<Set<Pair<V>>> spanningTree() {
		return this.spanningTree.get();
	}


  
}