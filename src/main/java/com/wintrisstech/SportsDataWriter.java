package com.wintrisstech;


import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Date;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102021
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportsDataWriter
{
    private int i;//week counter
    private int j;//game counter
    private int rowOffset;
    public SportsDataWriter(String desktopPath, XSSFWorkbook sportDataWorkbook, String weekNumberString, String homeTeam, String awayTeam, String homePicks, String awayPicks, String underPicks, String overPicks, int i, int j)
    {
        XSSFWorkbook updatedSportWorkbook = sportDataWorkbook;
        File coversUpdatedOutputFile = new File(desktopPath + "/SportData.xlsx");
        System.out.println("(8) Writing " + coversUpdatedOutputFile);

        System.out.println("(9) Writing covers workbook xlsx");// to File: " + coversOutputFile);
        try
        {
            FileOutputStream coversUpdatedFOS = new FileOutputStream(coversUpdatedOutputFile);
            updatedSportWorkbook.write(coversUpdatedFOS);
            coversUpdatedFOS.close();
        }
        catch (Exception e)
        {
            System.out.println("Sports Data xlsx file writing problem");
            e.printStackTrace();
        }
        System.out.println("(10) Finished writing updated SportData.xlsx");// workbook to File: " + coversUpdatedOutputFile);
    }
    public void setI(int i)
    {
        this.i = i;
    }
}
