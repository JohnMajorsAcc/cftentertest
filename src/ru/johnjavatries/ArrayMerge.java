package ru.johnjavatries;

import java.io.*;
import java.util.Arrays;

import static ru.johnjavatries.Main.FILES_DIR;

public class ArrayMerge {
    private final String outputFileName;
    private final String[] inputFileNames;
    private final boolean isAscSort;
    private final boolean isStringType;
    String tempFile1Name = "outputtemp1.txt";
    String tempFile2Name = "outputtemp2.txt";

    private int tempInt1;
    private int tempInt2;

    private File outputFile;
    private File[] inputFiles;
    private File tempFile1;
    private File tempFile2;

    public ArrayMerge(String outputFileName, String[] inputFileNames, boolean isAscSort, boolean isStringType) {
        this.outputFileName = outputFileName;
        this.inputFileNames = inputFileNames;
        this.isAscSort = isAscSort;
        this.isStringType = isStringType;
    }


    @Override
    public String toString() {
        return "MergeSort{" +
                "outputFileName='" + outputFileName + '\'' +
                ", inputFileNames=" + Arrays.toString(inputFileNames) +
                ", isAscSort=" + isAscSort +
                ", isStringType=" + isStringType +
                '}';
    }

    public void mergeFiles() {
        checkFiles();

        boolean writeToFirstTemp = true;

        for (File inputFile : inputFiles) {

            if (inputFile.exists() && inputFile.canRead()) {
                if (writeToFirstTemp) {

                    if (isStringType) {
                        mergeTwoStringFiles(inputFile, tempFile2, tempFile1);
                    } else
                        mergeTwoIntFiles(inputFile, tempFile2, tempFile1);

                    writeToFirstTemp = false;
                } else {
                    if (isStringType) {
                        mergeTwoStringFiles(inputFile, tempFile1, tempFile2);
                    } else {
                        mergeTwoIntFiles(inputFile, tempFile1, tempFile2);
                    }
                    writeToFirstTemp = true;
                }
            }
        }

        if (writeToFirstTemp) {
            try {
                copyFileUsingStream(tempFile2, outputFile);
            } catch (IOException exception) {
                System.out.println("Trouble with copying file (temp 2): " + exception.getMessage());
            }
        } else {
            try {
                copyFileUsingStream(tempFile1, outputFile);
            } catch (IOException exception) {
                System.out.println("Trouble with copying file (temp 1): " + exception.getMessage());
            }
        }
        tempFile1.delete();
        tempFile2.delete();
    }

