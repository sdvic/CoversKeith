package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 21020322
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static org.jsoup.Jsoup.connect;
public class Main
{
    private String version = "210322";
    String weekNumberString;
    private int j;//game counter
    String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    String dataEventID;
    String thisGameDate;
    private XSSFSheet coversSheet;
    private Elements randomGamesElements;
    private String dirtyURL = "https://www.covers.com/sports/nfl/matchups?selectedDate=2020-09-10";
    private Document randomGamesDoc;
    private XSSFWorkbook sportsDataWorkBook;
    public static void main(String[] args) throws IOException, ParseException
    {
        out.println("(1) Hello SharpMarkets, version 210318, Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        WebSiteReader webSiteReader = new WebSiteReader();//Read Covers.com...any week, to get game week dates
        randomGamesDoc = webSiteReader.readCleanWebsite(dirtyURL);
        randomGamesElements = randomGamesDoc.getAllElements();
        src.main.java.com.wintrisstech.SportDataReader sportDataReader = new src.main.java.com.wintrisstech.SportDataReader(deskTopPath);//Reads sports data xlsx file to hash map
        sportsDataWorkBook = sportDataReader.getSportDataWorkBook();
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator();
        sportsDataAggregator.setSportDataWorkBook(sportsDataWorkBook);
        sportsDataAggregator.setVerson(version);
        for (int i = 1; i < 3; i++)//week number 1 based
        {
            //String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "SharpMarkets ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
            weekNumberString = Integer.toString(i);
            Elements matchupWeekDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().select(".covers-MatchupFilters-footballDate, value");
            Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
            String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
            Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekDate).get();
            Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
            coversSheet = sportDataReader.getSportDataSheet();
            sportsDataAggregator.aggregateSportsData(thisWeekGameElements, coversSheet, weekNumberString, nflWeekDate);
            String homeTeam = sportsDataAggregator.getHomeTeam();
            String awayTeam = sportsDataAggregator.getAwayTeam();
            src.main.java.com.wintrisstech.SportsDataWriter sportsDataWriter = new src.main.java.com.wintrisstech.SportsDataWriter(deskTopPath, sportDataReader.getSportDataWorkBook(), weekNumberString, sportsDataAggregator.getHomeTeam(), sportsDataAggregator.getAwayTeam(), sportsDataAggregator.getHome(), sportsDataAggregator.getAway(), sportsDataAggregator.getUnder(), sportsDataAggregator.getOver(), i, j);
            sportsDataWriter.setI(i);
            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportsDataAggregator.getOver() + "\nUnder " + sportsDataAggregator.getUnder() + "\nHome " + sportsDataAggregator.getHome() + "\nAway " + sportsDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);
        }
        out.print("\n(11)  Proper Finish...hooray!");
    }
}


