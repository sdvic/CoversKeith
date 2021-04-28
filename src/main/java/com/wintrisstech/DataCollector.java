package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210427
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class DataCollector
{
    private Elements nflRandomElements;
    private Elements optionElements;
    private int numberOfWeeksThisSeason;
    private String gameWeek;
    private String seasonDate;
    private String[] seasonDates;
    private String[] weekDates;
    private String gameID;
    private String[] weekDataEventIDs;
    public void collectSeasonDates(Object[] nflRandomDocumentsAndElements)
    {
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        optionElements = nflRandomElements.select("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonDates = new String[numberOfWeeksThisSeason];
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            seasonDate = optionElements.select("option:contains(Week )").get(i).val();//Season date
            getSeasonDates()[i] = seasonDate;
        }
    }
    public void collectWeekEventIDs(Elements weekDocument)
    {
        Elements did = weekDocument.select(".cmg_follow_link[data-event-id]");
        int numberOfGamesThisWeek = did.size();
        weekDataEventIDs = new String[numberOfGamesThisWeek];
        int i = 0;
        for (Element e : did)//Build week game data event IDs array
        {
            gameID = e.attr("data-event-id").toString();
            weekDataEventIDs[i] = gameID;
            i++;
        }
    }
    public String[] getSeasonDates(){return seasonDates;}
    public String[] getWeekEventIDs() {return weekDataEventIDs;}
}
