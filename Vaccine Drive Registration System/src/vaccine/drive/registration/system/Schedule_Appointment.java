/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Hrithik
 */
public class Schedule_Appointment extends javax.swing.JFrame {

    /**
     * Creates new form Schedule_Appointment
     */
    public Schedule_Appointment() {
        initComponents();
    }
    boolean freeflag = false;
    boolean costflag = false;
    boolean covishieldflag = false;
    boolean covaxinflag = false;
    
    int tabletype =0;
    public int vaccinatedornot(String dose1,String dose2)
    {
        if (dose1.equals("1") && dose2.equals("1"))
        {
            System.out.println("Vaccinated");
            return 2;
            
        }
        else if(dose1.equals("1") && dose2.equals("0"))
        {
            
            System.out.println("Partially Vaccinated");
            return 1;
        }
        else if(dose1.equals("0") && dose2.equals("0"))
        {
            System.out.println("Not Vaccinated");
            return 0;
        }
        return 0;
    }
    
    public String getproofnamefromproofid(String prooftype)
    {
        String proofname="";
        if(prooftype.equals("1"))
        {
            proofname="Aadhar Card";
        }
        else if(prooftype.equals("2"))
        {
            proofname="PAN Card";
        }
        else if(prooftype.equals("3"))
        {
            proofname="Voter ID";
        }
        else
        {
            proofname="Driving License";
        }
        return proofname;
        
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
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date dose2 = sdf.parse(dose2startdate);
            Date currDate= sdf.parse(currentDate);
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
    public static boolean validateAppointmentDate(Date date)
    {
        Date currentDate = new Date();
        int days_difference = (int) ((date.getTime() - currentDate.getTime()) / (1000*60*60*24)) % 365; 
        
        if(days_difference>0 && days_difference<7)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void user4()
    {
        ImageIcon image ;
        Registered_Beneficiary.jPanel6.setVisible(true);
        int vaccinationstatus = vaccinatedornot(AccountDetails.user[3][5],AccountDetails.user[3][6]);
        if( vaccinationstatus  == 0)
        {
            image = new ImageIcon(getClass().getResource("notvaccinated.png"));
        }
        else if( vaccinationstatus == 1)
        {
            image = new ImageIcon(getClass().getResource("PARTIALLY_VACCINATED.jpg"));
        }
        else
        {
            image = new ImageIcon(getClass().getResource("vaccinated.png"));
        }
        Registered_Beneficiary.vaccinatedornot3.setIcon(image);

        Registered_Beneficiary.jLabel51.setText(AccountDetails.user[3][1]);
        Registered_Beneficiary.jLabel52.setText(AccountDetails.user[3][0]);
        Registered_Beneficiary.jLabel54.setText(AccountDetails.user[3][2]);

        String prooftype = AccountDetails.user[3][3];
        String proofname = getproofnamefromproofid(prooftype);
        Registered_Beneficiary.jLabel56.setText(proofname);

        Registered_Beneficiary.jLabel58.setText(AccountDetails.user[3][4]);
        image = new ImageIcon(getClass().getResource("Dose1_notvaccinated.png"));
        if(AccountDetails.user[3][5].equals("1"))
        {
            image = new ImageIcon(getClass().getResource("Dose1vaccinated.png"));
            Registered_Beneficiary.jLabel61.setIcon(image);
            String vaccinetype="Covaxin";
            if(AccountDetails.user[3][7].equals("2"))
            {
                vaccinetype = "Covishield"; 
            }
            Registered_Beneficiary.jLabel60.setText(vaccinetype);
            Registered_Beneficiary.jLabel63.setForeground(new Color(51,204,0));
            Registered_Beneficiary.jLabel63.setText("Appointment Details Not Available");

            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            if(AccountDetails.user[3][6].equals("1"))
            {
                image = new ImageIcon(getClass().getResource("Dose2vaccinated.png"));
                Registered_Beneficiary.jLabel62.setIcon(image);
                Registered_Beneficiary.jLabel64.setForeground(new Color(51,204,0));
                Registered_Beneficiary.jLabel64.setText("Appointment Details Not Available");
                Registered_Beneficiary.jLabel59.setVisible(false);//schedule

            }
            else
            {
                Registered_Beneficiary.jLabel62.setIcon(image);
                String app_details ="";
                if(AccountDetails.user[3][8].equals("0"))
                {
                    Registered_Beneficiary.jLabel64.setForeground(new Color(255,0,0));
                    String dose1date = AccountDetails.user[3][13];
                    String startdate = getDose2Start(dose1date);
                    String enddate = getDose2End(dose1date);
                    app_details = "Start Date:  " + startdate +"  End Date: "+enddate;
                }
                else if(AccountDetails.user[3][8].equals("1"))
                {
                    Registered_Beneficiary.jLabel64.setForeground(new Color(255,0,0));
                    app_details="Appointment on ";
                    app_details = app_details+ AccountDetails.user[3][11] + " @ " + AccountDetails.user[3][10];
                    Registered_Beneficiary.jLabel59.setVisible(false);
                }
                Registered_Beneficiary.jLabel64.setText(app_details);
                String dose1date = AccountDetails.user[3][13];
                String startdate = getDose2Start(dose1date);
                if(!(validateDose2Date(startdate)))
                {
                    Registered_Beneficiary.jLabel59.setVisible(false);
                }
            }

        }
        else
        {
            Registered_Beneficiary.jLabel61.setIcon(image);
            Registered_Beneficiary.jLabel65.setVisible(false);
            Registered_Beneficiary.jLabel60.setVisible(false);

            String app_details ="Not Yet Scheduled";
            if(AccountDetails.user[3][8].equals("1"))
            {
                Registered_Beneficiary.jLabel63.setForeground(new Color(255,0,0));
                app_details="Appointment on ";
                app_details = app_details+ AccountDetails.user[3][11] + " @ " + AccountDetails.user[3][10];
                Registered_Beneficiary.jLabel59.setVisible(false);
             }
            Registered_Beneficiary.jLabel63.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel63.setText(app_details); 
            Registered_Beneficiary.jLabel64.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel64.setText("Not Yet Scheduled"); 
            
            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            Registered_Beneficiary.jLabel62.setIcon(image);
        }
        
    }
    
    public void user3()
    {
        System.out.print("User 3");
        ImageIcon image ;
        Registered_Beneficiary.jPanel5.setVisible(true);
        int vaccinationstatus = vaccinatedornot(AccountDetails.user[2][5],AccountDetails.user[2][6]);
        if( vaccinationstatus  == 0)
        {
            image = new ImageIcon(getClass().getResource("notvaccinated.png"));
        }
        else if( vaccinationstatus == 1)
        {
            image = new ImageIcon(getClass().getResource("PARTIALLY_VACCINATED.jpg"));
        }
        else
        {
            image = new ImageIcon(getClass().getResource("vaccinated.png"));
        }
        Registered_Beneficiary.vaccinatedornot4.setIcon(image);

        Registered_Beneficiary.jLabel80.setText(AccountDetails.user[2][1]);
        Registered_Beneficiary.jLabel79.setText(AccountDetails.user[2][0]);
        Registered_Beneficiary.jLabel75.setText(AccountDetails.user[2][2]);

        String prooftype = AccountDetails.user[2][3];
        String proofname = getproofnamefromproofid(prooftype);
        Registered_Beneficiary.jLabel78.setText(proofname);

        Registered_Beneficiary.jLabel68.setText(AccountDetails.user[2][4]);
        image = new ImageIcon(getClass().getResource("Dose1_notvaccinated.png"));
        if(AccountDetails.user[2][5].equals("1"))
        {
            image = new ImageIcon(getClass().getResource("Dose1vaccinated.png"));
            Registered_Beneficiary.jLabel73.setIcon(image);
            String vaccinetype="Covaxin";
            if(AccountDetails.user[2][7].equals("2"))
            {
                vaccinetype = "Covishield"; 
            }
            Registered_Beneficiary.jLabel74.setText(vaccinetype);
            Registered_Beneficiary.jLabel70.setForeground(new Color(51,204,0));
            Registered_Beneficiary.jLabel70.setText("Appointment Details Not Available");

            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            if(AccountDetails.user[2][6].equals("1"))
            {
                image = new ImageIcon(getClass().getResource("Dose2vaccinated.png"));
                Registered_Beneficiary.jLabel72.setIcon(image);
                Registered_Beneficiary.jLabel71.setForeground(new Color(51,204,0));
                Registered_Beneficiary.jLabel71.setText("Appointment Details Not Available");
                Registered_Beneficiary.jLabel67.setVisible(false);//schedule

            }
            else
            {
                Registered_Beneficiary.jLabel72.setIcon(image);
                String app_details ="";
                if(AccountDetails.user[2][8].equals("0"))
                {
                    Registered_Beneficiary.jLabel71.setForeground(new Color(255,0,0));
                    String dose1date = AccountDetails.user[2][13];
                    String startdate = getDose2Start(dose1date);
                    String enddate = getDose2End(dose1date);
                    app_details = "Start Date:  " + startdate +"  End Date: "+enddate;
                }
                else if(AccountDetails.user[2][8].equals("1"))
                {
                    Registered_Beneficiary.jLabel71.setForeground(new Color(255,0,0));
                    app_details="Appointment on ";
                    app_details = app_details+ AccountDetails.user[2][11] + " @ " + AccountDetails.user[2][10];
                    Registered_Beneficiary.jLabel67.setVisible(false);
                }
                Registered_Beneficiary.jLabel71.setText(app_details);
                String dose1date = AccountDetails.user[2][13];
                String startdate = getDose2Start(dose1date);
                if(!(validateDose2Date(startdate)))
                {
                    Registered_Beneficiary.jLabel67.setVisible(false);
                }
            }

        }
        else
        {
            Registered_Beneficiary.jLabel73.setIcon(image);
            Registered_Beneficiary.jLabel66.setVisible(false);
            Registered_Beneficiary.jLabel74.setVisible(false);

            String app_details ="Not Yet Scheduled";
            if(AccountDetails.user[2][8].equals("1"))
            {
                Registered_Beneficiary.jLabel70.setForeground(new Color(255,0,0));
                app_details="Appointment on ";
                app_details = app_details+ AccountDetails.user[2][11] + " @ " + AccountDetails.user[2][10];
                Registered_Beneficiary.jLabel67.setVisible(false);
             }
            Registered_Beneficiary.jLabel70.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel70.setText(app_details); 
            Registered_Beneficiary.jLabel71.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel71.setText("Not Yet Scheduled"); 
            
            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            Registered_Beneficiary.jLabel72.setIcon(image);
        }
    }
    
    public void user2()
    {
        ImageIcon image ;
        Registered_Beneficiary.jPanel4.setVisible(true);
        int vaccinationstatus = vaccinatedornot(AccountDetails.user[1][5],AccountDetails.user[1][6]);
        if( vaccinationstatus  == 0)
        {
            image = new ImageIcon(getClass().getResource("notvaccinated.png"));
        }
        else if( vaccinationstatus == 1)
        {
            image = new ImageIcon(getClass().getResource("PARTIALLY_VACCINATED.jpg"));
        }
        else
        {
            image = new ImageIcon(getClass().getResource("vaccinated.png"));
        }
        Registered_Beneficiary.vaccinatedornot6.setIcon(image);

        Registered_Beneficiary.jLabel110.setText(AccountDetails.user[1][1]);
        Registered_Beneficiary.jLabel109.setText(AccountDetails.user[1][0]);
        Registered_Beneficiary.jLabel105.setText(AccountDetails.user[1][2]);

        String prooftype = AccountDetails.user[1][3];
        String proofname = getproofnamefromproofid(prooftype);
        Registered_Beneficiary.jLabel108.setText(proofname);

        Registered_Beneficiary.jLabel98.setText(AccountDetails.user[1][4]);
        image = new ImageIcon(getClass().getResource("Dose1_notvaccinated.png"));
        if(AccountDetails.user[1][5].equals("1"))
        {
            image = new ImageIcon(getClass().getResource("Dose1vaccinated.png"));
            Registered_Beneficiary.jLabel103.setIcon(image);
            String vaccinetype="Covaxin";
            if(AccountDetails.user[1][7].equals("2"))
            {
                vaccinetype = "Covishield"; 
            }
            Registered_Beneficiary.jLabel104.setText(AccountDetails.user[1][7]);
            Registered_Beneficiary.jLabel100.setForeground(new Color(51,204,0));
            Registered_Beneficiary.jLabel100.setText("Appointment Details Not Available");

            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            if(AccountDetails.user[1][6].equals("1"))
            {
                image = new ImageIcon(getClass().getResource("Dose2vaccinated.png"));
                Registered_Beneficiary.jLabel102.setIcon(image);
                Registered_Beneficiary.jLabel101.setForeground(new Color(51,204,0));
                Registered_Beneficiary.jLabel101.setText("Appointment Details Not Available");
                Registered_Beneficiary.jLabel97.setVisible(false);//schedule

            }
            else
            {
                Registered_Beneficiary.jLabel102.setIcon(image);
                String app_details ="";
                if(AccountDetails.user[1][8].equals("0"))
                {
                    Registered_Beneficiary.jLabel101.setForeground(new Color(255,0,0));
                    String dose1date = AccountDetails.user[1][13];
                    String startdate = getDose2Start(dose1date);
                    String enddate = getDose2End(dose1date);
                    app_details = "Start Date:  " + startdate +"  End Date: "+enddate;
                }
                else if(AccountDetails.user[1][8].equals("1"))
                {
                    Registered_Beneficiary.jLabel101.setForeground(new Color(255,0,0));
                    app_details="Appointment on ";
                    app_details = app_details+ AccountDetails.user[1][11] + " @ " + AccountDetails.user[1][10];
                    Registered_Beneficiary.jLabel97.setVisible(false);
                }
                Registered_Beneficiary.jLabel101.setText(app_details);
                String dose1date = AccountDetails.user[1][13];
                String startdate = getDose2Start(dose1date);
                if(!(validateDose2Date(startdate)))
                {
                    Registered_Beneficiary.jLabel97.setVisible(false);
                }
            }

        }
        else
        {
            Registered_Beneficiary.jLabel103.setIcon(image);
            Registered_Beneficiary.jLabel96.setVisible(false);
            Registered_Beneficiary.jLabel104.setVisible(false);

            String app_details ="Not Yet Scheduled";
            if(AccountDetails.user[1][8].equals("1"))
            {
                Registered_Beneficiary.jLabel100.setForeground(new Color(255,0,0));
                app_details="Appointment on ";
                app_details = app_details+ AccountDetails.user[1][11] + " @ " + AccountDetails.user[1][10];
                Registered_Beneficiary.jLabel97.setVisible(false);
             }
            Registered_Beneficiary.jLabel100.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel100.setText(app_details); 
            Registered_Beneficiary.jLabel101.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel101.setText("Not Yet Scheduled"); 
            
            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            Registered_Beneficiary.jLabel102.setIcon(image);
        }
    }
     public void user1()
    {
        ImageIcon image ;
        Registered_Beneficiary.jPanel3.setVisible(true);
        int vaccinationstatus = vaccinatedornot(AccountDetails.user[0][5],AccountDetails.user[0][6]);
        if( vaccinationstatus  == 0)
        {
            image = new ImageIcon(getClass().getResource("notvaccinated.png"));
        }
        else if( vaccinationstatus == 1)
        {
            image = new ImageIcon(getClass().getResource("PARTIALLY_VACCINATED.jpg"));
        }
        else
        {
            image = new ImageIcon(getClass().getResource("vaccinated.png"));
        }
        Registered_Beneficiary.vaccinatedornot5.setIcon(image);

        Registered_Beneficiary.jLabel95.setText(AccountDetails.user[0][1]);
        Registered_Beneficiary.jLabel94.setText(AccountDetails.user[0][0]);
        Registered_Beneficiary.jLabel90.setText(AccountDetails.user[0][2]);

        String prooftype = AccountDetails.user[0][3];
        String proofname = getproofnamefromproofid(prooftype);
        Registered_Beneficiary.jLabel93.setText(proofname);

        Registered_Beneficiary.jLabel83.setText(AccountDetails.user[0][4]);
        image = new ImageIcon(getClass().getResource("Dose1_notvaccinated.png"));
        if(AccountDetails.user[0][5].equals("1"))
        {
            image = new ImageIcon(getClass().getResource("Dose1vaccinated.png"));
            Registered_Beneficiary.jLabel88.setIcon(image);
            String vaccinetype="Covaxin";
            if(AccountDetails.user[0][7].equals("2"))
            {
                vaccinetype = "Covishield"; 
            }
            Registered_Beneficiary.jLabel89.setText(vaccinetype);
            Registered_Beneficiary.jLabel85.setForeground(new Color(51,204,0));
            Registered_Beneficiary.jLabel85.setText("Appointment Details Not Available");

            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            if(AccountDetails.user[0][6].equals("1"))
            {
                image = new ImageIcon(getClass().getResource("Dose2vaccinated.png"));
                Registered_Beneficiary.jLabel87.setIcon(image);
                Registered_Beneficiary.jLabel86.setForeground(new Color(51,204,0));
                Registered_Beneficiary.jLabel86.setText("Appointment Details Not Available");
                Registered_Beneficiary.jLabel82.setVisible(false);//schedule

            }
            else
            {
                Registered_Beneficiary.jLabel87.setIcon(image);
                String app_details ="";
                if(AccountDetails.user[0][8].equals("0"))
                {
                    Registered_Beneficiary.jLabel86.setForeground(new Color(255,0,0));
                    String dose1date = AccountDetails.user[0][13];
                    String startdate = getDose2Start(dose1date);
                    String enddate = getDose2End(dose1date);
                    app_details = "Start Date:  " + startdate +"  End Date: "+enddate;
                }
                else if(AccountDetails.user[0][8].equals("1"))
                {
                    Registered_Beneficiary.jLabel86.setForeground(new Color(255,0,0));
                    app_details="Appointment on ";
                    app_details = app_details+ AccountDetails.user[0][11] + " @ " + AccountDetails.user[0][10];
                    Registered_Beneficiary.jLabel82.setVisible(false);
                }
                Registered_Beneficiary.jLabel86.setText(app_details);
                String dose1date = AccountDetails.user[0][13];
                String startdate = getDose2Start(dose1date);
                if(!(validateDose2Date(startdate)))
                {
                    Registered_Beneficiary.jLabel82.setVisible(false);
                }
            }

        }
        else
        {
            Registered_Beneficiary.jLabel88.setIcon(image);
            Registered_Beneficiary.jLabel81.setVisible(false);
            Registered_Beneficiary.jLabel89.setVisible(false);

            String app_details ="Not Yet Scheduled";
            if(AccountDetails.user[0][8].equals("1"))
            {
                Registered_Beneficiary.jLabel85.setForeground(new Color(255,0,0));
                app_details="Appointment on ";
                app_details = app_details+ AccountDetails.user[0][11] + " @ " + AccountDetails.user[0][10];
                Registered_Beneficiary.jLabel82.setVisible(false);
             }
            Registered_Beneficiary.jLabel85.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel85.setText(app_details); 
            Registered_Beneficiary.jLabel86.setForeground(new Color(255,0,0));
            Registered_Beneficiary.jLabel86.setText("Not Yet Scheduled"); 
            
            image = new ImageIcon(getClass().getResource("Dose2_notvaccinated.png"));
            Registered_Beneficiary.jLabel87.setIcon(image);
        }
    }
     
     public static String convertdateformat(Date d)
     {
        String date = new SimpleDateFormat("yyyy/MM/dd").format(d);
        return date;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jComboBox1 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton1.setVisible(false);
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton2.setVisible(false);
        jButton2 = new javax.swing.JButton();
        jButton2.setVisible(false);
        jScrollPane1 = new javax.swing.JScrollPane();

        jTable1 = new javax.swing.JTable();

        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(963, 832));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/VDRS Logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel21.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 153));
        jLabel21.setText("< BACK");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/bookappointment.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Enter Your PIN");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Select Your District");

        jButton1.setBackground(new java.awt.Color(29, 49, 171));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField4.setBorder(null);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(29, 49, 171));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Your District", "Coimbatore", "Chennai", "Cuddalore", "Erode", "Madurai", "Salem", "Tiruppur", "Trichy", "Krishnagiri", "Dindigul", " " }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Preferred Appointment Date");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/filterby.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/cost.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/vaccine.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/free_ns.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/paid_ns.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covaxin_ns.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covishield_ns.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(20, 49, 171));
        jRadioButton1.setText("COVAXIN");

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(20, 49, 171));
        jRadioButton2.setText("COVISHIELD");

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setForeground(new java.awt.Color(20, 49, 171));
        jButton2.setText("Schedule Appointment");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setForeground(new java.awt.Color(20, 49, 171));

        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTable1.setFont(new java.awt.Font("Trebuchet MS", 0, 20)); // NOI18N
        jTable1.setForeground(new java.awt.Color(20, 49, 171));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Results Available"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(20, 49, 171));
        jTable1.setRowHeight(40);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 40, 78));
        jLabel13.setText("DOSE 1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jButton1))
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addComponent(jLabel8))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try
        {
            Date date = jDateChooser1.getDate();        
            jRadioButton1.setVisible(false);
            jRadioButton2.setVisible(false);
            jButton2.setVisible(false);

            System.out.print(date.toString());
            freeflag = false;
            costflag = false;
            covishieldflag = false;
            covaxinflag = false;

            ImageIcon image= new ImageIcon(getClass().getResource("free_ns.png"));
            jLabel9.setIcon(image);

            image= new ImageIcon(getClass().getResource("paid_ns.png"));
            jLabel10.setIcon(image);

            image= new ImageIcon(getClass().getResource("covaxin_ns.png"));
            jLabel11.setIcon(image);

            image= new ImageIcon(getClass().getResource("covishield_ns.png"));
            jLabel12.setIcon(image);

            String pincode = jTextField4.getText();
            String district = (String)jComboBox1.getSelectedItem();
            if(jComboBox1.getSelectedIndex()==0)
            {
                district="";
            }

            if( ( jComboBox1.getSelectedIndex()==0 && pincode.equals("") )  || !validateAppointmentDate(date))
            {
                if(jComboBox1.getSelectedIndex()==0 && pincode.equals("") )
                {
                    JOptionPane.showMessageDialog(this, "Please Enter Either District or Pincode !!!");
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Please Select a Valid Appointment Date");
                }

            }
            else
            { 
                int c = ScheduleAppointment.getCenters(pincode,district);
                if(c>0)
                {
                    DefaultTableModel model = new DefaultTableModel();
                    tabletype=1;
                    model.addColumn("Name");
                    model.addColumn("Address");
                    model.addColumn("Phone");
                    model.addColumn("CovaxinCount");
                    model.addColumn("CovishieldCount");
                    model.addColumn("Price");
                    model.addColumn("Crowdness");

                    int rows=model.getRowCount();
                    if(rows>0)
                    {
                        for(int i=0;i<rows;i++)
                        {
                            model.removeRow(0);
                        }
                    }
                    String[][] sample = new String[c][4];
                    String m_date = convertdateformat(date);
                    System.out.print(m_date);
                    for(int i = 0;i < c;i++)
                    {
                        String[] temp =  { ScheduleAppointment.center[i][0],ScheduleAppointment.center[i][5],ScheduleAppointment.center[i][4],m_date };
                        sample[i] = temp;
                    }
                    
                    Predictcrowd.writeInput(sample,c);
                    System.out.print("Prediction Started");
                    ArrayList<Float> crowd=Predictcrowd.getcrowdness();
                    System.out.print("Predicted Successfully");
                    String Price = "";
                    String crowdcat="";
                    for(int i=0;i<c;i++)
                    {
                        crowdcat = "Low";
                        if(0.6<crowd.get(i))
                        {
                            crowdcat="High";
                        }
                        else if(0.35<crowd.get(i))
                        {
                            crowdcat="Moderate";
                        }
                       
                        if(Integer.parseInt(ScheduleAppointment.center[i][7]) == 0 )
                        {
                            Price = "Free";
                        }
                        else
                        {
                            Price = "";
                            Price = Price.concat(ScheduleAppointment.center[i][7]).concat("/").concat(ScheduleAppointment.center[i][8]);
                        }
                      model.addRow(new Object[] {ScheduleAppointment.center[i][1] ,ScheduleAppointment.center[i][2] ,ScheduleAppointment.center[i][3] ,ScheduleAppointment.center[i][4] ,ScheduleAppointment.center[i][5], Price,crowdcat});
                    }
                    jTable1.setModel(model);

                }
                else
                {
                    JOptionPane.showMessageDialog(this, "No results Matched!!");
                     DefaultTableModel model = new DefaultTableModel();
                     model.addColumn("No results Available");
                     jTable1.setModel(model);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please choose an Appointment date");
        }
             
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if (!Character.isDigit(c) || jTextField4.getText().length()==10)
        {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
         jRadioButton1.setVisible(false);
        jRadioButton2.setVisible(false);
        jButton2.setVisible(false);
        
        if(freeflag)
        {
            freeflag=false;
            ImageIcon image= new ImageIcon(getClass().getResource("free_ns.png"));
            jLabel9.setIcon(image);
        }
        else
        {
            freeflag=true;
            ImageIcon image= new ImageIcon(getClass().getResource("free_s.png"));
            jLabel9.setIcon(image);  
        }
        
        
        String pincode = jTextField4.getText();
        String district = (String)jComboBox1.getSelectedItem();
        if(jComboBox1.getSelectedIndex()==0)
        {
            district="";
        }
        
        if( ( jComboBox1.getSelectedIndex()==0 && pincode.equals("") )  || !validateAppointmentDate(jDateChooser1.getDate()))
        {
            if(jComboBox1.getSelectedIndex()==0 && pincode.equals("") )
            {
                JOptionPane.showMessageDialog(this, "Please Enter Either District or Pincode !!!");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Select a Valid Appointment Date");
            }
             
        }
        else
        { 
            String Price="";
            ArrayList column = new ArrayList<Object>();
            ArrayList value = new ArrayList<Object>();
            int c = ScheduleAppointment.filtercenters(pincode, district, freeflag, costflag, covaxinflag, covishieldflag);
            if(c>0)
            {
                int col_c=0;
                if(covishieldflag ^ covaxinflag)
                {
                    if(covaxinflag)
                    {
                        column.add("Covaxincount");
                        value.add(4);
                        tabletype=2;
                    }
                    else
                    {
                        column.add("Covishieldcount");
                        value.add(5);
                        tabletype=2;
                    }
                    col_c++;
                }
                else
                {
                    column.add("Covaxincount");
                    column.add("Covishieldcount");
                    value.add(4);
                    value.add(5);
                    col_c++;
                    col_c++;
                    tabletype=1;
                }

                 if(costflag ^ freeflag && costflag)
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }
                else if(!(freeflag ^ costflag))
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }


                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Address");
                model.addColumn("Phone");
                System.out.println("Col:"+col_c);
                for(int i=0;i<col_c;i++)
                {
                    model.addColumn(column.get(i));
                }
                model.addColumn("Crowdness");

                System.out.println("Col:"+col_c);
                String[][] sample = new String[c][4];
                Date date = jDateChooser1.getDate();  
                String m_date = convertdateformat(date);
                for(int i = 0;i < c;i++)
                {
                    String[] temp =  { ScheduleAppointment.center[i][0],ScheduleAppointment.center[i][5],ScheduleAppointment.center[i][4],m_date };
                    sample[i] = temp;
                }
                Predictcrowd.writeInput(sample,c);
                ArrayList<Float> crowd=Predictcrowd.getcrowdness();
                Object[] rowdata = new Object[col_c+4]; 
                for(int i=0;i<c;i++)
                {

                    int k=0;
                    rowdata[k++] = ScheduleAppointment.center[i][1] ;
                    rowdata[k++] = ScheduleAppointment.center[i][2] ;
                    rowdata[k++] = ScheduleAppointment.center[i][3] ;
                    String crowdcat;
                    for(int j=0;j<col_c;j++)
                    {
                        int index = (Integer) value.get(j);                       
                        if(index==7)
                        {
                             if(Integer.parseInt(ScheduleAppointment.center[i][7]) == 0 )
                            {
                                Price = "Free";
                            }
                            else
                            {
                                Price = "";
                                if(covaxinflag == covishieldflag)
                                {
                                    Price = Price.concat(ScheduleAppointment.center[i][7]).concat("/").concat(ScheduleAppointment.center[i][8]);
                                }
                                else
                                {
                                    if(covaxinflag)
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][7]);
                                    }
                                    else
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][8]);
                                    }
                                }

                            }
                             rowdata[k++] = Price;
                        }
                        else
                        {
                            rowdata[k++] = ScheduleAppointment.center[i][index];
                        }  
                    }
                    crowdcat = "Low";
                    if(0.6<crowd.get(i))
                    {
                        crowdcat="High";
                    }
                    else if(0.35<crowd.get(i))
                    {
                        crowdcat="Moderate";
                    }
                    rowdata[k++] = crowdcat;
                    model.addRow(rowdata);
                }
                jTable1.setModel(model);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No results Matched!!");
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No results Available");
                jTable1.setModel(model);
            }
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
         jRadioButton1.setVisible(false);
        jRadioButton2.setVisible(false);
        jButton2.setVisible(false);
        if(costflag)
        {
            costflag=false;
            ImageIcon image= new ImageIcon(getClass().getResource("paid_ns.png"));
            jLabel10.setIcon(image);
        }
        else
        {
            costflag=true;
            ImageIcon image= new ImageIcon(getClass().getResource("paid_s.png"));
            jLabel10.setIcon(image);
            
        }
        
        
        String pincode = jTextField4.getText();
        String district = (String)jComboBox1.getSelectedItem();
        if(jComboBox1.getSelectedIndex()==0)
        {
            district="";
        }
        
        if( ( jComboBox1.getSelectedIndex()==0 && pincode.equals("") )  || !validateAppointmentDate(jDateChooser1.getDate()))
        {
            if(jComboBox1.getSelectedIndex()==0 && pincode.equals("") )
            {
                JOptionPane.showMessageDialog(this, "Please Enter Either District or Pincode !!!");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Select a Valid Appointment Date");
            }
             
        }
        else
        { 
            String Price="";
            ArrayList column = new ArrayList<Object>();
            ArrayList value = new ArrayList<Object>();
            int c = ScheduleAppointment.filtercenters(pincode, district, freeflag, costflag, covaxinflag, covishieldflag);
            if(c>0)
            {
                int col_c=0;
                if(covishieldflag ^ covaxinflag)
                {
                    if(covaxinflag)
                    {
                        column.add("Covaxincount");
                        value.add(4);
                        tabletype=2;
                    }
                    else
                    {
                        column.add("Covishieldcount");
                        value.add(5);
                        tabletype=2;
                    }
                    col_c++;
                }
                else
                {
                    column.add("Covaxincount");
                    column.add("Covishieldcount");
                    value.add(4);
                    value.add(5);
                    col_c++;
                    col_c++;
                    tabletype=1;
                }

                 if(costflag ^ freeflag && costflag)
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }
                else if(!(freeflag ^ costflag))
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }


                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Address");
                model.addColumn("Phone");
                System.out.println("Col:"+col_c);
                for(int i=0;i<col_c;i++)
                {
                    model.addColumn(column.get(i));
                }
                model.addColumn("Crowdness");

                System.out.println("Col:"+col_c);
                String[][] sample = new String[c][4];
                Date date = jDateChooser1.getDate();  
                String m_date = convertdateformat(date);
                for(int i = 0;i < c;i++)
                {
                    String[] temp =  { ScheduleAppointment.center[i][0],ScheduleAppointment.center[i][5],ScheduleAppointment.center[i][4],m_date };
                    sample[i] = temp;
                }
                Predictcrowd.writeInput(sample,c);
                ArrayList<Float> crowd=Predictcrowd.getcrowdness();
                Object[] rowdata = new Object[col_c+4]; 
                for(int i=0;i<c;i++)
                {

                    int k=0;
                    rowdata[k++] = ScheduleAppointment.center[i][1] ;
                    rowdata[k++] = ScheduleAppointment.center[i][2] ;
                    rowdata[k++] = ScheduleAppointment.center[i][3] ;
                    String crowdcat;
                    for(int j=0;j<col_c;j++)
                    {
                        int index = (Integer) value.get(j);                       
                        if(index==7)
                        {
                             if(Integer.parseInt(ScheduleAppointment.center[i][7]) == 0 )
                            {
                                Price = "Free";
                            }
                            else
                            {
                                Price = "";
                                if(covaxinflag == covishieldflag)
                                {
                                    Price = Price.concat(ScheduleAppointment.center[i][7]).concat("/").concat(ScheduleAppointment.center[i][8]);
                                }
                                else
                                {
                                    if(covaxinflag)
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][7]);
                                    }
                                    else
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][8]);
                                    }
                                }

                            }
                             rowdata[k++] = Price;
                        }
                        else
                        {
                            rowdata[k++] = ScheduleAppointment.center[i][index];
                        }  
                    }
                    crowdcat = "Low";
                    if(0.6<crowd.get(i))
                    {
                        crowdcat="High";
                    }
                    else if(0.35<crowd.get(i))
                    {
                        crowdcat="Moderate";
                    }
                    rowdata[k++] = crowdcat;
                    model.addRow(rowdata);
                }
                jTable1.setModel(model);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No results Matched!!");
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No results Available");
                jTable1.setModel(model);
            }
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
         jRadioButton1.setVisible(false);
        jRadioButton2.setVisible(false);
        jButton2.setVisible(false);
        
        if(covaxinflag)
        {
            covaxinflag=false;
            ImageIcon image= new ImageIcon(getClass().getResource("covaxin_ns.png"));
            jLabel11.setIcon(image);
        }
        else
        {
            covaxinflag=true;
            ImageIcon image= new ImageIcon(getClass().getResource("covaxin_s.png"));
            jLabel11.setIcon(image);
            
        }
        
         String pincode = jTextField4.getText();
        String district = (String)jComboBox1.getSelectedItem();
        if(jComboBox1.getSelectedIndex()==0)
        {
            district="";
        }
        
        if( ( jComboBox1.getSelectedIndex()==0 && pincode.equals("") )  || !validateAppointmentDate(jDateChooser1.getDate()))
        {
            if(jComboBox1.getSelectedIndex()==0 && pincode.equals("") )
            {
                JOptionPane.showMessageDialog(this, "Please Enter Either District or Pincode !!!");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Select a Valid Appointment Date");
            }
             
        }
        else
        { 
            String Price="";
            ArrayList column = new ArrayList<Object>();
            ArrayList value = new ArrayList<Object>();
            int c = ScheduleAppointment.filtercenters(pincode, district, freeflag, costflag, covaxinflag, covishieldflag);
            if(c>0)
            {
                int col_c=0;
                if(covishieldflag ^ covaxinflag)
                {
                    if(covaxinflag)
                    {
                        column.add("Covaxincount");
                        value.add(4);
                        tabletype=2;
                    }
                    else
                    {
                        column.add("Covishieldcount");
                        value.add(5);
                        tabletype=2;
                    }
                    col_c++;
                }
                else
                {
                    column.add("Covaxincount");
                    column.add("Covishieldcount");
                    value.add(4);
                    value.add(5);
                    col_c++;
                    col_c++;
                    tabletype=1;
                }

                 if(costflag ^ freeflag && costflag)
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }
                else if(!(freeflag ^ costflag))
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }


                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Address");
                model.addColumn("Phone");
                System.out.println("Col:"+col_c);
                for(int i=0;i<col_c;i++)
                {
                    model.addColumn(column.get(i));
                }
                model.addColumn("Crowdness");

                System.out.println("Col:"+col_c);
                String[][] sample = new String[c][4];
                Date date = jDateChooser1.getDate();  
                String m_date = convertdateformat(date);
                for(int i = 0;i < c;i++)
                {
                    String[] temp =  { ScheduleAppointment.center[i][0],ScheduleAppointment.center[i][5],ScheduleAppointment.center[i][4],m_date };
                    sample[i] = temp;
                }
                Predictcrowd.writeInput(sample,c);
                ArrayList<Float> crowd=Predictcrowd.getcrowdness();
                Object[] rowdata = new Object[col_c+4]; 
                for(int i=0;i<c;i++)
                {

                    int k=0;
                    rowdata[k++] = ScheduleAppointment.center[i][1] ;
                    rowdata[k++] = ScheduleAppointment.center[i][2] ;
                    rowdata[k++] = ScheduleAppointment.center[i][3] ;
                    String crowdcat;
                    for(int j=0;j<col_c;j++)
                    {
                        int index = (Integer) value.get(j);                       
                        if(index==7)
                        {
                             if(Integer.parseInt(ScheduleAppointment.center[i][7]) == 0 )
                            {
                                Price = "Free";
                            }
                            else
                            {
                                Price = "";
                                if(covaxinflag == covishieldflag)
                                {
                                    Price = Price.concat(ScheduleAppointment.center[i][7]).concat("/").concat(ScheduleAppointment.center[i][8]);
                                }
                                else
                                {
                                    if(covaxinflag)
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][7]);
                                    }
                                    else
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][8]);
                                    }
                                }

                            }
                             rowdata[k++] = Price;
                        }
                        else
                        {
                            rowdata[k++] = ScheduleAppointment.center[i][index];
                        }  
                    }
                    crowdcat = "Low";
                    if(0.6<crowd.get(i))
                    {
                        crowdcat="High";
                    }
                    else if(0.35<crowd.get(i))
                    {
                        crowdcat="Moderate";
                    }
                    rowdata[k++] = crowdcat;
                    model.addRow(rowdata);
                }
                jTable1.setModel(model);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No results Matched!!");
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No results Available");
                jTable1.setModel(model);
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
       jRadioButton1.setVisible(false);
        jRadioButton2.setVisible(false);
        jButton2.setVisible(false);
        
        if(covishieldflag)
        {
            covishieldflag=false;
            ImageIcon image= new ImageIcon(getClass().getResource("covishield_ns.png"));
            jLabel12.setIcon(image);
        }
        else
        {
            covishieldflag=true;
            ImageIcon image= new ImageIcon(getClass().getResource("covishield_s.png"));
            jLabel12.setIcon(image);
        }
        
         String pincode = jTextField4.getText();
        String district = (String)jComboBox1.getSelectedItem();
        if(jComboBox1.getSelectedIndex()==0)
        {
            district="";
        }
        
        if( ( jComboBox1.getSelectedIndex()==0 && pincode.equals("") )  || !validateAppointmentDate(jDateChooser1.getDate()))
        {
            if(jComboBox1.getSelectedIndex()==0 && pincode.equals("") )
            {
                JOptionPane.showMessageDialog(this, "Please Enter Either District or Pincode !!!");
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please Select a Valid Appointment Date");
            }
             
        }
        else
        { 
            String Price="";
            ArrayList column = new ArrayList<Object>();
            ArrayList value = new ArrayList<Object>();
            int c = ScheduleAppointment.filtercenters(pincode, district, freeflag, costflag, covaxinflag, covishieldflag);
            if(c>0)
            {
                int col_c=0;
                if(covishieldflag ^ covaxinflag)
                {
                    if(covaxinflag)
                    {
                        column.add("Covaxincount");
                        value.add(4);
                        tabletype=2;
                    }
                    else
                    {
                        column.add("Covishieldcount");
                        value.add(5);
                        tabletype=2;
                    }
                    col_c++;
                }
                else
                {
                    column.add("Covaxincount");
                    column.add("Covishieldcount");
                    value.add(4);
                    value.add(5);
                    col_c++;
                    col_c++;
                    tabletype=1;
                }

                 if(costflag ^ freeflag && costflag)
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }
                else if(!(freeflag ^ costflag))
                {
                    column.add("Price");
                    value.add(7); 
                    col_c++;
                }


                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Name");
                model.addColumn("Address");
                model.addColumn("Phone");
                System.out.println("Col:"+col_c);
                for(int i=0;i<col_c;i++)
                {
                    model.addColumn(column.get(i));
                }
                model.addColumn("Crowdness");

                System.out.println("Col:"+col_c);
                String[][] sample = new String[c][4];
                Date date = jDateChooser1.getDate();  
                String m_date = convertdateformat(date);
                for(int i = 0;i < c;i++)
                {
                    String[] temp =  { ScheduleAppointment.center[i][0],ScheduleAppointment.center[i][5],ScheduleAppointment.center[i][4],m_date };
                    sample[i] = temp;
                }
                Predictcrowd.writeInput(sample,c);
                ArrayList<Float> crowd=Predictcrowd.getcrowdness();
                Object[] rowdata = new Object[col_c+4]; 
                for(int i=0;i<c;i++)
                {

                    int k=0;
                    rowdata[k++] = ScheduleAppointment.center[i][1] ;
                    rowdata[k++] = ScheduleAppointment.center[i][2] ;
                    rowdata[k++] = ScheduleAppointment.center[i][3] ;
                    String crowdcat;
                    for(int j=0;j<col_c;j++)
                    {
                        int index = (Integer) value.get(j);                       
                        if(index==7)
                        {
                             if(Integer.parseInt(ScheduleAppointment.center[i][7]) == 0 )
                            {
                                Price = "Free";
                            }
                            else
                            {
                                Price = "";
                                if(covaxinflag == covishieldflag)
                                {
                                    Price = Price.concat(ScheduleAppointment.center[i][7]).concat("/").concat(ScheduleAppointment.center[i][8]);
                                }
                                else
                                {
                                    if(covaxinflag)
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][7]);
                                    }
                                    else
                                    {
                                        Price = Price.concat(ScheduleAppointment.center[i][8]);
                                    }
                                }

                            }
                             rowdata[k++] = Price;
                        }
                        else
                        {
                            rowdata[k++] = ScheduleAppointment.center[i][index];
                        }  
                    }
                    crowdcat = "Low";
                    if(0.6<crowd.get(i))
                    {
                        crowdcat="High";
                    }
                    else if(0.35<crowd.get(i))
                    {
                        crowdcat="Moderate";
                    }
                    rowdata[k++] = crowdcat;
                    model.addRow(rowdata);
                }
                jTable1.setModel(model);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No results Matched!!");
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("No results Available");
                jTable1.setModel(model);
            }
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
      
    int vaccinetype=0;
      jRadioButton1.setVisible(false);
      jRadioButton2.setVisible(false);
      jButton2.setVisible(false);
      int i = jTable1.getSelectedRow();
      System.out.print(jTable1.getValueAt(i, 3));
      System.out.println(i+" "+tabletype);
      int dose1flag = Integer.parseInt(AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][5]); 
      if(AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][7].equals("Covaxin"))
      {
           vaccinetype = 1;
      }
      else if(AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][7].equals("Covishield"))
      {
          vaccinetype = 2;
      }
      if(tabletype == 1)
      {
          if(Integer.parseInt(jTable1.getValueAt(i, 3).toString())>0)
          {   
              if(  dose1flag!=1 || vaccinetype ==1)
              {
                  jRadioButton1.setVisible(true);
                   jButton2.setVisible(true);
              }
          }
          if(Integer.parseInt(jTable1.getValueAt(i, 4).toString())>0)
          {
              if(dose1flag!=1 || vaccinetype==2)
              {
                  jRadioButton2.setVisible(true);
                  jButton2.setVisible(true);
              }   
          }
      }
      else if(tabletype == 2)
      {
          if(jTable1.getColumnName(3).equals("Covaxincount"))
          {
              if(  dose1flag!=1 || vaccinetype==1)
              {
                  jRadioButton1.setVisible(true);
                   jButton2.setVisible(true);
              }
          }
          else
          {
              if(  dose1flag!=1 || vaccinetype==2)
              {
                  jRadioButton2.setVisible(true);
                  jButton2.setVisible(true);
              }
          }
      }
        
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int i=0;
        try
        {
            Date date = jDateChooser1.getDate();      
            String Appointmentdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            int row = jTable1.getSelectedRow();
            
            if(validateAppointmentDate(date))
            {
                if(jRadioButton1.isSelected())
                {
                    new Confirm_Appointment().setVisible(true);
                    Confirm_Appointment.jLabel4.setText( AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][1] );
                    Confirm_Appointment.jLabel6.setText( AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][0] );

                    VaccineDriveRegistrationSystem.selection[i++]= AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][0];
                    VaccineDriveRegistrationSystem.selection[i++]=ScheduleAppointment.center[row][0];
                    VaccineDriveRegistrationSystem.selection[i++]="1";
                    VaccineDriveRegistrationSystem.selection[i++]=Appointmentdate;


                    String centername= ScheduleAppointment.center[row][1];
                    String address= ScheduleAppointment.center[row][2];
                    Confirm_Appointment.jLabel7.setText(centername);     
                    Confirm_Appointment.jLabel8.setText(address);
                    Confirm_Appointment.jLabel10.setText("Covaxin");
                    this.setVisible(false);

                }
                else if(jRadioButton2.isSelected())
                {
                    new Confirm_Appointment().setVisible(true);
                    Confirm_Appointment.jLabel4.setText( AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][1] );
                    Confirm_Appointment.jLabel6.setText( AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][0] );
                    
                    VaccineDriveRegistrationSystem.selection[i++]= AccountDetails.user[VaccineDriveRegistrationSystem.currentBeneficiary][0];
                    VaccineDriveRegistrationSystem.selection[i++]=ScheduleAppointment.center[row][0];
                    VaccineDriveRegistrationSystem.selection[i++]="2";
                    VaccineDriveRegistrationSystem.selection[i++]=Appointmentdate;

                    
                    String centername= ScheduleAppointment.center[row][1];
                    String address= ScheduleAppointment.center[row][2];
                    Confirm_Appointment.jLabel7.setText(centername);     
                    Confirm_Appointment.jLabel8.setText(address);
                    Confirm_Appointment.jLabel10.setText("Covishield");
                    this.setVisible(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Please Select a Vaccine Type");
                }
                
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Ënter a valid Appointment Date");
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Ënter a valid Appointment Date");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
            int nob=RegisterBeneficiary.noofbeneficiaries(VaccineDriveRegistrationSystem.currentLogin);
            System.out.println("nob");
            new Registered_Beneficiary().setVisible(true);
            Registered_Beneficiary.jLabel4.setText(VaccineDriveRegistrationSystem.currentLogin);
            if(nob==4)
            {
                Registered_Beneficiary.jLabel13.setVisible(false);
            }
            AccountDetails.getusersbyphonenumber(VaccineDriveRegistrationSystem.currentLogin);
            System.out.println(AccountDetails.user[1][13]);
           switch(nob)
            {
                case 4:  user4();


                case 3: user3();

                case 2: user2();
                case 1: user1();

            }
            this.setVisible(false);
            

    }//GEN-LAST:event_jLabel21MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Schedule_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Schedule_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Schedule_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Schedule_Appointment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Schedule_Appointment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    public static javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
