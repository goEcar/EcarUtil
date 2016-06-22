package urils.ecaray.com.ecarutils.Util6;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.text.TextUtils;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static final int ORIENTATION_HYSTERESIS = 5;

    public static int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation = false;
        if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            dist = Math.min(dist, 360 - dist);
            changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
        }
        if (changeOrientation) {
            return ((orientation + 45) / 90 * 90) % 360;
        }
        return orientationHistory;
    }

    public static int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    @SuppressLint("NewApi")
    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        // See android.hardware.Camera.setCameraDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        // We're always working in a landscape item_waitapply. We can ignore
        // getDisplayRotation
        // figure which can provide incorrect rotation or provide rotation
        // figure too
        // soon, such as when opening from the lockscreen which is portrait.
        // As intended item_waitapply is landscape, we aim for 90 degrees from which to
        // calculate
        // orientation.
        int degrees = 90;
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT
                && info.orientation != 90) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing (or acting like it)
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    // Rotates and/or mirrors the bitmap. If a new bitmap is created, the
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

    //旋转图片的方向
    static public Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }

    public static boolean isEmpty(Collection c) {
        return !(c != null && c.size() > 0);
    }

    ;

    public static boolean isEmpty(Object[] arr) {
        return !(arr != null && arr.length > 0);
    }

    //设置输入法的完成按钮事件
    public static void setPutDone(TextView textV, TextView.OnEditorActionListener editorListener) {
        textV.setOnEditorActionListener(editorListener);
    }


    /**
     * @throws Exception
     * @功能：获取时间 yyyy/mm/dd
     * @param：
     * @return：
     */
    public static String getDateByLong(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
//        return date.substring(0, 4).concat(date.substring(4, 6)).concat(date.substring(6, 8).concat(date.substring(8, 10)));
        return Util.addText(new StringBuilder(), date.substring(0, 4), "-", date.substring(4, 6), "-",
                date.substring(6, 8), " ", date.substring(8, 10), ":", date.substring(10, 12));
    }

    /**
     * @throws Exception
     * @功能：获取时间 yyyy/mm/dd
     * @param：
     * @return：
     */
    public static String getCurrentTime() {
        // 定义日期格式
        SimpleDateFormat atter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        return atter.format(date);
    }



    //将data转为bitmap
    public static Bitmap getBitmap(byte[] data, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        int imageFormat = parameters.getPreviewFormat();
        int w = parameters.getPreviewSize().width;
        int h = parameters.getPreviewSize().height;
        Rect rect = new Rect(0, 0, w, h);
        YuvImage yuvImg = new YuvImage(data, imageFormat, w, h, null);
        Bitmap bitmap = null;
        try {
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            yuvImg.compressToJpeg(rect, 100, outputstream);
            bitmap = BitmapFactory.decodeByteArray(outputstream.toByteArray(), 0, outputstream.size());
            // Rotate the Bitmap
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            // We rotate the same Bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);

            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/fp.jpg"));
            //img.compressToJpeg(rect, 100, bos);
            //bos.flush();
            //bos.close();
            camera.startPreview();
        } catch (Exception e) {
        }
        return bitmap;
    }

    //获取车牌号
    public static String getCarNum(String carNum) {
        if (TextUtils.isEmpty(carNum)) {
            return "";
        }
        carNum = carNum.replaceAll("\\s", "");
        String city = carNum.substring(0, 1);
        String number = carNum.substring(1, carNum.length());
        return city.concat(number);
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

    /**
     * @throws Exception
     * @功能：调出键盘
     * @param：
     * @return：
     */
    public static void showKeyboard(Context context, EditText eText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(eText, InputMethodManager.SHOW_FORCED); // 隐藏键盘
    }

    /**
     * @throws Exception
     * @功能：键盘状态
     * @param：
     * @return：
     */
    public static boolean isKeyboardOpen(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    // 添加字符串
    public static synchronized String addText(StringBuilder sb, String... texts) {
        sb.delete(0, sb.length());
        for (String str : texts) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static final String CPU_ARCHITECTURE_TYPE_32 = "32";
    public static final String CPU_ARCHITECTURE_TYPE_64 = "64";

    /**
     * ELF文件头 e_indent[]数组文件类标识索引
     */
    private static final int EI_CLASS = 4;
    private static final int ELFCLASS64 = 2;

    /**
     * The system property key of CPU arch type
     */
    private static final String CPU_ARCHITECTURE_KEY_64 = "ro.product.cpu.abilist64";

    /**
     * The system libc.so file path
     */
    private static final String SYSTEM_LIB_C_PATH = "/system/lib/libc.so";
    private static final String SYSTEM_LIB_C_PATH_64 = "/system/lib64/libc.so";
    private static final String PROC_CPU_INFO_PATH = "/proc/cpuinfo";


    private static String getSystemProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method get = clazz.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(clazz, key, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }




    //获取车辆类型
    public static String castToCarType(String carType) {
        if (!TextUtils.isEmpty(carType)) {
            switch (carType) {
                case "1":
                    return "小型汽车";
                case "2":
                    return "大型汽车";
                case "3":
                    return "其他";
            }
        }
        return "--";
    }

    //判断一个字符串的首字符是否为字母
    public static boolean isFirstChinese(String s) {
        String regEx = "[\\u4e00-\\u9fa5]";
        // System.out.println(regEx);
        // System.out.println(str);
        Pattern p = Pattern.compile(regEx);
        String first = s.substring(0, 1);
        return p.matches(regEx, first);
    }

    //返回“ 小时 分钟 ”时间格式
    public static String getFormatedTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        return String.valueOf(Integer.parseInt(time) / 60).concat("小时 ")
                .concat(String.valueOf(Integer.parseInt(time) % 60)).concat("分钟");
    }

    // 把一个字符串中的大写转为小写，小写转换为大写：思路1
    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }

            }
        }
        return sb.toString();
    }

    /**
     * 验证车牌号是否合法
     *
     * @throws Exception
     * @功能：
     * @param：车牌号
     * @return：true 合法
     */
    public static boolean checkPlateNumber(String plateNumber) {
        boolean flag = false;
        try {
            String check1 = "^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{4}[a-zA-Z_0-9]?[a-zA-Z_0-9_\\u4e00-\\u9fa5]$|^[a-zA-Z]{2}\\d{7}$";
            String check2 = "^[无]{1}[0-9]{6,7}$";
            Pattern regex1 = Pattern.compile(check1);
            Pattern regex2 = Pattern.compile(check2);
            Matcher matcher1 = regex1.matcher(plateNumber);
            Matcher matcher2 = regex2.matcher(plateNumber);
            flag = matcher1.matches() || matcher2.matches();
        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    //是否需要蓝牙打印
    public static boolean isNeedBluetoothPrint() {
        String model= android.os.Build.MODEL;
        return !model.toUpperCase().contains("NX800");
    }

    //根据class 生成相应的对象
    @SuppressWarnings("unchecked")
    public static <T> T getImpl(Class<T> clazz) {

        try {
            return (T)clazz.newInstance();
            // return (T)Class.forName(className).getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}