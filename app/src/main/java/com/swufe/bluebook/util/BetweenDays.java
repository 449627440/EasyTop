package com.swufe.bluebook.util;

import java.text.ParseException;
import java.util.Date;

public class BetweenDays {
    public static long getDaySub(String beginDateStr,Date endDate)
     {
                 long day=0;
                 java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 java.util.Date beginDate;
                 try
                 {
                         beginDate = format.parse(beginDateStr);
                         day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
                         //System.out.println("相隔的天数="+day);
                     } catch (ParseException e)
                 {
                         // TODO 自动生成 catch 块
                         e.printStackTrace();
                     }
                 return day;
             }
}
