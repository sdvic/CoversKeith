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
 // * version 210405
 *******************************************************************/
public class WebSiteReader
{
    private Document nflRandomMatchupsDoc;
    private Elements nflRandomMatchupsElements;
    private Object[] docEl;
    public Object[] readCleanWebsite(String dirtyURL) throws IOException
    {
        System.out.print("(2) Reading random nfl week: " + dirtyURL + " and NOT! cleaning, ");
        Document dirtyDoc = Jsoup.parse(String.valueOf(connect(dirtyURL).get()));
        boolean isValidDocument = Jsoup.isValid(String.valueOf(dirtyDoc), Whitelist.basic());
        if (isValidDocument)
        {
            System.out.println(dirtyURL + " is valid");
            nflRandomMatchupsDoc = dirtyDoc;
        }
        else
        {
            System.out.println("\t\t\tThe document is not valid, Cleaning document");
            nflRandomMatchupsDoc = new Cleaner(Whitelist.basic()).clean(dirtyDoc);//Not cleaning dirty doc ***************
        }
        nflRandomMatchupsDoc = dirtyDoc;//invalidate cleaning!!!!!
        nflRandomMatchupsElements = nflRandomMatchupsDoc.getAllElements();
        System.out.println("(3) Finished reading but not cleaning!!TODO fix clean " + dirtyURL + " Elements.size(): " + nflRandomMatchupsElements.size() + ", Document.html().length(): " + nflRandomMatchupsDoc.html().length());
        docEl = new Object[]{nflRandomMatchupsDoc, nflRandomMatchupsElements};
        return docEl;
        }
    }


