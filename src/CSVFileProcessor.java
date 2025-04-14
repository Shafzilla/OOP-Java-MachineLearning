import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){

                String[] row = line.split(",");

                for(String index : row)
                {
                    System.out.printf("%-10s", index);
                }
                System.out.println();

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
