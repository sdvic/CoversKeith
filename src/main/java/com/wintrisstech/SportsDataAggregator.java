package com.wintrisstech;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102018
 *******************************************************************/
public class SportsDataAggregator
{
    private String under;
    private String home;
    private String away;
    private String over;
    private String dataEventID;
    private String dataLinkID;
    private String homeTeam;
    private String awayTeam;
    public SportsDataAggregator(Document week) throws IOException
    {
        System.out.println("(4) Start aggregating Covers info");
        Elements e = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
        dataEventID = e.attr("data-event-id");//two team event number on particular date
        dataLinkID = e.attr("data-link").replaceAll("/sport/football/nfl/matchup/", "");
        homeTeam = e.attr("data-home-team-fullname-search");
        awayTeam = e.attr("data-away-team-fullname-search");
        System.out.println("home-team => " + homeTeam);
        System.out.println("away-team => " + awayTeam);
        Document silver = connect("https://contests.covers.com/Consensus/MatchupConsensusDetails?externalId=%2fsport%2ffootball%2fcompetition%3a" + dataEventID).get();
        System.out.println("dataEventID => " + dataEventID);
        System.out.println("dataLinkID => " + dataLinkID);
        Elements rightConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright");
        Elements leftConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft");
        away = leftConsensus.get(0).text();
        over = leftConsensus.get(1).text();
        under = rightConsensus.get(1).text();
        home = rightConsensus.get(0).text();
        System.out.println("(5) Away  (BJ62) => " + away);
        System.out.println("(5) Home (BH60) => " + home);
        System.out.println("(6) Over (BM65) => " + over);
        System.out.println("(7) Under (BO67) => " + under);
    }
    public String getUnder()
    {
        return under;
    }
    public String getOver()
    {
        return over;
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
    public String getDataLinkID()
    {
        return dataLinkID;
    }
    public String getDataEventID()
    {
        return dataEventID;
    }
    public String getAway()
    {
        return away;
    }
    public String getHome()
    {
        return home;
    }
}
