package urils.ecaray.com.ecarutil;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;
import rxbus.ecaray.com.rxbuslib.rxbus.RxBusReact;
import urils.ecaray.com.ecarutils.Utils.NetUtils;
import urils.ecaray.com.ecarutils.Utils.SignUtils;
import urils.ecaray.com.ecarutils.Utils.ToastUtils;
import urils.ecaray.com.ecarutils.Utils.receive.NetConnectReceive;
import urils.ecaray.com.ecarutils.Utils.security.KeyStoreUtils;

public class MainActivity extends AppCompatActivity {

    boolean IS_DEBUG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLoger();

        RxBus.getDefault().register(this);
        NetUtils.init(RxBus.getDefault(), this);

        //检查签名  防止第二次打包
        if (!SignUtils.getSignInfo(this, -916382879)) {
            System.exit(0);
        }
        //判断代理-防止截包
        Toast.makeText(this, "是否是代理" + NetUtils.isWifiProxy(this), Toast.LENGTH_SHORT).show();

        //基于Keystore的加密解密
        final TextView after_encry_tv = (TextView) findViewById(R.id.after_encry_tv);
        final TextView after_decrypt_tv = (TextView) findViewById(R.id.after_decrypt_tv);
        final EditText need_deal_word_edit = (EditText) findViewById(R.id.need_deal_word_edit);


        final Button encryptBtn = (Button) findViewById(R.id.encryptBtn);
        final Button decryptBtn = (Button) findViewById(R.id.decryptBtn);

        //加密
        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                String need_deal_word_str = need_deal_word_edit.getText().toString().trim();
                String after_encry_str = KeyStoreUtils.getInstance().encryptString(need_deal_word_str, MainActivity.this);
                after_encry_tv.setText(after_encry_str);
            }
        });
        //解密
        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                String before_decrypt_str = after_encry_tv.getText().toString();
                String after_decrypt_str = KeyStoreUtils.getInstance().decryptString(before_decrypt_str, MainActivity.this);
                after_decrypt_tv.setText(after_decrypt_str);
            }

        });
    }

    @RxBusReact(clazz = Boolean.class, tag = NetConnectReceive.Tags.NET_CONNECT)
    public void showNetState(boolean isConnected) {
        if (isConnected) {
            Toast.makeText(this, "网络已连接  wifi" + NetUtils.isWifiConnected(this) + "移动网络" + NetUtils.isMobileConnected(this), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "网络已断开", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 方法描述： 初始化方法
     * <p>
     *
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
