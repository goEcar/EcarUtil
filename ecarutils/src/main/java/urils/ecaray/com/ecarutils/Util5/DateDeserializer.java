package urils.ecaray.com.ecarutils.Util5;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间格式化类
 * @author kb
 *
 */
public class DateDeserializer implements JsonDeserializer<Date> , JsonSerializer<Object>{

	public JsonElement serialize(Date date, Type typeOfT,JsonSerializationContext context){
		return new JsonPrimitive("/Date(" + date.getTime() + ")/");
	}

	public JsonElement serialize(Object arg0, Type arg1,JsonSerializationContext arg2){
		Date date = (Date) arg0;
		return new JsonPrimitive("/Date(" + date.getTime() + ")/");
	}

	public Date deserialize(JsonElement json, Type typeOfT,JsonDeserializationContext context)
			throws JsonParseException {
		String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
		Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
		Matcher matcher = pattern.matcher(json.getAsJsonPrimitive().getAsString());
		String result = matcher.replaceAll("$2");
		//return new Date(new Long(result));
		return new Date(Long.valueOf(result));
	}

	/**
	 * @author 格式化时间
	 * @strFormat 格式化字符串
	 */
	public static String formatDate(String strFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
		//Calendar calandar = Calendar.getInstance();
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate.getTime());
	}
	public static void showDateLog(long t){
		Date date = new Date(t);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
	}
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 * @return
	 */
	public static long strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate.getTime();
	}
	public static String longDateToStr(long d){
		Date date = new Date(d);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(date);
		return dateString;
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
}
