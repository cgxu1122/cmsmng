package com.ifhz.core.base.commons.codec;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.Charset;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 14-2-14
 * Time: 上午11:29
 */
public final class DesencryptUtils {

    /**
     * MD5加密算法(默认编码UTF-8)
     *
     * @param value 非空字符串
     * @return
     */
    public static String md5Str(@Nonnull String value) {
        return md5Str(value, Charsets.UTF_8);
    }

    /**
     * MD5 加密算法
     *
     * @param value   非空字符串
     * @param charset 编码格式
     * @return
     */
    public static String md5Str(@Nonnull String value, @Nonnull Charset charset) {
        Preconditions.checkArgument(value != null, "value");
        return Hashing.md5().hashString(value, charset).toString();
    }

    /**
     * MD5 加密算法
     *
     * @param file 加密文件
     * @return
     */
    public static String md5File(@Nonnull File file) throws Exception {
        Preconditions.checkArgument(file != null, "value");
        if (file.exists() && file.isFile()) {
            return Files.hash(file, Hashing.md5()).toString();
        }
        throw new Exception("file must be exists and must be file");
    }

    private DesencryptUtils() {
    }
}
