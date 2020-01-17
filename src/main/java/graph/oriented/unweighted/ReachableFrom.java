package graph.oriented.unweighted;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ReachableFrom<V> {

    private Function<V, Set<V>> children;

    public ReachableFrom(Function<V, Set<V>> children) {
        this.children = children;
    }

    public Set<V> get(V vertex){
        Set<V> reached = new HashSet<>();
        this.getFromRec(vertex, reached);
        return reached;
    }

    private void getFromRec(V vertex, Set<V> reached){
        for(V v : this.children.apply(vertex)) 
            if(reached.add(v)) getFromRec(v, reached);
    }

}