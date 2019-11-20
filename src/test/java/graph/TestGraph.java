package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
 
public class TestGraph{
    
    private Graph<String, Integer> graph;

    @BeforeEach
    public void init(){
        Set<String> vertices = new HashSet<>(Arrays.asList("Red", "Blue", "Green", "Yellow", "Black", "White"));
        List<Edge<String, Integer>> edges = new ArrayList<>();
        edges.add(new Edge<>("Red", "Blue"));
        edges.add(new Edge<>("Red", "Green"));
        edges.add(new Edge<>("Green", "Yellow"));
        edges.add(new Edge<>("Yellow", "Red"));
        edges.add(new Edge<>("Blue", "White"));
        edges.add(new Edge<>("White", "Black"));
        this.graph = new Graph<String, Integer>(vertices, edges);
    }

    @Test
    public void testAddNewVertex(){
        int nb = this.graph.getVertices().size();
        Assertions.assertTrue(this.graph.add("Purple"));
        Assertions.assertEquals(nb + 1, this.graph.getVertices().size());
    }

    @Test
    public void testAddPresentVertex(){
        int nb = this.graph.getVertices().size();
        Assertions.assertFalse(this.graph.add("Red"));
        Assertions.assertEquals(nb, this.graph.getVertices().size());
    }

    @Test
    public void testRemovePresentVertex(){
        int nbV = this.graph.getVertices().size();
        int nbE = this.graph.getEdges().size();

        Assertions.assertTrue(this.graph.remove("Red"));
        Assertions.assertEquals(nbV - 1, this.graph.getVertices().size());
        Assertions.assertEquals(nbE - 3, this.graph.getEdges().size());
    }

    @Test
    public void testRemoveNotPresentVertex(){
        int nbV = this.graph.getVertices().size();
        int nbE = this.graph.getEdges().size();
        
        Assertions.assertFalse(this.graph.remove("Purple"));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        Assertions.assertEquals(nbE, this.graph.getEdges().size());
    }

    private void checkVertexEdgeAndExist(String vertex, int adjEdge, boolean exist){
        int counter = 0;
        for(Edge<String, Integer> edge : this.graph.getEdges())
            if(edge.contains(vertex)) counter++;
        
        Assertions.assertEquals(adjEdge, counter);
        Assertions.assertEquals(exist, this.graph.getVertices().contains(vertex));
    }

