package com.fehead.bookstore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nightessss 2020/7/15 0:09
 */
public class EmailUtil {
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)){
            return false;
        }
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
//    public static void main(String[] args) {
//        String msg = "1dasd23424@qq.cmn";
//        System.out.println(isEmail(msg));
//    }

}
