package urils.ecaray.com.ecarutils.Util1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ===============================================
 * 
 * 项目名称: ParkBeens
 * 
 * 包: com.chmtech.parkbeens.utils
 * 
 * 类名称: SpUtil
 * 
 * 类描述: 共享文件操作工具类
 * 
 * 创建人:
 * 
 * 创建时间: 2015-4-9 下午5:15:23
 * 
 * 修改人:
 * 
 * 修改时间: 2015-4-9 下午5:15:23
 * 
 * 修改备注:
 * 
 * 版本:
 * 
 * ===============================================
 */
public class SpUtil {
	private SharedPreferences sp;
	private Editor editor;
	private Context context;
	public static final String TAG = "PushDemoActivity";
	public static final String EXTRA_MESSAGE = "frist_location";

	public SpUtil(Context context, String fileName) {
		sp = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		editor = sp.edit();
	}

	// 保存值
	public void save(String key, Object value) {
		if (value instanceof Integer) {
			editor.putInt(key, (Integer) value).commit();
		} else if (value instanceof String) {
			editor.putString(key, (String) value).commit();
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value).commit();
		}
	}

	// 获取值 参数：cla:获取数据的类型
	public Object getData(String key, Class cla, Object obj) {
		String strContent;
		int intContent;
		boolean booleanContent;
		long longContent;
		if (cla == Integer.class) {
			intContent = sp.getInt(key, (int) obj);
			return intContent;
		} else if (cla == String.class) {
			strContent = sp.getString(key, (String) obj);
			return strContent;
		} else if (cla == Boolean.class) {
			booleanContent = sp.getBoolean(key, (boolean) obj);
			return booleanContent;
		} else if (cla == Long.class) {
			longContent = sp.getLong(key, (long) obj);
			return longContent;
		} else {
			return null;
		}
	}


}
