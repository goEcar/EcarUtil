package urils.ecaray.com.ecarutils.Util5;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsUtil {
	public static String urlParse(String url) {
		int len1 = url.indexOf("&t=");
		int len2 = url.indexOf("&method=");
		if (len1 != -1 && len2 != -1) {
			String str1 = url.substring(len1 + 3, len2);
			String str2 = url.substring(len1, len2);
			url = url.replaceAll(str2, "");
			int len3 = url.indexOf("Index.aspx?");
			url = url.substring(0, len3) + str1 + "api/" + url.substring(len3);
		}
		url = url.replaceAll(" ", "%20").replaceAll("<", "%3C")
				.replaceAll(">", "%3E");
		return url;
	}
	/**
	 * @功能：对String 进行encoding 操作
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getDecodedStr(String str) {
		try {
			return java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	// 将二进制字符串转换为char
	public static char BinstrToChar(String binStr) {
		int[] temp = BinstrToIntArray(binStr);
		int sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;
	}

	// 将初始二进制字符串转换成字符串数组，以空格相隔
	public static String[] StrToStrArray(String str) {
		return str.split(" ");
	}

	// 将二进制字符串转换成int数组
	public static int[] BinstrToIntArray(String binStr) {
		char[] temp = binStr.toCharArray();
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = temp[i] - 48;
		}
		return result;
	}

	/**
	 * 去除EditText中的空字符
	 */
	public static String deleterTrim(String edit) {
//		String Str = "";
//
//		if (!TextUtils.isEmpty(edit)) {
//			for (int i = 0; i < edit.length(); i++) {
//				if (!edit.substring(i, i + 1).equals(" ")) {
//					Str = Str + edit.substring(i, i + 1);
//				}
//			}
//		}
//		return Str;

		if (TextUtils.isEmpty(edit))
			return null;
		return edit.replaceAll("\\s*", "");
	}
	/**
	 * 验证身份证是否合法
	 * 
	 * @功能：
	 * @param：身份证
	 * @return：true 合法
	 * @throws Exception
	 */
	public static boolean checkIdentityNumber(String identityNumber) {
		boolean flag = false;
		try {
			String check1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
			String check2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
			Pattern regex1 = Pattern.compile(check1);
			Pattern regex2 = Pattern.compile(check2);
			Matcher matcher1 = regex1.matcher(identityNumber);
			Matcher matcher2 = regex2.matcher(identityNumber);
			flag = matcher1.matches() || matcher2.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return true手机号正确
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^(1)\\d{10}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			Log.e("tag", "验证手机号码错误");
			flag = false;
		}
		return flag;
	}
	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return true手机号正确
	 */
	public static boolean isNumber(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[0-9]*$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			Log.e("tag", "验证银行卡错误");
			flag = false;
		}
		return flag;
	}
	/**
	 * 验证身份证号码
	 * 
	 * @param mobiles
	 * @return true手机号正确
	 */
	public static boolean isNumberOrChar(String number) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[A-Za-z0-9()]*$");
			Matcher m = p.matcher(number);
			flag = m.matches();
		} catch (Exception e) {
			Log.e("tag", " 验证身份证号码错误");
			flag = false;
		}
		return flag;
	}
	/**
	 * 验证车牌号(除前二位)是否合法
	 * 
	 * @功能：
	 * @param：车牌号
	 * @return：true 合法
	 * @throws Exception
	 */
	public static boolean checkPlateNumber(String plateNumber) {
		boolean flag = false;
		try {
			String check1 = "^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\\u4e00-\\u9fa5]$|^[a-zA-Z]{2}\\d{7}$";
			Pattern regex1 = Pattern.compile(check1);
			Matcher matcher1 = regex1.matcher(plateNumber);
			flag = matcher1.matches();
		} catch (Exception e) {
			Log.e("tag", "验证车牌号错误");
			flag = false;
		}
		return flag;
	}

	/**
	 * @throws Exception
	 * @功能：是否符合数字格式
	 * @param：
	 * @return：
	 */
	public static boolean checkAmout(String amount) {
		Pattern p = Pattern
				.compile("^([1-9]\\d*\\.?\\d{0,2})|(0\\.\\d{0,2})|([1-9]\\d*)|0$");
		Matcher m = p.matcher(amount);
		return m.matches();
	}
	
}
