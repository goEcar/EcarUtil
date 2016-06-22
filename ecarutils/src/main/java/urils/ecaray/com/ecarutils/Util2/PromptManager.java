package urils.ecaray.com.ecarutils.Util2;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import urils.ecaray.com.ecarutils.R;


public class PromptManager {
    public static ProgressDialog dialog;


    /**
     * 询问
     *
     * @param context
     */
    public static void showAskDialog(Context context, String text,
                                     OnClickListener ok, OnClickListener cancel) {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle(R.string.app_name).setMessage(text)
                .setPositiveButton("确定", ok)//
                .setNegativeButton("取消", cancel)//
                .show();
    }

    public static void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }


    private static Toast toast;

    public static void showToast(String msg, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showLongToast(String msg, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
