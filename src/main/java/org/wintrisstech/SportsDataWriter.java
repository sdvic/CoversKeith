package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210101A
 *******************************************************************/
public class SportsDataWriter
{
    String desktopPath = System.getProperty("user.home") + "/Desktop";
    private final File coversUpdatedOutputFile = new File(desktopPath + "/coversUpdatedOutputFile.xlsx");
    private XSSFWorkbook coversUpdatedWorkbook;
    public void writeSportsUpdatedWorkbook()
    {
        coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(0).setCellValue("New Data to any desktop");
        System.out.println("======================Added: " + coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(0).getStringCellValue() + " to updated Sprots Data file.");
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
}
