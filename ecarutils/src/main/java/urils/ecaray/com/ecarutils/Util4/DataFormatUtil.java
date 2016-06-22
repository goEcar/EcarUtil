package urils.ecaray.com.ecarutils.Util4;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DataFormatUtil {
    // 添加字符串
    public static synchronized String addText(StringBuilder sb, String... texts) {
        sb.delete(0, sb.length());
        for (String str : texts) {
            sb.append(str);
        }
        return sb.toString();
    }
    // float保留一位数字
    public static Float floatTo1p(float data) {
        BigDecimal bd = new BigDecimal(data);
        float num = bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return num;
    }

    /**
     * @param count byte长度
     * @return 小于1m返回kb，否则返回mb
     * @功能：将byte转化为kb或者mb
     */
    public static String byte2Mega(long count) {
        StringBuffer sbf = new StringBuffer();
        if (count < 1024 * 1024) {// 小于1m
            float total = (float) count / 1024;
            sbf.append(floatTo1p(total) + "KB");
        } else {
            float total = (float) count / 1024 / 1024;
            sbf.append(floatTo1p(total) + "MB");
        }

        return sbf.toString();
    }

    // double除以万保留两位数字
    public static String doubleTo2pRTstr(Double data) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(data / 10000.00d);
    }

    //double除以万保留两位数字
    public static double doubleTo2p(double data) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return Double.parseDouble(df.format(data / 10000.00d));
    }


    // double保留两位数字
    public static String doubleTo2pRTstr(Double data, boolean isCast) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.FLOOR);

        return df.format(data);
    }

    // double除以万保留两位数字
    public static String doubleTo2pRTstr(String data) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);

            return df.format((Double.parseDouble(data) / 10000.00d));
        } catch (Exception e) {
            return "0";
        }
    }


    // double除以百保留两位数字
    public static String doubleTo2pRTstr100(String data) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);

            return df.format((Double.parseDouble(data) / 100.00d));
        } catch (Exception e) {
            return "0";
        }
    }


    // double保留两位数字   toCast是否除以万
    public static String doubleTo2pRTstr(String data, boolean toCast) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.FLOOR);

            return df.format(toCast ? Double.parseDouble(data) / 10000.00d : Double.parseDouble(data));
        } catch (Exception e) {
            return "0";
        }
    }

    // double保留1位数字
    public static String doubleTo1pRTstr(String data) {
        try {
            DecimalFormat df = new DecimalFormat("0.0");
            df.setRoundingMode(RoundingMode.FLOOR);
            return df.format((Double.parseDouble(data) / 10000.00d));
        } catch (Exception e) {
            return "0";
        }
    }


    // double保留一位数字
    public static Double doubleTo1p(Double data) {
        BigDecimal bd = new BigDecimal(data);
        double num = bd.setScale(1, BigDecimal.ROUND_FLOOR).doubleValue();
        return num;
    }

    // double保留0位数字
    public static String doubleTo0p(Double data) {
        BigDecimal bd = new BigDecimal(data);
        double num = bd.setScale(1, BigDecimal.ROUND_FLOOR).doubleValue();
        return String.valueOf((int) num);
    }


    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）	 *	 * @param str 无逗号的数字	 * @return 加上逗号的数字
     */
    public static String toMoneyFormat(String text) {        // 将传进数字反转
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int setDip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
