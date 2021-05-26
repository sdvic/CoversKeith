package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210525
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
public class DataCollector
{
    private Elements nflRandomElements;
    private String seasonOptions;
    private String seasonDate;
    private String matchUpIDvalue;
    private InfoPrinter infoPrinter;
    private String[] allSeasonDates;
    private String[] values;
    private HashMap<String, String> thisSeasonWeeks;
    private HashMap<String, String> allNFLseasons;
    private Element thisWeekElements;
    private String homeTeam;
    private String awayTeam;
    public DataCollector(InfoPrinter infoPrinter)
    {
        this.infoPrinter = infoPrinter;
    }
    public void collectAllSeasonDates(Elements nflRandomElements)
    {
        allNFLseasons = new HashMap<String, String>();
        Elements cmg_season_dropdown = nflRandomElements.select("#cmg_season_dropdown");
        Elements options = cmg_season_dropdown.select("Option");
        values = new String[options.size()];
        for (Element e : options)
        {
            allNFLseasons.put(e.text(), e.val());
            System.out.println("*" + e.text() + " *" + e.val());
        }
    }
    public void collectThisSeasonWeeks(Elements nflRandomElements)
    {
        HashMap<String, String> matchups = new HashMap<String, String>();
        Elements cmg_week_filter_dropdown = nflRandomElements.select("#cmg_week_filter_dropdown");
        Elements options = cmg_week_filter_dropdown.select("Option");
        for (Element e : options)
        {
            matchups.put(e.text(), e.val());
            System.out.println("=" + e.text() + " =" + e.val());
        }
    }
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        HashMap<String, String> matchups = new HashMap<>();
        Elements matchupIDs = thisWeekElements.select(".cmg_matchup_game_box");
        for (Element e : matchupIDs)//Build week matchup IDs array
        {
            homeTeam = e.attr("data-home-team-fullname-search");
            awayTeam = e.attr("data-away-team-fullname-search");
            matchups.put(homeTeam, awayTeam);
            System.out.println( homeTeam + "   " + awayTeam);
        }
    }
//        HashMap<String, String> matchups = new HashMap<>();
//        Elements matchUpIDs = thisWeekElements.select("#cmg_week_filter_dropdown");
//        for (Element e : matchUpIDs)//Build week matchup IDs array
//        {
//            String matchUpIDvalue = e.select("option").val();
//            String matchupIDtext = e.select("option").text();
//            matchups.put(matchupIDtext, matchUpIDvalue);
//            System.out.println(" thisWeekText => " + matchupIDtext);
//        }
//    }
    public String getThisSeasonWeeks(String thisWeek)
    {
        return thisSeasonWeeks.get(thisWeek);
    }
    public String getHomeTeam(){return homeTeam;}
    public String getAwayTeam() {return awayTeam;}
}
