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
 * version 210416
 * Reads/cleans input URL and returns all Elements and Document
 *******************************************************************/
public class WebSiteReader
{
    private Document nflRandomMatchupsDoc;
    private Elements nflRandomMatchupsElements;
    private Object[] documentsAndElements;
    public Object[] readCleanWebsite(String urlToRead) throws IOException
    {
        System.out.println("(2) Reading and cleaning " + urlToRead);
        Document dirtyDoc = Jsoup.parse(String.valueOf(connect("https://www.covers.com/sports/nfl/matchups?selectedDate=2020-09-10").get()));
        boolean isValidDocument = Jsoup.isValid(String.valueOf(dirtyDoc), Whitelist.basic());
        if (isValidDocument)
        {
            nflRandomMatchupsDoc = dirtyDoc;
        }
        else
        {
            nflRandomMatchupsDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc);
        }
        nflRandomMatchupsDoc = dirtyDoc;//invalidate cleaning!!!!!//Not cleaning dirty doc...overrides nflRandomMatchupsDoc ***************
        nflRandomMatchupsElements = dirtyDoc.getAllElements();//invalidate cleaning!!!!!//Not cleaning dirty doc...overrides nflRandomMatchupsElements ***************
        System.out.println("(3) Finished reading but not cleaning!!TODO fix clean");
        documentsAndElements = new Object[]{nflRandomMatchupsDoc, nflRandomMatchupsElements};
        return documentsAndElements;
        }
    }


