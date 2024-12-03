import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiplierFinder {

    private static final String FILENAME = "input.txt";
    private static final String MULTIPLIER_REGEX = "mul\\(\\d{1,3},\\d{1,3}\\)";
    private static final String DO_MULTIPLIER_REGEX = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don\\'t\\(\\)";

    public static void main(String[] args) {

        final String input = bootstrapAndReturnFileContent();

        System.out.println(String.format("Sum of valid multiply operations: %d", getSumOfValidMultiplyOperations(input, false)));
        System.out.println(String.format("Sum of valid multiply operations with do-check: %d", getSumOfValidMultiplyOperations(input, true)));
    }

    private static int getSumOfValidMultiplyOperations(final String input, boolean checkDo) {
        int sum = 0;
        final Matcher matcher;
        if (checkDo) { // part 2
            boolean doMultiply = true;
            matcher = Pattern.compile(DO_MULTIPLIER_REGEX).matcher(input);
            while (matcher.find()) {
                final String match = matcher.group();

                if (match.startsWith("don't")) {
                    doMultiply = false;
                    continue;
                } else if (match.startsWith("do")) {
                    doMultiply = true;
                    continue;
                }

                if (doMultiply) {
                    final String[] extract = match
                    .substring(4, match.length() - 1)
                    .split(",");
                    
                    final int x = Integer.parseInt(extract[0]);
                    final int y = Integer.parseInt(extract[1]);
                    sum += x * y;
                }
            }
        } else { // part 1
            matcher = Pattern.compile(MULTIPLIER_REGEX).matcher(input);
            while (matcher.find()) {
                final String match = matcher.group();
                final String[] extract = match
                .substring(4, match.length() - 1)
                .split(",");
                
                final int x = Integer.parseInt(extract[0]);
                final int y = Integer.parseInt(extract[1]);
                sum += x * y;
            }
        }

        return sum;
    }

    private static String bootstrapAndReturnFileContent() {
        // we will ignore newlines in this implementation
        final Scanner scanner = getFileScanner();
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        return builder.toString();
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