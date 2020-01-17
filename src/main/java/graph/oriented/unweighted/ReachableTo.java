package graph.oriented.unweighted;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ReachableTo<V> {

    private Function<V, Set<V>> parents;

    public ReachableTo(Function<V, Set<V>> parents) {
        this.parents = parents;
    }

    public Set<V> get(V vertex){
        Set<V> reached = new HashSet<>();
        this.getToRec(vertex, reached);
        return reached;
    }

    private void getToRec(V vertex, Set<V> reached){
        for(V el : this.parents.apply(vertex)) 
            if(reached.add(el)) getToRec(el, reached);
    }

}