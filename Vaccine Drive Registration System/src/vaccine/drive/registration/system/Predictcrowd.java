/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import com.opencsv.CSVWriter;
import java.io.*;
import java.util.*;

/**
 *
 * @author Hrithik
 */
public class Predictcrowd {
    public static void writeInput(String arr[][],int n)
    {
        File file = new File("sample.csv");
        try 
        {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);


            String[] header = { "center_id","csa","cva","date" };
            writer.writeNext(header);
            
            for(int i=0;i<n;i++)
            {
                System.out.println(arr[i][3]);
                String[] data1 = arr[i];
                writer.writeNext(data1);
            }

            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Float> getcrowdness() {
        String s = null;
         ArrayList<Float> crowd = new ArrayList<Float>();
        try {

            Process p = Runtime.getRuntime().exec("python crowdness.py");


            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            
            String output = "";
            while ((s = stdInput.readLine()) != null) {
                output += s;

            }
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(output.split(" ")));

          for(String x:list){
            	crowd.add(Float.parseFloat(x));
            }

            System.out.println(crowd);

            // read any errors from the attempted command

            String error = "";
            while ((s = stdError.readLine()) != null) {
              	error += s;
            }
        	
        	System.out.println(error);
        }
        catch (IOException e)
        {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
        return crowd;
    }
    
}
