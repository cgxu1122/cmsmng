package com.ifhz.core.base.commons.xml;

import com.google.common.io.Closeables;
import com.google.common.io.Resources;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;


/**
 * XML解析的工具类
 *
 * @author chenggang.xu@qunar.com created at 13-9-5 下午2:45
 * @version 1.0.0
 */
public final class JAXBUtil {
    private JAXBUtil() {

    }

    /**
     * 从指定的xml文件中解析出<code>T</code>类型的对象
     *
     * @param clazz
     * @param xmlInClassPath
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public static <T> T unmarshal(Class<T> clazz, String xmlInClassPath) {
        Reader reader = null;
        try {
            URL xmlUrl = Resources.getResource(xmlInClassPath);
            reader = new InputStreamReader(xmlUrl.openStream(), "UTF-8");
            JAXBContext context = JAXBContext.newInstance(clazz);
            return (T) context.createUnmarshaller().unmarshal(reader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unmarshal object for class:" + clazz + " xml:" + xmlInClassPath, e);
        } finally {
            Closeables.closeQuietly(reader);
        }
    }

    public static void marshal(Object obj, OutputStream out) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(obj, out);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
