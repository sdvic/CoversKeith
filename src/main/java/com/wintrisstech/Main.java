package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 210603A
 * * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
public class Main extends JComponent
{
    private static String version = "210603A";
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
        System.out.println("(4) Collecting nflHistory Elements");
        dataCollector.collectThisSeasonWeeks(nflHistoryElements);
        System.out.println("(5) Collecting this week matchups");
        dataCollector.collectThisWeekMatchups(thisWeekElements);
        System.out.println("+++++++++++++++++++++++ number of matchups this week is " + dataCollector.getThisWeekMatchupIDs().size());
        System.out.println("(8) Read sportDataWorkbook");
        sportDataWorkbook = sportDataReader.readSportData();
        for (int i = 0; i < 8; i++)
        {
            String thisMatchupID = dataCollector.getThisWeekMatchupIDs().get(i);
            System.out.println("Getting consensus data for matchup " +  thisMatchupID);
            Elements thisMatchupConsensusElements = webSiteReader.readCleanWebsite("https://contests.covers.com/consensus/matchupconsensusdetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
            dataCollector.collectConsensusData(thisMatchupConsensusElements, i, thisMatchupID);
            aggregator.setThisWeekAwayTeamsMap(dataCollector.getThisWeekAwayTeamsMap());
            aggregator.setThisWeekHomeTeamsMap(dataCollector.getThisWeekHomeTeamsMap());
            aggregator.setThisWeekGameDatesMap(dataCollector.getThisWeekGameDatesMap());
            aggregator.buildSportDataUpdate(sportDataWorkbook, thisMatchupID, i);
            sportDataWriter.writeSportData(sportDataWorkbook);
        }
        System.out.println("(6) Aggregating AwayTeam");
        aggregator.setAwayTeam(dataCollector.getAwayTeam());
        System.out.println("(6) Aggregating HomeTeam");
        aggregator.setHomeTeam(dataCollector.getHomeTeam());

        System.out.println("Add update sheet to SportData.xlsx");
        System.out.println("(9) Aggregate sportDataWorkbook");
        System.out.println("(10) Write updatedSportDataWorkbook");
        sportDataWriter.writeSportData(sportDataWorkbook);
        System.out.print("(11)  Proper Finish...hooray!");
    }
}
