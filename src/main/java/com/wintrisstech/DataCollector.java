package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210523
 * Builds data event id array and calendar date array
 *******************************************************************/
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class DataCollector
{
    private Elements nflRandomElements;
    private String seasonOptions;
    private String seasonDate;
    private String matchUpID;
    private InfoPrinter infoPrinter;
    private String[] matchUpIDs;
    private String[] allSeasonDates;
    private String[] values;
    public DataCollector(InfoPrinter infoPrinter)
    {
        this.infoPrinter = infoPrinter;
    }
    public void collectThisSeasonDates(Elements nflRandomElements, String thisSeasonYear)
    {
//        seasonOptions = nflRandomElements.select("option");
//        seasonElements = seasonOptions.select("option:contains(Week )").size();
//        seasonDates = new String[seasonElements];
//        for (int i = 0; i < seasonElements; i++)
//        {
//            seasonDate = seasonOptions.select("option:contains(Week )").get(i).val();//Season date
//            seasonDates[i] = seasonDate;
//        }
//        infoPrinter.printInfo("%-12s", "Week ", seasonDates.length);//Print season week dates
//        infoPrinter.printInfo("%-12s", seasonDates);
    }
    public void collectMatchupIDs(Elements thisWeekElements)
    {
        Elements matchUpIDs = thisWeekElements.select(".cmg_follow_link[data-event-id]");
        int numberOfGamesThisWeek = matchUpIDs.size();
        this.matchUpIDs = new String[numberOfGamesThisWeek];
        int i = 0;
        for (Element e : matchUpIDs)//Build week matchup IDs array
        {
            matchUpID = e.attr("data-event-id");
            this.matchUpIDs[i] = matchUpID;
            i++;
        }
        infoPrinter.printInfo("%-11s", "Matchup ", this.matchUpIDs.length);
        infoPrinter.printInfo("%-11s", this.matchUpIDs);
    }
    public void collectAllNFLSeasonDates(Elements nflRandomElements)
    {
        Elements cmg_season_dropdown = nflRandomElements.select("#cmg_season_dropdown");
        Elements options = cmg_season_dropdown.select("Option");
        values = new String[options.size()];
        int i = 0;
        for (Element e : options)
        {
            values[i] = e.val();
            System.out.println("values[" + i + "] => " + e.val());
            i++;
        }
    }
    public String[] getValues()
    {
        return values;
    }
}
