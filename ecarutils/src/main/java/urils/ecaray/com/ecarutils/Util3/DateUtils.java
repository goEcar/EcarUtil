package urils.ecaray.com.ecarutils.Util3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ===============================================
 * 
 * 项目名称: parkBees-andriod
 * 
 * 包: com.chmtech.parkbees.utils
 * 
 * 类名称: DateUtils
 * 
 * 类描述:计算时间的工具类
 * 
 * 创建人: 金征
 * 
 * 创建时间: 2015-5-7 下午8:11:41
 * 
 * 修改人:
 * 
 * 修改时间: 2015-5-7 下午8:11:41
 * 
 * 修改备注:
 * 
 * 版本:
 * 
 * ===============================================
 */
public class DateUtils {
	/**
	 * @功能：获取几个月后或者几个月前的日期
	 * @param：mon:负数为往前，正数为往后
	 * @return：
	 * @throws Exception
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
	 * @功能：获取时间 yyyy-mm-dd
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getYearDayOne(long day) {
		// 定义日期格式
		SimpleDateFormat atter = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date(day);
		return atter.format(date);
	}

	/**
	 * @功能：获取时间 yyyy-mm-dd
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getYearDayTwo(long day) {
		// 定义日期格式
		SimpleDateFormat atter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(day);
		return atter.format(date);
	}

	public static String getAllDay(long day) {
		// 定义日期格式
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
		Date date = new Date(day);
		return matter.format(date);
	}

	/**
	 * @功能：获取时间 hh:mm
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static String getTimeTwo(long day) {
		// 定义日期格式
		SimpleDateFormat matter = new SimpleDateFormat("HH:mm");
		Date date = new Date(day);
		return matter.format(date);
	}

	/**
	 * @功能：获取当前时间 （hh:mm:ss格式）
	 * @param：leng：秒数
	 * @return：
	 * @throws Exception
	 */
	public static String[] getTimes(long leng) {
		if (leng <= 0) {
			return new String[] { "00", "00", "00" };// 数据错误时
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
	 * @功能：获取时间长度 （hh小时mm分 格式）
	 * @param：leng：秒数
	 * @return：
	 * @throws Exception
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
	 * @功能：获取时间长度 （dd天hh小时mm分 格式）
	 * @param：leng：秒数
	 * @return：
	 * @throws Exception
	 */
	public static String getTotalTimeDHM(long leng) {
		leng /= 1000;// 获取秒数
		String[] times = new String[3];
		long dd = leng / (60 * 60 * 24);// 天数
		long hh = (leng - 60 * 60 * 24 * dd) / (60 * 60);// 小时
		long mm = (leng / 60) % 60;// 分钟
		long ss = leng % 60;// 秒
		StringBuilder sb = new StringBuilder();
		times[0] = String.valueOf(dd);
		times[1] = (hh < 12) ? (DataFormatUtil.addText(sb, "0",
				String.valueOf(hh))) : (String.valueOf(hh));
		times[2] = (mm < 10) ? (DataFormatUtil.addText(sb, "0",
				String.valueOf(mm))) : (String.valueOf(mm));
		return DataFormatUtil.addText(sb, times[0], "天" + times[1], "小时",
				times[2], "分");

	}

	/**
	 * @功能：计算相差的天数
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static long daysOfTwo(long date1, long date2) {

		Date smdate = new Date(date1);
		Date bdate = new Date(date2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
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

	//根据毫秒得到00:00:00样式
	public static String getTimeByMS(long leng ,int delayed){
		String[] times = new String[3];
		long hh = leng / (60 * 60 );
		long mm = (leng - (60 * 60 *hh)) / 60;
		long ss = (leng - (60 * 60 *hh + 60 * mm)) % 60;
		if (delayed > 0){
			ss = ss + delayed;
			if (ss == 60){
				ss =0;
				mm++ ;
				if (mm == 60){
					mm = 00;
					hh++;
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		times[0] = (hh < 10) ? "0"+String.valueOf(hh) : String.valueOf(hh);
		times[1] = (mm < 10) ? "0"+ String.valueOf(mm) : String.valueOf(mm);
		times[2] = (ss < 10) ? "0"+ String.valueOf(ss) : String.valueOf(ss);
		return DataFormatUtil.addText(sb, times[0], ":" + times[1], ":",
				times[2]);
	}

	//根据毫秒得到00:00样式
	public static String getTime(long leng){
		leng = leng / 1000;
		String[] times = new String[2];
		long hh = leng / (60 * 60 );
		long mm = (leng - (60 * 60 *hh)) / 60;
		StringBuilder sb = new StringBuilder();
		times[0] = String.valueOf(hh);
		times[1] = (mm < 10) ? "0"+ String.valueOf(mm) : String.valueOf(mm);
		return DataFormatUtil.addText(sb, times[0], "小时" + times[1], "分");
	}
}
