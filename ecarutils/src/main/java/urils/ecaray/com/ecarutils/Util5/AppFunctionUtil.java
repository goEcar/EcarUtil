package urils.ecaray.com.ecarutils.Util5;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class AppFunctionUtil {

    /**
     * @功能：跳转到另外的Activity
     * @param：mClass:目标activity类的class
     * @return：
     * @throws Exception
     */
    public static void openActivity(Activity context, Class mClass) {
        Intent intent = new Intent();
        intent.setClass(context, mClass);
        context.startActivity(intent);

    }
}
