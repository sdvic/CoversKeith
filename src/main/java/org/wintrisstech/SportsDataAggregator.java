package org.wintrisstech;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210118
 *******************************************************************/
public class SportsDataAggregator
{
    private String totalHomePicks;
    private String totalAwayPicks;
    public SportsDataAggregator(HashMap sportDataMap, Document gamePage, Workbook sportDataWorkbook, String weekNumber) throws IOException
    {
        Document nflPage = connect("https://www.covers.com/sports/nfl/matchups").get();
        Elements gameDates = nflPage.getElementsByClass("covers-MatchupFilters-footballDate");
        System.out.println("ooooooooooooooooooooo=> Week " + weekNumber + " is: " + gameDates.select("option[value]").get(Integer.parseInt(weekNumber)));//Index selects weeks...index[2] gets week 2
        Connection week1 = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=2020-09-10");
        System.out.println("(4) Started aggregating NFL sport date");
        String teams = gamePage.getElementsByClass("covers-CoversConsensus-detailsGameDate").text();
        System.out.println("(5) Matchup => " + teams);
        totalHomePicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").get(0).text();
        totalAwayPicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").get(0).text();
        System.out.println("(7) Total Home Picks => for " + teams + " = " + totalHomePicks);
        System.out.println("(8) Total Away Picks => for " + teams + " = " + totalAwayPicks);
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
