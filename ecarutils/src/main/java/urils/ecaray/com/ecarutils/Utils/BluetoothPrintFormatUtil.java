package urils.ecaray.com.ecarutils.Utils;

import android.annotation.SuppressLint;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 蓝牙打印,排版打印格式
 *
 * @author linjinfa@126.com
 * @date 2013-6-17 下午3:37:10
 */
@SuppressLint("NewApi")
public class BluetoothPrintFormatUtil {

	/**
	 * 打印纸一行最大的字节
	 */
	private static final int LINE_BYTE_SIZE = 16;
	/**
	 * 分隔符
	 */

	private static StringBuffer sb = new StringBuffer();

	/**
	 * 排版居中标题
	 *
	 * @param title
	 * @return
	 */
	public static String printTitle(String title) {
		sb.delete(0, sb.length());
		for (int i = 0; i < (LINE_BYTE_SIZE - getBytesLength(title)) / 2; i++) {
			sb.append(" ");
		}
		sb.append(title);
		return sb.toString();
	}

	/**
	 * 排版居中内容(以':'对齐)
	 *
	 * 例：姓名：李白 病区：5A病区 住院号：11111
	 *
	 * @param msg
	 * @return
	 */
	public static String printMiddleMsg(
			LinkedHashMap<String, String> middleMsgMap) {
		sb.delete(0, sb.length());
		for (Entry<String, String> middleEntry : middleMsgMap.entrySet()) {
			sb.append(middleEntry.getKey() + " : " + middleEntry.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}



	/**
	 * 获取数据长度
	 *
	 * @param msg
	 * @return
	 */
	private static int getBytesLength(String msg) {
		return msg.getBytes(Charset.forName("GB2312")).length;
	}

	/**
	 * 虚线
	 *
	 * @param title
	 * @return
	 */
	public static String getDash() {
		sb.delete(0, sb.length());
		for (int i = 0; i < LINE_BYTE_SIZE*2; i++) {
			sb.append("-");
		}
		return sb.toString();
	}


}
