import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class XMASFinder {

    private static final String FILENAME = "input.txt";
    private static final int LENGTH = 140;
    private static final String TARGET = "XMAS"; // edit me
    private static final char TARGET_FIRST_LETTER = TARGET.charAt(0);

    public static void main(String[] args) {

        final char[][] input = bootstrapAndReturnFileContent();

        System.out.println(String.format("# of XMAS: %d", getSumOfSequences(input)));
        System.out.println(String.format("# of X-MAS: %d", getSumOfCrossMas(input)));
    }

    private static int getSumOfSequences(final char[][] data) {
        int sum = 0;
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] == TARGET_FIRST_LETTER) {
                    sum += getScore(i, j, data);
                }
            }
        }


        return sum;
    }

    private static int getScore(final int pos_i, final int pos_j, final char[][] data) {
        int sum = 0;
        sum += getVerticalDownwardsScore(pos_i, pos_j, data);
        sum += getVerticalUpwardsScore(pos_i, pos_j, data);
        sum += getHorizontalLeftwardsScore(pos_i, pos_j, data);
        sum += getHorizontalRightwardsScore(pos_i, pos_j, data);
        sum += getDiagonalQ1Score(pos_i, pos_j, data);
        sum += getDiagonalQ2Score(pos_i, pos_j, data);
        sum += getDiagonalQ3Score(pos_i, pos_j, data);
        sum += getDiagonalQ4Score(pos_i, pos_j, data);
        return sum;
    }

    private static int getSumOfCrossMas(final char[][] data) {
        int sum = 0;
        
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] == 'A') {
                    sum += getCrossMasScore(i, j, data);
                }
            }
        }


        return sum;
    }

    private static int getCrossMasScore(final int pos_i, final int pos_j, final char[][] data) {
        if (stonkMas(pos_i, pos_j, data) && stinkMas(pos_i, pos_j, data)) {
            return 1;
        }
        return 0;
    }

    private static boolean stonkMas(final int pos_i, final int pos_j, final char[][] data) {
        boolean mas = data[pos_i-1][pos_j-1] == 'M' && data[pos_i+1][pos_j+1] == 'S';
        boolean sam = data[pos_i-1][pos_j-1] == 'S' && data[pos_i+1][pos_j+1] == 'M';
        return mas || sam;
    }

    private static boolean stinkMas(final int pos_i, final int pos_j, final char[][] data) {
        boolean mas = data[pos_i+1][pos_j-1] == 'M' && data[pos_i-1][pos_j+1] == 'S';
        boolean sam = data[pos_i+1][pos_j-1] == 'S' && data[pos_i-1][pos_j+1] == 'M';
        return mas || sam;
    }

    private static int getVerticalUpwardsScore(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i-i][pos_j] != TARGET.charAt(i)) {
                return 0;
            }
        }
        System.out.println(String.format("Vertical Upwards at %d, %d", pos_i, pos_j));
        return 1;
    }

    private static int getVerticalDownwardsScore(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i+i][pos_j] != TARGET.charAt(i)) {
                return 0;
            }
        }
        System.out.println(String.format("Vertical Downwards at %d, %d", pos_i, pos_j));

        return 1;
    }
    private static int getHorizontalLeftwardsScore(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i][pos_j-i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        System.out.println(String.format("Horizontal Leftwards at %d, %d", pos_i, pos_j));

        return 1;
    }
    private static int getHorizontalRightwardsScore(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i][pos_j+i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        System.out.println(String.format("Horizontal Rightwards at %d, %d", pos_i, pos_j));

        return 1;
    }
    private static int getDiagonalQ1Score(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i-i][pos_j+i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        return 1;
    }
    private static int getDiagonalQ2Score(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i-i][pos_j-i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        return 1;
    }
    private static int getDiagonalQ3Score(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i+i][pos_j-i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        return 1;
    }
    private static int getDiagonalQ4Score(final int pos_i, final int pos_j, final char[][] data) {
        for (int i = 1; i < TARGET.length(); i++) {
            if (data[pos_i+i][pos_j+i] != TARGET.charAt(i)) {
                return 0;
            }
        }
        return 1;
    }

    private static char[][] bootstrapAndReturnFileContent() {
        final Scanner scanner = getFileScanner();

        char[][] input = new char[LENGTH+TARGET.length()*2-2][LENGTH+TARGET.length()*2-2];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                input[i][j] = '#';
            }
        }

        for (int i = 0; i < LENGTH; i++) {
            char[] line = scanner.nextLine().toCharArray();
            for (int j = 0; j < line.length; j++) {
                input[TARGET.length() + i-1][TARGET.length() + j-1] = line[j];
            }

        }

        for (int i = 0; i < TARGET.length()-1; i++) {
            for (int j = 0; j < input.length; j++) {
                input[i][j] = '#';
            }
        }

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                System.out.print(input[i][j]);
            }
            System.out.println();
        }

        return input;
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