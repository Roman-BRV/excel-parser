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
import org.springframework.stereotype.Service;
import ua.pp.helperzit.excelparser.service.models.Table;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;

@Service
public class TableGenerator {

    private static final Logger log = LoggerFactory.getLogger(TableGenerator.class);

    public Table generateTable(TableDescription tableDescription) throws ServiceException {

        log.debug("Going to generate table by user's description.");

        String filePath = tableDescription.getFilePath();
        String tableName = tableDescription.getTableName();
        TableParsingCriteria criteria = tableDescription.getTableParsingCriteria();

        List<String> heads= new ArrayList<>();
        List<String> keys = new ArrayList<>();

        int rowCount = criteria.getEndRowNumber() - criteria.getStartRowNumber() + 1;
        if(criteria.isHasHeads()) {
            rowCount--;
        }
        int columnCount = CellReference.convertColStringToIndex(criteria.getEndColunmName()) - CellReference.convertColStringToIndex(criteria.getStartColunmName()) + 1;
        String[][] tableData = new String[rowCount][columnCount];

        log.debug("Start parsing Excel file: {}.", filePath);
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {

            Sheet sheet = workbook.getSheetAt(criteria.getSheetNumber());
            log.debug("Get Excel sheet number: {}.", (criteria.getSheetNumber() + 1));

            parseHeads(heads, sheet, criteria);
            parseBody(tableData, keys, sheet, criteria);

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
        table.setTableParsingCriteria(criteria);
        table.setHeads(heads);
        table.setKeys(keys);
        table.setTableData(tableData);

        log.debug("{} has been successfully generated.", table);

        return table;

    }

    private void parseHeads(List<String> heads, Sheet sheet, TableParsingCriteria criteria) throws ServiceException{

        log.debug("Going to parse heads of columns in propoused table.");

        if(!criteria.isHasHeads()) {
            for (int colunmIndex = CellReference.convertColStringToIndex(criteria.getStartColunmName()); colunmIndex <= CellReference.convertColStringToIndex(criteria.getEndColunmName()); colunmIndex++) {
                heads.add("At column - " + CellReference.convertNumToColString(colunmIndex));
                log.debug("Setted default heads of all columns.");
            }
        } else {
            Row row = sheet.getRow(criteria.getStartRowNumber());
            if(row == null) {
                log.error("Excel row number: {} is empty. Heads row cannot be empty!",(criteria.getStartRowNumber() + 1));
                throw new ServiceException("Excel row number: " + (criteria.getStartRowNumber() + 1) + " is empty. Heads row cannot be empty!");
            }

            for (int columnIndex = CellReference.convertColStringToIndex(criteria.getStartColunmName()); columnIndex <= CellReference.convertColStringToIndex(criteria.getEndColunmName()); columnIndex++) {
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                heads.add(geStringValue(cell));
            }
        }

        log.debug("Heads of columns in propoused table has been successfully parsed.");
    }

    private void parseBody(String[][] tableData, List<String> keys, Sheet sheet, TableParsingCriteria criteria) {

        log.debug("Going to parse body of propoused table.");

        int resultRowIndex = 0;
        int currentRowIndex = criteria.getStartRowNumber();
        if(criteria.isHasHeads()) {
            currentRowIndex++;
        }

        for (; currentRowIndex <= criteria.getEndRowNumber(); currentRowIndex++) {

            Row row = sheet.getRow(currentRowIndex);
            setKeyIfDefault(keys, criteria, row, currentRowIndex);
            if(row == null) {
                continue;
            }

            int resultCellIndex = 0;

            for (int columnIndex = CellReference.convertColStringToIndex(criteria.getStartColunmName()); columnIndex <= CellReference.convertColStringToIndex(criteria.getEndColunmName()); columnIndex++) {

                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                tableData[resultRowIndex][resultCellIndex] = geStringValue(cell);
                if(columnIndex == CellReference.convertColStringToIndex(criteria.getKeyColunmName())) {
                    keys.add(geStringValue(cell));
                }
                resultCellIndex++;
            }
            resultRowIndex++;

        }

        log.debug("Body of propoused table has been successfully parsed.");
    }

    private void setKeyIfDefault(List<String> keys, TableParsingCriteria criteria, Row row, int rowIndex){

        if(!criteria.isHasKeys()) {
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
