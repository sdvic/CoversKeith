package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210118
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportsDataWriter
{
    public SportsDataWriter(String desktopPath, XSSFWorkbook sportDataWorkbook, String totalHomePicks, String totalAwayPicks)
    {
        XSSFWorkbook updatedSportWorkbook = sportDataWorkbook;
        File coversUpdatedOutputFile = new File(desktopPath + "/UpdatedSportData.xlsx");
        updatedSportWorkbook.getSheetAt(0).getRow(3).getCell(0).setCellValue(totalHomePicks);
        XSSFSheet coversSheet = updatedSportWorkbook.getSheetAt(0);
        coversSheet.getRow(3).getCell(0).setCellValue("Buffalo at Denver");
        coversSheet.getRow(3).getCell(69).setCellValue(totalHomePicks);
        coversSheet.getRow(3).getCell(71).setCellValue(totalAwayPicks);
        System.out.println("(9) Writing covers workbook to File: " + coversUpdatedOutputFile);
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
}
