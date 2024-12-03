import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class RecordKeeping {

    private static final String FILENAME = "input.txt";
    private static final int RECORDS_LENGTH = 1000;
    private static final int MIN_STEP = 1;
    private static final int MAX_STEP = 3;
    private static final boolean DAMPENED = true;
    private static final boolean NOT_DAMPENED = false;

    public static void main(String[] args) {

        System.out.println(String.format("Valid Record Score: %d", getNumberOfValidRecords(NOT_DAMPENED)));
        System.out.println(String.format("... With Dampening: %d", getNumberOfValidRecords(DAMPENED)));
    }

    private static int getNumberOfValidRecords(boolean dampeningSetting) {
        final Scanner scanner = getFileScanner();

        int validScore = 0;

        for (int i = 0; i < RECORDS_LENGTH; i++) {
            final int[] record = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            final int[] reversed = reversedArray(record);
            int trend = checkTrend(record);
            if (trend > 0) {
                if (isValidIncreasingSequence(record, dampeningSetting) || isValidDecreasingSequence(reversed, dampeningSetting)) {
                    validScore++;
                }
            } else if (trend < 0) {
                if (isValidDecreasingSequence(record, dampeningSetting) || isValidIncreasingSequence(reversed, dampeningSetting)) {
                    validScore++;
                }
            }
        }

        return validScore;
    }

    private static int[] reversedArray(int[] arr) {
        final int[] reverse = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reverse[i] = arr[arr.length - 1 - i];
        }
        return reverse;
    }

    private static int checkTrend(int[] arr) {
        int increasing = 0;
        int decreasing = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i+1]) {
                increasing++;
            } else if (arr[i] > arr[i+1]) {
                decreasing++;
            }
        }

        if (increasing > decreasing) {
            return 1;
        } else if (increasing < decreasing) {
            return -1;
        } else {
            return 0;
        }
    }

    private static boolean isValidIncreasingSequence(int[] arr, boolean dampened) {
        boolean pardoned = true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i+1]) {
                if (dampened && pardoned) {
                    pardoned = false;
                    arr[i+1] = arr[i];
                    continue;
                }
                return false;
            }

            if (arr[i+1] - arr[i] > MAX_STEP || arr[i+1] - arr[i] < MIN_STEP) {
                if (dampened && pardoned) {
                    pardoned = false;
                    arr[i+1] = arr[i];
                    continue;
                }
                return false;
            }
        }

        return true;
    }

    private static boolean isValidDecreasingSequence(int[] arr, boolean dampened) {
        boolean pardoned = true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] <= arr[i+1]) {
                if (dampened && pardoned) {
                    pardoned = false;
                    arr[i+1] = arr[i];
                    continue;
                }
                return false;
            }

            if (arr[i] - arr[i+1] > MAX_STEP || arr[i] - arr[i+1] < MIN_STEP) {
                if (dampened && pardoned) {
                    pardoned = false;
                    arr[i+1] = arr[i];
                    continue;
                }
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