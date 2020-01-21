import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import graph.ReachableVerticesAccessor;

public class Main{

    public static void main(String ... args){
    	{
    		Function<Node, Set<Node>> related = node -> node.getNeighbours();
    		ReachableVerticesAccessor<Node> accessor = new ReachableVerticesAccessor<Node>(related);
    	}
    	{
    		Function<NodeA, Set<NodeA>> related = nodeA -> nodeA.getSpecificNeighbours();
			ReachableVerticesAccessor<NodeA> accessor = new ReachableVerticesAccessor<NodeA>(related);
    	}
    	{
    		Function<Node, Set<NodeA>> related = node -> node.getNeighbours().stream().map(n -> (NodeA) n).collect(Collectors.toSet());
			ReachableVerticesAccessor<NodeA> accessor = new ReachableVerticesAccessor<>(related);
    	}
    }

    interface Node {
		Set<Node> getNeighbours();
    }
    interface NodeA extends Node {
		Set<NodeA> getSpecificNeighbours();
    }
}