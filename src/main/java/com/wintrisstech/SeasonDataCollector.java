package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 * version 210405
 * Launch with Covers.command
 *******************************************************************/
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import static java.lang.System.out;
public class SeasonDataCollector
{
    private Document nflRandomDoc;
    private Elements nflRandomElements;
    private Elements optionElements;
    private int numberOfWeeksThisSeason;
    private String gameWeek;
    private String gameDate;
    private String[] seasonGameDates;
    public void collectSeasonData(Object[] nflRandomDocumentsAndElements)
    {
        nflRandomDoc = (Document) nflRandomDocumentsAndElements[0];
        nflRandomElements = (Elements) nflRandomDocumentsAndElements[1];
        optionElements = nflRandomDoc.getElementsByTag("option");
        numberOfWeeksThisSeason = optionElements.select("option:contains(Week )").size();
        seasonGameDates = new String[numberOfWeeksThisSeason];
        out.println("Number of Weeks this season => " + numberOfWeeksThisSeason);
        for (int i = 0; i < numberOfWeeksThisSeason; i++)
        {
            gameWeek = optionElements.select("option:contains(Week )").get(i).text();//Game week
            gameDate = optionElements.select("option:contains(Week )").get(i).val();//Game date
            seasonGameDates[i] = gameDate;
        }
    }
    public String getSeasonWeekDate(int seasonWeekNumber)
    {
        return seasonGameDates[seasonWeekNumber];
    }
}
