package urils.ecaray.com.ecarutils.Util2;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlParseUtils {
    public static final String CONFIG_DIR = "xml";

    // public static final String FILE_NAME = "App.config";

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        // sb.append(ROOT_DIR);
        // sb.append(File.separator);
        return sb.toString();
    }


    public static InputStream readXml(Context context, String filename)
            throws IOException {
        return context.getResources().getAssets().open(filename);

    }

    //
    // public static InputStream readXmlFormFile(Context context, String
    // filename) {
    // try {
    // return new FileInputStream(new File(getConfigDir(), FILE_NAME));
    // } catch (FileNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return null;
    // }
}
