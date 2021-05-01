package com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2021 Dan Farris
 * version 2100501
 *******************************************************************/
public class InfoPrinter
{
    public void printInfo(String format, String[] infoToPrint)
    {
        int weekIndex = 0;
        for (String s : infoToPrint)
        {
            System.out.printf(format, infoToPrint[weekIndex++]);
        }
        System.out.println();
    }
    public void printInfo(String format, String textToPrint, int numberOfTimesToPrint)
    {
        for (int i = 0; i < numberOfTimesToPrint; i++)
        {
            System.out.printf(format, textToPrint + (i + 1));
        }
        System.out.println();
    }
    public InfoPrinter getInfoPrinter()
    {
        return this;
    }
}
