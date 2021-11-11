/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccine.drive.registration.system;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Hrithik
 */
public class Certificate_Generation {
    public static String star(String s){
        int l = s.length();
        String ns= new String();
        for(int i=0;i<l-4;i++){
            ns+='*';
        }
        ns+=s.substring(l-4,l);
        return ns;
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
    public static void certificate(int b_id)
    {
        String name = "Dhadhi";
        String age = "19";
        String gender="Male";
        String ref="1234567890";
        String proof="";
        int vid=0;
        String vaccinator="Aravind";
        String type ="CoviShield";
        String center ="PSG";
        String dose1="-";
        String dose2="-";
        String prooftype="-";
        int d1=0,d2=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/VDRS", "root", "Bh@280801");
            String command = "select * from beneficiary natural join vaccinator natural join vaccination_center where b_id = ?";
            PreparedStatement st = connection.prepareStatement(command);
            st.setInt(1,b_id);
            ResultSet res = st.executeQuery();
            if(res.next()){
                name = res.getString("b_name");
                age = Integer.toString(res.getInt("b_age"));
                gender = res.getString("b_gender");
                ref = Integer.toString(b_id);
                vaccinator = res.getString("v_name");
                center = res.getString("c_name");
                int t = res.getInt("vaccine_type");

                if (t==1){
                    type="Covaxin";
                }
                else if (t==2){
                    type="Covishield";
                }
                proof = res.getString("proof_id");
                proof= star(proof);
                int p = res.getInt("proof");
                if (p==1){
                    prooftype="AADHAR";
                }
                else if (p==2){
                    prooftype ="PAN";
                }
                else if (p==3){
                    prooftype ="VOTER ID";
                }
                else if (p==4){
                    prooftype="DRIVING LICENSE";
                }
                d1=res.getInt("dose1_flag");
                d2=res.getInt("dose2_flag");
                if (d2==1 && d1==1){
                    dose1=res.getDate("dose1_date").toString();
                    dose2=res.getDate("dose2_date").toString();
                }
                else if ((d1==1) && (d2==0)){
                    dose1=res.getDate("dose1_date").toString();
                    String start = getDose2Start(dose1);
                    String end = getDose2End(dose1);
                    dose2 = "Between " + start + " and " + end;
                }
                else{
                    System.out.println("No certificate");
                }
            }
            System.out.println(name +" "+ age +" "+gender +" "+proof +" "+ ref +" "+ vaccinator +" "+ center +" "+ type +" "+ dose1 +" "+ dose2);

        }
        catch(Exception e){
            System.out.println("Exit");
            e.printStackTrace();
        }
        try{
            // Creating a PdfDocument object
            String fn = name+"_"+ref;
            String file = "D://"+fn+"_Certificate.pdf";
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
            Document doc = new Document(pdfDoc);

            Text text1 = new Text("Certificate for COVID-19 Vaccination");
            Color color = WebColors.getRGBColor("Teal");
            text1.setFontColor(color);
            text1.setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
            text1.setFontSize((float) 25.0);
            Paragraph para1 = new Paragraph(text1);
            para1.setTextAlignment(TextAlignment.CENTER);
            String var1="";
            if (d2==1) {
                var1 = "Fully Vaccinated";
            }
            else{
                var1="1st Dose Vaccinated";
            }

            Text text2 = new Text(var1);//Will be either fully vaccinated or 1st Dose Vaccinated
            text2.setFontColor(color);
            text2.setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
            text2.setFontSize((float) 16.0);
            Paragraph para2 = new Paragraph(text2);
            para2.setTextAlignment(TextAlignment.CENTER);


            Text text3 = new Text("Beneficiary Details");
            text3.setFontColor(color);
            text3.setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
            text3.setFontSize((float) 14.0);
            Paragraph para3 = new Paragraph(text3);


            Table table = new Table(2);
            Color color1 = WebColors.getRGBColor("teal");

            table.addCell(new Cell().add(new Paragraph("Beneficiary Name")).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph((name))));
            table.addCell(new Cell().add(new Paragraph(("Age"))).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(age)));
            table.addCell(new Cell().add(new Paragraph(("Proof Type"))).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(prooftype)));
            table.addCell(new Cell().add(new Paragraph(("Verified Proof"))).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(proof)));
            table.addCell(new Cell().add(new Paragraph("Gender")).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(gender)));
            table.addCell(new Cell().add(new Paragraph("Benficiary Reference ID")).setFontColor(color1).setBold().setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(ref)));
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.setBorder(new SolidBorder(2));



            Text text4 = new Text("Vaccination Details");
            text4.setFontColor(color);
            text4.setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
            text4.setFontSize((float) 14.0);
            Paragraph para4 = new Paragraph(text4);


            Table table1 = new Table(2);

            table1.addCell(new Cell().add(new Paragraph("Vaccination Type")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
            table1.addCell(new Cell().add(new Paragraph(type)));
            table1.addCell(new Cell().add(new Paragraph("Dose 1 Vaccination Date")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
            table1.addCell(new Cell().add(new Paragraph(dose1)));
            if (d2==1) {
                table1.addCell(new Cell().add(new Paragraph("Dose 2 Vaccination Date")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
                table1.addCell(new Cell().add(new Paragraph(dose2)));
            }
            else{
                table1.addCell(new Cell().add(new Paragraph("Dose 2 Due Date")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
                table1.addCell(new Cell().add(new Paragraph(dose2)));
            }
            table1.addCell(new Cell().add(new Paragraph("Vaccinated By")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
            table1.addCell(new Cell().add(new Paragraph(vaccinator)));
            table1.addCell(new Cell().add(new Paragraph("Vaccinated At")).setFontColor(color).setBold().setTextAlignment(TextAlignment.CENTER));
            table1.addCell(new Cell().add(new Paragraph(center)));
            table1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table1.setVerticalAlignment(VerticalAlignment.MIDDLE);
            table1.setBorder(new SolidBorder(2));

            Paragraph lb = new Paragraph("\n\n");
            Paragraph empty = new Paragraph("\n");

            ImageData data = ImageDataFactory.create("certificate_p.jpg");
            Image img = new Image(data);
            img.setMaxHeight(100);
            img.setMaxHeight(100);
            img.setHorizontalAlignment(HorizontalAlignment.CENTER);


            doc.add(img);
            doc.add(para1);
            doc.add(para2);
            doc.add(lb);
            doc.add(para3);
            doc.add(empty);
            doc.add(table);
            doc.add(lb);
            doc.add(para4);
            doc.add(empty);
            doc.add(table1);
            doc.add(lb);

            // Closing the document
            doc.close();
            System.out.println("Content added successfully..");


        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("Certificate Generated");
    }

    public static void main(String[] args) {
        int b_id=1004;
        certificate(b_id);
    }
}
