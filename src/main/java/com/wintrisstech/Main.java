package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 21020304
 *******************************************************************/
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
public class Main
{
    String weekNumberString;
    String version = "210304";
    int numberOfWeeks = 4;
    private int j;//game counter
    String deskTopPath = System.getProperty("user.home") + "/Desktop";/* User's desktop path */
    String dataEventID;
    private XSSFSheet coversSheet;
    private int gameTotal;
    private Elements thisWeeksGames;
    public static void main(String[] args) throws IOException, ParseException
    {
        new Main().getGoing();
//        new Main().jspoupTester();
    }
    private void getGoing() throws IOException, ParseException
    {
        WebSiteReader webSiteReader = new WebSiteReader();
        thisWeeksGames = webSiteReader.readCleanWebsite();
        SportDataReader sportDataReader = new SportDataReader(deskTopPath);//Reads sports data xlsx file to hash map
        SportsDataAggregator sportsDataAggregator = new SportsDataAggregator();
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
            System.out.println("++++++++++++++++++++++++++week => " + weekNumberString + " of " + numberOfWeeks + "weeks");
            System.out.println("matchupsDate => " + matchupsDate);
            XSSFWorkbook sportDataWorkBook = sportDataReader.getSportDataWorkBook();
            coversSheet = sportDataReader.getSportDataSheet();
            gameTotal += sportsDataAggregator.aggregateSportsData(thisWeekGames, coversSheet, weekNumberString);
            System.out.println("---------------------------------------------- game total " + gameTotal);
            String homeTeam = sportsDataAggregator.getHomeTeam();
            String awayTeam = sportsDataAggregator.getAwayTeam();
            SportsDataWriter sportsDataWriter = new SportsDataWriter(deskTopPath, sportDataWorkBook, weekNumberString, sportsDataAggregator.getHomeTeam(), sportsDataAggregator.getAwayTeam(), sportsDataAggregator.getHome(), sportsDataAggregator.getAway(), sportsDataAggregator.getUnder(), sportsDataAggregator.getOver(), i, j);
            sportsDataWriter.setI(i);
            JOptionPane.showMessageDialog(null, "Week " + weekNumberString + " " + matchupsDate + "\n" + "Data Event ID " + sportsDataAggregator.getDataEventID() + "\n" + "Data Link ID " + "\n" + awayTeam + " at " + homeTeam + "\nOver " + sportsDataAggregator.getOver() + "\nUnder " + sportsDataAggregator.getUnder() + "\nHome " + sportsDataAggregator.getHome() + "\nAway " + sportsDataAggregator.getAway(), "DanPick version 200209A", JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.print("(11)  Proper Finish...hooray!");
    }
    public void jspoupTester()
    {
        String html = "<html><head><title>Sample Title</title></head>"
                + "<body>"
                + "<p>Sample Content</p>"
                + "<div id='sampleDiv'><a href='www.google.com'>Google</a>"
                + "<h3><a>Sample</a><h3>"
                + "</div>"
                + "<div id='imageDiv' class='header'><img name='google' src='google.png' />"
                + "<img name='yahoo' src='yahoo.jpg' />"
                + "</div>"
                + "<option value='2020-09-10'>Week 1</option>"
                + "<option value='2020-09-17'>Week 2</option>"
                + "</body></html>";
        Document document = Jsoup.parse(html);

        //a with href
        Elements links = document.select("a[href]");
        for (Element link : links)
        {
            System.out.println("Href: " + link.attr("href"));
            System.out.println("Text: " + link.text());
        }

        // img with src ending .png
        Elements pngs = document.select("img[src$=.png]");

        for (Element png : pngs)
        {
            System.out.println("(1) Name: " + png.attr("name"));
        }

        // div with class=header
        Element headerDiv = document.select("div.header").first();
        System.out.println("(2) Id: " + headerDiv.id());

        // get Week
        Element optionDiv = document.select("option").get(1);
        System.out.println("(3) Week: " + optionDiv.text());

        // get date
        Element optionVal = document.select("option").get(1);
        System.out.println("(4) Date: " + optionVal.val());

        // direct a after h3
        Elements sampleLinks = document.select("h3 > a");
        for (Element link : sampleLinks)
        {
            System.out.println("(5...)Text: " + link.text());
        }
    }

}


