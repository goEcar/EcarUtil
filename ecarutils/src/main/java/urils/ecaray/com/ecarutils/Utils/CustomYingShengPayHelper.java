package urils.ecaray.com.ecarutils.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2015/11/23 0023.
 */
public class CustomYingShengPayHelper {
    Context mContext = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 2:
                    case 3:
                    case 4:
                        CustomYingShengPayHelper.closeProgress();
                        String e = (String) msg.obj;
                        CustomYingShengPayHelper.this.showInstallConfirmDialog(CustomYingShengPayHelper.this.mContext, e);
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    };

    public CustomYingShengPayHelper(Context context) {
        this.mContext = context;
    }

    public boolean detectMobile_sp(String apkname) {
        boolean isMobile_spExist = this.isMobile_spExist();
        if (!isMobile_spExist) {
            File cacheDir = this.mContext.getCacheDir();
            final String cachePath = cacheDir.getAbsolutePath() + "/temp.apk";
            this.retrieveApkFromAssets(this.mContext, apkname, cachePath);
            (new Thread(new Runnable() {
                public void run() {
                    Message msg1 = CustomYingShengPayHelper.this.mHandler.obtainMessage(4, cachePath);
                    CustomYingShengPayHelper.this.mHandler.sendMessage(msg1);
                }
            })).start();
        }
        return isMobile_spExist;
    }

    public void showInstallConfirmDialog(final Context context, final String cachePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("为保证您的交易安全，需要您安装银盛安全支付服务，才能进行付款。\n\n点击确定，立即安装。");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(268435456);
                intent.setDataAndType(Uri.parse("file://" + cachePath), "application/vnd.android.package-archive");
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", (DialogInterface.OnClickListener) null);
        builder.show();
    }

    public boolean isMobile_spExist() {
        PackageManager manager = this.mContext.getPackageManager();
        List pkgList = manager.getInstalledPackages(0);

        for (int i = 0; i < pkgList.size(); ++i) {
            PackageInfo pI = (PackageInfo) pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.yinsheng.android.app")) {
                Double versionName = Double.parseDouble(pI.versionName);
                if (versionName<=3) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    public boolean retrieveApkFromAssets(Context context, String fileName, String path) {
        boolean bRet = false;

        try {
            InputStream e = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            boolean i = false;

            int i1;
            while ((i1 = e.read(temp)) > 0) {
                fos.write(temp, 0, i1);
            }

            fos.close();
            e.close();
            bRet = true;
        } catch (IOException var10) {
            var10.printStackTrace();
        }

        return bRet;
    }

    static void closeProgress() {
        try {
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
