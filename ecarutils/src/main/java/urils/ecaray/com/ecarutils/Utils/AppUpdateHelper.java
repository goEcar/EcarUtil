package urils.ecaray.com.ecarutils.Utils;

import android.annotation.SuppressLint;


/**
 * ===============================================
 * <p/>
 * 项目名称: ParkBees
 * <p/>
 * 包: com.chmtech.parkbees.helper
 * <p/>
 * 类名称: FileUpdateHelper
 * <p/>
 * 类描述:升级文件
 * <p/>
 * 创建人: 金征
 * <p/>
 * 创建时间: 2015-4-17 下午2:28:42
 * <p/>
 * 修改人:
 * <p/>
 * 修改时间: 2015-4-17 下午2:28:42
 * <p/>
 * 修改备注:
 * <p/>
 * 版本:
 * <p/>
 * ===============================================
 */
@SuppressLint("NewApi")
public class AppUpdateHelper {
    public static final String GET_UPDATA_FAIL = "100000";// 获取升级信息失败
    private static AppUpdateHelper fuh;
    public static final int FROM_MAIN_UPDATE = 0x20000;// 来自于首页升级(不提示无新版本提示)
    public static final int FROM_UPDATE_BUTTON = 0x20001;// 来自于升级按钮(提示无新版本提示)
    public static boolean isUpdatting;

    private AppUpdateHelper() {
        super();
        // TODO Auto-generated constructor stub
    }

    static public AppUpdateHelper getInstance() {
        if (fuh == null) {
            fuh = new AppUpdateHelper();
        }
        return fuh;
    }

}
