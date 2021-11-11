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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import javax.net.ssl.HttpsURLConnection;
import java.lang.Math;

public class OTP {
    static int otp;
    static String num ;
    public static int sendSms(String message,String number)
    {
        int code=-1;
        num = number;
        try
        {

            String apiKey="KFb03uAheTNtr8gzsc2Gfw7PCdaMOxiJDvWIylRkpSVUjLHm45T9RUQWrqzNobfjZ3nAe6uOY7mSFGHs";
            String sendId="FSTSMS";
            //important step...
            message=URLEncoder.encode(message, "UTF-8");
            String language="english";
            String route="p";
            String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            //sending get request using java..
            URL url=new URL(myUrl);
            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();


            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");

            code=con.getResponseCode();
            System.out.println("Response code : "+code);
            StringBuffer response=new StringBuffer();
            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));

            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }

            System.out.println(response);


        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return code;

    }

    public static void generateOTP()
    {
        int max=9999;
        int min=1000;
        otp =(int) (Math.random()*(max-min+1)+min);
    }
    
    public static boolean verifyOTP(String enteredotp)
    {
        if(Integer.parseInt(enteredotp)==otp)
        {
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String[] args) {
        System.out.println(otp);
        String mobilenumber = "9940802875";

        OTP.sendSms("Your OTP is "+otp+"-Time:"+new Date().toLocaleString(), mobilenumber);

    }
}
