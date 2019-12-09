package graph;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.Function;

public class NonOrientedGraphOperations<V> {

    public Supplier<Set<V>> vertices;
    public Function<V, Set<V>> related;
    
    public NonOrientedGraphOperations(Supplier<Set<V>> vertices, Function<V, Set<V>> related){
        this.vertices = vertices;
        this.related = related;
    }

    public Set<V> reachable(V vertex) {
        Set<V> reached = new HashSet<>();
        this.reachableRec(vertex, reached);
        return reached;
    }

    public boolean isConnected(){
        Set<V> allVertices = this.vertices.get();
        if(allVertices == null || allVertices.isEmpty()) return false;
        Set<V> reachedVertices = this.reachable(allVertices.iterator().next());  
        return (reachedVertices == null || allVertices == null || reachedVertices.size() != allVertices.size()) ? false : reachedVertices.containsAll(allVertices);
    }

    public boolean hasCycle(){
        return !this.vertices.get().stream().noneMatch(v -> this.reachable(v).contains(v));
    }

    public Optional<Set<Pair<V>>> spanningTree(){
        return Optional.empty();
    }

    private void reachableRec(V vertex, Set<V> reached){
        Set<V> neighbors = this.related.apply(vertex);
        for(V v : neighbors){
            if(!reached.contains(v)){
                reached.add(v);
                this.reachableRec(v, reached);
            }
        }
    }

}