import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jdk.jfr.Percentage;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {


        float randomMagicNumber = 1.5f;


        String inputFiles = "input/a.txt";

        final int time = Parser.getSimulationTime(inputFiles);
        final int nIntersection = Parser.getNumberOfIntersections(inputFiles);
        final int nStreets = Parser.getNumberOfStreets(inputFiles);
        final int nCars = Parser.getNumberOfCars(inputFiles);


        Map<String,Street> streets = Parser.getStreets(inputFiles, 1, nStreets);
        List<Car> cars = Parser.getCars(inputFiles, 1 + nStreets , nCars, streets);
        Map<Integer, CrossRoad> crossRoads = Parser.getCrossRoads(streets);
        System.out.println(crossRoads);

        Map<String, Integer> streetTraffic = new HashMap<>();
        for(Car car: cars) {
            for(Street street : car.route) {
                Integer traffic = streetTraffic.getOrDefault(street.name, 0);
                streetTraffic.put(street.name, traffic + 1);
            }
        }

        // Calculate
        for(CrossRoad crossRoad: crossRoads.values()) {
            for(String streetName: crossRoad.priorities.keySet()) {
                Integer traffic = streetTraffic.getOrDefault(streetName, 0);
                crossRoad.priorities.put(streetName, traffic);
            }
        }

        for(CrossRoad crossRoad: crossRoads.values()) {
            Integer totalCarsFromAllStreets = crossRoad.priorities.values().stream().reduce(0, Integer::sum);
            // System.out.println("++ " + totalCarsFromAllStreets);
            
            for(String streetName: crossRoad.priorities.keySet()) {
                Integer traffic = streetTraffic.getOrDefault(streetName, 0);
                // System.out.println(streetName + " %% " + traffic/totalCarsFromAllStreets);
                crossRoad.percentages.put(streetName, Float.valueOf((float)traffic.intValue()/totalCarsFromAllStreets.intValue()));
            }
        }

        PrintStream out = new PrintStream( new FileOutputStream(file + ".out") );

        //write result to output file
        PrintStream out = new PrintStream( new FileOutputStream(file + ".out") );


        for(CrossRoad crossRoad: crossRoads.values()) {
            for(Map.Entry<String, Float> entry :crossRoad.percentages.entrySet()) {
                int seconds = Math.round( randomMagicNumber * entry.getValue() );

                List<String> trafficLights = new ArrayList<>();
                if(seconds != 0) {
                    trafficLights = line + "\n" + entry.getKey() + " " + seconds;
                }
            }
        }

        //write result to output file
        PrintStream out = new PrintStream( new FileOutputStream(file + ".out") );
        out.println(firstLine);
        out.close();
    }
}