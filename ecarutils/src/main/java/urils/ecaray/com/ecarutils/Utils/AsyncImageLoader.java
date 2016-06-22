package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片异步加载类
 *
 * @author Leslie.Fang
 * @company EnwaySoft
 */
public class AsyncImageLoader {
    // 最大线程数
    private static final int MAX_THREAD_NUM = 10;
    private Map<String, SoftReference<Bitmap>> imageCaches = null;
    private FileUtil fileUtil;
    private Context context;
    // 线程池
    private ExecutorService threadPools = null;

    public AsyncImageLoader(Context context) {
        this.context = context;
        imageCaches = new HashMap<String, SoftReference<Bitmap>>();
        fileUtil = new FileUtil(context);
    }

    public Bitmap loadImage(final String imageUrl,
                            final ImageDownloadCallBack imageDownloadCallBack) {
        final String filename = imageUrl
                .substring(imageUrl.lastIndexOf("/") + 1);

        final String file = Environment.getExternalStorageDirectory().getPath()
                + "/chongdaifu/tupian/";
        File dir = new File(file);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        final String filepath = file + filename;
        Log.d("TAG", filename + "---" + filepath);
        // 先从软引用中找
        if (imageCaches.containsKey(imageUrl)) {
            SoftReference<Bitmap> reference = imageCaches.get(imageUrl);
            Bitmap bitmap = reference.get();
            Log.d("TAG", "&&**&^*&^*&^*&^*&^*&");
            // 软引用中的 Bitmap 对象可能随时被回收
            // 如果软引用中的 Bitmap 已被回收，则从文件中找
            if (bitmap != null) {
                Log.i("aaaa", "cache exists " + filename);

                return bitmap;
            }
        }

        // 从文件中找
        File fir = new File(filepath);
        if (fir.exists()) {
            Log.i("aaaa", "file exists " + filename);
            Log.d("TAG", "*****************");

            // InputStream is =
            // context.getResources().openRawResource(R.drawable.pic1);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 5; // width，hight设为原来的十分一
            Bitmap bitmap = BitmapFactory.decodeFile(filepath, options);
            // Bitmap btp =BitmapFactory.decodeStream(is,null,options);

            // 重新加入到内存软引用中
            imageCaches.put(imageUrl, new SoftReference<Bitmap>(bitmap));

            return bitmap;
        }

        // 软引用和文件中都没有再从网络下载
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.d("TAG", "^^^^^^^^^^^^");

            if (threadPools == null) {
                threadPools = Executors.newFixedThreadPool(MAX_THREAD_NUM);
            }

            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 111 && imageDownloadCallBack != null) {
                        Bitmap bitmap = (Bitmap) msg.obj;
                        imageDownloadCallBack.onImageDownloaded(bitmap);
                    }
                }
            };

            Thread thread = new Thread() {
                @Override
                public void run() {
                    Log.i("aaaa", Thread.currentThread().getName()
                            + " is running");

                    URL url;
                    try {
                        url = new URL(imageUrl);
                        URLConnection conn = url.openConnection();
                        conn.connect();
                        // 取得返回的InputStream
                        InputStream inputStream = conn.getInputStream();
                        // InputStream inputStream = HTTPService.getInstance()
                        // .getStream(imageUrl);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // 取得链接

                        // 图片下载成功重新缓存并执行回调刷新界面
                        if (bitmap != null) {
                            // 加入到软引用中
                            imageCaches.put(imageUrl,
                                    new SoftReference<Bitmap>(bitmap));
                            // 缓存到文件系统
                            fileUtil.saveBitmap(filepath, bitmap);
                            // String[] path = FileUtils.ListFile(file);
                            // if(path.length > 5){
                            // Log.i("bbbbbb",
                            // "88888888888888-----------------");
                            // fileUtil.deletepictur(path[0]);
                            // }
                            Message msg = new Message();
                            msg.what = 111;
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };

            threadPools.execute(thread);
        }

        return null;
    }

    public void shutDownThreadPool() {
        if (threadPools != null) {
            threadPools.shutdown();
            threadPools = null;
        }
    }

    /**
     * 图片下载完成回调接口
     */
    public interface ImageDownloadCallBack {
        void onImageDownloaded(Bitmap bitmap);
    }
}
