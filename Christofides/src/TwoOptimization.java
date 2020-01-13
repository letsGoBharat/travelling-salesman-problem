import java.util.ArrayList;
import java.util.List;

public class TwoOptimization {

    /*
    Two Optimization algorithm to optimize the found tour.
     */

    public int getFitness() {
        return fitness;
    }

    public int[][] getDistanceGraph() {
        return distanceGraph;
    }

    public void setDistanceGraph(int[][] distanceGraph) {
        this.distanceGraph = distanceGraph;
    }

    private int distanceGraph[][];

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    private int fitness;

    public List<Integer> doOpt(List<Integer> cities) {
        List<Integer> newTour;

        int newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;

        while (swaps != 0) {
            swaps = 0;


            for (int i = 1; i < cities.size() - 2; i++) {
                for (int j = i + 1; j < cities.size() - 1; j++) {
                    comparisons++;

                    if(distanceGraph[cities.get(i)][cities.get(i-1)] + distanceGraph[cities.get(j+1)][cities.get(j)] >= distanceGraph[cities.get(i)][cities.get(j+1)] + distanceGraph[cities.get(i-1)][cities.get(j)]) {

                       newTour = swap(cities, i, j);
                        newDist = Christofides.calculateNewDistance(newTour);

                        if (newDist < fitness) {
                            cities = newTour;
                            fitness = newDist;
                            swaps++;
                            improve++;
                        }
                    }
                }
            }
            iterations++;
        }
       // System.out.println("Total comparisons made: " + comparisons);
       // System.out.println("Total improvements made: " + improve);
       // System.out.println("Total iterations made: " + iterations);
        return cities;
    }



    private List<Integer> swap(List<Integer> cities, int i, int j) {

        List<Integer> newTour = new ArrayList<>();

        //take array up to first point i and add to newTour
        int size = cities.size();
        for (int k = 0; k <= i - 1; k++) {
            newTour.add(cities.get(k));
        }


        int dec = 0;
        for (int k = i; k <= j; k++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }


        for (int k = j + 1; k < size; k++) {
            newTour.add(cities.get(k));
        }

        return newTour;
    }
}