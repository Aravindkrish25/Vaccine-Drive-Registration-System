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

public class RegisterBeneficiary {
    public static void main(String args[]) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String myDatabase = "VDRS";
            String url = "jdbc:mysql://" + serverName + "/" + myDatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

            Scanner kb = new Scanner(System.in);
            System.out.println("Enter name : ");
            String b_name = kb.nextLine();
            System.out.println("Enter age : ");
            int b_age = kb.nextInt();
            int b_id = 1;
            System.out.println("Enter proof type : ");
            int proofType = kb.nextInt();
            System.out.println("Enter proof Id : ");
            String proof_id = kb.nextLine();
            proof_id = kb.nextLine();
            System.out.println("Enter gender : ");
            String b_gender = "" + kb.nextLine().charAt(0);
            System.out.println("Enter phone number : ");
            String b_phone = kb.nextLine();
            
            
            int flag = 1;

            String command1 = "select max(b_id) from beneficiary";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();

            if(result.next()) {
                b_id = result.getInt("max(b_id)") + 1;
            }
            System.out.println(b_id);

            if(!validate_name(b_name)){
                flag = 0;
                System.out.println("Invalid Username");
            }

            if(!validate_age(b_age)){
                flag = 0;
                System.out.println("Beneficiary of the given age in not eligible");
            }

            if(!validate_phone(b_phone)){
                flag = 0;
                System.out.println("Invalid Phone number");
            }

            if(!validate_proof(proofType,proof_id)){
                flag = 0;
                System.out.println("Invalid proof ID for the given proof type");
            }
            
            if(noofbeneficiaries(b_phone)>=4)
            {
                flag=0;
                System.out.println("Exceeded number of beneficiaries(>4) for the mobile number");
            }

            if(flag == 1) {
                String command2 = "insert into beneficiary(b_id,b_name,proof_id,proof,b_age,b_gender,b_phone) values(?,?,?,?,?,?,?)";
                st = connection.prepareStatement(command2);
                st.setInt(1,b_id);
                st.setString(2,b_name);
                st.setString(3,proof_id);
                st.setInt(4,proofType);
                st.setInt(5,b_age);
                st.setString(6,b_gender);
                st.setString(7,b_phone);

                int rows = st.executeUpdate();
                System.out.println(rows + " row(s) affected");

            } else {
                System.out.println("Some Data Entered by you is Invalid! Please follow the suggested format.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int noofbeneficiaries(String s)
    {
        int nob=-1;
        try{
            String driverName = "com.mysql.jdbc.Driver";
        
        Class.forName(driverName); // here is the ClassNotFoundException

        String serverName = "localhost:3306";
        String myDatabase = "VDRS";
        String url = "jdbc:mysql://" + serverName + "/" + myDatabase;

        String username = "root";
        String password = "Bh@280801";
        Connection connection = DriverManager.getConnection(url, username, password);
        
        String command1 = "select count(*) from beneficiary where b_phone = ?";
        PreparedStatement st = connection.prepareStatement(command1);
        st.setString(1,s);
        ResultSet result = st.executeQuery();
        
        
        if(result.next()) {
                nob = result.getInt("count(*)");
            }
            System.out.println("NOB:"+nob);
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return nob;
        
    }   

    public static boolean validate_name(String s) {
        int l = s.length();
        if(l == 0 || l > 50) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            if(s.charAt(i) != ' ' && !Character.isAlphabetic(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate_age(int age) {
        if(age < 18) {
            return false;
        }
        return true;
    }
    
     public static boolean digitOnly(String s) {
        int n = s.length();
        for(int i=0;i<n;i++){
            if(!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean alphaOnly(String s) {
        int n = s.length();
        for(int i=0;i<n;i++){
            if(!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate_phone(String phone) {
        return digitOnly(phone) && (phone.length() == 10);
    }

    public static boolean validate_proof(int proof_type,String proof_id){
        int n = proof_id.length();
        boolean flag = false;
        switch(proof_type){
            case 1:{    //Adhaar
                if(n==12 && digitOnly(proof_id)) {
                    flag = true;
                }
                break;
            }
            case 2:{    //PAN
                if(n==10 && alphaOnly(proof_id.substring(0,5)) && (digitOnly(proof_id.substring(5,9))) && Character.isLetter(proof_id.charAt(9))) {
                    flag = true;
                }
                break;
            }
            case 3:{    //Voter ID
                if(n==10&&alphaOnly(proof_id.substring(0,3))&&digitOnly(proof_id.substring(3))) {
                    flag = true;
                }
                break;
            }
            case 4:{    //Driving License
                if(n==16 && alphaOnly(proof_id.substring(0,2)) && digitOnly(proof_id.substring(2,4)) && (proof_id.charAt(4)==' '||Character.isLetter(proof_id.charAt(4))) && digitOnly(proof_id.substring(5))) {
                    flag=true;
                }
                break;
            }
        }
        return flag;
    }
    public static void insert(String b_name,int b_age,String b_phone,int proofType,String proof_id,String b_gender) {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "localhost:3306";
            String myDatabase = "VDRS";
            String url = "jdbc:mysql://" + serverName + "/" + myDatabase;

            String username = "root";
            String password = "Bh@280801";
            Connection connection = DriverManager.getConnection(url, username, password);

                        
            int flag = 1;
            int b_id = 1;
            String command1 = "select max(b_id) from beneficiary";
            PreparedStatement st = connection.prepareStatement(command1);
            ResultSet result = st.executeQuery();

            if(flag == 1) {
                String command2 = "insert into beneficiary(b_id,b_name,proof_id,proof,b_age,b_gender,b_phone) values(?,?,?,?,?,?,?)";
                st = connection.prepareStatement(command2);
                st.setInt(1,b_id);
                st.setString(2,b_name);
                st.setString(3,proof_id);
                st.setInt(4,proofType);
                st.setInt(5,b_age);
                st.setString(6,b_gender);
                st.setString(7,b_phone);

                int rows = st.executeUpdate();
                System.out.println(rows + " row(s) affected");

            } else {
                System.out.println("Some Data Entered by you is Invalid! Please follow the suggested format.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}