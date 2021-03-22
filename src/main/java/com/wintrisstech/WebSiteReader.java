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
 // * version 210322
 *******************************************************************/
public class WebSiteReader
{
    private String calendarWeekDate = "2020-10-18";
    private String dirtyURL;
    private Document nflRandomMatchupsDoc;
    private Elements nflRandomMatchupsElements;
    public Document readCleanWebsite(String dirtyURL) throws IOException
    {
        this.dirtyURL = dirtyURL;
        System.out.println("(4) Reading and cleaning " + dirtyURL);
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
            nflRandomMatchupsDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc);
            nflRandomMatchupsElements = nflRandomMatchupsDoc.getAllElements();
            System.out.println("\t\t\tThe document is now clean...hooray...its length is " + nflRandomMatchupsDoc.html().length());
        }
        //out.println("***=> " + nflRandomMatchupsElements);//??????????????????????????????????????????? TODO find date list !!!
        System.out.println("(5) Finished reading and cleaning website");
        return nflRandomMatchupsDoc;
        }
    }


