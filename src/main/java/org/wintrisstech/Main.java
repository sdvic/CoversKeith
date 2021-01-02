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
    private static SportsDataAdder sportsDataAdder;
    private XSSFWorkbook sportDataWorkBook;
    private String totalHomePicks;
    private String totalAwayPicks;
    public static void main(String[] args) throws IOException
    {
        String version = "210102";
        System.out.println("(1) Hello Covers World Version => " + version);
        System.out.println("Copyright 2021 Dan Farris");
        main = new Main();
        SportsDataAdder sportsDataAdder;
        main.getGoing();
    }
    private void getGoing() throws IOException
    {
        String gameNumber = JOptionPane.showInputDialog("DanPic NFL Analysis Program\nEnter game number\nCopyright 2020 Dan Farris");
        gameNumber = "80920";
        SportsDataReader sportsDataReader = new SportsDataReader();//Reads sports data xlsx file
        sportDataWorkBook = sportsDataReader.getSportDataWorkBook();
        SportsDataWriter sportsDataWriter = new SportsDataWriter();

        String event = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + gameNumber;
        Document gamePage = Jsoup.connect(event).get();
        String gameNo = gameNumber;
        String teams = gamePage.getElementsByClass("covers-CoversConsensus-detailsGameDate").text();
        System.out.println("(6) Matchup => " + teams);
        System.out.println("(7) Game number => " + gameNo);
        Elements gameDates = gamePage.getElementsByClass("covers-CoversConsensus-consensusTableContainer covers-CoversConsensusDetailsTable");
        System.out.println("(8) Number of games for this week => " + gameDates.size());
        Element home = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").get(0);
        totalHomePicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").get(0).text();
        totalAwayPicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").get(0).text();
        System.out.print("(9) Total Home Picks => " + getTotalHomePicks() + " for " + teams);
        System.out.println(" = " + getTotalHomePicks());
        sportsDataWriter.setTotalHomePicks(parseInt(totalHomePicks));
        System.out.print("(10) Total Away Picks => " + getTotalAwayPicks() + " for " + teams);
        System.out.println(" = " + getTotalAwayPicks());
        sportsDataWriter.setTotalHomePicks(parseInt(totalHomePicks));
        sportsDataWriter.setTotalAwayPicks(parseInt(totalAwayPicks));
        sportsDataWriter.setCoversUpdatedWorkbook(sportDataWorkBook);
        sportsDataWriter.writeSportsUpdatedWorkbook();
        System.out.println("(11)  Proper Finish...hooray!");
        Document nflMatchUps = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get();
        Elements elements = nflMatchUps.getElementsContainingText("Week ");
    }
    public void setSportDataWorkBook(XSSFWorkbook sportDataWorkBook)
    {
        this.sportDataWorkBook = sportDataWorkBook;
    }
    public String getTotalHomePicks()
    {
        return totalHomePicks;
    }
    public String getTotalAwayPicks()
    {
        return totalAwayPicks;
    }
}


