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
 // * version 2102012A
 *******************************************************************/
public class Main
{
    int i = 3;
    String weekNumberString;
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        while(i++ < 10)
        {
            String version = "210208A";
            String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
            //String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "DanPic ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
            weekNumberString = Integer.toString(i);
            System.out.println("(1) Hello DanPick, version " + version + ", Copyright 2021 Dan Farris");
            Elements matchupDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().getElementsByClass("covers-MatchupFilters-footballDate");
            Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
            String matchupsDate = dateValue.getElementsByAttribute("value").val();
            Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
            System.out.println("week => " + Integer.toString(i));
            System.out.println("matchupsDate => " + matchupsDate);
            SportDataReader sportDataReader = new SportDataReader(deskTopPath);//Reads sports data xlsx file to hash map
            XSSFWorkbook sportDataWorkBook = sportDataReader.getSportDataWorkBook();
            System.out.println(sportDataWorkBook);
            HashMap sportDataMap = sportDataReader.getSportDataMap();
            SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(week);
            String homeTeam = sportsDataAggregator.getHomeTeam();
            String awayTeam = sportsDataAggregator.getAwayTeam();
            String totalHomePicks = sportsDataAggregator.getAwayOU();
            String totalAwayPicks = sportsDataAggregator.getHomeATS();
            SportsDataWriter sportsDataWriter = new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks, homeTeam, awayTeam, matchupsDate,i);
            sportsDataWriter.setI(i);
            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + matchupsDate + "\n" + "Data Event ID " + sportsDataAggregator.getDataEventID() + "\n" + "Data Link ID " + sportsDataAggregator.getDataLinkID() + "\n" + awayTeam + " at " + homeTeam + "\nHome OU => " + sportsDataAggregator.getAwayOU() + "\nAway ATS => " + sportsDataAggregator.getHomeATS(), "DanPick version 200209A", JOptionPane.INFORMATION_MESSAGE);
            System.out.print("(11)  Proper Finish...time " + i + " hooray!");
        }
    }
}


