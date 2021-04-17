package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210416
 * Read large SportData excel work book (SportData.xlsx) on user's desktop and return workBook
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
    public XSSFWorkbook readSportData(String userDeskTopPath)
    {
        try
        {
            System.out.println("(2) Reading SportsData.xlsx on user's desktop.");
            sportDataInputFile = new File(userDeskTopPath + "/SportData.xlsx");/* End user's desktop */
            FileInputStream sportDataFIS = new FileInputStream(sportDataInputFile);
            sportDataWorkBook = new XSSFWorkbook(sportDataFIS);
            sportDataFIS.close();
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data");
            e.printStackTrace();
        }
        System.out.println("(3) Finished reading SportsData Excel file, size => TODO get file size" );
        return sportDataWorkBook;
    }
}
