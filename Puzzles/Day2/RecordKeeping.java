import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class RecordKeeping {

    private static final String FILENAME = "input.txt";
    private static final int RECORDS_LENGTH = 1000;
    private static final int MIN_STEP = 1;
    private static final int MAX_STEP = 3;

    public static void main(String[] args) {

        System.out.println(String.format("Valid Record Score: %d", getNumberOfValidRecords()));
    }

    private static int getNumberOfValidRecords() {
        final Scanner scanner = getFileScanner();

        int validScore = 0;

        for (int i = 0; i < RECORDS_LENGTH; i++) {
            final int[] record = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (record[0] < record[1]) { // increasing
                if (isValidIncreasingSequence(record)) {
                    validScore++;
                }
            } else if (record[0] > record[1]) { // decreasing
                if (isValidDecreasingSequence(record)) {
                    validScore++;
                }
            }
        }

        return validScore;
    }

    private static boolean isValidIncreasingSequence(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i+1]) {
                return false;
            }

            if (arr[i+1] - arr[i] > MAX_STEP || arr[i+1] - arr[i] < MIN_STEP) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidDecreasingSequence(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] <= arr[i+1]) {
                return false;
            }

            if (arr[i] - arr[i+1] > MAX_STEP || arr[i] - arr[i+1] < MIN_STEP) {
                return false;
            }
        }

        return true;
    }

    private static Scanner getFileScanner() {
        try {
            final Scanner scanner = new Scanner(new File(FILENAME));
            return scanner;
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Something wrong happened! %s", e));
            return null;
        }
    }
}