package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210601
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
public class DataCollector
{
    private Elements nflRandomElements;
    private String seasonOptions;
    private String seasonDate;
    private String thisMatchupID;
    private InfoPrinter infoPrinter;
    private String[] allSeasonDates;
    private String[] values;
    private Element thisWeekElements;
    private String homeTeam;
    private String awayTeam;
    private WebSiteReader webSiteReader;
    private String matchupID;
    private String awayTeamID;
    private ArrayList<String> gameWeekDates;
    private ArrayList<String> matchupIDs;
    private ArrayList<String> gameWeekNumbers;
    private String thisWeek;
    private ArrayList<String> weekDataEventIDs;
    public void collectAllSeasonDates(Elements nflRandomElements)
    {
        ArrayList<String> seasonDates = new ArrayList<String>();
        ArrayList<String> seasonCodes = new ArrayList<String>();
        Elements cmg_season_dropdown = nflRandomElements.select("#cmg_season_dropdown");
        Elements options = cmg_season_dropdown.select("Option");
        int i = 0;
        for (Element e : options)
        {
            seasonDates.add(e.text());
            seasonCodes.add(e.val());
            System.out.println("seasonDate => " + seasonDates.get(i) + ", seasonCode => " + seasonCodes.get(i));
            i++;
        }
    }
    public void collectThisSeasonWeeks(Elements nflRandomElements)
    {
        gameWeekNumbers = new ArrayList<String>();
        gameWeekDates = new ArrayList<String>();
        Elements cmg_week_filter_dropdown = nflRandomElements.select("#cmg_week_filter_dropdown");
        Elements options = cmg_week_filter_dropdown.select("Option");
        int i = 0;
        for (Element e : options)
        {
            gameWeekNumbers.add(e.text());
            gameWeekDates.add(e.val());
            System.out.println("gameWeekNumber => " + gameWeekNumbers.get(i) + ", gameWeekDate => " + gameWeekDates.get(i));
            i++;
        }
    }
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        ArrayList<String> homeTeams = new ArrayList<String>();
        ArrayList<String> awayTeams = new ArrayList<String>();
        matchupIDs = new ArrayList<>();
        Elements thisWeekMatchupIDs = thisWeekElements.select(".cmg_matchup_game_box");
        int i = 0;
        for (Element e : thisWeekMatchupIDs)//Build week matchup IDs array
        {
            homeTeam = e.attr("data-home-team-fullname-search");
            awayTeam = e.attr("data-away-team-fullname-search");
            matchupID = e.attr("data-event-ID");
            homeTeams.add(homeTeam);
            awayTeams.add(awayTeam);
            matchupIDs.add(matchupID);
            System.out.println("This week (" + thisWeek + "): homeTeam => " + homeTeams.get(i) + "   awayTeam => " + awayTeams.get(i) + "  data-event-ID => " + matchupID);
            i++;
        }
    }
    public void collectConsensusData(Elements thisMatchupConsensus)
    {
        Elements matchUpIDs = thisMatchupConsensus.select("#cmg_week_filter_dropdown");
        ArrayList<String> atsHomes = new ArrayList<String>();
        ArrayList<String> atsAways = new ArrayList<String>();
        ArrayList<String> ouOvers = new ArrayList<String>();
        ArrayList<String> ouUnders = new ArrayList<String>();
        Elements rightConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersright");
        Elements leftConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersleft");
        String ouOver = leftConsensus.select("div").get(1).text();
        String atsHome = rightConsensus.select("div").get(1).text();
        String atsAway = leftConsensus.select("div").get(0).text();
        String ouUnder = rightConsensus.select("div").get(0).text();
        atsAways.add(atsAway);
        atsHomes.add(atsHome);
        ouOvers.add(ouOver);
        ouUnders.add(ouUnder);
        System.out.println("..................... home " + homeTeam + " vs " + awayTeam + " away...................");
        System.out.println("atsAway => " + atsAway);
        System.out.println("ouUnder => " + atsHome);
        System.out.println("ouOver => " + ouOver);
        System.out.println("atsHome => " + ouUnder);
    }
    public void collectWeekEventIDs(Elements weekDocument)
    {
        System.out.println("Collecting gameIDs");
         weekDataEventIDs = new ArrayList<String>();
        Elements did = weekDocument.select(".cmg_follow_link[data-event-id]");
        for (Element e : did)//Build week game data event IDs array
        {
            String gameID = e.attr("data-event-id").toString();
            weekDataEventIDs.add(gameID);
            System.out.println("this week gameID => " + gameID);
        }
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public void setThisMatchupID(String thisMatchupID)
    {
        this.thisMatchupID = thisMatchupID;
    }
    public ArrayList<String> getGameWeekDates()
    {
        return gameWeekDates;
    }
    public ArrayList<String> getMatchupIDs()
    {
        return matchupIDs;
    }
    public ArrayList<String> getGameWeekNumbers()
    {
        return gameWeekNumbers;
    }
    public ArrayList<String> getWeekDataEventIDs()
    {
        return weekDataEventIDs;
    }
}
