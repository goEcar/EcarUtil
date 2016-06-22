package urils.ecaray.com.ecarutils.Util7;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MathsUtil {
	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	final static double DEF_PI = 3.14159265359; // PI
	final static double DEF_2PI = 6.28318530712; // 2*PI
	final static double DEF_PI180 = 0.01745329252; // PI/180.0
	final static double DEF_R = 6370693.5; // radius of earth

	/**
	 * get请求url加密
	 * 
	 * @param url
	 * @return
	 */
//	public static String getEncryptionValuePair2(String url) {
//		try {
//			List<NameValuePair> pairs = getUrlParameters(url);
//			String value = "";
//			for (int i = 0; i < pairs.size(); i++) {
//				NameValuePair pair = pairs.get(i);
//				if (pair.getName().equals("method")) {
//					value = pair.getValue().toLowerCase();
//				}
//			}
//			value = getMD5Code(EncryptionUtil
//					.binaryStrToStr(UserInfo.SIGNKEY) + value);
//			url += ("&appkey="
//					+ EncryptionUtil.binaryStrToStr(UserInfo.APP_KEY)
//					+ "&security=" + value + "&mobilecode="
//					+ Environments.getIMEI() + "&timestamp="
//					+ getSystemTimestamp() + "&SID=" + SettingPreferences.getDefault()
//					.getSid());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			TagUtil.showLogDebug(e.toString());
//		}
//		return getEncryptionUrl(url);
//	}

	public static String getEncryptionValuePair(String url) {
//		try {
//			List<NameValuePair> pairs = getUrlParameters(url);
//			for (int i = 0; i < pairs.size(); i++) {
//				NameValuePair pair = pairs.get(i);
//				if (pair.getName().equals("method")) {
//					String value = pair.getValue().toLowerCase();
//					value = getMD5Code(EncryptionUtil
//							.binaryStrToStr(Constants.SIGNKEY) + value);
//					url += ("&appkey="
//							+ EncryptionUtil.binaryStrToStr(Constants.APP_KEY)
//							+ "&security=" + value + "&mobilecode="
//							+ Environments.getIMEI() + "&timestamp="
//							+ getSystemTimestamp() + "&SID=" + SettingPreferences.getDefault(ParkApplication.getInstance())
//							.getSid());
//				}
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			TagUtil.showLogDebug(e.toString());
//		}
//		return getEncryptionUrl(url);
		return url;
	}
	/**
	 * get请求url加密
	 * 
	 * @param url
	 * @return
	 */
	public static String getEncryptionUrl(String url) {
//		List<String> listStr = new ArrayList<String>();
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			List<NameValuePair> pairs = getUrlParameters(url);
//			for (int i = 0; i < pairs.size(); i++) {
//				NameValuePair pair = pairs.get(i);
//				listStr.add(pair.getName());
//				String key = pair.getValue();
//				map.put(pair.getName(), key);
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String[] names = new String[listStr.size()];
//		listStr.toArray(names);
//		Comparator cmp = Collator.getInstance(java.util.Locale.ENGLISH);
//		Arrays.sort(names, cmp);
//		String str = "";
//		for (int j = 0; j < names.length; j++) {
//			if (j == 0)
//				str += (names[j] + "=" + map.get(names[j]));
//			else
//				str += ("&" + names[j] + "=" + map.get(names[j]));
//		}
//		// TagUtil.showLogDebug("排序后的请求参数--"+str);
//		String code = str + EncryptionUtil.binaryStrToStr(Constants.SIGNKEY);
//		try {
//			code = new String(code.getBytes(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			TagUtil.showLogError(e.toString());
//		}
//		TagUtil.showLogDebug("code=" + code);
//		String sign = "&sign=" + getMD5Code(code);
//		return (url += sign);
		return url;
	}

	/**
	 * 支付宝get请求url加密
	 * 
	 * @param url
	 * @return
	 */
	public static String getAlipayEncryptionUrl(String url) {
//		// url += "&SID=" + ParkApplication.SID;
//		List<String> listStr = new ArrayList<String>();
//		Map<String, String> map = new HashMap<String, String>();
//		try {
//			List<NameValuePair> pairs = getUrlParameters(url);
//			for (int i = 0; i < pairs.size(); i++) {
//				NameValuePair pair = pairs.get(i);
//				listStr.add(pair.getName());
//				String key = pair.getValue();
//				map.put(pair.getName(), key);
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String[] names = new String[listStr.size()];
//		listStr.toArray(names);
//		Comparator cmp = Collator.getInstance(java.util.Locale.ENGLISH);
//		Arrays.sort(names, cmp);
//		String str = "";
//		for (int j = 0; j < names.length; j++) {
//			if (j == 0)
//				str += (names[j] + "=" + map.get(names[j]));
//			else
//				str += ("&" + names[j] + "=" + map.get(names[j]));
//		}
//		TagUtil.showLogDebug("排序后的请求参数--" + str);
//		String code = str.concat(Constants.ALIPAY_KEY);
//		try {
//			code = new String(code.getBytes(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			TagUtil.showLogError(e.toString());
//		}
//		String sign = "&sign=" + getMD5Code(code);
//		return (url += sign);
		return url;
	}

	/**
	 * 将Url地址转化为请求参数键值对
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	public static List<NameValuePair> getUrlParameters(String url)
//			throws UnsupportedEncodingException {
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		String[] urlParts = url.split("\\?");
//		if (urlParts.length > 1) {
//			String query = urlParts[1];
//			for (String param : query.split("&")) {
//				String pair[] = param.split("=");
//				String key = URLDecoder.decode(pair[0], "UTF-8");
//				String value = "";
//				if (pair.length > 1) {
//					value = URLDecoder.decode(pair[1], "UTF-8");
//				}
//				if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value)))
//					params.add(new BasicNameValuePair(key, value));
//			}
//		}
//		return params;
//	}


	/**
	 * 将Url地址转化为请求参数键值对
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String,String> getUrlMaps(String url)
			throws UnsupportedEncodingException {
		Map<String,String> multipartEntity = new HashMap<String,String>();
		String[] urlParts = url.split("\\?");
		if (urlParts.length > 1) {
			String query = urlParts[1];
			for (String param : query.split("&")) {
				String pair[] = param.split("=");
				String key = URLDecoder.decode(pair[0], "UTF-8");
				String value = "";
				if (pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8");
				}
				if ((!TextUtils.isEmpty(key)) && (!TextUtils.isEmpty(value)))
					multipartEntity.put(key, value);
			}
		}
		return multipartEntity;
	}

	/**
	 * MD5加密算法
	 * 
	 * @param strObj
	 * @return
	 */
	public static String getMD5Code(String strObj) {
//		String resultString = null;
		try {
//			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			strObj = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return strObj;
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	// 返回形式为数字跟字符串
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

	/**
	 * 格式化金额数字
	 * 
	 * @param s
	 * @return
	 */
	public static String formatMoneyData(String s) {
		/*
		 * if(s.indexOf(".") > 0){ s = s.replaceAll("0+?$", "");//去掉多余的0 s =
		 * s.replaceAll("[.]$", "");//如最后一位是.则去掉 }
		 */
		if (s == null || s.equals(""))
			return "0.00";
		if (s.indexOf(",") != -1)
			return s;
		DecimalFormat df2 = new DecimalFormat("###,###,##0.00");// 保留2位
		return df2.format(Double.parseDouble(s));
	}
	
	/**
	 * 格式化金额数字
	 * 
	 * @param s
	 * @return
	 */
	public static String formatMoneyDataNumber(String s) {
		/*
		 * if(s.indexOf(".") > 0){ s = s.replaceAll("0+?$", "");//去掉多余的0 s =
		 * s.replaceAll("[.]$", "");//如最后一位是.则去掉 }
		 */
		if (s == null || s.equals(""))
			return "0.00";
		if (s.indexOf(",") != -1)
			return s;
		DecimalFormat df2 = new DecimalFormat("########0.00");// 保留2位
		return df2.format(Double.parseDouble(s));
	}
	public static double getDouble(String str){
		double result = 0.00;
		result = Double.parseDouble(str);
		return result;
	}
	/**
	 * 格式化金额数字
	 *
	 * @param
	 * @return
	 */
	public static String formatMoneyDataNumber(double num) {
		String s = num +"";
		/*
		 * if(s.indexOf(".") > 0){ s = s.replaceAll("0+?$", "");//去掉多余的0 s =
		 * s.replaceAll("[.]$", "");//如最后一位是.则去掉 }
		 */
		if (s == null || s.equals(""))
			return "0.00";
		if (s.indexOf(",") != -1)
			return s;
		DecimalFormat df2 = new DecimalFormat("########0.00");// 保留2位
		return df2.format(Double.parseDouble(s));
	}

	/**
	 * 格式化距离
	 * 
	 * @param s
	 * @return
	 */
	public static String formatDistanceData(String s) {
		/*
		 * if(s.indexOf(".") > 0){ s = s.replaceAll("0+?$", "");//去掉多余的0 s =
		 * s.replaceAll("[.]$", "");//如最后一位是.则去掉 }
		 */
		if (s == null || s.equals(""))
			return "0";
		DecimalFormat df2 = new DecimalFormat("########0.0");// 保留2位
		return df2.format(Double.parseDouble(s));
	}

	/**
	 * 格式化时间不带秒
	 */
	public static String timeFormat(String time) {
		if (time.equals("") || time == null) {
			return "";
		} else {
			if (time.indexOf(" ") != -1) {
				time = time.substring(time.indexOf(" ") + 1, time.length());
			}
			time = time.substring(0, time.lastIndexOf(":"));
			return time;
		}

	}

	/**
	 * 格式化时间带秒
	 * 截取时分秒的数据 例：10:45:14
	 */
	public static String timeFormatMin(String time) {
		if (TextUtils.isEmpty(time)) {
			return "";
		} else {
			if (time.indexOf(" ") != -1) {
				time = time.substring(time.indexOf(" ") + 1, time.length());
			}
			return time;
		}
	}

	/**
	 * 获取两坐标点之间的距离
	 * 
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return
	 */
	public static double getShortDistance(double lon1, double lat1,
										  double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public static String formateTime(String times) {
		String str = "";
		int time = Integer.parseInt(times);

		if (time % 60 == 0) {
			str = time / 60 + "小时";
		} else if (time > 60) {
			str = time / 60 + "小时" + time % 60 + "分钟";
		} else {
			str = time + "分钟";
		}

		return str;
	}

	/**
	 * 
	 * 登录密码验证正则
	 */
	public static boolean regatRegax(String str) {
		boolean chagflag = false;
		for (int i = 0; i < str.length(); i++) {
			String strOne = str.substring(i, i + 1);
			if (strOne.matches("^[0-9a-zA-Z]+$")) {
				chagflag = true;
				break;
			}
		}
		boolean flag = str.matches("[\\x20-\\x7E]*") && chagflag
				&& !str.matches("^[0-9]+$") && !str.matches("^[a-zA-Z]+$");

		return flag;
	}

	/**
	 *
	 * 登录密码验证正则
	 */
	public static boolean checkPassword(String str) {
		boolean chagflag = false;
		for (int i = 0; i < str.length(); i++) {
			String strOne = str.substring(i, i + 1);
			if (strOne.matches("^[0-9a-zA-Z]+$")) {
				chagflag = true;
				break;
			}
		}
		return chagflag;
	}

	/**
	 *
	 * 登录密码验证正则
	 */
	public static boolean checkPass(String str) {
		boolean chagflag = true;
		for (int i = 0; i < str.length(); i++) {
			String strOne = str.substring(i, i + 1);
			if (!strOne.matches("^[0-9a-zA-Z]+$")) {
				chagflag = false;
				break;
			}
		}
		return chagflag;
	}

	/**
	 * 格式化日期
	 */
	public static String formatDate2(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date da = df.parse(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(da);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 格式化日期
	 */
	public static String formatDate(String date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
			Date da = df.parse(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(da);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}


	/**
	 * @功能：对String 进行encoding 操作
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getEncodedStr(String str) {
		try {
			return java.net.URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static synchronized String addText(StringBuilder sb, String... texts) {
		sb.delete(0, sb.length());
		for (String str : texts) {
			sb.append(str);
		}
		return sb.toString();
	}


	/**
	 * 微信
	 * @param buffer
	 * @return
	 */
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * MD5加密算法
	 *
	 * @param strObj
	 * @return
	 */
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

}
