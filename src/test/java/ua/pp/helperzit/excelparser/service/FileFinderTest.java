package ua.pp.helperzit.excelparser.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileFinderTest {

    private static final String TEST_FILE_DIR = "src\\test\\resources\\";
    private static final String TEST_EMPTY_DIR_NAME = "emptyDirectory";
    private static final String TEST_XLSX_FILE_NAME = "test.xlsx";
    private static final String TEST_XLS_FILE_NAME = "test.xls";
    private static final String UNEXIST_FILE_NAME = "unexist";
    
    FileFinder fileFinder;

    @BeforeEach
    void setUp() throws Exception {
        
        fileFinder = new FileFinder();
    }
    
    @Test
    void checkDirectoryPathShouldReturnTrueIfCheckedPathIsDirectory() {
        String currentDirPath = getTestDirAbsolutePath();
        assertTrue(fileFinder.checkDirectoryPath(currentDirPath));
    }
    
    @Test
    void checkDirectoryPathShouldReturnFalseIfCheckedDirectoryUnexist() {
        String unexistDirPath = getTestDirAbsolutePath() + UNEXIST_FILE_NAME;
        assertFalse(fileFinder.checkDirectoryPath(unexistDirPath));
    }

    @Test
    void checkFilePathShouldReturnTrueIfCheckedXlsxFileExists() {
        String currentXlsxPath = getTestDirAbsolutePath() + TEST_XLSX_FILE_NAME;
        assertTrue(fileFinder.checkFilePath(currentXlsxPath));
    }
    
    @Test
    void checkFilePathShouldReturnTrueIfCheckedXlsFileExists() {
        String currentXlsPath = getTestDirAbsolutePath() + TEST_XLS_FILE_NAME;
        assertTrue(fileFinder.checkFilePath(currentXlsPath));
    }
    
    @Test
    void checkFilePathShouldReturnFalseIfCheckedPathIsDirectory() {
        String currentDirPath = getTestDirAbsolutePath();
        assertFalse(fileFinder.checkFilePath(currentDirPath));
    }
    
    @Test
    void checkFilePathShouldReturnFalseIfCheckedFileUnexist() {
        String unexistFilePath = getTestDirAbsolutePath() + UNEXIST_FILE_NAME;
        assertFalse(fileFinder.checkFilePath(unexistFilePath));
    }
    
    @Test
    void getInclusionsNamesShouldReturnListOfInclusionsNamesWhenDirectoryIsNotEmpty() {
        List<String> expInclusionsNames = new ArrayList<>();
        expInclusionsNames.add(TEST_EMPTY_DIR_NAME);
        expInclusionsNames.add(TEST_XLS_FILE_NAME);
        expInclusionsNames.add(TEST_XLSX_FILE_NAME);
        String currentDirPath = getTestDirAbsolutePath();
        
        assertEquals(expInclusionsNames, fileFinder.getInclusionsNames(currentDirPath));
    }
    
    @Test
    void getInclusionsNamesShouldReturnEmptyListWhenDirectoryIsEmpty() {
        String emptyDirPath = getTestDirAbsolutePath() + "\\" + TEST_EMPTY_DIR_NAME;
        assertTrue(fileFinder.getInclusionsNames(emptyDirPath).isEmpty());
    }
    
    private String getTestDirAbsolutePath() {
        
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        return path.substring(0, path.length()-1) + TEST_FILE_DIR;
    }

}
