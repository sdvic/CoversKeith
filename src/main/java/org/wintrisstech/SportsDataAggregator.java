package org.wintrisstech;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210129A
 *******************************************************************/
public class SportsDataAggregator
{
    private String totalHomeOUPicks;
    private String totalHomeATSPicks;
    private String dataEventID;
    private String dataLinkID;
    private String homeTeam;
    private String awayTeam;
    public SportsDataAggregator(Document week) throws IOException
    {
        System.out.println("(4) Start aggregating Covers info");
        totalHomeOUPicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").text();
        totalHomeATSPicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").text();
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
        Elements awayConsensus = silver.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft");
        totalHomeATSPicks = awayConsensus.get(0).text();
        totalHomeOUPicks = awayConsensus.get(1).text();
        System.out.println("(5) Total Away O/U Picks (BT) => " + totalHomeOUPicks);//Column BT?
        System.out.println("(6) Total Home ATS Picks (BR) => " + totalHomeATSPicks);//Column BR
    }
    public String getTotalHomeOUPicks()
    {
        return totalHomeOUPicks;
    }
    public String getTotalHomeATSPicks()
    {
        return totalHomeATSPicks;
    }
    public String getHomeTeam()
    {
        return homeTeam;
    }
    public String getAwayTeam()
    {
        return awayTeam;
    }
}
