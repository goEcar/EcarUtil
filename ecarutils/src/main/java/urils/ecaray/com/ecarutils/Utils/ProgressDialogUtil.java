package urils.ecaray.com.ecarutils.Utils;



import android.app.ProgressDialog;
import android.content.Context;

public  class ProgressDialogUtil {
	 
	
	public static ProgressDialog progressDialog = null;
	
	public static void showProgressDialog(Context context,String dialogTitle,String msg) {
		progressDialog = ProgressDialog.show(context, dialogTitle,msg, true, true);
	}

}
