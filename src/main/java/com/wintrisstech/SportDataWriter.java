package com.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210525
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportDataWriter
{
    private File updatedSportDataFile;
    private XSSFWorkbook updatedSportDataWorkbook;
    public void writeSportData(String updatedSportDataPath)
    {
        System.out.println("(8) *NOT* Writing updatedSportData File to: " + updatedSportDataPath);
        System.out.println("In SportDataWriter, updatedSportDataFilePath => " + updatedSportDataPath);
        try
        {
            //FileOutputStream updatedSportDataFOS = new FileOutputStream(updatedSportDataPath);
            //updatedSportDataWorkbook.write(updatedSportDataFOS);
            //updatedSportDataFOS.close();
        }
        catch (Exception e)
        {
            System.out.println("Sports Data xlsx file writing problem");
            e.printStackTrace();
        }
        System.out.println("(10) Finished writing updated SportData.xlsx");// workbook to File: " + sportDataFile);
    }
}
