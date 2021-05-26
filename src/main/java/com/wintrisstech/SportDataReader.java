package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210525
 * Read large SportData excel work book (SportData.xlsx) on user's desktop and return workBook
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
public class SportDataReader
{
    private XSSFWorkbook sportDataWorkBook;
    private File sportDataInputFile;
    public XSSFSheet readSportData(String sportDataPath)
    {
        try
        {
            System.out.println("(2) Reading SportData.xlsx from user's desktop.");
            System.out.println("In sportDataReader(), sportData path => " + sportDataPath);
            sportDataInputFile = new File(sportDataPath);//On user's desktop
            FileInputStream sportDataFIS = new FileInputStream(sportDataInputFile);
            sportDataWorkBook = new XSSFWorkbook(sportDataFIS);
            sportDataFIS.close();
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data");
            e.printStackTrace();
        }
        return sportDataWorkBook.getSheetAt(0);
    }
}
