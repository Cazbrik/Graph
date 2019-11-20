package graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
 
public class TestEdge{

    private Edge<String, Integer> edge;

    @BeforeEach
    public void init(){
        this.edge = new Edge<String, Integer>("Start", "End");
    }

    @Test
    public void testContainsStartNode(){
        Assertions.assertTrue(this.edge.contains("Start"));
    }

    @Test
    public void testContainsEndNode(){
        Assertions.assertTrue(this.edge.contains("End"));
    }

    @Test
    public void testNotContainsNode(){
        Assertions.assertFalse(this.edge.contains("Ennd"));
    }

    @Test
    public void testContainsNull(){
        Assertions.assertFalse(this.edge.contains(null));
    }

    @Test
    public void testEqualsSameObject(){
        Assertions.assertEquals(this.edge, this.edge);
    }

    @Test
    public void testEqualsObjectWithSameNodes(){
        Edge<String, Integer> edgeStr = new Edge<>("Start", "End");
        Assertions.assertEquals(this.edge, edgeStr);
    }

    @Test
    public void testEqualsDifferentCost(){
        Edge<String, Integer> edgeCost = new Edge<>("Start", "End", 3);
        Assertions.assertNotEquals(this.edge, edgeCost);
    }

    @Test
    public void testEqualsNotSameNodeType(){
        Edge<Integer, Integer> edgeInt = new Edge<>(3, 5);
        Assertions.assertNotEquals(this.edge, edgeInt);
    }

    @Test
    public void testEqualsNullObject(){
        Assertions.assertNotEquals(this.edge, null);
    }

    @Test
    public void testEqualsNotSameType(){
        Assertions.assertNotEquals(this.edge, "Start");
    }

    @Test
    public void testChangeStartNode(){
        this.edge.change("Start", "Debut");
        Assertions.assertEquals("Debut", this.edge.getStart());
        Assertions.assertEquals("End", this.edge.getEnd());
    }

    @Test
    public void testChangeEndNode(){
        this.edge.change("End", "Fin");
        Assertions.assertEquals("Start", this.edge.getStart());
        Assertions.assertEquals("Fin", this.edge.getEnd());
    }

    @Test
    public void testChangeNotObjectNode(){
        this.edge.change("Fin", "Start");
        Assertions.assertEquals("Start", this.edge.getStart());
        Assertions.assertEquals("End", this.edge.getEnd());
    }

    @Test
    public void testChangeNullNode(){
        this.edge.change("Fin", null);
        Assertions.assertEquals("Start", this.edge.getStart());
        Assertions.assertEquals("End", this.edge.getEnd());
    }

}