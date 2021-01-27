package ua.pp.helperzit.excelparser.ui.console;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.helperzit.excelparser.service.FileFinder;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;

public class TableDescriptionConversation {
    
    private static final Logger log = LoggerFactory.getLogger(TableDescriptionConversation.class);

    private static final String EXCEL_COLUMN_NAME_PATTERN = "^[A-Z]{1,3}";

    private static final String ANSWER_IF_TRUE = "1";
    private static final String ANSWER_IF_FALSE = "0";

    private static final String UNEXIST_KEY_COLUMN_NAME = "default";

    private static final String EXIT_COMMAND = "EXIT!";
    private static final String USER_EXIT_MESSAGE = "User entered exit comand. Table description hasn't created.";

    public TableDescription askTableDescription() throws UIException {
        
        log.debug("Start conversation with user for getting table description.");

        FileFinder fileFinder = new FileFinder();
        TableDescription tableDescription = new TableDescription();
        
        System.out.println("Hello I`m chatbot Marichka. Welcome at Excel Parser!");
        printAppDescription();
        System.out.println("Enter path to file:");

        String answer;
        try (Scanner input = new Scanner(System.in);) {
            
            while(true) {
                
                answer = input.nextLine();
                
                if (fileFinder.checkDirectoryPath(answer)) {
                    System.out.println("It is directory. Includes:");
                    List<String> fileNames = fileFinder.getFileNames(answer);
                    for (String fileName : fileNames) {
                        System.out.println(fileName);
                    }
                    System.out.println("Enter path to file:");

                } else if (fileFinder.checkFilePath(answer)) {
                    
                    System.out.println("File path correct - " + answer);
                    String filePath = answer;
                    log.debug("User entered correct file path - {}.", filePath);
                    tableDescription.setFilePath(filePath);

                    System.out.println("Enter name for parsing table. For example: deals_30-02-00:");
                    String tableName = input.nextLine();
                    if(tableName.equals(EXIT_COMMAND)) {
                        log.warn(USER_EXIT_MESSAGE);
                        throw new UIException(USER_EXIT_MESSAGE);
                    }
                    log.debug("User entered name of tanle (future object)  - {}.", tableName);
                    tableDescription.setTableName(tableName);

                    TableParsingCriteria tableParsingCriteria = constructTableParsingCriteria(input);
                    tableDescription.setTableParsingCriteria(tableParsingCriteria);
                    System.out.println("File for parsing: " + filePath);
                    System.out.println("Table name: " + tableName);
                    System.out.println("Excel coordinates for parsing: " + tableParsingCriteria);
                    break;

                } else if(answer.equals(EXIT_COMMAND)) {
                    log.warn(USER_EXIT_MESSAGE);
                    throw new UIException(USER_EXIT_MESSAGE);

                } else {
                    System.out.println("Please enter correct path.");

                }
            } 
            
        } catch (Exception e) {
            throw new UIException("Problems in UI layer.", e);
        }
        
        return tableDescription;

    }

    private TableParsingCriteria constructTableParsingCriteria(Scanner input) throws UIException {

        TableParsingCriteria criteria = new TableParsingCriteria();
        
        System.out.println("Enter number of excel sheet (1 - first):");
        int sheetNumber = askForNumber(input) - 1;
        log.debug("User entered sheet number (index): {}.", sheetNumber);
        criteria.setSheetNumber(sheetNumber);

        System.out.println("Enter name of start excel column (A - XFD for xlsx or A - IV for xls):");
        String startColunmName = askForColumnName(input);
        log.debug("User entered start colunm name: {}.", startColunmName);
        criteria.setStartColunmName(startColunmName);
        
        System.out.println("Enter number of start excel row (1 - 1048576 for xlsx or 1 - 65536 for xls):");
        int startRowNumber = askForNumber(input) - 1;
        log.debug("User entered start row number (index): {}.", startRowNumber);
        criteria.setStartRowNumber(startRowNumber);

        System.out.println("Enter name of end excel column (A - XFD for xlsx or A - IV for xls):");
        String endColunmName = askForColumnName(input);
        log.debug("User entered end colunm name: {}.", endColunmName);
        criteria.setEndColunmName(endColunmName);
        
        System.out.println("Enter number of end excel row (1 - 1048576 for xlsx or 1 - 65536 for xls):");
        int endRowNumber = askForNumber(input) - 1;
        log.debug("User entered end row number (index): {}.", endRowNumber);
        criteria.setEndRowNumber(endRowNumber);

        System.out.println("First parsing row has HEADs?");
        boolean hasHeads = askForBoolean(input);
        log.debug("User entered: has first row table heads - {}.", hasHeads);
        criteria.setHasHeads(hasHeads);
        
        System.out.println("Do you need in KEYs?");
        boolean hasKeys = askForBoolean(input);
        log.debug("User entered: has table column with keys - {}.", hasKeys);
        criteria.setHasKeys(hasKeys);
        if(!criteria.isHasKeys()) {
            criteria.setKeyColunmName(UNEXIST_KEY_COLUMN_NAME);
            log.debug("Table hasn't column with keys. Setted default value - {}.", UNEXIST_KEY_COLUMN_NAME);
        }else {
            System.out.println("Enter name of KEY excel column (ONLY between name of start excel column and name of end excel column).");
            String keyColunmName = askForColumnName(input);
            log.debug("User entered key colunm name: {}.", keyColunmName);
            criteria.setKeyColunmName(keyColunmName);
        }

        log.debug("{} has been successfully generated.", criteria);
        
        return criteria;
    }

    private int askForNumber(Scanner input) throws UIException {

        boolean isEndOfQuery = false;
        String answer;
        int numberAnswer = 0;

        do {
            answer = input.nextLine();
            if (answer.chars().allMatch( Character::isDigit ) && !answer.equals(EXIT_COMMAND)) {
                numberAnswer = Integer.parseInt(answer);
                if(numberAnswer > 0) {
                    isEndOfQuery = true;
                } else {
                    unexprctedAnswerHendling(answer);
                }

            } else if(answer.equals(EXIT_COMMAND)) {
                log.warn(USER_EXIT_MESSAGE);
                throw new UIException(USER_EXIT_MESSAGE);

            } else {
                unexprctedAnswerHendling(answer);

            }
        } while (!isEndOfQuery);
        
        return numberAnswer;

    }

    private String askForColumnName (Scanner input) throws UIException {

        boolean isEndOfQuery = false;
        String answer = "";


        do {
            answer = input.nextLine().toUpperCase();
            if (answer.matches(EXCEL_COLUMN_NAME_PATTERN) && !answer.equals(EXIT_COMMAND)) {
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                log.warn(USER_EXIT_MESSAGE);
                throw new UIException(USER_EXIT_MESSAGE);

            } else {
                unexprctedAnswerHendling(answer);

            }
        } while (!isEndOfQuery);

        return answer;

    }

    private boolean askForBoolean(Scanner input) throws UIException {

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
                log.warn(USER_EXIT_MESSAGE);
                throw new UIException(USER_EXIT_MESSAGE);

            } else {
                unexprctedAnswerHendling(answer);

            }
        } while (!isEndOfQuery);

        return booleanAnswer;

    }
    
    private void unexprctedAnswerHendling(String answer) {
        
        log.warn("User unexprcted answer - {}. Start to ask once more.", answer);
        System.out.println("You entered unexprcted answer.");
        System.out.println("Try once more:");
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
