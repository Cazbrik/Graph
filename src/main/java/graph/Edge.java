package graph;

import java.util.Objects;

public class Edge<T, U> {

    private U cost;
    private T start;
    private T end;

    public Edge(T start, T end){
        this(start, end, null);
    }

    public Edge(T start, T end, U cost){
        this.start = Objects.requireNonNull(start, "Start vertex must not be null");
        this.end = Objects.requireNonNull(end, "End vertex must not be null");
        this.cost = cost;
    }

    public boolean contains(T vertex){
        if(vertex == null) return false;
        return vertex.equals(this.start) || vertex.equals(this.end);
    }

    public void change(T old, T changed){
        if(!this.contains(old) || changed == null) return;
        if(this.end.equals(old)) this.end = changed;
        if(this.start.equals(old)) this.start = changed;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Edge<?, ?>)) return false;

        Edge<?, ?> edge = (Edge<?, ?>) obj;
        if(edge.start == null || edge.end == null) return false;
        return edge.start.equals(this.start) && edge.end.equals(this.end) && ((edge.cost == null && this.cost == null) || edge.cost.equals(this.cost));

    }
    
    @Override
    public int hashCode(){
        return this.start.hashCode() * this.end.hashCode() * (int) this.cost;
    }

    @Override
    public String toString(){
        return this.start + " -> " + this.end + " : " + this.cost;
    }

    public T getStart(){
        return this.start;
    }

    public T getEnd(){
        return this.end;
    }

}