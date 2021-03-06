package com.tiptimes.tp.common;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.AnimationSet;

import com.tiptimes.tp.constant.Constants;
import com.tiptimes.tp.util.HttpUtil;
import com.tiptimes.tp.util.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import haihemoive.Application;

/**
 * 图片下载线程
 */
public class ImageLoadService implements Runnable,ControllerObserver {
	private ImageLoadInfo imageLoadInfo;
	private volatile int controllerStatus = Constants.CONTROLLER_STATUS_HOLDER;

	public ImageLoadService(ImageLoadInfo imageLoadInfo){
		this.imageLoadInfo = imageLoadInfo;
	}
	
	@Override
	public void run() {
        Bitmap bitmap = null;
        if(imageLoadInfo.isSearchInCache()){
            bitmap = CacheManager.getImage(imageLoadInfo.getUrl());
        }

		if(bitmap !=null){
            //缓存命中
			imageLoadInfo.getOnImageLoadListener().loaded(bitmap, imageLoadInfo.getUrl());
		}else{
            L.d(L.TAG, "no hit: start download form"+imageLoadInfo.getUrl());
            try {
                if(!imageLoadInfo.hasProgress()){
                    justDownLoad();
                }else{
                    downLoadWithProgress();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if(controllerStatus == Constants.CONTROLLER_STATUS_HOLDER){
                    imageLoadInfo.getOnImageLoadListener().loadFail(Message.obtainMessage(e.getMessage()));
                }else{
                    L.d(L.TAG, imageLoadInfo.getUrl() + "宿主控制器destroy!");
                }

            }
        }
		ThreadPoolManager.removeTask(this);
	}
	
	private void justDownLoad() throws Exception{
        URL u = new URL(imageLoadInfo.getUrl());
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        InputStream is = conn.getInputStream();
        BitmapDrawable bitmap =new BitmapDrawable(Application.getApplication().getResources(), is);

        if(imageLoadInfo.getCacheLevel()==1){
            CacheManager.putImageInMermory(imageLoadInfo.getUrl(), bitmap.getBitmap());
        }else if(imageLoadInfo.getCacheLevel()==2){
            CacheManager.putImage(imageLoadInfo.getUrl(), bitmap.getBitmap(),-1);
        }
        imageLoadInfo.getOnImageLoadListener().loaded(bitmap.getBitmap(), imageLoadInfo.getUrl());
	}

    /**
     * 有下载进度的图片下载
     * @throws Exception
     */
	private void downLoadWithProgress() throws Exception{
        URL url = new URL(imageLoadInfo.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(HttpUtil.REQUEST_TIMEOUT);
        conn.connect();
        float lenghtOfFile = conn.getContentLength();
        File tempFile = new File(CacheManager.getTempDic(),new Date().getTime()+"");
        tempFile.createNewFile();
        FileOutputStream f = new FileOutputStream(tempFile);


        InputStream in = conn.getInputStream();
        byte[] buffer = new byte[1024];
        int len1 = 0;
        long total = 0;

        while ((len1 = in.read(buffer)) > 0) {
            total += len1;
            if(controllerStatus == Constants.CONTROLLER_STATUS_HOLDER){
                imageLoadInfo.getOnImageLoadListener().loading(lenghtOfFile,total);
            }else{
    			L.d(L.TAG, imageLoadInfo.getUrl()+"宿主控制器destroy!");
                tempFile.delete();
                f.close();
                in.close();
                return;
            }
            f.write(buffer, 0, len1);
        }

        BitmapDrawable bitmap = new BitmapDrawable(Application.getApplication().getResources() ,tempFile.getAbsolutePath());
        CacheManager.putImage(imageLoadInfo.getUrl(),bitmap.getBitmap(),-1);//放入缓存中
        imageLoadInfo.getOnImageLoadListener().loaded(bitmap.getBitmap(),imageLoadInfo.getUrl());
        tempFile.delete();//删除临时文件
        f.close();
        in.close();
	}

	@Override
	public void controllerDestroy() {
		// TODO Auto-generated method stub
		controllerStatus = Constants.CONTROLLER_STATUS_DESTROY;
	}

	@Override
	public int controllerID() {
		// TODO Auto-generated method stub
		return imageLoadInfo.getControllerID();
	}
	
	
}
