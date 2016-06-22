package urils.ecaray.com.ecarutils.Util6;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Build;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();


    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * 保存二进制流到指定路径
     *
     * @param instream
     * @param filepath
     */
    public static void saveFile(InputStream instream, String filepath) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return;
        }

        File file = new File(filepath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int cnt = 0;

            while ((cnt = instream.read(buffer)) != -1) {
                fos.write(buffer, 0, cnt);
            }

            instream.close();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    public static boolean retrieveApkFromAssets(Context context, String fileName, String path) {
        boolean bRet = false;

        try {
            InputStream e = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            boolean i = false;

            int i1;
            while ((i1 = e.read(temp)) > 0) {
                fos.write(temp, 0, i1);
            }

            fos.close();
            e.close();
            bRet = true;
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return bRet;
    }


    /**
     *
     * @param from origin file path
     * @param to   target file path
     */
    static  public void  copyFile(String from, String to) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return;
        }

        File fileFrom = new File(from);
        File fileTo = new File(to);

        try {

            FileInputStream fis = new FileInputStream(fileFrom);
            FileOutputStream fos = new FileOutputStream(fileTo);
            byte[] buffer = new byte[1024];
            int cnt = 0;

            while ((cnt = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, cnt);
            }

            fis.close();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    /**
     * 保存 JSON 字符串到指定文件
     *
     * @param json
     * @param filepath
     */
    static public boolean  saveJson(String json, String filepath) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return false;
        }

        File file = new File(filepath);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * 删除指定的 JSON 文件
     *
     * @param filepath
     * @return
     */
    static public boolean deleteJson(String filepath) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return false;
        }

        File file = new File(filepath);

        if (file.exists()) {
            file.delete();
        }

        return false;
    }

    /**
     * 从指定文件读取 JSON 字符串
     *
     * @param filepath
     * @return
     */
    public String readJson(String filepath) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return null;
        }

        File file = new File(filepath);
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }

        return sb.toString();
    }

    /**
     * 保存图片到制定路径
     *
     * @param filepath
     * @param bitmap
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void saveBitmap(String filepath, Bitmap bitmap) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return;
        }

        if (bitmap == null) {
            return;
        }

        try {
            File file = new File(filepath);
            FileOutputStream outputstream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputstream);
            outputstream.flush();
            outputstream.close();
        } catch (FileNotFoundException e) {
            Log.i(TAG, e.getMessage());
        } catch (IOException e) {
            Log.i(TAG, e.getMessage());
        }
    }


    /**
     * 删除 Files 目录下所有的图片
     *
     * @return
     */
    public static boolean cleanCache(Context context) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return false;
        }

        File dir = context.getExternalFilesDir(null);

        if (dir != null) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }

        return true;
    }

    /**
     * 计算 Files 目录下图片的大小
     *
     * @return
     */
    public String getCacheSize(Context context) {
        if (!isExternalStorageWritable()) {
            Log.i(TAG, "SD卡不可用，保存失败");
            return null;
        }

        long sum = 0;
        File dir = context.getExternalFilesDir(null);

        if (dir != null) {
            for (File file : dir.listFiles()) {
                sum += file.length();
            }
        }

        if (sum < 1024) {
            return sum + "字节";
        } else if (sum < 1024 * 1024) {
            return (sum / 1024) + "K";
        } else {
            return (sum / (1024 * 1024)) + "M";
        }
    }

    /**
     * 返回当前应用 SD 卡的绝对路径 like
     * /storage/sdcard0/Android/data/com.example.test/files
     */
    public String getAbsolutePath(Context context) {
        File root = context.getExternalFilesDir(null);

        if (root != null) {
            return root.getAbsolutePath();
        }

        return null;
    }

    /**
     * 返回当前应用的 SD卡缓存文件夹绝对路径 like
     * /storage/sdcard0/Android/data/com.example.test/cache
     */
    public static String getCachePath(Context context) {
        File root = context.getExternalCacheDir();

        if (root != null) {
            return root.getAbsolutePath();
        }

        return null;
    }

    /**
     * 删除指定的 图片
     *
     * @param filepath
     * @return
     */
    public boolean deletepictur(String filepath) {
        if (!isExternalStorageWritable()) {
            return false;
        }

        File file = new File(filepath);

        if (file.exists()) {
            file.delete();
        }

        return false;
    }


    private static final File parentPath = Environment
            .getExternalStorageDirectory();
    private static String storagePath = "";


    /**
     * 保存Bitmap到sdcard
     *
     * @param b
     */
    public static String saveBitmap(Bitmap bm,String fileName) {
        File f = new File( fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 10, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileName;

    }
    /**
     * @throws Exception
     * @功能：yuv转prg
     * @param：
     * @return：
     */
    public static int[] decodeYUV420SPrgb565(int[] rgb, byte[] yuv420sp,
                                             int width, int height) {
        final int frameSize = width * height;
        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420sp[yp])) - 16;
                if (y < 0)
                    y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420sp[uvp++]) - 128;
                    u = (0xff & yuv420sp[uvp++]) - 128;
                }
                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);
                if (r < 0)
                    r = 0;
                else if (r > 262143)
                    r = 262143;
                if (g < 0)
                    g = 0;
                else if (g > 262143)
                    g = 262143;
                if (b < 0)
                    b = 0;
                else if (b > 262143)
                    b = 262143;
                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000)
                        | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
            }
        }
        return rgb;
    }

    /**
     * @throws Exception
     * @功能：保存图片
     * @param：quality：压缩率
     * @return：
     */
    public static boolean saveBitmap(byte[] yuv420, String fileName, int width,
                                     int height, int quality) {
        boolean result = false;
        int[] bytes = new int[width * height];
        decodeYUV420SPrgb565(bytes, yuv420, width, height);
        Bitmap bitmap = Bitmap.createBitmap(bytes, width, height,
                Config.RGB_565);
        saveBitmap(bitmap, fileName);
        return result = true;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static boolean SaveImage(byte[] data, int width, int height,
                                    int index, int nDegree) {

        int nImageWidth = width;
        int nImageHeight = height;

        String strPre = "C";

        // 1.采用NV21格式 YuvImage类进行保存 效率很高
        String fileName = strPre + nDegree + "_IMG_" + String.valueOf(index)
                + ".jpg";
        String fileDataName = strPre + nDegree + "_DATA_"
                + String.valueOf(index) + ".yuv";
        File sdRoot = Environment.getExternalStorageDirectory();
        String dir = "/photoss/";
        File mkDir = new File(sdRoot, dir);
        if (!mkDir.exists()) {
            mkDir.mkdirs();
        }

        File pictureFile = new File(sdRoot, dir + fileName);
        File dataFile = new File(sdRoot, dir + fileDataName);

        if (!pictureFile.exists()) {
            try {
                pictureFile.createNewFile();

                FileOutputStream filecon = new FileOutputStream(pictureFile);
                YuvImage image = new YuvImage(data, ImageFormat.NV21,
                        nImageWidth, nImageHeight, null);

                image.compressToJpeg(
                        new Rect(0, 0, image.getWidth(), image.getHeight()),
                        70, filecon); // 将NV21格式图片，以质量70压缩成Jpeg，并得到JPEG数据流

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!dataFile.exists()) {
            try {
                FileOutputStream fops = new FileOutputStream(dataFile);
                fops.write(data);
                fops.flush();
                fops.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static String input2Str(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] bs = new byte[1024];
        int i = 0;
        while ((i = in.read(bs)) != -1) {
            out.write(bs, 0, i);
        }
        in.close();
        out.close();

        byte[] bytesResult = out.toByteArray();
        String strResult = new String(bytesResult, "utf-8");
        return strResult;
    }

    public static String getCRC32(File mFile) {
        CRC32 crc32 = new CRC32();
        FileInputStream fileinputstream = null;
        CheckedInputStream checkedinputstream = null;
        String crc = null;
        try {
            fileinputstream = new FileInputStream(mFile);
            checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
            byte[] buffer = new byte[4096];
            while (checkedinputstream.read(buffer) != -1) {
            }
            crc = String.valueOf(checkedinputstream.getChecksum().getValue());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileinputstream != null) {
                try {
                    fileinputstream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (checkedinputstream != null) {
                try {
                    checkedinputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return crc;
    }

    //二进制转文件
    //fileName文件的全路径  by：二进制数据
    public static File castByte2File(String fileName, byte[] by) {
        {
            FileOutputStream fileout = null;
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            try {
                fileout = new FileOutputStream(file);
                fileout.write(by, 0, by.length);

            } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    fileout.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return file;
        }
    }


    /**
     * 将Bitmap存为 .bmp格式图片
     * @param bitmap
     */
    //保存为bmp图片
    public static void saveBmp(Bitmap bitmap) {
// TODO Auto-generated method stub
        if (bitmap != null) {
            int w = bitmap.getWidth(), h = bitmap.getHeight();
            int[] pixels = new int[w * h];
            bitmap.getPixels(pixels, 0, w, 0, 0, w, h);

            byte[] rgb = addBMP_RGB_888(pixels, w, h);
            byte[] header = addBMPImageHeader(rgb.length);
            byte[] infos = addBMPImageInfosHeader(w, h);

            byte[] buffer = new byte[54 + rgb.length];
            System.arraycopy(header, 0, buffer, 0, header.length);
            System.arraycopy(infos, 0, buffer, 14, infos.length);
            System.arraycopy(rgb, 0, buffer, 54, rgb.length);
            try {
                FileOutputStream fos = new FileOutputStream( "/sdcard/hello.bmp");
                fos.write(buffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // BMP文件头
    private static byte[] addBMPImageHeader(int size) {
        byte[] buffer = new byte[14];
        buffer[0] = 0x42;
        buffer[1] = 0x4D;
        buffer[2] = (byte) (size >> 0);
        buffer[3] = (byte) (size >> 8);
        buffer[4] = (byte) (size >> 16);
        buffer[5] = (byte) (size >> 24);
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        buffer[8] = 0x00;
        buffer[9] = 0x00;
        buffer[10] = 0x36;
        buffer[11] = 0x00;
        buffer[12] = 0x00;
        buffer[13] = 0x00;
        return buffer;
    }

    // BMP文件信息头
    private static byte[] addBMPImageInfosHeader(int w, int h) {
        byte[] buffer = new byte[40];
        buffer[0] = 0x28;
        buffer[1] = 0x00;
        buffer[2] = 0x00;
        buffer[3] = 0x00;
        buffer[4] = (byte) (w >> 0);
        buffer[5] = (byte) (w >> 8);
        buffer[6] = (byte) (w >> 16);
        buffer[7] = (byte) (w >> 24);
        buffer[8] = (byte) (h >> 0);
        buffer[9] = (byte) (h >> 8);
        buffer[10] = (byte) (h >> 16);
        buffer[11] = (byte) (h >> 24);
        buffer[12] = 0x01;
        buffer[13] = 0x00;
        buffer[14] = 0x18;
        buffer[15] = 0x00;
        buffer[16] = 0x00;
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;
        buffer[20] = 0x00;
        buffer[21] = 0x00;
        buffer[22] = 0x00;
        buffer[23] = 0x00;
        buffer[24] = (byte) 0xE0;
        buffer[25] = 0x01;
        buffer[26] = 0x00;
        buffer[27] = 0x00;
        buffer[28] = 0x02;
        buffer[29] = 0x03;
        buffer[30] = 0x00;
        buffer[31] = 0x00;
        buffer[32] = 0x00;
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;
        buffer[36] = 0x00;
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;
        return buffer;
    }

    private static byte[] addBMP_RGB_888(int[] b, int w, int h) {
        int len = b.length;
        System.out.println(b.length);
        byte[] buffer = new byte[w * h * 3];
        int offset = 0;
        for (int i = len - 1; i >= 0; i -= w) {
// DIB文件格式最后一行为第一行，每行按从左到右顺序
            int end = i, start = i - w + 1;
            for (int j = start; j <= end; j++) {
                buffer[offset] = (byte) (b[j] >> 0);
                buffer[offset + 1] = (byte) (b[j] >> 8);
                buffer[offset + 2] = (byte) (b[j] >> 16);
                offset += 3;
            }
        }
        return buffer;
    }


    public static void saveBitmapByPng(Bitmap bitmap) throws IOException
    {
        File file = new File("/sdcard/test.png");
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
