package urils.ecaray.com.ecarutils.Utils.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;
import urils.ecaray.com.ecarutils.Utils.NetUtils;

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
public class NetConnectReceive extends BroadcastReceiver {
    /**
     *   1.执行NetUtils 的init方法 传入rxbus
     *
     *   2.需要用Rxbus接收
     *                   @RxBusReact(clazz = Boolean.class,tag = NetConnectReceive.Tags.NET_CONNECT)
     *
     *   3.具体网络类型使用  NetUtils.isWifiConnected， NetUtils.isMobileConnected判断
     *
     * */

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetUtils.isNetworkConnected(context);
//            System.out.println("网络状态：" + isConnected);
//            System.out.println("wifi状态：" + NetUtils.isWifiConnected(context));
//            System.out.println("移动网络状态：" + NetUtils.isMobileConnected(context));
//            System.out.println("网络连接类型：" + NetUtils.getConnectedType(context));
//            if (isConnected) {
////                Toast.makeText(context, "网络已连接  wifi"+NetUtils.isWifiConnected(context) +"移动网络"+NetUtils.isMobileConnected(context), Toast.LENGTH_LONG).show();
//            } else {
////                Toast.makeText(context, "网络已断开", Toast.LENGTH_LONG).show();
//            }
            NetUtils.rxBus.post(isConnected,Tags.NET_CONNECT);
        }
    }

    public  interface Tags{
        String NET_CONNECT = "netconnect";
    }
}
