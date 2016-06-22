package urils.ecaray.com.ecarutils.Util7;

import com.google.gson.Gson;

/**
 * JSON解析类
 * 
 * @author kb
 * 
 */
public class JsonUtil {

	/**
	 * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
	 * 
	 * @param str
	 *            目标对象
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (null != str && str.length() != 0) {
			return false;
		}
		return true;
	}

	// 获取Object
	public static <T extends Object> T getObject(String jsonString, Class<T> cls) {

		return (T) new Gson().fromJson(jsonString, cls);
	}

//	// 获取list
//	public static SparseArray<Object> getList(String jsonString, Class<?> cls) {
//		JSONObject jsonOb;
//		SparseArray<Object> list = new SparseArray<Object>();
//		try {
//			jsonOb = new JSONObject(jsonString);
//			if (jsonOb.has(Consts.net_state)) {
//				list.put(Consts.net_get_state,
//						jsonOb.getString(Consts.net_state));
//			}
//			if (jsonOb.has(Consts.net_totalCount)) {
//				list.put(Consts.net_get_totalCount,
//						jsonOb.getString(Consts.net_totalCount));
//			}
//			if (jsonOb.has(Consts.net_resultcode)) {
//				list.put(Consts.net_get_resultcode,
//						jsonOb.getString(Consts.net_resultcode));
//			}
//			if (jsonOb.has(Consts.net_resultmsg)) {
//				list.put(Consts.net_get_resultmsg,
//						jsonOb.getString(Consts.net_resultmsg));
//			}
//			if (jsonOb.has(Consts.net_data)) {
//				list.put(Consts.net_get_data,
//						JSON.parseArray(jsonOb.getString(Consts.net_data), cls));
//			}
//			if (jsonOb.has(Consts.net_ts)) {
//				list.put(Consts.net_get_ts, jsonOb.getString(Consts.net_ts));
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (list.size() == 0) {// 如果为获取数据就设定state为0
//			list.put(Consts.net_get_state, 0);
//		}
//		return list;
//	}
//
//
//	/**
//	 * @功能：设置u和t
//	 * @param：
//	 * @return：
//	 * @throws Exception
//	 */
//	public static void setUandTV(String jsonStr, Context context) {
//		if (jsonStr == null || TextUtils.isEmpty(jsonStr) || context == null) {
//			return;
//		}
//		AppApplication app = AppManager.getInstance().getAppApplication();
//		JSONObject jsonOb;
//		try {
//			jsonOb = new JSONObject(jsonStr);
//			SpUtil spUtil = new SpUtil(context, Consts.SP_NAME);
//
//			String uKey = null;
//			if (!app.getIsLogin()) {// 已登录或者测试环境下不需要赋值
//				if (jsonOb.has(Consts.net_u)) {
//					uKey = jsonOb.getString(Consts.net_u);
//
//					if (uKey != null && !TextUtils.isEmpty(uKey)) {
//						app.setuKey(uKey);
//						spUtil.save(Consts.U_KEY, uKey);
//					}
//				}
//			}
//
//			String tKey = null;
//			if (jsonOb.has(Consts.net_t)) {
//				tKey = jsonOb.getString(Consts.net_t);
//				if (tKey != null && !TextUtils.isEmpty(tKey)) {
//					app.settKey(tKey);
//					spUtil.save(Consts.T_KEY, tKey);
//				}
//			}
//
//			String vKey = null;
//			if (jsonOb.has(Consts.net_v)) {
//				vKey = jsonOb.getString(Consts.net_v);
//				if (vKey != null && !TextUtils.isEmpty(vKey)) {
//					app.setvKey(vKey);
//					spUtil.save(Consts.V_KEY, vKey);
//				}
//			}
//
//			TagUtil.showLogDebug("----------", "uKey=" + app.getU() + "tKey="
//					+ app.getT() + "vKey=" + app.getV());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}