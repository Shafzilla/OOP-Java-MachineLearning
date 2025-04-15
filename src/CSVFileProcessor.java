import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CSVFileProcessor
{
    private String file;
    private BufferedReader reader;
    private ArrayList<Map<String, String>> listCSV;
    private String[] featureValues = {"HasGoodCredit", "HasStableJob", "HasDebt", "HasCollateral", "ApplicationIsAccepted"};
    private String[][] freqencyTable = {
            {"Yes", "Yes", "Yes", "Yes"},
            {"Yes", "Yes", "Yes", "No"},
            {"Yes", "Yes", "No", "Yes",},
            {"Yes", "Yes", "No", "No"},
            {"Yes", "No", "Yes", "Yes"},
            {"Yes", "No", "Yes", "No"},
            {"Yes", "No", "No", "Yes"},
            {"Yes", "No", "No", "No"},
            {"No", "Yes", "Yes", "Yes"},
            {"No", "Yes", "Yes", "No"},
            {"No", "Yes", "No", "Yes"},
            {"No", "Yes", "No", "No"},
            {"No", "No", "Yes", "Yes"},
            {"No", "No", "Yes", "No"},
            {"No", "No", "No", "Yes"},
            {"No", "No", "No", "No"}
};


    public CSVFileProcessor()
    {
        file = "application_data.csv";

    }

    public void buildCSVList()
    {

        listCSV = new ArrayList<>();
        int[] freqNums;
        int yesNum = 0;
        int noNum = 0;
        String line = "";
        try
        {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null)
            {
                Map<String, String> rowMap= new HashMap<>();
                String[] rowScanList = new String[5];
                String[] row = line.split(",");
                for (int i = 0; i < 5; i++)
                {
                    rowMap.put(featureValues[i], row[i]);
                    System.out.println(featureValues[i] + ": " + rowMap.get(featureValues[i]));
                    rowScanList[i] = row[i];
                }
                Boolean found = false;
                for (String[] tempRowScan : freqencyTable)
                {
                    System.out.println("entering for loop gfggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
                    if (Objects.equals(tempRowScan[0], rowScanList[0]) && Objects.equals(tempRowScan[1], rowScanList[1]) && Objects.equals(tempRowScan[2], rowScanList[2]) && Objects.equals(tempRowScan[3], rowScanList[3]))
                    {
                        System.out.println("Accessed theebbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                        if(Objects.equals(rowScanList[4], "Yes"))
                        {
                            yesNum = yesNum + 1;
                        } else if (Objects.equals(rowScanList[4], "No"))
                        {
                            noNum = noNum + 1;
                        }
                        break;
                    }
                }

                listCSV.add(rowMap);

            }
            System.out.println("Number of Yeses" + yesNum + "Number of Nos" + noNum);
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }

    public void printCSVList()
    {

        for(int i = 0; i <= ((listCSV.size())-1); i++)
        {
            System.out.println("\n");
            for (int j = 0; j <= ((featureValues.length) - 1); j++)
            {
                // printing row and column
                System.out.printf("%25s",listCSV.get(i).get(featureValues[j]));
            }


        }
    }

    public void trainOnData()
    {
        String[] rowScan = new String[0];
        for(Map<String, String> row : listCSV)
        {
            int i = 0;
            rowScan[i] = row.get(featureValues[i]);

        }
    }


    public void printFileContents()
    {

        String line = "";
        int[] binaryNum = new int[5];

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
//                System.out.println("IsApproved: " + row[4]);

                for (int i = 0; i < row.length; i++)
                {
                    if (Objects.equals(row[i], "Yes"))
                    {
                        binaryNum[i] = 1;

                    } else if (Objects.equals(row[i], "No"))
                    {
                        binaryNum[i] = 0;
                    }

                }
                System.out.print("The CSV Binary: [ ");
                for(int i = 0; i< binaryNum.length; i++) {
                    System.out.print(" " + binaryNum[i] + " ");
                }
                System.out.println("]");



//                for(String index : row)
//                {
//                    System.out.printf("%-10s", index);
//                }
//                System.out.println();

            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
