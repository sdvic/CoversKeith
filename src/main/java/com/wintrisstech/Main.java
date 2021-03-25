package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 21020324
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;

import static java.lang.System.out;
public class Main
{
    private String version = "210324";
    String weekNumberString;
    private int j;//game counter
    String dataEventID;
    String thisGameDate;
    private XSSFSheet sportDataSheet;
    private Elements randomGamesElements;
    private String dirtyURL = "https://www.covers.com/sports/nfl/matchups?selectedDate=2020-09-10";
    private Document randomGamesDoc;
    private XSSFWorkbook sportDataWorkBook;
    String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    public static void main(String[] args) throws IOException, ParseException
    {
        out.println("(1) Hello SharpMarkets, version 210318, Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        //WebSiteReader webSiteReader = new WebSiteReader();//Read Covers.com...any week, to get game week dates
        //randomGamesDoc = webSiteReader.readCleanWebsite(dirtyURL);//Random nfl week
        //randomGamesElements = randomGamesDoc.getAllElements();
        SportDataReader sportDataReader = new SportDataReader();
        sportDataSheet = sportDataReader.readSportData(deskTopPath);
        //SportDataAggregator sportDataAggregator = new SportDataAggregator();
        //sportDataAggregator.aggregateSportsData(sportDataSheet, randomGamesElements);
//        sportDataAggregator.setVerson(version);
        for (int i = 1; i < 3; i++)//week number 1 based
        {
//            //String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "SharpMarkets ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
//            weekNumberString = Integer.toString(i);
//            Elements matchupWeekDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().select(".covers-MatchupFilters-footballDate, value");
//            Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
//            String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
//            Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekDate).get();
//            Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
//            //coversSheet = sportDataReader.getSportDataSheet();
//            sportDataAggregator.aggregateSportsData(thisWeekGameElements, coversSheet, weekNumberString, nflWeekDate);
//            String homeTeam = sportDataAggregator.getHomeTeam();
//            String awayTeam = sportDataAggregator.getAwayTeam();
//            //sportDataWriter = new SportDataWriter();
//            //sportDataWriter.setI(i);
//            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportDataAggregator.getOver() + "\nUnder " + sportDataAggregator.getUnder() + "\nHome " + sportDataAggregator.getHome() + "\nAway " + sportDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);
        }
        out.print("\n(11)  Proper Finish...hooray!");
    }
}


