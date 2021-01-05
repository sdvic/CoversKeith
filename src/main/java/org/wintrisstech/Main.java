package org.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.io.IOException;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210104A
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException
    {
        String version = "210104A";
        System.out.println("(1) Hello Covers World Version => " + version);
        System.out.println("Copyright 2021 Dan Farris");
        String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop */
        JOptionPane.showInputDialog("DanPic NFL Analysis Program\nEnter game number\nCopyright 2020 Dan Farris");
        String gameNumber = "80920";
        String nflEvent = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + gameNumber;
        Document gamePage = Jsoup.connect(nflEvent).get();
        SportDataExcelReader sportDataExcelReader = new SportDataExcelReader(deskTopPath);//Reads sports data xlsx file to hash map
        XSSFWorkbook sportDataWorkBook = sportDataExcelReader.getSportDataWorkBook();
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(gamePage, sportDataWorkBook);
        String totalHomePicks = sportsDataAggregator.getTotalHomePicks();
        String totalAwayPicks = sportsDataAggregator.getTotalAwayPicks();
        new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks);
        System.out.println("(11)  Proper Finish...hooray!");
    }
}


