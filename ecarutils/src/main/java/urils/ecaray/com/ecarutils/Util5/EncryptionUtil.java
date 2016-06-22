package urils.ecaray.com.ecarutils.Util5;


/**
 * 3DES加密工具类
 */
public class EncryptionUtil {
	// 向量
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";



	// 将字符串转换成二进制字符串，以空格相隔
	public static String StrToBinstr(String str) {
		char[] strChar = str.toCharArray();
		String result = "";
		for (int i = 0; i < strChar.length; i++) {
			result += Integer.toBinaryString(strChar[i]) + " ";
		}
		return result;
	}

	// 将二进制字符串转换成Unicode字符串
	public static String BinstrToStr(String binStr) {
		String[] tempStr = StringsUtil.StrToStrArray(binStr);
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++) {
			tempChar[i] = StringsUtil.BinstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
	}
}
