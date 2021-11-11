/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

/**
 *
 * @author Hrithik
 */

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;

public class AccountDetails 
{
    public static String[][] user = new String[4][30];
    // 4 users max for 1 mobile number
    // each user record stores :
    // 0 b_id
    // 1 b_name
    // 2 b_age
    // 3 proof(type)
    // 4 proof_id
    // 5 dose1_flag
    // 6 dose2_flag
    // 7 vaccine_type (1: covaxin, 2: covishield)
    // 8 app_flag
    // 9 app_id
    //10 c_id
    //11 app_date
    public static void getusersbyphonenumber(String phone)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");


            String command1 = "select * from beneficiary where b_phone = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(1, phone);
            ResultSet result = st.executeQuery();
            int bid[] = new int[4];
            int j = 0;
            int count=0;
            int r=0,s=0;
            while (result.next()) {
                j=0;
                user[count][j++]= String.valueOf(result.getInt("b_id"));
                user[count][j++]=result.getString("b_name");
                user[count][j++]=String.valueOf(result.getInt("b_age"));
                user[count][j++]=String.valueOf(result.getInt("proof"));
                user[count][j++]=result.getString("proof_id");
                user[count][j++]=String.valueOf(result.getInt("dose1_flag"));
                user[count][j++]=String.valueOf(result.getInt("dose2_flag"));
                user[count][j++]=getvaccinetype(result.getInt("vaccine_type"));
                r=j;
                s=r;
                count++;
            }

            for(int x=0;x<count;x++){
                int b_id=Integer.parseInt(user[x][0]);
                String command2 = "select * from appointment_details where b_id = ?";
                PreparedStatement st1 = connection.prepareStatement(command2);
                st1.setString(1,user[x][0]);
                ResultSet result1 = st1.executeQuery();
                r=s;
                if(result1.next()){
                    
                    user[x][r++]="1"; // app_flag
                    user[x][r++]= String.valueOf(result1.getInt("app_id"));
                    user[x][r++]= getcenternamefromid(result1.getInt("c_id"));
                    user[x][r++]= result1.getDate("app_date").toString();
                }
                else{
                    user[x][r++]="0"; // app_flag
                    user[x][r++]= "0";
                    user[x][r++]= "0";
                    user[x][r++]="0";
                }

            }

            s=s+4;
            System.out.print("S"+s);
            for(int x=0;x<count;x++){
                r=s;
                Class.forName("com.mysql.jdbc.Driver");
                String command = "select * from beneficiary natural join vaccinator where b_id = ?";
                st = connection.prepareStatement(command);
                st.setString(1, user[x][0]);
                result = st.executeQuery();
                if(result.next()){
                     user[x][r++]= String.valueOf(getcenternamefromid(result.getInt("c_id")));
                    if (result.getInt("dose1_flag") == 1)
                        user[x][r++]=result.getDate("dose1_date").toString();
                    else
                        user[x][r++]="0";
                    if(result.getInt("dose2_flag")==1)
                        user[x][r++]=result.getDate("dose2_date").toString();
                    else
                        user[x][r++]="0";
                }
                else{
                    user[x][r++]="0";
                    user[x][r++]="0";
                    user[x][r++]="0";
                }
            }
            s=s+3;
            for (int x=0;x<count;x++){
                for(int y=0;y<s;y++) {
                    System.out.print(user[x][y] + " ");
                }
                System.out.println();
            }
            

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static String getcenternamefromid(int c_id)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select c_name from vaccination_center where c_id = ?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setString(1, String.valueOf(c_id));
            ResultSet result = st.executeQuery();
            if(result.next()){
                return result.getString("c_name");
            }
            else
            {
                return "Noname";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    public static String getvaccinetype(int v){
        if (v==1){
            return "Covaxin";
        }
        else if (v==2){
            return "Covishield";
        }
        else{
            return "Yet to Vaccinate";
        }
    }
    public static void deleterecordsbeforecurrentdate()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "delete from appointment_details where app_date < CURDATE()";
            PreparedStatement st = connection.prepareStatement(command);
            int s= st.executeUpdate();
            System.out.println(s + " Records deleted");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
      //  deleterecordsbeforecurrentdate();
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter phone number : ");
        String phone = kb.nextLine();
        getusersbyphonenumber(phone);

    }
}