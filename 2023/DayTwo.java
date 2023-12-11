import java.io.*;
import java.util.regex.*;

public class DayTwo {
    public static void main(String[] args) throws IOException {
        String fileName = "day2.txt"; // Replace with the path to your file
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String line;
        int sumOfIds = 0;
        long sumOfPowers = 0;
        while ((line = br.readLine()) != null) {
            // Part 1: Calculate sum of IDs if the game is possible
            if (isGamePossible(line)) {
                sumOfIds += getGameId(line);
            }

            // Part 2: Calculate sum of powers of minimum sets
            int[] minCubes = findMinimumCubes(line);
            long power = (long) minCubes[0] * minCubes[1] * minCubes[2];
            sumOfPowers += power;
        }
        br.close();

        System.out.println("Part 1 Result: " + sumOfIds);
        System.out.println("Part 2 Result: " + sumOfPowers);
    }

    private static boolean isGamePossible(String game) {
        int maxRed = 12, maxGreen = 13, maxBlue = 14;
        Pattern pattern = Pattern.compile("(\\d+) red|green|blue");
        Matcher matcher = pattern.matcher(game);

        while (matcher.find()) {
            int red = 0, green = 0, blue = 0;
            for (String group : game.split(";")) {
                red = countCubes(group, "red");
                green = countCubes(group, "green");
                blue = countCubes(group, "blue");

                if (red > maxRed || green > maxGreen || blue > maxBlue) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int getGameId(String game) {
        Pattern idPattern = Pattern.compile("Game (\\d+):");
        Matcher idMatcher = idPattern.matcher(game);
        if (idMatcher.find()) {
            return Integer.parseInt(idMatcher.group(1));
        }
        return 0;
    }

    private static int[] findMinimumCubes(String game) {
        int minRed = 0, minGreen = 0, minBlue = 0;
        for (String group : game.split(";")) {
            minRed = Math.max(minRed, countCubes(group, "red"));
            minGreen = Math.max(minGreen, countCubes(group, "green"));
            minBlue = Math.max(minBlue, countCubes(group, "blue"));
        }
        return new int[]{minRed, minGreen, minBlue};
    }

    private static int countCubes(String group, String color) {
        Pattern pattern = Pattern.compile("(\\d+) " + color);
        Matcher matcher = pattern.matcher(group);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }
}
