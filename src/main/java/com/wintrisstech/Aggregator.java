package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210613A
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;

import static java.lang.System.out;
public class Aggregator
{
    private String ouUnder;
    private String home;
    private String away;
    private String ouOver;
    private String dataEventID;
    private String homeTeam;
    private String awayTeam;
    private int gameCount = 7;
    private final int rowOffset = 3;
    private String thisMatchupDate;
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekGameDatesMap = new HashMap<>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private Sheet sportDataSheet;
    private XSSFWorkbook sportDataWorkBook = new XSSFWorkbook();
    private XSSFSheet sportDataUpdateSheet = null;
    byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
    private String atsHome;
    private String atsAway;
    private HashMap<String, String> ouOversMap;
    private HashMap<String, String> ouUndersMap;
    public XSSFWorkbook buildSportDataUpdate(XSSFWorkbook sportDataWorkbook, String dataEventID, int eventIndex)
    {
        sportDataSheet = sportDataWorkbook.getSheet("Data");
        sportDataSheet.createRow(eventIndex);
        sportDataSheet.setColumnWidth(1, 25 * 256);
        CellStyle myStyle = sportDataWorkbook.createCellStyle();
        Font myFont = sportDataWorkbook.createFont();
        myFont.setBold(true);
        myStyle.setFont(myFont);
        XSSFFont xssfFont = (XSSFFont) myFont;
        xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
        homeTeam = thisWeekHomeTeamsMap.get(dataEventID);
        awayTeam = thisWeekAwayTeamsMap.get(dataEventID);
        thisMatchupDate = thisWeekGameDatesMap.get(dataEventID);
        atsHome = atsHomesMap.get(dataEventID);
        atsAway = atsAwaysMap.get(dataEventID);
        ouOver = ouOversMap.get(dataEventID);
        ouUnder = ouUndersMap.get(dataEventID);
        out.println("................................ "+ eventIndex + " " + dataEventID + " " + homeTeam + " " + awayTeam + " " + thisMatchupDate + " " + atsHome + " " + atsAway + " " + ouOver + " " + ouUnder);
        sportDataSheet.getRow(eventIndex).createCell(0);
        sportDataSheet.getRow(eventIndex).getCell(0).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
        sportDataSheet.getRow(eventIndex).createCell(1);
        sportDataSheet.getRow(eventIndex).getCell(1).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(1).setCellValue(thisMatchupDate);
        sportDataSheet.getRow(eventIndex).createCell(59);
        sportDataSheet.getRow(eventIndex).getCell(59).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(59).setCellValue(atsHome);
        sportDataSheet.getRow(eventIndex).createCell(61);
        sportDataSheet.getRow(eventIndex).getCell(61).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(61).setCellValue(atsAway);
        sportDataSheet.getRow(eventIndex).createCell(64);
        sportDataSheet.getRow(eventIndex).getCell(64).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(64).setCellValue(ouOver);
        sportDataSheet.getRow(eventIndex).createCell(66);
        sportDataSheet.getRow(eventIndex).getCell(66).setCellStyle(myStyle);
        sportDataSheet.getRow(eventIndex).getCell(66).setCellValue(ouUnder);
        return sportDataWorkbook;
    }
    public void setThisWeekHomeTeamsMap(HashMap<String, String> thisWeekHomeTeamsMap){this.thisWeekHomeTeamsMap = thisWeekHomeTeamsMap;}
    public void setThisWeekAwayTeamsMap(HashMap<String, String> thisWeekAwayTeamsMap){this.thisWeekAwayTeamsMap = thisWeekAwayTeamsMap;}
    public void setThisWeekGameDatesMap(HashMap<String, String> thisWeekGameDatesMap) {this.thisWeekGameDatesMap = thisWeekGameDatesMap;}
    public void setAtsHomesMap(HashMap<String, String> atsHomes)
    {
        this.atsHomesMap = atsHomes;
    }
    public void setAtsAwaysMap(HashMap<String, String> atsAwayMap)
    {
        this.atsAwaysMap = atsAwayMap;
    }
    public void setOuOversMap(HashMap<String, String> ouOversMap)
    {
        this.ouOversMap = ouOversMap;
    }
    public void setOuUndersMap(HashMap<String, String> ouUndersMap)
    {
        this.ouUndersMap = ouUndersMap;
    }
}
