import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOne {
    public static void main(String[] args) {
        String fileName = "day1.txt"; 
        try {
            int totalSumPartOne = calculateSumPartOne(fileName);
            System.out.println("Part 1 Result: " + totalSumPartOne);

            int totalSumPartTwo = calculateSumPartTwo(fileName);
            System.out.println("Part 2 Result: " + totalSumPartTwo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int calculateSumPartOne(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int totalSum = 0;

        while ((line = reader.readLine()) != null) {
            totalSum += getCalibrationValuePartOne(line);
        }

        reader.close();
        return totalSum;
    }

    private static int getCalibrationValuePartOne(String line) {
        char[] chars = line.toCharArray();
        char firstDigit = ' ', lastDigit = ' ';

        for (char c : chars) {
            if (Character.isDigit(c)) {
                if (firstDigit == ' ') {
                    firstDigit = c;
                }
                lastDigit = c;
            }
        }

        if (firstDigit != ' ' && lastDigit != ' ') {
            return Integer.parseInt("" + firstDigit + lastDigit);
        }

        return 0;
    }

    private static int calculateSumPartTwo(String fileName) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get(fileName));
        inputs.removeIf(String::isEmpty);

        Map<Character, String> digits = new HashMap<>();
        digits.put('1', "one");
        digits.put('2', "two");
        digits.put('3', "three");
        digits.put('4', "four");
        digits.put('5', "five");
        digits.put('6', "six");
        digits.put('7', "seven");
        digits.put('8', "eight");
        digits.put('9', "nine");

        int sum = 0;

        for (String line : inputs) {
            Character first = null, last = null;

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (digits.containsKey(ch)) {
                    if (first == null) {
                        first = ch;
                    }
                    last = ch;
                } else {
                    for (Map.Entry<Character, String> entry : digits.entrySet()) {
                        if (i + entry.getValue().length() <= line.length()) {
                            String sub = line.substring(i, i + entry.getValue().length());
                            if (sub.equals(entry.getValue())) {
                                if (first == null) {
                                    first = entry.getKey();
                                }
                                last = entry.getKey();
                                break;
                            }
                        }
                    }
                }
            }

            if (first != null && last != null) {
                int n = Integer.parseInt("" + first + last);
                sum += n;
            }
        }

        return sum;
    }
}
