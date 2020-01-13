import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SimulatedAnealing {
	
	static int numberOfCities;
	static ArrayList<City> cities;
	
	
	private static Travel travel = new Travel(numberOfCities, cities);
	 
	 	public static void main(String[] args) {
	 		HashMap<City, Integer> map = new HashMap<>();
	 		cities = getInputCities();
	 		travel = new Travel(numberOfCities, cities);
	 		ArrayList<City> finalPath = new ArrayList<City>(numberOfCities);
	 		for(int i=0;i<numberOfCities;i++) {
	 			finalPath.add(travel.getCity(i));
	 		}
	 		Travel optimalPath = simulateAnnealing(10, 10000, 0.9995);
	 		for(int i=0;i<numberOfCities;i++) {
	 			System.out.println(finalPath.indexOf(optimalPath.getCity(i)));
	 			finalPath.set(finalPath.indexOf(optimalPath.getCity(i)), null);
	 		}
	 		
	 	}
	 	
	 	private static ArrayList<City> getInputCities() {
			
			ArrayList<City> cities = new ArrayList<City>();
			Scanner input = new Scanner(System.in);
			numberOfCities = Integer.parseInt(input.nextLine());
			String[] cityCoordinates;
			City city;
			int cityCount = numberOfCities;
			while(cityCount > 0) {
				cityCoordinates = input.nextLine().split(" ");
				city = new City(Double.parseDouble(cityCoordinates[0]), Double.parseDouble(cityCoordinates[1]));
				cities.add(city);
				cityCount--;
			}
			
			return cities;
		}

	    public static Travel simulateAnnealing(double startingTemperature, int numberOfIterations, double coolingRate) {
	        double t = startingTemperature;
	        double bestDistance = travel.getDistance();
	        Travel bestSolution = travel;
	        Travel currentSolution = bestSolution;

	        for (int i = 0; i < numberOfIterations; i++) {
	            if (t > 0.1) {
	                currentSolution.swapCities();
	                double currentDistance = currentSolution.getDistance();
	                if (currentDistance < bestDistance) {
	                    bestDistance = currentDistance;
	                } else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()) {
	                    currentSolution.revertSwap();
	                }
	                t *= coolingRate;
	            } else {
	                continue;
	            }
	        }
	        return currentSolution;
	    }

}
