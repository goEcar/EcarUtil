package urils.ecaray.com.ecarutils.Util5;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by fgu4sa on 2016/1/14.
 */
public class IntentUtil {

    public static void openActivity(Activity context, Class mClass) {
        Intent intent = new Intent();
        intent.setClass(context, mClass);
        context.startActivity(intent);

    }

    public static void openActivityAndFinish(Activity context, Class mClass) {
        Intent intent = new Intent();
        intent.setClass(context, mClass);
        context.startActivity(intent);
        context.finish();
    }
}
