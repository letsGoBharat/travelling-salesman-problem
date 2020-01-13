import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PrimsAlgorithm {

    /*
    Prims Algorithm to find the minimum spanning tree.
     */

    private int numberOfCities;

    public int[][] getGraph() {
        return graph;
    }

    private int graph[][];

    public Node[] getNodes() {
        return nodes;
    }

    private Node[] nodes;

    public List<Integer> getDfsPath() {
        return dfsPath;
    }

    private List<Integer> dfsPath = new ArrayList<>();



    public void runAlgorithm() {

        readInputValues();
        buildGraph();
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

    private void buildGraph() {

        graph = new int[numberOfCities][numberOfCities];
        int value;
        for (int i = 0; i < numberOfCities; i++) {

            for (int j = 0; j < numberOfCities; j++) {

                if (i != j && graph[i][j] == 0) {

                    value = calclulateDistance(nodes[i], nodes[j]);
                    graph[i][j] = value;
                    graph[j][i] = value;
                } else {
                    continue;
                }
            }
        }

    }


    private void readInputValues() {

        Scanner readInput = new Scanner(System.in);
        List<Node> testList = new ArrayList<Node>();
        numberOfCities = Integer.parseInt(readInput.nextLine());
        nodes = new Node[numberOfCities];
        String[] cityCoordinates;
        Node tempNode;
        for (int i = 0; i < numberOfCities; i++) {

            cityCoordinates = readInput.nextLine().split(" ");
            tempNode = new Node(Float.parseFloat(cityCoordinates[0]), Float.parseFloat(cityCoordinates[1]));
            tempNode.setIndex(i);
            tempNode.setKey(Integer.MAX_VALUE);
             
            nodes[i] = tempNode;
        }
        testList = Arrays.asList(nodes);
        Collections.shuffle(testList);
        nodes = testList.toArray(new Node[numberOfCities]);
        nodes[0].setParent(nodes[0]);
        nodes[0].setKey(0);
    }


    private int calclulateDistance(Node city1, Node city2) {

        float resultX = Math.abs(city1.getxCoordinate() - city2.getxCoordinate());
        float resultY = Math.abs(city1.getyCoordinate() - city2.getyCoordinate());
        return Math.round((float) Math.sqrt(resultX * resultX + resultY * resultY));
    }

}
