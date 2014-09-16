package com.tiptimes.tp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiptimes.R;



public class EmptyTipView extends RelativeLayout {
	private Context context;
	private ImageView tipImg;
	private TextView tipTxt;
	private ProgressBar tipPB;

	public EmptyTipView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public EmptyTipView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}


	public EmptyTipView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.library_tipview, this, true);
		tipImg = (ImageView) findViewById(R.id.news_default_tip_img);
		tipPB = (ProgressBar) findViewById(R.id.news_default_tip_pb);
		tipTxt = (TextView) findViewById(R.id.news_default_tip_txt);
	}

	
	public void hide(){
		show(false);
		this.setVisibility(View.GONE);
	}

	public void show(boolean flag) {
		this.setVisibility(View.VISIBLE);
		if (flag) {
			tipImg.setVisibility(View.GONE);
			tipPB.setVisibility(View.VISIBLE);
			tipTxt.setText(context.getString(R.string.tips_load));
		} else {
			tipImg.setVisibility(View.VISIBLE);
			tipPB.setVisibility(View.GONE);
			tipTxt.setText(context.getString(R.string.tips_refresh));
		}
	}

}