package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 18:36
 */
public enum AutoRun {

    Y("Y", 1),
    N("N", 0);

    public String dbValue;
    public int infValue;

    AutoRun(String dbValue, int infValue) {
        this.dbValue = dbValue;
        this.infValue = infValue;
    }

    public static AutoRun getByDbValue(String dbValue) {
        if (StringUtils.isNotBlank(dbValue)) {
            for (AutoRun active : AutoRun.values()) {
                if (StringUtils.equalsIgnoreCase(active.dbValue, dbValue.trim())) {
                    return active;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String[] ss = new String[]{"7060              ",
                "7295C             ",
                "7298D             ",
                "8295M             ",
                "8720L             ",
                "8729              ",
                "A708t             ",
                "A760              ",
                "A766              ",
                "A850              ",
                "A880              ",
                "Coolpad_5217      ",
                "Coolpad_5310      ",
                "Coolpad_5951      ",
                "Coolpad_7232      ",
                "Coolpad_7296S     ",
                "Coolpad_7320      ",
                "Coolpad_8089      ",
                "Coolpad_8705      ",
                "Coolpad_8729      ",
                "G3502             ",
                "G620              ",
                "G7106             ",
                "G730-U00          ",
                "GT-I8558          ",
                "GT-I9152P         ",
                "GT-I9300I         ",
                "GT-N7108          ",
                "GT-S7568I         ",
                "GT-S7898I         ",
                "H30-U10           ",
                "HUAWEI_G6-T00     ",
                "HUAWEI_G6-U00     ",
                "HUAWEI_G610-T11   ",
                "HUAWEI_G610-U00   ",
                "HUAWEI_G630-U00   ",
                "HUAWEI_P7-L00     ",
                "HUAWEI_Y325-T00   ",
                "I8552             ",
                "I9118             ",
                "J936D             ",
                "L50U              ",
                "Lenovo_A300t      ",
                "Lenovo_A308t      ",
                "Lenovo_A316       ",
                "Lenovo_A378t      ",
                "Lenovo_A388t      ",
                "Lenovo_A560       ",
                "Lenovo_A670t      ",
                "Lenovo_A678t      ",
                "Lenovo_A788t      ",
                "Lenovo_A828t      ",
                "Lenovo_A850+      ",
                "Lenovo_A889       ",
                "Lenovo_A890e      ",
                "Lenovo_S658t      ",
                "Lenovo_S820e      ",
                "Lenovo_S960       ",
                "Lenovo_S968t      ",
                "N9002             ",
                "N9006             ",
                "N9008             ",
                "N9008V            ",
                "NX403A            ",
                "PHICOMM_C230w     ",
                "Q705U             ",
                "S39h              ",
                "S650              ",
                "S7278U            ",
                "S7562c            ",
                "S7572             ",
                "S898t             ",
                "SM-G3502I         ",
                "SM-G3508I         ",
                "SM-G3588V         ",
                "SM-G3818          ",
                "SM-G3819D         ",
                "SM-G9008V         ",
                "SM-G9008W         ",
                "SM-G9009D         ",
                "SM-N9008S         ",
                "TCL_S720T         ",
                "U5s               ",
                "U879              ",
                "VTEL_X1           ",
                "XM50h             ",
                "Y511              ",
                "Y511-T00          ",
                "ZTE_U960E         ",
                "vivo_X3L          ",
                "vivo_Y18L         ",
                "荣耀3C            "};

        String str = "insert into TY_MODEL_INFO(MODEL_ID,UA,MODEL_NAME,GROUP_ID,TAGNUM,TAGPRICE) values(SEQ_MODEL_INFO.NEXTVAL,''{0}'',''{1}'',{2},1,1);";


        for (int i = 0; i < ss.length; i++) {
            String temp = ss[i].trim();
            String[] os = new String[]{temp, temp, "1"};
            String[] os1 = new String[]{temp, temp, "2"};
            String[] os2 = new String[]{temp, temp, "3"};
            String[] os3 = new String[]{temp, temp, "4"};
            String t = MessageFormat.format(str, os);
            String t1 = MessageFormat.format(str, os1);
            String t2 = MessageFormat.format(str, os2);
            String t3 = MessageFormat.format(str, os3);

            System.out.println(t);
            System.out.println(t1);
            System.out.println(t2);
            System.out.println(t3);
        }
    }
}
