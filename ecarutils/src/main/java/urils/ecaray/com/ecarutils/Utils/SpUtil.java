package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * <p/>
 * 类描述: 共享文件操作工具类
 * <p/>
 */
public class SpUtil {
    private SharedPreferences sp;
    private Editor editor;
    private Context context;
    public static final String TAG = "PushDemoActivity";
    public static final String RESPONSE_METHOD = "method";
    public static final String RESPONSE_CONTENT = "content";
    public static final String RESPONSE_ERRCODE = "errcode";
    protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
    public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
    public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
    public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
    protected static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_MESSAGE = "frist_location";
    public static String logStringCache = "";

    public SpUtil(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        editor = sp.edit();
    }


    /**
     * 方法描述：  保存值
     *<p>
     * @param
     * @return
     */
    public void save(String key, Object value) {
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value).apply();
        } else if (value instanceof String) {
            editor.putString(key, value == null ? "" : (String) value).apply();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value).apply();
        }
    }

    /**
     * 方法描述：/ 获取值 参数：cla:获取数据的类型
     *<p>
     * @param
     * @return
     */
    public Object getData(String key, Class cla, Object obj) {
        String strContent;
        int intContent;
        boolean booleanContent;
        long longContent;
        if (cla == Integer.class) {
            intContent = sp.getInt(key, (Integer) obj);
            return intContent;
        } else if (cla == String.class) {
            strContent = sp.getString(key, (String) obj);
            return strContent;
        } else if (cla == Boolean.class) {
            booleanContent = sp.getBoolean(key, (Boolean) obj);
            return booleanContent;
        } else if (cla == Long.class) {
            longContent = sp.getLong(key, (Long) obj);
            return longContent;
        } else {
            return null;
        }
    }


    /**
     * 方法描述：  // 获取ApiKey
     *<p>
     * @param
     * @return
     */
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
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }



}
