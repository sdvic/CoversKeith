package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210628
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
public class DataCollector
{
    private String thisMatchupID;
    private String homeTeam;
    private String awayTeam;
    private String awayTeamScore;
    private String homeTeamScore;
    private String gameDate;
    private ArrayList<String> thisWeekGameDates = new ArrayList<String>();
    private HashMap<String, String> thisWeekGameDatesMap= new HashMap<>();
    private ArrayList<String> thisGameWeekNumbers = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekAwayTeamScores = new ArrayList<String>();
    private ArrayList<String> thisWeekHomeTeams = new ArrayList<String>();
    private HashMap<String, String> thisWeekHomeTeamsMap = new HashMap<>();
    private ArrayList<String> thisWeekAwayTeams = new ArrayList<String>();
    private HashMap<String, String> thisWeekAwayTeamsMap = new HashMap<>();
    private ArrayList<String> atsHomes = new ArrayList<String>();
    private HashMap<String, String> atsHomesMap = new HashMap<>();
    private HashMap<String, String> atsAwaysMap = new HashMap<>();
    private HashMap<String, String> ouUndersMap = new HashMap<>();
    private HashMap<String, String> ouOversMap = new HashMap<>();
    private String thisWeek;
    private Elements thisWeekMatchupIdElements;
    private ArrayList<String> thisWeekMatchupIDs;
    public void collectThisWeekMatchups(Elements thisWeekElements)
    {
        thisWeekMatchupIDs = new ArrayList<>();
        thisWeekMatchupIdElements = thisWeekElements.select(".cmg_matchup_game_box");
        for (Element e : thisWeekMatchupIdElements)//Build week matchup IDs array
        {
            awayTeam = e.attr("data-away-team-fullname-search");
            homeTeam = e.attr("data-home-team-fullname-search");
            thisMatchupID = e.attr("data-event-id");
            String[] gameDateTime = e.attr("data-game-date").split(" ");
            gameDate = gameDateTime[0];
//            String gameTime = gameDateTime[1];
//            Elements eee = thisWeekElements.select("#cmg_week_filter_dropdown");
//            String week = eee.select("[value~=2019-09-05").text();
//            System.out.println(week);//trying to find week #
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
        }
    }
    public void collectConsensusData(Elements thisMatchupConsensus, String thisMatchupID)
    {
        String ouOver = "invalid";
        String ouUnder = "invalid";
        String atsHome = "invalid";
        String atsAway = "invalid";
        Elements rightConsensus = null;
        Elements leftConsensus = null;
        try//Game delayed/cancelled...no consensus data
{
    rightConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersright");//Home/Under
    leftConsensus = thisMatchupConsensus.select(".covers-CoversConsensusDetailsTable-finalWagersleft");//Away/Over
    ouOver = leftConsensus.select("div").get(1).text();
    ouUnder = rightConsensus.select("div").get(1).text();
    atsHome = leftConsensus.select("div").get(0).text();
    atsAway = rightConsensus.select("div").get(0).text();
}
catch (Exception e)
{
    System.out.println("========================> Missing Consensus Data");
    return;
}
        atsHomesMap.put(thisMatchupID, atsAway);
        atsAwaysMap.put(thisMatchupID, atsHome);
        ouOversMap.put(thisMatchupID, ouOver);
        ouUndersMap.put(thisMatchupID, ouUnder);
    }
    public void collectAllSeasonDates(Elements nflRandomElements)
    {
        ArrayList<String> seasonDates = new ArrayList<String>();
        ArrayList<String> seasonCodes = new ArrayList<String>();
        Elements cmg_season_dropdown = nflRandomElements.select("#cmg_season_dropdown");
        Elements options = cmg_season_dropdown.select("Option");
        for (Element e : options)
        {
            seasonDates.add(e.text());
            seasonCodes.add(e.val());
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
            i++;
        }
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
    public HashMap<String, String> getAtsHomesMap()
    {
        return atsHomesMap;
    }
    public HashMap<String, String> getAtsAwaysMap()
    {
        return atsAwaysMap;
    }
    public HashMap<String, String> getOuOversMap()
    {
        return ouOversMap;
    }
    public HashMap<String, String> getOuUndersMap()
    {
        return ouUndersMap;
    }
    public ArrayList<String> getThisWeekMatchupIDs()
    {
        return thisWeekMatchupIDs;
    }
}
