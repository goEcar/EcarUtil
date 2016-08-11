package urils.ecaray.com.ecarutils.Utils;


import com.orhanobut.logger.Logger;

//log打印
public class TagUtil {


    /**
     * 方法描述：等同于  Log.i
     * <p>
     *
     * @param
     * @return
     */
    public static void i(String o) {
        Logger.i(o);
    }
    /**
     * 方法描述：等同于  Log.i
     * <p>
     *
     * @param
     * @return
     */
    public static void i(String tag, String o) {
        Logger.i(tag, o);
    }
    /**
     * 方法描述：等同于  Log.i
     * <p>
     *
     * @param
     * @return
     */
    public static void i(String tag, String action, String des) {
        Logger.i(tag, "[ " + action + " ]" + ":" + des);
    }

    /**
     * 方法描述：等同于  Log.d
     * <p>
     *
     * @param
     * @return
     */
    public static void d(String o) {
        Logger.d(o);

    }
    /**
     * 方法描述：等同于  Log.d
     * <p>
     *
     * @param
     * @return
     */
    public static void d(String tag, String o) {
        Logger.d(tag, o);
    }
    /**
     * 方法描述：等同于  Log.d
     * <p>
     *
     * @param
     * @return
     */
    public static void d(String tag, String action, String des) {
        Logger.d(tag, "[ " + action + " ]" + ":" + des);
    }

    /**
     * 方法描述：等同于  Log.e
     * <p>
     *
     * @param
     * @return
     */
    public static void e(String o) {
        Logger.e(o);

    }
    /**
     * 方法描述：等同于  Log.e
     * <p>
     *
     * @param
     * @return
     */
    public static void e(String tag, String o) {
        Logger.e(tag, o);
    }
    /**
     * 方法描述：等同于  Log.e
     * <p>
     *
     * @param
     * @return
     */
    public static void e(String tag, String action, String des) {
        Logger.e(tag, "[ " + action + " ]" + ":" + des);
    }

    /**
     * 方法描述：等同于  Log.v
     * <p>
     *
     * @param
     * @return
     */
    public static void v(String o) {
        Logger.v(o);

    }
    /**
     * 方法描述：等同于  Log.v
     * <p>
     *
     * @param
     * @return
     */
    public static void v(String tag, String o) {
        Logger.v(tag, o);
    }
    /**
     * 方法描述：等同于  Log.v
     * <p>
     *
     * @param
     * @return
     */
    public static void v(String tag, String action, String des) {
        Logger.v(tag, "[ " + action + " ]" + ":" + des);
    }


    /**
     * 方法描述：输出结果为json格式
     * <p>
     *
     * @param
     * @return
     */
    public static void json(String json) {
        Logger.json(json);
    }
    /**
     * 方法描述：输出结果为json格式
     * <p>
     *
     * @param
     * @return
     */
    public static void json(String tag, String json) {
        Logger.json(tag, json);
    }


}
