package urils.ecaray.com.ecarutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;
import rxbus.ecaray.com.rxbuslib.rxbus.RxBusReact;
import urils.ecaray.com.ecarutils.Utils.NetUtils;
import urils.ecaray.com.ecarutils.Utils.TagUtil;
import urils.ecaray.com.ecarutils.Utils.ToastUtils;
import urils.ecaray.com.ecarutils.Utils.receive.NetConnectReceive;

public class MainActivity extends AppCompatActivity {

    boolean IS_DEBUG =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLoger();

        RxBus.getDefault().register(this);
        NetUtils.init(RxBus.getDefault(),this );
    }

    @RxBusReact(clazz = Boolean.class,tag = NetConnectReceive.Tags.NET_CONNECT)
    public void showNetState(boolean isConnected){
        if (isConnected) {
                Toast.makeText(this, "网络已连接  wifi"+NetUtils.isWifiConnected(this) +"移动网络"+NetUtils.isMobileConnected(this), Toast.LENGTH_LONG).show();
        } else {
                Toast.makeText(this, "网络已断开", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 方法描述： 初始化方法
     *<p>
     * @param
     * @return
     */
    private void initLoger() {
        Logger
                .init("TagUtil")               // default tag : 默认的tag
                .setMethodCount(2)            // 打印的方法深度层级
                .hideThreadInfo()         // 是否隐藏当前执行的线程类型
                .setLogLevel(IS_DEBUG ? LogLevel.FULL : LogLevel.NONE);  // 是否开启log    LogLevel.FULL：开启    LogLevel.NONE：关闭

        ToastUtils.init(getApplication()); //初始化吐司工具
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);

    }
}
