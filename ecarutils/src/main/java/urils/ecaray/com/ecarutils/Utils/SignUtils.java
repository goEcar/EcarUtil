package urils.ecaray.com.ecarutils.Utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * 类描述：签名工具类
 *<p>
 */
public class SignUtils {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64Util.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64Util.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 拼接字符串：转码
     * 功能：获取请求url  1.拼接url 2.转码
     */
    public static String getUrlEncode(String baseUrl, TreeMap<String, String> tMap, String requestKey) {
        return DataFormatUtil.addText(new StringBuilder(), baseUrl,
                securityKeyMethodEnc(tMap,requestKey));
    }

    /**
     * 拼接字符串：不转码
     * 功能：获取请求url  1.拼接url 2.转码
     */
    public static String getUrlNoEncode(String baseUrl, TreeMap<String, String> tMap, String requestKey) {
        return DataFormatUtil.addText(new StringBuilder(), baseUrl,
                securityKeyMethodNoEnc(tMap,requestKey));
    }

    /**
     * @throws Exception
     * @功能：加密方法（加encode）
     * @param：
     * @return：
     */
    public static String securityKeyMethodEnc(TreeMap<String, String> tMap, String requestKey) {
        return getSecurityKeys(tMap, true,requestKey);
    }

    /**
     * @throws Exception
     * @功能：加密方法（不加encode）
     * @param：
     * @return：
     */
    public static String securityKeyMethodNoEnc(TreeMap<String, String> tMap, String requestKey) {
        return getSecurityKeys(tMap, false,requestKey);
    }

    /**
     * @throws Exception
     * @功能：获取拼接后的请求字符串
     * @param：encode:是否添加encode
     * @return：
     */
    public static String getSecurityKeys(TreeMap<String, String> tMap, boolean encode, String requestKey) {
        Set<String> keys = tMap.keySet();
        Iterator<String> iterator = keys.iterator();
        String parmas = "";
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = tMap.get(key);
            parmas = DataFormatUtil.addText(new StringBuilder(), parmas, key, "=", value, "&");
        }
        parmas = DataFormatUtil.addText(new StringBuilder(), parmas, "requestKey=",
                StringsUtil.BinstrToStr(requestKey));
//                requestKey);
        String md5 = MD5Util.md5(parmas).toString();
        tMap.remove("requestKey");
        Iterator<String> iterator1 = keys.iterator();
        parmas = "";
        while (iterator1.hasNext()) {
            String key = iterator1.next();
            String value = tMap.get(key);
            if (encode) {
                value = DataFormatUtil.getEncodedStr(value);
            }
            parmas = DataFormatUtil.addText(new StringBuilder(), parmas, key, "=", value, "&");
        }
        parmas = DataFormatUtil.addText(new StringBuilder(), parmas, "sign", "=", md5);
        return parmas;
    }

}
