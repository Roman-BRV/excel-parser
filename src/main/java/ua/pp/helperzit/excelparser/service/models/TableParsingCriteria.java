package ua.pp.helperzit.excelparser.service.models;

public class TableParsingCriteria {

    private static final String DEFAULT_KEY_COLUMN_NAME = "default";

    private int sheetNumber;
    private int startRowNumber;
    private String startColunmName;
    private int endRowNumber;
    private String endColunmName;
    private boolean hasHeads;
    private boolean hasKeys;
    private String keyColunmName;

    public TableParsingCriteria() {

        this.keyColunmName = DEFAULT_KEY_COLUMN_NAME;

    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public int getStartRowNumber() {
        return startRowNumber;
    }

    public String getStartColunmName() {
        return startColunmName;
    }

    public int getEndRowNumber() {
        return endRowNumber;
    }

    public String getEndColunmName() {
        return endColunmName;
    }

    public boolean isHasHeads() {
        return hasHeads;
    }

    public boolean isHasKeys() {
        return hasKeys;
    }

    public String getKeyColunmName() {
        return keyColunmName;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public void setStartRowNumber(int startRowNumber) {
        this.startRowNumber = startRowNumber;
    }

    public void setStartColunmName(String startColunmName) {
        this.startColunmName = startColunmName;
    }

    public void setEndRowNumber(int endRowNumber) {
        this.endRowNumber = endRowNumber;
    }

    public void setEndColunmName(String endColunmName) {
        this.endColunmName = endColunmName;
    }

    public void setHasHeads(boolean hasHeads) {
        this.hasHeads = hasHeads;
    }

    public void setHasKeys(boolean hasKeys) {
        this.hasKeys = hasKeys;
    }

    public void setKeyColunmName(String keyColunmName) {
        this.keyColunmName = keyColunmName;
    }

    @Override
    public String toString() {
        return "TableParsingCriteria [sheetNumber=" + sheetNumber + ", startRowNumber=" + startRowNumber
                + ", startColunmName=" + startColunmName + ", endRowNumber=" + endRowNumber + ", endColunmName="
                + endColunmName + ", hasHeads=" + hasHeads + ", hasKeys=" + hasKeys + ", keyColunmName=" + keyColunmName
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endColunmName == null) ? 0 : endColunmName.hashCode());
        result = prime * result + endRowNumber;
        result = prime * result + (hasHeads ? 1231 : 1237);
        result = prime * result + (hasKeys ? 1231 : 1237);
        result = prime * result + ((keyColunmName == null) ? 0 : keyColunmName.hashCode());
        result = prime * result + sheetNumber;
        result = prime * result + ((startColunmName == null) ? 0 : startColunmName.hashCode());
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
        if (endColunmName == null) {
            if (other.endColunmName != null) {
                return false;
            }
        } else if (!endColunmName.equals(other.endColunmName)) {
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
        if (keyColunmName == null) {
            if (other.keyColunmName != null) {
                return false;
            }
        } else if (!keyColunmName.equals(other.keyColunmName)) {
            return false;
        }
        if (sheetNumber != other.sheetNumber) {
            return false;
        }
        if (startColunmName == null) {
            if (other.startColunmName != null) {
                return false;
            }
        } else if (!startColunmName.equals(other.startColunmName)) {
            return false;
        }
        if (startRowNumber != other.startRowNumber) {
            return false;
        }
        return true;
    }

}
