package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210426
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
public class Main
{
    private static String version = "210424";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private Object[] nflRandomDocumentsAndElements;
    private HashMap<String, String> weekList = new HashMap<>();
    public DataCollector dataCollector = new DataCollector();
    WebSiteReader webSiteReader = new WebSiteReader();
    SportDataReader sportDataReader = new SportDataReader();
    SportDataAggregator sportDataAggregator = new SportDataAggregator();
    SportDataWriter sportDataWriter = new SportDataWriter();
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException
    {
        nflRandomDocumentsAndElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=");//Get all Elements and Document from a random week at https://www.covers.com/sports/nfl/matchups
        dataCollector.collectSeasonDates(nflRandomDocumentsAndElements);
        nflDataPrinter();
        System.out.println("(2) Read sportDataWorkbook");
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        System.out.println("(3) Send sportDataWorkbook to aggregator()");
        sportDataAggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        System.out.println("Iterating through NFL season weeks");
        for (int i = 0; i < 2; i++)//Working all NFL season weeks
        {
            Document weekDoc = Jsoup.connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + dataCollector.getSeasonDates()[i]).get();
            Elements weekElements = weekDoc.getAllElements();
            dataCollector.collectWeekDates(weekElements);
            System.out.println("(6) Get this week data so that we can find matchup dates with this week, which is => " + dataCollector.getWeekDates()[i]);
            System.out.println("Working on NFL season week # " + (i+1) + " which starts on " + dataCollector.getSeasonDates()[i]);
//                Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
//                String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
            Elements thisWeekGameElements = weekElements.select("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
            System.out.println("Number of Games this week => " + thisWeekGameElements.size());
            weekDataPrinter();
            String homeTeam = sportDataAggregator.getHomeTeam();
            String awayTeam = sportDataAggregator.getAwayTeam();
            String thisNFLweekCalendarDate = dataCollector.getSeasonDates()[i];
            Object[] thisNFLweekDocAndElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisNFLweekCalendarDate);//"www.covers.com/sports/nfl/matchups?selectedDate=" + thisNFLweekCalendarDate);
            //Elements nflWeekElements = nflWeekDoc.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "nflWeekDoc"
            weekElements = (Elements) thisNFLweekDocAndElements[1];
            Elements e = weekElements.select("[cmg_game_data cmg_matchup_game_box]");//this is good...all games in "nflWeekDoc"
//                //sportDataWriter.setI(j);
//                JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportDataAggregator.getOver() + "\nUnder " + sportDataAggregator.getUnder() + "\nHome " + sportDataAggregator.getHome() + "\nAway " + sportDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.print("(11)  Proper Finish...hooray!");
    }
    private void weekDataPrinter()
    {
        //=========================================================================
//        for (String s : dataCollector.getWeekDates())//Collecting  week calendar dates and data event IDs
//        {
//            System.out.print("Week " + (weekIndex++) + "      ");
//        }
//        System.out.println();
//        for (String s : dataCollector.getWeekEventIDs())
//        {
//            System.out.print(s + "       ");
//        }
//        System.out.println();
//        for (String s : dataCollector.getWeekDates())
//        {
//            System.out.print(s + "  ");
//        }
//        System.out.println();
        //=============================================================================
    }
    private void nflDataPrinter()
    {
        int weekIndex = 0;
        for (String s : dataCollector.getSeasonDates())//Print out NFL season week numbers
        {
            System.out.print("Week " + (1 + weekIndex++) + "        ");
        }
        System.out.println();
        weekIndex = 0;
        for (String s : dataCollector.getSeasonDates())//Print out NFL season dates
        {
            System.out.print(dataCollector.getSeasonDates()[weekIndex++] + "    ");
        }
        System.out.println();
    }
}




