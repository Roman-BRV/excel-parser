package ua.pp.helperzit.excelparser.service.models;

public class TableParsingCriteria {

    private static final String DEFAULT_KEY_COLUMN_NAME = "default";

    private int sheetNumber;
    private int startRowNumber;
    private String startColumnName;
    private int endRowNumber;
    private String endColumnName;
    private boolean hasHeads;
    private boolean hasKeys;
    private String keyColumnName;

    public TableParsingCriteria() {

        this.keyColumnName = DEFAULT_KEY_COLUMN_NAME;

    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public int getStartRowNumber() {
        return startRowNumber;
    }

    public String getStartColumnName() {
        return startColumnName;
    }

    public int getEndRowNumber() {
        return endRowNumber;
    }

    public String getEndColumnName() {
        return endColumnName;
    }

    public boolean isHasHeads() {
        return hasHeads;
    }

    public boolean isHasKeys() {
        return hasKeys;
    }

    public String getKeyColumnName() {
        return keyColumnName;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public void setStartRowNumber(int startRowNumber) {
        this.startRowNumber = startRowNumber;
    }

    public void setStartColumnName(String startColumnName) {
        this.startColumnName = startColumnName;
    }

    public void setEndRowNumber(int endRowNumber) {
        this.endRowNumber = endRowNumber;
    }

    public void setEndColumnName(String endColumnName) {
        this.endColumnName = endColumnName;
    }

    public void setHasHeads(boolean hasHeads) {
        this.hasHeads = hasHeads;
    }

    public void setHasKeys(boolean hasKeys) {
        this.hasKeys = hasKeys;
    }

    public void setKeyColumnName(String keyColumnName) {
        this.keyColumnName = keyColumnName;
    }

    @Override
    public String toString() {
        return "TableParsingCriteria [sheetNumber=" + sheetNumber + ", startRowNumber=" + startRowNumber
                + ", startColumnName=" + startColumnName + ", endRowNumber=" + endRowNumber + ", endColumnName="
                + endColumnName + ", hasHeads=" + hasHeads + ", hasKeys=" + hasKeys + ", keyColumnName=" + keyColumnName
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endColumnName == null) ? 0 : endColumnName.hashCode());
        result = prime * result + endRowNumber;
        result = prime * result + (hasHeads ? 1231 : 1237);
        result = prime * result + (hasKeys ? 1231 : 1237);
        result = prime * result + ((keyColumnName == null) ? 0 : keyColumnName.hashCode());
        result = prime * result + sheetNumber;
        result = prime * result + ((startColumnName == null) ? 0 : startColumnName.hashCode());
        result = prime * result + startRowNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TableParsingCriteria other = (TableParsingCriteria) obj;
        if (endColumnName == null) {
            if (other.endColumnName != null) {
                return false;
            }
        } else if (!endColumnName.equals(other.endColumnName)) {
            return false;
        }
        if (endRowNumber != other.endRowNumber) {
            return false;
        }
        if (hasHeads != other.hasHeads) {
            return false;
        }
        if (hasKeys != other.hasKeys) {
            return false;
        }
        if (keyColumnName == null) {
            if (other.keyColumnName != null) {
                return false;
            }
        } else if (!keyColumnName.equals(other.keyColumnName)) {
            return false;
        }
        if (sheetNumber != other.sheetNumber) {
            return false;
        }
        if (startColumnName == null) {
            if (other.startColumnName != null) {
                return false;
            }
        } else if (!startColumnName.equals(other.startColumnName)) {
            return false;
        }
        if (startRowNumber != other.startRowNumber) {
            return false;
        }
        return true;
    }

}
