package ua.pp.helperzit.excelparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ua.pp.helperzit.excelparser.service.ServiceException;
import ua.pp.helperzit.excelparser.service.TableGenerator;
import ua.pp.helperzit.excelparser.service.models.Table;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.console.TableDescriptionConversation;

public class ExcelParser {

    public static void main(String[] args) throws EncryptedDocumentException, FileNotFoundException, IOException {

        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        String fileXLSXPath = path.substring(0, path.length()-1) + "temp.xlsx";
        //        String inXLSFileLocation = path.substring(0, path.length()-1) + "tempRead.xls";

        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("sheet1");
            Row row0 = sheet.createRow(0);
            Cell cell0 = row0.createCell(0);

            cell0.setCellValue("setted text");

            Row row2 = sheet.createRow(2);
            for (int i = 0; i < 5; i++) {
                Cell cell = row2.createCell(i);
                cell.setCellValue(i+1);
            }

            FileOutputStream fos = new FileOutputStream(fileXLSXPath);
            outWorkbook.write(fos);
            fos.close();
        }

        String parsingResult;
        CellReference cellRef;
        try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(fileXLSXPath))) {

            for (Sheet sheet : inWorkbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        parsingResult = geStringValue(cell);
                        cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        System.out.println(cellRef.formatAsString(false) + " - " + parsingResult);
                    }
                }
            }
        }


        //        try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSFileLocation))) {
        //
        //            for (Sheet sheet : inWorkbook) {
        //                for (Row row : sheet) {
        //                    for (Cell cell : row) {
        //                        parsingResult = geStringValue(cell);
        //                        cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
        //                        System.out.println(cellRef.formatAsString(true) + " - " + parsingResult);
        //                    }
        //                }
        //            }
        //        }


        TableParsingCriteria hardCodeCriteria = new TableParsingCriteria(0, 0, "A", 2, "C", false, false, "default");

        TableGenerator tableGenenerator = new TableGenerator();
        String tbl  = "table";
        //Table hardCodeTable = tableGenenerator.parseFile(fileXLSXPath, tbl, hardCodeCriteria);
        Table hardCodeTable = new Table();
        System.out.println(hardCodeTable);

        System.out.println("-------------------------------------------------------");

        TableDescriptionConversation parsingCriteriaConversation = new TableDescriptionConversation();
        //parsingCriteriaConversation.startConversation();
        
//        TableDescription tableDescription = parsingCriteriaConversation.askTableDescription();
//        String filePath = tableDescription.getFilePath();
//        String tableName = tableDescription.getTableName();
//        TableParsingCriteria criteria = tableDescription.getTableParsingCriteria();
        
        // ! This is code which will stay at Main !
        
        Table table;
        try {
            table = tableGenenerator.generateTable();
            System.out.println(table);
            System.out.println("TableUI equals HardCodeTable - " + table.equals(hardCodeTable));
        } catch (ServiceException e) {
            System.out.println("Application stoped it's work.");
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