    @Test
    public void testReplacePresentVertexByPresentOne(){
        int nbV = this.graph.getVertices().size();

        Assertions.assertTrue(this.graph.replace("Red", "Yellow"));
        Assertions.assertEquals(nbV - 1, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("Red", 0, false);
        this.checkVertexEdgeAndExist("Yellow", 4, true);
    }

    @Test
    public void testReplaceNotPresentVertex(){
        int nbV = this.graph.getVertices().size();
        Assertions.assertFalse(this.graph.replace("Purple", "Yellow"));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("Purple", 0, false);
        this.checkVertexEdgeAndExist("Yellow", 2, true);
    }

    @Test
    public void testReplacePresentVertexByNotPresentOne(){
        int nbV = this.graph.getVertices().size();
        Assertions.assertTrue(this.graph.replace("Yellow", "Purple"));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("Yellow", 0, false);
        this.checkVertexEdgeAndExist("Purple", 2, true);
    }

    @Test
    public void testMergeNotPresentEndVertex(){
        int nbV = this.graph.getVertices().size();
        BiFunction<String, String, String> func = (s1, s2) -> s1 + s2;
        Assertions.assertFalse(this.graph.merge("Red", "Purple", func));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("RedPurple", 0, false);
        this.checkVertexEdgeAndExist("Red", 3, true);
    }

    @Test
    public void testMergeNotPresentStartVertex(){
        int nbV = this.graph.getVertices().size();
        BiFunction<String, String, String> func = (s1, s2) -> s1 + s2;
        Assertions.assertFalse(this.graph.merge("Purple", "Red", func));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("Red", 3, true);
        this.checkVertexEdgeAndExist("Purple", 0, false);
    }

    @Test
    public void testMergeNullMergeFunc(){
        int nbV = this.graph.getVertices().size();
        BiFunction<String, String, String> func = (s1, s2) -> s1 + s2;
        Assertions.assertFalse(this.graph.merge("Red", "Yellow", null));
        Assertions.assertEquals(nbV, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("Yellow", 2, true);
        this.checkVertexEdgeAndExist("Red", 3, true);
    }

    @Test
    public void testMergeSucced(){
        int nbV = this.graph.getVertices().size();
        BiFunction<String, String, String> func = (s1, s2) -> s1 + s2;
        Assertions.assertTrue(this.graph.merge("Red", "Yellow", func));
        Assertions.assertEquals(nbV - 1, this.graph.getVertices().size());
        this.checkVertexEdgeAndExist("RedYellow", 4, true);
        this.checkVertexEdgeAndExist("Red", 0, false);
        this.checkVertexEdgeAndExist("Yellow", 0, false);
    }

    @Test
    public void testAddEdgeWithNotPresentNode(){
        int nb = this.graph.getEdges().size();
        Assertions.assertFalse(this.graph.add(new Edge<>("Purple", "Red")));
        Assertions.assertEquals(nb , this.graph.getEdges().size());
        this.checkVertexEdgeAndExist("Red", 3, true);
        this.checkVertexEdgeAndExist("Purple", 0, false);
    }

    @Test
    public void testAddEdgeSuccess(){
        int nb = this.graph.getEdges().size();
        Assertions.assertTrue(this.graph.add(new Edge<>("Yellow", "Red")));
        Assertions.assertEquals(nb + 1, this.graph.getEdges().size());
        this.checkVertexEdgeAndExist("Red", 4, true);
        this.checkVertexEdgeAndExist("Yellow", 3, true);
    }

    @Test
    public void testRemoveNotPresentEdge(){
        int nb = this.graph.getEdges().size();
        Assertions.assertFalse(this.graph.remove(new Edge<>("Purple", "Red")));
        Assertions.assertEquals(nb, this.graph.getEdges().size());
        this.checkVertexEdgeAndExist("Red", 3, true);
        this.checkVertexEdgeAndExist("Purple", 0, false);
    }

    @Test
    public void testRemovePresentEdge(){
        int nb = this.graph.getEdges().size();
        Assertions.assertTrue(this.graph.remove(new Edge<>("Yellow", "Red")));
        Assertions.assertEquals(nb - 1, this.graph.getEdges().size());
        this.checkVertexEdgeAndExist("Red", 2, true);
        this.checkVertexEdgeAndExist("Yellow", 1, true);
    }

    @Test
    public void testGetRelatedEdgeNotPresentNode(){
        List<Edge<String, Integer>> list = this.graph.getRelatedEdge("Purple");
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testGetRelatedEdgeNullNode(){
        List<Edge<String, Integer>> list = this.graph.getRelatedEdge(null);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testGetRelatedEdgePresentNode(){
        List<Edge<String, Integer>> list = this.graph.getRelatedEdge("Red");
        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.contains(new Edge<>("Yellow", "Red")));
        Assertions.assertTrue(list.contains(new Edge<>("Red", "Blue")));
        Assertions.assertTrue(list.contains(new Edge<>("Red", "Green")));
    }

    @Test
    public void testGetRelatedVertexNotPresentNode(){
        List<String> list = this.graph.getRelatedVertex("Purple");
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testGetRelatedVertexNullNode(){
        List<String> list = this.graph.getRelatedVertex(null);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testGetRelatedVertexPresentNode(){
        List<String> list = this.graph.getRelatedVertex("Red");
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.contains("Blue"));
        Assertions.assertTrue(list.contains("Green"));
    }

    @Test
    public void testReachableFromNotPresent(){
        Set<String> list = this.graph.reachableFrom("Purple");
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testReachableFromNotNull(){
        Set<String> list = this.graph.reachableFrom(null);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testReachableFromPresent(){
        Set<String> list = this.graph.reachableFrom("Red");
        Assertions.assertEquals(6, list.size());
        Assertions.assertTrue(list.contains("Red"));
        Assertions.assertTrue(list.contains("Blue"));
        Assertions.assertTrue(list.contains("Green"));
        Assertions.assertTrue(list.contains("Yellow"));
        Assertions.assertTrue(list.contains("Black"));
        Assertions.assertTrue(list.contains("White"));
    }

    @Test
    public void testConnectedGraph(){
        Assertions.assertTrue(this.graph.isConnected());
    }

    @Test
    public void testNotConnectedGraph(){
        this.graph.add("Purple");
        Assertions.assertFalse(this.graph.isConnected());
    }

    @Test
    public void testNotConnectedEmptyGraph(){
        Assertions.assertFalse(new Graph<String, Integer>().isConnected());
    }

}