package ua.pp.helperzit.excelParser;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
    
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
    
    public static void main(String[] args) throws IOException {
        
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
            try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSXFileLocation))) {
                parsingResult = geStringValue(inWorkbook.getSheetAt(0).getRow(0).getCell(0));
            }
            System.out.println(parsingResult);
            
            try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSFileLocation))) {
                parsingResult = geStringValue(inWorkbook.getSheetAt(0).getRow(0).getCell(0));
            }
            System.out.println(parsingResult);

        
    }

}
