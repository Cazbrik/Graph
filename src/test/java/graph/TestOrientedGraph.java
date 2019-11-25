package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;

public class TestOrientedGraph{

    private SimpleGraph simple;

    @BeforeEach
    public void initSimpleGraph(){
        this.simple = new SimpleGraph(new ArrayList<>(Arrays.asList(12, 3, 5, 6, 8, 1, 34)));
    }

    @Test
    public void testChildrenVertices(){

        Collection<Integer> children = this.simple.childrenVertices(34);
        Assertions.assertTrue(children.isEmpty());

        children = this.simple.childrenVertices(8);
        Assertions.assertEquals(1, children.size());
        Assertions.assertTrue(children.contains(1));

        children = this.simple.childrenVertices(3);
        Assertions.assertEquals(1, children.size());
        Assertions.assertTrue(children.contains(5));

        children = this.simple.childrenVertices(12);
        Assertions.assertEquals(1, children.size());
        Assertions.assertTrue(children.contains(3));

    }

    @Test
    public void testChildrenVertexNotPresent(){
        Collection<Integer> children = this.simple.childrenVertices(35);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testChildrenVertexNull(){
        Collection<Integer> children = this.simple.childrenVertices(null);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableFromNotPresent(){
        Set<Integer> children = this.simple.reachableFrom(35);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableFromNull(){
        Set<Integer> children = this.simple.reachableFrom(null);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableFromEnd(){
        Set<Integer> children = this.simple.reachableFrom(34);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableFromMiddle(){
        Set<Integer> children = this.simple.reachableFrom(8);
        Assertions.assertEquals(2, children.size());
        Assertions.assertTrue(children.contains(1));
        Assertions.assertTrue(children.contains(34));
    }

    @Test
    public void testReachableFromStart(){
        Set<Integer> children = this.simple.reachableFrom(12);
        Assertions.assertEquals(6, children.size());
        Assertions.assertTrue(children.contains(3));
        Assertions.assertTrue(children.contains(5));
        Assertions.assertTrue(children.contains(6));
        Assertions.assertTrue(children.contains(8));
        Assertions.assertTrue(children.contains(1));
        Assertions.assertTrue(children.contains(34));
    }        

    @Test
    public void testReachableToNotPresent(){
        Set<Integer> children = this.simple.reachableTo(35);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableToNull(){
        Set<Integer> children = this.simple.reachableTo(null);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testReachableToEnd(){
        Set<Integer> children = this.simple.reachableTo(34);
        Assertions.assertEquals(6, children.size());
        Assertions.assertTrue(children.contains(3));
        Assertions.assertTrue(children.contains(5));
        Assertions.assertTrue(children.contains(6));
        Assertions.assertTrue(children.contains(8));
        Assertions.assertTrue(children.contains(1));
        Assertions.assertTrue(children.contains(12));
    }

    @Test
    public void testReachableToMiddle(){
        Set<Integer> children = this.simple.reachableTo(8);
        Assertions.assertEquals(4, children.size());
        Assertions.assertTrue(children.contains(6));
        Assertions.assertTrue(children.contains(5));
        Assertions.assertTrue(children.contains(3));
        Assertions.assertTrue(children.contains(12));
    }

    @Test
    public void testReachableToStart(){
        Set<Integer> children = this.simple.reachableTo(12);
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    public void testStronglyConnectedNotConnected(){
        this.simple.addVertex(0);
        Assertions.assertFalse(this.simple.isStronglyConnected());
    }

    @Test
    public void testStronglyConnectedButWeaklyConnected(){
        this.simple.addVertex(-1);
        this.simple.addVertex(13);
        Assertions.assertFalse(this.simple.isStronglyConnected());
        Assertions.assertTrue(this.simple.isWeaklyConnected());
    }

    @Test
    public void testStronglyConnected(){
        Assertions.assertTrue(this.simple.isStronglyConnected());
    }

    @Test
    public void testWeaklyConnectedNotConnected(){
        this.simple.addVertex(0);
        Assertions.assertFalse(this.simple.isWeaklyConnected());
    }

    @Test
    public void testWeaklyConnected(){
        Assertions.assertTrue(this.simple.isWeaklyConnected());
    }

    @Test
    public void testHasNoCycle(){
        Assertions.assertFalse(this.simple.hasCycle());
    }

    @Test
    public void testHasCycle(){
        this.simple.addVertex(1);
        Assertions.assertTrue(this.simple.hasCycle());
    }


}

class SimpleGraph extends OrientedGraph<Integer, Integer>{

    private List<Integer> graph;
    private int nullCounter = 0;

    public SimpleGraph(List<Integer> graph){
        super(
            e -> { return (graph.get(e) >= 0) ? graph.get(e) : graph.get(e + 1);},
            e -> { return (graph.get(e) >= 0) ? graph.get(e + 1) : graph.get(e);}
        );
        this.graph = graph;
    }

    public List<Integer> getGraph() {
        return this.graph;
    }

    public void addVertex(Integer vertex){
        if(vertex == 0) this.nullCounter++;
        this.graph.add(vertex);
    }

    public void removeVertex(Integer vertex){
        this.graph.remove(vertex);
    }

    public Collection<Integer> getVertices(){
        return this.graph;
    }

    public void setEdges(List<Integer> edges){
        return;
    }

    public void addEdge(Integer edge){ 
        return;
    }

    public void removeEdge(Integer edge){ 
        return;
    }

    public Collection<Integer> getEdges(){
        return IntStream.rangeClosed(0, this.graph.size() - 2 - this.nullCounter).mapToObj(i->i).collect(Collectors.toList());
    }

    public Collection<Integer> relatedEdges(Integer vertex){

        Set<Integer> edges = new HashSet<>();
        if(vertex == null) return edges;
        int index = this.graph.indexOf(vertex);

        if(index > 0 && index < this.graph.size()) edges.add(index - 1);
        if(index < this.graph.size() - 1 && index > -1) edges.add(index);

        return edges;
        
    }

    @Override
    public String toString(){
        return this.graph.toString();
    }

}