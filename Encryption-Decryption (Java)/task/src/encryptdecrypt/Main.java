package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = "";
        String pathIn = "";
        String pathOut = "";
        String inputData = "";
        String inputFinal = "";
        String input = "";
        String key = "";
        String method = "shift";

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-mode")) {
                option = args[i + 1];
            } else if (args[i].contains("-key")) {
                key = args[i + 1];
            } else if (args[i].contains("-in")) {
                pathIn = args[i + 1];
            } else if (args[i].contains("-data")) {
                inputData = args[i + 1];
            } else if (args[i].contains("-out")) {
                pathOut = args[i + 1];
            } else if (args[i].contains("unicode")) {
                method = "unicode";
            }
        }

        if (inputData.isEmpty() && pathIn.isEmpty()) {
            input = "";
        } else if (pathIn.isEmpty()) input = inputData;
        else if (inputData.isEmpty()) {
            inputFinal = pathIn;
            File file = new File(inputFinal);
            try (Scanner scanner1 = new Scanner(file)) {
                while (scanner1.hasNext()) {
                    input = scanner1.nextLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String result = "";
        if (option.equals("dec")) {
            if (method.equals("unicode")) result = dec(input, key);
            else result = shiftDec(input, key);
        } else {
            if (method.equals("unicode")) result = enc(input, key);
            else result = shiftEnc(input, key);
        }

        if (pathOut.isEmpty()) {
            System.out.println(result);
        } else {
            File file2 = new File(pathOut);
            FileWriter writer = null;

            try {
                writer = new FileWriter(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer.write(result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String enc(String input, String key) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] += (char) (Integer.parseInt(key));
        }
        return String.valueOf(c);
    }

    public static String dec(String input, String key) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            c[i] -= (char) (Integer.parseInt(key));
        }
        return String.valueOf(c);
    }

    public static String shiftEnc(String input, String key1) {
        int key = Integer.parseInt(key1);
        char[] c = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = (char) ((c[i] + key) > 122 ? c[i] + key - 26 : c[i] + key);
            } else if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = (char) ((c[i] + key) > 90 ? c[i] + key - 26 : c[i] + key);
            }
        }
        return String.valueOf(c);
    }

    public static String shiftDec(String input, String key1) {
        int key = Integer.parseInt(key1);
        char[] c = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            if (c[i] >= 'a' && c[i] <= 'z') {
                c[i] = (char) ((c[i] - key) < 97 ? c[i] - key + 26 : c[i] - key);
            } else if (c[i] >= 'A' && c[i] <= 'Z') {
                c[i] = (char) ((c[i] + key) < 65 ? c[i] - key + 26 : c[i] - key);
            }
        }
        return String.valueOf(c);
    }
}