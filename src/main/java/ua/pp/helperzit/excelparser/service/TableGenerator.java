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

import ua.pp.helperzit.excelparser.service.models.Table;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;
import ua.pp.helperzit.excelparser.ui.console.TableDescriptionConversation;

public class TableGenerator {

    public Table generateTable() throws ServiceException {

        TableDescriptionConversation tableDescriptionConversation = new TableDescriptionConversation();
        TableDescription tableDescription;
        try {
            tableDescription = tableDescriptionConversation.askTableDescription();
        } catch (UIException uiException) {
            throw new ServiceException("Something went wrong whit in UI layer.", uiException);
        }
        String filePath = tableDescription.getFilePath();
        String tableName = tableDescription.getTableName();

        TableParsingCriteria tableParsingCriteria = tableDescription.getTableParsingCriteria();
        int sheetNumber = tableParsingCriteria.getSheetNumber();
        int startRowNumber = tableParsingCriteria.getStartRowNumber();
        int startColunmNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getStartColunmName());
        int endRowNumber = tableParsingCriteria.getEndRowNumber();
        int endColunmNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getEndColunmName());
        boolean hasHeads = tableParsingCriteria.isHasHeads();
        boolean hasKeys = tableParsingCriteria.isHasKeys();
        int keyColunmNumber = CellReference.convertColStringToIndex(tableParsingCriteria.getKeyColunmName());

        List<String> heads= new ArrayList<>();
        List<String> keys = new ArrayList<>();
        String[][] tableData = new String[endRowNumber - startRowNumber + 1][endColunmNumber - startColunmNumber + 1];

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {

            Sheet sheet = workbook.getSheetAt(sheetNumber);

            parseHeads(heads, sheet, hasHeads, startRowNumber, startColunmNumber, endColunmNumber);
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

                for (int columnIndex = startColunmNumber; columnIndex <= endColunmNumber; columnIndex++) {

                    Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    tableData[tmpRowIndex][tmpCellIndex] = geStringValue(cell);
                    if(columnIndex == keyColunmNumber) {
                        keys.add(geStringValue(cell));
                    }
                    tmpCellIndex++;
                }
                tmpRowIndex++;

            }

        } catch (EncryptedDocumentException e) {
            throw new ServiceException("Something went wrong whit parsing Excel file - " + filePath, e);
        } catch (FileNotFoundException e) {
            throw new ServiceException("Excel file - " + filePath + " not founded.", e);
        } catch (IOException e) {
            throw new ServiceException("Something went wrong whit reading Excel file - " + filePath, e);
        } 

        Table table = new Table();

        table.setFilePath(filePath);
        table.setTableName(tableName);
        table.setTableParsingCriteria(tableParsingCriteria);
        table.setHeads(heads);
        table.setKeys(keys);
        table.setTableData(tableData);

        return table;

    }

    private void parseHeads(List<String> heads, Sheet sheet, boolean hasHeads, int startRowNumber, int startColunmNumber, int endColunmNumber) throws ServiceException{

        if(!hasHeads) {
            for (int colunmIndex = startColunmNumber; colunmIndex <= endColunmNumber; colunmIndex++) {
                heads.add("At column - " + CellReference.convertNumToColString(colunmIndex));
            }
        } else {
            Row row = sheet.getRow(startRowNumber);
            if(row == null) {
                throw new ServiceException("Excel row nubber: " + (startRowNumber + 1) + " is empty. Heads row cannot be empty!");
            }

            for (int columnIndex = startColunmNumber; columnIndex <= endColunmNumber; columnIndex++) {
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                heads.add(geStringValue(cell));
            }
        }
    }

    private void setKeyIfDefault(List<String> keys, boolean hasKeys, Row row, int rowIndex){

        if(!hasKeys) {
            keys.add("At row - " + (rowIndex + 1));
        } else if(row == null) {
            keys.add("All row - " + (rowIndex + 1) + " empty.");
        }  
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
