import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class CSVFileProcessor
{
    private String file;
    private BufferedReader reader;

    public CSVFileProcessor()
    {
        file = "application_data.csv";

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
