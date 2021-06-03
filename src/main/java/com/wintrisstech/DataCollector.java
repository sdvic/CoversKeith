package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210603A
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();
    private HashMap<String, String> thisWeekGameDatesMap= new HashMap<>();
    private ArrayList<String> thisWeekMatchupIDs = new ArrayList<String>();
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    ArrayList<String> atsHomes = new ArrayList<String>();
    ArrayList<String> atsAways = new ArrayList<String>();
    ArrayList<String> ouOvers = new ArrayList<String>();
    ArrayList<String> ouUnders = new ArrayList<String>();
    private String thisWeek;
    private String awayTeamID;
    private Elements thisWeekMatchupIdElements;
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        thisWeekMatchupIdElements = thisWeekElements.select(".cmg_matchup_game_box");
        for (Element e : thisWeekMatchupIdElements)//Build week matchup IDs array
        {
            awayTeam = e.attr("data-away-team-fullname-search");
            homeTeam = e.attr("data-home-team-fullname-search");
            thisMatchupID = e.attr("data-event-id");
            gameDate = e.attr("data-game-date");
            homeTeamScore = e.attr("data-home-score");
            awayTeamScore = e.attr("data-away-score");
            thisWeek = e.attr("data-competition-type");
            thisWeekGameDates.add(gameDate);
            thisWeekGameDatesMap.put(thisMatchupID, gameDate);
            thisWeekHomeTeams.add(homeTeam);
            thisWeekAwayTeams.add(awayTeam);
            thisWeekHomeTeamsMap.put(thisMatchupID, homeTeam);
            thisWeekAwayTeamsMap.put(thisMatchupID, awayTeam);
            thisWeekHomeTeamScores.add(homeTeamScore);
            thisWeekAwayTeamScores.add((awayTeamScore));
            thisGameWeekNumbers.add(thisWeek);
            thisWeekMatchupIDs.add(thisMatchupID);
            System.out.println("homeTeam => " + thisWeekHomeTeamsMap.get(thisMatchupID));
            System.out.println("awayTeam => " + thisWeekAwayTeamsMap.get(thisMatchupID));
            System.out.println("gameDate => " + thisWeekGameDatesMap.get(thisMatchupID));
            System.out.println("thisMatchupID => " + thisMatchupID);
            System.out.println("homeTeamScore => " + homeTeamScore);
            System.out.println("awayTeamScore => " + awayTeamScore);
            System.out.println("thisWeek => " + thisWeek);
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
            i++;
        }
        System.out.println("Collected + " + options.size() + " season week dates for this NFL year ");
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
            i++;
        }
        System.out.println("Collected " + options.size() + " season weeks.");
    }
    public void collectConsensusData(Elements thisMatchupConsensus, int matchupIndex, String thisMatchupID)
    {
        System.out.println("(9) Collecting consensus data from https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + thisMatchupID);
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
        System.out.println("..................... home " + thisWeekHomeTeamsMap.get(thisWeekMatchupIDs.get(matchupIndex)) + " vs " + thisWeekAwayTeamsMap.get(thisWeekMatchupIDs.get(matchupIndex))+ " away...................");
        System.out.println("atsAway => " + atsAway);
        System.out.println("ouUnder => " + atsHome);
        System.out.println("ouOver => " + ouOver);
        System.out.println("atsHome => " + ouUnder);
        System.out.println("homeTeam =>" + thisWeekHomeTeamsMap.get(thisWeekMatchupIDs.get(matchupIndex)));
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public ArrayList<String> getThisWeekMatchupIDs()
    {
        return thisWeekMatchupIDs;
    }
    public HashMap<String, String> getThisWeekHomeTeamsMap()
    {
        return thisWeekHomeTeamsMap;
    }
    public HashMap<String, String> getThisWeekAwayTeamsMap()
    {
        return thisWeekAwayTeamsMap;
    }
    public HashMap<String, String> getThisWeekGameDatesMap()
    {
        return thisWeekGameDatesMap;
    }
}
