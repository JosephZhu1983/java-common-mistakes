package org.geekbang.time.commonmistakes.datetime.mysteryproblem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonMistakesApplication {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time1 = "1900-01-01 08:05:44";
        String time2 = "1900-01-01 08:05:43";
        Date date1 = simpleDateFormat.parse(time1);
        Date date2 = simpleDateFormat.parse(time2);
        System.out.println(date1.getTime() / 1000 - date2.getTime() / 1000);

        String time3 = "1900-01-01 08:05:43";
        String time4 = "1900-01-01 08:05:42";
        Date date3 = simpleDateFormat.parse(time3);
        Date date4 = simpleDateFormat.parse(time4);
        System.out.println(date3.getTime() / 1000 - date4.getTime() / 1000);
    }

}

