package graph;

import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Objects;
import java.util.Comparator;

public abstract class WeightedOrientedGraph<V, E, C> extends OrientedGraph<V, E> {

    /** 
    * No hypothesis can be made on the representation of the edges or vertices
    * So we can not implement theses operations at this level of abstaction  
    */

    /**
    * Cost related operation that can not be implemented
    */

    private Function<E, C> cost;
    private Comparator<C> comparator;

    public WeightedOrientedGraph(Function<E, V> start, Function<E, V> end, Function<E, C> cost, Comparator<C> comparator){
        super(start, end);
        this.cost = Objects.requireNonNull(cost, "Cost operation must not be null");
        this.comparator = Objects.requireNonNull(comparator, "Comparator operation must not be null");
    }

    /**
    * WeigthedGraph related operations that can be implemented
    * Because theses do not depend on graph representation
    */

    public Optional<Set<E>> minSpanningTree(){

        if(!this.isWeaklyConnected()) return Optional.empty();

        Set<E> result = new HashSet<>();
        List<E> originalEdges = new ArrayList<>(this.getEdges());
        Iterator<E> iter = new ArrayList<>(originalEdges).iterator();
        while(iter.hasNext()) this.removeEdge(iter.next());

        originalEdges.sort((e1, e2) -> { return this.comparator.compare(this.cost.apply(e1), this.cost.apply(e2));});

        int counter = 0;
        iter = originalEdges.iterator();
        while(iter.hasNext()){
            E edge = iter.next();
            this.addEdge(edge);
            if(this.hasCycle()) {
                this.removeEdge(edge);
            } else {
                counter++;
                result.add(edge);
            }
        }

        this.setEdges(originalEdges);

        return Optional.of(result);

    }

}