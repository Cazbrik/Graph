package graph;

public class Graph<V, E> {

    private OrientedGraph<V, E> graph;

    public Graph(Function<E, V> start, Function<E, V> end){
        this.graph = new OrientedGraph<V, E>(start, end){
            
        }
    }

    abstract public void addVertex(V vertex);

    abstract public void removeVertex(V vertex);

    abstract public Collection<V> getVertices();

    abstract public void addEdge(E edge);

    abstract public void removeEdge(E edge);

    abstract public Collection<E> getEdges();

    abstract public void setEdges(List<E> edges);

    abstract public Collection<E> relatedEdges(V vertex);

}