package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * Read large SportData excel work book (SportData.xlsx) on user's desktop and return workBook
 * version 210628
 *******************************************************************/
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
public class SportDataReader
{
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private XSSFWorkbook sportDataWorkbook;
    private InputStream is;
    public XSSFWorkbook readSportData()
    {
        try
        {
            is = new FileInputStream(deskTopPath + "/SportData.xlsx");
            sportDataWorkbook = (XSSFWorkbook) WorkbookFactory.create(is);
            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sportDataWorkbook;
    }
}