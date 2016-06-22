package urils.ecaray.com.ecarutils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ===============================================
 * <p/>
 * 项目名称: parkBees-andriod
 * <p/>
 * 包: com.chmtech.parkbees.utils
 * <p/>
 * 类名称: DateUtils
 * <p/>
 * 类描述:计算时间的工具类
 * <p/>
 * 创建人: 金征
 * <p/>
 * 创建时间: 2015-5-7 下午8:11:41
 * <p/>
 * 修改人:
 * <p/>
 * 修改时间: 2015-5-7 下午8:11:41
 * <p/>
 * 修改备注:
 * <p/>
 * 版本:
 * <p/>
 * ===============================================
 */
public class DateUtils {
    /**
     * @throws Exception
     * @功能：获取几个月后或者几个月前的日期
     * @param：mon:负数为往前，正数为往后
     * @return：
     */
    public static String getToMonDate(int mon) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        // 将calendar装换为Date类型
        Date date = new Date();
        // 将date类型转换为BigDecimal类型（该类型对应oracle中的number类型）
        // 获取当前时间的前6个月
        calendar.add(Calendar.MONTH, mon);
        Date date02 = calendar.getTime();
        return matter.format(date02);
    }

    @SuppressWarnings("deprecation")
    public static String getToMonDate(int mon, String time) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
        int year = Integer.valueOf(time.substring(0, 4));
        int month = Integer.valueOf(time.substring(5, 7));
        int day = Integer.valueOf(time.substring(8, 10));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        calendar.add(Calendar.MONTH, mon);
        return matter.format(calendar.getTime());
    }

    /**
     * @throws Exception
     * @功能：获取时间 yyyy-mm-dd
     * @param：
     * @return：
     */
    public static String getYearDay(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(day);
        return matter.format(date);
    }

    public static String getAllDay(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(day);
        return matter.format(date);
    }

    /**
     * @throws Exception
     * @功能：获取时间 hh小时mm分
     * @param：
     * @return：
     */
    public static String getTime(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("h小时mm分");
        Date date = new Date(day);
        return matter.format(date);
    }

    /**
     * @throws Exception
     * @功能：获取时间 hh:mm
     * @param：
     * @return：
     */
    public static String getTimeTwo(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("hh:mm");
        Date date = new Date(day);
        return matter.format(date);
    }

    /**
     * @throws Exception
     * @功能：获取当前时间 （hh:mm:ss格式）
     * @param：leng：秒数
     * @return：
     */
    public static String[] getTimes(long leng) {
        if (leng < 0) {
            return new String[]{"0", "0", "0"};// 数据错误时
        }
        String[] times = new String[3];
        long hh = leng / (60 * 60);// 小时
        long mm = (leng / 60) % 60;// 分钟
        long ss = leng % 60;// 秒
        StringBuilder sb = new StringBuilder();
        times[0] = (hh < 10) ? (DataFormatUtil.addText(sb, "0",
                String.valueOf(hh))) : (String.valueOf(hh));
        times[1] = (mm < 10) ? (DataFormatUtil.addText(sb, "0",
                String.valueOf(mm))) : (String.valueOf(mm));
        times[2] = (ss < 10) ? (DataFormatUtil.addText(sb, "0",
                String.valueOf(ss))) : (String.valueOf(ss));
        return times;

    }

    /**
     * @throws Exception
     * @功能：获取时间长度 （hh小时mm分 格式）
     * @param：leng：秒数
     * @return：
     */
    public static String getTotalTime(long leng) {
        leng /= 1000;// 获取秒数
        String[] times = new String[2];
        long hh = leng / (60 * 60);// 小时
        long mm = (leng / 60) % 60;// 分钟
        long ss = leng % 60;// 秒
        StringBuilder sb = new StringBuilder();
        times[0] = (hh < 12) ? (DataFormatUtil.addText(sb, "0",
                String.valueOf(hh))) : (String.valueOf(hh));
        times[1] = (mm < 10) ? (DataFormatUtil.addText(sb, "0",
                String.valueOf(mm))) : (String.valueOf(mm));
        return DataFormatUtil.addText(sb, times[0], "小时", times[1], "分");

    }

    /**
     * @throws Exception
     * @功能：计算相差的天数
     * @param：
     * @return：
     */
    public static long daysOfTwo(long date1, long date2) {

        Date smdate = new Date(date1);
        Date bdate = new Date(date2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return between_days;

    }

    /**
     * @throws Exception
     * @功能：计算相差的时间
     * @param：
     * @return：
     */
    public static String timeOfTwo(long start, long end) {
        Date startDate = new Date(start);
        Date endDate = new Date(end);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startDate = sdf.parse(sdf.format(startDate));
            endDate = sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long days = (time2 - time1) / (1000 * 3600 * 24);
        long hours = (time2 - time1) / (1000 * 3600) - days * 24;
        long mins = (time2 - time1) / (1000 * 60) - days * 24 * 60 - hours * 60;
        return days + "天" + hours + "时" + mins + "分";
    }

    public static long timeToLong(String time) {
        long intime = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(time);
            intime = date.getTime();
        } catch (ParseException e) {
        }
        return intime;
    }
}
