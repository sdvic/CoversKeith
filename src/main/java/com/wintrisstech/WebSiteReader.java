package com.wintrisstech;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import java.io.IOException;
import static org.jsoup.Jsoup.connect;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210304
 *******************************************************************/
public class WebSiteReader
{
    private String calendarWeekDate = "2020-10-18";
    private String dirtyURL = "https://www.covers.com/sports/nfl/matchups?selectedDate=" + calendarWeekDate;
    private Document nflMatchupsDoc;
    private Elements nflMatchups;
    public Elements readCleanWebsite() throws IOException
    {
        System.out.println("(4) Reading and cleaning " + dirtyURL);//https://www.covers.com/sports/nfl/matchups?selectedDate=2020-10-18");
        String dirtyString = connect(dirtyURL).get().html();
        boolean valid = Jsoup.isValid(dirtyString, Whitelist.basic());
        if (valid)
        {
            System.out.println("The document is valid");
        }
        else
        {
            System.out.println("\t\t\tThe document is not valid, Cleaning document");
            Document dirtyDoc = Jsoup.parse(dirtyString);
            nflMatchupsDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc);
            nflMatchups = nflMatchupsDoc.getAllElements();
            System.out.println("\t\t\tThe document is now clean...hooray...its length is " + nflMatchups.html().length());
        }
        System.out.println("(5) Finished reading and cleaning website");
        System.out.println("******************************************************************************************************************************");
        return nflMatchups;
        }
    }


