package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210409
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.select.Elements;

import static java.lang.System.out;
public class SeasonAndEventDataCollector
{
    private Elements nflRandomElements;
    private Elements optionElements;
    private int numberOfWeeksThisSeason;
    private String gameWeek;
    private String gameDate;
    private String[] seasonGameDates;
    private String[] dataEventIDs;
    public void collectSeasonData(Object[] nflRandomDocumentsAndElements)
    {
        out.println("+++++++++++++++++++++++++++++++++ collecting season data");
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        optionElements = nflRandomElements.select("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonGameDates = new String[numberOfWeeksThisSeason];
        out.println("Number of Weeks this season => " + numberOfWeeksThisSeason);
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            gameDate = optionElements.select("option:contains(Week )").get(i).val();//Game date
            seasonGameDates[i] = gameDate;
            out.println(i + " " +  seasonGameDates[i]);
        }
    }
    public void collectDataEventIDs(Object[] nflRandomDocumentsAndElements)
    {
        out.println("-------------------- collecting data event ids");
        nflRandomElements = (Elements)nflRandomDocumentsAndElements[1];
        Elements cmgFollowLink = nflRandomElements.select(".cmg_follow_link");
        int numberOfGamesThisWeek = cmgFollowLink.size();
        dataEventIDs = new String[numberOfGamesThisWeek];
        for (int i = 0; i < numberOfGamesThisWeek; i++)
        {
            dataEventIDs[i] = cmgFollowLink.get(i).attr("data-event-id");
            out.println(i + " " + dataEventIDs[i]);
        }
//        Elements ee = e.select("data-event-id");
//        out.println("ee(size) = " + ee.size());
//        for (int i = 0; i < ee.size(); i++)
//        {
//            out.println("[" + i + "]" + ee.get(i));
//        }
//        deids = nflRandomElements.select(".cmg_game_data cmg_matchup_game_box");
//        out.println("deids size is " + deids.size());
//        out.println(deids);
//        for (int i = 1; i < deids.size(); i++)
//        {
//            dataEventIDs[i] = deids.attr("data-event-id");
//            out.println("[" + i + "] "  + dataEventIDs[i]);
//        }
//        deids = nflRandomElements.select(".cmg_follow_link");
//        dataEventIDs = new String[deids.size()];
//        for (int i = 0; i < deids.size(); i++)
//        {
//            dataEventIDs[i] = deids.get(i).attr("data-event-id");
//            out.println("**************************Game(SeasonDataCollector) [" + i + "] "  + dataEventIDs[i]);
//        }
    }

    public String getSeasonWeekDate(int seasonWeekNumber)
    {
        return seasonGameDates[seasonWeekNumber];
    }
}
