package urils.ecaray.com.ecarutils.Util1;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFormatUtil {
	// 添加字符串
	public static synchronized String addText(StringBuilder sb, String... texts) {
		sb.delete(0, sb.length());
		for (String str : texts) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * @功能：将byte转化为kb或者mb
	 * @param count
	 *            byte长度
	 * @return 小于1m返回kb，否则返回mb
	 */
	public static String byte2Mega(long count) {
		StringBuffer sbf = new StringBuffer();
		if (count < 1024 * 1024) {// 小于1m
			float total = (float) count / 1024;
			sbf.append(floatTo1p(total) + "KB");
		} else {
			float total = (float) count / 1024 / 1024;
			sbf.append(floatTo1p(total) + "MB");
		}

		return sbf.toString();
	}


	// float保留一位数字
	public static Float floatTo1p(float data) {
		BigDecimal bd = new BigDecimal(data);
		float num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		return num;
	}

	// float保留一位数字
	public static Float floatTo1pdown(float data) {
		BigDecimal bd = new BigDecimal(data);
		float num = bd.setScale(1, BigDecimal.ROUND_DOWN).floatValue();
		return num;
	}

	// double保留两位数字
	public static Double doubleTo2p(Double data) {
		BigDecimal bd = new BigDecimal(data);
		double num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}

	// double保留两位数字
	public static String doubleTo2pRTstr(Double data) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(data);
	}


	// 转为保留二位小数格式
	public static String formatData(double data) {
		DecimalFormat format = new DecimalFormat("##0.00");
		String formatData = format.format(data);
		return formatData;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int setDip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static StringBuffer md5(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf;
	}


	// 设置控件的宽 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static int setViewsWith(Context context, Double size, View view) {
		LayoutParams lp = view.getLayoutParams();
		int size1 = (int) (size * Util.getScreenWidth(context));
		lp.width = size1;
		return size1;

	}

	// 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static void setViewsHeightPx(Context context, int pxValue, View view) {
		LayoutParams lp = view.getLayoutParams();
		lp.height = pxValue;
	}

	// 设置控件的宽 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
	public static void setViewsWidthPx(Context context, int pxValue, View view) {
		LayoutParams lp = view.getLayoutParams();
		lp.width = pxValue;
	}

	/**
	 * @功能：对String 进行encoding 操作
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getEncodedStr(String str) {
		if(str==null){	
			return str;
		}
		String result = "";
		try {
			result = java.net.URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @功能：检查是否符手机格式
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static boolean isPhoneNum(String mobiles) {
		if (TextUtils.isEmpty(mobiles)){
			return false;
		}
		Pattern p = Pattern.compile("^[1][34578][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
