package ua.pp.helperzit.excelparser.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.HashMap;
//import java.util.Map;

import ua.pp.helperzit.excelparser.service.models.TableParsingCriteria;

public class Table {

    private String filePath;
    private String tableName;
    private TableParsingCriteria tableParsingCriteria;
    List<String> heads;
    List<String> keys;
    private String[][] tableData;
    //private Map<String, List<Map<String, String>>> tableData;

    public Table() {

        this.filePath = "";
        this.tableName = "";
        this.tableParsingCriteria = new TableParsingCriteria();
        this.heads = new ArrayList<String>();
        this.keys = new ArrayList<String>();
        //this.tableData = new HashMap<>();
    }
    
//  public Table(String filePath, String tableName, TableParsingCriteria tableParsingCriteria) {
//
//      this.filePath = filePath;
//      this.tableName = tableName;
//      this.tableParsingCriteria = tableParsingCriteria;
//      this.tableData = new HashMap<>();
//  }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableParsingCriteria getTableParsingCriteria() {
        return tableParsingCriteria;
    }

    public void setTableParsingCriteria(TableParsingCriteria tableParsingCriteria) {
        this.tableParsingCriteria = tableParsingCriteria;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(List<String> heads) {
        this.heads = heads;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String[][] getTableData() {
        return tableData;
    }

    public void setTableData(String[][] tableData) {
        this.tableData = tableData;
    }

    @Override
    public String toString() {
        return "Table [filePath=" + filePath + ", tableName=" + tableName + ", tableParsingCriteria="
                + tableParsingCriteria + ", heads=" + heads + ", keys=" + keys + ", tableData="
                + Arrays.toString(tableData) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
        result = prime * result + ((heads == null) ? 0 : heads.hashCode());
        result = prime * result + ((keys == null) ? 0 : keys.hashCode());
        result = prime * result + Arrays.deepHashCode(tableData);
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((tableParsingCriteria == null) ? 0 : tableParsingCriteria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Table other = (Table) obj;
        if (filePath == null) {
            if (other.filePath != null)
                return false;
        } else if (!filePath.equals(other.filePath))
            return false;
        if (heads == null) {
            if (other.heads != null)
                return false;
        } else if (!heads.equals(other.heads))
            return false;
        if (keys == null) {
            if (other.keys != null)
                return false;
        } else if (!keys.equals(other.keys))
            return false;
        if (!Arrays.deepEquals(tableData, other.tableData))
            return false;
        if (tableName == null) {
            if (other.tableName != null)
                return false;
        } else if (!tableName.equals(other.tableName))
            return false;
        if (tableParsingCriteria == null) {
            if (other.tableParsingCriteria != null)
                return false;
        } else if (!tableParsingCriteria.equals(other.tableParsingCriteria))
            return false;
        return true;
    } 

}
