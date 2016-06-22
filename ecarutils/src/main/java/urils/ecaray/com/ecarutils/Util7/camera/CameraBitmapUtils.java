//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package urils.ecaray.com.ecarutils.Util7.camera;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraBitmapUtils {
    private static final String TAG = CameraBitmapUtils.class.getCanonicalName();
    public static final String JPG_SUFFIX = ".jpg";
    private static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public CameraBitmapUtils() {
    }

    public static void displayToGallery(Context context, File photoFile) {
        if (photoFile != null && photoFile.exists()) {
            String photoPath = photoFile.getAbsolutePath();
            String photoName = photoFile.getName();

            try {
                ContentResolver e = context.getContentResolver();
                Media.insertImage(e, photoPath, photoName, (String) null);
            } catch (FileNotFoundException var5) {
                var5.printStackTrace();
            }

            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + photoPath)));
        }
    }

    public static File saveToFile(Bitmap bitmap, File folder) {
        String fileName = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        return saveToFile(bitmap, folder, fileName);
    }

    public static File saveToFile(Bitmap bitmap, File folder, String fileName) {
        if (bitmap != null) {
            if (!folder.exists()) {
                folder.mkdir();
            }

            File file = new File(folder, fileName + ".jpg");
            if (file.exists()) {
                file.delete();
            }

            try {
                file.createNewFile();
                BufferedOutputStream e = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(CompressFormat.JPEG, 100, e);
                e.flush();
                e.close();
                return file;
            } catch (IOException var5) {
                var5.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static int getBitmapDegree(String path) {
        short degree = 0;

        try {
            ExifInterface e = new ExifInterface(path);
            int orientation = e.getAttributeInt("Orientation", 1);
            switch (orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return degree;
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) degree);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return newBitmap;
    }

    public static Bitmap decodeBitmapFromFile(File imageFile, int requestWidth, int requestHeight) {
        return imageFile != null ? decodeBitmapFromFile(imageFile.getAbsolutePath(), requestWidth, requestHeight) : null;
    }

    public static Bitmap decodeBitmapFromFile(String imagePath, int requestWidth, int requestHeight) {
        if (TextUtils.isEmpty(imagePath)) {
            return null;
        } else {
            Log.i(TAG, "requestWidth: " + requestWidth);
            Log.i(TAG, "requestHeight: " + requestHeight);
            if (requestWidth > 0 && requestHeight > 0) {
                Options options1 = new Options();
                options1.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, options1);
                Log.i(TAG, "original height: " + options1.outHeight);
                Log.i(TAG, "original width: " + options1.outWidth);
                if (options1.outHeight == -1 || options1.outWidth == -1) {
                    try {
                        ExifInterface e = new ExifInterface(imagePath);
                        int height = e.getAttributeInt("ImageLength", 1);
                        int width = e.getAttributeInt("ImageWidth", 1);
                        Log.i(TAG, "exif height: " + height);
                        Log.i(TAG, "exif width: " + width);
                        options1.outWidth = width;
                        options1.outHeight = height;
                    } catch (IOException var7) {
                        var7.printStackTrace();
                    }
                }

                options1.inSampleSize = calculateInSampleSize(options1, requestWidth, requestHeight);
                Log.i(TAG, "inSampleSize: " + options1.inSampleSize);
                options1.inJustDecodeBounds = false;
                return BitmapFactory.decodeFile(imagePath, options1);
            } else {
                Bitmap options = BitmapFactory.decodeFile(imagePath);
                return options;
            }
        }
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, (Rect) null, options);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;

            for (int halfWidth = width / 2; halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth; inSampleSize *= 2) {

            }

            long totalPixels = (long) (width * height / inSampleSize);

            for (long totalReqPixelsCap = (long) (reqWidth * reqHeight * 2); totalPixels > totalReqPixelsCap; totalPixels /= 2L) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**************蜜蜂*********************/
//    /**
//     * 上传头像先压缩。图片先按比例压缩，再按质量压缩
//     *
//     * @param filePath    文件路径
//     * @param w           图片宽度
//     * @param h           图片高度
//     * @param isTakephoto
//     * @return 缩放后的图片
//     */
//    @SuppressWarnings("resource")
//    public static String getBitmapByFile(String filePath, int w, int h,
//                                         boolean isTakephoto) {
//        FileInputStream inputStream = null;
//        BitmapFactory.Options opts = null;
//        Bitmap bitmap = null;
//        try {
//            inputStream = new FileInputStream(filePath);
//            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//            byte[] data = new byte[4096];
//            int count = -1;
//            while ((count = inputStream.read(data)) != -1) {
//                outStream.write(data, 0, count);
//            }
//            outStream.flush();
//            opts = new BitmapFactory.Options();
//            // 如果图片大于1M先进行尺寸压缩
//            if (outStream.toByteArray().length > 1024 * 1024) {
//                opts.inJustDecodeBounds = true;
//                // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
//                BitmapFactory.decodeFile(filePath, opts);
//                if(opts.outHeight == -1 || opts.outWidth == -1) {
//                    try {
//                        ExifInterface e = new ExifInterface(filePath);
//                        int height = e.getAttributeInt("ImageLength", 1);
//                        int width = e.getAttributeInt("ImageWidth", 1);
//                        Log.i(TAG, "exif height: " + height);
//                        Log.i(TAG, "exif width: " + width);
//                        opts.outWidth = width;
//                        opts.outHeight = height;
//                    } catch (IOException var7) {
//                        var7.printStackTrace();
//                    }
//                }
//
//                // 缩放的比例
//                opts.inSampleSize = computeSampleSize(opts, h, w);
//                // 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
//                // inJustDecodeBounds设为false表示把图片读进内存中
//                opts.inJustDecodeBounds = false;
//                opts.inDither = false;
//                opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            }
//            bitmap = compressImage(BitmapFactory.decodeFile(filePath, opts));
//            int degrees = getExifOrientation(filePath);
////            if (degrees != 0) {
////                bitmap = rotateAndMirror(bitmap, degrees, false);
////            }
////            int degree = BitmapUtils.getBitmapDegree(mPhotoFile.getAbsolutePath());//检查是否有被旋转，并进行纠正
//            if (degrees != 0) {
//                bitmap = CameraBitmapUtils.rotateBitmapByDegree(bitmap, degrees);
//            }
//        } catch (Exception e) {
//            TagUtil.showLogError("cameraBitmapUtils---" + e.toString());
//        }
//        return savePicToLocal(filePath, isTakephoto, bitmap);
//    }
//
//    public static int computeSampleSize(BitmapFactory.Options options,
//                                        int reqHeight, int reqWidth) {
//        int height = options.outHeight;
//        int width = options.outWidth;
//        int inSampleSize = 1;
//        /*
//         * if (reqHeight <= 800 || reqWidth <= 480) { return 1; }
//		 */
//        if (height > reqHeight || width > reqWidth) {
//            final int heightRatio = Math.round((float) height
//                    / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
//        return inSampleSize;
//    }
//
//    private static int options = 100;
//    private static Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, options,
//                baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();// 重置baos即清空baos
//            options -= 10;// 每次都减少10
//            image.compress(Bitmap.CompressFormat.JPEG, options,
//                    baos);// 这里压缩options%，把压缩后的数据存放到baos中
//        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(
//                baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
////        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
////        image.compress(Bitmap.CompressFormat.JPEG, options, baos1);
////        long a = baos1.toByteArray().length;
//        return bitmap;
//    }
//
//
//    public static int getExifOrientation(String filepath) {
//        int degree = 0;
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(filepath);
//        } catch (IOException ex) {
//        }
//        if (exif != null) {
//            int orientation = exif.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION, -1);
//            if (orientation != -1) {
//                // We only recognize a subset of orientation tag values.
//                switch (orientation) {
//                    case ExifInterface.ORIENTATION_ROTATE_90:
//                        degree = 90;
//                        break;
//                    case ExifInterface.ORIENTATION_ROTATE_180:
//                        degree = 180;
//                        break;
//                    case ExifInterface.ORIENTATION_ROTATE_270:
//                        degree = 270;
//                        break;
//                }
//            }
//        }
//        return degree;
//    }
//
//    public static Bitmap rotateAndMirror(Bitmap b, int degrees, boolean mirror) {
//        if ((degrees != 0 || mirror) && b != null) {
//            Matrix m = new Matrix();
//            m.setRotate(degrees, (float) b.getWidth() / 2,
//                    (float) b.getHeight() / 2);
//            if (mirror) {
//                m.postScale(-1, 1);
//                degrees = (degrees + 360) % 360;
//                if (degrees == 0 || degrees == 180) {
//                    m.postTranslate((float) b.getWidth(), 0);
//                } else if (degrees == 90 || degrees == 270) {
//                    m.postTranslate((float) b.getHeight(), 0);
//                } else {
//                    throw new IllegalArgumentException("Invalid degrees="
//                            + degrees);
//                }
//            }
//
//            try {
//                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
//                        b.getHeight(), m, true);
//                if (b != b2) {
//                    b.recycle();
//                    b = b2;
//                }
//            } catch (OutOfMemoryError ex) {
//                // We have no memory to rotate. Return the original bitmap.
//            }
//        }
//        return b;
//    }
//
//    // 保存图片到本地
//    //保存压缩好的头像到本地
//    private static String savePicToLocal(String filePath, boolean isTakephoto,
//                                         Bitmap bitmap) {
//        String path = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            if (isTakephoto) {
//                // 获取filepath的后缀，-5的原因是后缀可能是“jpng”
//                path = filePath;
//            } else {
//                path = mPhotosPath + System.currentTimeMillis() + ".png";
//            }
//            File file = new File(path);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            fileOutputStream = new FileOutputStream(path,true);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options,
//                    fileOutputStream);
//            options = 100;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileOutputStream.flush();
//                fileOutputStream.close();
//                if (bitmap != null && !bitmap.isRecycled()) {
//                    bitmap.recycle();
//                    bitmap = null;
//                } else {
//                    return null;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return path;
//    }
//
//    /**
//     * 头像存储路径
//     */
//    public static String mPhotosPath = Environment.getExternalStorageDirectory().getPath()
//            + "/parking/camera/";
//    public static String savePicToLocal(Bitmap bitmap) {
//        String filePath = mPhotosPath + System.currentTimeMillis() + ".png";
//        File file = new File(filePath);
//        FileOutputStream fileOutputStream = null;
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            fileOutputStream = new FileOutputStream(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//        return filePath;
//    }

    /**************蜜蜂*********************/


    /**************蜜蜂2*********************/

    //    / Rotates and/or mirrors the bitmap. If a new bitmap is created, the
    // original bitmap is recycled.
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

    public static int computeSampleSize(Options options,
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

    private static int options = 100;

    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, options,
                baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(CompressFormat.JPEG, options,
                    baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(
                baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, options, baos1);
        long a = baos1.toByteArray().length;
        return bitmap;
    }


    public static String mPhotosPath = Environment.getExternalStorageDirectory().getPath() + "/parking/camera1/";

    /**************蜜蜂2*********************/
}
