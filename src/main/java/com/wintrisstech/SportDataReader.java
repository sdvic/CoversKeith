package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210324
 * Read large SportData excel work sheet into sportData hashmap
 *******************************************************************/
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
public class SportDataReader
{
    private XSSFWorkbook sportDataWorkBook;
    private XSSFSheet sportDataSheet;
    private File sportDataInputFile;
    public  XSSFSheet readSportData(String deskTopPath)
    {
        try
        {
            System.out.println("(2) Reading SportsData Excel file");// from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
            sportDataInputFile = new File(deskTopPath + "/SportData.xlsx");/* End user's desktop */
            FileInputStream sportDataFIS = new FileInputStream(sportDataInputFile);
            System.out.println("(2) Reading SportsData Excel file");// from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
            sportDataWorkBook = new XSSFWorkbook(sportDataFIS);
            sportDataFIS.close();
            sportDataSheet = sportDataWorkBook.getSheetAt(0);
            for (Row r : sportDataSheet)
            {

                System.out.println("row[" + r.getRowNum() + "] = " + r.getCell(2));
            }
            System.out.println(sportDataSheet);
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data");
            e.printStackTrace();
        }
        System.out.println("(3) Finished reading SportsData Excel file");// from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
        return sportDataSheet;
    }
}
