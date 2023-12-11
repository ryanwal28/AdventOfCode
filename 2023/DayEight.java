import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayEight {
    public static void main(String[] args) {
        try {
            // Reading the input file into a list of strings
            List<String> input = Files.readAllLines(Paths.get("day8.txt"));

            // Convert L to 0 and R to 1 in the first line of the file for directional moves
            String moves = input.get(0).replace("L", "0").replace("R", "1");

            // A map to store node transitions
            Map<String, String[]> nodes = new HashMap<>();

            // Parsing the nodes from the input, starting from the third line
            for (String line : input.subList(2, input.size())) {
                String[] node = line.split(" = ");
                String[] transitions = node[1].replace("(", "[\"").replace(")", "\"]")
                                              .replace(", ", "\", \"")
                                              .replaceAll("[\\[\\]\"]", "")
                                              .split(", ");
                nodes.put(node[0], transitions);
            }

            // Logic for Part 1: Follow the moves and count the number of transitions
            String current = "AAA";
            int resultPart1 = 0;
            while (!current.equals("ZZZ")) {
                for (char move : moves.toCharArray()) {
                    current = nodes.get(current)[Character.getNumericValue(move)];
                    resultPart1++;
                    if (current.equals("ZZZ")) break;
                }
            }
            System.out.println("Part 1 Result: " + resultPart1);

            // Logic for Part 2: Initialize a list of starting nodes ending with 'A'
            List<String> currentPart2 = new ArrayList<>();
            for (String key : nodes.keySet()) {
                if (key.endsWith("A")) {
                    currentPart2.add(key);
                }
            }

            // Perform moves for each starting node until they end with 'Z'
            int[] resultPart2 = new int[currentPart2.size()];
            for (int i = 0; i < currentPart2.size(); i++) {
                while (!currentPart2.get(i).endsWith("Z")) {
                    for (char move : moves.toCharArray()) {
                        currentPart2.set(i, nodes.get(currentPart2.get(i))[Character.getNumericValue(move)]);
                        resultPart2[i]++;
                        if (currentPart2.get(i).endsWith("Z")) break;
                    }
                }
            }
            System.out.println("Part 2 Result: " + lcm(resultPart2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Compute the Least Common Multiple (LCM) of a set of numbers
    private static long lcm(int... numbers) {
        long result = 1;
        for (int number : numbers) {
            result = lcm(result, number);
        }
        return result;
    }

    // Compute the LCM of two numbers
    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    // Compute the Greatest Common Divisor (GCD) of two numbers
    private static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}
