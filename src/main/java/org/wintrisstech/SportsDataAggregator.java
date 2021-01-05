package org.wintrisstech;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.Integer.parseInt;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210104
 *******************************************************************/
public class SportsDataAggregator
{
    private Main main;
    private Document gamePage;
    private Workbook sportDataWorkbook;
    public SportsDataAggregator(Document gamePage, Workbook sportDataWorkbook)
    {
        this.gamePage = gamePage;
        this.sportDataWorkbook = sportDataWorkbook;
        System.out.println("(4) Started aggregating NFL sport date");
        String teams = gamePage.getElementsByClass("covers-CoversConsensus-detailsGameDate").text();
        System.out.println("(5) Matchup => " + teams);
        Elements gameDates = gamePage.getElementsByClass("covers-CoversConsensus-consensusTableContainer covers-CoversConsensusDetailsTable");
        System.out.println("(6) Number of games for this week => " + gameDates.size());
        String totalHomePicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").get(0).text();
        String totalAwayPicks = gamePage.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").get(0).text();
        System.out.println("(7) Total Home Picks => for " + teams + " = " + totalHomePicks);
        System.out.println("(8) Total Away Picks => for " + teams + " = " + totalAwayPicks);
    }
}
