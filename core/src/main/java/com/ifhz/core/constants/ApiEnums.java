package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/2
 * Time: 21:06
 */
public final class ApiEnums {


    public static enum UpdateType {
        Add(0, "A"),
        Modify(1, "M"),
        Delete(2, "D");

        public int VALUE;
        public String TYPE;

        UpdateType(int VALUE, String TYPE) {
            this.VALUE = VALUE;
            this.TYPE = TYPE;
        }

        public static int getfromType(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (UpdateType temp : UpdateType.values()) {
                    if (StringUtils.equalsIgnoreCase(type, temp.TYPE)) {
                        return temp.VALUE;
                    }
                }
            }

            return 0;
        }

        public static UpdateType getfromValue(int value) {
            for (UpdateType temp : UpdateType.values()) {
                if (value == temp.VALUE) {
                    return temp;
                }
            }
            return null;
        }
    }
}
