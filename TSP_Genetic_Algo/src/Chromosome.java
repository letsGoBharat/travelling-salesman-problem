import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chromosome {

    public List<City> getGenes() {
        return genes;
    }

    private List<City> genes;

    private double fitness;

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Chromosome(int size){

        genes = new ArrayList<>(size);
        for(int i=0;i<size;i++) {
            genes.add(new City());
        }

    }

    public Chromosome(List<City> cities) {

        genes = new ArrayList<>(cities);
        Collections.shuffle(genes);
    }

    public double calculateFitness() {

        double distance = 0;
        City city1;
        City city2;
        double distanceBetween2Cities;
        double resultX;
        double resultY;
        for(int i=0;i<genes.size()-1;i++) {

                int j=i+1;
                city1 = genes.get(i);
                city2 = genes.get(j);
                resultX = Math.abs(city1.getxCoordinate()-city2.getxCoordinate());
                resultY = Math.abs(city1.getyCoordinate()-city2.getyCoordinate());
                distanceBetween2Cities = Math.sqrt(resultX*resultX+resultY*resultY);
                distance+=distanceBetween2Cities;


        }

        city1 = genes.get(0);
        city2 = genes.get(genes.size()-1);
        resultX = Math.abs(city1.getxCoordinate()-city2.getxCoordinate());
        resultY = Math.abs(city1.getyCoordinate()-city2.getyCoordinate());
        distanceBetween2Cities = Math.sqrt(resultX*resultX+resultY*resultY);
        distance+=distanceBetween2Cities;

        return distance;
    }


}
