package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210401
 * Read large SportData excel work sheet into sportData hashmap
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
public class SportDataReader
{
    private XSSFWorkbook sportDataWorkBook;
    private XSSFSheet sportDataSheet;
    private File sportDataInputFile;
    public XSSFWorkbook readSportData(String deskTopPath)
    {
        try
        {
            System.out.println("(4) Reading SportsData Excel file");// from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
            sportDataInputFile = new File(deskTopPath + "/SportData.xlsx");/* End user's desktop */
            FileInputStream sportDataFIS = new FileInputStream(sportDataInputFile);
            sportDataWorkBook = new XSSFWorkbook(sportDataFIS);
            sportDataFIS.close();
            sportDataSheet = sportDataWorkBook.getSheetAt(0);
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data");
            e.printStackTrace();
        }
        System.out.println("(5) Finished reading SportsData Excel file");// from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
        return sportDataWorkBook;
    }
}
