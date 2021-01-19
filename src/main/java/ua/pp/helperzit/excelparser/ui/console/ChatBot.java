package ua.pp.helperzit.excelparser.ui.console;

import java.util.List;
import java.util.Scanner;

import ua.pp.helperzit.excelparser.service.FileFinder;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;

public class ChatBot {
    
    private static final String EXCEL_COLUMN_NAME_PATTERN = "^[A-Z]{1,3}";
    
    private static final String ANSWER_IF_TRUE = "1";
    private static final String ANSWER_IF_FALSE = "0";
    
    private static final int UNEXIST_KEY_COLUMN_NUMBER = -1;
    
    private static final String EXIT_COMMAND = "EXIT!";
    
    //private Table table = new Table();
    private FileFinder fileFinder = new FileFinder();
    private TableParsingCriteria tableParsingCriteria;
    private String filePath;
    private String tableName;
    
    public void startConversation() throws UIException {
        
        System.out.println("Hello I`m chatbot Marichka. Welcome at office JDBC school!");
        printProposedCommands();
        
        String answer;
        try (Scanner input = new Scanner(System.in);) {
            do {
                answer = input.nextLine();
                if (fileFinder.checkDirPath(answer)) {
                    System.out.println("DIR");
                    List<String> fileNames = fileFinder.getFileNames(answer);
                    for (String fileName : fileNames) {
                        System.out.println(fileName);
                    }

                } else if (fileFinder.checkFilePath(answer)) {
                    System.out.println("File path correct - " + answer);
                    filePath = answer;
                    
                    System.out.println("Enter name for parsing table. For example: deals_30-02-00.");
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

        TableParsingCriteria tableParsingCriteria = new TableParsingCriteria();

        System.out.println("Enter number of excel sheet (1 - first).");
        tableParsingCriteria.setSheetNumber(askForNumber(input) - 1);
        
        System.out.println("Enter name of start excel column (A - XFD for xlsx or A - IV for xls).");
        tableParsingCriteria.setStartColunmNumber(askForNumber(input) - 1);
        System.out.println("Enter number of start excel row (1 - 1048576 for xlsx or 1 - 65536 for xls).");
        tableParsingCriteria.setStartRowNumber(askForNumber(input) - 1);
        
        System.out.println("Enter name of end excel column (A - XFD for xlsx or A - IV for xls).");
        tableParsingCriteria.setEndColunmNumber(askForNumber(input) - 1);
        System.out.println("Enter number of end excel row (1 - 1048576 for xlsx or 1 - 65536 for xls).");
        tableParsingCriteria.setEndRowNumber(askForNumber(input) - 1);
        
        System.out.println("First parsing row has HEADs?");
        tableParsingCriteria.setHasHeads(askForBoolean(input));
        System.out.println("Do you need in KEYs?");
        tableParsingCriteria.setHasKeys(askForBoolean(input));
        if(!tableParsingCriteria.isHasKeys()) {
            tableParsingCriteria.setKeyColunmNumber(UNEXIST_KEY_COLUMN_NUMBER);
        }else {
            System.out.println("Enter name of KEY excel column (A - XFD for xlsx or A - IV for xls).");
            tableParsingCriteria.setKeyColunmNumber(askForNumber(input) - 1);
        }
        
        
        return tableParsingCriteria;
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
                System.out.println("Try once more.");

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
                System.out.println("Try once more.");

            }
        } while (!isEndOfQuery);
        
        return answer;

    }
    
    private boolean askForBoolean(Scanner input) {

        boolean isEndOfQuery = false;
        String answer;
        boolean booleanAnswer = false;

        System.out.println("Enter 1 if YES or 0 if NO.");
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
                System.out.println("Try once more.");

            }
        } while (!isEndOfQuery);
        
        return booleanAnswer;

    }
    
    private void printProposedCommands() {
        
        System.out.println();
        System.out.println("Please enter the number of activity, which do you want to do:");
        System.out.println("1. Find all groups with less or equals student count");
        System.out.println("2. Find all students related to course with given name");
        System.out.println("3. Add new student");
        System.out.println("4. Delete student by STUDENT_ID");
        System.out.println("5. Add a student to the course");
        System.out.println("6. Remove the student from one of his or her courses");
        System.out.println();
        System.out.println("0. Exit the application.");
    }

}
