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
 // * version 2102020
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportsDataWriter
{
    private int i;
    public SportsDataWriter(String desktopPath, XSSFWorkbook sportDataWorkbook, String weekNumberString, String homeTeam, String awayTeam, String homePicks, String awayPicks, String underPicks, String overPicks)
    {
        XSSFWorkbook updatedSportWorkbook = sportDataWorkbook;
        File coversUpdatedOutputFile = new File(desktopPath + "/UpdatedSportData.xlsx");
        System.out.println("(8) Writing " + coversUpdatedOutputFile);
        XSSFSheet coversSheet = updatedSportWorkbook.getSheetAt(0);
        coversSheet.getRow(0).getCell(0).setCellValue(new Date().getTime());
        coversSheet.getRow(i + 2).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
        coversSheet.getRow(i + 2).getCell(4 - 1).setCellValue("week" + weekNumberString);
        coversSheet.getRow(i + 2).getCell(60 - 1).setCellValue(homePicks);//BH60
        coversSheet.getRow(i + 2).getCell(62 - 1).setCellValue(awayPicks);//BJ62
        coversSheet.getRow(i + 2).getCell(65 - 1).setCellValue(overPicks);//BM65
        coversSheet.getRow(i + 2).getCell(67 - 1).setCellValue(underPicks);//BO67
        System.out.println("(9) Writing covers workbook xlsx");// to File: " + coversUpdatedOutputFile);
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
