import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TravellingSalesPerson2D {

    /*
    Try to use a genetic algorithm implementation to solve the TSP problem.
     */

    private static Deadline deadline;

    public static void main(String[] args) {

        List<City> cities = readInputValues();
        deadline = new Deadline();

        if(!cities.isEmpty()) {
            getBestCityPath(cities);
        }

    }


    private static void calculateDistance(List<City> cities) {

        int[] indexes = new int[]{0,8,5,4,3,9,6,2,1,7};

        double distance = 0;
        City city1;
        City city2;
        double distanceBetween2Cities;
        double resultX;
        double resultY;

        for(int i=0;i<indexes.length-1;i++) {

            int j=i+1;
            city1 = cities.get(indexes[i]);
            city2 = cities.get(indexes[j]);
            resultX = Math.abs(city1.getxCoordinate()-city2.getxCoordinate());
            resultY = Math.abs(city1.getyCoordinate()-city2.getyCoordinate());
            distanceBetween2Cities = Math.sqrt(resultX*resultX+resultY*resultY);
            distance+=distanceBetween2Cities;


        }
        city1 = cities.get(indexes[0]);
        city2 = cities.get(indexes[indexes.length-1]);
        resultX = Math.abs(city1.getxCoordinate()-city2.getxCoordinate());
        resultY = Math.abs(city1.getyCoordinate()-city2.getyCoordinate());
        distanceBetween2Cities = Math.sqrt(resultX*resultX+resultY*resultY);
        distance+=distanceBetween2Cities;
        System.out.println("Distance..."+distance);
    }

    private static void getBestCityPath(List<City> cities) {

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        geneticAlgorithm.generateInitialPopulation(cities);
        double fitnessValue = Integer.MAX_VALUE;
        double fitness = Integer.MAX_VALUE;
        int iterations = 0;

        do {
            fitnessValue = fitness;
            geneticAlgorithm.crossOver();
            geneticAlgorithm.mutation();
            geneticAlgorithm.selection();
            fitness = geneticAlgorithm.getBestFitnessValue();


        }while( fitness<=fitnessValue  && Math.abs(deadline.remainingMs())<Constants.MAX_MS);

        Chromosome best = geneticAlgorithm.getBest();
        for(City city:best.getGenes()) {

            System.out.println(cities.indexOf(city));
        }
    }

    private static List<City> readInputValues() {

        List<City> cities = new ArrayList<>();
        Scanner readInput = new Scanner(System.in);
        int size = Integer.parseInt(readInput.nextLine());
            cities = new ArrayList<>(size);
            String[] cityCoordinates;
            City city;
            while (size > 0) {
                cityCoordinates = readInput.nextLine().split(" ");

                city = new City(Double.parseDouble(cityCoordinates[0]), Double.parseDouble(cityCoordinates[1]));
                cities.add(city);
                size--;
            }


        return cities;
    }
}
