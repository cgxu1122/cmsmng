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
        String imei1 = "864545024785429|8729|1";
        String imei2 = "864545024785767|8729|1";
        String imei3 = "864545024785775|8729|2";
        String imei4 = "864545024786591|8729|2";
        String imei5 = "864545024837618|8729|3";
        String imei6 = "864545024838335|8729|3";
        String imei7 = "864545024838459|8729|4";
        String imei8 = "864545024838608|8729|4";
        String imei9 = "864545024839911|8729|4";
        String imei10 = "864545024791237|8729|4";

        System.out.println(decode(imei1));
        System.out.println(decode(imei2));
        System.out.println(decode(imei3));
        System.out.println(decode(imei4));
        System.out.println(decode(imei5));
        System.out.println(decode(imei6));
        System.out.println(decode(imei7));
        System.out.println(decode(imei8));
        System.out.println(decode(imei9));
        System.out.println(decode(imei10));


    }

    private CodecUtils() {
    }
}
