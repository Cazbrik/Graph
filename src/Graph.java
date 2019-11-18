package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Graph<T> {

    private Set<T> vertices; // no duplicate
    private List<Edge<T>> edges;

    public Graph(){
        this(new HashSet<>(), new ArrayList<>());
    }

    public Graph(Set<T> vertices, List<Edge<T>> edges){
        this.vertices = vertices;
        this.edges = edges;
    }

    /* Operation on vertices */

    public boolean add(T vertex){
        return this.vertices.add(vertex);
    }

    public boolean remove(T vertex){

        Iterator<Edge<T>> iter = this.edges.iterator();
        while(iter.hasNext()){
            Edge<T> element = iter.next();
            if(element.contains(vertex)) this.edges.remove(element);
        }

        return this.vertices.remove(vertex);

    }

    public boolean replace(T old, T newOne){
        
        if(!this.vertices.contains(old) || this.vertices.contains(newOne)) return false;
        
        for(Edge<T> edge : this.edges) edge.change(old, newOne);
        this.vertices.remove(old);
        return this.vertices.add(newOne);

    }

    public boolean merge(T v1, T v2, BiFunction<T, T, T> merge){

        if(v1 == null || v2 == null || merge == null) return false;
        if(!this.vertices.contains(v1) || !this.vertices.contains(v2)) return false;

        T newOne = merge.apply(v1, v2);
        return this.replace(v1, newOne) && this.replace(v2, newOne);

    }

    public List<Edge<T>> getRelated(T vertex){
        List<Edge<T>> related = new ArrayList<>();
        for(Edge<T> edge : this.edges) 
            if(edge.contains(vertex)) related.add(edge);
        return related;
    }

    /* Operation on edges */

    public boolean add(Edge<T> edge){
        return this.edges.add(edge);
    }

    public boolean remove(Edge<T> edge){
        return this.edges.remove(edge);
    }

    /* Operation on graph */

    /**
     * The browseFunc should return null when the all the graph was covered
     */
    public void browse(T start, BiFunction<T, List<Edge<T>>, T> browseFunc){
        while(start != null) start = browseFunc.apply(start, this.getRelated(start));
    }

}
