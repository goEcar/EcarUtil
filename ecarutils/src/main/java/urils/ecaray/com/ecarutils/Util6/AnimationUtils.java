package urils.ecaray.com.ecarutils.Util6;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {
	public static final int rela1 = Animation.RELATIVE_TO_SELF;
	public static final int rela2 = Animation.RELATIVE_TO_PARENT;

	public static final int Default = -1;
	public static final int Linear = 0;
	public static final int Accelerate = 1;
	public static final int Decelerate = 2;
	public static final int AccelerateDecelerate = 3;
	public static final int Bounce = 4;
	public static final int Overshoot = 5;
	public static final int Anticipate = 6;
	public static final int AnticipateOvershoot = 7;

	
	/**
	 * 表示下一个界面要进来啦
	 */
	public static final int ANIMATION_IN=1;
	/**
	 * 表示当前显示的界面要出去啦
	 */
	public static final int ANIMATION_OUT=2;
	/**
	 * 动画时长
	 */
	public static final int ANIMATION_DURATION=120;//动画时长
	/**
	 * 为一个View启动动画必须指定这个参数，如果是这个类型说明是正常（正方向）进入下一个界面或者正常（正方向）退出当前界面
	 */
	public static final int ANIMATION_DERECTION_POSITIVE=1;//切换类型：进入
	/**
	 * 为一个View启动动画必须指定这个参数，如果是这个类型说明是以相反的方向进入下一个界面或者以相反的方向退出当前界面
	 */
	public static final int ANIMATION_DERECTION_NAGITIVE=-1;//切换类型：返回
	
	
	public static abstract class AnimationEndListener implements AnimationListener{

		@Override
		public void onAnimationStart(Animation animation) {}
		@Override
		public abstract void onAnimationEnd(Animation animation);
	
		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
	}
	private static class MyAnimationListener implements AnimationListener {
		private View view;

		public MyAnimationListener(View view) {
			this.view = view;
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			this.view.setVisibility(View.GONE);
			/*ViewGroup parent=(ViewGroup)this.view.getParent();
			parent.removeView(view);*/
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

	}

	private static void setEffect(Animation animation, int interpolatorType, long durationMillis, long delayMillis) {
		switch (interpolatorType) {
			case 0:
				animation.setInterpolator(new LinearInterpolator());
				break;
			case 1:
				animation.setInterpolator(new AccelerateInterpolator());
				break;
			case 2:
				animation.setInterpolator(new DecelerateInterpolator());
				break;
			case 3:
				animation.setInterpolator(new AccelerateDecelerateInterpolator());
				break;
			case 4:
				animation.setInterpolator(new BounceInterpolator());
				break;
			case 5:
				animation.setInterpolator(new OvershootInterpolator());
				break;
			case 6:
				animation.setInterpolator(new AnticipateInterpolator());
				break;
			case 7:
				animation.setInterpolator(new AnticipateOvershootInterpolator());
				break;
			default:
				break;
		}
		animation.setDuration(durationMillis);
		animation.setStartOffset(delayMillis);
	}

	private static void baseIn(View view, Animation animation, long durationMillis, long delayMillis) {
		setEffect(animation, Default, durationMillis, delayMillis);
		view.setVisibility(View.VISIBLE);
		view.startAnimation(animation);
	}

	private static void baseOut(View view, Animation animation, long durationMillis, long delayMillis) {
		setEffect(animation, Default, durationMillis, delayMillis);
		animation.setAnimationListener(new MyAnimationListener(view));
		view.startAnimation(animation);
	}

	public void show(View view) {
		view.setVisibility(View.VISIBLE);
	}

	public void hide(View view) {
		view.setVisibility(View.GONE);
	}

	public void transparent(View view) {
		view.setVisibility(View.INVISIBLE);
	}

	public static Animation fadeIn(View view, long durationMillis, long delayMillis) {
		AlphaAnimation animation = new AlphaAnimation(0, 1);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation fadeOut(View view, long durationMillis, long delayMillis) {
		AlphaAnimation animation = new AlphaAnimation(1, 0);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation slideIn(View view, long durationMillis, long delayMillis,int derection) {
		TranslateAnimation animation = null;
		if(derection==ANIMATION_DERECTION_POSITIVE)
			animation = new TranslateAnimation(rela2, 1, rela2, 0, rela2, 0, rela2, 0);
		else if(derection==ANIMATION_DERECTION_NAGITIVE)
			animation = new TranslateAnimation(rela2, -1, rela2, 0, rela2, 0, rela2, 0);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation slideOut(View view, long durationMillis, long delayMillis,int derection) {
		TranslateAnimation animation = null;
		if(derection==ANIMATION_DERECTION_POSITIVE)
			animation = new TranslateAnimation(rela2, 0, rela2, -1, rela2, 0, rela2, 0);
		else if(derection==ANIMATION_DERECTION_NAGITIVE)
			animation = new TranslateAnimation(rela2, 0, rela2, 1, rela2, 0, rela2, 0);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation scaleIn(View view, long durationMillis, long delayMillis) {
		ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1, rela2, 0.5f, rela2, 0.5f);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation scaleOut(View view, long durationMillis, long delayMillis) {
		ScaleAnimation animation = new ScaleAnimation(1, 0, 1, 0, rela2, 0.5f, rela2, 0.5f);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation rotateIn(View view, long durationMillis, long delayMillis) {
		RotateAnimation animation = new RotateAnimation(-90, 0, rela1, 0, rela1, 1);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static Animation rotateOut(View view, long durationMillis, long delayMillis) {
		RotateAnimation animation = new RotateAnimation(0, 90, rela1, 0, rela1, 1);
		animation.setDuration(durationMillis);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static AnimationSet scaleRotateIn(View view, long durationMillis, long delayMillis) {
		ScaleAnimation animation1 = new ScaleAnimation(0, 1, 0, 1, rela1, 0.5f, rela1, 0.5f);
		RotateAnimation animation2 = new RotateAnimation(0, 360, rela1, 0.5f, rela1, 0.5f);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animation1);
		animation.addAnimation(animation2);
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static AnimationSet scaleRotateOut(View view, long durationMillis, long delayMillis) {
		ScaleAnimation animation1 = new ScaleAnimation(1, 0, 1, 0, rela1, 0.5f, rela1, 0.5f);
		RotateAnimation animation2 = new RotateAnimation(0, 360, rela1, 0.5f, rela1, 0.5f);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animation1);
		animation.addAnimation(animation2);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static AnimationSet slideFadeIn(View view, long durationMillis, long delayMillis) {
		TranslateAnimation animation1 = new TranslateAnimation(rela2, 1, rela2, 0, rela2, 0, rela2, 0);
		AlphaAnimation animation2 = new AlphaAnimation(0, 1);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animation1);
		animation.addAnimation(animation2);
		if(view==null)return animation;
		baseIn(view, animation, durationMillis, delayMillis);
		return null;
	}

	public static AnimationSet slideFadeOut(View view, long durationMillis, long delayMillis) {
		TranslateAnimation animation1 = new TranslateAnimation(rela2, 0, rela2, -1, rela2, 0, rela2, 0);
		AlphaAnimation animation2 = new AlphaAnimation(1, 0);
		AnimationSet animation = new AnimationSet(false);
		animation.addAnimation(animation1);
		animation.addAnimation(animation2);
		if(view==null)return animation;
		baseOut(view, animation, durationMillis, delayMillis);
		return null;
	}
}
