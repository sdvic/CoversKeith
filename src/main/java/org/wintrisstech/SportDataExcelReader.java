package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210104A
 * Read large SportData excel work sheet into sportData hashmap
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
public class SportDataExcelReader
{
    private File sportDataInputFile;
    private XSSFWorkbook sportDataWorkBook;
    private String keyValue;
    private double valueValue;
    private final HashMap<String, Double> sportDataMap = new HashMap<String, Double>();
    public SportDataExcelReader(String deskTopPath)
    {
        try
        {
            sportDataInputFile = new File(deskTopPath + "/SportData.xlsx");/* End user's desktop */
            System.out.println("(2) Started reading SportsData Excel file from: " + sportDataInputFile + " to: " + sportDataMap.getClass() + " size => " + sportDataMap.size());
            FileInputStream sportsDataFIS = new FileInputStream(sportDataInputFile);
            sportDataWorkBook = new XSSFWorkbook(sportsDataFIS);
            sportsDataFIS.close();
        }
        catch (Exception e)
        {
            System.out.println("FileNotFoundException in read Sports Data toHashMap()");
            e.printStackTrace();
        }
        FormulaEvaluator evaluator = sportDataWorkBook.getCreationHelper().createFormulaEvaluator();
        XSSFSheet sportDataSheet = sportDataWorkBook.getSheetAt(0);
        for (int rowIndex = 0; rowIndex < sportDataSheet.getLastRowNum(); rowIndex++)
        {
            XSSFRow row = sportDataSheet.getRow(rowIndex);
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
                //System.out.println("Can't get key String value at line 65 while reading Sports Data");
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
                //System.out.println("Can't get numeric value at line 80");
            }
            sportDataMap.put(keyValue, valueValue);
        }
//        System.out.println("        ===========Sports Data Map======================");
//        sportDataMap.forEach((K, V) -> System.out.println("             " +  K + " => " + V ));
        System.out.println("(3) Finished reading SportData Excel file from: " + sportDataInputFile + " to: SportDataHashMap, HashMap size: " + sportDataMap.size());
    }
    public XSSFWorkbook getSportDataWorkBook()
    {
        return sportDataWorkBook;
    }
}
