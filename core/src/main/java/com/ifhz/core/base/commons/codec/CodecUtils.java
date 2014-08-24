package com.ifhz.core.base.commons.codec;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 0:13
 */
public final class CodecUtils {
    private static byte KEY = 0x12;

    public static String decode(String source) {
        if (StringUtils.isNotBlank(source)) {
            char[] charArray = source.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] ^= KEY;
            }
            return new String(charArray);
        }

        return source;
    }

    public static void main(String[] args) throws Exception {
        /*String dd = "2014-07-05 12:30:00";
        Date date = DateFormatUtils.parse(dd, GlobalConstants.DEFAULT_DATE_FORMAT);

        String str = decode("864545023839806|MI MU|7|C4:46:19:73:37:A7|TY2|" + date.getTime());
        JSONArray array = new JSONArray();
        array.add(str);
        System.out.println(array.toJSONString());
        System.out.println(decode("864545023839805|MI MU|7|C4:46:19:73:37:A7|TY2|" + new Date().getTime()));
        System.out.println(decode("*$&'&'\" !*!+*\"'n_[2_Gn%nQ&(&$(#+(%!(!%(S%nFK n#&\"&'!'%+%%$&"));

*/
        ////手机imei|手机ua|到达状态
        String imei1 = "352956061737216|SM-G3588V|1";
        String imei2 = "DB11112|8729|2";
        String imei3 = "DB11113|8729|3";
        String imei10 = "DB11114|8729|4";

        System.out.println(decode(imei1));
        System.out.println(decode(imei2));
        System.out.println(decode(imei3));
        System.out.println(decode(imei10));


    }

    private CodecUtils() {
    }
}
