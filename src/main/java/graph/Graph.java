package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.Comparator;


public class Graph<T, U> {

    private Set<T> vertices; // no duplicate
    private List<Edge<T, U>> edges;

    public Graph(){
        this(new HashSet<>());
    }

    public Graph(Set<T> vertices){
        this(vertices, new ArrayList<>());
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
        this.edges.forEach(e -> builder.append(e.toString()).append('\n'));
        return builder.toString();
    }

    /* Operation on vertices */

    public boolean add(T vertex){
        return this.vertices.add(vertex);
    }

    public boolean remove(T vertex){
        List<Edge<T, U>> toRemove = this.edges.stream().filter(x -> x.contains(vertex)).collect(Collectors.toList());
        this.edges.removeAll(toRemove);
        return this.vertices.remove(vertex);
    }

    public boolean replace(T old, T newOne){
        if(!this.vertices.contains(old)) return false;
        this.edges.forEach(e -> e.change(old, newOne));
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
        while(start != null) start = browseFunc.apply(start, this.getChildrenVertices(start));
    }

    public List<Edge<T, U>> getRelatedEdge(T vertex){
        return this.selectEdges(edge -> edge.contains(vertex));
    }

    public List<T> getChildrenVertices(T vertex){
        return this.getRelatedEdge(vertex).stream().filter(x -> x.getStart().equals(vertex)).map(x -> x.getEnd()).collect(Collectors.toList());
    }

    public List<T> getParentVertices(T vertex){
        return this.getRelatedEdge(vertex).stream().filter(x -> x.getEnd().equals(vertex)).map(x -> x.getStart()).collect(Collectors.toList());
    }

    private void reachableRec(T vertex, Set<T> reached){
        for(T el : this.getChildrenVertices(vertex)) 
            if(reached.add(el)) reachableRec(el, reached);
    }

    public Set<T> reachableFrom(T vertex){
        Set<T> reached = new HashSet<>();
        this.reachableRec(vertex, reached);
        return reached;
    }

    private void reachableToRec(T vertex, Set<T> reached){
        for(T el : this.getParentVertices(vertex)) 
            if(reached.add(el)) reachableToRec(el, reached);
    }

    public Set<T> reachableTo(T vertex){
        Set<T> reached = new HashSet<>();
        this.reachableToRec(vertex, reached);
        return reached;
    }

    public boolean isStronglyConnected(){

        if(this.vertices.isEmpty() || this.vertices.size() > this.edges.size() + 1) return false;
        Iterator<T> iter = this.vertices.iterator();
        
        while(iter.hasNext()){

            Set<T> reachVertices  = this.reachableFrom(iter.next());
            if(reachVertices.size() != this.vertices.size()) continue;
            for(T vertex : reachVertices) if(!this.vertices.contains(vertex)) continue;

            return true;
        }

        return false;

    }

    public boolean isWeaklyConnected(){

        if(this.vertices.isEmpty() || this.vertices.size() > this.edges.size() + 1) return false;

        Set<T> reached = new HashSet<>();

        Iterator<T> iter = this.vertices.iterator();

        while(iter.hasNext()){
            T vertex = iter.next();
            reached.addAll(this.reachableFrom(vertex));
            reached.addAll(this.reachableTo(vertex));
        }

        if(reached.size() != this.vertices.size()) return false;
        for(T v : reached) if(!this.vertices.contains(v)) return false;

        return true;

    }

    public List<Edge<T, U>> selectEdges(Function<Edge<T, U>, Boolean> filter){
        if(filter == null) return new ArrayList<>();
        return this.edges.stream().filter( el -> filter.apply(el)).collect(Collectors.toList());
    }

    public List<T> selectVertices(Function<T, Boolean> filter){
        if(filter == null) return new ArrayList<>();
        return this.vertices.stream().filter( el -> filter.apply(el)).collect(Collectors.toList());
    }

    public void applyOnVertices(Function<T, Boolean> filter, Consumer<T> func){
        if(func == null) return;
        for(T vertex : this.selectVertices(filter)) func.accept(vertex);
    }

    public void applyOnEdges(Function<Edge<T, U>, Boolean> filter, Consumer<Edge<T, U>> func){
        if(func == null) return;
        for(Edge<T, U> edge : this.selectEdges(filter)) func.accept(edge);
    }

    public boolean hasCycle(){
        return !this.vertices.stream().noneMatch(v -> this.reachableFrom(v).contains(v));
    }

    public Graph<T, U> minSpanningTree(Comparator<U> comparator){

        if(!this.isWeaklyConnected() || comparator == null) return null;

        Graph<T, U> minTree = new Graph<>(new HashSet<>(this.vertices));
        List<Edge<T, U>> edges = new ArrayList<>(this.edges);
        edges.sort( (e1, e2) -> {return comparator.compare(e1.getCost(), e2.getCost());});

        Iterator<Edge<T, U>> iter = edges.iterator();
        int counter = 0;

        while(iter.hasNext() && counter < this.vertices.size() - 1){
            Edge<T, U> edge = iter.next();
            if(!minTree.add(edge)) continue;
            if(minTree.hasCycle()) minTree.remove(edge);
            else counter++;
        }

        return minTree;

    }
    
}

class MyFactory {
    static FilterFactory<Edge<T>> onEdges(... edges) {
        return new FilterFactory<Edge<T>>(edges);
    }
    static FilterFactory<T> onVertices(... vertices) {
        return new FilterFactory<T>(vertices);
    }
}

class FilterFactory<T> {
    Collection<T> items;
    Filter<T> filter (Predictate<T> filter) {
        return new Filter<T>(filter);
    }
}

class Filter<T> {
    Collection<T> items;
    Predictate<T> filter;
    Collection<T> select() {
        return items.stream().filter(filter).collect(Collectors.toList());
    }
    void apply(Consumer<Edge<T, U>> func) {...}
}

f = new FilterFactory(filter)

MyFactory.onEdges(edges).filter(filter).select();

class Graph<T, E, L> {
    Set<T> vertices;
    List<E> edges;
    Function<E, T> getStart;
    int getMinCountEdges(T a, T b) {
        for(E edge: edges) {
            T v = getStart(edge);
            ...
        }
    }
    abstract L getShortestPath(T a, T b);
}
class WeightedGraph<T,U> extends Graph<T, WeightedEdge<T, U>, U> {
    Function<U, Number> costToNumber;
    U getMinWeightPath(T a, T b) {/*somme des poids des arêtes parcourues*/}
    Number getShortestPath(T a, T b) {return costToNumber.apply(getMinWeightPath(a,b));}
}
class NonWeightedGraph<T,U> extends Graph<T, Edge<T>, Integer> {
    Integer getShortestPath(T a, T b) {return getMinCountEdges(a,b);}
}