package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNonOrientedGraphOperations {

    private List<Integer> graph;
    private NonOrientedGraphOperations<Integer> operator;

    @BeforeEach
    public void initSimpleGraph(){

        final Random rand = new Random();
        this.graph = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            this.graph.add(rand.nextInt());

        final Supplier<Set<Integer>> vertices = () -> new HashSet<>(this.graph);
        final Function<Integer, Set<Integer>> related = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())
                return rel;
            final int pos = this.graph.indexOf(x);
            rel.add((pos < this.graph.size() - 1) ? this.graph.get(pos + 1) : this.graph.get(0));
            rel.add((pos > 0) ? this.graph.get(pos - 1) : this.graph.get(this.graph.size() - 1));
            return rel;
        };

        this.operator = new NonOrientedGraphOperations<>(vertices, related);
    }

    @Test
    public void testReachableAllVertex() {
        final Set<Integer> result = this.operator.reachable(this.graph.get(0));
        assertEquals(this.graph.size(), result.size());
        assertTrue(this.graph.containsAll(result));
    }

    @Test
    public void testReachableNoVertex() {
        this.operator.related = x -> new HashSet<>();
        final Set<Integer> result = this.operator.reachable(this.graph.get(0));
        assertTrue(result.isEmpty());
    }

    @Test
    public void testIsConnected(){
        assertTrue(this.operator.isConnected());
    }

    @Test
    public void testIsNotConnected(){
        this.operator.related = x -> new HashSet<>();
        assertFalse(this.operator.isConnected());
    }

    @Test
    public void testHasCycle(){
        assertTrue(this.operator.hasCycle());
    }

    @Test
    public void testHasNoCycle(){
        this.operator.related = x -> new HashSet<>();
        assertFalse(this.operator.hasCycle());
    }

    @Test
    public void spanningTree(){
        //ToDo
    }

}