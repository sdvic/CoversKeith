package com.wintrisstech;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102014
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportsDataWriter
{
    private String homeTeam;
    private String awayTeam;
    private int i;
    public SportsDataWriter(String desktopPath, XSSFWorkbook sportDataWorkbook, String totalHomePicks, String totalAwayPicks, String homeTeam, String awayTeam, String matchupsDate, int i)
    {
        XSSFWorkbook updatedSportWorkbook = sportDataWorkbook;
        File coversUpdatedOutputFile = new File(desktopPath + "/UpdatedSportData.xlsx");
        System.out.println("(7) Writing " + coversUpdatedOutputFile);
        XSSFSheet coversSheet = updatedSportWorkbook.getSheetAt(0);
        coversSheet.getRow(0).getCell(0).setCellValue("Updated " + new Date().toString());
        coversSheet.getRow(i + 2).getCell(60 - 1).setCellValue(totalHomePicks);//BH
        coversSheet.getRow(i + 2).getCell(62 - 1).setCellValue(totalAwayPicks);//BJ
        System.out.println("(8) Writing covers workbook xlsx");// to File: " + coversUpdatedOutputFile);
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
