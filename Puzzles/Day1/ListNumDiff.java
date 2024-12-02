import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ListNumDiff {

    private static final String FILENAME = "input.txt";
    private static final int COLUMN_LENGTH = 1000;

    public static void main(String[] args) {
        final int[] leftColumn = new int[COLUMN_LENGTH];
        final int[] rightColumn = new int[COLUMN_LENGTH];
        final HashMap<Integer, Integer> similarityMap = new HashMap<>();

        final Scanner scanner = getFileScanner();

        for (int i = 0; i < COLUMN_LENGTH; i++) {
            leftColumn[i] = scanner.nextInt();
            rightColumn[i] = scanner.nextInt();
            similarityMap.putIfAbsent(rightColumn[i], 0);
            similarityMap.computeIfPresent(rightColumn[i], (k,v) -> v + 1);
        }

        // We can just pre sort it honestly
        Arrays.sort(leftColumn);
        Arrays.sort(rightColumn);

        System.out.println(String.format("Sum of Differences: %d", getSumOfDifference(leftColumn, rightColumn)));
        System.out.println(String.format("Similarity Score: %d", getSimilarityScore(leftColumn, similarityMap)));
    }

    private static int getSumOfDifference(final int[] leftColumn, final int[] rightColumn) {
        int sumOfDifference = 0;

        for (int i = 0; i < COLUMN_LENGTH; i++) {
            sumOfDifference += Math.abs(leftColumn[i] - rightColumn[i]);
        }

        return sumOfDifference;
    }

    private static int getSimilarityScore(final int[] leftColumn, final HashMap<Integer, Integer> similarityMap) {
        int similarityScore = 0;

        for (int i = 0; i < COLUMN_LENGTH; i++) {
            similarityScore += leftColumn[i] * similarityMap.getOrDefault(leftColumn[i], 0);
        }

        return similarityScore;
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