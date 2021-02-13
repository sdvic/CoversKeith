package com.wintrisstech;


import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102012A
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
        System.out.println("=================================updating row " + i);
        coversSheet.getRow(i).getCell(0).setCellValue(matchupsDate + "-" + awayTeam + " @ " + homeTeam);
        coversSheet.getRow(i).getCell(1).setCellValue(matchupsDate);
        coversSheet.getRow(i).getCell(69).setCellValue(totalHomePicks);//BR70
        coversSheet.getRow(i).getCell(71).setCellValue(totalAwayPicks);//BT72
        System.out.println("(8) Finished writing covers workbook to File: " + coversUpdatedOutputFile);
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
        System.out.println("(10) Finished writing updated SportData.xlsx workbook to File: " + coversUpdatedOutputFile);
    }
    public void setI(int i)
    {
        this.i = i;
    }
}
