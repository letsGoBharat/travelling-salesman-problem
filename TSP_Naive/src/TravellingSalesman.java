import java.util.ArrayList;
import java.util.Scanner;

public class TravellingSalesman {
	
	static int size;

	public static void main(String[] args) {
		
		int[] tour = new int[size];
		ArrayList<City> cities  = getInputCities();
		
		tour = calculateTour(cities);
		for(int i=0;i<cities.size();i++) {
			System.out.println(tour[i]);
		}

	}
	
	private static ArrayList<City> getInputCities() {
		
		ArrayList<City> cities;
		Scanner input = new Scanner(System.in);
		size = Integer.parseInt(input.nextLine());
		cities = new ArrayList<City>(size);
		String[] cityCoordinates;
		City city;
		while(size > 0) {
			cityCoordinates = input.nextLine().split(" ");
			city = new City(Double.parseDouble(cityCoordinates[0]), Double.parseDouble(cityCoordinates[1]));
			cities.add(city);
			size--;
		}
		
		return cities;
	}
	
	private static int[] calculateTour(ArrayList<City> cities) {
		int size1 = cities.size();
		int[] tour = new int[size1];
		boolean[] used = new boolean[size1];
		int best;
		
		tour[0] = 0;
		used[0] = true;
		for(int i=1;i < size1;i++) {
			best = -1;
			for(int j=0;j<size1;j++) {
				if((!used[j]) && (best == -1 || calculateDistance(cities.get(tour[i-1]), cities.get(j)) < calculateDistance(cities.get(tour[i-1]), cities.get(best)))) {
					best = j;
				}
			tour[i] = best;
			}
			used[best] = true;
		}
		return tour;
	}
	
	private static double calculateDistance(City city1, City city2) {
		double value1 = city1.getxValue() - city2.getxValue();
		double value2 = city1.getyValue() - city2.getyValue();
		double distance = Math.sqrt((value1*value1) + (value2*value2));
		return distance;
	}

}
