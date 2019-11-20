package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.lang.StringBuilder;

public class Graph<T, U> {

    private Set<T> vertices; // no duplicate
    private List<Edge<T, U>> edges;

    public Graph(){
        this(new HashSet<>(), new ArrayList<>());
    }

    public Graph(Set<T> vertices, List<Edge<T, U>> edges){
        this.vertices = vertices;
        this.edges = edges;
    }

    public Set<T> getVertices(){
        return this.vertices;
    }

    public List<Edge<T, U>> getEdges(){
        return this.edges;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Edge<T, U> edge : this.edges)
            builder.append(edge.toString()).append('\n');
        return builder.toString();
    }

    /* Operation on vertices */

    public boolean add(T vertex){
        return this.vertices.add(vertex);
    }

    public boolean remove(T vertex){

        Iterator<Edge<T, U>> iter = this.edges.iterator();
        while(iter.hasNext()){
            Edge<T, U> element = iter.next();
            if(element.contains(vertex)) iter.remove();
        }

        return this.vertices.remove(vertex);

    }

    public boolean replace(T old, T newOne){
        
        if(!this.vertices.contains(old)) return false;
        
        for(Edge<T, U> edge : this.edges) edge.change(old, newOne);
        this.vertices.remove(old);
        this.vertices.add(newOne);
        return true;

    }

    public boolean merge(T v1, T v2, BiFunction<T, T, T> merge){

        if(v1 == null || v2 == null || merge == null) return false;
        if(!this.vertices.contains(v1) || !this.vertices.contains(v2)) return false;

        T newOne = merge.apply(v1, v2);
        return this.replace(v1, newOne) && this.replace(v2, newOne);

    }

    /* Operation on edges */

    public boolean add(Edge<T, U> edge){
        if(!this.vertices.contains(edge.getEnd()) || !this.vertices.contains(edge.getStart())) return false;
        return this.edges.add(edge);
    }

    public boolean remove(Edge<T, U> edge){
        return this.edges.remove(edge);
    }

    /* Operation on graph */

    /**
     * The browseFunc should return null when the all the graph was covered
     */
    public void browse(T start, BiFunction<T, List<T>, T> browseFunc){
        while(start != null) start = browseFunc.apply(start, this.getRelatedVertex(start));
    }

    public List<Edge<T, U>> getRelatedEdge(T vertex){
        List<Edge<T, U>> related = new ArrayList<>();
        for(Edge<T, U> edge : this.edges) 
            if(edge.contains(vertex)) related.add(edge);
        return related;
    }

    public List<T> getRelatedVertex(T vertex){
        List<T> related = new ArrayList<>();
        for(Edge<T, U> edge : this.getRelatedEdge(vertex))
            if(edge.getStart().equals(vertex)) related.add(edge.getEnd());
        return related;
    }

    private void reachableRec(T vertex, Set<T> reached){
        for(T el : this.getRelatedVertex(vertex)){
            if(reached.add(el)){
                reachableRec(el, reached);
            }
        } 
    }

    public Set<T> reachableFrom(T vertex){
        Set<T> reached = new HashSet<>();
        this.reachableRec(vertex, reached);
        return reached;
    }

}
