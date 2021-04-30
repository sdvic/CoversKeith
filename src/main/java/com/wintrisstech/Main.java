package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210430
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
public class Main
{
    private static String version = "210430";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private Object[] nflRandomDocumentsAndElements;
    private Object[] thisWeekDocsAndElements;
    private HashMap<String, String> weekList = new HashMap<>();
    private InfoPrinter infoPrinter = new InfoPrinter();
    public DataCollector dataCollector = new DataCollector(infoPrinter);
    WebSiteReader webSiteReader = new WebSiteReader();
    SportDataReader sportDataReader = new SportDataReader();
    SportDataAggregator sportDataAggregator = new SportDataAggregator();
    SportDataWriter sportDataWriter = new SportDataWriter();
    private Elements thisWeekElements;
    private Elements nflRandomElements;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        Main main = new Main();
        main.getGoing();
    }
    private void getGoing() throws IOException
    {
        nflRandomElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=");//Get all season beginning dates https://www.covers.com/sports/nfl/matchups
        dataCollector.collectSeasonDates(nflRandomElements);//Builds array of all NFL season dates
        System.out.println("(2) Read sportDataWorkbook");
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        System.out.println("(3) Send sportDataWorkbook to aggregator()");
        sportDataAggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        for (int nflWeek = 0; nflWeek < 5; nflWeek++)//Iterate through all NFL season weeks
        {
            System.out.println("Reading week #" + (nflWeek + 1));
            thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + dataCollector.getSeasonDates()[nflWeek]);//Get all of this week's NFL games
            dataCollector.collectMatchupIDs(thisWeekElements);
        }
        System.out.print("(11)  Proper Finish...hooray!");
    }
}




