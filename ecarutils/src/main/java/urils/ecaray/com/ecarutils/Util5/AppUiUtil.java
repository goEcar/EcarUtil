package urils.ecaray.com.ecarutils.Util5;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        LayoutParams lp = view.getLayoutParams();
        lp.width = (int) (size * sizeX);
    }

    // 设置控件的高 参数:context 上下文 size;屏幕的倍数 view:需要调整的控件
    public static void setViewsHeight(Context context, Double size, View view) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Service.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int sizeY = dm.heightPixels;// 高度
        LayoutParams lp = view.getLayoutParams();
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
        LayoutParams lp = view.getLayoutParams();
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
     * @param pm packgeName需要判断的app包名
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
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // 隐藏软键盘
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean flag = imm.isActive();
        if (imm.isActive()) {

        }
        ((InputMethodManager) activity
                .getSystemService(Service.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static void closeBoard(Context mcontext) {
        InputMethodManager imm = (InputMethodManager) mcontext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        if (imm.isActive()) // 一直是true
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
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

    public static void setTextColor(TextView textView, String content,
                                    int start, int end, int colorInt) {
        SpannableString sp = new SpannableString(content);
        sp.setSpan(new ForegroundColorSpan(colorInt), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 3代表从第几个字符开始变颜色，注意第一个字符序号是0,8代表变色到第几个字符．
        textView.setText(sp);
    }

    public static void setTextColor(TextView textView, String content,
                                    String colorString, float textSize, int start, int end) {
        // SpannableString sp = new SpannableString(content);
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        if (textSize != -1) {
            style.setSpan(new RelativeSizeSpan(textSize), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        style.setSpan(new ForegroundColorSpan(Color.parseColor(colorString)),
                start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 3代表从第几个字符开始变颜色，注意第一个字符序号是0,8代表变色到第几个字符．
        textView.setText(style);
    }

    // /**
    // * @功能：根据url放大图片
    // * @param：
    // * @return：
    // * @throws Exception
    // */
    // static public void showBigPicPoupByUrl(View parent, Activity context,
    // Bitmap tBitmap) {
    // Display display = context.getWindowManager().getDefaultDisplay();
    // View contentView = LayoutInflater.from(context).inflate(
    // R.layout.view_big_pic_popup, null);
    //
    // contentView.setBackgroundColor(Color.BLACK);
    // final PopupWindow popupWindow = new PopupWindow(contentView,
    // LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    // popupWindow.setFocusable(true);
    // popupWindow.setBackgroundDrawable(new BitmapDrawable());// 返回键关闭
    // popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
    // ImageView imageView = (ImageView) contentView.findViewById(R.id.logo_b);
    // if (tBitmap == null) {
    // return;
    // }
    // int width = tBitmap.getWidth();
    // int height = tBitmap.getHeight();
    // if (width > height) {
    // width = display.getWidth();
    // height = (int) (height * ((double) display.getWidth() / (double) tBitmap
    // .getWidth()));
    //
    // } else if (width == height) {
    // width = height = display.getWidth();
    // } else {
    // width = display.getWidth();
    // height = (int) (tBitmap.getHeight() * ((double) display.getWidth()) /
    // (double) tBitmap
    // .getWidth());
    // if (height > display.getHeight()) {
    // height = display.getHeight();
    // width = (int) (tBitmap.getWidth()
    // * ((double) display.getHeight()) / (double) tBitmap
    // .getHeight());
    // }
    // Log.i("tag", "width=" + width + "  " + display.getWidth());
    // Log.i("tag", "height=" + height + "   " + display.getHeight());
    // }
    // // 在这儿就可以实现图片的大小缩放
    // Bitmap resize = Bitmap.createScaledBitmap(tBitmap, width - 1,
    // height - 1, true);
    // // Matrix m = new Matrix();
    // // m.setRotate(90); // 逆时针旋转15度
    // // 做好旋转与大小之后，重新创建位图，0-width宽度上显示的区域，高度类似
    // // Bitmap b = Bitmap.createBitmap(resize, 0, 0, height - 1, width - 1,
    // // null, true);
    // // 显示图片
    // imageView.setImageBitmap(resize);
    // imageView.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // // TODO Auto-generated method stub
    // popupWindow.dismiss();
    // }
    // });
    // }
    //
    // /**
    // * @功能：根据FileName放大图片
    // * @param：
    // * @return：
    // * @throws Exception
    // */
    // @SuppressWarnings("deprecation")
    // static public void showBigPicPoupByFN(View parent, Activity context,
    // String fileName) {
    // View contentView = LayoutInflater.from(context).inflate(
    // R.layout.view_big_pic_popup, null);
    // contentView.setBackgroundColor(Color.BLACK);
    // final PopupWindow popupWindow = new PopupWindow(contentView,
    // LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    // popupWindow.setFocusable(true);
    // popupWindow.setBackgroundDrawable(new BitmapDrawable());// 返回键关闭
    // popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
    // Display display = context.getWindowManager().getDefaultDisplay();
    // ImageView imageView = (ImageView) contentView.findViewById(R.id.logo_b);
    // Bitmap tBitmap = null;
    // try {
    // tBitmap = BitmapFactory.decodeFile(fileName);
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // if (tBitmap == null) {
    // return;
    // }
    // int width = tBitmap.getWidth();
    // int height = tBitmap.getHeight();
    // if (width > height) {
    // width = display.getWidth();
    // height = (int) (height * ((double) display.getWidth() / (double) tBitmap
    // .getWidth()));
    //
    // } else if (width == height) {
    // width = height = display.getWidth();
    // } else {
    // width = display.getWidth();
    // height = (int) (tBitmap.getHeight() * ((double) display.getWidth()) /
    // (double) tBitmap
    // .getWidth());
    // if (height > display.getHeight()) {
    // height = display.getHeight();
    // width = (int) (tBitmap.getWidth()
    // * ((double) display.getHeight()) / (double) tBitmap
    // .getHeight());
    // }
    // Log.i("tag", "width=" + width + "  " + display.getWidth());
    // Log.i("tag", "height=" + height + "   " + display.getHeight());
    // }
    //
    // // 在这儿就可以实现图片的大小缩放
    // Bitmap resize = Bitmap.createScaledBitmap(tBitmap, width - 1,
    // height - 1, true);
    // imageView.setImageBitmap(resize);
    // imageView.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // // TODO Auto-generated method stub
    // popupWindow.dismiss();
    // }
    // });
    // popupWindow.setBackgroundDrawable(new PaintDrawable());
    // contentView.setOnKeyListener(new OnKeyListener() {
    //
    // @Override
    // public boolean onKey(View v, int keyCode, KeyEvent event) {
    // // TODO Auto-generated method stub
    // if (keyCode == KeyEvent.KEYCODE_BACK) {
    // popupWindow.dismiss();
    // return true;
    // }
    // return false;
    // }
    // });
    // }

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


    public static void setTextViewDrawable(Activity activity,
                                           TextView textView, int id) {
        Drawable drawable = activity.getResources().getDrawable(id);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    /*
     * 设置控件所在的位置Y，并且不改变宽高， Y为绝对位置，此时X可能归0
     */
    public static void setLayoutY(ImageView view, int y) {
        MarginLayoutParams margin = new MarginLayoutParams(
                view.getLayoutParams());
        margin.setMargins(margin.leftMargin, y, margin.rightMargin, y
                + margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                margin);
        view.setLayoutParams(layoutParams);
    }





    public static LatLng getLatLngByStr(String parkPoint) {
        String[] parkPoints = parkPoint.split(",");
        Double mLat2 = Double.parseDouble(parkPoints[0]);
        Double mLon2 = Double.parseDouble(parkPoints[1]);
        if (mLat2 > mLon2) {
            mLat2 = mLat2 + mLon2;
            mLon2 = mLat2 - mLon2;
            mLat2 = mLat2 - mLon2;
        }
        LatLng ll = new LatLng(mLat2,
                mLon2);
        return ll;
    }

    /**
     * 关闭对话框
     */
    public static void closeDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


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

    //设置本地文件webview参数
    public static WebSettings setUrlLocal(WebView webView, String url) {
        WebSettings bs = webView.getSettings();
        bs.setBuiltInZoomControls(false);
        bs.setJavaScriptEnabled(true);
        bs.setSupportZoom(false);// 设置自动缩放
        bs.setUseWideViewPort(false);
        bs.setDisplayZoomControls(false);
        bs.setLoadWithOverviewMode(true);
        bs.setJavaScriptCanOpenWindowsAutomatically(true);
        bs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        bs.setLoadWithOverviewMode(true);
        bs.setDefaultTextEncodingName("UTF-8");

        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);// 水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示
        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 18) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }
        return bs;
    }
    //设置网络webview参数
    public static WebSettings setUrlWithServer(WebView webView, String url) {
        WebSettings bs = webView.getSettings();
        bs.setBuiltInZoomControls(true);
        bs.setJavaScriptEnabled(true);
        bs.setSupportZoom(true);// 设置自动缩放
        bs.setUseWideViewPort(true);
        bs.setDisplayZoomControls(false);
        bs.setLoadWithOverviewMode(true);
        bs.setJavaScriptCanOpenWindowsAutomatically(true);
        bs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        bs.setLoadWithOverviewMode(true);
        bs.setDefaultTextEncodingName("UTF-8");
        bs.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);// 水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示
        if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT <= 18) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }
        return bs;
    }


}
