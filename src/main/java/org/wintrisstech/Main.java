package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210118
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        String version = "210118";
        System.out.println("(1) Hello DanPick, version " + version + "\nCopyright 2021 Dan Farris");
        String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop */
        String weekNumber = JOptionPane.showInputDialog("DanPic NFL Analysis Program\nEnter week number\nCopyright 2021 Dan Farris");
        String gameNumber = "80965";//Week 17
        String nflEvent = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + gameNumber;
        Document nflBasePage = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get();
        Document gamePage = Jsoup.connect(nflEvent).get();
        SportDataExcelReader sportDataExcelReader = new SportDataExcelReader(deskTopPath);//Reads sports data xlsx file to hash map
        HashMap sportDataMap = sportDataExcelReader.getSportDataMap();
        XSSFWorkbook sportDataWorkBook = sportDataExcelReader.getSportDataWorkBook();
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(sportDataMap, gamePage, sportDataWorkBook, weekNumber);
        String totalHomePicks = sportsDataAggregator.getTotalHomePicks();
        String totalAwayPicks = sportsDataAggregator.getTotalAwayPicks();
        new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks);
        System.out.println("(11)  Proper Finish...hooray!");
    }
}


