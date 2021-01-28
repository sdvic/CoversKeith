package org.wintrisstech;
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
 // * version 210128
 *******************************************************************/
public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
    }
    private void getGoing() throws IOException, ParseException
    {
        String version = "210128";
        String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
        String weekNumberString = JOptionPane.showInputDialog(null, "Enter Matchups Week Number", "DanPic ver" + version + ", Copyright 2021 Dan Farris", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("(1) Hello DanPick, version " + version + ", Copyright 2021 Dan Farris, Processing week " + weekNumberString);
        //Document nflMatchups = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get();
        Elements matchupDates = Jsoup.connect("https://www.covers.com/sports/nfl/matchups").get().getElementsByClass("covers-MatchupFilters-footballDate");
        Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
        String matchupsDate = dateValue.getElementsByAttribute("value").val();
        Document week = connect("https://www.covers.com/sports/nfl/matchups?selectedDate=" + matchupsDate).get();
        System.out.println("week => "+ weekNumberString);
        System.out.println("matchupsDate => " + matchupsDate);
        SportDataExcelReader sportDataExcelReader = new SportDataExcelReader(deskTopPath);//Reads sports data xlsx file to hash map
        HashMap sportDataMap = sportDataExcelReader.getSportDataMap();
        XSSFWorkbook sportDataWorkBook = sportDataExcelReader.getSportDataWorkBook();
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator(week);
        JOptionPane.showMessageDialog(null, "Week " + weekNumberString + "\nDate " + matchupsDate +"\nTotal Away Picks => missing" + "\nTotal Home Picks => missing");
        String totalHomePicks = sportsDataAggregator.getTotalHomePicks();
        String totalAwayPicks = sportsDataAggregator.getTotalAwayPicks();
        new SportsDataWriter(deskTopPath, sportDataWorkBook, totalHomePicks, totalAwayPicks);
        System.out.print("(11)  Proper Finish...hooray!");
    }
}


