package sk.dudas.appengine.viacnezsperk.util;

import java.text.Normalizer;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 4.11.2012
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static String normalize(String value) {
        String str = Normalizer.normalize(value, Normalizer.Form.NFD);
        String dediacritized = str.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return dediacritized.toLowerCase();
    }
}
