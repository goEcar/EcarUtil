package urils.ecaray.com.ecarutils.Utils;

/**
 *
 */
public class MathsUtil {
    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    final static double DEF_PI = 3.14159265359; // PI
    final static double DEF_2PI = 6.28318530712; // 2*PI
    final static double DEF_PI180 = 0.01745329252; // PI/180.0
    final static double DEF_R = 6370693.5; // radius of earth

    /**
     * 方法描述： 转换字节数组为16进制字串
     *<p>
     * @param 需要转换的byte
     * @return 转换后的结果
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    // 返回形式为数字跟字符串
    /**
     * 方法描述：
     *<p>
     * @param
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

}
