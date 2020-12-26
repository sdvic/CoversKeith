package org.wintrisstech;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.io.IOException;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 201226B
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException
    {
        String version = "201226B";
        System.out.println("1.  Hello Covers World Version => " + version );
        System.out.println("Copyright 2020 Dan Farris");
        new Main().getGoing();
    }
    private void getGoing() throws IOException
    {
        String gameNumber = JOptionPane.showInputDialog("DanPic NFL Analysis Program\nEnter game number\nCopyright 2020 Dan Farris");
        gameNumber = "80920";
        new SportsDataReader();
        String event = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + gameNumber;
        Document doc = Jsoup.connect(event).get();
        System.out.println("(4))  Getting data for game number " + gameNumber + ", " + doc.title());
        Elements gameDates = doc.getElementsByClass("covers-CoversConsensus-consensusTableContainer covers-CoversConsensusDetailsTable");
        System.out.println("(5))  Number of games for this week => " + gameDates.size());
        System.out.println("Picks for this game => " + gameDates.get(1).getElementsByIndexEquals(2).text());
        System.out.println("(6))  Proper Finish...hooray!");
    }
}


