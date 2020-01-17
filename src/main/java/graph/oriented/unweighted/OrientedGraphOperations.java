package graph.oriented.unweighted;

import java.util.function.Function;
import java.util.function.Supplier;

import graph.nonoriented.unweighted.NonOrientedGraphOperations;
import utils.Pair;

import java.util.Set;
import java.util.Optional;

public class OrientedGraphOperations<V> {

    private NonOrientedGraphOperations<V> operator;
    private ReachableFrom<V> reachableFrom;
    private ReachableTo<V> reachableTo;
    private IsStronglyConnected<V> isStronglyConnected;
    private HasCircuit<V> hasCircuit;

    public OrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents){
        Function<V, Set<V>> related = x -> { Set<V> rel = children.apply(x); rel.addAll(parents.apply(x)); return rel; };
        this.operator = new NonOrientedGraphOperations<>(vertices, related);
        this.reachableFrom = new ReachableFrom<>(children);
        this.reachableTo = new ReachableTo<>(parents);
        this.isStronglyConnected = new IsStronglyConnected<>(vertices, this.reachableFrom);
        this.hasCircuit = new HasCircuit<>(vertices, this.reachableFrom);
    }

    public Set<V> reachable(V vertex){
        return this.operator.reachable(vertex);
    }

    public Set<V> reachableFrom(V vertex){
        return this.reachableFrom.get(vertex);
    }

    public Set<V> reachableTo(V vertex){
        return this.reachableTo.get(vertex);
    }

    public boolean isStronglyConnected(){
        return isStronglyConnected.check();
    }

    public boolean isWeaklyConnected(){
        return this.operator.isConnected();
    }

    public boolean hasCycle(){
        return this.operator.hasCycle();
    }
    
    public boolean hasCircuit(){
        return this.hasCircuit.check();
    }

    public Optional<Set<Pair<V>>> spanningTree(){
        return this.operator.spanningTree();
    }

}