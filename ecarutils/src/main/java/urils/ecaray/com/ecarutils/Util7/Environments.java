package urils.ecaray.com.ecarutils.Util7;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.telephony.TelephonyManager;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Environments {
	public static final String Wifi_State ="wifi";
	public static final String Mobile_State ="3G";
	public static final String No_Connection="no_conection";
	public static String Net_State= "3G";//默认3G状�?
	/**
	 * 获取SD卡路�?
	 * @return
	 */
	public static String getSDPath(){
		File sdDir = null;
		if(isSDEnable()){
			sdDir = Environment.getExternalStorageDirectory();//获取跟目�?
			return sdDir.toString();
		}
		return null;
	}
/**
 * 判断SD是否可用
 * @return true可以，false不可用
 */
	public static boolean isSDEnable(){
		return Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存�?
	}
	/**
	 * 读取输入数据�?
	 * @param is 输入�?
	 * @return 字节�?
	 */
	public static byte[] readInputStream(InputStream is) {   
		ByteArrayOutputStream baos=new ByteArrayOutputStream();   
		byte[] buffer=new byte[1024 ];   
		int length=-1 ;   
		try {   
			while((length=is.read(buffer))!=-1 ){   
				baos.write(buffer, 0 , length);   
			}   
			baos.flush();   
		} catch (IOException e) {   
			e.printStackTrace();   
		}   
		byte[] data=baos.toByteArray();   
		try {   
			is.close();   
			baos.close();   
		} catch (IOException e) {   
			e.printStackTrace();   
		}   
		return data;   
	} 
	/**
	 * 格式化图片数据流
	 * @param imageByte 图片字节�?
	 * @param maxSize  图片�?��尺寸
	 * @return  Bitmap对象
	 */
	public static Bitmap decodeBitmap(byte imageByte[], final int maxSize){
		Bitmap bitmap =null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length,
				options);
		options.inJustDecodeBounds = false;
		int be = (int) (options.outWidth / (float) maxSize);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
				imageByte.length, options);
		return bitmap;
	}
	/**
	 * IMSI：International Mobile SubscriberIdentification Number    国际移动用户识别�?
	 * @param context
	 * @return
	 */
	public static final String getIMSI(Context context){
		TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		return imsi;
	}

	/**
	 * 获取SD卡路�?
	 * @return
	 */
	public static String getSdPath(){
		boolean isSDExist=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(isSDExist){
			//如果存在SDcard 就找到跟目录?
			File sdcardDir=Environment.getExternalStorageDirectory(); 
			return sdcardDir.toString();
		}
		return null;
	}
	/**
	 * 创建�?��缩放的图�?
	 * 
	 * @param path
	 *            图片地址
	 * @param w
	 *            图片宽度
	 * @param h
	 *            图片高度
	 * @return 缩放后的图片
	 */
	public static Bitmap decodeBitmapByByte(byte imageByte[], int w) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存�?
			BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length,opts);
			int srcWidth = opts.outWidth;// 获取图片的原始宽�?
			int srcHeight = opts.outHeight;// 获取图片原始高度
			int destWidth = 0;
			int destHeight = 0;
			// 缩放的比�?
			double ratio = 0.0;
			if (srcWidth < w ) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长�?
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} 
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能�?过inSampleSize来进行缩放，其�?表明缩放的�?数，SDK中建议其值是2的指数�?
			newOpts.inSampleSize = (int) ratio + 1;
			// inJustDecodeBounds设为false表示把图片读进内存中
			newOpts.inJustDecodeBounds = false;
			// 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			// 获取缩放后图�?
			return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length,newOpts);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
/**
 *  获取系统版本�?or 版本名称  
 * @param context 
 * @param getCode true为版本号，false 为版本名�?
 * @return 
 */
	public static  String getVersionName(Context context, boolean getCode) {
		// 获取packagemanager的实�?
		String version = "";
		PackageManager packageManager = context.getPackageManager();
		try {
			// getPackageName()是你当前类的包名�?代表是获取版本信�?
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			if(getCode)
				version = packInfo.versionCode+"";
			else
				version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

}
