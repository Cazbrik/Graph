package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;

public class TestOrientedGraph{

    private SimpleGraph simple;

    @BeforeEach
    public void initSimpleGraph(){
        this.simple = new SimpleGraph(12, 3, 5, 6, 8, 1, 34);
    }

    @Test
    public void testChildrenVertices(){

        Set<Integer> children = this.simple.childrenVertices(34);
        Assertions.assertTrue(children.isEmpty());

        children = this.simple.childrenVertices(8);
        Assertions.assertEquals(1, children.size());
        Assertions.assertTrue(children.contains(1));

    }

    @Test
    public void testChildrenVertexNotPresent(){
        Set<Integer> children = this.simple.childrenVertices(35);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testChildrenVertexNull(){
        Set<Integer> children = this.simple.childrenVertices(null);
        Assertions.assertTrue(children.isEmpty());
    }


}

class SimpleGraph extends OrientedGraph<Integer, Integer>{

    private List<Integer> graph;

    public SimpleGraph(Integer ... graph){
        super(
            e -> {return graph[e];},
            e -> {return graph[e + 1];}
        );
        this.graph = Arrays.asList(graph);
    }

    public void addVertex(Integer vertex){
        this.graph.add(vertex);
    }

    public void removeVertex(Integer vertex){
        this.graph.remove(vertex);
    }

    public Collection<Integer> getVertices(){
        return this.graph;
    }

    public void addEdge(Integer edge){ 
        return;
    }

    public void removeEdge(Integer edge){ 
        return;
    }

    public Collection<Integer> getEdges(){
        return IntStream.rangeClosed(0, this.graph.size() - 2).mapToObj(i->i).collect(Collectors.toList());
    }

    public Collection<Integer> relatedEdges(Integer vertex){

        Set<Integer> edges = new HashSet<>();
        int index = this.graph.indexOf(vertex);

        if(index > 0 && index < this.graph.size()) edges.add(index - 1);
        if(index < this.graph.size() - 1 && index > -1) edges.add(index);

        return edges;
        
    }

}