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
    // An Array of the feature labels
    private final String[] featureValues =
            {
            "HasGoodCredit",
            "HasStableJob",
            "HasDebt",
            "HasCollateral",
            "ApplicationIsAccepted"
            };

    // A table of all possible permutations
    private final  String[][] permutationTable =
            {
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

    // A 2-D array for frequency of "yes" and "no" occurrences per permutation {(number of "Yes"s), (number of "No"s)}
    private int[][] frequencyOutcomes =
            {
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            {0,0},
            };


    // An array to store the chance of a loan being accepted as a percentage, per permutation
    private final double[] permRulesPercentage =
            {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };

    public CSVFileProcessor(String file)
    {
        setFile(file);

    }

    public void setFile(String file)
    {
        this.file = file;
    }

    public void buildCSVList()
    {

        listCSV = new ArrayList<>();
        int yesNum = 0;
        int noNum = 0;
        String line = "";
        try
        {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null)
            {
                Map<String, String> rowMap= new HashMap<>(); // New map is made everytime for storing new row
                String[] rowScanList = new String[5]; // array to temporarily store feature values for operations
                String[] row = line.split(","); // array to store feature values temporarily
                for (int i = 0; i < 5; i++) // loop to insert values for columns into each row
                {
                    rowMap.put(featureValues[i], row[i]);
                    rowScanList[i] = row[i];
                }
                Boolean found = false;
                int i = 0; // to track index of the permutation table
                for (String[] permutationMatcher : permutationTable)
                {

                    if (Objects.equals(permutationMatcher[0], rowScanList[0])
                            && Objects.equals(permutationMatcher[1], rowScanList[1])
                            && Objects.equals(permutationMatcher[2], rowScanList[2])
                            && Objects.equals(permutationMatcher[3], rowScanList[3])) // if the scanned row matches the permutation
                    {

                        if(Objects.equals(rowScanList[4], "Yes"))
                        {
                            yesNum = yesNum + 1;
                            frequencyOutcomes[i][0] = frequencyOutcomes[i][0] + 1; // to increment "Yes"s per permutation
                        }
                        else if (Objects.equals(rowScanList[4], "No"))
                        {
                            noNum = noNum + 1;
                            frequencyOutcomes[i][1] = frequencyOutcomes[i][1] + 1; // to increment "No"s per permutation
                        }
                        break;
                    }
                    i = i+1;
                }


                listCSV.add(rowMap); // add row to the list

            }
            //System.out.println("Number of Yeses: " + yesNum + "Number of Nos: " + noNum);
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

    // method to print frequency table
    public void printFrequencyTable()
    {
        try
        {
            for (int[] row : frequencyOutcomes)
            {
                System.out.println("Yes: " + row[0] + " No: " + row[1]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // method to print the dataset in a table
    public void printCSVList()
    {
        try
        {

            for (int i = 0; i <= ((listCSV.size()) - 1); i++)
            {
                System.out.println("\n");
                for (int j = 0; j <= ((featureValues.length) - 1); j++)
                {
                    // printing row and column
                    System.out.printf("%25s", listCSV.get(i).get(featureValues[j]));
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // method to print the contents straight from the CSV file in binary form
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

    // method to generate the chance of an application being accepted as a percentage
    public void generateRule()
    {
        try
        {
            for (int i = 0; i < permRulesPercentage.length; i++)
            {
                double total = frequencyOutcomes[i][0] + frequencyOutcomes[i][1]; // get total ("Yes"s + "No"s)
                double percentage = (frequencyOutcomes[i][0] / total) * 100; // get percentages of "Yes"s
                permRulesPercentage[i] = percentage;


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public double[] getPermRulesPercentage()
    {
        return permRulesPercentage;
    }


    public String[][] getPermutationTable()
    {
        return permutationTable;
    }

    public ArrayList<Map<String, String>> getListCSV() {
        return listCSV;
    }

    public String[] getFeatureValues() {
        return featureValues;
    }

    public int[][] getFrequencyOutcomes() {
        return frequencyOutcomes;
    }

}
