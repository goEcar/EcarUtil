package urils.ecaray.com.ecarutils.Utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


/**
 * <b>@项目名：</b> ESchool<br>
 * <b>@包名：</b>  com.szqt.efxparents.helper<br>
 * <b>@创建者：</b> 陆先俊<br>
 * <b>@创建时间：</b> 2015/11/20 11:03<br>
 * <b>@公司：</b> 深圳掌舵科技有限公司<br>
 * <b>@邮箱：</b> CarlLu0712@163.com<br>
 * <p/>
 * <b>@描述</b> ：泡泡窗体弹出的帮助类<br>
 */
public class PopupWindowHelper {

    private PopupWindow mPopupWindow;

    private Activity mActivity;


    /**
     * 设置window窗体的透明度
     *
     * @param alpha 透明度的值
     */
    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = alpha;
        mActivity.getWindow().setAttributes(params);
    }


    /**
     * 设置弹出框的高度
     *
     * @param height 高度
     */
    public void setPopupWindowHeight(int height) {
        if (mPopupWindow != null) {
            mPopupWindow.setHeight(height);
        }
    }

    /**
     * 设置默认的高度
     */
    public void setPopupWindowDefaultHeight() {
        if (mPopupWindow != null) {
            mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 显示弹出窗体
     *
     * @param showView 需要显示出来的view
     * @param rootView 需要依附的根view
     */
    public void showPopupWindow(View showView, View rootView) {
        //设置窗体弹出显示的view
        mPopupWindow.setContentView(showView);
        //判断窗体是否是显示的
        if (mPopupWindow.isShowing()) {
            //显示就隐藏
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            setWindowAlpha(0.6f);
        }
    }

    /**
     * 让popupwindow消失
     */
    public void dismissPopupWindow() {
        mPopupWindow.dismiss();
    }
}
