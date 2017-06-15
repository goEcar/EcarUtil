package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/*
 *===============================================
 *
 * 文件名:${type_name}
 *
 * 描述: 
 *
 * 作者:
 *
 * 版权所有:深圳市亿车科技有限公司
 *
 * 创建日期: ${date} ${time}
 *
 * 修改人:   金征
 *
 * 修改时间:  ${date} ${time} 
 *
 * 修改备注: 
 *
 * 版本:      v1.0 
 *
 *===============================================
 */
public class SecondPackage{
    private final static String TAG = "SecondPackage" ;
    int hashCode;//hashcode

    public SecondPackage(Context context,int hashCode){
        // TODO Auto-generated constructor stub
        this.context = context;
        this.hashCode=hashCode;
    }
    private Context context;


    /************protect second package*******************/
    private void byte2hex(byte b, StringBuffer buf) {

        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',

                '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        int high = ((b & 0xf0) >> 4);

        int low = (b & 0x0f);

        buf.append(hexChars[high]);

        buf.append(hexChars[low]);

    }
	/*

	* Converts a byte array to hex string

	*/

    private String toHexString(byte[] block) {

        StringBuffer buf = new StringBuffer();



        int len = block.length;



        for (int i = 0; i < len; i++) {

            byte2hex(block[i], buf);

            if (i < len-1) {

                buf.append(":");

            }

        }

        return buf.toString();

    }

    public boolean getSignInfo() {
        boolean checkright = false;
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int code = sign.hashCode();

            Log.i(TAG, "hashCode:" + code);
            //对比MD5值和hashcode是否和自己原来的MD5相同
            if(code == hashCode){
                checkright = true;
            }


            //parseSignature(sign.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkright;
    }

    void parseSignature(byte[] signature) throws CertificateException {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature));
            byte[] buffer = cert.getEncoded();
            System.out.println( "md5: "+ new String(buffer));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }
    /***********************************/

}
