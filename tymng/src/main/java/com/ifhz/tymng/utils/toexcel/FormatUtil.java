
package com.ifhz.tymng.utils.toexcel;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class FormatUtil {

    //格式化货币，以 ###,###.00 输出
    public static String processMoney(Double inMoney) {
        double outMoney = 0.0;
        outMoney = inMoney.doubleValue();
//		if(outMoney==0){
//			return "0.00";
//		}
        NumberFormat format = new DecimalFormat("#,###,##0.00");
        return format.format(outMoney);
    }

    //格式化日期，以 yyyy-mm-dd 的形式输出
    public static String processDate(Date date) {
        String s = "";
        if (date == null) {
            s = "";
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            s += cal.get(Calendar.YEAR);
            s += "-";
            s += cal.get(Calendar.MONTH) + 1;
            s += "-";
            s += cal.get(Calendar.DATE);
        }
        return s;
    }

    public static String nullToString(String str) {
        if (str == null)
            str = "";
        return str;
    }

    public static String MoneyToString(Double inMoney) {
        double outMoney = 0.0;
        outMoney = inMoney.doubleValue();
//		if(outMoney==0){
//			return "0.00";
//		}
        NumberFormat format = new DecimalFormat("######0.00");
        return format.format(outMoney);
    }

    public static void main(String[] a) {
        System.out.println(processDate(new java.sql.Date(System.currentTimeMillis())));
    }
}

