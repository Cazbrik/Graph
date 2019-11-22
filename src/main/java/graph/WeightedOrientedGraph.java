package graph;

import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Optional;

public abstract WeightedOrientedGraph<V, E, C> extends OrientedGraph<V, E> {

    /** 
    * No hypothesis can be made on the representation of the edges or vertices
    * So we can not implement theses operations at this level of abstaction  
    */

    /**
    * Cost related operation that can not be implemented
    */

    private Function<E, C> cost;

    public OrientedGraph(Function<E, V> start, Function<E, V> end, Function<E, C> cost){
        super(start, end);
        this.cost = cost;
    }

    /**
    * Graph related operation that can not be implemented
    */

    abstract public void addVertex(V vertex);

    abstract public void removeVertex(V vertex);

    abstract public Collection<V> getVertices();

    abstract public void addEdge(E edge);

    abstract public void removeEdge(E edge);

    abstract public Collection<E> getEdges();

    abstract public Collection<E> relatedEdges(V vertex);

    /**
    * WeigthedGraph related operations that can be implemented
    * Because theses do not depend on graph representation
    */

     public Optional<Set<E>> minSpanningTree(Comparator<U> comparator){
        return Optional.empt();
    }

}