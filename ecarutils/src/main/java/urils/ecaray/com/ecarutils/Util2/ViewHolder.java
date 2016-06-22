package urils.ecaray.com.ecarutils.Util2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ===============================================
 * <p/>
 * 项目名称: parkBees-andriod
 * <p/>
 * 包: com.chmtech.parkbees.utils
 * <p/>
 * 类名称: ViewHolder
 * <p/>
 * 类描述:公共的ViewHolder
 * <p/>
 * 创建人: 金征
 * <p/>
 * 创建时间: 2015-5-9 上午11:39:49
 * <p/>
 * 修改人:
 * <p/>
 * 修改时间: 2015-5-9 上午11:39:49
 * <p/>
 * 修改备注:
 * <p/>
 * 版本:
 * <p/>
 * ===============================================
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    /**
     * @throws Exception
     * @功能：viewHolder的入口方法
     * @param：
     * @return：
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * @throws Exception
     * @功能：通过viewid获取控件
     * @param：
     * @return：
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * @throws Exception
     * @功能：设置textView的text
     * @param：viewId:textview的id，text:设置的文本
     * @return：
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * @throws Exception
     * @功能：设置textView的text-关键词高亮显示
     * @param：viewId:textview的id，text:设置的文本
     * @return：
     */
    public ViewHolder setTextQuery(int viewId, String text, String query,
                                   int color) {
        TextView tv = getView(viewId);
        StringUtils.setSearchKey(tv, text, query, color);
        return this;
    }

    /**
     * @throws Exception
     * @功能：设置textVeiw的颜色
     * @param：
     * @return：
     */
    public ViewHolder setTextColor(int viewId, String text, int color) {
        TextView tv = getView(viewId);
        tv.setText(text);
        tv.setTextColor(color);
        return this;
    }

    /**
     * @throws Exception
     * @功能：设置ImageView的图片
     * @param：viewId:imageview的id，resId:图片id
     * @return：
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * @throws Exception
     * @功能：设置ImageView的图片Bitmap
     * @param：viewId:imageview的id，bm:图片Bitmap
     * @return：
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bm);
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        ImageView iv = getView(viewId);
        iv.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * @throws Exception
     * @功能：设置ImageView的图片url
     * @param：viewId:imageview的id，bm:图片Bitmap
     * @return：
     */
    public ViewHolder setImageURL(int viewId, String url) {
        ImageView iv = getView(viewId);
        return this;
    }

    public ViewHolder setClick(int viewId, OnClickListener onClick) {
        Button btn = getView(viewId);
        btn.setOnClickListener(onClick);
        return this;
    }
}
