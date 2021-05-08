package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210508
 *******************************************************************/
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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
    Document thisWeekGameDoc;
    Elements thisWeekElements;
    Document nflRandomDoc;
    String gameDate;
    private final HashMap<String, String> weekList = new HashMap<>();
    private Sheet sportDataSheet;
    private Workbook sportDataWorkBook = new XSSFWorkbook();
    private WebSiteReader websiteReader;
    public void aggregateSportsData(Elements thisMatchupConsensus) throws IOException
    {
        this.sportDataWorkBook = sportDataWorkBook;
        //sportDataSheet = sportDataWorkBook.getSheetAt(0);
        out.println("(4) Aggregating Covers info");
        //for (Element e : thisMatchupConsensus)
        for (int i = 0; i < 1; i++)//thisMatchupConsensus.size()
        {
            Element thisMatchup = thisMatchupConsensus.get(i);
            awayTeam = thisMatchup.select(".covers-CoversConsensusDetailsTable-sideHeadLeft").get(0).text();
            awayTeam = awayTeam.replaceAll("[^\\sa-zA-Z]", "").trim();
            homeTeam = thisMatchup.select(".covers-CoversConsensusDetailsTable-sideHeadRight").get(0).text();
            homeTeam = homeTeam.replaceAll("[^\\sa-zA-Z]", "").trim();
            Elements rightConsensus = thisMatchup.select(".covers-CoversConsensusDetailsTable-finalWagersright");
            Elements leftConsensus = thisMatchup.select(".covers-CoversConsensusDetailsTable-finalWagersleft");
            away = leftConsensus.get(0).text();
            over = leftConsensus.get(1).text();
            under = rightConsensus.get(1).text();
            home = rightConsensus.get(0).text();
            out.println("away team => " + awayTeam);
            out.println("home team => " + homeTeam);
            out.println("away = > " + away);
            out.println("over = > " + over);
            out.println("under = > " + under);
            out.println("home = > " + home);
        }
//        byte[] rgb = new byte[]{(byte) 255, (byte) 0, (byte) 0};
//        CellStyle myStyle = sportDataWorkBook.createCellStyle();
//        Font myFont = sportDataWorkBook.createFont();
//        myFont.setBold(true);
//        myStyle.setFont(myFont);
//        XSSFFont xssfFont = (XSSFFont) myFont;
//        xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
//        sportDataSheet.getRow(0).getCell(0).setCellStyle(myStyle);
//        sportDataSheet.getRow(0).getCell(0).setCellValue("Updated " + new Date().toString());
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
//        //JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\n" + "Week Date " + matchupsCalendarDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + getOver() + "\nUnder " + getUnder() + "\nHome " + getHome() + "\nAway " + getAway(), "Sharp Markets version " + version, JOptionPane.INFORMATION_MESSAGE);
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
    {return homeTeam; }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public void setThisGameDate(String thisGameDate)
    {
        this.thisGameDate = thisGameDate;
    }
    public void setSportDataWorkBook(Workbook sportDataWorkBook)
    {
        this.sportDataWorkBook = sportDataWorkBook;
    }
    public void setWebsiteReader(WebSiteReader websiteReader)
    {
        this.websiteReader = websiteReader;
    }
}
