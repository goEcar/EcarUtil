package urils.ecaray.com.ecarutils.Util7;

import android.util.Log;

import java.io.File;

/**
 * 文件缓存类
 *
 * @author Administrator
 */
public class FileCache {

    public final String File_Cache_Path = "/yitingche/cache";
    private File cacheDir;

    public FileCache() {
        //找一个用来缓存图片的路径
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), File_Cache_Path);//有SD卡的
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        }
    }


    public void setUrlCache(String data, String url) {
        String strName = MathsUtil.getMD5Code(getCacheDecodeString(url));
        File file = new File(cacheDir, strName);
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtil.writeTextFile(file, data);
        } catch (Exception e) {
            Log.e("tag", "write file " + e.toString());
            e.printStackTrace();
        }
    }

    public static String getCacheDecodeString(String url) {
        //1. 处理特殊字符
        //2. 去除后缀名带来的文件浏览器的视图凌乱(特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
        int index = url.indexOf("timestamp");
        if (index != -1)
            url = url.substring(0, index);
        return url.replaceAll("[.:/,%?&={}]", "+").replaceAll("[+]+", "+").replace("\"", "");
    }
} 
