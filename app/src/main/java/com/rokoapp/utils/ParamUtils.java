package com.rokoapp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParamUtils {
    public static String PREFS_NAME = "userLogin";
    public static String U_ID = "userId";
    public static String ANDROID_TOKEN = "android_token";
    public static String ID_USER;
    public static String MOBILE_USER;
    public static String EMAIL_USER;
    public static String USER_COMPLIMENT_PASS;
    public static String USER_COMPLEMENT_PASS_VALIDITY;
    public static String USER_TOTAL_PASS;
    public static String ID_AUTH;
    private String API_KEY = "AIzaSyCqpmNJvuXegxmzsbzoghbXlipzrCOC-8o";

    /*================================ Start User Info Params ================================================*/

    public static String USER_NAME = "userName";
    public static String MOBILE_NUM = "mobile";
    public static String EMAIL = "email";
    public static String OFFICE_END_TIME = "time_you_leave_office";
    public static String PASS_EARNED = "earned";
    public static String DOB = "dob";
    public static String GENDER = "gender";
    public static String OFFICE_ADDRESS = "office_address";
    public static String USER_TYPE = "userType";
    public static String HOME_LAT_LONG = "home_lat_long";
    public static String MORNING_HOME_LEAVE = "time_you_leave_home";
    public static String OFFICE_LAT_LONG = "office_lat_long";
    public static String HOME_ADDRESS = "home_address";
    public static String REFER_CODE = "refer_and_code";
    public static String COMPLIMENTARY_PASS = "comp_pass";
    public static String COMPLIMENTARY_PASS_VALIDITY = "comp_pass_valid";
    public static String TOTAL_PASS = "total_pass";


    /*================================ End User Info Params ================================================*/


    public static CharSequence trimTrailingWhitespace(CharSequence source) {

        if(source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        while(--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i+1);
    }

    public static boolean isDigits(String number){
        if(!TextUtils.isEmpty(number)){
            return TextUtils.isDigitsOnly(number);
        }else{
            return false;
        }
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        return dateFormat.format(date);
    }

    public static String changeBookingDateFormat(String time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            Date dt = null;
            try {
                dt = sdf.parse(time);
//            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return sdfs.format(dt);
        }else
            return null;
    }

    public static String changeAttendDateFormat(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        Date dt = null;
        try {
            dt = sdf.parse(time);
//            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdfs.format(dt);
    }

    public static String changeBirthDateFormat(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfs = new SimpleDateFormat("dd-MMM-yyyy");
        Date dt = null;
        try {
            dt = sdf.parse(time);
//            System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdfs.format(dt);
    }

    @SuppressLint("SimpleDateFormat")
    public static String changeDateFormat(String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Date date;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Calendar c = Calendar.getInstance();
            long yesterday = (c.getTimeInMillis()-86400000);
            String yesterdayDate = outputFormat.format(yesterday);

            String currentDate = outputFormat.format(c.getTime());
            if (str.equals(currentDate)){
                str = "Today";
            }else if (str.equals(yesterdayDate)){
                str = "Yesterday";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getOriginTime(String time, String timeToAdd){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        long newTime = 0;
        try {
            date = sdf.parse(time);
            long timeInM = date.getTime();
            long givenTime = Long.parseLong(timeToAdd)*1000;
            newTime = timeInM + givenTime;
//            System.out.println("Time Display:=============== " +date.getTime() ); // <-- I got result here
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdfs.format(new Date(newTime));/**/
    }
}
