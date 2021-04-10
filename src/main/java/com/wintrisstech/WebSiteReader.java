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
 * version 210409
 * Reads/cleans input URL and returns all Elements and Document
 *******************************************************************/
public class WebSiteReader
{
    private Document nflRandomMatchupsDoc;
    private Elements nflRandomMatchupsElements;
    private Object[] documentsAndElements;
    public Object[] readCleanWebsite(String urlToRead) throws IOException
    {
        System.out.print("(2) Reading random nfl week: " + urlToRead + " and NOT! cleaning, ");
        Document dirtyDoc = Jsoup.parse(String.valueOf(connect(urlToRead).get()));
        boolean isValidDocument = Jsoup.isValid(String.valueOf(dirtyDoc), Whitelist.basic());
        if (isValidDocument)
        {
            System.out.println(urlToRead + " is valid");
            nflRandomMatchupsDoc = dirtyDoc;
        }
        else
        {
            System.out.println("\t\t\tThe document is not valid, Cleaning document");
            nflRandomMatchupsDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc);
        }
        nflRandomMatchupsDoc = dirtyDoc;//invalidate cleaning!!!!!//Not cleaning dirty doc...overrides nflRandomMatchupsDoc ***************
        nflRandomMatchupsElements = dirtyDoc.getAllElements();//invalidate cleaning!!!!!//Not cleaning dirty doc...overrides nflRandomMatchupsElements ***************
        System.out.println("(3) Finished reading but not cleaning!!TODO fix clean " + urlToRead + " Elements.size(): " + nflRandomMatchupsElements.size() + ", Document.html().length(): " + nflRandomMatchupsDoc.html().length());
        documentsAndElements = new Object[]{nflRandomMatchupsDoc, nflRandomMatchupsElements};
        return documentsAndElements;
        }
    }


