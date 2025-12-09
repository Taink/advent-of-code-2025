package taink.practice;

import taink.practice.common.Triple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day8 {
    static void main() {
        Path path = Paths.get("src/main/resources/input.txt");
//        Path path = Paths.get("src/test/resources/small_input.txt");
        List<String> input;
        try {
            input = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Triple<Integer, Integer, Integer>> triples = new ArrayList<>();
        for (String line : input) {
            String[] coords = line.split(",");
            Triple<Integer, Integer, Integer> coordsTriple = new Triple<>(
                    Integer.parseInt(coords[0]),
                    Integer.parseInt(coords[1]),
                    Integer.parseInt(coords[2]));
            triples.add(coordsTriple);
        }

        IO.println(triples);

        Map<Set<Triple<Integer, Integer, Integer>>, Integer> distanceMatrix = new HashMap<>();
        for (int i = 0; i < triples.size() - 1; i++) {
            var baseTriple = triples.get(i);
            for (int j = i + 1; j < triples.size(); j++) {
                var otherTriple = triples.get(j);
                var pair = Set.of(baseTriple, otherTriple);
                distanceMatrix.computeIfAbsent(pair, _ -> distance(baseTriple, otherTriple));
            }
        }

        IO.println("Computed "+distanceMatrix.size()+" euclidian distances for "+triples.size()+" triples");

        List<Set<Triple<Integer, Integer, Integer>>> circuits = new ArrayList<>();
        var analyzedDistanceMatrix = distanceMatrix.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).toList();
        for (var distanceByPair : analyzedDistanceMatrix) {
            var pair = distanceByPair.getKey().iterator();
            var first = pair.next();
            var second = pair.next();
            Set<Triple<Integer, Integer, Integer>> firstMatch = null;
            for (Set<Triple<Integer, Integer, Integer>> circuit : List.copyOf(circuits)) {
                if (circuit.contains(first) || circuit.contains(second)) {
                    if (firstMatch != null) {
                        firstMatch.addAll(circuit);
                        circuits.remove(circuit);
                    }
                    circuit.add(first);
                    circuit.add(second);
                    firstMatch = circuit;
                }
            }
            if (firstMatch == null) {
                var newSet = new HashSet<>(distanceByPair.getKey());
                circuits.add(newSet);
            }
            if (circuits.size() == 1 && circuits.getFirst().containsAll(triples)) {
                IO.println("All junction boxes connected!");
                IO.println("Last connection: "+distanceByPair.getKey());
                IO.println("Multiplying the x coords of the last two junction boxes produces: "+first.x() * second.x());
                break;
            }
        }

        var circuitsBySizeDesc = circuits.stream().map(Set::size).sorted(Collections.reverseOrder());
        var sizeProductOfThreeLargestCircuits = circuitsBySizeDesc.limit(3).reduce(1, (acc, b) -> acc * b);
        IO.println("Multiplying together the sizes of the three largest circuits produces: "+sizeProductOfThreeLargestCircuits);
    }

    static int distance(Triple<Integer, Integer, Integer> coords1, Triple<Integer, Integer, Integer> coords2) {
        double result = Math.sqrt(Math.powExact((long) (coords2.x() - coords1.x()), 2) +
                Math.powExact((long) (coords2.y() - coords1.y()), 2) +
                Math.powExact((long) (coords2.z() - coords1.z()), 2)
        );
        return (int) result;
    }
}
