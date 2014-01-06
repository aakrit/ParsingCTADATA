package Parse;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 12/30/13
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChicagoData
{
    private static BufferedReader inputfileName;
    private static FileOutputStream outputFile;
    private static int numberOfCols = 0;
    private static ArrayList<String> tableAttributes = new ArrayList<String>();
    private static String[] tableAttrTypes = new String[10];
    private static String varchar = " VARCHAR(50) ", integer = " INT ", date = " DATE ", tableName = " ChicagoCTA ";


    private static void readInputAndWriteToFile() throws IOException{
        String read;
        int lineCount = 0;
        boolean createTable = false, insertFile = false;
        while((read = inputfileName.readLine()) != null){

            if(!createTable)    //create table for 1st time
            {
                String[] stv = read.split(",");
                for(String s : stv) tableAttributes.add(s);
                File file = new File("Table.sql");
                if(!file.exists()) file.createNewFile();
                outputFile = new FileOutputStream(file);
                int i = 0;
                writeToFile("CREATE TABLE IF NOT EXIST"+tableName+"(");
                writeToFile(tableAttributes.get(i++) + integer+ " NOT NULL AUTO_INCREMENT PRIMARY KEY");
                writeToFile(tableAttributes.get(i++) + varchar);
                writeToFile(tableAttributes.get(i++) + varchar);
                writeToFile(tableAttributes.get(i++) + integer);
                writeToFile(tableAttributes.get(i++) + integer);
                writeToFile(tableAttributes.get(i++) + integer);
                writeToFile(tableAttributes.get(i++) + date);
                writeToFile(tableAttributes.get(i++) + varchar);
                writeToFile(tableAttributes.get(i++) + varchar);
                writeToFile(") Engine=InnoDB;");
                createTable = true;
                continue;
            }
            //insert values into the table
            if(!insertFile){
                File file = new File("Insert.sql");
                if(!file.exists()) file.createNewFile();
                outputFile = new FileOutputStream(file);
                insertFile = true;
            }
            int j = 0;
            String[] nextLine = read.split(",");
            writeToFile("INSERT INTO"+tableName+"(");
            StringBuffer att = new StringBuffer();
            for(j = 1; j < tableAttributes.size()-1; j++){
                att.append(tableAttributes.get(j) + ", ");
            }
            att.append(tableAttributes.get(j++)+")");
            writeToFile(att.toString());
            att.setLength(0);
            att.append("VALUES ( ");
            for(j = 1; j < tableAttributes.size()-1; j++){
                att.append("'"+nextLine[j]+"', ");
            }
            att.append(nextLine[tableAttributes.size()-1]+" \");\n");
            writeToFile(att.toString());
            att.setLength(0);
        }
    }

    //write data to the file
    private static void writeToFile(String s) throws IOException
    {
        outputFile.write(s.getBytes());
        String endl = "\n";
        outputFile.write(endl.getBytes());
        outputFile.flush();
    }



    public static void main(String[] args){
        if(args.length == 0)
        {
            try//get the filename to read from and the number of threads to launch
            {
                inputfileName = new BufferedReader(new FileReader("CTA_Data.csv"));
                readInputAndWriteToFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(args.length == 1)
        {
            try//get the filename to read from and the number of threads to launch
            {
                inputfileName = new BufferedReader(new FileReader(args[0]));
                readInputAndWriteToFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //create a new
         //open the .csv file
    }

}
