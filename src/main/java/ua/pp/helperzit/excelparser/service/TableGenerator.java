package ua.pp.helperzit.excelparser.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.helperzit.excelparser.service.models.Table;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;
import ua.pp.helperzit.excelparser.ui.console.TableDescriptionConversation;

public class TableGenerator {

    private static final Logger log = LoggerFactory.getLogger(TableGenerator.class);

    private TableDescriptionConversation tableDescriptionConversation = new TableDescriptionConversation();

    public Table generateTable() throws ServiceException {

        log.debug("Going to generate table by user's description.");

        TableDescription tableDescription;
        try {
            tableDescription = tableDescriptionConversation.askTableDescription();
            log.debug("{} has been successfully getted.", tableDescription);
        } catch (UIException uiException) {
            log.error("Conversation with user was failed with message: {}", uiException.getMessage(),  uiException);
            throw new ServiceException("Something went wrong whit in UI layer.", uiException);
        }
        String filePath = tableDescription.getFilePath();
        String tableName = tableDescription.getTableName();

        TableParsingCriteria tableParsingCriteria = tableDescription.getTableParsingCriteria();
        int sheetNumber = tableParsingCriteria.getSheetNumber();
        int startRowNumber = tableParsingCriteria.getStartRowNumber();
        int startColunmNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getStartColunmName());
        int endRowNumber = tableParsingCriteria.getEndRowNumber();
        int endColumnNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getEndColunmName());
        boolean hasHeads = tableParsingCriteria.isHasHeads();
        boolean hasKeys = tableParsingCriteria.isHasKeys();
        int keyColunmNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getKeyColunmName());

        List<String> heads= new ArrayList<>();
        List<String> keys = new ArrayList<>();
        int rowCount = endRowNumber - startRowNumber + 1;
        if(hasHeads) {
            rowCount--;
        }
        int columnCount = endColumnNumber - startColunmNumber + 1;
        String[][] tableData = new String[rowCount][columnCount];

        log.debug("Start parsing Excel file: {}.", filePath);
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {

            Sheet sheet = workbook.getSheetAt(sheetNumber);
            log.debug("Get Excel sheet number: {}.", (sheetNumber + 1));

            parseHeads(heads, sheet, hasHeads, startRowNumber, startColunmNumber, endColumnNumber);
            if(hasHeads) {
                startRowNumber++;
            }

            int tmpRowIndex = 0;
            for (int rowIndex = startRowNumber; rowIndex <= endRowNumber; rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                setKeyIfDefault(keys, hasKeys, row, rowIndex);
                if(row == null) {
                    continue;
                }

                int tmpCellIndex = 0;

                for (int columnIndex = startColunmNumber; columnIndex <= endColumnNumber; columnIndex++) {

                    Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    tableData[tmpRowIndex][tmpCellIndex] = geStringValue(cell);
                    if(columnIndex == keyColunmNumber) {
                        keys.add(geStringValue(cell));
                    }
                    tmpCellIndex++;
                }
                tmpRowIndex++;

            }
            log.debug("Propoused table area has been successfully parsed.");

        } catch (EncryptedDocumentException e) {
            log.error("Parsing of Excel file - {} was failed with message: {}", filePath, e.getMessage(), e);
            throw new ServiceException("Something went wrong whit parsing Excel file - " + filePath, e);
        } catch (FileNotFoundException e) {
            log.error("Excel file -  {}  not founded.", filePath, e);
            throw new ServiceException("Excel file - " + filePath + " not founded.", e);
        } catch (IOException e) {
            log.error("Reading Excel file - {} was failed with message: {}.", filePath, e.getMessage(), e);
            throw new ServiceException("Something went wrong whit reading Excel file - " + filePath, e);
        } 

        Table table = new Table();

        table.setFilePath(filePath);
        table.setTableName(tableName);
        table.setTableParsingCriteria(tableParsingCriteria);
        table.setHeads(heads);
        table.setKeys(keys);
        table.setTableData(tableData);

        log.debug("{} has been successfully generated.", table);

        return table;

    }

    private void parseHeads(List<String> heads, Sheet sheet, boolean hasHeads, int startRowNumber, int startColunmNumber, int endColunmNumber) throws ServiceException{

        log.debug("Going to parse heads of columns in propoused table.");

        if(!hasHeads) {
            for (int colunmIndex = startColunmNumber; colunmIndex <= endColunmNumber; colunmIndex++) {
                heads.add("At column - " + CellReference.convertNumToColString(colunmIndex));
                log.debug("Setted default heads of all columns.");
            }
        } else {
            Row row = sheet.getRow(startRowNumber);
            if(row == null) {
                log.error("Excel row number: {} is empty. Heads row cannot be empty!",(startRowNumber + 1));
                throw new ServiceException("Excel row number: " + (startRowNumber + 1) + " is empty. Heads row cannot be empty!");
            }

            for (int columnIndex = startColunmNumber; columnIndex <= endColunmNumber; columnIndex++) {
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                heads.add(geStringValue(cell));
            }
        }

        log.debug("Heads of columns in propoused table has been successfully parsed.");
    }

    private void setKeyIfDefault(List<String> keys, boolean hasKeys, Row row, int rowIndex){

        if(!hasKeys) {
            keys.add("At row - " + (rowIndex + 1));
        } else if(row == null) {
            keys.add("All row - " + (rowIndex + 1) + " empty.");
        }

        log.debug("Setted default key at Excel row number: {}.", (rowIndex + 1));
    }

    private static String geStringValue(Cell cell) {

        String result;

        switch (cell.getCellType()) {
        case STRING:
            result = cell.getRichStringCellValue().getString();
            break;
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                result = cell.getDateCellValue().toString();
            } else {
                result = Double.toString(cell.getNumericCellValue());
            }
            break;
        case BOOLEAN:
            result = Boolean.toString(cell.getBooleanCellValue());
            break;
        case FORMULA:
            result = cell.getCellFormula();
            break;
        case BLANK:
            result = "";
            break;
        default:
            result = "";
        }

        return result;
    }

}
