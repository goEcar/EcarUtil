package urils.ecaray.com.ecarutils.Util6;



import android.app.ProgressDialog;
import android.content.Context;

public  class ProgressDialogUtil {
	 
	
	public static ProgressDialog progressDialog = null;
	
	public static void showProgressDialog(Context context,String dialogTitle,String msg) {
		progressDialog = ProgressDialog.show(context, dialogTitle,msg, true, true);
	}

}
