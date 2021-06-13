package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210612A
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
    private static String version = "210612";
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
    private String thisWeek;
    private Elements thisMatchupConsensusElements;
    private int globalMatchupIndex;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        Main main = new Main();
        main.getStarted();
    }
    private void getStarted() throws IOException
    {
        ArrayList<String> thisSeasonDates = new ArrayList<>();
        fill2019SeasonDates(thisSeasonDates);
        for (String thisWeek : thisSeasonDates)
        {
            this.thisWeek = thisWeek;
            getGoing(thisWeek);
        }
        System.out.println("Proper Finish...HOORAY!");
    }

    private void getGoing(String thisWeek) throws IOException
    {
        String thisSeason = "2019";
        System.out.println("************************************** NEW WEEK => " + thisWeek + ", NFL SEASON => " + thisSeason + " ***************************************");
        nflHistoryElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisSeason);//Has all NFL season beginning date history and this season year info from https://www.covers.com/sports/nfl/matchups?selectedDate=thisSeasonYear
        dataCollector.collectAllSeasonDates(nflHistoryElements);//Builds a String array of all past and current NFL season year dates available from Covers.com
        thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisWeek);//Get all of this week's games info
        dataCollector.collectThisSeasonWeeks(nflHistoryElements);
        dataCollector.collectThisWeekMatchups(thisWeekElements);
        sportDataWorkbook = sportDataReader.readSportData();
        for (String s : dataCollector.getThisWeekMatchupIDs())
        {
            String thisMatchupID = dataCollector.getThisWeekMatchupIDs().get(globalMatchupIndex);//Get this matchup ID...used as key for all data retrieval
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
            sportDataWriter.writeSportData(sportDataWorkbook);
            globalMatchupIndex++;
        }
        sportDataWriter.writeSportData(sportDataWorkbook);
    }
    private void fill2019SeasonDates(ArrayList<String> thisSeasonDates)
    {
        thisSeasonDates.add("2019-09-05");
        thisSeasonDates.add("2019-09-12");
        thisSeasonDates.add("2019-09-19");
        thisSeasonDates.add("2019-09-26");
        thisSeasonDates.add("2019-10-03");
        thisSeasonDates.add("2019-10-10");
        thisSeasonDates.add("2019-10-17");
        thisSeasonDates.add("2019-10-24");
        thisSeasonDates.add("2019-10-31");
        thisSeasonDates.add("2019-11-07");
        thisSeasonDates.add("2019-11-14");
        thisSeasonDates.add("2019-11-21");
        thisSeasonDates.add("2019-11-28");
        thisSeasonDates.add("2019-12-05");
        thisSeasonDates.add("2019-12-12");
        thisSeasonDates.add("2019-12-21");
        thisSeasonDates.add("2019-12-29");
        thisSeasonDates.add("2020-01-04");
        thisSeasonDates.add("2020-01-11");
        thisSeasonDates.add("2020-01-19");
        thisSeasonDates.add("2020-01-26");
        thisSeasonDates.add("2020-02-02");
    }
}
