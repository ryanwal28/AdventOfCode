import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFour {

    public static void main(String[] args) {
        try {
            // Initialize a file and scanner to read from "day4.txt"
            File inputFile = new File("day4.txt");
            Scanner fileScanner = new Scanner(inputFile);
            ArrayList<String> lines = new ArrayList<>();

            // Read all lines from the file into an ArrayList
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
            fileScanner.close(); // Close the scanner

            // Compile a regex pattern to match and extract card data
            Pattern pattern = Pattern.compile("Card\\s+(\\d+):\\s+([\\d\\s]+)\\s*\\|\\s*([\\d\\s]+)");
            int pilePoints = 0; // To store the total points for part 1
            int[] cardInstances = new int[lines.size()]; // To keep track of card instances for part 2
            Arrays.fill(cardInstances, 1); // Initialize card instances to 1

            // Process each line in the ArrayList
            for (String entry : lines) {
                Matcher matcher = pattern.matcher(entry);
                if (matcher.find()) {
                    // Extract card number and groups of numbers
                    int cardNum = Integer.parseInt(matcher.group(1));
                    int[] group1Numbers = Arrays.stream(matcher.group(2).trim().split("\\s+"))
                                                .mapToInt(Integer::parseInt)
                                                .toArray();
                    int[] group2Numbers = Arrays.stream(matcher.group(3).trim().split("\\s+"))
                                                .mapToInt(Integer::parseInt)
                                                .toArray();

                    HashSet<Integer> set1 = new HashSet<>();
                    for (int number : group1Numbers) {
                        set1.add(number); // Add group 1 numbers to a HashSet
                    }

                    int commonElementsCount = 0; // Count of common elements in group 1 and 2
                    for (int number : group2Numbers) {
                        if (set1.contains(number)) {
                            commonElementsCount++;
                        }
                    }

                    // Calculate points based on common elements
                    pilePoints += commonElementsCount >= 1 ? Math.pow(2, commonElementsCount - 1) : 0;

                    // Update card instances for each card number
                    for (int idx = 0; idx < commonElementsCount; idx++) {
                        if (cardNum + idx < cardInstances.length) {
                            cardInstances[cardNum + idx] += cardInstances[cardNum - 1];
                        }
                    }
                }
            }

            // Print the result for part 1 and part 2
            System.out.println("Part 1 Result: " + pilePoints);
            System.out.println("Part 2 Result: " + Arrays.stream(cardInstances).sum());

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            e.printStackTrace();
        }
    }
}
