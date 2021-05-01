package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 2100501
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
    private String matchUpID;
    private InfoPrinter infoPrinter;
    private String[] matchUpIDsString;
    public DataCollector(InfoPrinter infoPrinter)
    {
        this.infoPrinter = infoPrinter;
    }
    public void collectSeasonDates(Elements nflRandomElements)
    {
        optionElements = nflRandomElements.select("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonDates = new String[numberOfWeeksThisSeason];
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            seasonDate = optionElements.select("option:contains(Week )").get(i).val();//Season date
            seasonDates[i] = seasonDate;
        }
        infoPrinter.printInfo("%-12s", "Week ", seasonDates.length);//Print season week dates
        infoPrinter.printInfo("%-12s", seasonDates);
    }
    public void collectMatchupIDs(Elements thisWeekElements)
    {
        Elements matchUpIDs = thisWeekElements.select(".cmg_follow_link[data-event-id]");
        int numberOfGamesThisWeek = matchUpIDs.size();
        matchUpIDsString = new String[numberOfGamesThisWeek];
        int i = 0;
        System.out.println("..........Number of games this week =>" + numberOfGamesThisWeek);

        for (Element e : matchUpIDs)//Build week matchup IDs array
        {
            matchUpID = e.attr("data-event-id");
            matchUpIDsString[i] = matchUpID;
            i++;
        }
        infoPrinter.printInfo("%-11s", "Matchup ", matchUpIDsString.length);
        infoPrinter.printInfo("%-11s", matchUpIDsString);
    }
    public String[] getSeasonDates()
    {
        return seasonDates;
    }
    public String[] getMatchUpIDsString()
    {
        return matchUpIDsString;
    }
}
