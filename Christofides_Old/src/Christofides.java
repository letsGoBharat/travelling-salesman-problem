import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Christofides {

    private static Node[] nodes;

    public static int distanceGraph[][];

    private static List<Integer> optimizedTour;
    
    private static List<Integer> threeOptimizedTour;

    private static int fitness = Integer.MAX_VALUE;

    public static void main(String[] args) {


        PrimsAlgorithm prims = new PrimsAlgorithm();
        prims.runAlgorithm();
        distanceGraph = prims.getGraph();
        MinWeightPerfectMatching findMatch = new MinWeightPerfectMatching();
        nodes = prims.getNodes();
        for(Node n : nodes) {
        	System.out.println(n.getIndex() + " test");
        }
        List<Integer> doubleAlgorithm = prims.doubleAlgo();
        findMatch.setDistanceGraph(prims.getGraph());
        findMatch.setOddNodes(Arrays.stream(prims.getNodes()).filter(x -> x.getDegree() % 2 != 0).collect(Collectors.toList()));
        findMatch.runAlgorithm();

        List<Integer> eulerTour = new ArrayList<>();
        if (!Arrays.stream(prims.getNodes()).anyMatch(n -> n.getDegree() % 2 != 0)) {

            //Even edges for all vertices. Find Euler's Tour using Fleury's algorithm.
            EulersTour tour = new EulersTour();
            tour.setNodes(prims.getNodes());
            tour.runAlgorithm();
            eulerTour = tour.getEulerPath().stream().distinct().collect(Collectors.toList());

        }

        NearestNeighbour nearestNeighbour = new NearestNeighbour();
        nearestNeighbour.setGraph(prims.getGraph());
        nearestNeighbour.runAlgorithm();
        List<Integer> naive = nearestNeighbour.getTour();

        if (!eulerTour.isEmpty()) {
        	//swapCities(eulerTour);
            performTwoOpt(eulerTour);
        }
        performTwoOpt(doubleAlgorithm);
        performTwoOpt(naive);
        optimizedTour.forEach(System.out::println);
    }

    public static int calculateNewDistance(List<Integer> newTour) {
        int distance = 0;
        for (int i = 0; i < newTour.size() - 1; i++) {
            int j = i + 1;
            distance += Christofides.distanceGraph[newTour.get(i)][newTour.get(j)];
        }

        return distance + Christofides.distanceGraph[newTour.get(0)][newTour.get(newTour.size() - 1)];

    }

    private static void performTwoOpt(List<Integer> indices) {

        TwoOptimization twoOpt = new TwoOptimization();
        twoOpt.setFitness(calculateNewDistance(indices));
        twoOpt.setDistanceGraph(distanceGraph);
        if (twoOpt.getFitness() < fitness) {
            optimizedTour = twoOpt.alternate(indices);
            //threeOptimizedTour = twoOpt.alternate3Opt(optimizedTour);
            fitness = twoOpt.getFitness();
        }

    }
    
//    public static void swapCities(List<Integer> tour) {
//        int a = generateRandomIndex(tour.size());
//        int b = generateRandomIndex(tour.size());
//        int x = tour.get(a);
//        int y = tour.get(b);
//        tour.set(a, y);
//        tour.set(b, x);
//    }
//
//    private static int generateRandomIndex(int size) {
//        return (int) (Math.random() * size);
//    }

}
