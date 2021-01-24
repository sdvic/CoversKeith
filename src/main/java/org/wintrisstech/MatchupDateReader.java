package org.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210123
 *******************************************************************/
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static org.jsoup.Jsoup.connect;
public class MatchupDateReader
{
    public String getMatchupDateWeek(String weekNumberString) throws IOException
    {
        Document nflPage = connect("https://www.covers.com/sports/nfl/matchups").get();//Find matchup date from game week
        Elements eventNumberClass = nflPage.getElementsByClass("cmg_game_data cmg_matchup_game_box");
        Element event = nflPage.getElementById("data-sdi-event-id");
        Elements matchupDates = nflPage.getElementsByClass("covers-MatchupFilters-footballDate");
        Element dateValue = matchupDates.select("option[value]").get(parseInt(weekNumberString));
        String matchupsDate = dateValue.getElementsByAttribute("value").val();
        return matchupsDate;
    }
}
