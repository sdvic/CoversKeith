package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210416
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import static java.lang.System.out;
public class Main
{
    private static String version = "210409";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private Document randomGamesDoc;
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private Object[] nflRandomDocumentsAndElements;
    private HashMap<String, String> weekList = new HashMap<>();
    public SeasonAndEventDataCollector seasonAndEventDataCollector = new SeasonAndEventDataCollector();
    WebSiteReader webSiteReader = new WebSiteReader();
    SportDataReader sportDataReader = new SportDataReader();
    SportDataAggregator sportDataAggregator = new SportDataAggregator();
    SportDataWriter sportDataWriter = new SportDataWriter();
    private Elements thisWeekGameElements;
    private int weekIndex;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException
    {
        out.println("(1) Read sportDataWorkbook");
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        out.println("(2) Send sportDataWorkbook to aggregator()");
        sportDataAggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        out.println("(3) Get Covers random week data so that we can collect NFL season week calendar dates.");
        nflRandomDocumentsAndElements = webSiteReader.readCleanWebsite(nflRandomWeekURL);//Get all Elements and Document from a random week at https://www.covers.com/sports/nfl/matchups
        out.println("(4) Get NFL season week calendar dates");
        seasonAndEventDataCollector.collectSeasonDates(nflRandomDocumentsAndElements);
        seasonAndEventDataCollector.collectDataEventIDs(nflRandomDocumentsAndElements);
        for (String s : seasonAndEventDataCollector.getDataEventIDs())
        {
            out.print("Week " + (weekIndex++) + "      ");
        }
        out.println();
        for (String s : seasonAndEventDataCollector.getDataEventIDs())
        {
            out.print(s + "       ");
        }
        out.println();
        for (String s : seasonAndEventDataCollector.getSeasonWeekDates())
        {
            out.print(s + "  ");
        }
        out.println();

        out.println("Iterating through NFL season weeks");
        for (int i = 0; i < 2; i++)//Working all NFL season weeks
        {
            //Document matchupWeekDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate).get();
//                Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
//                String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
//                Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekDate).get();
//                Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
//                out.println("Number of Games this week => " + thisWeekGameElements.size());
//                String homeTeam = sportDataAggregator.getHomeTeam();
//
//                String awayTeam = sportDataAggregator.getAwayTeam();
            String thisNFLweekCalendarDate = seasonAndEventDataCollector.getSeasonWeekDate(i);
            out.println("........................Working on NFL week date " + thisNFLweekCalendarDate);
            Object[] thisNFLweekDocumentsAndElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisNFLweekCalendarDate);//"www.covers.com/sports/nfl/matchups?selectedDate=" + thisNFLweekCalendarDate);
            //Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
            thisWeekGameElements = (Elements)thisNFLweekDocumentsAndElements[1];
            Elements e = thisWeekGameElements.select("[cmg_game_data cmg_matchup_game_box]");//this is good...all games in "week"
            out.println("Number of Games this week => " + thisWeekGameElements.size());
            String homeTeam = sportDataAggregator.getHomeTeam();
            String awayTeam = sportDataAggregator.getAwayTeam();
            out.println("home " + homeTeam +"/" + "away " + awayTeam);
//                //sportDataWriter.setI(j);
//                JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportDataAggregator.getOver() + "\nUnder " + sportDataAggregator.getUnder() + "\nHome " + sportDataAggregator.getHome() + "\nAway " + sportDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);

        }
        out.print("(11)  Proper Finish...hooray!");
    }
}




