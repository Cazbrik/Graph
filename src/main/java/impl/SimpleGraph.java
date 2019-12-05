package impl;

import lib.Pair;
import lib.NonOrientedGraph;
import lib.OrientedGraph;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.Objects;

public class SimpleGraph<V> implements NonOrientedGraph<V>, OrientedGraph<V> {

    private Function<V, Set<V>> children;
    private Function<V, Set<V>> parents;
    private Supplier<Set<V>> vertices;

    /**
    * For Non oriented graph
    */
    public SimpleGraph(Supplier<Set<V>> vertices, Function<V, Set<V>> related) {
        this(vertices, related, v -> new HashSet<>());
    }

    /**
    * For oriented graph
    */
    public SimpleGraph(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents) {
        this.children = Objects.requireNonNull(children, "Children operation can not be null !");
        this.parents = Objects.requireNonNull(parents, "Parents operation can not be null !");
        this.vertices = Objects.requireNonNull(vertices, "Vertices operation can not be null !");
    }

    public Set<V> childrenVertices(V vertex){
        return this.children.apply(vertex);
    }

    public Set<V> parentsVertices(V vertex){
        return this.parents.apply(vertex);
    }

    public Set<V> relatedVertices(V vertex){
        Set<V> reached = this.childrenVertices(vertex);
        reached.addAll(this.parentsVertices(vertex));
        return reached;
    }

    public Set<V> reachableFrom(V vertex){
        Set<V> reached = new HashSet<>();
        this.reachableFromRec(vertex, reached);
        return reached;
    }

    public Set<V> reachableTo(V vertex){
        Set<V> reached = new HashSet<>();
        this.reachableToRec(vertex, reached);
        return reached;
    }

    public Set<V> reachableVertices(V vertex){
        Set<V> reached = this.reachableFrom(vertex);
        reached.addAll(this.reachableTo(vertex));
        return reached;
    }
    
    public boolean isConnected(){
        return this.isWeaklyConnected();
    }

    public boolean isStronglyConnected(){

        Set<V> vertices = this.vertices.get();

        if(vertices.isEmpty()) return false;

        for(V v : vertices) {
            Set<V> reached  = this.reachableFrom(v);
            reached.add(v);
            if(reached.size() != vertices.size()) continue;
            for(V vertex : reached) if(!vertices.contains(vertex)) continue;

            return true;
        }

        return false;
        
    }

    public boolean isWeaklyConnected(){
        
        Set<V> vertices = this.vertices.get();

        if(vertices.isEmpty()) return false;

        for(V v : vertices) {
            Set<V> reached  = new HashSet<>();
            this.weaklyConnectedRec(v, reached);
            reached.add(v);
            if(reached.size() != vertices.size()) continue;
            for(V vertex : reached) if(!vertices.contains(vertex)) continue;

            return true;
        }

        return false;
      
    }

    public boolean hasCycle() {
        return !this.vertices.get().stream().noneMatch(v -> this.reachableVertices(v).contains(v));
    }
    
    public boolean hasCircuit(){
        return !this.vertices.get().stream().noneMatch(v -> this.reachableFrom(v).contains(v));
    }

    private void reachableFromRec(V vertex, Set<V> reached){
        for(V v : this.childrenVertices(vertex)) 
            if(reached.add(v)) reachableFromRec(v, reached);
    }

    private void reachableToRec(V vertex, Set<V> reached){
        for(V el : this.parentsVertices(vertex)) 
            if(reached.add(el)) reachableToRec(el, reached);
    }

    private void weaklyConnectedRec(V vertex, Set<V> reached){ // to test
        Set<V> related = this.relatedVertices(vertex);
        if(reached.containsAll(related)) return;
        reached.addAll(related);
        for(V v : related) this.weaklyConnectedRec(v, reached);
    }

    /**
    * Not Implemented yet
    */

    public Optional<Set<Pair<V>>> spanningTree(){
        return Optional.empty();
    }

}