package graph;

import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class ReachableVerticesAccessor<V> {

    private Function<V, Set<V>> related;

    public ReachableVerticesAccessor(Function<V, Set<V>> related){
        this.related = related;
    }

    public Set<V> get(V vertex) {
        Set<V> reached = new HashSet<>();
        this.getRec(vertex, reached);
        return reached;
    }

    private void getRec(V vertex, Set<V> reached){
        Set<V> neighbors = this.related.apply(vertex);
        for(V v : neighbors){
            if(!reached.contains(v)){
                reached.add(v);
                this.getRec(v, reached);
            }
        }
    }

}