package com.example.kallakurigroup.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validations {
    static Pattern mp;
    static Matcher mm;
    static String mregex = "^[7-9][0-9]{9}$";
    static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  //  String numberregex = "^[0-9]";
    //^(?!(\d)\1+$|(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=0)){5}\d$|(?:0(?=9)|1(?=0)|2(?=1)|3(?=2)|4(?=3)|5(?=4)|6(?=5)|7(?=6)|8(?=7)|9(?=8)){5}\d$)\d{6}$
    static String pinpattern="^[012345|123456|234567|345678|456789|567890|543210|654321|765432|876543|987654|098765" +
          "|000000|111111|222222|333333|444444|555555|666666|777777|888888|999999|112233|223344|334455|445566|556677|667788|778899|889900|11122|222333|333444|444555|555666|666777|777888|888999|999000]$";
    //String pinpattern2="^[(123|234|000|345|456|567|678|789|890|987|876|765|654|543|432|321|210)+]$";
    static String regPostcode = "^[1-9][0-9]{5}$";

   static String pp= "^d{6}(012345|123456|234567|345678|456789|567890|543210|654321|765432|876543|987654|098765|000000|111111|222222|333333|444444|555555|666666|777777|888888|999999)\\d*?(\\d)\\1{2,}$";

    static String numberregex ="^[0-9]+$";

    public static boolean mobilenumberValidation(String number)
    {
        boolean mnflag=false;
         mp = Pattern.compile(mregex);
         mm = mp.matcher(number);
        if (mm.matches())
        {
            mnflag=true;
            Log.e("regex change ", ":" + number);
        }
        else
        {
            mnflag=false;
            Log.e("regex not match ", ":" + number);
        }
        return mnflag;
    }
    public static boolean emailValidate(final String hex) {

        mp = Pattern.compile(EMAIL_PATTERN);
        mm = mp.matcher(hex);
        return mm.matches();
    }
    public static boolean numberValidate(final String num) {

        mp = Pattern.compile(numberregex);
        mm = mp.matcher(num);
        return mm.matches();

    }
    public static boolean pinValidation(String input)
    {
       // boolean isMatch = .IsMatch(input, "^[0-9]{6}$")     && Regex.IsMatch(input, @"(([0-9]{1})\2+)") && input.Distinct().Count() > 1;
        Boolean mnflag=false;
        mp = Pattern.compile(pinpattern);
        mm = mp.matcher(input);
        if(!mm.matches()) {
            mnflag=true;
            Log.e("pin validations ", ":" + input);
        }
        else
        {
            mnflag=false;
            Log.e("pin does not match", ":" + input);
        }
        return mnflag;
    }
    public static boolean postalcodeValidation(String input)
    {
        mp = Pattern.compile(regPostcode);
        mm = mp.matcher(input);
        return mm.matches();
    }

    public static boolean isConsecutive(final String pinCode)
    {
        int [] digits = new int [pinCode.length()];
        int [] differences = new int [pinCode.length()-1];
        int temp = 0;

        Log.e("pins:::",pinCode);
        for(int i = 0; i < pinCode.length(); i++)
            digits[i] = Integer.parseInt(String.valueOf(pinCode.charAt(i)));

        for(int i = 0; i < digits.length -1; i++)
            differences[i] = Math.abs(digits[i] - digits[i+1]);

        if(differences.length != 0) {
            temp = differences[0];
            for (int i = 1; i < differences.length; i++)
                if (temp != differences[i])
                    return false;
        }

        return true;
    }

}
