package ua.pp.helperzit.excelparser.ui.console;

import java.util.List;
import java.util.Scanner;

import ua.pp.helperzit.excelparser.service.FileFinder;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;

public class ParsingCriteriaConversation {

    private static final String EXCEL_COLUMN_NAME_PATTERN = "^[A-Z]{1,3}";

    private static final String ANSWER_IF_TRUE = "1";
    private static final String ANSWER_IF_FALSE = "0";

    private static final String UNEXIST_KEY_COLUMN_NAME = "default";

    private static final String EXIT_COMMAND = "EXIT!";

    private FileFinder fileFinder = new FileFinder();
    private TableParsingCriteria tableParsingCriteria;
    private String filePath;
    private String tableName;



    public TableParsingCriteria getTableParsingCriteria() {
        return tableParsingCriteria;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTableName() {
        return tableName;
    }

    public void startConversation() throws UIException {

        System.out.println("Hello I`m chatbot Marichka. Welcome at Excel Parser!");
        printAppDescription();
        System.out.println("Enter path to file:");

        String answer;
        try (Scanner input = new Scanner(System.in);) {
            do {
                answer = input.nextLine();
                if (fileFinder.checkDirPath(answer)) {
                    System.out.println("It is directory. Includes:");
                    List<String> fileNames = fileFinder.getFileNames(answer);
                    for (String fileName : fileNames) {
                        System.out.println(fileName);
                    }

                } else if (fileFinder.checkFilePath(answer)) {
                    System.out.println("File path correct - " + answer);
                    filePath = answer;

                    System.out.println("Enter name for parsing table. For example: deals_30-02-00:");
                    tableName = input.nextLine();

                    tableParsingCriteria = constructTableParsingCriteria(input);
                    System.out.println(filePath);
                    System.out.println(tableName);
                    System.out.println(tableParsingCriteria);
                    break;

                } else if(answer.equals(EXIT_COMMAND)) {
                    System.out.println("Goodbay! Have a nice day!");

                } else {
                    System.out.println("Please enter correct path.");

                }
            } while (!answer.equals(EXIT_COMMAND));
        } catch (Exception e) {
            throw new UIException("Problems in UI layer");
        }

    }

    private TableParsingCriteria constructTableParsingCriteria(Scanner input) {

        TableParsingCriteria criteria = new TableParsingCriteria();

        System.out.println("Enter number of excel sheet (1 - first):");
        criteria.setSheetNumber(askForNumber(input) - 1);

        System.out.println("Enter name of start excel column (A - XFD for xlsx or A - IV for xls):");
        criteria.setStartColunmName(askForColumnName(input));
        System.out.println("Enter number of start excel row (1 - 1048576 for xlsx or 1 - 65536 for xls):");
        criteria.setStartRowNumber(askForNumber(input) - 1);

        System.out.println("Enter name of end excel column (A - XFD for xlsx or A - IV for xls):");
        criteria.setEndColunmName(askForColumnName(input));
        System.out.println("Enter number of end excel row (1 - 1048576 for xlsx or 1 - 65536 for xls):");
        criteria.setEndRowNumber(askForNumber(input) - 1);

        System.out.println("First parsing row has HEADs?");
        criteria.setHasHeads(askForBoolean(input));
        System.out.println("Do you need in KEYs?");
        criteria.setHasKeys(askForBoolean(input));
        if(!criteria.isHasKeys()) {
            criteria.setKeyColunmName(UNEXIST_KEY_COLUMN_NAME);
        }else {
            System.out.println("Enter name of KEY excel column (ONLY between name of start excel column and name of end excel column).");
            criteria.setKeyColunmName(askForColumnName(input));
        }

        return criteria;
    }

    private int askForNumber(Scanner input) {

        boolean isEndOfQuery = false;
        String answer;
        int numberAnswer = 0;

        do {
            answer = input.nextLine();
            if (answer.chars().allMatch( Character::isDigit ) && !answer.equals(EXIT_COMMAND)) {
                numberAnswer = Integer.parseInt(answer);
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                System.out.println("You exit to uper level of conversation.");
                isEndOfQuery = true;

            } else {
                System.out.println("You entered unexprcted answer.");
                System.out.println("Try once more:");

            }
        } while (!isEndOfQuery);

        return numberAnswer;

    }

    private String askForColumnName (Scanner input) {

        boolean isEndOfQuery = false;
        String answer = "";


        do {
            answer = input.nextLine().toUpperCase();
            if (answer.matches(EXCEL_COLUMN_NAME_PATTERN) && !answer.equals(EXIT_COMMAND)) {
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                System.out.println("You exit to uper level of conversation.");
                isEndOfQuery = true;

            } else {
                System.out.println("You entered unexprcted answer.");
                System.out.println("Try once more:");

            }
        } while (!isEndOfQuery);

        return answer;

    }

    private boolean askForBoolean(Scanner input) {

        boolean isEndOfQuery = false;
        String answer;
        boolean booleanAnswer = false;

        System.out.println("Enter 1 if YES or 0 if NO:");
        do {
            answer = input.nextLine();
            if ((answer.equals(ANSWER_IF_TRUE)||answer.equals(ANSWER_IF_FALSE)) && !answer.equals(EXIT_COMMAND)) {
                if(answer.equals(ANSWER_IF_TRUE)) {
                    booleanAnswer = true;
                }
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                System.out.println("You exit to uper level of conversation.");
                isEndOfQuery = true;

            } else {
                System.out.println("You entered unexprcted answer.");
                System.out.println("Try once more:");

            }
        } while (!isEndOfQuery);

        return booleanAnswer;

    }

    private void printAppDescription() {

        System.out.println();
        System.out.println("In this application you can read the area of any Excel file from your computer and convert it to java object.");
        System.out.println("Please insert next information:");
        System.out.println("1. Enter path to file. You can enter the path to dir, and we will help you find the file.");
        System.out.println("2. Enter name of data, which will be read. For future object.");
        System.out.println("3. Enter coordinates of first and last cells. The Area need to be rectangle — it's a table.");
        System.out.println("4. Choose Heads row. It can be the first row of chosen area or names of Excel column. For future object.");
        System.out.println("5. Choose Keys row. It can be selected column of chosen area or names of Excel row. For future object.");
        System.out.println("Enter word EXIT! if need to exit from conversation.");
        System.out.println();
        System.out.println("Let's start!.");
    }

}
