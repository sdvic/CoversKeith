package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210304
 *******************************************************************/
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Date;
import static org.jsoup.Jsoup.connect;
public class SportsDataAggregator
{
    private String under;
    private String home;
    private String away;
    private String over;
    private String dataEventID;
    private String homeTeam;
    private String awayTeam;
    private int gameIndex;
    private int gameTotal;
    private int rowOffset = 3;
    private String matchupsDate;
    private XSSFWorkbook sportDataWorkBook;

    public int aggregateSportsData(Elements thisWeekGamesElements, Document thisWeekGamesDoc, XSSFSheet sportDataSheet, String weekNumberString, String matchupsDate) throws IOException
    {
        for (gameIndex = 0; gameIndex < 4 ; gameIndex++)//game counter zero based
        {
            System.out.println("(4) Start aggregating Covers info, game " + (gameIndex + 1));//game number this week counter...int j is zero based
            dataEventID = thisWeekGamesElements.get(gameIndex).attr("data-event-id");//two team event number on particular date...int i is 1 based
            homeTeam = thisWeekGamesElements.get(gameIndex).attr("data-home-team-fullname-search");
            awayTeam = thisWeekGamesElements.get(gameIndex).attr("data-away-team-fullname-search");
            System.out.println("home-team => " + homeTeam);
            System.out.println("away-team => " + awayTeam);
            Document silver = connect("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventID).get();
            Elements rightConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright");
            Elements leftConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft");
            away = leftConsensus.get(0).text();
            over = leftConsensus.get(1).text();
            under = rightConsensus.get(1).text();
            home = rightConsensus.get(0).text();
            System.out.println("(5) Away  (BJ62) => " + away);
            System.out.println("(5) Home (BH60) => " + home);
            System.out.println("(6) Over (BM65) => " + over);
            System.out.println("(7) Under (BO67) => " + under);
            System.out.println("game total " + gameTotal);

            byte[] rgb = new byte[]{(byte)255, (byte)0, (byte)0};
            CellStyle myStyle = sportDataWorkBook.createCellStyle();
            Font myFont = sportDataWorkBook.createFont();
            myFont.setBold(true);
            myStyle.setFont(myFont);
            XSSFFont xssfFont = (XSSFFont)myFont;
            xssfFont.setColor(new XSSFColor(rgb, null));//Load new values into SportData.xlsx sheet
            sportDataSheet.getRow(0).getCell(0).setCellStyle(myStyle);
            sportDataSheet.getRow(0).getCell(0).setCellValue("Updated " + new Date().toString());
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(0).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(0).setCellValue(awayTeam + " @ " + homeTeam);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(1).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(1).setCellValue(matchupsDate);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(2).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(2).setCellValue(matchupsDate.substring(0, 4));//Pick year only out of date
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(3).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(3).setCellValue("week" + weekNumberString);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(59).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(59).setCellValue(home);//BH60
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(61).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(61).setCellValue(away);//BJ62
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(64).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(64).setCellValue(over);//BM65
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(66).setCellStyle(myStyle);
            sportDataSheet.getRow(rowOffset + gameTotal).getCell(66).setCellValue(under);//BO67
            gameTotal++;
        }
        return gameTotal;
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
    public String getDataEventID()
    {
        return dataEventID;
    }
    public String getAway()
    {
        return away;
    }
    public String getHome()
    {
        return home;
    }
    public void setGameTotal(int gameTotal)
    {
        this.gameTotal = gameTotal;
    }
    public void setSportDataWorkBook(XSSFWorkbook sportDataWorkBook)
    {
        this.sportDataWorkBook = sportDataWorkBook;
    }
}
