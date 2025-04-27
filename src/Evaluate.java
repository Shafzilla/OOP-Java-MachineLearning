import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Evaluate extends Prediction
{
    private String[] featureValues;

    private ArrayList<Map<String, String>> data;

    private String[][] permutationTable;

    private int[][] testingFreqTable = getFrequencyOutcomes();

    private final double[] testingRulePercentage = getPermRulesPercentage();

    private String[] testLikelyHood;

    private double testAccuracy;

    public Evaluate(String file)
    {
        super(file);
        testLikelyHood = new String[16];
        testLikelyHood = getTestLikelyHood();

        CSVFileProcessor fileProcessor = new CSVFileProcessor(file);
        setData(fileProcessor.getListCSV());
        setFeatureValues(fileProcessor.getFeatureValues());
        setPermutationTable(fileProcessor.getPermutationTable());


    }

    public void evaluateAccuracy()
    {

        setTestingFreqTable();

        printTestFreqTable();

        setTestingRulePercentage();

        printTestingRulePercentage();

        calculateTestLikelyhood();

        printTestLikelyhood();

        testPredictorAccuracy();

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

    public void setTestingFreqTable()
    {
        for(int i = 0; i < 150; i++)
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
                        testingFreqTable[k][0] = testingFreqTable[k][0] + 1; // to increment "Yes"s per permutation
                    } else if (Objects.equals(rowScanList[4], "No"))
                    {
                        testingFreqTable[k][1] = testingFreqTable[k][1] + 1; // to increment "No"s per permutation
                    }
                    break;

                }

                k = k+1;

            }
        }
    }

    public void printTestFreqTable()
    {
        for (int[] row : testingFreqTable)
        {
            System.out.println("Yes: " + row[0] + " No: " + row[1]);
        }
    }

    public void setTestingRulePercentage()
    {
        for (int i = 0; i < testingRulePercentage.length; i++)
        {
            double total = testingFreqTable[i][0] + testingFreqTable[i][1]; // get total ("Yes"s + "No"s)
            double percentage = (testingFreqTable[i][0] / total) * 100; // get percentages of "Yes"s
            testingRulePercentage[i] = percentage;

        }
    }

    public void printTestingRulePercentage()
    {
        for (double row : testingRulePercentage)
        {
            System.out.println(row);
        }

    }

    public void calculateTestLikelyhood()
    {
        for(int i = 0; i < testingRulePercentage.length; i++)
        {
            if(testingRulePercentage[i] >= 50)
            {
                testLikelyHood[i] = "Yes";
            }
            else
            {
                testLikelyHood[i] = "No";
            }

        }
    }

    public void printTestLikelyhood()
    {
        for (String row : testLikelyHood)
        {
            System.out.println(row);
        }
    }

    public void testPredictorAccuracy()
    {
        int testYesNum = 0;
        int testNoNum = 0;

        try {

            for (int i = 150; i < 200; i++) {

                Map<String, String> row = data.get(i);

                String[] rowScanList = new String[5];

                for (int j = 0; j < featureValues.length; j++) {
                    rowScanList[j] = row.get(featureValues[j]);
                }

                int k = 0;
                for (String[] permutationMatcher : permutationTable) {

                    if (Objects.equals(permutationMatcher[0], rowScanList[0])
                            && Objects.equals(permutationMatcher[1], rowScanList[1])
                            && Objects.equals(permutationMatcher[2], rowScanList[2])
                            && Objects.equals(permutationMatcher[3], rowScanList[3])) {
                        if (Objects.equals(rowScanList[4], testLikelyHood[k])) {
                            testYesNum = testYesNum + 1;
                        } else {
                            testNoNum = testNoNum + 1;
                        }
                        break;

                    }

                    k = k + 1;

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(testYesNum);
        System.out.println(testNoNum);


        double testTotal = testYesNum + testNoNum;
        testAccuracy = (testYesNum / testTotal) * 100;

        System.out.println(testAccuracy);
    }

    public double getTestAccuracy()
    {
        return testAccuracy;
    }

    public ArrayList<Map<String, String>> getData()
    {
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

    public int[][] getTestingFreqTable()
    {
        return testingFreqTable;
    }

    public double[] getTestingRulePercentage()
    {
        return testingRulePercentage;
    }

    public String[] getTestLikelyHood()
    {
        return testLikelyHood;
    }

    public void setFeatureValues(String[] featureValues)
    {
        this.featureValues = featureValues;
    }

    public void setData(ArrayList<Map<String, String>> data)
    {
        this.data = data;
    }

    public void setPermutationTable(String[][] permutationTable)
    {
        this.permutationTable = permutationTable;
    }

    public void setTestingFreqTable(int[][] testingFreqTable)
    {
        this.testingFreqTable = testingFreqTable;
    }

    public void setTestLikelyHood(String[] testLikelyHood)
    {
        this.testLikelyHood = testLikelyHood;
    }

    public void setTestAccuracy(double testAccuracy)
    {
        this.testAccuracy = testAccuracy;
    }
}
