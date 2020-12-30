package org.wintrisstech;
//******************************************************************************************
// * Application to extract Consensus xlsx data from Covers
// * version 2012229A
// * copyright 2020 Dan Farris
//*********************************************************************************
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 201229A
 *******************************************************************/
public class SportsDataReader
{
    private File sportDataInputFile;
    private XSSFWorkbook sportDataWorkBook;
    private XSSFCell valueCell;
    private XSSFCell keyCell;
    private String keyValue;
    private double valueValue;
    private final HashMap<String, Double> sportDataMap = new HashMap<String, Double>();
    private String errMsg;
    private int cellType;

    public SportsDataReader()
    {
        try
        {
            String inputFileName = "/Users/vicwintriss/Desktop/SportData2.xlsx";
            sportDataInputFile = new File(inputFileName);
            System.out.println("(2) Started reading SportsData Excel file from: " + sportDataInputFile + " to sportData HashMap");
            FileInputStream sportsDataFIS = new FileInputStream(sportDataInputFile);
            sportDataWorkBook = new XSSFWorkbook(sportsDataFIS);
            sportsDataFIS.close();
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data toHashMap()");
            e.printStackTrace();
        }
        FormulaEvaluator evaluator = getSportDataWorkBook().getCreationHelper().createFormulaEvaluator();
        XSSFSheet pandlSheet = getSportDataWorkBook().getSheetAt(0);
        for (int rowIndex = 0; rowIndex < pandlSheet.getLastRowNum(); rowIndex++)
        {
            XSSFRow row = pandlSheet.getRow(rowIndex);
            if (row == null)
            {
                continue;
            }
            XSSFCell keyCell = row.getCell(0); //Key cell
            if (keyCell == null)
            {
                continue;
            }
            try
            {
                keyValue = keyCell.getStringCellValue();
                final CellValue keyCellValue = evaluator.evaluate(keyCell);
                String keyStringRaw = ((CellValue) keyCellValue).formatAsString().trim();//Found Key String
                keyValue = keyStringRaw.replaceAll("^\"+|\"+$", "").trim();//Strip off quote signs
                keyValue.trim();
            }
            catch (Exception e)
            {
                System.out.println("Can't get key String value at line 65 while reading Sports Data");
                continue;
            }
            XSSFCell valueCell = row.getCell(1); //Value cell
            if (valueCell == null)
            {
                continue;
            }
            try
            {
                valueValue = valueCell.getNumericCellValue();
            }
            catch(Exception e)
            {
                System.out.println("Can't get numeric value at line 80");
            }
            getSportDataMap().put(keyValue, valueValue);
        }
        //System.out.println("        ===========Sports Data Map======================");
        //getSportDataMap().forEach((K, V) -> System.out.println("             " +  K + " => " + V ));
        System.out.println("(3) Finished reading SportData Excel file from: " + sportDataInputFile + " to: SportDataHashMap, HashMap size: " + getSportDataMap().size());
    }
    public HashMap<String, Double> getSportDataMap()
    {
        return sportDataMap;
    }
    public XSSFWorkbook getSportDataWorkBook()
    {
        return sportDataWorkBook;
    }
}
