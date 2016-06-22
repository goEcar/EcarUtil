package urils.ecaray.com.ecarutils.Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateString() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateString() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(currentTime);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 发布遛狗的时间是否过期
     *
     * @param str ** 1月1日 周四 18:00
     * @return true 没过期可以报名 //false 过期不可报名
     */
    public static boolean ifOutTime(String str) {
        try {
            boolean flag = false;
            Date now = new Date();
            int topic_Year = Integer
                    .valueOf(str.substring(0, str.indexOf("年")));
            int topic_Month = Integer.valueOf(str.substring(
                    str.indexOf("年") + 1, str.indexOf("月")));
            int topic_Day = Integer.valueOf(str.substring(str.indexOf("月") + 1,
                    str.indexOf("日")));
            int topic_Hour = Integer.valueOf(str.substring(
                    str.lastIndexOf(" ") + 1, str.indexOf(":")));
            int topic_Min = Integer.valueOf(str.substring(str.indexOf(":") + 1,
                    str.length()));
            String time = topic_Year + "-" + topic_Month + "-" + topic_Day
                    + " " + topic_Hour + ":" + topic_Min + ":00";
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);

            long result = now.getTime() - date.getTime();
            if (result < 0) {
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 控制事件点击次数
     */
    private static long timeOut;
}
