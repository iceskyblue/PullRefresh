package com.example.adminstrator.pullrefresh;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class RefreshProgressBar extends RelativeLayout {

	private ImageView animationImage;
	private TextView tipText;
	
	private RotateAnimation rotate;
	private String pullTip;
	private String refreshingTip;
	private String triggerTip;
	
	private AnimationListener rotateListener = new AnimationListener(){

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			RefreshProgressBar.this.setLayerType(LAYER_TYPE_NONE, null);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public RefreshProgressBar(Context context, int position) {
		super(context);
		// TODO Auto-generated constructor stub
		
		
		init(context, position);
		
	}

	public RefreshProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RefreshProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	private void startAnimation(View view){
		this.setLayerType(LAYER_TYPE_HARDWARE, null);
		if(null == rotate){
			rotate = new RotateAnimation(0, 360, view.getWidth()/2, view.getHeight()/2);
			rotate.setDuration(1000);
			rotate.setInterpolator(new LinearInterpolator());
			rotate.setAnimationListener(rotateListener);
			rotate.setRepeatCount(Animation.INFINITE);
		}
		
		view.setAnimation(rotate);
		rotate.startNow();
	}
	
	private void stopAnimation(){
		if(null != rotate){
			rotate.cancel();
			rotate = null;
		}
	}
	
	private void init(Context context, int position){
		
		this.animationImage = new ImageView(context);
		this.tipText = new TextView(context);
		this.tipText.setGravity(Gravity.CENTER);
		animationImage.setImageResource(R.drawable.default_ptr_rotate);
		
		LayoutParams rlpImg = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlpImg.leftMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

		LayoutParams rlpTip = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		if(position == 0){
			//header
			rlpImg.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			rlpTip.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			tipText.setText("start refresh");
			this.setPadding(0, 0, 0, 10);
		}else if(position == 1){
			//footer
			rlpImg.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			rlpTip.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			tipText.setText("start loading more");
			this.setPadding(0, 10, 0, 0);
		}else{
			throw new IllegalStateException("position params error:"+position);
		}

		tipText.setTextSize(16);
		tipText.setTextColor(Color.BLACK);
		tipText.setLayoutParams(rlpTip);
		animationImage.setLayoutParams(rlpImg);
		this.addView(tipText);
		this.addView(animationImage);
		
	}
	
	public void setTipText(String pullTip, String triggerTip, String refreshingTip){
		this.pullTip = pullTip;
		this.triggerTip = triggerTip;
		this.refreshingTip = refreshingTip;
	}
	public void setAnimationImage(String path){
		Bitmap bmp = BitmapFactory.decodeFile(path);
		if(null == bmp){
			return;
		}
		this.animationImage.setImageBitmap(bmp);
	}

	public void setLoadProperty(int textColor, int textSize, int bgColor){
		this.setBackgroundColor(bgColor);
		if(textSize > 0){
			this.tipText.setTextSize(textSize);
		}
	}

	public void setState(int mode, int state){
		if(state == 0){
				tipText.setText(pullTip);
				stopAnimation();
			}else if(state == 1){
				tipText.setText(triggerTip);

			}else if(state == 2){
				tipText.setText(refreshingTip);

				startAnimation(animationImage);
			}else if(state == 3){
				stopAnimation();
			}
//		if(mode == 0){
//			//下拉
//			if(state == 0){
//				tipText.setText("下拉刷新");
//				stopAnimation();
//			}else if(state == 1){
//				tipText.setText("松手刷新");
//
//			}else if(state == 2){
//				tipText.setText("正在刷新");
//
//				startAnimation(animationImage);
//			}else if(state == 3){
//				stopAnimation();
//			}
//		}else if(mode == 1){
//			 //上拉
//			if(state == 0){
//				tipText.setText("上拉加载");
//				stopAnimation();
//			}else if(state == 1){
//				tipText.setText("松手加载");
//			}else if(state == 2){
//				tipText.setText("正在加载");
//				startAnimation(animationImage);
//			}else if(state == 3){
//				stopAnimation();
//			}
//		}
	}
}
