/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner.DefaultEditor;

/**
 *
 * @author Hrithik
 */
public class Vaccinator extends javax.swing.JFrame {
    static String[] detail = new String[6];
    /**
     * Creates new form Vaccinator
     */
    public Vaccinator() 
    {
        initComponents();
        
        int covishieldflag = Vaccinator_Administrate.getcovishieldflag(VaccineDriveRegistrationSystem.vaccinator_login);
        int covaxinflag =     Vaccinator_Administrate.getcovaxinflag(VaccineDriveRegistrationSystem.vaccinator_login);
        int covishieldcount = Vaccinator_Administrate.getcovishieldcount(VaccineDriveRegistrationSystem.vaccinator_login);
        int covaxincount = Vaccinator_Administrate.getcovaxincount(VaccineDriveRegistrationSystem.vaccinator_login);
        String name = AccountDetails.getcenternamefromid(Vaccinator_Administrate.getcidfromvid(VaccineDriveRegistrationSystem.vaccinator_login));
        
        ((DefaultEditor) jSpinner1.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor) jSpinner2.getEditor()).getTextField().setEditable(false);
        
        jLabel17.setText(name);
        jSpinner1.setValue(covaxincount);
        jSpinner2.setValue(covishieldcount);
        jLabel7.setText(Integer.toString(covaxincount));
        jLabel9.setText(Integer.toString(covishieldcount));
        
