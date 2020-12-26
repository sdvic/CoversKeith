package org.wintrisstech;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Vic Wintriss
 * version 201225B   Getting first in picks list
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException
    {
        String eventNumber = "80920";
        String event = "https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + eventNumber;
        System.out.println("1.  Hello World");
        Document doc = Jsoup.connect(event).get();
        System.out.println("2.  Got => " + doc.title());
        Elements gameDates = doc.getElementsByClass("covers-CoversConsensus-consensusTableContainer covers-CoversConsensusDetailsTable");
        System.out.println("3.  gameDates size => " + gameDates.size());
        System.out.println("Picks => " + gameDates.get(1).getElementsByIndexEquals(2).text());
        System.out.println("4.  Proper Finish...hooray!");
    }
    public void sportsReader()
    {
        String version = "DanPic 201206";
        System.out.println("Version " + version + "\nCopyright 2020 Dan Farris\n");
        int targetMonth = Integer.parseInt(JOptionPane.showInputDialog("DanPic NFL Analysis Program \n Copright 2020 Dan Farris"));
        String followOnAnswer = JOptionPane.showInputDialog("Press Return to continue...");
        SportsDataReader sportsDataReader = new SportsDataReader(targetMonth);
    }
}


