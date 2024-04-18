package com.njglyy.corporate_group_backend.utils;

public class Encrypt {
    public String GeneratePassword(String user_string){
        int x_length;
        int x_position1;
        int x_position2;
        int x_temp_int1;
        int x_temp_int2;
        char x_temp_str1='0';
        char x_temp_str2='0';
        String pw_string = "123456Aa!";

        user_string=user_string.trim();
        pw_string=pw_string.trim();
        user_string=user_string.toUpperCase();

        if(pw_string.length()>0){
            x_length= user_string.length();
            x_position1 = 1;
            x_temp_int1 = 0;
            while(x_position1<=x_length){
                x_temp_str1=user_string.charAt(x_position1-1);
                x_temp_int2=(int)x_temp_str1;
                x_temp_int1=x_temp_int1+x_temp_int2;
                x_position1++;
            }
            x_temp_int1 = x_temp_int1 + x_length;
            while( x_temp_int1 > 122){
                x_temp_int1 = x_temp_int1 - 75;
            }
            x_temp_str2=pw_string.charAt(0);
            x_temp_int2=(int)x_temp_str2;
            x_temp_int1=x_temp_int1+x_temp_int2;
            if((x_temp_int1 > 122) && (x_temp_int1 < 198)){
                x_temp_int1 = x_temp_int1 - 75;
            }
            if(x_temp_int1>197){
                x_temp_int1 = x_temp_int1 - 133;
            }
            if((x_temp_int1 > 57)&&(x_temp_int1 < 65)){
                x_temp_int1 = x_temp_int1 - 7;
            }
            if((x_temp_int1 > 90) && (x_temp_int1 < 97)){
                x_temp_int1 = x_temp_int1 - 6;
            }
            x_length=pw_string.length();
            x_temp_int2 = x_length - 1;

            pw_string=(char) x_temp_int1+pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
            x_position1 = 1;
            while(x_position1 <= x_length){
                x_temp_str1=pw_string.charAt(x_position1-1);
                x_temp_int1=(int)x_temp_str1;
                x_position2=x_position1-1;
                if(x_position2==0){
                    x_position2=x_length;
                }
                x_temp_str2=pw_string.charAt(x_position2-1);
                x_temp_int2=(int)x_temp_str2;
                x_temp_int1=x_temp_int1+x_temp_int2;
                if((x_temp_int1 > 122) && ( x_temp_int1 < 198)){
                    x_temp_int1 = x_temp_int1 - 75;
                }
                if(x_temp_int1 > 197){
                    x_temp_int1 = x_temp_int1 - 133;
                }
                if(( x_temp_int1 > 57)&&(x_temp_int1 < 65)){
                    x_temp_int1 = x_temp_int1 - 7;
                }
                if((x_temp_int1 > 90)&&(x_temp_int1 < 97)){
                    x_temp_int1 = x_temp_int1 - 6;
                }
                if(x_position1 == 1){
                    x_temp_int2 = x_length - 1;
                    pw_string=(char) x_temp_int1+pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
                }
                else{
                    x_temp_int2 = x_length - x_position1;
                    x_position1 = x_position1 - 1;
                    pw_string=pw_string.substring(0,x_position1)+ (char)x_temp_int1 +pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
                    x_position1++;
                }
                x_position1++;

            }
        }
        else{
            pw_string = " ";
        }
        return pw_string;
    }

    public String VerifyPassword(String user_string, String pw_string){
        int x_length;
        int x_position1;
        int x_position2;
        int x_temp_int1;
        int x_temp_int2;
        char x_temp_str1='0';
        char x_temp_str2='0';

        user_string=user_string.trim();
        pw_string=pw_string.trim();
        user_string=user_string.toUpperCase();


        if(pw_string.length()>0){
            x_length= user_string.length();
            x_position1 = 1;
            x_temp_int1 = 0;
            while(x_position1<=x_length){
                x_temp_str1=user_string.charAt(x_position1-1);
                x_temp_int2=(int)x_temp_str1;
                x_temp_int1=x_temp_int1+x_temp_int2;
                x_position1++;
            }
            x_temp_int1 = x_temp_int1 + x_length;
            while( x_temp_int1 > 122){
                x_temp_int1 = x_temp_int1 - 75;
            }
            x_temp_str2=pw_string.charAt(0);
            x_temp_int2=(int)x_temp_str2;
            x_temp_int1=x_temp_int1+x_temp_int2;
            if((x_temp_int1 > 122) && (x_temp_int1 < 198)){
                x_temp_int1 = x_temp_int1 - 75;
            }
            if(x_temp_int1>197){
                x_temp_int1 = x_temp_int1 - 133;
            }
            if((x_temp_int1 > 57)&&(x_temp_int1 < 65)){
                x_temp_int1 = x_temp_int1 - 7;
            }
            if((x_temp_int1 > 90) && (x_temp_int1 < 97)){
                x_temp_int1 = x_temp_int1 - 6;
            }
            x_length=pw_string.length();
            x_temp_int2 = x_length - 1;

            pw_string=(char) x_temp_int1+pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
            x_position1 = 1;
            while(x_position1 <= x_length){
                x_temp_str1=pw_string.charAt(x_position1-1);
                x_temp_int1=(int)x_temp_str1;
                x_position2=x_position1-1;
                if(x_position2==0){
                    x_position2=x_length;
                }
                x_temp_str2=pw_string.charAt(x_position2-1);
                x_temp_int2=(int)x_temp_str2;
                x_temp_int1=x_temp_int1+x_temp_int2;
                if((x_temp_int1 > 122) && ( x_temp_int1 < 198)){
                    x_temp_int1 = x_temp_int1 - 75;
                }
                if(x_temp_int1 > 197){
                    x_temp_int1 = x_temp_int1 - 133;
                }
                if(( x_temp_int1 > 57)&&(x_temp_int1 < 65)){
                    x_temp_int1 = x_temp_int1 - 7;
                }
                if((x_temp_int1 > 90)&&(x_temp_int1 < 97)){
                    x_temp_int1 = x_temp_int1 - 6;
                }
                if(x_position1 == 1){
                    x_temp_int2 = x_length - 1;
                    pw_string=(char) x_temp_int1+pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
                }
                else{
                    x_temp_int2 = x_length - x_position1;
                    x_position1 = x_position1 - 1;
                    pw_string=pw_string.substring(0,x_position1)+ (char)x_temp_int1 +pw_string.substring(pw_string.length()-x_temp_int2,pw_string.length());
                    x_position1++;
                }
                x_position1++;

            }
        }
        else{
            pw_string = " ";
        }
        return pw_string;
    }
}
