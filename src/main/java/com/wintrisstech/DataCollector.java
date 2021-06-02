package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210602
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
    private String awayTeamScore;
    private String homeTeamScore;
    private WebSiteReader webSiteReader;
    private String gameDate;
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();;
    private ArrayList<String> thisWeekMatchupIDs = new ArrayList<String>();;
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();;
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();;
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();;
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private String thisWeek;
    private String awayTeamID;
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        Elements thisWeekMatchupIDs = thisWeekElements.select(".cmg_matchup_game_box");
        int i = 0;
        for (Element e : thisWeekMatchupIDs)//Build week matchup IDs array
        {
            awayTeam = e.attr("data-away-team-fullname-search");
            homeTeam = e.attr("data-home-team-fullname-search");
            thisMatchupID = e.attr("data-event-id");
            gameDate = e.attr("data-game-date");
            homeTeamScore = e.attr("data-home-score");
            awayTeamScore = e.attr("data-away-score");
            thisWeek = e.attr("data-competition-type");
            thisWeekGameDates.add(gameDate);
            thisWeekHomeTeams.add(homeTeam);
            thisWeekAwayTeams.add(awayTeam);
            thisWeekHomeTeamScores.add(homeTeamScore);
            thisWeekAwayTeamScores.add((awayTeamScore));
            thisGameWeekNumbers.add(thisWeek);
            //thisWeekMatchupIDs.add(thisMatchupID);
            System.out.println("........................... ");
            System.out.println("homeTeam => " + homeTeam);
            System.out.println("awayTeam => " + awayTeam);
            System.out.println("gameDate => " + gameDate);
            System.out.println("thisMatchupID => " + thisMatchupID);
            System.out.println("homeTeamScore => " + homeTeamScore);
            System.out.println("awayTeamScore => " + awayTeamScore);
            System.out.println("thisWeek => " + thisWeek);
            i++;
        }
    }
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
        thisGameWeekNumbers = new ArrayList<String>();
        thisWeekGameDates = new ArrayList<String>();
        Elements cmg_week_filter_dropdown = nflRandomElements.select("#cmg_week_filter_dropdown");
        Elements options = cmg_week_filter_dropdown.select("Option");
        int i = 0;
        for (Element e : options)
        {
            thisGameWeekNumbers.add(e.text());
            thisWeekGameDates.add(e.val());
            System.out.println("gameWeekNumber => " + thisGameWeekNumbers.get(i) + ", gameWeekDate => " + thisWeekGameDates.get(i));
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
    public ArrayList<String> getThisWeekGameDates()
    {
        return thisWeekGameDates;
    }
    public ArrayList<String> getThisWeekMatchupIDs()
    {
        return thisWeekMatchupIDs;
    }
    public ArrayList<String> getThisGameWeekNumbers()
    {
        return thisGameWeekNumbers;
    }
}
