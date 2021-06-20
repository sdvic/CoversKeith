package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210613A
 * * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
public class Main extends JComponent
{
    private static String version = "210613A";
    private String nflRandomWeekURL = "https://www.covers.com/sports/nfl/matchups";
    private XSSFWorkbook sportDataWorkbook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private HashMap<String, String> weekList = new HashMap<>();
    private InfoPrinter infoPrinter = new InfoPrinter();
    public DataCollector dataCollector = new DataCollector();
    public WebSiteReader webSiteReader = new WebSiteReader();
    public SportDataReader sportDataReader = new SportDataReader();
    public Aggregator aggregator = new Aggregator();
    public SportDataWriter sportDataWriter = new SportDataWriter();
    private Elements thisWeekElements;
    private Elements nflHistoryElements;
    private Elements thisMatchupConsensusElements;
    private int globalMatchupIndex = 3;
    private String thisSeason = "2020";
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        Main main = new Main();
        main.initialize();//Get out of static context
    }
    private void initialize() throws IOException
    {
        ArrayList<String> thisSeasonDates = new ArrayList<>();
        fill2019SeasonDates(thisSeasonDates);//Puts all week dates into thisSeasonDates
        nflHistoryElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisSeason);//Gets all NFL season beginning date history and this season year info.
        dataCollector.collectAllSeasonDates(nflHistoryElements);//Builds a String array of all past and current NFL season year dates available from Covers.com
        for (String thisWeekDate : thisSeasonDates)//Process all matchups in this season
        {
            System.out.println("************************************** NEW NFL WEEK => " + thisWeekDate + ", NFL SEASON => " + thisSeason + " ***************************************");
            processAllWeeks(thisWeekDate);
        }
        System.out.println("Proper Finish...HOORAY!");
    }
    private void processAllWeeks(String thisWeek) throws IOException
    {
        thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisWeek);//Get all of this week's games info
        dataCollector.collectThisSeasonWeeks(nflHistoryElements);
        dataCollector.collectThisWeekMatchups(thisWeekElements);
        sportDataWorkbook = sportDataReader.readSportData();
        for (String s : dataCollector.getThisWeekMatchupIDs())
        {
            String thisMatchupID = s;
            processAllMatchups(thisMatchupID);
        }
        sportDataWriter.writeSportData(sportDataWorkbook);
    }
    private void processAllMatchups(String thisMatchupID) throws IOException
    {
        thisMatchupConsensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
        dataCollector.collectConsensusData(thisMatchupConsensusElements, thisMatchupID);
        aggregator.setThisWeekAwayTeamsMap(dataCollector.getThisWeekAwayTeamsMap());
        aggregator.setThisWeekHomeTeamsMap(dataCollector.getThisWeekHomeTeamsMap());
        aggregator.setThisWeekGameDatesMap(dataCollector.getThisWeekGameDatesMap());
        aggregator.setAtsHomesMap(dataCollector.getAtsHomesMap());
        aggregator.setAtsAwaysMap(dataCollector.getAtsAwaysMap());
        aggregator.setOuOversMap(dataCollector.getOuOversMap());
        aggregator.setOuUndersMap(dataCollector.getOuUndersMap());
        aggregator.buildSportDataUpdate(sportDataWorkbook, thisMatchupID, globalMatchupIndex);
        globalMatchupIndex++;
//        if (globalMatchupIndex > 17)
//        {
//            sportDataWriter.writeSportData(sportDataWorkbook);//temporary limit...
//            System.out.println("GlobalMatchupIndex > " + globalMatchupIndex + ", writing to SportData.xlsx amd System.exit(0)");
//            System.exit(0);
//        }
    }
    private void fill2019SeasonDates(ArrayList<String> thisSeasonDates)
    {




















        thisSeasonDates.add("2020-09-10");
        thisSeasonDates.add("2020-09-17");
        thisSeasonDates.add("2020-09-24");
        thisSeasonDates.add("2020-10-01");
        thisSeasonDates.add("2020-10-08");
        thisSeasonDates.add("2020-10-18");
        thisSeasonDates.add("2020-10-22");
        thisSeasonDates.add("2020-10-29");
        thisSeasonDates.add("2020-11-05");
        thisSeasonDates.add("2020-11-12");
        thisSeasonDates.add("2020-11-19");
        thisSeasonDates.add("2020-11-26");
        thisSeasonDates.add("2020-12-06");
        thisSeasonDates.add("2020-12-10");
        thisSeasonDates.add("2020-12-17");
        thisSeasonDates.add("2020-12-25");
        thisSeasonDates.add("2021-01-03");
        thisSeasonDates.add("2021-01-09");
        thisSeasonDates.add("2021-01-16");
        thisSeasonDates.add("2021-01-24");
        thisSeasonDates.add("2021-02-07");
    }
}
