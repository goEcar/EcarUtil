package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DBPreferenceUtil {
    private SharedPreferences dbs;
    private final String DB_PREFS_NAME = "com.preferences.dbs";

    public DBPreferenceUtil(Context context) {
        dbs = context.getSharedPreferences(DB_PREFS_NAME, 0);
    }

    /**
     * 设置默认维度
     *
     * @param str
     */
    public void setLongitude(String str) {
        Editor editor = dbs.edit();
        editor.putString("db_longitude", str);
        editor.commit();
    }

    /**
     * 获取默认维度
     *
     * @param str
     */
    public String getLongitude() {
        return dbs.getString("db_longitude", "0");
    }

    /**
     * 设置默认经度
     *
     * @param str
     */
    public void setLatitude(String str) {
        Editor editor = dbs.edit();
        editor.putString("db_Latitude", str);
        editor.commit();
    }

    /**
     * 获取默认维度
     *
     * @param str
     */
    public String getLatitude() {
        return dbs.getString("db_Latitude", "0");
    }

    /**
     * 设置默认城市
     *
     * @param str
     */
    public void setCity(String str) {
        Editor editor = dbs.edit();
        editor.putString("db_City", str);
        editor.commit();
    }

    /**
     * 设置默认城市
     *
     * @param str
     */
    public String getCity() {
        return dbs.getString("db_City", "");
    }

    /**
     * 设置推送状态
     *
     * @param str
     */
    public void setPushState(boolean bool) {
        Editor editor = dbs.edit();
        editor.putBoolean("db_PushState", bool);
        editor.commit();
    }

    /**
     * 获取推送状态
     *
     * @param str
     */
    public boolean getPushState() {
        return dbs.getBoolean("db_PushState", true);
    }

    /**
     * 设置Log 文件路径
     *
     * @param str
     */
    public void setLogFilePath(String str) {
        Editor editor = dbs.edit();
        editor.putString("db_FilePath", str);
        editor.commit();
    }

    /**
     * 获取Log 文件路径
     *
     * @param str
     */
    public String getLogFilePath() {
        return dbs.getString("db_FilePath", "");
    }
}
