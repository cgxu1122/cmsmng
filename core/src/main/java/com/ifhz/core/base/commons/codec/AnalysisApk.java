package com.ifhz.core.base.commons.codec;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/26
 * Time: 23:54
 */
public class AnalysisApk {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisApk.class);


    private static final float RADIX_MULTS[] = {
            0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F
    };
    private static final String DIMENSION_UNITS[] = {
            "px", "dip", "sp", "pt", "in", "mm", "", ""
    };
    private static final String FRACTION_UNITS[] = {
            "%", "%p", "", "", "", "", "", ""
    };


    public static String genApkPackageName(InputStream inputStream) {
        String result = null;
        File toFile = null;
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(MapConfig.getString(GlobalConstants.KEY_LOCAL_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app"));
            buffer.append(File.separator);
            buffer.append(new Date().getTime());
            buffer.append(".apk");
            toFile = new File(buffer.toString());
            ByteStreams.copy(inputStream, Files.newOutputStreamSupplier(toFile));
            Map<String, String> map = parseApk(toFile);
            if (MapUtils.isNotEmpty(map)) {
                result = map.get("fullPackagePath");
            }
        } catch (Exception e) {
            LOGGER.error("copyFile error", e);
        } finally {
            if (toFile != null) {
                toFile.deleteOnExit();
            }
        }

        return result;
    }

    /**
     * 解压 zip 文件(apk可以当成一个zip文件)，只能解压zip文件
     *
     * @param apkFile zip 文件
     * @return Map<String,String>
     * <p>
     * version:版本号;
     * fullPackagePath:包名;
     * icon :有即成功，无即失败
     * </p>
     * @throws IOException
     */
    public static Map<String, String> parseApk(File apkFile) {
        Map<String, String> result = Maps.newHashMap();
        //version:版本号;
        //fullPackagePath:包名;
        //icon :有即成功，无即失败
        byte b[] = new byte[1024];
        int length;
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(apkFile);
            Enumeration enumeration = zipFile.entries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                if (zipEntry.isDirectory()) {

                } else {
                    if (StringUtils.equalsIgnoreCase("AndroidManifest.xml", zipEntry.getName())) {
                        try {
                            AXmlResourceParser parser = new AXmlResourceParser();
                            parser.open(zipFile.getInputStream(zipEntry));
                            while (true) {
                                int type = parser.next();
                                if (type == XmlPullParser.END_DOCUMENT) {
                                    break;
                                }
                                switch (type) {
                                    case XmlPullParser.START_TAG: {
                                        for (int i = 0; i != parser.getAttributeCount(); ++i) {
                                            if ("versionName".equals(parser.getAttributeName(i))) {
                                                result.put("version", getAttributeValue(parser, i));
                                            } else if ("package".equals(parser.getAttributeName(i))) {
                                                result.put("fullPackagePath", getAttributeValue(parser, i));
                                            }
                                            if (LOGGER.isDebugEnabled()) {
                                                LOGGER.debug("{}={}", parser.getAttributeName(i), getAttributeValue(parser, i));
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            LOGGER.error("AnalysisApk AndroidManifest_xml error", e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("AnalysisApk error", e);
        }
        return result;
    }


    private static String getAttributeValue(AXmlResourceParser parser, int index) {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == TypedValue.TYPE_STRING) {
            return parser.getAttributeValue(index);
        }
        if (type == TypedValue.TYPE_ATTRIBUTE) {
            return String.format("?%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_REFERENCE) {
            return String.format("@%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_FLOAT) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == TypedValue.TYPE_INT_HEX) {
            return String.format("0x%08X", data);
        }
        if (type == TypedValue.TYPE_INT_BOOLEAN) {
            return data != 0 ? "true" : "false";
        }
        if (type == TypedValue.TYPE_DIMENSION) {
            return Float.toString(complexToFloat(data)) +
                    DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type == TypedValue.TYPE_FRACTION) {
            return Float.toString(complexToFloat(data)) +
                    FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return String.format("#%08X", data);
        }
        if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", data, type);
    }

    private static String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    /////////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
    public static float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
    }


    public static void main(String[] args) throws Exception {
        /*String apkPath = "D:\\qq.apk";
//        apkPath = "D:\\gedouzhiwang3.apk";
        apkPath = "D:\\1.apk";
        String iconSavePath = "D:\\1.png";
        Map<String, String> ret = parseApk(apkPath, iconSavePath);
        System.out.println(JSON.toJSONString(ret));

        apkPath = "D:\\2.apk";
        iconSavePath = "D:\\2.png";
        ret = parseApk(apkPath, iconSavePath);
        System.out.println(JSON.toJSONString(ret));*/

        /*apkPath = "D:\\3.apk";
        iconSavePath = "D:\\3.png";
        ret = parseApk(apkPath, iconSavePath);
        System.out.println(JSON.toJSONString(ret));*/

        File file = new File("D:\\\\gedouzhiwang3.apk");
        InputStream ins = new FileInputStream(file);

        System.out.println(genApkPackageName(ins));
    }
}
