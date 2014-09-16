package com.tiptimes.tp.common;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;

import com.tiptimes.tp.controller.Application;

/**
 * @author haoli
 */
public abstract class ImageLoadListenerAdapter implements OnLoadListener<BitmapDrawable>{
	@SuppressLint("HandlerLeak")
	private Handler mhandler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				int progress = msg.arg1;
				loding(progress);
				break;
			case 1:
				Message fm = (Message) msg.obj;
				fail(fm);
				break;
			case 2:
				Bitmap image = (Bitmap) msg.obj;
				loadded(image);
				break;

			default:
				break;
			}
		};
	};

	public abstract void loding(int progress);
	public abstract void fail(Message message);
	public abstract void loadded(Bitmap image);
	
	@Override
	public void loading(int prorgess) {
		// TODO Auto-generated method stub
		mhandler.sendMessage(mhandler.obtainMessage(0, prorgess, prorgess));
	}

	@Override
	public void loadFail(Message message) {
		// TODO Auto-generated method stub
		mhandler.sendMessage(mhandler.obtainMessage(1, message));
	}

	@Override
	public void loaded(BitmapDrawable image, String url) {
	    //  Application.getBitmapCache().put(url, image.getBitmap());
		mhandler.sendMessage(mhandler.obtainMessage(2, image.getBitmap()));
	}

}
