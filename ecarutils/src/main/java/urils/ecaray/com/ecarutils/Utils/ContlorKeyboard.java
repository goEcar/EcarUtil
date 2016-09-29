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

    /**
     * @param root 最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public boolean isVisibility; //是否上移
    private int AREA_HEIGHT = 100;//遮盖的高度


    public ContlorKeyboard setAREA_HEIGHT(int AREA_HEIGHT) {
        this.AREA_HEIGHT = AREA_HEIGHT;
        return this;
    }

    public void controlKeyboardLayout(final View root, final View scrollToView) {
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
                            root.scrollTo(0, srollHeight);
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
}
