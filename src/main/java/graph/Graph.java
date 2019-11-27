package graph;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.function.Function;

public abstract class Graph<V, E> {

    private Function<E, E> revert;
    private OrientedGraph<V, E> graph;

    public Graph(Function<E, V> start, Function<E, V> end, Function<E, E> revert){
        this.revert = revert;
        this.graph = new OrientedGraph<V, E>(start, end){

            public void addVertex(V vertex){
                Graph.this.addVertex(vertex);
            }

            public void removeVertex(V vertex){
                Graph.this.removeVertex(vertex);
            }

            public Collection<V> getVertices(){
                return Graph.this.getVertices();
            }

            public void addEdge(E edge){
                Graph.this.addEdge(edge);
                Graph.this.addEdge(Graph.this.revert(edge));
            }

            public void removeEdge(E edge){
                Graph.this.removeEdge(edge);
                Graph.this.removeEdge(Graph.this.revert(edge));
            }

            public Collection<E> getEdges(){
                return Graph.this.getEdges();
            }

            public void setEdges(List<E> edges){
                Graph.this.setEdges(edges);
            }

            public Collection<E> relatedEdges(V vertex){
                return Graph.this.relatedEdges(vertex);
            }
        };
    }

    abstract public void addVertex(V vertex);

    abstract public void removeVertex(V vertex);

    abstract public Collection<V> getVertices();

    abstract public void addEdge(E edge);

    abstract public void removeEdge(E edge);

    abstract public Collection<E> getEdges();

    abstract public void setEdges(List<E> edges);

    abstract public Collection<E> relatedEdges(V vertex);

    /* Graph operations */

    public Collection<V> relatedVertices(V vertex){
        return this.graph.childrenVertices(vertex);
    }

    public Set<V> reachableFrom(V vertex){
        return this.graph.reachableFrom(vertex);
    }

    public Set<V> reachableTo(V vertex){
        return this.graph.reachableTo(vertex);
    }

    public boolean isConnected(){
        return this.graph.isWeaklyConnected();        
    }

    public boolean hasCycle(){
        return this.graph.hasCycle();
    }

    public Set<E> spanningTree(){
        return this.graph.spanningTree();
    }

}