import java.util.Collections;
import java.util.List;

public class CityService {

    public static void printCities(List<City> cities) {

        for(City city:cities) {
            System.out.println(cities.indexOf(city));
        }
    }
}
