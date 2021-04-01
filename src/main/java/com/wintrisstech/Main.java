package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210401
 * Launch with Covers.command
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import static org.jsoup.helper.HttpConnection.connect;
public class Main
{
    private static String version = "210401";
    String weekNumberString;
    private int j;//game counter
    String dataEventID;
    String thisGameDate;
    private XSSFSheet sportDataSheet;
    private Elements randomGamesElements;
    private String coversNFLurl = "https://www.covers.com/sports/nfl/matchups";
    private Document randomGamesDoc;
    private XSSFWorkbook sportDataWorkBook;
    private String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    private Object[] nflRandomDocumentsAndElements;
    private HashMap<String, String> weekList = new HashMap<>();
    private Document nflRandomDoc;
    private Elements nflRandomElements;
    private String gameWeek;
    private String gameDate;
    public static void main(String[] args) throws IOException, ParseException
    {
        System.out.println("(1) Hello SharpMarkets, version " + version + ", Copyright 2021 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        WebSiteReader webSiteReader = new WebSiteReader();
        nflRandomDocumentsAndElements = webSiteReader.readCleanWebsite(coversNFLurl);//nflRandomMatchups Document[0] and nflRandomMatchups Elements[1]
        nflRandomDoc = (Document) nflRandomDocumentsAndElements[0];
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        Elements e = nflRandomDoc.getElementsByTag("option");
        int length = (e.select("option:contains(Week )").size());
        for (int i = 0; i < length; i++)
        {
            gameWeek = e.select("option:contains(Week )").get(i).text();//Game week
            gameDate = e.select("option:contains(Week )").get(i).val();//Game date
            weekList.put(gameWeek, gameDate);
        }
        //weekList.entrySet().forEach(entry -> {out.println(entry.getKey() + " " + entry.getValue());});
        SportDataReader sportDataReader = new SportDataReader();
        sportDataWorkBook = sportDataReader.readSportData(deskTopPath);
        SportDataAggregator sportDataAggregator = new SportDataAggregator();
        sportDataAggregator.setVersion(version);
        for (int i = 1; i < 3; i++)
        {
            sportDataAggregator.aggregateSportsData(nflRandomDocumentsAndElements, sportDataWorkBook, weekList);
//            //String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "SharpMarkets ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
            weekNumberString = Integer.toString(i);
            String weekDate = weekList.get("Week " + i);
            out.println("WeekDate => " + weekDate);
            Document matchupWeekDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + weekDate).get();
            Element weekNumber = matchupWeekDates.select("option[value]").get(parseInt(weekNumberString));
            String nflWeekDate = weekNumber.getElementsByAttribute("value").val();
            Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + nflWeekDate).get();
            Elements thisWeekGameElements = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
            //coversSheet = sportDataReader.getSportDataSheet();
            String homeTeam = sportDataAggregator.getHomeTeam();
            String awayTeam = sportDataAggregator.getAwayTeam();
            //sportDataWriter = new SportDataWriter();
            //sportDataWriter.setI(i);
            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + nflWeekDate + "\n" + "Game Date " + thisGameDate + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportDataAggregator.getOver() + "\nUnder " + sportDataAggregator.getUnder() + "\nHome " + sportDataAggregator.getHome() + "\nAway " + sportDataAggregator.getAway(), "SharpMarkets version " + version, JOptionPane.INFORMATION_MESSAGE);
        }
        out.print("\n(11)  Proper Finish...hooray!");
    }
}


