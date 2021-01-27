package ua.pp.helperzit.excelparser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.pp.helperzit.excelparser.service.models.Table;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;
import ua.pp.helperzit.excelparser.ui.UIException;
import ua.pp.helperzit.excelparser.ui.console.TableDescriptionConversation;

class TableGeneratorTest {
    
    private static final String TEST_FILE_DIR = "src\\test\\resources\\";
    private static final String TEST_FILE_NAME = "test.xlsx";
    private static final String TEST_TABLE_NAME = "table";

    private static final int TEST_SHEET_NUMBER = 0;
    private static final int TEST_START_ROW_NUMBER = 0;
    private static final String TEST_START_COLUMN_NAME = "A";
    private static final int TEST_END_ROW_NUMBER = 2;
    private static final String TEST_END_COLUMN_NAME = "C";
    private static final boolean TEST_HAS_HEADS = false;
    private static final boolean TEST_HAS_KEYS = false;
    //private static final String TEST_KEY_COLUMN_NAME = "";
    private static final String UNEXIST_KEY_COLUMN_NAME = "default";
    
    private static final String[] TEST_HEADS = {"At column - A", "At column - B", "At column - C"};
    private static final String[] TEST_KEYS = {"At row - 1", "At row - 2", "At row - 3"};
    private static final String[][] TEST_TABLE_DATA = {{"setted text", "", ""}, {"1.0", "2.0", "3.0"}, {null, null, null}};
    
    @InjectMocks
    TableGenerator tableGenerator = new TableGenerator();
    
    @Mock
    TableDescriptionConversation mockTableDescriptionConversation;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGenerateTable() throws ServiceException, UIException {
        
        Table expTable = expTableConstruct();
        
        TableDescription testTableDescription = testTableDescriptionConstruct();
        when(mockTableDescriptionConversation.askTableDescription()).thenReturn(testTableDescription);
        Table resultTable = tableGenerator.generateTable();
        assertEquals(expTable, resultTable);
    }
    
    private TableDescription testTableDescriptionConstruct() {
        
        TableDescription testTableDescription = new TableDescription();
        testTableDescription.setFilePath(getTestFileAbsolutePath());
        testTableDescription.setTableName(TEST_TABLE_NAME);
        testTableDescription.setTableParsingCriteria(testTableParsingCriteriaConstruct());
        
        return testTableDescription;
    }
    
    private String getTestFileAbsolutePath() {
        
        File currentDir = new File(".");
        String path = currentDir.getAbsolutePath();
        return path.substring(0, path.length()-1) + TEST_FILE_DIR + TEST_FILE_NAME;
    }
    
    private TableParsingCriteria testTableParsingCriteriaConstruct() {
        
        TableParsingCriteria tableParsingCriteria = new TableParsingCriteria();
        tableParsingCriteria.setSheetNumber(TEST_SHEET_NUMBER);
        tableParsingCriteria.setStartRowNumber(TEST_START_ROW_NUMBER);
        tableParsingCriteria.setStartColunmName(TEST_START_COLUMN_NAME);
        tableParsingCriteria.setEndRowNumber(TEST_END_ROW_NUMBER);
        tableParsingCriteria.setEndColunmName(TEST_END_COLUMN_NAME);
        tableParsingCriteria.setHasHeads(TEST_HAS_HEADS);
        tableParsingCriteria.setHasKeys(TEST_HAS_KEYS);
        tableParsingCriteria.setKeyColunmName(UNEXIST_KEY_COLUMN_NAME);
        
        return tableParsingCriteria;
    }
    
    private Table expTableConstruct() {
        
        Table expTable = new Table();
        expTable.setFilePath(getTestFileAbsolutePath());
        expTable.setTableName(TEST_TABLE_NAME);
        expTable.setTableParsingCriteria(testTableParsingCriteriaConstruct());
        expTable.setHeads(Arrays.asList(TEST_HEADS));
        expTable.setKeys(Arrays.asList(TEST_KEYS));
        expTable.setTableData(TEST_TABLE_DATA);
        
        return expTable;
    }

}
