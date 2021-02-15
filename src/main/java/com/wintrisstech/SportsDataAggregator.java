package com.wintrisstech;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102014
 *******************************************************************/
public class SportsDataAggregator
{
    private String leftOver2OU;
    private String rightHome2ATS;
    private String rightUnder2OU;
    private String leftAway2ATS;
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
        System.out.println("hc[0] left2 ATS Away (BJ62) => " + leftConsensus.get(0).text());
        System.out.println("hc[1] left-over2 (BO65) => " + leftConsensus.get(1).text());
        System.out.println("ac[0] right2 ATS Home (BH60) => " + rightConsensus.get(0).text());
        System.out.println("ac[1] right-under2 (BO67) => " + rightConsensus.get(1).text());
        rightUnder2OU = leftConsensus.get(1).text();
        leftAway2ATS = leftConsensus.get(0).text();
        rightHome2ATS = rightConsensus.get(0).text();
        leftOver2OU = rightConsensus.get(1).text();
        System.out.println("(5) Left Away2 ATS  (BJ62) => " + leftAway2ATS);
        System.out.println("(5) Right Home2 ATS (BH60) => " + rightHome2ATS);
        System.out.println("(6) left-over2 OU (BM65) => " + leftOver2OU);
        System.out.println("(6) right-under2 OU (BO67) => " + rightUnder2OU);
    }
    public String getLeftOver2OU()
    {
        return leftOver2OU;
    }
    public String getLeftAway2ATS()
    {
        return leftAway2ATS;
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
