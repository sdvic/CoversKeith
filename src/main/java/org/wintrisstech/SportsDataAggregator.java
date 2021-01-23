package org.wintrisstech;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210122
 *******************************************************************/
public class SportsDataAggregator
{
    private String totalHomePicks;
    private String totalAwayPicks;
    public SportsDataAggregator(HashMap sportDataMap, Document gamePage, Workbook sportDataWorkbook, String weekNumberString) throws IOException
    {
        Document nflPage = connect("https://www.covers.com/sports/nfl/matchups").get();
        Elements matchupDates = nflPage.getElementsByClass("covers-MatchupFilters-footballDate");
        Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
        String matchupsDate = dateValue.getElementsByAttribute("value").val();
        System.out.println("Week " + weekNumberString + " => " + matchupsDate);
        System.out.println("(4) Start aggregating");
        Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
        totalHomePicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").text();
        totalAwayPicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").text();
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
