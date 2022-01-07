package cn.xavier.hrm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtils {

    //手机号的验证规则
    private static final String PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9]))\\d{8}$";

    //邮箱的校验规则
    private static final String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

    //断言验证手机号
    public static void assertPhone(String phone,String message){
        //格式判断
        Pattern p = Pattern.compile(PHONE_REGEX);
        Matcher m = p.matcher(phone);
        if(!m.matches()){
            throw new RuntimeException(message);
        }
    }

    public static void assertPhone(String phone){
        assertPhone(phone,"无效的手机号");
    }
    //断言验证邮箱
    public static void assertEmail(String email,String message){
        //格式判断
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(email);
        if(!m.matches()){
            throw new RuntimeException(message);
        }
    }
    public static void assertEmail(String email){
        assertEmail(email,"无效的邮箱");
    }
    //断言不为空
    public static void assertNotNull(Object obj,String message){
        if(null == obj){
            throw new RuntimeException(message);
        }
        if(obj instanceof String){
            String objStr = (String) obj;
            if(objStr.length() == 0){
                throw new RuntimeException(message);
            }
        }
    }
    public static void assertNotNull(Object obj){
        assertNotNull(obj,"不可为空");
    }

    public static void isTrue(boolean b, String msg) {
        if (!b) {
            throw new RuntimeException(msg);
        }
    }
}