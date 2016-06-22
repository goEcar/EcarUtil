package urils.ecaray.com.ecarutils.Util1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class MathsUtil {
	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	final static double DEF_PI = 3.14159265359; // PI
	final static double DEF_2PI = 6.28318530712; // 2*PI
	final static double DEF_PI180 = 0.01745329252; // PI/180.0
	final static double DEF_R = 6370693.5; // radius of earth

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
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
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
	
	public static String formatMoney(double d) {
		/*
		 * if(s.indexOf(".") > 0){ s = s.replaceAll("0+?$", "");//去掉多余的0 s =
		 * s.replaceAll("[.]$", "");//如最后一位是.则去掉 }
		 */
		DecimalFormat df2 = new DecimalFormat("########0.00");// 保留2位
		return df2.format(d);
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
	public static double GetShortDistance(double lon1, double lat1,
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

	/**
	 * DES解密算法
	 * 
	 * @param str
	 * @return
	 */
	public static String DEXDecryptString(String str) {
		try {
			str = Des3.decode(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TagUtil.showLogDebug("code="+str);
		return str;
	}
}
