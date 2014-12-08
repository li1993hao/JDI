package com.tiptimes.tp.common;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * @author haoli
 */
public abstract class ImageLoadListenerAdapter implements OnLoadListener<Bitmap>{
	@SuppressLint("HandlerLeak")
	private Handler mhandler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
                float total = msg.getData().getFloat("total");
                float prorgress =  msg.getData().getFloat("prorgress");
                loading_(total,prorgress);
				break;
			case 1:
				Message fm = (Message) msg.obj;
                loadFail_(fm);
				break;
			case 2:
				Bitmap image = (Bitmap) msg.obj;
                loaded_(image);
				break;

			default:
				break;
			}
		};
	};

	public abstract void loading_(float total,float prorgress);
	public abstract void loadFail_(Message message);
	public abstract void loaded_(Bitmap image);
	
	@Override
	public void loading(float total,float prorgress) {
		// TODO Auto-generated method stub
        android.os.Message message = new android.os.Message();
        message.what = 0;
        Bundle bundle = new Bundle();
        bundle.putFloat("total", total);
        bundle.putFloat("prorgress", prorgress);
        message.setData(bundle);
		mhandler.sendMessage(message);
	}

	@Override
	public void loadFail(Message message) {
		// TODO Auto-generated method stub
		mhandler.sendMessage(mhandler.obtainMessage(1, message));
	}

	@Override
	public void loaded(Bitmap image, String url) {
	    //  Application.getBitmapCache().put(url, image.getBitmap());
		mhandler.sendMessage(mhandler.obtainMessage(2, image));
	}

}
