package org.wintrisstech;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210128
 *******************************************************************/
public class SportsDataAggregator
{
    private String totalHomePicks;
    private String totalAwayPicks;
    private String dataEventID;
    private String dataLinkID;
    public SportsDataAggregator(Document week) throws IOException
    {
        System.out.println("(4) Start aggregating Covers info");
        totalHomePicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersleft").text();
        totalAwayPicks = week.getElementsByClass("covers-CoversConsensusDetailsTable-finalWagersright").text();
        Elements e = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
        dataEventID = e.attr("data-event-id");//two team event number on particular date
        dataLinkID = e.attr("data-link").replaceAll("/sport/football/nfl/matchup/", "");
        System.out.println("dataEventID => " + dataEventID);
        System.out.println("dataLinkID => " + dataLinkID);
        System.out.println("(5) Total Home Picks => " + totalHomePicks);
        System.out.println("(6) Total Away Picks => " + totalAwayPicks);
    }
    public String getTotalHomePicks()
    {
        return totalHomePicks;
    }
    public String getTotalAwayPicks()
    {
        return totalAwayPicks;
    }
}
