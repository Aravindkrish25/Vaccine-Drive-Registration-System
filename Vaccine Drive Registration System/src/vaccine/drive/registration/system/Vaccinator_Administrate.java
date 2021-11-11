/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hrithik
 */
public class Vaccinator_Administrate
{
    public static int appointmentexists(int b_id,int c_id,String curr){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from appointment_details where b_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,b_id);
            ResultSet res = st.executeQuery();
            if(res.next()) {
                 int c = res.getInt("c_id");
                System.out.println(res.getDate("app_date").toString());
                String d=res.getDate("app_date").toString();
                if ((c==c_id) && (d.equals(curr))) {
                    // same place same appointent date
                    System.out.println("Returns 1");
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int getcidfromvid(int v_id)
    {
        int c_id=-1;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command1 = "select c_id from vaccinator where v_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,v_id);
            ResultSet result = st.executeQuery();
            if(result.next())
            {
                c_id=result.getInt("c_id");
            }
            return c_id;
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return c_id;
    }
    public static void administervaccine(int b_id,int v_id,int vaccine_choice){
        int c_id =getcidfromvid(v_id);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from appointment_details where b_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            ResultSet res;
            String curr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if(appointmentexists(b_id,c_id,curr)==1) {
                System.out.println("Appointment Exists");
                try {
                    // store app_id and vaccine type
                    command = "select * from appointment_details where b_id=?";
                    st = connection.prepareStatement(command);
                    st.setInt(1, b_id);
                    res = st.executeQuery();
                    int type=-1;
                    int appid=-1;
                    if (res.next()) {
                        type=res.getInt("app_vaccine_type");
                        appid=res.getInt("app_id");
                    }
                        // delete appointment
                        command = "delete from appointment_details where app_id = ?";
                        System.out.println(appid);
                        st = connection.prepareStatement(command);
                        st.setInt(1,appid);
                        System.out.println("execupdate");
                        int n = st.executeUpdate();
                        System.out.println( n+ " rows deleted in app_details");
                        command = "select * from beneficiary where b_id=?";
                        st = connection.prepareStatement(command);
                        st.setInt(1, b_id);
                        res = st.executeQuery();
                        int d1=-1;
                        int d2=-1;
                        if (res.next()){
                            d1=res.getInt("dose1_flag");
                            d2=res.getInt("dose2_flag");
                        }
                        if ((d1==1)&&(d2==0)) {
                            // second dose administered
                            System.out.println("Second dose of existing appointment");
                            d2=1;
                            Date dose2= java.sql.Date.valueOf(curr);
                            command = "update beneficiary set dose2_flag=?,v_id=?,dose2_date=? where b_id=?";
                            st=connection.prepareStatement(command);
                            st.setInt(1,d2);
                            st.setInt(2,v_id);
                            st.setDate(3, (java.sql.Date) dose2);
                            st.setInt(4,b_id);
                            n=st.executeUpdate();
                            System.out.println("rows updated");
                        }
                        if ((d1==0)&&(d2==0)){
                            // first dose administered
                            System.out.println("First dose with appointment");
                            d1=1;
                            Date dose1= java.sql.Date.valueOf(curr);
                            command = "update beneficiary set dose1_flag=?,v_id=?,dose1_date=?,vaccine_type=? where b_id=?";
                            st=connection.prepareStatement(command);
                            st.setInt(1,d1);
                            st.setInt(2,v_id);
                            st.setDate(3, (java.sql.Date) dose1);
                            st.setInt(4,type);
                            st.setInt(5,b_id);
                            n=st.executeUpdate();
                            System.out.println(n+ "rows updated");
                        }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            }
            else if(appointmentexists(b_id,c_id,curr)==0){
                try{
                // no previous appointment
                    command = "select * from beneficiary where b_id=?";
                    st = connection.prepareStatement(command);
                    st.setInt(1, b_id);
                    res = st.executeQuery();
                    int d1=-1;
                    int d2=-1,n;
                    if (res.next()){
                        d1=res.getInt("dose1_flag");
                        d2=res.getInt("dose2_flag");
                    }
                    if ((d1==1)&&(d2==0)) {
                        // second dose administered
                        System.out.println("Second dose without appointment");
                        d2=1;
                        System.out.println(curr);
                        Date dose2= java.sql.Date.valueOf(curr);
                        command = "update beneficiary set dose2_flag=?,v_id=?,dose2_date=? where b_id=?";
                        st=connection.prepareStatement(command);
                        st.setInt(1,d2);
                        st.setInt(2,v_id);
                        st.setDate(3, (java.sql.Date) dose2);
                        st.setInt(4,b_id);
                        n=st.executeUpdate();
                        System.out.println(n+ "rows updated");
                        if (vaccine_choice==1) {
                            command = "update vaccination_center set covaxin_count = covaxin_count - 1 where c_id = ?";
                            st = connection.prepareStatement(command);
                            st.setInt(1, c_id);
                            st.executeUpdate();
                        }
                        else{
                            command = "update vaccination_center set covishield_count = covishield_count - 1 where c_id = ?";
                            st = connection.prepareStatement(command);
                            st.setInt(1, c_id);
                            st.executeUpdate();
                        }
                    }
                    if ((d1==0)&&(d2==0)){
                        // first dose administered
                        System.out.println("First dose without appointment");
                        d1=1;
                        Date dose1= java.sql.Date.valueOf(curr);
                        command = "update beneficiary set dose1_flag=?,v_id=?,dose1_date=?,vaccine_type=? where b_id=?";
                        st=connection.prepareStatement(command);
                        st.setInt(1,d1);
                        st.setInt(2,v_id);
                        st.setDate(3, (java.sql.Date) dose1);
                        st.setInt(4,vaccine_choice);
                        st.setInt(5,b_id);
                        n=st.executeUpdate();
                        System.out.println(n+ "rows updated");
                        if (vaccine_choice==1) {
                            command = "update vaccination_center set covaxin_count = covaxin_count - 1 where c_id = ?";
                            st = connection.prepareStatement(command);
                            st.setInt(1, c_id);
                            st.executeUpdate();
                        }
                        else{
                            command = "update vaccination_center set covishield_count = covishield_count - 1 where c_id = ?";
                            st = connection.prepareStatement(command);
                            st.setInt(1, c_id);
                            st.executeUpdate();
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Cannot Vaccinate Now, come again later");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static int getcovaxincount(int v_id){
        int c_id=getcidfromvid(v_id);
        int cv=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from vaccination_center where c_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,c_id);
            ResultSet res = st.executeQuery();
            if (res.next()){
                cv=res.getInt("covaxin_count");
                return cv;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cv;
    }
    public static int getcovishieldcount(int v_id){
        int c_id=getcidfromvid(v_id);
        int cs=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from vaccination_center where c_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,c_id);
            ResultSet res = st.executeQuery();
            if (res.next()){
                cs=res.getInt("covishield_count");
                return cs;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cs;
    }
    public static int getcovaxinflag(int v_id){
        int c_id=getcidfromvid(v_id);
        int cv=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from vaccination_center where c_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,c_id);
            ResultSet res = st.executeQuery();
            if (res.next()){
                cv=res.getInt("covaxin_flag");
                return cv;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cv;
    }
    public static int getcovishieldflag(int v_id){
        int c_id=getcidfromvid(v_id);
        int cv=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from vaccination_center where c_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,c_id);
            ResultSet res = st.executeQuery();
            if (res.next()){
                cv=res.getInt("covishield_flag");
                return cv;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cv;
    }
    
    public static void updatevaccinecount(int v_id,int avail1,int avail2,int new1,int new2){
        int c_id=getcidfromvid(v_id);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "update vaccination_center set covaxin_flag=?,covishield_flag=?,covaxin_count=?,covishield_count=? where c_id=?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,avail1);
            st.setInt(2,avail2);
            st.setInt(3,new1);
            st.setInt(4,new2);
            st.setInt(5,c_id);
            int n = st.executeUpdate();
            System.out.println(n+" rows updated in vaccination_center");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static int gettypefromappointment(int b_id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
            String command = "select app_vaccine_type from appointment_details where b_id = ?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,b_id);
            ResultSet result = st.executeQuery();
            if(result.next()){
                return result.getInt("app_vaccine_type");
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int getvaccinetypefromid(int b_id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/vdrs", "root", "Bh@280801");
            String command = "select vaccine_type from beneficiary where b_id = ?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,b_id);
            ResultSet result = st.executeQuery();
            if(result.next()){
                return result.getInt("vaccine_type");
            }
            else{
                return 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
}
