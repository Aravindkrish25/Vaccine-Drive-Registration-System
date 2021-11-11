/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author Hrithik
 */
public class Stats {
     public static void statsbytype() {
        try {

            String label1 = "Covaxin";
            String label2 = "Covishield";
            String covaxin = new String();
            String covishield = new String();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
                String command = "select count(*) as count from beneficiary where vaccine_type != 0 group by vaccine_type order by vaccine_type";
                PreparedStatement st = connection.prepareStatement(command);
                ResultSet res = st.executeQuery();
                int c=0;
                while (res.next()){
                    if (c==0){
                        covaxin = Integer.toString(res.getInt("count"));
                    }
                    else{
                        covishield = Integer.toString(res.getInt("count"));
                    }
                    c++;
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(label1+" " +covaxin + " " + label2 +" "+covishield);
            System.out.println();
            int total=(Integer.parseInt(covaxin)+Integer.parseInt(covishield));
            float fper = Float.parseFloat(covaxin)*100/total;
            float sper = Float.parseFloat(covishield)*100/total;
            System.out.println(fper+" "+sper);
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Covaxin ("+Math.round(fper) +" %)", Integer.parseInt(covaxin));
            dataset.setValue("Covishield (" +Math.round(sper) +" %)",Integer.parseInt(covishield));
            JFreeChart chart = ChartFactory.createPieChart("Type Wise Statistics",   // chart title
                    dataset,          // data
                    true,      // include legend
                    false,
                    false);
            int width = 640;   /* Width of the image */
            int height = 480;  /* Height of the image */
            File pieChart = new File( "typewisestatspiechart.jpeg");
            try
            {
                ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
            } catch (Exception e)
            {
                System.out.print(e);
            }
            ImageIcon image = new ImageIcon("D://PieChart.jpeg");

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
     
     public static void statsbygender()
     {
        try {
            int c1=0,c2=0;
            String label = new String();
            String men = new String();
            String women = new String();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
                String command = "select b_gender from beneficiary where vaccine_type != 0 ";
                PreparedStatement st = connection.prepareStatement(command);
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    String g = res.getString("b_gender");
                    if (g.equals("M")) {
                        c1 += 1;
                    } else {
                        c2 += 1;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
                men = Integer.toString(c1);
                women=Integer.toString(c2);
                System.out.println(label +" " +men + " " + women);
                System.out.println();
                int total=(Integer.parseInt(men)+Integer.parseInt(women));
                float menper = Float.parseFloat(men)*100/total;
                float womenper = Float.parseFloat(women)*100/total;
                System.out.println(menper+" "+womenper);
                DefaultPieDataset dataset = new DefaultPieDataset();
                dataset.setValue("Women ("+Math.round(womenper) +" %)", Integer.parseInt(women));
                dataset.setValue("Men (" +Math.round(menper) +" %)",Integer.parseInt(men));
                JFreeChart chart = ChartFactory.createPieChart("Gender Wise vaccination Stats " + label,   // chart title
                        dataset,          // data
                        true,             // include legend
                        false,
                        false);
                int width = 640;   /* Width of the image */
                int height = 480;  /* Height of the image */
                File pieChart = new File( "genderwisepiechart.jpeg" );
                try
                {
                    ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
                } catch (Exception e)
                {
                    System.out.print(e);
                }
                ImageIcon image = new ImageIcon("D://PieChart.jpeg");

            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
     
     public static void statsbyage() 
     {
        try {
            int c1=0,c2=0,c3=0;
            String c_18_to_45 = new String();
            String c_45_to_60 = new String();
            String c_60_and_above = new String();
            String c_total = new String();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
                String command = "select b_age from beneficiary where vaccine_type != 0 ";
                PreparedStatement st = connection.prepareStatement(command);
                ResultSet res = st.executeQuery();
                while (res.next()){
                    int age = res.getInt("b_age");
                    if ((age>=18)&&(age<=45)){
                        c1+=1;
                    }
                    else if((age>45)&&(age<=60)){
                        c2+=1;
                    }
                    else{
                        c3+=1;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            c_18_to_45 =Integer.toString(c1);
            c_45_to_60=Integer.toString(c2);
            c_60_and_above=Integer.toString(c3);
            c_total=Integer.toString(c1+c2+c3);
            System.out.println(c_18_to_45 + " " + c_45_to_60 + " "+ c_60_and_above);
            System.out.println();
            int total=(Integer.parseInt(c_total));
            float firstper = Float.parseFloat(c_18_to_45)*100/total;
            float secondper = Float.parseFloat(c_45_to_60)*100/total;
            float thirdper = Float.parseFloat(c_60_and_above)*100/total;
            System.out.println(firstper+" "+secondper+" "+thirdper);
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue(" 18 to 45 ("+Math.round(firstper) +" %)", Integer.parseInt(c_18_to_45));
            dataset.setValue(" 46 to 60 (" +Math.round(secondper) +" %)",Integer.parseInt(c_45_to_60));
            dataset.setValue(" 60 above (" +Math.round(thirdper) +" %)",Integer.parseInt(c_60_and_above));
            JFreeChart chart = ChartFactory.createPieChart("Age wise vaccine statistics " ,   // chart title
                    dataset,          // data
                    true,             // include legend
                    true,
                    false);
            int width = 640;   /* Width of the image */
            int height = 480;  /* Height of the image */
            File pieChart = new File( "agewisepiechart.jpeg" );
            try
            {
                ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
            } catch (Exception e)
            {
                System.out.print(e);
            }
            System.out.println("Pie Chart generated");

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
     public static void statsbydistrict() throws Exception {
        String[][] record = new String[10][3];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
            String command = "select c_district,count(*) as count from beneficiary natural join vaccinator natural join vaccination_center where dose2_flag=0 group by c_district order by c_district";
            PreparedStatement st = connection.prepareStatement(command);
            ResultSet res = st.executeQuery();
            int i=0;
            while (res.next()){
                record[i][0]=res.getString("c_district");
                record[i][1]=Integer.toString(res.getInt("count"));
                i++;
            }
            int j=0;
            command = "select c_district,count(*) as count from beneficiary natural join vaccinator natural join vaccination_center where dose2_flag=1 group by c_district order by c_district";
            st = connection.prepareStatement(command);
            res = st.executeQuery();
            while(res.next()){
                if (record[j][0].equals(res.getString("c_district"))){
                    record[j][2]=Integer.toString(res.getInt("count"));
                }
                else{
                    System.out.println("Mismatch");
                }
                j+=1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String dose1 ="Dose1 Vaccinated";
        String dose2= "Dose2 Vaccinated";
        String name;

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for(int i=0;i<10;i++) {
            name = record[i][0];
            int c1 = Integer.parseInt(record[i][1]);
            int c2 = Integer.parseInt(record[i][2]);
            dataset.addValue(c1, dose1, name);
            dataset.addValue(c2,dose2,name);
        }
        
        JFreeChart barChart = ChartFactory.createBarChart(
                "DISTRICT WISE VACCINATION STATS",
                "District", "No. of beneficiaries vaccinated",
                dataset,PlotOrientation.VERTICAL,
                true, true, false);
        barChart.setBorderVisible(true);

        int width = 1000;    /* Width of the image */
        int height = 640;   /* Height of the image */
        File BarChart = new File( "DistrictWiseStatsBarChart.jpeg" );
        ChartUtils.saveChartAsJPEG( BarChart , barChart , width , height );
        System.out.println("Chart created");
    }
    
    
    
}
