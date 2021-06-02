package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210602
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
    private static String version = "210602";
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
    private Elements thisSeasonElements;
    private String thisSeason;
    private String thisWeek;
    private String thisWeekNumber;
    private String thisMatchupID;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Starting SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        Main main = new Main();
        main.getGoing();
    }
    private void getGoing() throws IOException
    {
        String thisSeason;// = JOptionPane.showInputDialog("NFL Season (2yyy)?");
        thisSeason = "2020";
        thisWeek = "2020-09-10";
        System.out.println("(1) Reading all NFL past and current NFL season Elements + season date code + this season week number/date");
        nflHistoryElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisSeason);//Has all NFL season beginning date history and this season year info from https://www.covers.com/sports/nfl/matchups?selectedDate=thisSeasonYear
        System.out.println("(2) Collecting all NFL season week dates");
        dataCollector.collectAllSeasonDates(nflHistoryElements);//Builds a String array of all past and current NFL season year dates available from Covers.com
        System.out.println("(3) Reading Elements for this week: " + thisWeek + " from https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisWeek);
        thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + thisWeek);//Get all of this week's games info
        //System.out.println("............................... " + thisWeekElements);
        System.out.println("(4) Collecting nflHistory Elements");
        dataCollector.collectThisSeasonWeeks(nflHistoryElements);
        System.out.println("(5) Collecting this week matchups");
        dataCollector.collectThisWeekMatchups(thisWeekElements);
        System.out.println("(6) Collecting this week eventIDs");
        ArrayList<String> gameWeekNumbers = dataCollector.getThisGameWeekNumbers();
        ArrayList<String> matchupIDs = dataCollector.getThisWeekMatchupIDs();
        System.out.println("(7) Aggregating AwayTeam");
        aggregator.setAwayTeam(dataCollector.getAwayTeam());
        System.out.println("(8) Aggregating HomeTeam");
        aggregator.setHomeTeam(dataCollector.getHomeTeam());
        System.out.println("(9) Collecting consensus data from https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a*thisMatchupID*");
//        for (String s : dataCollector.getThisWeekMatchupIDs())
//        {
//            Elements thisMatchupConsensus = webSiteReader.readCleanWebsite("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + s);
//            dataCollector.collectConsensusData(thisMatchupConsensus);
//        }
        System.out.println("(10) Read sportDataWorkbook");
        sportDataWorkbook = sportDataReader.readSportData();
        System.out.println("Add update sheet to SportData.xlsx");
        System.out.println("(11) Aggregate sportDataWorkbook");
        aggregator.buildSportDataUpdate(sportDataWorkbook);
        System.out.println("(12) Write updatedSportDataWorkbook");
        sportDataWriter.writeSportData(sportDataWorkbook);
        //  thisWeekElements = webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + dataCollector.getAllNFLseasons().get(thisSeason));//Has all NFL weeks for this year
//        System.out.println("(3) Send sportDataWorkbook to aggregator()");
//        System.out.println("Iterating through all of this NFL season weeks");
//        for (Element e : dataCollector.thisWeekElements)//Iterate through all this NFL season weeks
//        {
//            System.out.println("Iterating through all of this NFL week matchups");
//            for (int matchupIndex = 0; matchupIndex < 1; matchupIndex++)//Iterate through consensus for all matchups this NFL week
////            {
//            dataCollector.collectThisNFLWeekMatchupIDs(thisSeasonElements);
////                String thisMatchupID = dataCollector.getMatchUpIDs()[matchupIndex];
////                System.out.println("Reading consensus data for matchup #" + (matchupIndex + 1) + ", matchupID => " + thisMatchupID);
////                Elements thisMatchupConsensus = webSiteReader.readCleanWebsite("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
////            }
//        }
        System.out.print("(11)  Proper Finish...hooray!");

    }
}
