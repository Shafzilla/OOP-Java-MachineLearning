import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Prediction
{
    private ArrayList<Map<String, String>> data;

    private String[] featureValues;

    // A table of all possible permutations
    private  String[][] permutationTable;

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


    public Prediction(String file)
    {
        CSVFileProcessor fileProcessor = new CSVFileProcessor(file);
        setData(fileProcessor.getListCSV());
        setPermutationTable(fileProcessor.getPermutationTable());
        setFeatureValues(fileProcessor.getFeatureValues());
        generateFrequencyTable();
        generateRule();

    }

    public void generateFrequencyTable()
    {
        for(int i = 0; i < data.size(); i++)
        {

            Map<String, String> row = data.get(i);

            String[] rowScanList = new String[5];

            for (int j = 0; j < featureValues.length; j++)
            {
                rowScanList[j] = row.get(featureValues[j]);
            }

            int k = 0;
            for (String[] permutationMatcher : permutationTable)
            {

                if (Objects.equals(permutationMatcher[0], rowScanList[0])
                        && Objects.equals(permutationMatcher[1], rowScanList[1])
                        && Objects.equals(permutationMatcher[2], rowScanList[2])
                        && Objects.equals(permutationMatcher[3], rowScanList[3]))
                {
                    if (Objects.equals(rowScanList[4], "Yes"))
                    {
                        frequencyOutcomes[k][0] = frequencyOutcomes[k][0] + 1; // to increment "Yes"s per permutation
                    } else if (Objects.equals(rowScanList[4], "No"))
                    {
                        frequencyOutcomes[k][1] = frequencyOutcomes[k][1] + 1; // to increment "No"s per permutation
                    }
                    break;

                }

                k = k+1;

            }
        }
    }

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

    public void printFreqTable()
    {
        for (int[] row : frequencyOutcomes)
        {
            System.out.println("Yes: " + row[0] + " No: " + row[1]);
        }
    }

    public void printData()
    {
        for(int i = 0; i < data.size(); i++)
        {
            Map<String, String> row = data.get(i);

            String[] rowScanList = new String[5];

            for (int j = 0; j < featureValues.length; j++)
            {
                rowScanList[j] = row.get(featureValues[j]);
            }

            System.out.println(rowScanList[0] + " " + rowScanList[1] + " " + rowScanList[2] + " " + rowScanList[3] + " " + rowScanList[4]);

        }
    }

    public double[] getPermRulesPercentage()
    {
        return permRulesPercentage;
    }

    public int[][] getFrequencyOutcomes() {
        return frequencyOutcomes;
    }

    public ArrayList<Map<String, String>> getData() {
        return data;
    }

    public String[] getFeatureValues()
    {
        return featureValues;
    }

    public String[][] getPermutationTable()
    {
        return permutationTable;
    }

    public void setData(ArrayList<Map<String, String>> data)
    {
        this.data = data;
    }

    public void setFeatureValues(String[] featureValues) {
        this.featureValues = featureValues;
    }

    public void setPermutationTable(String[][] permutationTable)
    {
        this.permutationTable = permutationTable;
    }

    public void setFrequencyOutcomes(int[][] frequencyOutcomes)
    {
        this.frequencyOutcomes = frequencyOutcomes;
    }

    public void setFrequencyOutcomesElement(int row, int col, int value)
    {
        this.frequencyOutcomes[row][col] = value;
    }

    public int getFrequencyOutcomesElement(int row, int col)
    {
        return frequencyOutcomes[row][col];
    }


}
