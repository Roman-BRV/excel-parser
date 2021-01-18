package ua.pp.helperzit.excelparser.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;

public class TableGenerator {

    public Table parseFile(String filePath, String tableName, TableParsingCriteria tableParsingCriteria) {

        Table table = new Table();

        List<String> heads = table.getHeads();
        List<String> keys = table.getKeys();

        int sheetNumber = tableParsingCriteria.getSheetNumber();
        int startRowNumber = tableParsingCriteria.getStartRowNumber();
        int startColunmNumber = tableParsingCriteria.getStartColunmNumber();
        int endRowNumber = tableParsingCriteria.getEndRowNumber();
        int endColunmNumber = tableParsingCriteria.getEndColunmNumber();
        boolean hasHeads = tableParsingCriteria.isHasHeads();
        boolean hasKeys = tableParsingCriteria.isHasKeys();
        int keyColunmNumber = tableParsingCriteria.getKeyColunmNumber();
        
        String[][] tableData = new String[endRowNumber - startRowNumber + 1][endColunmNumber - startColunmNumber + 1];

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {

            Sheet sheet = workbook.getSheetAt(sheetNumber);
            if(!hasHeads) {
                for (int colunmIndex = startColunmNumber; colunmIndex <= endColunmNumber; colunmIndex++) {
                    heads.add("At column - " + CellReference.convertNumToColString(colunmIndex));
                }
            } else {
                Row row = sheet.getRow(startRowNumber);
                for (int columnIndex = startColunmNumber; columnIndex <= endColunmNumber; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    heads.add(geStringValue(cell));
                    startRowNumber++;
                }
            }

            int tmpRowIndex = 0;
            for (int rowIndex = startRowNumber; rowIndex <= endRowNumber; rowIndex++) {

                if(!hasKeys) {
                    keys.add("At row - " + (rowIndex + 1));
                }

                Row row = sheet.getRow(rowIndex);
                int tmpCellIndex = 0;

                for (int columnIndex = startColunmNumber; columnIndex <= endColunmNumber; columnIndex++) {

                    Cell cell = row.getCell(columnIndex);
                    tableData[tmpRowIndex][tmpCellIndex] = geStringValue(cell);
                    if(columnIndex == keyColunmNumber) {
                        keys.add(geStringValue(cell));
                    }
                    tmpCellIndex++;
                }

                tmpRowIndex++;

            }

            table.setFilePath(filePath);
            table.setTableName(tableName);
            table.setTableParsingCriteria(tableParsingCriteria);

        } catch (EncryptedDocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        table.setTableData(tableData);
        
        return table;

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