        if(covaxinflag==1)
        {
            jCheckBox1.setSelected(true);
        }
        else
        {
            jSpinner1.setValue(0);
            jSpinner1.setEnabled(false);
        }
        if(covishieldflag==1)
        {
            jCheckBox2.setSelected(true);
        }
        else
        {
            jSpinner2.setValue(0);
            jSpinner2.setEnabled(false);
        }
        
    }
    public static int validateproof(String proofid,int proof)
    {
        int bid= -1;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command1 = "select * from beneficiary where proof_id = ? and proof = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setString(1,proofid);
            st.setInt(2,proof);
            ResultSet result = st.executeQuery();
            if(result.next())
            {
                bid = result.getInt("b_id");
            }
            return bid;
        }
        catch(Exception e)
        {
            System.out.print(e);

        }
        return bid;
    }
    public static int getDetails(int bid)
    { 
       int i= 0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command1 = "select * from beneficiary where b_id = ?";
            PreparedStatement st = connection.prepareStatement(command1);
            st.setInt(1,bid);
            ResultSet result = st.executeQuery();
            i=0;
            if(result.next())
            {
                 detail[i++] = result.getString("b_name");
                 detail[i++] = Integer.toString(result.getInt("b_age"));
                 detail[i++] = Integer.toString(result.getInt("dose1_flag"));
                 detail[i++] = Integer.toString(result.getInt("dose2_flag"));
                 if(detail[2].equals("1"))
                 {
                     detail[i++] = result.getDate("dose1_date").toString();
                 }
                 else
                 {
                     detail[i++] = "Nothing";
                 }
                 if(detail[3].equals("1"))
                 {
                     detail[i++] = result.getDate("dose2_date").toString();
                 }
                 else
                 {
                     detail[i++] = "Nothing";
                 }
            }
            return i;
        }
        catch(Exception e)
        {
            System.out.print(e);

        }
        return i;
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
            if(days_difference<0)
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
            System.out.print(e);
        }
        return false;
    }
    
    public static String getDose2Start(String dose1date)
    {
        System.out.print("Dose2");
        System.out.print(dose1date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	try{
    	   //Setting the date to the given date
    	   c.setTime(sdf.parse(dose1date));
    	}catch(Exception e){
    		System.out.print("Catch here");
    	 }
    	   
    	//Number of Days to add
    	c.add(Calendar.DAY_OF_MONTH, 84);  
    	//Date after adding the days to the given date
    	String dose2startdate = sdf.format(c.getTime()); 
    	
    	return dose2startdate;
    	 
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton2.setVisible(false);
        jLabel12 = new javax.swing.JLabel();
        jLabel12.setVisible(false);
        jLabel13 = new javax.swing.JLabel();
        jLabel13.setVisible(false);
        jLabel14 = new javax.swing.JLabel();
        jLabel14.setVisible(false);
        jLabel15 = new javax.swing.JLabel();
        jLabel15.setVisible(false);
        jLabel16 = new javax.swing.JLabel();
        jLabel16.setVisible(false);
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton1.setVisible(false);
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton2.setVisible(false);
        jButton3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/VDRS Logo.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel21.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 153));
        jLabel21.setText("Logout");
        jLabel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(33, 33, 33))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(20, 6, 131));
        jLabel2.setText("Update Vaccines Available");

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Available");
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Available");
        jCheckBox2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox2StateChanged(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 200, 1));
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 200, 1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covaxin_s.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covishield_s.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covaxin_s.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 13, 159));
        jLabel6.setText("Count");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vaccine/drive/registration/system/covishield_s.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton1.setBackground(new java.awt.Color(0, 10, 81));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(20, 6, 131));
        jLabel10.setText("Enter Beneficiary Proof ID:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(20, 6, 131));
        jLabel11.setText("Administrate Vaccine ");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aadhar Card", "Pan Card", "Voter ID", "Driving License", " " }));

        jButton2.setBackground(new java.awt.Color(0, 10, 81));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(20, 6, 131));
        jLabel12.setText("NAME");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(20, 6, 131));
        jLabel13.setText("AGE");
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(97, 92, 92));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(97, 92, 92));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(97, 92, 92));
        jLabel16.setText("DOSE 1");

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton1.setText("Covaxin");

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jRadioButton2.setText("Covishield");

        jButton3.setBackground(new java.awt.Color(0, 10, 81));
        jButton3.setFont(new java.awt.Font("Rockwell Extra Bold", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("->");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(20, 6, 131));
        jLabel17.setText("Center Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(84, 84, 84)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(84, 84, 84)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel11)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(84, 84, 84)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(64, 64, 64))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        jRadioButton1.setVisible(false);
        jRadioButton2.setVisible(false);
        jLabel16.setVisible(false);
        jLabel15.setVisible(false);
        jLabel14.setVisible(false);
        jButton2.setVisible(false);
        
        int proof =  jComboBox1.getSelectedIndex()+1;
        boolean flag =true;
        String proofid = jTextField1.getText();
        int bid= validateproof(proofid,proof);
        if( bid != -1 )
        {
            int k =getDetails(bid);
            jLabel12.setVisible(true);
            jLabel13.setVisible(true);
            jLabel14.setVisible(true);
            jLabel15.setVisible(true);
            jLabel16.setVisible(true);
            
            jLabel14.setText(detail[0]);
            jLabel15.setText(detail[1]);
            
            int dose1flag=Integer.parseInt(detail[2]);
            int dose2flag=Integer.parseInt(detail[3]);
            if(dose1flag == 1 && dose2flag == 1)
            {
                jLabel16.setText("Fully Vaccinated");
                flag = false;
            }
            else if(dose1flag == 1 && dose2flag == 0)
            {
                jLabel16.setText("Partially Vaccinated");
                String date = getDose2Start(detail[4]);
                if(!validateDose2Date(date))
                {
                    flag = false;
                }
            }
            else if(dose1flag == 0 && dose2flag == 0)
            {
                jLabel16.setText("Not Vaccinated");
                jButton2.setVisible(true);
            }
            int cid = Vaccinator_Administrate.getcidfromvid(VaccineDriveRegistrationSystem.vaccinator_login);
            String curr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            
            int app_exist = Vaccinator_Administrate.appointmentexists(bid, cid, curr);
            if(  app_exist== 1)
            {
                if(Vaccinator_Administrate.gettypefromappointment(bid)==1)
                {
                    jRadioButton1.setVisible(true);
                }
                else
                {
                    jRadioButton2.setVisible(true);
                }
            }
            else if(app_exist==0 && !(dose1flag==1 && dose2flag==1))
            {
                int covishieldcount = Vaccinator_Administrate.getcovishieldcount(VaccineDriveRegistrationSystem.vaccinator_login);
                int covaxincount = Vaccinator_Administrate.getcovaxincount(VaccineDriveRegistrationSystem.vaccinator_login);
                String date = getDose2Start(detail[4]);
                if(dose1flag == 1 && validateDose2Date(date))
                {
                    if(Vaccinator_Administrate.getvaccinetypefromid(bid)==1 && covaxincount>0)
                    {
                       jRadioButton1.setVisible(true);
                    }
                    else if(Vaccinator_Administrate.getvaccinetypefromid(bid)==2 && covishieldcount>0)
                    {
                        jRadioButton2.setVisible(true);
                    }
                }
                else if(dose1flag == 0)
                {
                    if(covaxincount>0)
                    {
                        jRadioButton1.setVisible(true);
                    }
                    if(covishieldcount>0)
                    {
                        jRadioButton2.setVisible(true);
                    }  
                }
            }
            jButton2.setVisible(flag);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Invalid Proof");
        
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        int ch = jComboBox1.getSelectedIndex();
        if(ch == 1)
        {
            char c=evt.getKeyChar();
            if (!Character.isDigit(c) && jTextField1.getText().length()==12)
            {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
           
        int proof =  jComboBox1.getSelectedIndex()+1;
        String proofid = jTextField1.getText();
        int bid= validateproof(proofid,proof);
        int vid = VaccineDriveRegistrationSystem.vaccinator_login;
        int cid = Vaccinator_Administrate.getcidfromvid(VaccineDriveRegistrationSystem.vaccinator_login);
        String curr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int choice =-1;
        if(Vaccinator_Administrate.appointmentexists(bid, cid, curr) == 0)
        {
            if(jRadioButton1.isSelected())
            {
                   choice =1;
            }
            else if(jRadioButton2.isSelected())
            {
                   choice =2;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please select a Vaccine type");
                return;
            }
        }
        if( bid != -1 )
        {
            Vaccinator_Administrate.administervaccine(bid,vid,choice);
            int covishieldcount = Vaccinator_Administrate.getcovishieldcount(VaccineDriveRegistrationSystem.vaccinator_login);
            int covaxincount = Vaccinator_Administrate.getcovaxincount(VaccineDriveRegistrationSystem.vaccinator_login);
            jLabel7.setText(Integer.toString(covaxincount));
            jLabel9.setText(Integer.toString(covishieldcount));
            
            JOptionPane.showMessageDialog(this, "Vaccination Status Updated");
            jLabel12.setVisible(false);
            jLabel13.setVisible(false);
            jRadioButton1.setVisible(false);
            jRadioButton2.setVisible(false);
            jLabel16.setVisible(false);
            jLabel15.setVisible(false);
            jLabel14.setVisible(false);
            jButton2.setVisible(false);
        }
         else
        {
            JOptionPane.showMessageDialog(this,"Invalid Proof");
        
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged

    }//GEN-LAST:event_jSpinner1StateChanged

    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        // TODO add your handling code here:
        if(jCheckBox1.isSelected())
        {
            
            int covaxincount = Vaccinator_Administrate.getcovaxincount(VaccineDriveRegistrationSystem.vaccinator_login);
            jSpinner1.setValue(covaxincount);
            jSpinner1.setEnabled(true);
        }
        else
        {
            jSpinner1.setValue(0);
            jSpinner1.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1StateChanged

    private void jCheckBox2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox2StateChanged
        // TODO add your handling code here:
        if(jCheckBox2.isSelected())
        {
            
            int covishield = Vaccinator_Administrate.getcovishieldcount(VaccineDriveRegistrationSystem.vaccinator_login);
            jSpinner2.setEnabled(true);
            jSpinner2.setValue(covishield);
        }
        else
        {
            jSpinner2.setValue(0);
            jSpinner2.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox2StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int covaxinflag=0 ,covishield=0;
        int cv = 0;
        int cs =0;
        if(jCheckBox1.isSelected() )
        {
           covaxinflag =1;
           cv = (Integer)jSpinner1.getValue();
        }
        if(jCheckBox2.isSelected())
        {
            cs= (Integer)jSpinner2.getValue();
            covishield=1;
        }

        int n = JOptionPane.showConfirmDialog(this, "Press Yes to Confirm");
        if(n==0)
        {
            Vaccinator_Administrate.updatevaccinecount(VaccineDriveRegistrationSystem.vaccinator_login, covaxinflag, covishield, cv, cs);
            int covishieldcount = Vaccinator_Administrate.getcovishieldcount(VaccineDriveRegistrationSystem.vaccinator_login);
            int covaxincount = Vaccinator_Administrate.getcovaxincount(VaccineDriveRegistrationSystem.vaccinator_login);
            jLabel7.setText(Integer.toString(covaxincount));
            jLabel9.setText(Integer.toString(covishieldcount));
        }
   
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        new OTPLogin_1().setVisible(true);
        VaccineDriveRegistrationSystem.currentBeneficiary=-1;
        VaccineDriveRegistrationSystem.currentLogin="-1";
        VaccineDriveRegistrationSystem.vaccinator_login=-1;
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
            java.util.logging.Logger.getLogger(Vaccinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vaccinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vaccinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vaccinator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vaccinator().setVisible(true);
                
            }
           
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
