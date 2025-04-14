import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CSVFileProcessor
{
    private String file;
    private BufferedReader reader;

    private Map<String, String> rowMap;


    public CSVFileProcessor()
    {
        file = "application_data.csv";

    }

    public void convertCSVToBinary()
    {
        rowMap = new HashMap<>();
        String line = "";
        int[] binaryNum = new int[5];

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");
//                System.out.println("IsApproved: " + row[4]);
                rowMap.put("HasGoodCredit", row[0]);
                rowMap.put("HasStableJob", row[1]);
                rowMap.put("HasDebt", row[2]);
                rowMap.put("HasColateral", row[3]);
                rowMap.put("ApplicationIsAccepted", row[4]);

                System.out.println("HasGoodCredit: " + rowMap.get("HasGoodCredit"));
                System.out.println("HasStableJob: " + rowMap.get("HasStableJob"));
                System.out.println("HasDebt: " + rowMap.get("HasDebt"));
                System.out.println("HasColateral: " + rowMap.get("HasColateral"));
                System.out.println("ApplicationIsAccepted: " + rowMap.get("ApplicationIsAccepted"));

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
