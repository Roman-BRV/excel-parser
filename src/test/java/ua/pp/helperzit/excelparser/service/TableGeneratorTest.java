package ua.pp.helperzit.excelparser.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableGeneratorTest {
    
    private static final String TEST_FILE_DIR = "src\\test\\resources\\";
    private static final String TEST_FILE_NAME = "test.xlsx";

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testGenerateTable() {
        File file = new File(getTestDirAbsolutePath() + TEST_FILE_NAME);
        assertTrue(file.exists());
    }
    
    private String getTestDirAbsolutePath() {
        
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        return path.substring(0, path.length()-1) + TEST_FILE_DIR;
    }

}
