import java.util.Scanner;

public class CityService {

    public static int numberOfCities;

    public static int graph[][];

    public static Node[] nodes;

    public static void buildGraph() {

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


    public static void readInputValues() {

        Scanner readInput = new Scanner(System.in);
        numberOfCities = Integer.parseInt(readInput.nextLine());
        nodes = new Node[numberOfCities];
        String[] cityCoordinates;
        Node tempNode;
        for (int i = 0; i < numberOfCities; i++) {

            cityCoordinates = readInput.nextLine().split(" ");
            tempNode = new Node(Float.parseFloat(cityCoordinates[0]), Float.parseFloat(cityCoordinates[1]));
            tempNode.setIndex(i);
            if (i != 0) {
                tempNode.setKey(Integer.MAX_VALUE);
            } else {
                tempNode.setParent(tempNode);
            }
            nodes[i] = tempNode;
        }
    }


    public static int calclulateDistance(Node city1, Node city2) {

        float resultX = Math.abs(city1.getxCoordinate() - city2.getxCoordinate());
        float resultY = Math.abs(city1.getyCoordinate() - city2.getyCoordinate());
        return Math.round((float) Math.sqrt(resultX * resultX + resultY * resultY));
    }
}
