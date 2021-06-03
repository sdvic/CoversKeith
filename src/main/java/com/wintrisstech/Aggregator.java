package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210603A
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;

import static java.lang.System.out;
public class Aggregator
{
    private String under;
    private String home;
    private String away;
    private String over;
    private String dataEventID;
    private String homeTeam;
    private String awayTeam;
    private int gameCount = 7;
    private final int rowOffset = 3;
    private String thisGameDate;
    private int numberOfGamesThisWeek;
    private String nflSeasonYear;
    private Document thisWeekGameDoc;
    private Elements thisWeekElements;
    private Document nflRandomDoc;
    private String gameDate;
    private HashMap<String, String> weekList = new HashMap<>();
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    private HashMap<String, String> thisWeekGameDatesMap = new HashMap<>();
    private Sheet sportDataSheet;
    private XSSFWorkbook sportDataWorkBook = new XSSFWorkbook();
    private XSSFWorkbook updatedSportDataWorkBook;
    private WebSiteReader websiteReader;
    private SportDataReader sportDataReader;
    private XSSFSheet sportDataUpdateSheet = null;
    byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
    public XSSFWorkbook buildSportDataUpdate(XSSFWorkbook sportDataWorkbook, String dataEventID, int eventIndex)
    {
        //sportDataWorkbook.createSheet("Update");
        sportDataSheet = sportDataWorkbook.getSheet("Update");
        sportDataSheet.createRow(eventIndex);
        sportDataSheet.getRow(eventIndex).createCell(0);
        sportDataSheet.getRow(eventIndex).createCell(1);
        CellStyle myStyle = sportDataWorkbook.createCellStyle();
        sportDataSheet.getRow(eventIndex).getCell(0).setCellStyle(myStyle);
        Font myFont = sportDataWorkbook.createFont();
        myFont.setBold(true);
        myStyle.setFont(myFont);
        XSSFFont xssfFont = (XSSFFont) myFont;
        xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
        homeTeam = thisWeekHomeTeamsMap.get(dataEventID);
        awayTeam = thisWeekAwayTeamsMap.get(dataEventID);
        thisGameDate = thisWeekGameDatesMap.get(dataEventID);
        out.println("************************** eventIndex => "+ eventIndex + " dataEventID => " + dataEventID + " homeTeam => " + homeTeam + " awayTeam => " + awayTeam);
        sportDataSheet.getRow(eventIndex).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
        sportDataSheet.getRow(eventIndex).getCell(1).setCellValue(thisGameDate);
        return sportDataWorkbook;
    }

//        sportDataSheet.getRow(rowOffset + gameCount).getCell(2).setCellStyle(myStyle);
//        //nflSeasonYear = matchupsCalendarDate.substring(0,4);
//        Row rr = sportDataSheet.createRow(rowOffset + gameCount);
//        rr.createCell(2);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(2).setCellValue(nflSeasonYear);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(3).setCellStyle(myStyle);
//        //sportDataSheet.getRow(rowOffset + gameCount).getCell(3).setCellValue("week" + weekNumberString);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(59).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(59).setCellValue(home);//BH60
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(61).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(61).setCellValue(away);//BJ62
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(64).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(64).setCellValue(over);//BM65
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(66).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(66).setCellValue(under);//BO67
        //JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\n" + "Week Date " + matchupsCalendarDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + getOver() + "\nUnder " + getUnder() + "\nHome " + getHome() + "\nAway " + getAway(), "Sharp Markets version " + version, JOptionPane.INFORMATION_MESSAGE);
//        gameCount++;
//        XSSFSheet sheet = sportDataWorkbook.getSheet("Update");
//        byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
//        CellStyle myStyle = sportDataWorkbook.createCellStyle();
//        Font myFont = sportDataWorkbook.createFont();
//        myFont.setBold(true);
//        myStyle.setFont(myFont);
//        XSSFFont xssfFont = (XSSFFont) myFont;
//        xssfFont.setColor(new XSSFColor(rgb, null));
        //sheet.getRow(0).getCell(0).setCellStyle(myStyle);
//        sheet.getRow(0).getCell(0).setCellValue("Hoorah");

    public String getUnder()
    {
        return under;
    }
    public String getOver()
    {
        return over;
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public void setHomeTeam(String homeTeam)
    {
        this.homeTeam = homeTeam;
        out.println("setting home team in aggregator => " + homeTeam);
    }
    public void setAwayTeam(String awayTeam)
    {
        this.awayTeam = awayTeam;
        out.println("setting away team in aggregator => " + awayTeam);
    }
    public XSSFWorkbook getSportDataWorkBook()
    {
        return sportDataWorkBook;
    }
    public void setThisWeekElements(Elements thisWeekElements)
    {
        this.thisWeekElements = thisWeekElements;
    }
    public void setUpdatedSportDataWorkBook(XSSFWorkbook updatedSportDataWorkBook)
    {
        this.updatedSportDataWorkBook = updatedSportDataWorkBook;
    }
    public void setSportDataReader(SportDataReader sportDataReader)
    {
        this.sportDataReader = sportDataReader;
    }
    public XSSFSheet getSportDataUpdateSheet()
    {
        return sportDataUpdateSheet;
    }
    public void setThisWeekHomeTeamsMap(HashMap<String, String> thisWeekHomeTeamsMap)
    {
        this.thisWeekHomeTeamsMap = thisWeekHomeTeamsMap;
    }
    public void setThisWeekAwayTeamsMap(HashMap<String, String> thisWeekAwayTeamsMap)
    {
        this.thisWeekAwayTeamsMap = thisWeekAwayTeamsMap;
    }
    public void setThisWeekGameDatesMap(HashMap<String, String> thisWeekGameDatesMap)
    {
        this.thisWeekGameDatesMap = thisWeekGameDatesMap;
    }
}
