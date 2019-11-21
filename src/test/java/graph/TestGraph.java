package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

 
public class TestGraph{
    
    private Graph<String, Integer> graph;

    @BeforeEach
    public void init(){
        Set<String> vertices = new HashSet<>(Arrays.asList("Red", "Blue", "Green", "Yellow", "Black", "White"));
        List<Edge<String, Integer>> edges = new ArrayList<>();
        edges.add(new Edge<>("Red", "Blue", 7));
        edges.add(new Edge<>("Red", "Green", 2));
        edges.add(new Edge<>("Green", "Yellow", 3));
        edges.add(new Edge<>("Yellow", "Red", 4));
        edges.add(new Edge<>("Blue", "White", 5));
        edges.add(new Edge<>("White", "Black", 6));
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
        Assertions.assertTrue(this.graph.remove(new Edge<>("Yellow", "Red", 4)));
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
        Assertions.assertTrue(list.contains(new Edge<>("Yellow", "Red", 4)));
        Assertions.assertTrue(list.contains(new Edge<>("Red", "Blue", 7)));
        Assertions.assertTrue(list.contains(new Edge<>("Red", "Green", 2)));
    }

    @Test
    public void testgetChildrenVerticesNotPresentNode(){
        List<String> list = this.graph.getChildrenVertices("Purple");
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testgetChildrenVerticesNullNode(){
        List<String> list = this.graph.getChildrenVertices(null);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testgetChildrenVerticesPresentNode(){
        List<String> list = this.graph.getChildrenVertices("Red");
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
    public void testReachableFromNull(){
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

        list = this.graph.reachableFrom("White");
        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(list.contains("Black"));
    }

    @Test
    public void testReachableToNotPresent(){
        Set<String> list = this.graph.reachableFrom("Purple");
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testReachableToNull(){
        Set<String> list = this.graph.reachableFrom(null);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void testReachableToPresent(){
        Set<String> list = this.graph.reachableTo("White");
        Assertions.assertEquals(4, list.size());
        Assertions.assertTrue(list.contains("Red"));
        Assertions.assertTrue(list.contains("Blue"));
        Assertions.assertTrue(list.contains("Green"));
        Assertions.assertTrue(list.contains("Yellow"));

        list = this.graph.reachableTo("Red");
        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.contains("Yellow"));
        Assertions.assertTrue(list.contains("Green"));
        Assertions.assertTrue(list.contains("Red"));
    }

    @Test
    public void testStronglyConnectedGraph(){
        Assertions.assertTrue(this.graph.isStronglyConnected());
    }

    @Test
    public void testNotStronglyConnectedGraph(){
        this.graph.add("Purple");
        Assertions.assertFalse(this.graph.isStronglyConnected());
    }

    @Test
    public void testNotStronglyConnectedEmptyGraph(){
        Assertions.assertFalse(new Graph<String, Integer>().isStronglyConnected());
    }

    @Test
    public void testWeaklyConnectedGraph(){
        Assertions.assertTrue(this.graph.isWeaklyConnected());
    }

    @Test
    public void testNotWeaklyConnectedGraph(){
        this.graph.add("Purple");
        Assertions.assertFalse(this.graph.isWeaklyConnected());
    }

    @Test
    public void testNotWeaklyConnectedEmptyGraph(){
        Assertions.assertFalse(new Graph<String, Integer>().isWeaklyConnected());
    }

    @Test
    public void testNotStronglyButWeaklyConnected(){
        this.graph.add("Purple");
        this.graph.add(new Edge<>("Purple", "Black", 3));
        Assertions.assertTrue(this.graph.isWeaklyConnected());
        Assertions.assertFalse(this.graph.isStronglyConnected());
    }

    @Test
    public void testHasCycleEmptyGraph(){
        Assertions.assertFalse(new Graph<String, Integer>().hasCycle());
    }

    @Test
    public void testHasCycle(){
        Assertions.assertTrue(this.graph.hasCycle());
    }

    @Test
    public void testHasNoCycle(){
        this.graph.remove(new Edge<>("Yellow", "Red", 4));
        Assertions.assertFalse(this.graph.hasCycle());
    }

    @Test
    public void testMinSpanningTreeNotConnected(){
        this.graph.add("Purple");
        Comparator<Integer> comp = (i1, i2) -> {
            if(i1 == null) return -1;
            if(i2 == null) return 1;
            return i1 - i2;
        };
        Assertions.assertEquals(null, this.graph.minSpanningTree(comp));
    }

    @Test
    public void testMinSpanningTreeNoCompFunc(){
        Assertions.assertEquals(null, this.graph.minSpanningTree(null));
    }

    @Test
    public void testMinSpanningTreeConnected(){

        Comparator<Integer> comp = (i1, i2) -> {
            if(i1 == null) return -1;
            if(i2 == null) return 1;
            return i1 - i2;
        };

        Graph<String, Integer> minTree = this.graph.minSpanningTree(comp);
        List<Edge<String, Integer>> edges = minTree.getEdges();
        Assertions.assertEquals(this.graph.getVertices().size() - 1, edges.size());
        Assertions.assertTrue(edges.contains(new Edge<>("Red", "Blue", 7)));
        Assertions.assertTrue(edges.contains(new Edge<>("Red", "Green", 2)));
        Assertions.assertTrue(edges.contains(new Edge<>("Green", "Yellow", 3)));
        Assertions.assertTrue(edges.contains(new Edge<>("Blue", "White", 5)));
        Assertions.assertTrue(edges.contains(new Edge<>("White", "Black", 6)));

    }

    @Test
    public void testMinSpanningTreeConnectedDifferent(){

        
        this.graph.remove(new Edge<>("Yellow", "Red", 4));
        this.graph.add(new Edge<>("Yellow", "Red", 1));

        Comparator<Integer> comp = (i1, i2) -> {
            if(i1 == null) return -1;
            if(i2 == null) return 1;
            return i1 - i2;
        };

        Graph<String, Integer> minTree = this.graph.minSpanningTree(comp);
        List<Edge<String, Integer>> edges = minTree.getEdges();
        Assertions.assertEquals(this.graph.getVertices().size() - 1, edges.size());
        Assertions.assertTrue(edges.contains(new Edge<>("Red", "Blue", 7)));
        Assertions.assertTrue(edges.contains(new Edge<>("Yellow", "Red", 1)));
        Assertions.assertTrue(edges.contains(new Edge<>("Red", "Green", 2)));
        Assertions.assertTrue(edges.contains(new Edge<>("Blue", "White", 5)));
        Assertions.assertTrue(edges.contains(new Edge<>("White", "Black", 6)));

    }


}