package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210427
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
    private static String version = "210427";
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
        System.out.println("Reading " + dataCollector.getSeasonDates().length + " NFL season week dates");
        infoPrinter("%-11s", "Week ", dataCollector.getSeasonDates().length);
        infoPrinter("%-11s", dataCollector.getSeasonDates());
        System.out.println("(2) Read sportDataWorkbook");
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        System.out.println("(3) Send sportDataWorkbook to aggregator()");
        sportDataAggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        for (int i = 0; i < 2; i++)//Iterate through all NFL season weeks
        {
            Document weekDoc = Jsoup.connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + dataCollector.getSeasonDates()[i]).get();
            Elements weekElements = weekDoc.getAllElements();
            dataCollector.collectWeekEventIDs(weekElements);
            System.out.println("Iterating through " + dataCollector.getWeekEventIDs().length + " games this week");
            System.out.println("Working on NFL season week #" + (i + 1) + ", game #" + (i + 1));
            infoPrinter("%-8s", "Game ", dataCollector.getSeasonDates().length);
            infoPrinter("%-8s", dataCollector.getWeekEventIDs());
            String homeTeam = sportDataAggregator.getHomeTeam();
            String awayTeam = sportDataAggregator.getAwayTeam();
            String thisNFLweekCalendarDate = dataCollector.getSeasonDates()[i];
        }
        System.out.print("(11)  Proper Finish...hooray!");
    }
    public void infoPrinter(String format, String[] infoToPrint)
    {
        int weekIndex = 0;
        for (String s : infoToPrint)
        {
            System.out.printf(format, infoToPrint[weekIndex++]);
        }
        System.out.println();
    }
    public void infoPrinter(String format, String textToPrint, int numberOfTimesToPrint)
    {
        for (int i = 0; i < numberOfTimesToPrint; i++)
        {
            System.out.printf(format, textToPrint + (i + 1));
        }
        System.out.println();
    }
}




