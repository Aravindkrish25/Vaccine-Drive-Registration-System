/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Hrithik
 */
public class home_page extends javax.swing.JFrame {

    /**
     * Creates new form home_page
     */
    public home_page() {
        initComponents();
        ImageIcon image;
        if (VaccineDriveRegistrationSystem.currentLogin.equals("-1"))
        {
            image = new ImageIcon(getClass().getResource("register-sign.png"));
        }
        else
        {
            image = new ImageIcon(getClass().getResource("logout.png"));
        }
        jLabel4.setIcon(image);
    }
    
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("View Stats>>>");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7);
        jLabel7.setBounds(70, 360, 150, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/VDRS_Logo-removebg-preview.png"))); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 20, 230, 70);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/Homepage_text_removebg.png"))); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(30, 130, 480, 190);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/Logout.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel4);
        jLabel4.setBounds(1120, 10, 120, 50);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/Home page.png"))); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(0, 0, 1250, 590);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/Home page_2.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(20, 6, 120));
        jLabel6.setText("Book Your Slot -->");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(509, 509, 509)
                        .addComponent(jLabel6)))
                .addContainerGap(232, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1267, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        ImageIcon image = new ImageIcon(getClass().getResource("register-sign.png"));
        if(VaccineDriveRegistrationSystem.currentLogin.equals("-1"))
        {
            new OTPLogin_1().setVisible(true);
            this.setVisible(false);
        }
        else
        {
            VaccineDriveRegistrationSystem.currentBeneficiary=-1;
            VaccineDriveRegistrationSystem.currentLogin="-1";
            jLabel4.setIcon(image);
            JOptionPane.showMessageDialog(this, "Logout Successful");
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        if(VaccineDriveRegistrationSystem.currentLogin.equals("-1"))
        {
            new OTPLogin_1().setVisible(true);
            this.setVisible(false);
        }
        else
        {
            int nob=RegisterBeneficiary.noofbeneficiaries(VaccineDriveRegistrationSystem.currentLogin);
            if(nob==0)
            {
                new Beneficiary_page().setVisible(true);
                this.setVisible(false);
                
            }
            else
            {
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
            }
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        new Statistis_Form().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jLabel7MouseClicked

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
            java.util.logging.Logger.getLogger(home_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home_page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
