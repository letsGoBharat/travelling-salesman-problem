import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Christofides {

    private static Node[] nodes;

    public static int MAX_SECONDS = 1800;

    public static int distanceGraph[][];

    private static List<Integer> optimizedTour;

    private static int fitness = Integer.MAX_VALUE;

    public static Deadline deadline;

    public static void main(String[] args) {
        deadline = new Deadline();
        CityService.readInputValues();
        CityService.buildGraph();
        distanceGraph = CityService.graph;
        PrimsAlgorithm prims = new PrimsAlgorithm();
        prims.setGraph(distanceGraph);
        prims.setNodes(CityService.nodes);
        prims.setNumberOfCities(CityService.numberOfCities);
        prims.runAlgorithm();
        List<Integer> doubleAlgorithm = prims.doubleAlgo();
         MinWeightPerfectMatching findMatch = new MinWeightPerfectMatching();
         nodes = prims.getNodes();

       findMatch.setDistanceGraph(distanceGraph);
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
        nearestNeighbour.setGraph(distanceGraph);
        nearestNeighbour.runAlgorithm();
        List<Integer> naive = nearestNeighbour.getTour();
        Randomization randomization = new Randomization();
        List<Integer> randomizedTourEulers = new ArrayList<>();
        int fitnessEulers = Integer.MAX_VALUE;
       if (!eulerTour.isEmpty()) {
          List<Integer> twoOptEulers = performTwoOpt(eulerTour);
          twoOptEulers = performTwoHOpt(twoOptEulers);
          perform3Opt(twoOptEulers);

        }
        List<Integer> twoOptDA = performTwoOpt(doubleAlgorithm);
        twoOptDA = performTwoHOpt(twoOptDA);
        perform3Opt(twoOptDA);
        List<Integer> twoOptNaive =  performTwoOpt(naive);
        twoOptNaive =  performTwoHOpt(twoOptNaive);
        perform3Opt(twoOptNaive);
        randomization.setIndices(optimizedTour);
        //System.out.println("Best Fitness.."+calculateNewDistance(optimizedTour));
        randomization.runAlgorithm().forEach(System.out::println);


    }

    public static int calculateNewDistance(List<Integer> newTour) {
        int distance = 0;
        for (int i = 0; i < newTour.size() - 1; i++) {
            int j = i + 1;
            distance += Christofides.distanceGraph[newTour.get(i)][newTour.get(j)];
        }

        return distance + Christofides.distanceGraph[newTour.get(0)][newTour.get(newTour.size() - 1)];

    }

    private static List<Integer> performTwoOpt(List<Integer> indices) {

        TwoOptimization twoOpt = new TwoOptimization();
        twoOpt.setFitness(calculateNewDistance(indices));
        twoOpt.setDistanceGraph(distanceGraph);
        List<Integer> twoOptOptimized =  twoOpt.doOpt(indices);
        if (twoOpt.getFitness() < fitness) {
            optimizedTour = new ArrayList<>(twoOptOptimized);
            fitness = twoOpt.getFitness();

        }
        return twoOpt.doOpt(indices);

    }

    private static List<Integer> performTwoHOpt(List<Integer> indices) {

        TwoHOptimization twoHOpt = new TwoHOptimization();
        twoHOpt.setFitness(calculateNewDistance(indices));
        twoHOpt.setDistanceGraph(distanceGraph);
        Integer[] indicesArray = indices.toArray(new Integer[indices.size()]);
        List<Integer> tempOptimizedTour   = Arrays.asList(twoHOpt.doOpt(indicesArray));;
        if (calculateNewDistance(tempOptimizedTour) < fitness) {
            optimizedTour = new ArrayList<>(tempOptimizedTour);
            fitness = calculateNewDistance(tempOptimizedTour);

        }
        return tempOptimizedTour;

    }

    private static List<Integer> perform3Opt(List<Integer> indices) {

        if(indices.size()<=300) {

        ThreeOptmization threeOpt = new ThreeOptmization();
        threeOpt.setDistanceGraph(distanceGraph);
        List<Integer> tempOptimizedTour   = threeOpt.doOpt(indices);
        if (calculateNewDistance(tempOptimizedTour) < fitness) {
            optimizedTour = new ArrayList<>(tempOptimizedTour);
            fitness = calculateNewDistance(tempOptimizedTour);

        }
            return tempOptimizedTour;
        }
        return null;

    }

}
