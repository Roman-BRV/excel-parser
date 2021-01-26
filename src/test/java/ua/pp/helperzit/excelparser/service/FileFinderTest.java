package ua.pp.helperzit.excelparser.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileFinderTest {

    private static final String TEST_FILE_DIR = "src\\test\\resources\\";
    private static final String TEST_XLSX_FILE_NAME = "test.xlsx";
    private static final String TEST_XLS_FILE_NAME = "test.xls";
    
    FileFinder fileFinder;

    @BeforeEach
    void setUp() throws Exception {
        
        fileFinder = new FileFinder();
    }

    @Test
    void checkFilePathShouldReturnTrueIfXlsxFileExists() {
        String currentXlsxPath = getTestDirAbsolutePath() + TEST_XLSX_FILE_NAME;
        assertTrue(fileFinder.checkFilePath(currentXlsxPath));
    }
    
    @Test
    void checkFilePathShouldReturnTrueIfXlsFileExists() {
        String currentXlsPath = getTestDirAbsolutePath() + TEST_XLS_FILE_NAME;
        assertTrue(fileFinder.checkFilePath(currentXlsPath));
    }
    
    private String getTestDirAbsolutePath() {
        
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        return path.substring(0, path.length()-1) + TEST_FILE_DIR;
    }

}
