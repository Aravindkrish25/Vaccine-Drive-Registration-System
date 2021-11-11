/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Hrithik
 */
public class ScheduleAppointment
{
    public static String[][] center = new String[100][9];
    //0centerid
    //1centername
    //2address
    //3phone
    //4covaxincount
    //5covishiledcount
    //6priceflag
    //7covishieldprice
    //8covaxinprice
    public static int getCenters(String pincode,String district)
    {
        int count=0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");

            PreparedStatement st;
            String command1 = "select * from vaccination_center where c_district = ? and c_pincode = ?";
            if(pincode.equals(""))
            {
                command1 = "select * from vaccination_center where c_district = ?" ;
                st = connection.prepareStatement(command1);
                st.setString(1,district);
            }
            else if(district.equals(""))
            {
                command1 = "select * from vaccination_center where c_pincode = ?" ;
                st = connection.prepareStatement(command1);
                st.setString(1,pincode);
            }
            else
            {
                st = connection.prepareStatement(command1);
                st.setString(1,district);
                st.setString(2,pincode);
            }
            ResultSet result = st.executeQuery();
            int j=0;
            int c1=0;
            int c2=0;
            while (result.next())
            {
                j=0;
                c1 = result.getInt("covaxin_count");
                c2 = result.getInt("covishield_count");
                if(c1>0 || c2>0)
                {
                    center[count][j++]= String.valueOf(result.getInt("c_id"));
                    center[count][j++]=result.getString("c_name");
                    center[count][j++]=result.getString("c_addr");
                    center[count][j++]=result.getString("c_phone");
                    center[count][j++]=String.valueOf(c1);
                    center[count][j++]=String.valueOf(c2);
                    center[count][j++]=String.valueOf(result.getInt("price_flag"));
                    center[count][j++]=String.valueOf(result.getInt("covaxin_price"));
                    center[count][j++]=String.valueOf(result.getInt("covishield_price"));
                    count++;
                }
            }

            return count;

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return count;
    }
    public static int filtercenters(String pincode,String district,boolean freeflag,boolean costflag,boolean covaxinflag,boolean covishieldflag)
    {
        int count=0;
        ArrayList<Object> temp = new ArrayList<Object>();
        Integer[] type = new Integer[10];


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");

            PreparedStatement st;
            int parametermaxindex=0;
            String command1 = "select * from vaccination_center where ";
            //Based on Location
            if(pincode.equals(""))
            {

                command1 = command1.concat("c_district = ?");
                temp.add(district);
                type[parametermaxindex++]=1;
            }
            else if(district.equals(""))
            {
                command1 = command1.concat("c_pincode = ?");
                temp.add(pincode);
                type[parametermaxindex++]=1;
            }
            else
            {
                command1 = command1.concat("c_district = ? and ");
                temp.add(district);
                type[parametermaxindex++]=1;

                command1 = command1.concat("c_pincode = ?");
                temp.add(pincode);
                type[parametermaxindex++]=1;
            }
            //Based on cost/paid
            if(freeflag ^ costflag)
            {
                command1 = command1.concat(" and price_flag = ?");
                if(freeflag)
                {
                    temp.add(0);
                    type[parametermaxindex++]=0;
                }
                else
                {
                    temp.add(1);
                    type[parametermaxindex++]=0;
                }
            }

            //Based on vaccinetype
            if(covishieldflag ^ covaxinflag)
            {
                if(covaxinflag)
                {
                    command1 = command1.concat(" and covaxin_count > ?");
                }
                else
                {
                    command1 = command1.concat(" and covishield_count > ?");
                }
                temp.add(0);
                type[parametermaxindex++]=0;
            }
            System.out.println(command1);
            st = connection.prepareStatement(command1);
            for(int i=0;i<parametermaxindex;i++)
            {
                if(type[i]==1)
                {
                    st.setString(i+1,(String)temp.get(i));
                }
                else
                {
                    st.setInt(i+1,(Integer)temp.get(i));
                }
            }

            ResultSet result = st.executeQuery();
            int j=0;
            int c1=0;
            int c2=0;
            while (result.next())
            {
                j=0;
                c1 = result.getInt("covaxin_count");
                c2 = result.getInt("covishield_count");
                if(c1>0 || c2>0)
                {
                    center[count][j++]= String.valueOf(result.getInt("c_id"));
                    center[count][j++]=result.getString("c_name");
                    center[count][j++]=result.getString("c_addr");
                    center[count][j++]=result.getString("c_phone");
                    center[count][j++]=String.valueOf(c1);
                    center[count][j++]=String.valueOf(c2);
                    center[count][j++]=String.valueOf(result.getInt("price_flag"));
                    center[count][j++]=String.valueOf(result.getInt("covaxin_price"));
                    center[count][j++]=String.valueOf(result.getInt("covishield_price"));
                    count++;
                }
            }

            return count;

        }catch (Exception e)
        {
            System.out.println(e);
        }
        return count;

    }
    public static void main(String args[])
    {
        int c = filtercenters("","Coimbatore",true,true,false,true);
        System.out.println(c);
        for (int x=0;x<c;x++)
        {
            for(int y=0;y<9;y++)
            {
                System.out.print(center[x][y] + " ");
            }
            System.out.println();
        }
    }
    public static void insertintoappdetails(int b_id,int c_id,int v,String appdate){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select COUNT(*) from appointment_details";
            PreparedStatement st = connection.prepareStatement(command);
            ResultSet res= st.executeQuery();
            int count=0;
            if(res.next()) {
                count = res.getInt("COUNT(*)");
            }
            int app_id=0;
            System.out.println(count);
            if (count==0){
                app_id=4001;
            }
            else if(count>0){
                command="select MAX(app_id) from appointment_details";
                st=connection.prepareStatement(command);
                res=st.executeQuery();
                int m=0;
                if (res.next()) {
                    m = res.getInt("MAX(app_id)");
                }
                app_id=m+1;
            }
            Date date1= java.sql.Date.valueOf(appdate);
            System.out.println(date1.toString());
            command= "insert into appointment_details values(?,?,?,?,?)";
            st= connection.prepareStatement(command);
            st.setInt(1,app_id);
            st.setInt(2,b_id);
            st.setInt(3,c_id);
            st.setInt(4,v);
            st.setDate(5, (java.sql.Date) date1);
            int c =st.executeUpdate();
            System.out.println(c);
            int nov=0;
            if (v==1) {
                command = "select covaxin_count from vaccination_center where c_id=?";
                st = connection.prepareStatement(command);
                st.setInt(1,c_id);
                res=st.executeQuery();
                if(res.next()){
                    nov = res.getInt("covaxin_count");
                }
                System.out.println(nov);
                if(nov>0){
                    nov=nov-1;
                }
                command="update vaccination_center set covaxin_count =? where c_id = ?";
                st=connection.prepareStatement(command);
                st.setInt(1,nov);
                st.setInt(2,c_id);
                c=st.executeUpdate();
                System.out.println(c);
            }
            else if(v==2){
                command = "select covishield_count from vaccination_center where c_id=?";
                st = connection.prepareStatement(command);
                st.setInt(1,c_id);
                res=st.executeQuery();
                if(res.next()){
                    nov = res.getInt("covishield_count");
                }
                System.out.println(nov);
                if(nov>0){
                    nov=nov-1;
                }
                command="update vaccination_center set covishield_count =? where c_id = ?";
                st=connection.prepareStatement(command);
                st.setInt(1,nov);
                st.setInt(2,c_id);
                c=st.executeUpdate();
                System.out.println(c);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public static String getDose2End(String dose1date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	try{
    	   //Setting the date to the given date
    	   c.setTime(sdf.parse(dose1date));
    	}
        catch(Exception e)
        {
    		e.printStackTrace();
    	}
    	   
    	//Number of Days to add
    	//Number of Days to add
    	c.add(Calendar.DAY_OF_MONTH, 115);  
    	//Date after adding the days to the given date
    	String dose2enddate = sdf.format(c.getTime()); 
    	
    	return dose2enddate; 
    }
    public static String getDose2Start(String dose1date)
    {
        System.out.print(dose1date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	try{
    	   //Setting the date to the given date
    	   c.setTime(sdf.parse(dose1date));
    	}catch(Exception e){
    		e.printStackTrace();
    	 }
    	   
    	//Number of Days to add
    	c.add(Calendar.DAY_OF_MONTH, 84);  
    	//Date after adding the days to the given date
    	String dose2startdate = sdf.format(c.getTime()); 
    	
    	return dose2startdate;
    	 
    }
    public static boolean validateDose2Date(String dose2startdate)
    {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            java.util.Date dose2 = sdf.parse(dose2startdate);
            java.util.Date currDate= sdf.parse(currentDate);
            int days_difference = (int) ((dose2.getTime() - currDate.getTime()) / (1000*60*60*24)) % 365; 
            if(days_difference<7)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return false;
    }
}
