package graph;

import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class OrientedGraph<V, E> {

    /** 
    * No hypothesis can be made on the representation of the edges or vertices
    * So we can not implement theses operations at this level of abstaction  
    */

    /**
    * Edge & Vertex related operation that can not be implemented
    */

    private Function<E, V> start, end;

    public OrientedGraph(Function<E, V> start, Function<E, V> end){
        this.start= start;
        this.end = end;
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
    * Graph related operations that can be implemented
    * Because theses do not depend on graph representation
    */

    public Set<V> childrenVertices(V vertex){
        return this.relatedEdges(vertex).stream().filter(x -> this.start.apply(x).equals(vertex)).map(x -> this.end.apply(x)).collect(Collectors.toSet());
    }

    public Collection<V> parentsVertices(V vertex){
        return this.relatedEdges(vertex).stream().filter(x -> this.end.apply(x).equals(vertex)).map(x -> this.start.apply(x)).collect(Collectors.toSet());
    }

    private void reachableRec(V vertex, Set<V> reached){
        for(V v : this.childrenVertices(vertex)) 
            if(reached.add(v)) reachableRec(v, reached);
    }

    public Set<V> reachableFrom(V vertex){
        Set<V> reached = new HashSet<>();
        this.reachableRec(vertex, reached);
        return reached;
    }

    private void reachableToRec(V vertex, Set<V> reached){
        for(V el : this.parentsVertices(vertex)) 
            if(reached.add(el)) reachableToRec(el, reached);
    }

    public Set<V> reachableTo(V vertex){
        Set<V> reached = new HashSet<>();
        this.reachableToRec(vertex, reached);
        return reached;
    }

    public boolean isStronglyConnected(){
        
        Collection<V> vertices = this.getVertices();
        Collection<E> edges = this.getEdges();

        if(vertices.isEmpty() || vertices.size() > edges.size() + 1) return false;

        Iterator<V> iter = vertices.iterator();
        
        while(iter.hasNext()){

            Set<V> reached  = this.reachableFrom(iter.next());
            if(reached.size() != vertices.size()) continue;
            for(V vertex : reached) if(!vertices.contains(vertex)) continue;

            return true;
        }

        return false;
    }

    public boolean isWeaklyConnected(){

        Collection<V> vertices = this.getVertices();
        Collection<E> edges = this.getEdges();

        if(vertices.isEmpty() || vertices.size() > edges.size() + 1) return false;

        Iterator<V> iter = vertices.iterator();
        Set<V> reached = new HashSet<V>();

        while(iter.hasNext()){
            V vertex = iter.next();
            reached.addAll(this.reachableFrom(vertex));
            reached.addAll(this.reachableTo(vertex));
        }

        if(reached.size() != vertices.size()) return false;
        for(V v : reached) if(!vertices.contains(v)) return false;

        return true;
    }

    public boolean hasCycle(){
        return !this.getVertices().stream().noneMatch(v -> this.reachableFrom(v).contains(v));
    }

    public Set<E> spanningTree(){

        Set<E> result = new HashSet<>();
    
        if(!this.isWeaklyConnected()) return result;

        Iterator<E> iter = this.getEdges().iterator();
        while(iter.hasNext()){
            E edge = iter.next();
            if(this.start.apply(edge) == null || this.end.apply(edge) == null) continue;
            if(!result.contains(this.start.apply(edge))) result.add(edge);
            if(!result.contains(this.end.apply(edge))) result.add(edge);
        }

        return result;

    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        this.getEdges().forEach(e -> builder.append(e.toString()).append('\n'));
        return builder.toString();
    }
    
}
