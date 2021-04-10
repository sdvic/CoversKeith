package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210409
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
    private static String version= "210409";;
    String weekNumberString;
    String dataEventID;
    String thisGameDate;
    private Elements randomGamesElements;
    private String coversNFLmatchupsURL = "https://www.covers.com/sports/nfl/matchups";
    private Document randomGamesDoc;
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private Object[] nflRandomDocumentsAndElements;
    private HashMap<String, String> weekList = new HashMap<>();
    private Document nflRandomDoc;
    private Elements nflRandomElements;
    private String gameWeek;
    private String gameDate;
    private Document thisWeekDocument;
    private Elements thisWeekElements;
    SeasonAndEventDataCollector seasonAndEventDataCollector = new SeasonAndEventDataCollector();
    WebSiteReader webSiteReader = new WebSiteReader();
    SportDataReader sportDataReader = new SportDataReader();
    SportDataAggregator sportDataAggregator = new SportDataAggregator();
    SportDataWriter sportDataWriter = new SportDataWriter();
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Hello SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException
    {
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);//Read in SportData.xlsx, the main SharpMarkets database, from user's desktop
        sportDataAggregator.setSportDataWorkBook(sportDataWorkBook);//Send SportData.xlsx to sportDataAggregator() for aggregation with Covers.com website data
        nflRandomDocumentsAndElements = webSiteReader.readCleanWebsite(coversNFLmatchupsURL);//Get all Elements and Document from a random week at https://www.covers.com/sports/nfl/matchups
        seasonAndEventDataCollector.collectSeasonData(nflRandomDocumentsAndElements);//passes season info to WebsiteReader for a random NFL week in order to extract NFL week dates
        for (int i = 0; i < 1; i++)//Substitute 2 for "numberOfWeeksThisSeason" for test only
        {
            String nflWeekCalendarDate = seasonAndEventDataCollector.getSeasonWeekDate(i);
            webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekCalendarDate);
            seasonAndEventDataCollector.collectDataEventIDs(webSiteReader.readCleanWebsite("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekCalendarDate));//passes nfl game week info in order to collect event ID's
            //sportDataAggregator.aggregateSportsData(thisWeekDocumentsAndElements);
//            for (int j = 1; j < 2; j++)//iterate through 2 games this week for test only
//            {
//                weekNumberString = Integer.toString(j);
//                String weekDate = weekList.get("Week " + j);
//                out.println("WeekDate => " + weekDate);
//                Document matchupWeekDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate).get();
//                Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
//                String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
//                Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekDate).get();
//                Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
//                out.println("Number of Games this week => " + thisWeekGameElements.size());
//                String homeTeam = sportDataAggregator.getHomeTeam();
//
//                String awayTeam = sportDataAggregator.getAwayTeam();
//                //sportDataWriter.setI(j);
//                JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportDataAggregator.getOver() + "\nUnder " + sportDataAggregator.getUnder() + "\nHome " + sportDataAggregator.getHome() + "\nAway " + sportDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);
//            }
            out.print("\n(11)  Proper Finish...hooray!");
        }
    }
}


