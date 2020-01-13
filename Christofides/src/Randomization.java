import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Randomization {

    private int distanceGraph[][];

    private static int NUMBER_OF_TIMES = 1000;

    private static int NUMBER_OF_SWAPS = 100;

    public int[][] getDistanceGraph() {
        return distanceGraph;
    }

    public void setDistanceGraph(int[][] distanceGraph) {
        this.distanceGraph = distanceGraph;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public List<Integer> getNewTour() {
        return newTour;
    }

    private int bestFitness;

    private List<Integer> newTour;


    private List<Integer> indices;

    public List<Integer> runAlgorithm() {

        newTour = new ArrayList<>(indices);
        List<Integer> bestTour  = indices;
        int counter = 0;
        bestFitness = Christofides.calculateNewDistance(newTour);
        int tempBestFitness;
        while(counter<NUMBER_OF_TIMES) {
            counter++;
           doRandomSwaps();
            tempBestFitness = Christofides.calculateNewDistance(newTour);
            if(tempBestFitness<bestFitness) {
                bestFitness = tempBestFitness;
                bestTour = new ArrayList<>(newTour);
            }
        }

        return bestTour;
    }

    public void doRandomSwaps() {
        int counter=0;
        while(counter<NUMBER_OF_SWAPS) {
            counter++;
            Random random = new Random();
            int i = random.nextInt(CityService.numberOfCities);
            int j = random.nextInt(CityService.numberOfCities);
            Collections.swap(newTour,i,j);
        }
    }
}
