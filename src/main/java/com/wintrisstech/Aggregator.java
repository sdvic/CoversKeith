package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210523
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Date;
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
    private int gameCount;
    private final int rowOffset = 3;
    private String thisGameDate;
    private int numberOfGamesThisWeek;
    private String nflSeasonYear;
    private Document thisWeekGameDoc;
    private Elements thisWeekElements;
    private Document nflRandomDoc;
    private String gameDate;
    private HashMap<String, String> weekList = new HashMap<>();
    private Sheet sportDataSheet;
    private Workbook sportDataWorkBook = new XSSFWorkbook();
    private WebSiteReader websiteReader;
    public void aggregateSportData(Elements thisMatchupConsensus, Sheet sportDataSheet)
    {
        this.sportDataSheet = sportDataSheet;
        out.println("(4) Aggregating Covers info");
        //for (int i = 0; i < 1; i++)//thisMatchupConsensus.size()
        {
            out.println("In aggregateSportData(), thisMatchupConsensus.size => " + thisMatchupConsensus.size());
            awayTeam = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-sideHeadLeft").text();
            awayTeam = awayTeam.replaceAll("[^\\sa-zA-Z]", "").trim();
            homeTeam = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-sideHeadRight").text();
            homeTeam = homeTeam.replaceAll("[^\\sa-zA-Z]", "").trim();
            Elements rightConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersright");
            Elements leftConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersleft");
            away = leftConsensus.text();
            over = leftConsensus.text();
            under = rightConsensus.text();
            home = rightConsensus.text();
            out.println("away team => " + awayTeam);
            out.println("home team => " + homeTeam);
            out.println("away = > " + away);
            out.println("over = > " + over);
            out.println("under = > " + under);
            out.println("home = > " + home);
        }
        byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
        CellStyle myStyle = sportDataWorkBook.createCellStyle();
        Font myFont = sportDataWorkBook.createFont();
        myFont.setBold(true);
        myStyle.setFont(myFont);
        XSSFFont xssfFont = (XSSFFont) myFont;
        xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
        //sportDataSheet.getRow(0).getCell(0).setCellStyle(myStyle);
        sportDataSheet.getRow(0).getCell(0).setCellValue("Updated " + new Date().toString());
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(0).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(1).setCellStyle(myStyle);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(1).setCellValue(thisGameDate);
//        sportDataSheet.getRow(rowOffset + gameCount).getCell(2).setCellStyle(myStyle);
//        //nflSeasonYear = matchupsCalendarDate.substring(0,4);
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
    }
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
    public void setSportDataWorkBook(XSSFSheet sportDataWorkBook)
    {
//        try
//        {
//            this.sportDataWorkBook = sportDataWorkBook;
//        }
//        catch (Exception e)
//        {
//
//        }
    }
}
