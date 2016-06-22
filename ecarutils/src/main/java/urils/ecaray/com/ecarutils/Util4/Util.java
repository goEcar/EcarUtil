package urils.ecaray.com.ecarutils.Util4;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Util {


    /**
     * @throws Exception
     * @功能：设置控件的渐变动画
     * @param：
     * @return：
     */
    public static void setAlphaAnim(View view) {
        AlphaAnimation aAnim = new AlphaAnimation(0.0f, 1.0f);
        aAnim.setDuration(1000);
        view.startAnimation(aAnim);

    }


    /**
     * @throws Exception
     * @功能：跳转到另外的Activity
     * @param：mClass:目标activity类的class
     * @return：
     */
    public static void openActivity(Activity context, Class mClass) {
        Intent intent = new Intent();
        intent.setClass(context, mClass);
        context.startActivity(intent);

    }

    public static void openActivityForResult(Activity context, Class mClass,
                                             int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, mClass);
        context.startActivityForResult(intent, requestCode);
    }

    public static void openActivityWithBundle(Activity context, Class mClass,
                                              Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(context, mClass);
        context.startActivity(intent);
    }


    public static boolean isEmptyInEditText(EditText et) {
        return et.getText().toString().isEmpty();
    }


    // 条件不成立不能关闭 AlertDialog 窗口

    public static void setCanClose(Dialog dialog, boolean isCancel) {
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog,
                    isCancel); // false - 使之不能关闭(此为机关所在，其它语句相同)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置输入法的完成按钮事件
    public static void setPutDone(TextView textV, TextView.OnEditorActionListener editorListener) {
        textV.setOnEditorActionListener(editorListener);
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
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//开启硬件加速后webview有可能会出现闪烁的问题，解决方法是在webview中设置：


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

    //渐变动画
    private void setAnim(View view) {
        AlphaAnimation aAnim = new AlphaAnimation(0f, 1f);
        aAnim.setDuration(500);
        view.startAnimation(aAnim);
    }




    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

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
}
