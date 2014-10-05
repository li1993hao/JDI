package com.tiptimes.tp.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tiptimes.tp.common.ImageLoadInfo;
import com.tiptimes.tp.common.ImageLoadListenerAdapter;
import com.tiptimes.tp.common.ThreadPoolManager;
import com.tiptimes.tp.controller.Controller;

/**
 * Created by haoli on 14-10-3.
 */
public class AsyImageView extends ImageView {

    public AsyImageView(Context context) {
        super(context);
    }

    public AsyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AsyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void loadImage(Controller controller, String url){
        ImageLoadInfo imageLoadInfo = new ImageLoadInfo(controller, this, url);
        ThreadPoolManager.getInstance().execImageDownload(imageLoadInfo);
    }

    public void loadImage(Controller controller, String url, int palceHolder){
        ImageLoadInfo imageLoadInfo = new ImageLoadInfo(controller, this, url,BitmapFactory.decodeResource(getResources(), palceHolder));
        ThreadPoolManager.getInstance().execImageDownload(imageLoadInfo);
    }

    public  void loadImage(Controller controller, String url, int palceHolder, ImageLoadListenerAdapter loadListener){
        ThreadPoolManager.getInstance().execImageDownload(new ImageLoadInfo.Builder().setController(controller).setUrl(url)
        .setImageView(this).setPlaceHoldImage(BitmapFactory.decodeResource(getResources(), palceHolder)).setHasProgress(true).setOnImageLoadListener(loadListener).Build());
    }

    public static void loadImage(ImageLoadInfo imageLoadInfo){
        ThreadPoolManager.getInstance().execImageDownload(imageLoadInfo);
    }
}
