package com.ifhz.core.service.pkgmng.constants;

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

        public static int getValuefromType(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (UpdateType temp : UpdateType.values()) {
                    if (StringUtils.equalsIgnoreCase(type, temp.TYPE)) {
                        return temp.VALUE;
                    }
                }
            }
            return 0;
        }
    }

    public enum AutoRun {
        Y(0, "Y"),//安装完启动
        N(1, "N");//安装完不启动

        public int VALUE;
        public String TYPE;

        AutoRun(int VALUE, String TYPE) {
            this.VALUE = VALUE;
            this.TYPE = TYPE;
        }

        public static int getValuefromType(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (AutoRun temp : AutoRun.values()) {
                    if (StringUtils.equalsIgnoreCase(type, temp.TYPE)) {
                        return temp.VALUE;
                    }
                }
            }
            return 0;
        }
    }


    public enum DesktopIcon {
        Y(0, "Y"),//创建桌面图标
        N(1, "N");//不创建桌面图标

        public int VALUE;
        public String TYPE;

        DesktopIcon(int VALUE, String TYPE) {
            this.VALUE = VALUE;
            this.TYPE = TYPE;
        }

        public static int getValuefromType(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (DesktopIcon temp : DesktopIcon.values()) {
                    if (StringUtils.equalsIgnoreCase(type, temp.TYPE)) {
                        return temp.VALUE;
                    }
                }
            }
            return 0;
        }
    }

    public enum CounterApp {
        Y(0, "Y"),//计数器
        N(1, "N");//不是计数器

        public int VALUE;
        public String TYPE;

        CounterApp(int VALUE, String TYPE) {
            this.VALUE = VALUE;
            this.TYPE = TYPE;
        }

        public static int getValuefromType(String type) {
            if (StringUtils.isNotBlank(type)) {
                for (CounterApp temp : CounterApp.values()) {
                    if (StringUtils.equalsIgnoreCase(type, temp.TYPE)) {
                        return temp.VALUE;
                    }
                }
            }
            return 0;
        }
    }
}
