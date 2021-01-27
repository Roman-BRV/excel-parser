package ua.pp.helperzit.excelparser;

import ua.pp.helperzit.excelparser.service.ServiceException;
import ua.pp.helperzit.excelparser.service.TableGenerator;
import ua.pp.helperzit.excelparser.service.models.Table;


public class ExcelParser {

    public static void main(String[] args) {

        TableGenerator tableGenenerator = new TableGenerator();        
        Table table;
        try {
            table = tableGenenerator.generateTable();
            System.out.println(table);

        } catch (ServiceException e) {
            System.out.println("Goodbay! Have a nice day!");
            System.out.println("Application stoped it's work.");
        }

    }

}
