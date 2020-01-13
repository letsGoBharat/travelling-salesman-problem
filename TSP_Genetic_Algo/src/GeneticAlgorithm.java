import java.util.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

    private List<Chromosome> population;
    private List<Chromosome> offspring;
    private List<Chromosome> totalPopulation;
    Map<Chromosome,Double> fitnessTotalPopulation = new HashMap<>();

    public Chromosome getBest() {
        return best;
    }

    private Chromosome best;

    public List<Chromosome> generateInitialPopulation(List<City> cities) {
        population = new ArrayList<>(Constants.NUMBER_OF_CHROMOSOMES);
        int counter=0;
        while(counter<Constants.NUMBER_OF_CHROMOSOMES) {
            population.add(new Chromosome(cities));
            counter++;
        }
        return population;
    }

    public void crossOver() {
        offspring = new ArrayList<>();
        int i = 0;
        int j = Constants.NUMBER_OF_CHROMOSOMES - 1;

        while(i + j == Constants.NUMBER_OF_CHROMOSOMES - 1 && i < Constants.NUMBER_OF_CHROMOSOMES / 2) {
            Chromosome parent1 = population.get(i);
            Chromosome parent2 = population.get(j);
            performPMX(parent1, parent2);
            i++;
            j--;
        }
    }

    /*
         For the PMX Crossover we choose two random indexes so that it makes a segment in P1 and P2.
         These segments will be the elements in the corresponding positions in offspring 1 and 2.
         Then we fill rest of the elements from parent 1 to offspring 2 and from parent 2 to offspring 1.
         The segment in P1 and P2 is a map between the elements.
    */
    private void performPMX(Chromosome parent1, Chromosome parent2) {
        Random random = new Random();
        int i = random.nextInt(parent1.getGenes().size());
        int j = random.nextInt(parent2.getGenes().size());
        // Here the map is that key->value and value->key

        if(i>j) {
            //Swap using Bitwise XOR
            i = i^j;
            j = i^j;
            i = i^j;
        }

        Map<City,City> crossoverMap = new HashMap<>();
        Chromosome offSpring1 = new Chromosome(parent2.getGenes().size());
        Chromosome offSpring2 = new Chromosome(parent1.getGenes().size());

        //Set the offsprings' initial elements from P1 and P2 and generate the crossover map
        for(int pointer=i;pointer<=j;pointer++) {
            City geneP1 = parent1.getGenes().get(pointer);
            City geneP2 = parent2.getGenes().get(pointer);
            crossoverMap.put(geneP1,geneP2);
            offSpring1.getGenes().set(pointer,geneP1);
            offSpring2.getGenes().set(pointer,geneP2);
        }

        //Set the rest of the genes.

        for(int pointer = 0;pointer<i;pointer++) {
            setGenes(offSpring1,offSpring2,parent1,parent2,crossoverMap,pointer);
        }

        for(int pointer=j+1;pointer<parent1.getGenes().size();pointer++) {
            setGenes(offSpring1,offSpring2,parent1,parent2,crossoverMap,pointer);
        }

        offspring.add(offSpring1);
        offspring.add(offSpring2);

    }


    private void setGenes(Chromosome offSpring1, Chromosome offSpring2, Chromosome parent1, Chromosome parent2,
                          Map<City, City> crossoverMap, int pointer) {
        City uniqueParentGene;
        City geneP1 = parent1.getGenes().get(pointer);;
        City geneP2 = parent2.getGenes().get(pointer);;
        uniqueParentGene = getUniqueGene(offSpring1, crossoverMap, geneP2, true);
        offSpring1.getGenes().set(pointer, uniqueParentGene);
        uniqueParentGene = getUniqueGene(offSpring2, crossoverMap, geneP1, false);
        offSpring2.getGenes().set(pointer, uniqueParentGene);
    }

    private City getUniqueGene(Chromosome offspring, Map<City, City> crossoverMap,
                                          City parentGene, boolean isKeyToValue) {
        if (offspring.getGenes().contains(parentGene)) {
            City geneMapping = new City();
            if (isKeyToValue) {
                geneMapping = crossoverMap.get(parentGene);
            } else {
                for (Map.Entry<City, City> entry : crossoverMap.entrySet()) {
                    if(entry.getValue().equals(parentGene)) {
                        geneMapping = entry.getKey();
                    }
                }
            }
            return getUniqueGene(offspring,crossoverMap,geneMapping,isKeyToValue);
        }
        return parentGene;
    }

    public void selection() {


            totalPopulation = new ArrayList<>(population);
            totalPopulation.addAll(offspring);
            evaluateTotalPopulation();
            sortTotalPopulation();

            for (int i=0; i < Constants.NUMBER_OF_CHROMOSOMES/2; i++) {
                population.set(i, totalPopulation.get(i));

            }

    }

    private void sortTotalPopulation(){
        fitnessTotalPopulation =  sortPopulation(fitnessTotalPopulation);

        totalPopulation = fitnessTotalPopulation.keySet().stream().collect(Collectors.toList());
    }

    private LinkedHashMap<Chromosome, Double> sortPopulation(Map<Chromosome,Double> fitnessMapPopulation) {
        return fitnessMapPopulation.entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private void evaluateTotalPopulation() {
        fitnessTotalPopulation = new HashMap<>();

        for(Chromosome chromosome: totalPopulation) {
            fitnessTotalPopulation.put(chromosome,chromosome.calculateFitness());
        }
    }


    /*
We will perform the mutation by processing the first half of the fittest parent and generating a random value.
If the random value is > some value (0.7 in this case), we will interchange the positions of the genes based on
a random integer.
 */
    public void mutation() {
        Random random = new Random();
        for(int i = 0; i < Constants.NUMBER_OF_CHROMOSOMES / 2; i++) {
            if(Math.random()>0.7) {
                List<City> genes = offspring.get(i).getGenes();

                for(int counter = 0;counter<2;counter++) {
                    int position1 = random.nextInt(genes.size());
                    int position2 = random.nextInt(genes.size());
                    City geneAtPosition1 = genes.get(position1);
                    City geneAtPosition2 = genes.get(position2);
                    genes.set(position2,geneAtPosition1);
                    genes.set(position1,geneAtPosition2);
                }
            }
        }
    }

    public double getBestFitnessValue() {
        best = fitnessTotalPopulation.entrySet().iterator().next().getKey();
                return fitnessTotalPopulation.get(best);
    }


}
