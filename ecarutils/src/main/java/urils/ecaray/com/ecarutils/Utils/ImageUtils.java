package urils.ecaray.com.ecarutils.Utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类描述：图片操作工具类
 *<p>
 */
public class ImageUtils {
    public static boolean m_bCapture = false; // using test
    public static int cameraWidth;
    public static int cameraHeight;

    /**
     * 旋转Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    public static boolean SaveBitmapToJPG(Bitmap bitmap, File pictureFile) {
        if (!pictureFile.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(pictureFile);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static boolean SaveBitmapToPNG(Bitmap bitmap, File pictureFile) {
        Bitmap mBitmap = compressImage(bitmap);
        if (!pictureFile.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(pictureFile);
                if (mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * @throws Exception
     * @功能：压缩图片
     * @param：
     * @return：
     */
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options < 10) {
                break;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static boolean SaveCutBitmap(Bitmap bitmap, int[] a, File pictureFile) {
        if (!pictureFile.exists()) {
            try {
                int nCutWidth = a[2] - a[0];
                int nCutHeight = a[3] - a[1];
                Bitmap cutBmp = Bitmap.createBitmap(bitmap, a[0], a[1],
                        nCutWidth, nCutHeight, null, false);

                FileOutputStream out = new FileOutputStream(pictureFile);
                if (cutBmp.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static Bitmap GetBitmapFromYUV420SP(byte[] yuv420sp, int width,
                                               int height) {
        int[] rgb = new int[width * height];

        decodeYUV420SPrgb565(rgb, yuv420sp, width, height);
        Bitmap bitmap = Bitmap.createBitmap(rgb, width, height, Config.RGB_565);
        rgb = null;

        return getRotateBitmap(bitmap, 90);
    }

    public static void decodeYUV420SPrgb565(int[] rgb, byte[] yuv420sp,
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
    }

    public static boolean SaveImage(byte[] data, int width, int height,
                                    int index, int nDegree) {
        if (m_bCapture)
            return false;

        int nImageWidth = width;
        int nImageHeight = height;

        String strPre = "I";

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

        m_bCapture = true;
        return true;
    }



    public static int computeSampleSize(BitmapFactory.Options options,
                                        int reqHeight, int reqWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        /*
         * if (reqHeight <= 800 || reqWidth <= 480) { return 1; }
		 */
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror) {
        if ((degrees != 0 || mirror) && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2,
                    (float) b.getHeight() / 2);
            if (mirror) {
                m.postScale(-1, 1);
                degrees = (degrees + 360) % 360;
                if (degrees == 0 || degrees == 180) {
                    m.postTranslate((float) b.getWidth(), 0);
                } else if (degrees == 90 || degrees == 270) {
                    m.postTranslate((float) b.getHeight(), 0);
                } else {
                    throw new IllegalArgumentException("Invalid degrees="
                            + degrees);
                }
            }

            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return b;
    }

}
