package ua.pp.helperzit.excelParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
    
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
                parsingResult = inWorkbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
            }
            System.out.println(parsingResult);
            
            try (Workbook inWorkbook = WorkbookFactory.create(new FileInputStream(inXLSFileLocation))) {
                parsingResult = inWorkbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
            }
            System.out.println(parsingResult);

        
    }

}
