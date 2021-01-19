package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210118
 * Read large SportData excel work sheet into sportData hashmap
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.HashMap;
public class SportDataExcelReader
{
    private File sportDataInputFile;
    private XSSFWorkbook sportDataWorkBook;
    private String keyValue;
    private double valueValue;
    private final HashMap<String, Double> sportDataMap = new HashMap<String, Double>();
    public SportDataExcelReader(String deskTopPath) throws ParseException
    {
        try
        {
            sportDataInputFile = new File(deskTopPath + "/SportData.xlsx");/* End user's desktop */
            System.out.println("(2) Started reading SportsData Excel file from: " + sportDataInputFile + " to: " + getSportDataMap().getClass().getName());
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
                CellValue keyCellValue = evaluator.evaluate(keyCell);
                String keyStringRaw = ((CellValue) keyCellValue).formatAsString().trim();//Found Key String
                keyValue = keyStringRaw.replaceAll("^\"+|\"+$", "").trim();//Strip off quote signs
                keyValue.trim();
            }
            catch (Exception e)
            {
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
            }

            getSportDataMap().put(keyValue, valueValue);
        }
        System.out.println("(3) Finished building sportsDataMap from SportData.xlsx.  Size => " + sportDataMap.size());
//        for (Map.Entry<String, Double> entry : getSportDataMap().entrySet())
//        {
//            String K = entry.getKey();
//            Double V = entry.getValue();
//            System.out.println("             " + K + " => " + V);
//            System.out.println("____________________V=>" + V);
//            double x = V;
//            System.out.println("____________________x=>" + x);
//            String s = String.format("%f", x);
//            System.out.println("____________________s=>" + s);
//        }
    }
    public XSSFWorkbook getSportDataWorkBook()
    {
        return sportDataWorkBook;
    }
    public HashMap<String, Double> getSportDataMap()
    {
        return sportDataMap;
    }
}
