package com.ifhz.core.service.common.enums;

import java.util.Calendar;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 14:01
 */
public enum SplitTableEnums {
    JANUARY(Calendar.JANUARY, 1),
    FEBRUARY(Calendar.FEBRUARY, 1),
    MARCH(Calendar.MARCH, 1),
    APRIL(Calendar.APRIL, 2),
    MAY(Calendar.MAY, 2),
    JUNE(Calendar.JUNE, 2),
    JULY(Calendar.JULY, 3),
    AUGUST(Calendar.AUGUST, 3),
    SEPTEMBER(Calendar.SEPTEMBER, 3),
    OCTOBER(Calendar.OCTOBER, 4),
    NOVEMBER(Calendar.NOVEMBER, 4),
    DECEMBER(Calendar.DECEMBER, 4);

    public final int MONTH;
    public final int SEASON;

    SplitTableEnums(int MONTH, int SEASON) {
        this.MONTH = MONTH;
        this.SEASON = SEASON;
    }

    public static SplitTableEnums getByCalendarMonth(int month) {
        for (SplitTableEnums enums : SplitTableEnums.values()) {
            if (enums.MONTH == month) {
                return enums;
            }
        }

        return null;
    }
}
