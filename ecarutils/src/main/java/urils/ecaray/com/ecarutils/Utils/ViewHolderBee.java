package urils.ecaray.com.ecarutils.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ===============================================
 * 
 * 项目名称: parkBees-andriod
 * 
 * 包: com.chmtech.parkbees.utils
 * 
 * 类名称: ViewHolder
 * 
 * 类描述:公共的ViewHolder
 * 
 * 创建人: 金征
 * 
 * 创建时间: 2015-5-9 上午11:39:49
 * 
 * 修改人:
 * 
 * 修改时间: 2015-5-9 上午11:39:49
 * 
 * 修改备注:
 * 
 * 版本:
 * 
 * ===============================================
 */
public class ViewHolderBee {
	private SparseArray<View> mViews;
	private int mPositon;
	private View mConvertView;

	private ViewHolderBee(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mPositon = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	/**
	 * @功能：viewHolder的入口方法
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public static ViewHolderBee get(Context context, View convertView,
									ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolderBee(context, parent, layoutId, position);
		} else {
			return (ViewHolderBee) convertView.getTag();
		}
	}

	/**
	 * @功能：通过viewid获取控件
	 * @param：
	 * @return：
	 * @throws Exception
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
	 * @功能：设置textView的text
	 * @param：viewId:textview的id，text:设置的文本
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setText(int viewId, String text) {
		TextView tv = getView(viewId);
		if(TextUtils.isEmpty(text)){
			text ="";
		}
		tv.setText(text);
		return this;
	}

	/**
	 * @功能：设置textView的text-关键词高亮显示
	 * @param：viewId:textview的id，text:设置的文本
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setTextQuery(int viewId, String text, String query,
									  int color) {
		TextView tv = getView(viewId);
		StringUtils.setSearchKey(tv, text, query, color);
		return this;
	}

	/**
	 * @功能：设置textVeiw的颜色
	 * @param：
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setTextColor(int viewId, String text, int color) {
		TextView tv = getView(viewId);
		tv.setText(text);
		tv.setTextColor(color);
		return this;
	}

	public ViewHolderBee setTextClick(int viewId , String text, OnClickListener onClickListener){
		TextView tv = getView(viewId);
		tv.setText(text);
		tv.setOnClickListener(onClickListener);
		return this;
	}
	
	public ViewHolderBee setLinClick(int viewId , OnClickListener onClickListener){
		LinearLayout ll = getView(viewId);
		ll.setOnClickListener(onClickListener);
		return this;
	}
	
	public ViewHolderBee setTextVisible(int viewId, boolean visible) {
		TextView tv = getView(viewId);
		tv.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		return this;
	}
	
	/**
	 * @功能：设置ImageView的图片
	 * @param：viewId:imageview的id，resId:图片id
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setImageResource(int viewId, int resId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(resId);
		return this;
	}

	/**
	 * @功能：设置ImageView的图片Bitmap
	 * @param：viewId:imageview的id，bm:图片Bitmap
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setImageBitmap(int viewId, Bitmap bm) {
		ImageView iv = getView(viewId);
		iv.setImageBitmap(bm);
		return this;
	}

	public ViewHolderBee setImageVisible(int viewId, boolean visible) {
		ImageView iv = getView(viewId);
		iv.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
		return this;
	}

	/**
	 * @功能：设置ImageView的图片url
	 * @param：viewId:imageview的id，bm:图片Bitmap
	 * @return：
	 * @throws Exception
	 */
	public ViewHolderBee setImageURL(int viewId, String url) {
		ImageView iv = getView(viewId);
		return this;
	}

	public ViewHolderBee setVisible(int viewId, boolean visble) {
		View iv = getView(viewId);
		iv.setVisibility(visble?View.VISIBLE:View.GONE);
		return this;
	}

}
