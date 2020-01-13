import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PrimsAlgorithm {

    /*
    Prims Algorithm to find the minimum spanning tree.
     */


    public Node[] getNodes() {
        return nodes;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }

    private Node[] nodes;

    public List<Integer> getDfsPath() {
        return dfsPath;
    }

    private List<Integer> dfsPath = new ArrayList<>();

    private int graph[][];

    private int numberOfCities;

    public void setNumberOfCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public void runAlgorithm() {

       // printGraph();
        findMinimumSpanningTree();
        buildDegreeAndChildren();
        runDFS(nodes[0]);

    }

    public List<Integer> doubleAlgo() {
       return dfsPath.stream().distinct().collect(Collectors.toList());
    }

    private  void runDFS(Node node) {

        while(!node.isTraversed())  {
            dfsPath.add(node.getIndex());
            if(node.getChildren().isEmpty()) {
                node.setTraversed(true);
            } else {
                Consumer<Node> processChild = childNode -> {
                        runDFS(childNode);
                        dfsPath.add(node.getIndex());
                };

                node.getChildren().forEach(processChild);
                node.setTraversed(true);
            }
        }
    }

    private void buildDegreeAndChildren() {
        Node tempNode;
        for(int i=1;i<nodes.length;i++) {
            tempNode = nodes[i];
            tempNode.incrementDegree();
            tempNode.getParent().addChild(tempNode);
            tempNode.getParent().incrementDegree();
            nodes[i] = tempNode;
        }

    }


    public void findMinimumSpanningTree() {

        int currentVertexIndex = 0;
        Node currentVertex;
        do {
            currentVertex = nodes[currentVertexIndex];
            currentVertex.setVertex(true);
            updateParentAndKeyValueForVertices(currentVertex);
            currentVertexIndex = findMinimumEdge();
        } while (currentVertexIndex != -1);
    }

    private void printGraph() {

        for (int[] row : graph) {

            for (int cell : row) {

                System.out.print(cell);
                System.out.print(" ");

            }

            System.out.println();
        }
    }


    private void updateParentAndKeyValueForVertices(Node node) {
        int index = node.getIndex();
        Node tempNode;
        int distanceFromGraph;
        for (int i = 0; i < numberOfCities; i++) {
            tempNode = nodes[i];
            distanceFromGraph = graph[index][i];
            if (i != index && !tempNode.isVertex() && distanceFromGraph < tempNode.getKey()) {
                tempNode.setParent(node);
                tempNode.setKey(distanceFromGraph);
                nodes[i]=tempNode;
            }

        }
    }

    private int findMinimumEdge() {

        int minIndex = -1;
        int minEdge = Integer.MAX_VALUE;
        Node tempNode;
        int tempKey;
        for (int i = 0; i < numberOfCities; i++) {
            tempNode = nodes[i];
            tempKey = tempNode.getKey();
            if (!tempNode.isVertex() && tempNode.getKey() < minEdge) {

                minIndex = i;
                minEdge = tempKey;
            }
        }
        return minIndex;
    }

}
