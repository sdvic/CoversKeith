package src.main.java.com.wintrisstech;
/*******************************************************************
 * Covers NFL Extraction Tool
 * Copyright 2020 Dan Farris
 // * version 210324
 * write new NFL Covers data to the large SportData Excel sheet
 *******************************************************************/
public class SportDataWriter
{
    private int i;//week counter
    private int j;//game counter
    private int rowOffset = 2;
    public SportDataWriter()
    {
        //XSSFWorkbook updatedSportWorkbook = sportDataWorkbook;
        //File sportDataFile = new File(desktopPath + "/SportData.xlsx");
        //System.out.println("(8) Writing " + sportDataFile);
        System.out.println("(9) Writing covers workbook xlsx");// to File: " + coversOutputFile);
        try
        {
           // FileOutputStream coversUpdatedFOS = new FileOutputStream(sportDataFile);
            //updatedSportWorkbook.write(coversUpdatedFOS);
            //coversUpdatedFOS.close();
        }
        catch (Exception e)
        {
            System.out.println("Sports Data xlsx file writing problem");
            e.printStackTrace();
        }
        System.out.println("(10) Finished writing updated SportData.xlsx");// workbook to File: " + sportDataFile);
    }
    public void setI(int i)
    {
        this.i = i;
    }
}
