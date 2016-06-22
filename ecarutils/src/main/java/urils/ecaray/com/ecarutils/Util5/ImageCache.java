package urils.ecaray.com.ecarutils.Util5;

import android.content.Context;

import java.io.File;

public class ImageCache {

    private static File cacheDir;
    private final String Image_File = "/xiayizhan/cache/image";

    public ImageCache(Context context) {
        //找一个用来缓存图片的路径
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), Image_File);//有SD卡的
        else
            cacheDir = context.getCacheDir();//无SD卡，获取运行内存空间
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;

    }

    public static void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}