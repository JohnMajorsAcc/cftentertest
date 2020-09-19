package ru.johnjavatries;


import static ru.johnjavatries.Main.*;

public class MyUtils{
    protected static int getNumberOfInputFiles(String[] args) {
        int numberOfInputFiles = 0;
        if (args[0].equals(SORT_ASC_ARG) || (args[0].equals(SORT_DESC_ARG))){
            numberOfInputFiles = args.length - 3; //number of input files if first argument exists
        } else {
            numberOfInputFiles = args.length - 2; //number of input files if first argument is not exists
        }
        if (numberOfInputFiles <= 1){
            System.out.println("To little input files.");
            System.exit(0);
        }
        return numberOfInputFiles;
    }

    protected static void checkArgsNumber(String[] args) throws ArgsException {
        if (args.length < 4) {
            throw new ArgsException("Number of arguments is not enough to start program.");
        }

        if (!args[0].equals(SORT_ASC_ARG) && !args[0].equals(SORT_DESC_ARG) && (!args[0].equals(STRING_DATA_TYPE) && (!args[0].equals(INT_DATA_TYPE)))){
            throw new ArgsException("Wrong first additional parameter.");
        }

        if ((args[0].equals(SORT_ASC_ARG) || args[0].equals(SORT_DESC_ARG)) && !(args[1].equals(STRING_DATA_TYPE) || args[1].equals(INT_DATA_TYPE))) {
            throw new ArgsException("Wrong second additional parameter.");
        }
    }
}
