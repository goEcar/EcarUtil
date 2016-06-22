package urils.ecaray.com.ecarutils.Util2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import urils.ecaray.com.ecarutils.R;

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

    // 总接受流量通知
    public static String getAllTraffic() {
        long traffic = TrafficStats.getTotalRxBytes();
        return byteToM(traffic) + "M";
    }

    public static long byteToM(long mByte) {
        return mByte / 1024 / 1024;
    }

    // 模拟错误
    public static void errorMake() {
        int i = 10 / 0;
    }

    // 发送message 需要object
    public static void sendMessageByWhat(Handler handler, int what) {
        Message message = new Message();
        message.what = what;
        handler.sendMessage(message);
    }

    // 发送message 不需要object
    public static <T> void sendMessageObj(Handler handler, int what, T t) {
        Message message = new Message();
        message.what = what;
        message.obj = t;
        handler.sendMessage(message);
    }

    // 设置控件的宽 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsWith(Context context, Double size, View view) {

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Service.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int sizeX = dm.widthPixels;
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = (int) (size * sizeX);
    }

    // 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsHeight(Context context, Double size, View view) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Service.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int sizeY = dm.heightPixels;// 高度
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = (int) (size * sizeY);
    }

    // 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsHeightPx(Context context, int pxValue, View view) {
        LayoutParams lp = view.getLayoutParams();
        lp.height = pxValue;
    }

    // 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsWidthPx(Context context, int pxValue, View view) {
        LayoutParams lp = view.getLayoutParams();
        lp.width = pxValue;
    }

    // 设置控件的宽高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsWAndH(Context context, Double sizeX,
                                     Double sizeY, View view) {
        int[] sizes = new int[2];

        DisplayMetrics dm = new DisplayMetrics();

        WindowManager wm = (WindowManager) context
                .getSystemService(Service.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        sizes[0] = dm.widthPixels;
        sizes[1] = dm.heightPixels;// 高度
        int x = sizes[0];
        int y = sizes[1];
        android.view.ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = (int) (sizeX * x);
        lp.height = (int) (sizeY * y);
    }

    // 判断服务是否运行 参数:context,上下文 service:需要判断的service类名 返回值:false 没有运行 true 有在运行
    public static boolean isServiceActived(Context context, Class mClass) {
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .contains(mClass.getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param packgeName需要判断的app包名
     * @return ture 存在 false 不存在
     * @功能： 判断指定包名的app是否已经存在
     */
    public static boolean isHasSameApp(PackageManager pm, String packgeName) {
        boolean isHas = false;
        List<PackageInfo> packageList = pm.getInstalledPackages(0);// 获取所有的package对象
        for (PackageInfo packageInfo : packageList) {
            if (packageInfo.packageName.equals(packgeName)) {
                isHas = true;
            }

        }
        return isHas;
    }

    // 设定ListView高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // 隐藏软键盘
    public static void hideKeyBoard(Activity activity) {
        ((InputMethodManager) activity
                .getSystemService(Service.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    // 软键盘
    public static void toggleKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    // //显示软键盘,view可以是EditText,TextView等控件
    public static void showKeyBoard(Activity activity, View view) {
        ((InputMethodManager) activity
                .getSystemService(Service.INPUT_METHOD_SERVICE)).showSoftInput(
                view, 0);
    }

    // 将editText设为获取焦点并清空
    public static void setEditTextFoucus(EditText et) {
        et.setText("");
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    // 将editText设为获取焦点不清空
    public static void setEditTextOnlyFoucus(EditText et) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    // 将editText设为失去焦点
    public static void setEditTextUnFoucus(EditText et) {
        et.setFocusable(false);
        et.setFocusableInTouchMode(true);
    }

    /**
     * @param //textView:需要设置的textview // content：需要设置的String
     *                                 //start:开始的位置(从0开始) //end:结束的位置
     * @return
     * @功能：同一个textview设置不同颜色
     */
    public static void setTextColor(TextView textView, String content,
                                    int start, int end) {
        SpannableString sp = new SpannableString(content);
        sp.setSpan(new ForegroundColorSpan(Color.WHITE), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 3代表从第几个字符开始变颜色，注意第一个字符序号是0,8代表变色到第几个字符．
        textView.setText(sp);
    }



    /**
     * 将url转换为bitmap(图片)
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap getBitmapFromUrl(String url) {
        URL mUrl;
        Bitmap bitmap = null;
        try {
            mUrl = new URL(url);
            BufferedInputStream bis = new BufferedInputStream(mUrl
                    .openConnection().getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmap;
    }
}