    private void mergeTwoStringFiles(File inputFile1, File inputFile2, File outputFile) {

        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader1 = new BufferedReader(new FileReader(inputFile1));
            bufferedReader2 = new BufferedReader(new FileReader(inputFile2));
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

            String element1 = bufferedReader1.readLine();
            String element2 = bufferedReader2.readLine();


            while (true) {
                System.out.println("<" + element1 + ">" + " " + "<" + element2 + ">");

                while (element1 != null && element1.equals("")) {
                    element1 = bufferedReader1.readLine();
                }

                while (element2 != null && element2.equals("")) {
                    element2 = bufferedReader2.readLine();
                }

                if (element1 == null && element2 != null) {
                    bufferedWriter.write(element2 + "\n");
                    element2 = bufferedReader2.readLine();
                    continue;
                }

                if (element1 != null && element2 == null) {
                    bufferedWriter.write(element1 + "\n");
                    element1 = bufferedReader1.readLine();
                    continue;
                }

                if (element1 == null) {
                    break;
                }

                if (isAscSort) {
                    if (element1.compareTo(element2) <= 0) {
                        bufferedWriter.write(element1 + "\n");
                        element1 = bufferedReader1.readLine();
                    } else if (element1.compareTo(element2) > 0) {
                        bufferedWriter.write(element2 + "\n");
                        element2 = bufferedReader2.readLine();
                    }
                } else {
                    if (element1.compareTo(element2) > 0) {
                        bufferedWriter.write(element1 + "\n");
                        element1 = bufferedReader1.readLine();
                    } else if (element1.compareTo(element2) <= 0) {
                        bufferedWriter.write(element2 + "\n");
                        element2 = bufferedReader2.readLine();
                    }

                }
            }
        } catch (IOException exception) {
            System.out.println("Something wrong: " + exception.getMessage());
        } finally {
            try {
                bufferedReader1.close();
                bufferedReader2.close();
                bufferedWriter.close();
            } catch (IOException exception) {
                System.out.println("Something wrong. Trouble with closing files: " + exception.getMessage());
            }
        }
    }


    public void mergeTwoIntFiles(File inputFile1, File inputFile2, File outputFile) {

        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader1 = null;
        BufferedReader bufferedReader2 = null;
        int tempInt1 = isAscSort ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int tempInt2 = isAscSort ? Integer.MIN_VALUE : Integer.MAX_VALUE;




        try {
            bufferedReader1 = new BufferedReader(new FileReader(inputFile1));
            bufferedReader2 = new BufferedReader(new FileReader(inputFile2));
            bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

            String element1 = bufferedReader1.readLine();
            String element2 = bufferedReader2.readLine();


            while (true) {
                System.out.println("element1: " + element1 + " element2:" + element2);
                int intElement1 = 0;

                while (element1 != null) {
                    try {
                        intElement1 = Integer.parseInt(element1);
                        break;
                    } catch (NumberFormatException exception) {
                        element1 = bufferedReader1.readLine();
                    }
                }

                int intElement2 = 0;
                while (element2 != null) {
                    try {
                        intElement2 = Integer.parseInt(element2);
                        break;
                    } catch (NumberFormatException exception) {
                        element2 = bufferedReader2.readLine();
                    }

                }

                if (element1 == null && element2 != null) {
                    if (intElement2 > tempInt2) {
                        tempInt2 = intElement2;
                        bufferedWriter.write(element2 + "\n");
                    }
                    element2 = bufferedReader2.readLine();
                    continue;
                }

                if (element1 != null && element2 == null) {
                    if (intElement1 > tempInt1) {
                        bufferedWriter.write(element1 + "\n");
                        tempInt1 = intElement1;
                    }
                        element1 = bufferedReader1.readLine();
                        continue;
                    }

                    if (element1 == null && element2 == null) {
                        break;
                    }

                    if (isAscSort) {
                        if (intElement1 <= intElement2) {
                            if (intElement1 > tempInt1) {
                                bufferedWriter.write(element1 + "\n");
                                tempInt1 = intElement1;
                            }
                            element1 = bufferedReader1.readLine();

                        } else if (intElement1 > intElement2) {
                            if (intElement2 > tempInt2) {
                                bufferedWriter.write(element2 + "\n");
                                tempInt2 = intElement2;
                            }
                            element2 = bufferedReader2.readLine();
                        }
                    } else {
                        if (intElement1 > intElement2) {
                            bufferedWriter.write(element1 + "\n");
                            element1 = bufferedReader1.readLine();
                        } else {
                            bufferedWriter.write(element2 + "\n");
                            element2 = bufferedReader2.readLine();
                        }
                    }

                }
            } catch(IOException exception){
                System.out.println("Something wrong: " + exception.getMessage());
            } finally{
                try {
                    bufferedReader1.close();
                    bufferedReader2.close();
                    bufferedWriter.close();
                } catch (IOException exception) {
                    System.out.println("Something wrong. Trouble with closing files: " + exception.getMessage());
                }
            }
        }

        private void copyFileUsingStream (File source, File dest) throws IOException {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(source);
                outputStream = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } finally {
                inputStream.close();
                outputStream.close();
            }
        }

        public void checkFiles () {
            outputFile = new File(FILES_DIR, outputFileName);
            System.out.println(outputFileName + " exists: " + outputFile.exists());

            try {
                tempFile1 = new File(FILES_DIR, tempFile1Name);
                System.out.println(tempFile1Name + " created: " + tempFile1.createNewFile());
                tempFile2 = new File(FILES_DIR, tempFile2Name);
                System.out.println(tempFile2Name + " created: " + tempFile2.createNewFile());
            } catch (IOException exception) {
                exception.printStackTrace();
            }


            inputFiles = new File[inputFileNames.length];

            for (int i = 0; i < inputFiles.length; i++) {
                inputFiles[i] = new File(FILES_DIR, inputFileNames[i]);
                System.out.println(inputFileNames[i] + " exists: " + inputFiles[i].exists());
            }
        }
    }

