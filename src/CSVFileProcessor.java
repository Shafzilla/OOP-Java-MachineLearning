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
    private Map<String, String> rowMap;

    public CSVFileProcessor()
    {
        file = "application_data.csv";

    }

    public void buildCSVList()
    {
        listCSV = new ArrayList<>();
        rowMap= new HashMap<>();
        String line = "";
        try
        {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null)
            {

                String[] row = line.split(",");
//                System.out.println("IsApproved: " + row[4]);
                for (int i = 0; i < 5; i++)
                {
                    rowMap.put(featureValues[i], row[i]);
                    System.out.println(featureValues[i] + ": " + rowMap.get(featureValues[i]));
                }

                listCSV.add(rowMap);

            }
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
        System.out.println(listCSV.get(6).get("HasDebt"));
        for(int i = 0; i < listCSV.size(); i++)
        {
            for (int j = 0; j < featureValues.length; i++)
            {
                System.out.println(listCSV.get(i).get(featureValues[j]));
            }


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

    public ArrayList<Map<String, String>> getDataset() {
        return listCSV;
    }

}
