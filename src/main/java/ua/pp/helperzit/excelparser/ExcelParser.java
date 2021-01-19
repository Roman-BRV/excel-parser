package ua.pp.helperzit.excelparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
import ua.pp.helperzit.excelparser.ui.UIException;
import ua.pp.helperzit.excelparser.ui.console.ChatBot;

public class ExcelParser {

    public static void main(String[] args) throws IOException, ServiceException, UIException {

        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        String outFileLocation = path.substring(0, path.length()-1) + "tempWrite.xlsx";
        String inXLSXFileLocation = path.substring(0, path.length()-1) + "tempRead.xlsx";
        String inXLSFileLocation = path.substring(0, path.length()-1) + "tempRead.xls";

        try (Workbook outWorkbook = new XSSFWorkbook()) {
            Sheet sheet = outWorkbook.createSheet("sheet1");
            Row row0 = sheet.createRow(0);
            Cell cell0 = row0.createCell(0);

            cell0.setCellValue("setted text");

            FileOutputStream fos = new FileOutputStream(outFileLocation);
            outWorkbook.write(fos);
            fos.close();
        }

        String parsingResult;
        CellReference cellRef;
        try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSXFileLocation))) {

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


        try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSFileLocation))) {

            for (Sheet sheet : inWorkbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        parsingResult = geStringValue(cell);
                        cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        System.out.println(cellRef.formatAsString(true) + " - " + parsingResult);
                    }
                }
            }
        }

        
        TableParsingCriteria tableParsingCriteria = new TableParsingCriteria(0, 0, 0, 2, 2, false, false, -1);
        
        TableGenerator tableGenenerator = new TableGenerator();
        String tbl  = "table";
        Table table = tableGenenerator.parseFile(inXLSXFileLocation, tbl, tableParsingCriteria);
        System.out.println(table);
        
        System.out.println("-------------------------------------------------------");
        ChatBot chatBot = new ChatBot();
        chatBot.startConversation();

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
