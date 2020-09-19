package ru.johnjavatries;

import java.util.Arrays;

import static ru.johnjavatries.MyUtils.checkArgsNumber;
import static ru.johnjavatries.MyUtils.getNumberOfInputFiles;

public class Main {
    public static final String SORT_ASC_ARG = "-a";
    public static final String SORT_DESC_ARG = "-d";
    public static final String STRING_DATA_TYPE = "-s";
    public static final String INT_DATA_TYPE = "-i";
    public static final String FILES_DIR = "files\\";


    public static void main(String[] args) {

        System.out.println(Arrays.toString(args));
        args = new String[]{   "-a", "-i", "output.txt", "input1.txt", "input2.txt", "input3.txt", "input4.txt", "input5.txt", "input6.txt"};
        //args = new String[]{   "-a", "-i", "output.txt", "input1.txt", "input2.txt"};
        //args = new String[]{   "-a", "-s", "output.txt", "instr1.txt", "instr2.txt", "instr3.txt", "instr4.txt", "instr5.txt", "instr6.txt"};
        //args = new String[]{   "-d", "-i", "des_output.txt", "des_input1.txt", "des_input2.txt", "des_input3.txt", "des_input4.txt"};

        System.out.println(Arrays.toString(args));

        try {
            checkArgsNumber(args);
        } catch (ArgsException e) {
            e.getMessage();
        }

        int numberOfInputFiles = getNumberOfInputFiles(args);
        System.out.println("Number of input files: " + numberOfInputFiles);

        boolean isAscSort = !args[0].equals(SORT_DESC_ARG);
        boolean isStringType = args[0].equals(STRING_DATA_TYPE) || args[1].equals(STRING_DATA_TYPE);

        String nameOfOutputFile = args[args.length - numberOfInputFiles - 1];
        String[] namesOfInputFiles = Arrays.copyOfRange(args, args.length - numberOfInputFiles, args.length);

        ArrayMerge arrayMerge = new ArrayMerge(nameOfOutputFile, namesOfInputFiles , isAscSort, isStringType);

        System.out.println("Program started.");

        arrayMerge.mergeFiles();

        System.out.println(arrayMerge);
    }

}
