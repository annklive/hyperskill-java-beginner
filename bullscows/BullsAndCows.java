package bullscows;

import java.util.Scanner;
import java.util.Random;

public class BullsAndCows {
    static final int TOTAL = 0;
    static final int BULL = 1;
    static final int COW = 2;
    static final int MAX_SYMBOLS = 36; // 0-9, a-z

    static char[] generateSecretWithTimeStamp(int length) {
        boolean[] usedDigits = new boolean[10];
        char[] secret = new char[length];
        int secretLength = 0;
        while (secretLength < length) {
            String pseudoRandomNumber = String.valueOf(System.nanoTime());
            int i = pseudoRandomNumber.length() - 1;
            while (secretLength < length && i >= 0) {
                int num = Character.getNumericValue(pseudoRandomNumber.charAt(i));
                if (!usedDigits[num]) {
                    usedDigits[num] = true;
                    secret[secretLength] = Character.forDigit(num, 10);
                    secretLength++;
                }
                i--;
            }
        }
        return secret;
    }

    static char[] generateSecret(int length, int numSymbol) {
        boolean[] usedDigits = new boolean[numSymbol];
        char[] secret = new char[length];
        int secretLength = 0;
        Random rnd = new Random();

        while (secretLength < length) {
            int num = rnd.nextInt(0, numSymbol);
            if (!usedDigits[num]) {
                usedDigits[num] = true;
                if (num < 10) {
                    secret[secretLength] = Character.forDigit(num, 10);
                } else {
                    secret[secretLength]  = (char) ('a' + (num - 10));
                }
                secretLength++;
            }
        }
        return secret;
    }

    static char[] parseInput(String input, int secretLength) {
        return input.substring(0, secretLength).toCharArray();
    }

    static int[] gradeInput(char[] secret, char[] guess) {
        int[] grades = new int[3];
        for (int i = 0; i < secret.length; i++) {
            if (guess[i] == secret[i]) {
                grades[BULL]++;
                grades[TOTAL]++;
            } else {
                for (int j = 0; j < secret.length; j++) {
                    if (j != i) {
                        if (guess[i] == secret[j]) {
                            grades[COW]++;
                            grades[TOTAL]++;
                        }
                    }
                }
            }
        }
        return grades;
    }
    static void displayResult(char[] secret, int[] grades) {
        String pattern = "Grade: %s";
        StringBuilder grade = new StringBuilder();
        if (grades[TOTAL] == 0) {
            grade.append("None");
        } else if (grades[BULL] > 0) {
            grade.append(grades[BULL]).append(" bull(s)");
        }
        if (grades[COW] > 0) {
            if (grades[BULL] > 0) {
                grade.append(" and ");
            }
            grade.append(grades[COW]).append(" cow(s)");
        }
        StringBuilder code = new StringBuilder();
        for (char c : secret) {
            code.append(c);
        }
        System.out.printf(pattern, grade, code);
    }

    static String generateSecretStars(int secretLength) {
        StringBuilder sb = new StringBuilder(secretLength);
        for (int i = 0; i < secretLength; i++) {
            sb.append("*");
        }
        return sb.toString();
    }

    static String generatePossibleSymbols(int numSymbols) {
        if (numSymbols <= 10) {
            int maxSymbol = numSymbols - 1;
            return String.format("(0-%d)", maxSymbol);
        } else {
            char maxCharacter = (char) ('a' + (numSymbols - 11));
            return String.format("(0-9, a-%c)", maxCharacter);
        }
    }

    static boolean checkValidSecretLength(int secretLength, int numSymbols) {
        if (secretLength <= 0 || secretLength > numSymbols) {
            System.out.printf(
                    "Error: it's not possible to generate a code with a length of %d with %d unique",
                    secretLength, numSymbols);
            return false;
        }
        return true;

    }
    static boolean checkValidNumberOfSymbols(int numSymbols) {
        if (numSymbols <= 0 || numSymbols > MAX_SYMBOLS) {
            System.out.printf(
                    "Error: maximum number of possible symbols in the code is %d %s.",
                    MAX_SYMBOLS, generatePossibleSymbols(MAX_SYMBOLS));
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        int secretLength, numSymbols;
        try {
            System.out.println("Please, enter the secret code's length:");
            input =  scanner.nextLine();
            secretLength = Integer.parseInt(input);
            System.out.println("Input the number of possible symbols in the code:");
            input =  scanner.nextLine();
            numSymbols = Integer.parseInt(input);
            if (checkValidSecretLength(secretLength, numSymbols) &&
                    checkValidNumberOfSymbols(numSymbols)) {
                if (secretLength > MAX_SYMBOLS) {
                    System.out.printf(
                            "Error: can't generate a secret number with a length > %d because there aren't enough unique digits.",
                            MAX_SYMBOLS);
                } else {
                    char[] secret = generateSecret(secretLength, numSymbols);
                    String secretStars = generateSecretStars(secretLength);
                    String possibleSymbols = generatePossibleSymbols(numSymbols);
                    System.out.printf("The secret is prepared: %s %s.", secretStars, possibleSymbols);

                    System.out.println("Okay, let's start a game!");
                    boolean finished = false;
                    int turn = 1;
                    do {
                        System.out.printf("\nTurn %d:\n", turn);
                        char[] guess = parseInput(scanner.next(), secretLength);
                        int[] grades = gradeInput(secret, guess);
                        displayResult(secret, grades);
                        if (grades[BULL] == secretLength) {
                            finished = true;
                            System.out.println("\nCongratulations! You guessed the secret code.");
                        } else {
                            turn++;
                        }
                    } while (!finished);
                }
            }
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
        }
    }
}

