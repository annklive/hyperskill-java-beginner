package chucknorris;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    final static int BIN_LEN = 7;
    final static Scanner scanner = new Scanner(System.in);

    public static String stringToBinary(String inputString) {
        StringBuilder encodedString = new StringBuilder();
        try {
            byte[] bytes = inputString.getBytes("US-ASCII");
            for (int i = 0; i < bytes.length; i++) {
                StringBuilder binary = new StringBuilder(Integer.toBinaryString(bytes[i]));
                while (binary.length() < BIN_LEN) {
                    binary.insert(0, "0");
                }
                encodedString.append(binary);
            }

        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return encodedString.toString();
    }


    public static char binaryToASCII(String binary) {
        return (char)Integer.parseInt(binary,2);
    }
    public static String binaryToString(String binary) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binary.length(); i+=BIN_LEN) {
            sb.append(binaryToASCII(binary.substring(i, i + BIN_LEN)));
        }
        return sb.toString();
     }

    public static String binaryToChuckNorris(String binary) {
        StringBuilder code = new StringBuilder();
        int i = 0;
        while (i < binary.length()) {
            char ch = binary.charAt(i);
            if (ch == '0') {
                code.append("00 0");
            } else { // ch == '1'
                code.append("0 0");
            }
            i++;
            while (i < binary.length() && ch == binary.charAt(i)) {
                code.append("0");
                i++;
            }
            code.append(" ");
        }
        return code.substring(0, code.length());
    }
    public static String chuckNorrisToBinary(String chuckNorris) {
        StringBuilder decodedString = new StringBuilder();
        Scanner bitScanner = new Scanner(chuckNorris);

        while (bitScanner.hasNext()) {
            String bit = bitScanner.next();
            String numBits = bitScanner.next();
            char ch = bit.length() == 1 ? '1' : '0';
            for (int i = 0; i < numBits.length(); i++) {
                decodedString.append(ch);
            }
        }
        return decodedString.toString();
    }

    public static int chuckNorrisDecodedBinaryStringLength(String[] codes) {
        int mLen = 0;
        for (int i = 1; i < codes.length; i+=2) {
            mLen += codes[i].length();
        }
        return mLen;
    }

    public static boolean correctHeaderCode(String[] codes) {
        for (int i = 0; i < codes.length; i+=2) {
            if (!codes[i].equals("0") && !codes[i].equals("00")) {
                return false;
            }
        }
        return true;
    }
    public static boolean validChuckNorris(String code) {
        String[] codes = code.split(" ");
        if (codes.length % 2 != 0) {
            //System.out.println("Encoded string is not valid : The number of blocks is odd");
            return false;
        }
        if (!Pattern.matches("[0 ]*", code)) {
            //System.out.println("Encoded string is not valid : the encoded message contains characters other than 0 or space");
            return false;
        }
        if (!correctHeaderCode(codes)) {
            //System.out.println("Encoded string is not valid : The first block of each sequence is not 0 or 00");
            return false;
        }
        if (chuckNorrisDecodedBinaryStringLength(codes) % 7 != 0) {
            //System.out.println("Encoded string is not valid : The length of the decoded binary string is not a multiple of 7");
            return false;
        }
        return true;
    }
    public static void encode() {
        System.out.println("Input string:");
        String inputString = scanner.nextLine();
        System.out.println("Encoded string:");

        String binaryInput = stringToBinary(inputString);
        String chuckNorris = binaryToChuckNorris(binaryInput);
        System.out.println(chuckNorris);
    }

    public static void decode() {
        System.out.println("Input encoded string:");
        String inputString = scanner.nextLine();
        if (validChuckNorris(inputString)) {
            System.out.println("Decoded string:");
            String binaryString = chuckNorrisToBinary(inputString);
            String decodedString = binaryToString(binaryString);
            System.out.println(decodedString);
        } else {
            System.out.println("Encoded string is not valid.");
        }
    }

    public static void main(String[] args) {
        boolean done = false;
        do {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();
            switch (operation) {
                case "encode":
                    encode();
                    break;
                case "decode":
                    decode();
                    break;
                case "exit":
                    System.out.println("Bye!");
                    done = true;
                    break;
                default:
                    System.out.printf("There is no '%s' operation\n", operation);
            }
        } while (!done);
    }
}
