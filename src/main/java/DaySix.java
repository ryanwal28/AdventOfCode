import java.io.*;
import java.util.*;

public class DaySix {
    public static void main(String[] args) throws IOException {
        // Reading the file
        BufferedReader br = new BufferedReader(new FileReader("day6.txt"));
        String timeLine = br.readLine();
        String distanceLine = br.readLine();
        br.close();

        // Part One: Parsing the input for multiple races
        int[] times = Arrays.stream(timeLine.split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(distanceLine.split("\\s+")).skip(1).mapToInt(Integer::parseInt).toArray();

        // Calculating the number of ways for each race
        long totalWaysPartOne = 1;
        for (int i = 0; i < times.length; i++) {
            totalWaysPartOne *= calculateWaysToWin(times[i], distances[i]);
        }

        // Part Two: Parsing the input for the single long race
        long singleTime = Long.parseLong(timeLine.split(":")[1].replaceAll("\\s", ""));
        long singleDistance = Long.parseLong(distanceLine.split(":")[1].replaceAll("\\s", ""));

        // Calculating the number of ways to win the single long race
        long waysToWinSingleRace = calculateWaysToWin(singleTime, singleDistance);

        System.out.println("Part 1 Result: " + totalWaysPartOne);
        System.out.println("Part 2 Result: " + waysToWinSingleRace);
    }

    // Function to calculate the number of ways to beat the record, updated to use long for larger numbers
    public static long calculateWaysToWin(long time, long recordDistance) {
        long waysToWin = 0;
        for (long buttonHoldTime = 0; buttonHoldTime < time; buttonHoldTime++) {
            long distance = buttonHoldTime * (time - buttonHoldTime);
            if (distance > recordDistance) {
                waysToWin++;
            }
        }
        return waysToWin;
    }
}
