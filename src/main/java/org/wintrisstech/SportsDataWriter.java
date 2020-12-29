package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 201228A
 *******************************************************************/
public class SportsDataWriter
{
    private final File coversUpdatedOutputFile = new File("/Users/vicwintriss/Desktop/coversUpdatedOutputFile.xlsx");
    private XSSFWorkbook coversUpdatedWorkbook;
    public SportsDataWriter()
    {
            System.out.println("(9) Writing covers workbook to File: " + coversUpdatedOutputFile);
            try
            {
                FileOutputStream budgetOutputFOS = new FileOutputStream(coversUpdatedOutputFile);
                coversUpdatedWorkbook.write(budgetOutputFOS);
                budgetOutputFOS.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("(10) Finished writing budget workbook to File: " + coversUpdatedOutputFile);
    }
}
