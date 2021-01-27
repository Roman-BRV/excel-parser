# excel-parser

In this application you can read the area of any Excel file from your computer and convert it to java object.
Parsing area of Excel file converts to object - Table: String [][] + 2 lists of Heads and Keys.
Table is able for converting to JSON :
TableName {
Key{Head:value, Head:value, ...}
Key{}
...
}
And another automatically work with data. Application can help a lot in work with many typical Excel files, reports for example.

Console (yet) user interface please insert next information:
1. Enter path to file. You can enter the path to directory, and it will help you find the file.
2. Enter name of data, which will be read. For name of future object.
3. Enter coordinates of first and last cells. The Area need to be rectangle — it's a table.
4. Choose Heads row. It can be the first row of chosen area or names of Excel column. For Heads in future object.
5. Choose Keys row. It can be selected column of chosen area or names of Excel row. For Keys in future object.

This version of application only print object Table to console, but it can be used as module in another application. 