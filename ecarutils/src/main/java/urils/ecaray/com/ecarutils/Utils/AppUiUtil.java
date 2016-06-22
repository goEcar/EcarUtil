package urils.ecaray.com.ecarutils.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.net.Uri;
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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private AppUiUtil() {

    }


    // 总接受流量通知
    public static String getAllTraffic() {
        long traffic = TrafficStats.getTotalRxBytes();
        return byteToM(traffic) + "M";
    }

    public static long byteToM(long mByte) {
        return mByte / 1024 / 1024;
    }

    // 模拟错误
//    public static void errorMake() {
//        int i = 10 / 0;
//    }

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
     * @param packgeName 需要判断的app包名
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
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;
    }

    // 隐藏软键盘
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {

        }
        ((InputMethodManager) activity
                .getSystemService(Service.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * @throws Exception
     * @功能：隐藏键盘
     * @param：
     * @return：
     */
    public static void hideKeyboard(Context context, EditText eText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(eText.getWindowToken(), 0); //隐藏
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

    public static void setTextColor(TextView view, String content, int colorInt) {
        setTextColor(view, content, 0, content.length(), colorInt);
    }

    public static void setTextColor(TextView textView, String content,
                                    String colorString, float textSize, int start, int end) {
        // SpannableString sp = new SpannableString(content);
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        style.setSpan(new RelativeSizeSpan(textSize), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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


    /**
     * 设置布局的margin
     */
    public static void setViewMargin(Context context, View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams msgParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        msgParams.topMargin = DataFormatUtil.setDip2px(context, top);
        msgParams.bottomMargin = DataFormatUtil.setDip2px(context, bottom);
        msgParams.leftMargin = DataFormatUtil.setDip2px(context, left);
        msgParams.rightMargin = DataFormatUtil.setDip2px(context, right);
        view.setLayoutParams(msgParams);
    }

    public static final int SHOW_TIME = 0;
    public static final int GET_ERROR = 1;
    public static final int GETTING_TIME = 2;


    /**
     * 0,显示时间计数，1，同步时间失败；2，GET_ERROR
     *
     * @param flag
     */
    public static void showClockTime(int flag, TextView timeTx, TextView getTx, boolean isHome) {
        String strNoMsgHome = "时钟同步失败";
        String strNoMsg = "网络异常，获取停车状态失败，您当前的停车状态可能不正确，请检查手机网络！";
        String str2Home = "正在同步时间";
        String str2 = "正在同步时间...";
        str2 = isHome ? str2Home : str2Home;//str2Home : str2;
//        strNoMsg = isHome ? strNoMsgHome : strNoMsg;
        strNoMsg = strNoMsgHome;
        if (timeTx != null && getTx != null)
            switch (flag) {
                case SHOW_TIME:// 显示时间
                    timeTx.setVisibility(View.VISIBLE);
                    getTx.setVisibility(View.GONE);
                    getTx.setText(strNoMsg);
                    break;
                case GET_ERROR:// 显示时间同步失败
/*                    getTx.setVisibility(View.VISIBLE);
                    timeTx.setVisibility(View.GONE);
                    getTx.setText(strNoMsg);*/
                    break;
                case GETTING_TIME:// 显示正在同步
                    getTx.setVisibility(View.VISIBLE);
                    timeTx.setVisibility(View.GONE);
                    getTx.setText(str2);
                    break;
            }
    }

    /*
     * 设置listview实际高度，解决listview与schoolview冲突
     */
    public static void setListViewHeight(ListView listView, int lenght) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + lenght;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 闪烁动画：true:start
     */
    public static AlphaAnimation flickerAnima(AlphaAnimation alphaAnimationPark, boolean startOrClose, View alphaView) {
        if (alphaAnimationPark == null && startOrClose) {
            alphaAnimationPark = new AlphaAnimation(0.4f, 1.0f);
            alphaAnimationPark.setDuration(1300);
            alphaAnimationPark.setRepeatCount(Animation.INFINITE);
            alphaAnimationPark.setRepeatMode(Animation.REVERSE);
        }
        if (startOrClose) {
            alphaView.startAnimation(alphaAnimationPark);
        } else if (alphaAnimationPark != null) {
            alphaView.clearAnimation();
        }
        return alphaAnimationPark;
    }


    public interface PromptClickListener {
        void callBack();
    }


    /**
     * 根据url加载
     *
     * @param webView
     * @param progressBar
     * @param url
     * @param activity
     */
    public static void loadData(final WebView webView, final ProgressBar progressBar,
                                final String url, final Activity activity) {

        setUrlWithServer(webView, url);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        });
    }

    //设置TextViewa左侧的图片
    @SuppressWarnings("deprecation")
    public static void setTvLeftPic(Context context, int resId, TextView tv) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
    }

//    public static void setTvLeftPic(Drawable drawable, TextView tv) {
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        tv.setCompoundDrawables(drawable, null, null, null);
//    }

    /**
     * 开启帧动画
     *
     * @param img
     * @return
     */
    public static AnimationDrawable startAnimDrawable(ImageView img) {
        AnimationDrawable animDrawble = (AnimationDrawable) img.getBackground();
        animDrawble.setOneShot(false);
        animDrawble.start();
        return animDrawble;
    }

    /**
     * 停车帧动画
     *
     * @param anim
     */
    public static void stopAnimDrawable(AnimationDrawable anim) {
        if (anim != null) {
            anim.stop();
        }
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
        bs.setJavaScriptEnabled(false);
        bs.setSupportZoom(true);// 设置自动缩放
        bs.setUseWideViewPort(true);
        bs.setDisplayZoomControls(false);
        bs.setLoadWithOverviewMode(true);
        bs.setJavaScriptCanOpenWindowsAutomatically(true);
//        bs.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        bs.setLoadWithOverviewMode(true);
        bs.setDefaultTextEncodingName("UTF-8");
        bs.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        bs.setTextSize(WebSettings.TextSize.LARGEST);

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
