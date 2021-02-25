import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser {

    public static int getSimulationTime(final String inputFile) throws Exception {
        Scanner scanner = new Scanner(new File(inputFile));
        String line = scanner.nextLine();
        return Integer.parseInt(line.split(" ")[0]);
    }

    public static int getNumberOfIntersections(final String inputFile) throws Exception {
        Scanner scanner = new Scanner(new File(inputFile));
        String line = scanner.nextLine();
        return Integer.parseInt(line.split(" ")[1]);
    }

    public static int getNumberOfStreets(final String inputFile) throws Exception {
        Scanner scanner = new Scanner(new File(inputFile));
        String line = scanner.nextLine();
        return Integer.parseInt(line.split(" ")[2]);
    }

    public static int getNumberOfCars(final String inputFile) throws Exception {
        Scanner scanner = new Scanner(new File(inputFile));
        String line = scanner.nextLine();
        return Integer.parseInt(line.split(" ")[3]);
    }

    public static Map<String, Street> getStreets(final String inputFile, final int startingLine, final int nStreets)
            throws Exception {

        Scanner scanner = new Scanner(new File(inputFile));
        skipLines(scanner, startingLine);

        Map<String, Street> streets = new HashMap<String, Street>();

        for (int i = 0; i < nStreets; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");

            final Street street = new Street();
            street.startsAt = Integer.parseInt(splitLine[0]);
            street.endsAt = Integer.parseInt(splitLine[1]);
            street.name = splitLine[2];
            street.effort = Integer.parseInt(splitLine[3]);
            streets.put(street.name, street);
        }

        return streets;
    }

    public static List<Car> getCars(final String inputFile, final int startingLine, final int nCars,
            final Map<String, Street> streets) throws Exception {

        Scanner scanner = new Scanner(new File(inputFile));
        skipLines(scanner, startingLine);

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < nCars; i++) {
            String line = scanner.nextLine();
            String[] splitLine = line.split(" ");
            int n = Integer.parseInt(splitLine[0]);

            Car car = new Car();
            car.route = new ArrayList<>();
            for (int j = 1; j <= n; j++) {
                car.route.add(streets.get(splitLine[j]));
            }
            cars.add(car);
        }
        return cars;
    }

    public static Map<Integer, CrossRoad> getCrossRoads(final Map<String, Street> streets) {
        
        Map<Integer, CrossRoad> crossRoads = new HashMap<>();

        for(Street street: streets.values()) {
            CrossRoad crossroad = crossRoads.getOrDefault(Integer.valueOf(street.endsAt), new CrossRoad());
            crossroad.priorities.put(street.name, 0);
            crossroad.id = street.endsAt;
            crossRoads.put(crossroad.id, crossroad);
        }

        return crossRoads;
    }

    private static void skipLines(Scanner scanner, int linesToSkip) {
        int counter = 0;
        while (counter < linesToSkip) {
            scanner.nextLine();
            counter = counter + 1;
        }
    }
}
