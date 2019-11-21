package graph;

public class OrientedGraph<V, E> {

    public OrientedGraph<V, E> add(T vertex);

    public OrientedGraph<V, E> remove(T vertex);

    public Collection<V> getVertices();

    public OrientedGraph<V, E> add(E edge);

    public OrientedGraph<V, E> remove(E edge);

    public Collection<E> getEdges();

    public Collection<T> childrenVertices(T vertex);

    public Collection<T> parentsVertices(T vertex);

    public Collection<T> reachableFrom(T vertex);

    public Collection<T> reachableTo(T vertex);

    public boolean isStronglyConnected();

    public boolean isWeaklyConnected();

    public boolean hasCycle();

    public OrientedGraph<V, E> spanningTree();

    @Override
    public String toString();
    
}
