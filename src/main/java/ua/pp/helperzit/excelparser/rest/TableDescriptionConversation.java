package ua.pp.helperzit.excelparser.rest;

import org.springframework.stereotype.Component;
import ua.pp.helperzit.excelparser.service.models.TableDescription;

@Component
public class TableDescriptionConversation {
  
  private TableDescription tableDescription;

  public void setTableDescription(TableDescription tableDescription) {
    this.tableDescription = tableDescription;
  }

  public TableDescription askTableDescription() {
    return tableDescription;
  }

}
