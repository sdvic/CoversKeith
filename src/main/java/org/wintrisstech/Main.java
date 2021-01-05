package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;

import static java.lang.Integer.parseInt;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210102A
 *******************************************************************/
public class Main
{
    private static Main main;
    private static SportsDataAggregator sportsDataAdder;
    private XSSFWorkbook sportDataWorkBook;
    private String totalHomePicks;
    private String totalAwayPicks;
    public static void main(String[] args) throws IOException
    {
        String version = "210104";
        System.out.println("(1) Hello Covers World Version => " + version);
        System.out.println("Copyright 2021 Dan Farris");
        main = new Main();
        main.getGoing();
    }
    private void getGoing() throws IOException
    {
        String gameNumber = JOptionPane.showInputDialog("DanPic NFL Analysis Program\nEnter game number\nCopyright 2020 Dan Farris");
        gameNumber = "80920";
        String nflEvent = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + gameNumber;
        SportsDataExcelReader sportsDataExcelReader = new SportsDataExcelReader();//Reads sports data xlsx file to hash map
        sportDataWorkBook = sportsDataExcelReader.getSportDataWorkBook();
        Document gamePage = Jsoup.connect(nflEvent).get();
        totalHomePicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").get(0).text();
        totalAwayPicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").get(0).text();
        new SportsDataAggregator(gamePage, sportDataWorkBook);
        new SportsDataWriter(sportDataWorkBook, totalHomePicks, totalAwayPicks);
        System.out.println("(11)  Proper Finish...hooray!");
        Document nflMatchUps = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get();
        Elements elements = nflMatchUps.getElementsContainingText("Week ");
    }
}


