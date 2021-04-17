package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210417
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.select.Elements;
public class SeasonAndEventDataCollector
{
    private Elements nflRandomElements;
    private Elements optionElements;
    private int numberOfWeeksThisSeason;
    private String gameWeek;
    private String gameDate;
    private String[] seasonGameDates;
    private String[] dataEventIDs;
    private String dataEventID;
    public void collectSeasonDates(Object[] nflRandomDocumentsAndElements)
    {
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        optionElements = nflRandomElements.select("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonGameDates = new String[numberOfWeeksThisSeason];
        System.out.println(("Number of Weeks this season => " + numberOfWeeksThisSeason));
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            gameDate = optionElements.select("option:contains(Week )").get(i).val();//Game date
            seasonGameDates[i] = gameDate;
        }
    }
    public void collectDataEventIDs(Object[] nflRandomDocumentsAndElements)
    {
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        Elements cmgFollowLink = nflRandomElements.select(".cmg_follow_link");
        int numberOfGamesThisWeek = cmgFollowLink.size();
        dataEventIDs = new String[numberOfGamesThisWeek];
        for (int i = 0; i < numberOfGamesThisWeek; i++)
        {
            dataEventIDs[i] = cmgFollowLink.get(i).attr("data-event-id");
        }
        System.out.println();
    }
    public String getSeasonWeekDate(int seasonWeekNumber)
    {
        return seasonGameDates[seasonWeekNumber];
    }
    public String[] getSeasonWeekDates()
    {
        return seasonGameDates;
    }
    public String[] getDataEventIDs()
    {
        return dataEventIDs;
    }
}
