package com.wintrisstech;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102012A
 *******************************************************************/
public class SportsDataAggregator
{
    private String awayOU;
    private String homeATS;
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
        Elements awayConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright");//BT72
        Elements homeConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft");//BR70
        homeATS = homeConsensus.get(0).text();
        awayOU = awayConsensus.get(1).text();
        System.out.println("(5) Away ATS  (BT72) => " + homeATS);//Column BT(72)Away
        System.out.println("(6) Home OU  (BR70) => " + awayOU);//Column BR(70)Home
    }
    public String getAwayOU()
    {
        return awayOU;
    }
    public String getHomeATS()
    {
        return homeATS;
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
}
