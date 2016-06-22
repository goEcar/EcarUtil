package urils.ecaray.com.ecarutils.Util7;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return true 邮箱名正确
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			Log.e("tag", "验证邮箱地址错误");
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
	public static boolean isMobileNO1(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^((13)|(15)|(18)|(17))\\d{9}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			Log.e("tag", "验证手机号码错误");
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
		//[a-zA-Z_0-9]{4}[a-zA-Z_0-9]?[a-zA-Z_0-9_\u4e00-\u9fa5]$|^[a-zA-Z]{2}\d{7}$
		boolean flag = false;
		try {
			String check1 = "[a-zA-Z_0-9]{4}[a-zA-Z_0-9_\\u4e00-\\u9fa5]$";
			Pattern regex1 = Pattern.compile(check1);
			Matcher matcher1 = regex1.matcher(plateNumber);
			flag = matcher1.matches();
		} catch (Exception e) {
			Log.e("tag", "验证车牌号错误");
			flag = false;
		}
		return flag;
	}

	// 将二进制字符串转换成Unicode字符串
	public static String BinstrToStr(String binStr) {
		String[] tempStr = StrToStrArray(binStr);
		char[] tempChar = new char[tempStr.length];
		for (int i = 0; i < tempStr.length; i++) {
			tempChar[i] = BinstrToChar(tempStr[i]);
		}
		return String.valueOf(tempChar);
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
	 * @功能：高亮显示搜索关键词
	 * @param：textView:需要设置的textView searchKey:关键词 color:高亮显示的颜色 text:需要设置的句子
	 * @return：
	 * @throws Exception
	 */
	public static void setSearchKey(TextView textView, String text,
									String searchKey, int color) {
		SpannableString s = new SpannableString(text);
		if (searchKey.equals("*")) {
			searchKey = "\\*";
		} else if (searchKey.contains("*") || searchKey.contains(".")) {
			searchKey = searchKey.replaceAll("\\*", "");
			searchKey = searchKey.replaceAll("\\.", "");
		}
		Pattern p = Pattern.compile(searchKey);
		Matcher m = p.matcher(s);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(color), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		textView.setText(s);

	}

	/**
	 * @功能：匹配是否为中文
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static boolean checkIsChines(String str) {
		boolean flag = false;
		try {
			String check = "^[\u4e00-\u9fa5]+$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}

	private static boolean isCurrentAccount(char account) {
		if (((int) account >= 0 && (int) account <= 47)
				|| ((int) account >= 58 && (int) account <= 64)
				|| ((int) account >= 91 && (int) account <= 96)
				|| ((int) account >= 123 && (int) account <= 127)
				|| (int) account == 65292 || (int) account == 12290
				|| (int) account == 65311 || (int) account == 65281
				|| (int) account == 8220 || (int) account == 65306
				|| (int) account == 65307) {
			return true;
		}
		return false;
	}

	public static boolean isAccount(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (isCurrentAccount(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @功能：特殊字符过滤
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getNoneSign(String text) {
		String filterchars = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？\"]";
		Pattern p = Pattern.compile(filterchars);
		Matcher m = p.matcher(text);
		String result=m.replaceAll("").trim();
		return result;
	}
	

	/**
	 * 检测是否有emoji表情
	 * 
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 * 
	 * @param codePoint
	 *            比较的单个字符
	 * @return
	 */
	public static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) ||

		(codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
}
