package ua.pp.helperzit.excelparser.service.models;

public class TableParsingCriteria {
    
    private static final int UNEXIST_KEY_COLUMN_NUMBER = -1;

    private int sheetNumber;
    private int startRowNumber;
    private int startColunmNumber;
    private int endRowNumber;
    private int endColunmNumber;
    private boolean hasHeads;
    private boolean hasKeys;
    private int keyColunmNumber;

    public TableParsingCriteria() {
        
        if(!hasKeys) {
            this.keyColunmNumber = UNEXIST_KEY_COLUMN_NUMBER;
        }

    }

    public TableParsingCriteria(int sheetNumber, int startRowNumber, int startColunmNumber, int endRowNumber, int endColunmNumber, boolean hasHeads, boolean hasKeys, int keyColunmNumber) {

        this.sheetNumber = sheetNumber;
        this.startRowNumber = startRowNumber;
        this.startColunmNumber = startColunmNumber;
        this.endRowNumber = endRowNumber;
        this.endColunmNumber = endColunmNumber;
        this.hasHeads = hasHeads;
        this.hasKeys = hasKeys;
        this.keyColunmNumber = keyColunmNumber;
    }

    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
    }

    public int getStartRowNumber() {
        return startRowNumber;
    }

    public void setStartRowNumber(int startRowNumber) {
        this.startRowNumber = startRowNumber;
    }

    public int getStartColunmNumber() {
        return startColunmNumber;
    }

    public void setStartColunmNumber(int startColunmNumber) {
        this.startColunmNumber = startColunmNumber;
    }

    public int getEndRowNumber() {
        return endRowNumber;
    }

    public void setEndRowNumber(int endRowNumber) {
        this.endRowNumber = endRowNumber;
    }

    public int getEndColunmNumber() {
        return endColunmNumber;
    }

    public void setEndColunmNumber(int endColunmNumber) {
        this.endColunmNumber = endColunmNumber;
    }

    public boolean isHasHeads() {
        return hasHeads;
    }

    public void setHasHeads(boolean hasHead) {
        this.hasHeads = hasHead;
    }

    public boolean isHasKeys() {
        return hasKeys;
    }

    public void setHasKeys(boolean hasKey) {
        this.hasKeys = hasKey;
    }

    public int getKeyColunmNumber() {
        return keyColunmNumber;
    }

    public void setKeyColunmNumber(int keyColunmNumber) {
        this.keyColunmNumber = keyColunmNumber;
    }

    @Override
    public String toString() {
        return "TableParserCriteria [sheetNumber=" + sheetNumber + ", startRowNumber=" + startRowNumber
                + ", startColunmNumber=" + startColunmNumber + ", endRowNumber=" + endRowNumber + ", endColunmNumber="
                + endColunmNumber + ", hasHead=" + hasHeads + ", hasKey=" + hasKeys + ", keyColunmNumber="
                + keyColunmNumber + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endColunmNumber;
        result = prime * result + endRowNumber;
        result = prime * result + (hasHeads ? 1231 : 1237);
        result = prime * result + (hasKeys ? 1231 : 1237);
        result = prime * result + keyColunmNumber;
        result = prime * result + sheetNumber;
        result = prime * result + startColunmNumber;
        result = prime * result + startRowNumber;
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
        TableParsingCriteria other = (TableParsingCriteria) obj;
        if (endColunmNumber != other.endColunmNumber)
            return false;
        if (endRowNumber != other.endRowNumber)
            return false;
        if (hasHeads != other.hasHeads)
            return false;
        if (hasKeys != other.hasKeys)
            return false;
        if (keyColunmNumber != other.keyColunmNumber)
            return false;
        if (sheetNumber != other.sheetNumber)
            return false;
        if (startColunmNumber != other.startColunmNumber)
            return false;
        if (startRowNumber != other.startRowNumber)
            return false;
        return true;
    }

}
