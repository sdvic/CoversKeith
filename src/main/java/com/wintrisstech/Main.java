package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 2100508A
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
public class Main
{
    private static String version = "210508A";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private HashMap<String, String> weekList = new HashMap<>();
    private InfoPrinter infoPrinter = new InfoPrinter();
    public DataCollector dataCollector = new DataCollector(infoPrinter);
    public WebSiteReader webSiteReader = new WebSiteReader();
    com.wintrisstech.SportDataReader sportDataReader = new com.wintrisstech.SportDataReader();
    Aggregator aggregator = new Aggregator();
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
        System.out.println("Getting all NFL season week dates");
        nflRandomElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=");//Get all season beginning dates https://www.covers.com/sports/nfl/matchups
        dataCollector.collectSeasonDates(nflRandomElements);//Builds array of all NFL season dates
        System.out.println("(2) Read sportDataWorkbook");
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        System.out.println("(3) Send sportDataWorkbook to aggregator()");
        aggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        for (int nflWeek = 0; nflWeek < 1; nflWeek++)//Iterate through all NFL season weeks
        {
            thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + dataCollector.getSeasonDates()[nflWeek]);//Get all of this week's NFL games
            dataCollector.collectMatchupIDs(thisWeekElements);
            System.out.println("Getting NFL week #" + (nflWeek + 1) + " ,week beginning " + dataCollector.getSeasonDates()[nflWeek] + ", number of matchups " + dataCollector.getMatchUpIDs().length);
            for (int matchupIndex = 0; matchupIndex < 1; matchupIndex++)//Iterate through consensus for all matchups this NFL week
            {
                String thisMatchupID = dataCollector.getMatchUpIDs()[matchupIndex];
                System.out.println("Reading consensus data for matchup #" + (matchupIndex + 1) + ", matchupID => " + thisMatchupID);
                Elements thisMatchupConsensus = webSiteReader.readCleanWebsite("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
                aggregator.aggregateSportsData(thisMatchupConsensus);
            }
        }
        System.out.print("(11)  Proper Finish...hooray!");
    }
}




