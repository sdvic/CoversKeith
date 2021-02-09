package com.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210208A
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        String version = "210208A";
        String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
        String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "DanPic ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("(1) Hello DanPick, version " + version + ", Copyright 2021 Dan Farris");
        Elements matchupDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().getElementsByClass("covers-MatchupFilters-footballDate");
        Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
        String matchupsDate = dateValue.getElementsByAttribute("value").val();
        Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
        System.out.println("week => "+ weekNumberString);
        System.out.println("matchupsDate => " + matchupsDate);
        SportDataReader sportDataReader = new SportDataReader(deskTopPath);//Reads sports data xlsx file to hash map
        XSSFWorkbook sportDataWorkBook = sportDataReader.getSportDataWorkBook();
        System.out.println(sportDataWorkBook);
        HashMap sportDataMap = sportDataReader.getSportDataMap();
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(week);
        String homeTeam = sportsDataAggregator.getHomeTeam();
        String awayTeam = sportsDataAggregator.getAwayTeam();
        String totalHomePicks = sportsDataAggregator.getTotalHomeOUPicks();
        String totalAwayPicks = sportsDataAggregator.getTotalHomeATSPicks();
        new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks, homeTeam, awayTeam, matchupsDate);
        JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\nDate " + matchupsDate +"\nTotal Away ATS Picks => " + sportsDataAggregator.getTotalHomeATSPicks() + "\nTotal Away O/U Picks => " + sportsDataAggregator.getTotalHomeOUPicks());
        System.out.print("(11)  Proper Finish...hooray!");
    }
}


