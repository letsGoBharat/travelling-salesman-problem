import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EulersTour {
    /*
    A Fleury Algorithm implementation for Euler's tour.
     */

    public List<Integer> getEulerPath() {
        return eulerPath;
    }

    private List<Integer> eulerPath;

    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    private Node[] nodes;

    public void runAlgorithm() {
        addParentAlsoAsAnAdjacentNode();
        eulerPath = new ArrayList<>(nodes.length);
        eulerPath.add(nodes[0].getIndex());
        findEulerTour(nodes[0]);
    }

    private void addParentAlsoAsAnAdjacentNode() {

        Arrays.stream(nodes).filter(n->n.getIndex()!=0).forEach(n->n.getChildren().add(n.getParent()));
    }

    private void findEulerTour(Node node) {

        List<Node> adjacentNodes = node.getChildren();
        Node adjNode;
        for(int i=0;i<adjacentNodes.size();i++) {

            adjNode = adjacentNodes.get(i);
            if (isValidNextEdge(node, adjNode))
            {
                removeEdge(node,adjNode);
                eulerPath.add(adjNode.getIndex());
                findEulerTour(adjNode);
            }
        }
    }

    private boolean isValidNextEdge(Node node, Node adjNode) {

        if (node.getChildren().size() == 1) {
            return true;
        }

        boolean[] isVisited = new boolean[nodes.length];
        int count1 = dfsCount(node, isVisited);
        removeEdge(node,adjNode);
        isVisited = new boolean[nodes.length];
        int count2 = dfsCount(node, isVisited);
        addEdge(node,adjNode);
        return count1<=count2;
    }

    private void removeEdge(Node node, Node adjacentNode) {

        node.getChildren().remove(adjacentNode);
        adjacentNode.getChildren().remove(node);
    }


    private void addEdge(Node node,Node adjacentNode) {

        node.getChildren().add(adjacentNode);
        adjacentNode.getChildren().add(node);
    }

    private int dfsCount(Node v, boolean[] isVisited)
    {
        // Mark the current node as visited
        isVisited[v.getIndex()] = true;
        int count = 1;
        // Recurse to all child nodes.
        for (Node adj : v.getChildren())
        {
            if (!isVisited[adj.getIndex()])
            {
                count = count + dfsCount(adj, isVisited);
            }
        }
        return count;
    }
}
