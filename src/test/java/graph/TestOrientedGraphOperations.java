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

public class TestOrientedGraphOperations<V> {

    private List<Integer> graph;
    private OrientedGraphOperations<Integer> operator;

    @BeforeEach
    public void initSimpleGraph(){

        final Random rand = new Random();
        this.graph = new ArrayList<>();
        for (int i = 0; i < 50; i++)
            this.graph.add(rand.nextInt());

        final Supplier<Set<Integer>> vertices = () -> new HashSet<>(this.graph);
        final Function<Integer, Set<Integer>> parents = x -> new HashSet<>();
        final Function<Integer, Set<Integer>> children = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())return rel;
            final int pos = this.graph.indexOf(x);
            rel.add((pos < this.graph.size() - 1) ? this.graph.get(pos + 1) : this.graph.get(0));
            return rel;
        };

        this.operator = new OrientedGraphOperations<>(vertices, children, parents);
    }

    @Test
    public void testRelatedOnlyChildren(){
        Set<Integer> rel = this.operator.related(this.graph.get(0));
        Set<Integer> chi = this.operator.children.apply(this.graph.get(0));
        assertEquals(chi.size(), rel.size());
        assertTrue(chi.containsAll(rel));
    }

    @Test
    public void testRelatedOnlyParents(){
        Function<Integer, Set<Integer>> children = this.operator.children;
        this.operator.children = this.operator.parents;
        this.operator.parents = children;

        Set<Integer> rel = this.operator.related(this.graph.get(0));
        Set<Integer> par = this.operator.parents.apply(this.graph.get(0));
        assertEquals(par.size(), rel.size());
        assertTrue(par.containsAll(rel));
    }

    @Test
    public void testRelatedBothParentChildren(){
    }

    @Test
    public void testReachableFromEnd(){

        final Function<Integer, Set<Integer>> children = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())return rel;
            final int pos = this.graph.indexOf(x);
            if(pos < this.graph.size() - 1) rel.add(this.graph.get(pos + 1));
            return rel;
        };

        this.operator.children = children;
        Set<Integer> reached = this.operator.reachableFrom(this.graph.get(this.graph.size() - 1));
        assertTrue(reached.isEmpty());
    }

    @Test
    public void testReachableFromStart(){
        
        final Function<Integer, Set<Integer>> children = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())return rel;
            final int pos = this.graph.indexOf(x);
            if(pos < this.graph.size() - 1) rel.add(this.graph.get(pos + 1));
            return rel;
        };
        this.operator.children = children;
        Set<Integer> reached = this.operator.reachableFrom(this.graph.get(0));
        assertEquals(this.graph.size() - 1, reached.size());
        reached.add(this.graph.get(0));
        assertTrue(this.graph.containsAll(reached));
    }

    @Test
    public void testReachableToEnd(){
        this.operator.parents = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())return rel;
            final int pos = this.graph.indexOf(x);
            if(pos > 0) rel.add(this.graph.get(pos - 1));
            return rel;
        };
        Set<Integer> reached = this.operator.reachableTo(this.graph.get(this.graph.size() - 1));
        assertEquals(this.graph.size() - 1, reached.size());
        reached.add(this.graph.get(this.graph.size() - 1));
        assertTrue(this.graph.containsAll(reached));
    }

    @Test
    public void testReachableToStart(){
        this.operator.parents = x -> {
            final Set<Integer> rel = new HashSet<>();
            if (this.graph.isEmpty())return rel;
            final int pos = this.graph.indexOf(x);
            if(pos > 0) rel.add(this.graph.get(pos - 1));
            return rel;
        };
        Set<Integer> reached = this.operator.reachableTo(this.graph.get(0));
        assertTrue(reached.isEmpty());
    }

    @Test
    public void testIsStronglyConnected(){
        assertTrue(this.operator.isStronglyConnected());
    }

    @Test
    public void testIsNotStronglyConnected(){
    }

    @Test
    public void testIsWeaklyConnected(){
        assertTrue(this.operator.isWeaklyConnected());
    }

    @Test
    public void testIsNotWeaklyConnected(){
    }

    @Test    
    public void testHasCircuit(){
        assertTrue(this.operator.hasCircuit());
    }

    @Test    
    public void testHasNoCircuit(){
        this.operator.children = x -> new HashSet<>();
        assertFalse(this.operator.hasCircuit());
    }

}