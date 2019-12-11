package graph;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

public class OrientedGraphOperations<V> {

    private Function<V, Set<V>> children, parents;
    private NonOrientedGraphOperations<V> operator;

    public OrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> children, Function<V, Set<V>> parents){
        this(new NonOrientedGraphOperations<>(vertices, x -> {
            Set<V> related = children.apply(x);
            related.addAll(parents.apply(x));
            return related;
        }), children, parents);
    }

    public OrientedGraphOperations(NonOrientedGraphOperations<V> operator, Function<V, Set<V>> children, Function<V, Set<V>> parents){
        this.parents = parents;
        this.children = children;
        this.operator = operator;
    }

    public Set<V> related(V vertex){
        return this.operator.related.apply(vertex);
    }

    public Set<V> reachable(V vertex){
        return this.operator.reachable(vertex);
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

    public boolean isStronglyConnected(){

        Set<V> vertices = this.operator.vertices.get();

        if(vertices.isEmpty()) return false;

        for(V v : vertices) {
            Set<V> reached  = this.reachableFrom(v);
            reached.add(v);
            if(reached == null || vertices == null || reached.size() != vertices.size()) return false;
            if(reached.containsAll(vertices)) return true;
        }

        return false;
    }

    public boolean isWeaklyConnected(){
        return this.operator.isConnected();
    }

    public boolean hasCycle(){
        return this.operator.hasCycle();
    }
    
    public boolean hasCircuit(){
        return !this.operator.vertices.get().stream().noneMatch(v -> this.reachableFrom(v).contains(v));
    }

    public Optional<Set<Pair<V>>> spanningTree(){
        return Optional.empty();
    }

    /** Private method */

    private void reachableFromRec(V vertex, Set<V> reached){
        for(V v : this.children.apply(vertex)) 
            if(reached.add(v)) reachableFromRec(v, reached);
    }

    private void reachableToRec(V vertex, Set<V> reached){
        for(V el : this.parents.apply(vertex)) 
            if(reached.add(el)) reachableToRec(el, reached);
    }

}