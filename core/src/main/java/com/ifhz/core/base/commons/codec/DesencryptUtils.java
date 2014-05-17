package com.ifhz.core.base.commons.codec;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import javax.annotation.Nonnull;
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
    public static String md5(@Nonnull String value) {
        Preconditions.checkArgument(value != null, "value");
        HashFunction function = Hashing.md5();
        HashCode hashCode = function.newHasher()
                .putString(value, Charsets.UTF_8)
                .hash();

        return hashCode.toString();
    }

    /**
     * MD5 加密算法
     *
     * @param value   非空字符串
     * @param charset 编码格式
     * @return
     */
    public static String md5(@Nonnull String value, @Nonnull Charset charset) {
        Preconditions.checkArgument(value != null, "value");
        HashFunction function = Hashing.md5();
        HashCode hashCode = function.newHasher()
                .putString(value, charset)
                .hash();

        return hashCode.toString();
    }

    private DesencryptUtils() {
    }
}
