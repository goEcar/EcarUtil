package urils.ecaray.com.ecarutils.Utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 字符校验工具类
 * <P/>
 */
    public class VerifyUtil {
	final static String PLEASE_SELECT = "请选择...";

	/**
	 * 判断字符串的长度
	 *  <P>
	 * @param str
	 */
	public static boolean isCheckLength(String str, int start, int end) {
		Pattern pattern = Pattern.compile("^.{" + start + "," + end + "}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 验证密码格式, 只支持英文和数字.
	 *    <P>
	 * @param pwd
	 * @return
	 */
	public static boolean verifyPasswordFormat(String pwd) {
		return Pattern.compile("[a-zA-Z0-9]*").matcher(pwd).matches();
	}

	/**
	 * 验证密码格式, 必须是字母和数字的组合
	 *   <P>
	 * @param pwd
	 * @return
	 */
	public static boolean verifyPwdFormat(String pwd) {
		return Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$").matcher(pwd).matches();
	}

	/**
	 * 验证密码是否包含字母、数字或特殊字符中的两种或以上;
	 *   <P>
	 * @param pwd
	 * @return
	 */
	public static boolean verifyAllFormat(String pwd) {
		boolean flag = false;
		if ((Pattern.compile("[A-Za-z]*").matcher(pwd).matches())
				|| (Pattern.compile("[0-9]*").matcher(pwd).matches())
				|| (Pattern.compile("((?=[\\x21-\\x7e]+)[^A-Za-z0-9])*")
						.matcher(pwd).matches())) {

			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	/**
	 * 方法描述：验证手机号是否正确
	 *<p>
	 * @param
	 * @return
	 */
	public static boolean isMobileNO(String telephone) {
		if (TextUtils.isEmpty(telephone))
			return false;
		Pattern pattern = Pattern.compile("^1[3,4,5,6,7,8,9][0-9]\\d{8}$");
		Matcher matcher = pattern.matcher(telephone);
		return matcher.matches();
	}

	/**
	 * 验证短信验证码长度;
	 *      <P>
	 * @param 验证码
	 * @return
	 */
	public static boolean isSmsCode(String msgCode) {
		Pattern p = Pattern.compile("^\\d{6}$");
		Matcher m = p.matcher(msgCode);
		return m.matches();
	}

	public static String setDeccode(String strUT) {
		String strUTF8 = null;
		try {
			strUTF8 = URLDecoder.decode(strUT, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return strUTF8;
	}

	public static String setEncode(String strUT) {
		String strUTF8 = null;
		try {
			strUTF8 = URLEncoder.encode(strUT, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return strUTF8;
	}
}
