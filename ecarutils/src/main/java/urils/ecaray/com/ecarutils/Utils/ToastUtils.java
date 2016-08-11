
package urils.ecaray.com.ecarutils.Utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Toast处理工具类
 *
 * <ul>
 *     显示文本的Toast
 *     <li>{@link #showToast(Context, String, int)} 显示Toast</li>
 *     <li>{@link #showToastLong(Context, String)} 显示长时间的Toast</li>
 *     <li>{@link #showToastShort(Context, String)} 显示短时间的Toast</li>
 * </ul>
 * <ul>
 *     显示资源id的Toast
 *     <li>{@link #showToast(Context, int, int)} 显示Toast</li>
 *     <li>{@link #showToastLong(Context, int)} 显示长时间的Toast</li>
 *     <li>{@link #showToastShort(Context, int)} 显示短时间的Toast</li>
 * </ul>
 */
public class ToastUtils {
  private static Application appContext;

    private ToastUtils() {}

    public static void init(Application app){
        appContext=app;
    }
    /**
     * 显示时间短的Toast
     * @param msg 显示的内容
     * */
    public static Toast showToastShort(String msg) {
        return showToast(msg, Toast.LENGTH_SHORT);
    }
    /**
     * 显示时间短的Toast
     * @param context 上下文
     * @param msg 显示的内容
     * */
    public static Toast showToastShort(Context context, String msg) {
        return showToast(context, msg, Toast.LENGTH_SHORT);
    }
    /**
     * 显示时间短的Toast
     * @param resId 显示的资源ID
     * */
    public static Toast showToastShort(int resId) {
        return showToast( resId, Toast.LENGTH_SHORT);
    }


    /**
     * 显示时间短的Toast
     * @param context 上下文
     * @param resId 显示的资源ID
     * */
	public static Toast showToastShort(Context context, int resId) {
		return showToast(context, resId, Toast.LENGTH_SHORT);
	}



    /**
     * 显示时间长的Toast
     * @param msg 显示的内容
     * */
    public static Toast showToastLong(String msg) {
        return showToast(msg, Toast.LENGTH_LONG);
    }


    /**
     * 显示时间长的Toast
     * @param context 上下文
     * @param msg 显示的内容
     * */
    public static Toast showToastLong(Context context, String msg) {
        return showToast(context, msg, Toast.LENGTH_LONG);
    }


    /**
     * 显示时间长的Toast
     * @param resId 显示的资源ID
     * */
    public static Toast showToastLong(int resId) {
        return showToast(resId, Toast.LENGTH_LONG);
    }


    /**
     * 显示时间长的Toast
     * @param context 上下文  ,resId ：String资源id
     * @param resId 显示的资源ID
     * */
	public static Toast showToastLong(Context context, int resId) {
        return showToast(context, resId, Toast.LENGTH_LONG);
	}



    /**
     * 显示Toast，自动处理主线程与非主线程
     * @param context 上下文    resId ：String资源id
     * @param resId 显示的资源ID
     * @param duration 时长
     * */
    private static Toast showToast(Context context, int resId, int duration) {
        if(context == null) {
            return null;
        }
        String text = context.getString(resId);
        return showToast(context, text, duration);
    }

    /**
     * 显示Toast，自动处理主线程与非主线程
     * @param context 上下文
     * @param text 要显示的toast内容
     * @param duration 时长
     * */
    private static Toast showToast(final Context context, final String text, final int duration) {
        if(context == null) {
            return null;
        }
        if(Looper.getMainLooper() == Looper.myLooper()) {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return toast;
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
        return null;
    }

    /**
     * 显示Toast，自动处理主线程与非主线程
     * @param resId 显示的资源ID
     * @param duration 时长
     * */
    private static Toast showToast(int resId, int duration) {
        if(appContext==null){
            Log.e("ToastUtil","未初始化ToastUtil");
            return null;
        }
        String text = appContext.getString(resId);
        return showToast(appContext, text, duration);
    }

    /**
     * 显示Toast，自动处理主线程与非主线程
     * @param text 要显示的toast内容
     * @param duration 时长
     * */
    private static Toast showToast(final String text, final int duration) {
        if(appContext==null){
            Log.e("ToastUtil","未初始化ToastUtil");
            return null;
        }
        if(Looper.getMainLooper() == Looper.myLooper()) {
            Toast toast = Toast.makeText(appContext, text, duration);
            toast.show();
            return toast;
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(appContext, text, duration);
                    toast.show();
                }
            });
        }
        return null;
    }
}
