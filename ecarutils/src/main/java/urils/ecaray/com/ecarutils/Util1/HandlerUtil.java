package urils.ecaray.com.ecarutils.Util1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * ===============================================
 * 
 * 项目名称: ParkBeens
 * 
 * 包: com.chmtech.parkbeens.utils
 * 
 * 类名称: HandlerUtil
 * 
 * 类描述: handler的工具类
 * 
 * 创建人: 金征
 * 
 * 创建时间: 2015-4-14 下午2:18:13
 * 
 * 修改人:
 * 
 * 修改时间: 2015-4-14 下午2:18:13
 * 
 * 修改备注:
 * 
 * 版本:
 * 
 * ===============================================
 */
public class HandlerUtil {
	// 发送空的message
	public static void sendMessage(Handler handler, int what) {
		Message msg =Message.obtain();
		msg.what = what;
		handler.sendEmptyMessage(what);
	}

	// 发送带obj的message
	public static void sendMessage(Object obj,int what,Handler handler) {
		Message msg =Message.obtain();
		msg.what = what;
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	// 发送带bundle的message
	public static void sendMessage(Handler handler, int what, Bundle bundle) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
}
