package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 201228B
 *******************************************************************/
public class SportsDataWriter
{
    private final File coversUpdatedOutputFile = new File("/Users/vicwintriss/Desktop/coversUpdatedOutputFile.xlsx");
    private XSSFWorkbook coversUpdatedWorkbook;
    public void writeSportsUpdatedWorkbook()
    {
        coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(0).setCellValue("Boo");
        System.out.println("00000000000000000000000 " + coversUpdatedWorkbook.getSheetAt(0).getRow(3).getCell(0).getStringCellValue());
            System.out.println("(9) Writing covers workbook to File: " + coversUpdatedOutputFile);
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
            System.out.println("(10) Finished writing budget workbook to File: " + coversUpdatedOutputFile);
    }
    public void setCoversUpdatedWorkbook(XSSFWorkbook coversUpdatedWorkbook)
    {
        this.coversUpdatedWorkbook = coversUpdatedWorkbook;
    }
}
