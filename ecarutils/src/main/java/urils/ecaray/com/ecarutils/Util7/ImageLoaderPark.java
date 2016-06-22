package urils.ecaray.com.ecarutils.Util7;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author bin
 */
public class ImageLoaderPark {

    private MemoryCache memoryCache = new MemoryCache();
    private ImageCache imageCache;
    private Map<ImageView, String> imageViews =
            Collections.synchronizedMap(new WeakHashMap<ImageView, String>()); //弱引用
    private ExecutorService executorService;
    private int REQUIRED_SIZE = 300; //图片大小
    //	private boolean showRoundCorner = false;//显示圆角
//	private boolean showScrollZoom  = false;//图片可拉伸
    private Activity a;

    public ImageLoaderPark(Context context) {
        a = (Activity) context;
        imageCache = new ImageCache(context);
        executorService = Executors.newFixedThreadPool(5);
        DisplayMetrics metric = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metric);
        REQUIRED_SIZE = metric.widthPixels;
    }


    private Bitmap getBitmap(String url) { //从sd卡读
        File f = imageCache.getFile(url);
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;
        //从网络下载
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            FileUtils.copyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //解码图像用来减少内存消耗
    private Bitmap decodeFile(File f) {
        try {
            //解码图像大小
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int width_tmp = o.outWidth;
            int scale = 1;
            //找到正确的刻度值，它应该是2的幂。
            while (true) {
                if (width_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    //任务队列
    private static class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }


    /**
     * 防止图片错位
     *
     * @param photoToLoad
     * @return
     */
    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }


    private int getScreenDensityPix(int dip) {
        DisplayMetrics dm = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int densityDPI = dm.densityDpi; //密度
        return dip * densityDPI / 160;
    }

    public void clearCache() {
        memoryCache.clear();
        imageCache.clear();
    }

    /**
     * 获取缩放比例
     *
     * @param bitmap 需要缩放的图片
     * @return 缩放比例
     */
    private float getScale(Bitmap bitmap) {
        /**
         * 获取屏幕宽度和高度
         */
        DisplayMetrics metric = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int mScreenWidth = metric.widthPixels;
        int mScreenHeight = metric.heightPixels;
        float scaleWidth = mScreenWidth / (float) bitmap.getWidth();
        float scaleHeight = mScreenHeight / (float) bitmap.getHeight();
        return Math.min(scaleWidth, scaleHeight);
    }


    /**
     * 切换为圆角图片
     *
     * @param changeCorner
     */
    public void setRoundCorner(boolean changeCorner) {
//		this.showRoundCorner = changeCorner;
    }

    /**
     * 设置显示图片宽度大小
     *
     * @param dip
     */
    public void setImageSize(int dip) {
        REQUIRED_SIZE = getScreenDensityPix(dip);
    }

    /**
     * 设置图片可伸缩
     *
     * @param showScrollZoom
     */
    public void setScrollZoom(boolean showScrollZoom) {
//		this.showScrollZoom = showScrollZoom;
    }

    public void clearImageCache() {
        imageViews.clear();
        imageCache.clear();
    }
} 