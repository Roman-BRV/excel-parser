package ua.pp.helperzit.excelparser.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.helperzit.excelparser.service.ServiceException;
import ua.pp.helperzit.excelparser.service.TableGenerator;
import ua.pp.helperzit.excelparser.service.models.TableDescription;
import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;

@RestController
@RequestMapping("/")
public class TableGeneratorRestController {
  
  @Autowired
  private TableGenerator tableGenerator;
  
  @GetMapping(value = "tableDescriptionConversation")
  public void generateTable(
      @RequestParam("filePath") String filePath, //src\\test\\resources\\test.xlsx
      @RequestParam("tableName") String tableName, //table
      @RequestParam("sheetNumber") int sheetNumber, //0
      @RequestParam("startRowNumber") int startRowNumber, //0
      @RequestParam("startColunmName") String startColunmName, //A
      @RequestParam("endRowNumber") int endRowNumber, //2
      @RequestParam("endColunmName") String endColunmName, //C
      @RequestParam("hasHeads") boolean hasHeads, //false
      @RequestParam("hasKeys") boolean hasKeys //false
      //@RequestParam("keyColunmName") String keyColunmName
      ) throws ServiceException {
    
    TableParsingCriteria tableParsingCriteria = new TableParsingCriteria();
    tableParsingCriteria.setSheetNumber(sheetNumber);
    tableParsingCriteria.setStartRowNumber(startRowNumber);
    tableParsingCriteria.setStartColunmName(startColunmName);
    tableParsingCriteria.setEndRowNumber(endRowNumber);
    tableParsingCriteria.setEndColunmName(endColunmName);
    tableParsingCriteria.setHasHeads(hasHeads);
    tableParsingCriteria.setHasKeys(hasKeys);
    
//    TableDescription tableDescription = new TableDescription(filePath.replace("-", "\\"), tableName, tableParsingCriteria);
//    
//    tableDescriptionConversation.setTableDescription(tableDescription);
    
    tableGenerator.generateTable(new TableDescription(filePath.replace("-", "\\"), tableName, tableParsingCriteria));
  }

}
