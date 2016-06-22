package urils.ecaray.com.ecarutils.Util3;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;


/**
 * ===============================================
 * <p/>
 * 项目名称: ShopHelper
 * <p/>
 * 包: com.shophelper.utils
 * <p/>
 * 类名称: AppUiUtil
 * <p/>
 * 类描述: 应用ui工具类
 * <p/>
 * 创建人: 金征
 * <p/>
 * 创建时间: 2014-11-18 上午9:47:56
 * <p/>
 * 修改人:
 * <p/>
 * 修改时间: 2014-11-18 上午9:47:56
 * <p/>
 * 修改备注:
 * <p/>
 * 版本:
 * <p/>
 * ===============================================
 */
public class AppUiUtil {
    //改变bar颜色
    private static WindowManager.LayoutParams windowAttributes;
    private static LayoutInflater mInflater;
    private static DisplayMetrics screenMetrics;


    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    static public int getScreenWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }

    static public int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    static public float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

}
