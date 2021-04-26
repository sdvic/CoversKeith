package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210426
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.select.Elements;
public class DataCollector
{
    private Elements nflRandomElements;
    private Elements optionElements;
    private int numberOfWeeksThisSeason;
    private String gameWeek;
    private String seasonDate;
    private String[] seasonDates;
    private int numberOfGamesThisWeek;
    private String[] weekDates;
    private String[] weekEventIDs;
    public void collectSeasonDates(Object[] nflRandomDocumentsAndElements)
    {
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        optionElements = nflRandomElements.select("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonDates = new String[numberOfWeeksThisSeason];
        System.out.println(("Number of Weeks this season => " + numberOfWeeksThisSeason));
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            seasonDate = optionElements.select("option:contains(Week )").get(i).val();//Season date
            getSeasonDates()[i] = seasonDate;
        }
    }
    public void collectWeekDates(Elements weekElements)
    {
        optionElements = weekElements.select("option");
        numberOfGamesThisWeek = optionElements.select("option:contains(Week )").size();
        weekDates = new String[numberOfGamesThisWeek];
        System.out.println(("Number of games this week => " + numberOfWeeksThisSeason));
        for (int i = 0; i < numberOfGamesThisWeek; i++)
        {
            seasonDate = optionElements.select("option:contains(Week )").get(i).val();//Game date
            weekDates[i] = seasonDate;
        }
    }

//    public void collectWeekEventIDs(Elements weekDocument)
//    {
//        Elements did = weekDocument.select(".cmg_follow_link[data-event-id]");//????????????????????????????????????????????
//        int numberOfGamesThisWeek = did.size();
//        System.out.println("Number of games this week => " + numberOfGamesThisWeek);
//        String[] weekDataEventIDs = new String[numberOfGamesThisWeek];
//        int i = 0;
//        for (Element e : did)
//        {
//            String s = e.attr("data-event-id").toString();
//            weekDataEventIDs[i] = s;
//            System.out.print(s + " ");
//            i++;
//        }
//        System.out.println();
//    }
    public String[] getSeasonDates(){return seasonDates;}
    public String[] getWeekDates()
    {
        return weekDates;
    }
    public String[] getWeekEventIDs()
    {
        return weekEventIDs;
    }
}
