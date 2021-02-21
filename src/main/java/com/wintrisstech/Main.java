package com.wintrisstech;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 2102020
 *******************************************************************/
public class Main
{
    String weekNumberString;
    String version = "210220";
    int numberOfWeeks = 21;
    String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    String dataEventID;
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        SportDataReader sportDataReader = new SportDataReader(deskTopPath);//Reads sports data xlsx file to hash map
        for (int i = 1; i < numberOfWeeks; i++)//week number 1 based
        {
            //String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "DanPic ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
            weekNumberString = Integer.toString(i);
            System.out.println("(1) Hello DanPick, version " + version + ", Copyright 2021 Dan Farris");
            Elements matchupDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().getElementsByClass("covers-MatchupFilters-footballDate");
            Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
            String matchupsDate = dateValue.getElementsByAttribute("value").val();
            Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
            Elements thisWeekGames = week.getElementsByClass("cmg_game_data cmg_matchup_game_box");//this is good...all games in "week"
            dataEventID = thisWeekGames.get(i).attr("data-event-id");//two team event number on particular date
            System.out.println("week => " + Integer.toString(i) + " of " + numberOfWeeks + "weeks");
            System.out.println("matchupsDate => " + matchupsDate);
            XSSFWorkbook sportDataWorkBook = sportDataReader.getSportDataWorkBook();
            SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(thisWeekGames);
            String homeTeam = sportsDataAggregator.getHomeTeam();
            String awayTeam = sportsDataAggregator.getAwayTeam();
            //SportsDataWriter sportsDataWriter = new SportsDataWriter(deskTopPath, sportDataWorkBook, weekNumberString, sportsDataAggregator.getHomeTeam(), sportsDataAggregator.getAwayTeam(), sportsDataAggregator.getHome(), sportsDataAggregator.getAway(), sportsDataAggregator.getUnder(), sportsDataAggregator.getOver());
            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + matchupsDate + "\n" + "Data Event ID " + sportsDataAggregator.getDataEventID() + "\n" + "Data Link ID " + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportsDataAggregator.getOver() + "\nUnder " + sportsDataAggregator.getUnder() + "\nHome " + sportsDataAggregator.getHome() + "\nAway " + sportsDataAggregator.getAway(), "DanPick version 200209A", JOptionPane.INFORMATION_MESSAGE);
            System.out.print("(11)  Proper Finish...hooray!");
        }
    }
}


