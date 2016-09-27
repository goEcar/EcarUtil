package urils.ecaray.com.ecarutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import urils.ecaray.com.ecarutils.Utils.TagUtil;
import urils.ecaray.com.ecarutils.Utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    boolean IS_DEBUG =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLoger();
        TagUtil.e("测试");
        ToastUtils.showToastShort("测试");
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

}
