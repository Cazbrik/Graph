package impl;

import lib.Pair;
import lib.WeightedGraph;
import lib.WeightedOrientedGraph;

import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Comparator;

public class SimpleWeightedGraph<V, C> implements WeightedGraph<V, C>, WeightedOrientedGraph<V, C> {

    private SimpleGraph<V> graph;
    private BiFunction<V, V, C> cost;
    private Comparator<C> comparator;

    public SimpleWeightedGraph(Supplier<Set<V>> vertices, Function<V, Set<V>> related, BiFunction<V, V, C> cost, Comparator<C> comparator) {
        this(vertices, related, v -> new HashSet<>(), cost, comparator);
    }

    public SimpleWeightedGraph(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents, BiFunction<V, V, C> cost, Comparator<C> comparator) {
        this(new SimpleGraph<V>(vertices, children, parents), cost, comparator);
    }

    public SimpleWeightedGraph(SimpleGraph<V> graph, BiFunction<V, V, C> cost, Comparator<C> comparator) {
        this.graph = Objects.requireNonNull(graph, "Graph operation can not be null !");
        this.cost = Objects.requireNonNull(cost, "Cost operation can not be null !");
        this.comparator = Objects.requireNonNull(comparator, "Comparator operation can not be null !");
    }

    public Set<V> childrenVertices(V vertex){
        return this.graph.childrenVertices(vertex);
    }

    public Set<V> parentsVertices(V vertex){
        return this.graph.parentsVertices(vertex);
    }

    public Set<V> relatedVertices(V vertex) {
        return this.graph.relatedVertices(vertex);
    }

    public Set<V> reachableFrom(V vertex){
        return this.graph.reachableFrom(vertex);
    }

    public Set<V> reachableTo(V vertex){
        return this.graph.reachableTo(vertex);
    }

    public Set<V> reachableVertices(V vertex) {
        return this.graph.reachableVertices(vertex);
    }

    public boolean isConnected() {
        return this.graph.isConnected();
    }

    public boolean isStronglyConnected(){
        return this.graph.isStronglyConnected();
    }

    public boolean isWeaklyConnected(){
        return this.graph.isWeaklyConnected();
    }

    public boolean hasCycle() {
        return this.graph.hasCycle();
    }
    
    public boolean hasCircuit(){
        return this.graph.hasCircuit();
    }

    public Optional<Set<Pair<V>>> spanningTree() {
        return this.graph.spanningTree();
    }

    public C getCost(V v1, V v2){
        return this.cost.apply(v1, v2);
    }

    public int compare(C c1, C c2) {
        return this.comparator.compare(c1, c2);
    }

    /**
    * Not implemented yet
    */

    public Optional<Set<Pair<V>>> minSpanningTree(){
        return Optional.empty();
    }

    public Optional<Set<V>> shortestPath(){
        return Optional.empty();
    }

}