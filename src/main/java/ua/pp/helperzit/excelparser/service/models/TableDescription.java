package ua.pp.helperzit.excelparser.service.models;

public class TableDescription {
    
    private String filePath;
    private String tableName;
    private TableParsingCriteria tableParsingCriteria;
    
    public TableDescription() {
        
        this.filePath = "";
        this.tableName = "";
        this.tableParsingCriteria = new TableParsingCriteria();
    }
    
    public TableDescription(String filePath, String tableName, TableParsingCriteria tableParsingCriteria) {
        
        this.filePath = filePath;
        this.tableName = tableName;
        this.tableParsingCriteria = tableParsingCriteria;
    }

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

    @Override
    public String toString() {
        return "TableDescription [filePath=" + filePath + ", tableName=" + tableName + ", tableParsingCriteria="
                + tableParsingCriteria + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
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
        TableDescription other = (TableDescription) obj;
        if (filePath == null) {
            if (other.filePath != null)
                return false;
        } else if (!filePath.equals(other.filePath))
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
