package cn.xavier.hrm.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    //开始时间
    public static String toStartForNow(){
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //格式化到 n天后 最大结束时间
    public static String toEndForPlus(int days){
        LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(days), LocalTime.MAX);
        return end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String toStartForSub(int days){
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now().plusDays(0-days), LocalTime.MIN);
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}