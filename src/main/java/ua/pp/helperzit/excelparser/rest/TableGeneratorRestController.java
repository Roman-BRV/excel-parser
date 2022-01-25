package ua.pp.helperzit.excelparser.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    private static final Logger log = LoggerFactory.getLogger(TableGeneratorRestController.class);

    private static final String BASE_REQUEST_MAPPING = "tableDescriptionConversation";

    @Autowired
    private TableGenerator tableGenerator;

    @PostMapping(value = BASE_REQUEST_MAPPING)
    public void generateTable(
            @RequestParam("filePath") Part filePart, //filePart of file - src\\test\\resources\\test.xlsx
            @RequestParam("tableName") String tableName, //table
            @RequestParam("sheetNumber") int sheetNumber, //0
            @RequestParam("startRowNumber") int startRowNumber, //0
            @RequestParam("startColumnName") String startColumnName, //A
            @RequestParam("endRowNumber") int endRowNumber, //2
            @RequestParam("endColumnName") String endColumnName, //C
            @RequestParam("hasHeads") boolean hasHeads, //false
            @RequestParam("hasKeys") boolean hasKeys //false
            //@RequestParam("keyColumnName") String keyColumnName
            ) throws ServiceException, RestException {

        log.debug("Going to work with POST http request - /{}.", BASE_REQUEST_MAPPING);

        String fileName = "";
        try {

            fileName = filePart.getSubmittedFileName();
            InputStream fileIS = filePart.getInputStream();

            String location = this.getClass().getClassLoader().getResource("").getPath().replace("target/classes/","").substring(1);
            String uploadPath = location + "upload-files" + File.separator + fileName;

            log.debug("Going to upload file - {} to - {}.", fileName, uploadPath);

            Files.copy(fileIS, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);

            log.debug("File - {} has been successfully uploaded to - {}.", fileName, uploadPath);

            TableParsingCriteria tableParsingCriteria = new TableParsingCriteria();
            tableParsingCriteria.setSheetNumber(sheetNumber);
            tableParsingCriteria.setStartRowNumber(startRowNumber);
            tableParsingCriteria.setStartColumnName(startColumnName);
            tableParsingCriteria.setEndRowNumber(endRowNumber);
            tableParsingCriteria.setEndColumnName(endColumnName);
            tableParsingCriteria.setHasHeads(hasHeads);
            tableParsingCriteria.setHasKeys(hasKeys);

            log.debug("Going to generate object Table with tableName - {} from file - {} by criteria - {}.", tableName, uploadPath, tableParsingCriteria);

            tableGenerator.generateTable(new TableDescription(uploadPath, tableName, tableParsingCriteria));

            log.debug("Object Table with tableName - {} from file - {} has been successfully generated.", tableName, uploadPath);

        } catch (ServiceException e) {
            log.error("Something went wrong on Service layer with message: {}.", e.getMessage(), e);
            throw new RestException("Something went wrong on Service layer.", e);
        } catch (FileNotFoundException e) {
            log.error("Excel file -  {}  not founded.", fileName, e);
            throw new RestException("Excel file - " + fileName + " not founded.", e);
        } catch (IOException e) {
            log.error("Reading Excel file - {} was failed with message: {}.", fileName, e.getMessage(), e);
            throw new RestException("Something went wrong whit reading Excel file - " + fileName, e);
        } 
    }

}
