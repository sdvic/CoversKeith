package org.wintrisstech;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210123
 *******************************************************************/
public class SportsDataAggregator
{
    private String totalHomePicks;
    private String totalAwayPicks;
    public SportsDataAggregator(HashMap sportDataMap, Document gamePage, Workbook sportDataWorkbook, String weekNumberString, String matchupsDate) throws IOException
    {
        System.out.println("(4) Start aggregating week " + weekNumberString + " => " + matchupsDate);
        Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
        totalHomePicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").text();
        totalAwayPicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").text();
        JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\nDate " + matchupsDate +"\nTotal Away Picks => " + totalAwayPicks + "\nTotal Home Picks => " +  totalHomePicks);
        System.out.println("(7) Total Home Picks => " + totalHomePicks);
        System.out.println("(8) Total Away Picks => " + totalAwayPicks);
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
