package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210102A
 *******************************************************************/
public class SportsDataWriter
{
    String desktopPath = System.getProperty("user.home") + "/Desktop";
    private final File coversUpdatedOutputFile = new File(desktopPath + "/coversUpdatedOutputFile.xlsx");
    private XSSFWorkbook coversUpdatedWorkbook;
    private int totalHomePicks;
    private int totalAwayPicks;

    public void writeSportsUpdatedWorkbook()
    {
        coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(0).setCellValue(totalHomePicks);
        System.out.println("Got home picks => " + totalHomePicks);
        System.out.println("Got away picks => " + totalAwayPicks);
        coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(69).setCellValue(totalHomePicks);
        System.out.println("======================Added: " + totalHomePicks +  " to updated Sports Data file column 69 (BR)...home picks.");
        coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(71).setCellValue(totalAwayPicks);
        System.out.println("======================Added: " + totalAwayPicks +  " to updated Sports Data file column 71 (BT)...away picks.");
            System.out.println("(4) Writing covers workbook to File: " + coversUpdatedOutputFile);
            try
            {
                FileOutputStream budgetOutputFOS = new FileOutputStream(coversUpdatedOutputFile);
                coversUpdatedWorkbook.write(budgetOutputFOS);
                budgetOutputFOS.close();
            }
            catch (Exception e)
            {
                System.out.println("Sports Data xlsx file writing problem");
                e.printStackTrace();
            }
        System.out.println("(5) Finished writing updated SportData.xlsx workbook to File: " + coversUpdatedOutputFile);
    }
    public void setCoversUpdatedWorkbook(XSSFWorkbook coversUpdatedWorkbook)
    {
        this.coversUpdatedWorkbook = coversUpdatedWorkbook;
    }
    public void setTotalHomePicks(int totalHomePicks)
    {
        this.totalHomePicks = totalHomePicks;
    }
    public void setTotalAwayPicks(int totalAwayPicks)
    {
        this.totalAwayPicks = totalAwayPicks;
    }
}
