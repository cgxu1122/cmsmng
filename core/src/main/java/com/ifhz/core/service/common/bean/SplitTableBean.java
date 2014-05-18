package com.ifhz.core.service.common.bean;

import com.google.common.collect.Lists;
import com.ifhz.core.service.common.enums.SplitTableEnums;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 15:26
 */
public class SplitTableBean {
    private Date initDate;
    private Date now;
    private int yearDiffValue;
    private List<String> suffixList = Lists.newArrayList();


    public SplitTableBean(Date initDate, Date now) {
        this.initDate = initDate;
        this.now = now;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowReason = SplitTableEnums.getByCalendarMonth(nowMonth).SEASON;

        calendar.clear();
        calendar.setTime(initDate);
        int initYear = calendar.get(Calendar.YEAR);
        int initMonth = calendar.get(Calendar.MONTH);
        int initReason = SplitTableEnums.getByCalendarMonth(initMonth).SEASON;


        int yearDiffValue = nowYear - initYear;
        this.yearDiffValue = yearDiffValue;
        if (yearDiffValue == 0) {
            this.suffixList.addAll(getSuffixForYear(nowYear, initReason, nowReason));
        } else if (yearDiffValue == 1) {
            //当年
            int startReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear, startReason, nowReason));
            //上一年
            int previousEndReason = SplitTableEnums.getByCalendarMonth(Calendar.DECEMBER).SEASON;
            this.suffixList.addAll(getSuffixForYear(initYear, initReason, previousEndReason));
        } else if (yearDiffValue == 2) {
            //当年
            int startReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear, startReason, nowReason));
            //中间年
            int middleStartReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            int middleEndReason = SplitTableEnums.getByCalendarMonth(Calendar.DECEMBER).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear - 1, middleStartReason, middleEndReason));
            //上一年
            int previousEndReason = SplitTableEnums.getByCalendarMonth(Calendar.DECEMBER).SEASON;
            this.suffixList.addAll(getSuffixForYear(initYear, initReason, previousEndReason));
        } else if (yearDiffValue >= 3) {
            //当年
            int startReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear, startReason, nowReason));
            //中间年
            int middleStartReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            int middleEndReason = SplitTableEnums.getByCalendarMonth(Calendar.DECEMBER).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear - 1, middleStartReason, middleEndReason));
            //上一年
            int previousStartReason = SplitTableEnums.getByCalendarMonth(Calendar.JANUARY).SEASON;
            int previousEndReason = SplitTableEnums.getByCalendarMonth(Calendar.DECEMBER).SEASON;
            this.suffixList.addAll(getSuffixForYear(nowYear - 2, previousStartReason, previousEndReason));
        }
    }


    public List<String> getSuffixList() {
        if (this.suffixList.size() > 9) {
            return this.suffixList.subList(0, 9);
        }

        return suffixList;
    }

    private List<String> getSuffixForYear(int year, int startReason, int endReason) {
        List<String> result = Lists.newArrayList();
        for (int i = endReason; i >= startReason; i--) {
            result.add("" + year + i);
        }

        return result;
    }


    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 0, 1);
        SplitTableBean bean = new SplitTableBean(calendar.getTime(), new Date());
        System.out.println(bean.getSuffixList());

        calendar.clear();
        calendar.set(2013, 1, 1);
        bean = new SplitTableBean(calendar.getTime(), new Date());
        System.out.println(bean.getSuffixList());

        calendar.clear();
        calendar.set(2010, 5, 1);
        bean = new SplitTableBean(calendar.getTime(), new Date());
        System.out.println(bean.getSuffixList());

        calendar.clear();
        calendar.set(2014, 1, 1);
        bean = new SplitTableBean(calendar.getTime(), new Date());
        System.out.println(bean.getSuffixList());

        calendar.clear();
        calendar.set(2014, 5, 1);
        bean = new SplitTableBean(calendar.getTime(), new Date());
        System.out.println(bean.getSuffixList());
    }
}
