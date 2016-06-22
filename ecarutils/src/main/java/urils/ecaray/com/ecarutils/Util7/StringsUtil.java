package urils.ecaray.com.ecarutils.Util7;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

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
	public static char binaryStrToChar(String binStr) {
		int[] temp = binaryStrToIntArray(binStr);
		int sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;
	}

	// 将初始二进制字符串转换成字符串数组，以空格相隔
	public static String[] strToStrArray(String str) {
		return str.split(" ");
	}

	// 将二进制字符串转换成int数组
	public static int[] binaryStrToIntArray(String binStr) {
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
		StringBuilder Str = new StringBuilder();

		if (!TextUtils.isEmpty(edit)) {
			for (int i = 0, len = edit.length(); i < len; i++) {
				if (!edit.substring(i, i + 1).equals(" ")) {
					Str.append(edit.substring(i, i + 1));
				}
			}
		}
		return Str.toString();
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
	 * @param
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
	 * @param
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
			String check1 = "[a-zA-Z_0-9]{4}[a-zA-Z_0-9]?[a-zA-Z_0-9_\\u4e00-\\u9fa5]$|^[a-zA-Z]{2}\\d{7}$";
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
	// 获取ApiKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (Exception e) {
		}
		return apiKey;
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
	 * 计算停车时长
	 */
	public static String calcuAcTimeLength(String timeLength){
//		long timeInt = Long.parseLong(timeLength);
//		String applyActualDuration = "";
//		//以秒的计算
//		int day = (int) (timeInt / (60*60 *24 *1000));
//		int hour = (int) ((timeInt % (60*60*24 *1000)) / (60*60*1000));
//		int minute = (int) (timeInt % 60*1000)/(1000);
////		//以分钟的计算
////		int day = (int) (timeInt / (60 *24));
////		int hour = (int) ((timeInt % (60*24)) / (60));
////		int minute = (int) (timeInt % 60);
//		if( day>0){
//			applyActualDuration += day+ "天";
//		}
//		if( hour>0){
//			applyActualDuration += hour+ "小时";
//		}
//		if(minute >0){
//			applyActualDuration += minute + "分钟";
//		}
//		if(TextUtils.isEmpty(applyActualDuration)){
//			applyActualDuration +=  "0分钟";
//		}
//		return applyActualDuration;
		return timeLength;
	}

	/**
	 * 是否为空格字符串
	 * @param cs
	 * @return
     */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if(cs != null && (strLen = cs.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if(!Character.isWhitespace(cs.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

}
