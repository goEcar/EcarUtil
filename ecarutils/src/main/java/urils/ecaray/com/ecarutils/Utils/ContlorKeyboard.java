package urils.ecaray.com.ecarutils.Utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/*
 *===============================================
 *
 * 文件名:${type_name}
 *
 * 描述: 键盘弹出时不覆盖提交按钮
 * 作者:
 *
 * 版权所有:深圳市亿车科技有限公司
 *
 * 创建日期: ${date} ${time}
 *
 * 修改人:   金征
 *
 * 修改时间:  ${date} ${time} 
 *
 * 修改备注: 
 *
 * 版本:      v1.0 
 *
 *===============================================
 */
public class ContlorKeyboard {

    private int AREA_HEIGHT = 100;//遮盖的高度

    public boolean isVisibility; //是否上移
    public boolean isNeedContlor; //是否需要调整



    public ContlorKeyboard setAREA_HEIGHT(int AREA_HEIGHT) {
        this.AREA_HEIGHT = AREA_HEIGHT;
        return this;
    }

    /**
     * 方法描述：解决系统键盘挡住按钮的问题
     *<p>
     * @param  root 当前不觉得最顶层View    scrollToView：需要显示的View(比如button)
     * @return
     */
    public void controlKeyboardLayoutToSys(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                // 若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > AREA_HEIGHT) {
                    if (!isVisibility) {
                        int[] location = new int[2];
                        // 获取scrollToView在窗体的坐标
                        scrollToView.getLocationInWindow(location);
                        // 计算root滚动高度，使scrollToView在可见区域
                        int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                        if (srollHeight >= 0) {
                            root.scrollTo(0, srollHeight+10);
                            isVisibility = true;
                        }
                    }
                } else {
                    // 键盘隐藏
                    root.scrollTo(0, 0);
                    isVisibility = false;
                }
            }
        });
    }

    /**
     * 方法描述：解决自定义键盘挡住按钮的问题 (只支持1个输入框)
     *<p>
     * @param  rootView 当前不觉得最顶层View  keyBoard：自定义键盘的KeyboardView  button：需要显示的View(比如button)
     * @return
     */
    public void controlKeyboardLayoutToCustom(final View rootView,final View keyBoard, final View button) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //1.判断是否需要调整
                int keyBoarrdTop;//键盘的顶部到屏幕顶部距离
                int buttonBottom;//按钮底部到屏幕顶部的距离

                if(isNeedContlor){//需要调整
                    keyBoarrdTop= rootView.getHeight()-keyBoard.getHeight();//键盘的y坐标
                    buttonBottom= button.getBottom();//按钮的y坐标
                    if(keyBoarrdTop <buttonBottom){  //会盖住按钮
                        isNeedContlor=true;
                    } else{
                        return;
                    }
                } else{
                    return;
                }

                //2.键盘弹出则调整
                if (keyBoard.isShown()) {      // 键盘弹出
                    if (!isVisibility) {
                        // 计算root滚动高度，使scrollToView在可见区域
                        int srollHeight = buttonBottom - keyBoarrdTop  ;
                        if (srollHeight >= 0) {
                            rootView.scrollTo(0, srollHeight+10);
                            isVisibility = true;
                        }
                    }
                } else {
                    rootView.scrollTo(0, 0);
                    isVisibility = false;
                }
            }
        });
    }
}
