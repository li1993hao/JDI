package com.tiptimes.tp.common;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tiptimes.tp.controller.Controller;

/**
 * 图片下载信息封装类
 * @author haoli
 */
public class ImageLoadInfo  {
	private int controllerID; //控制器id
	private String url; //请求url
	private boolean hasProgress; //是否有进度条
	private OnLoadListener<Bitmap> onImageLoadListener; //回调信息监听者
	private Bitmap placeHoldImage; //占位图片
	private ImageView imageView; //要填充的imageView
    private int cacheLevel = 2;  //缓冲等级0 不缓冲，1只缓冲到内存中，2缓冲到硬盘里.
    private boolean searchInCache = true;  //false不去缓存搜索,直接下载.true去缓冲搜索
	
	public ImageView getImageView() {
		return imageView;
	}
	public String getUrl() {
		return url;
	}

    public boolean isSearchInCache() {
        return searchInCache;
    }

    public boolean isHasProgress() {
        return hasProgress;
    }

    public int getCacheLevel() {
        return cacheLevel;
    }
    public boolean hasProgress() {
        return hasProgress;
    }
    public OnLoadListener<Bitmap> getOnImageLoadListener() {
        return onImageLoadListener;
    }
    public Bitmap getPlaceHoldImage() {
        return placeHoldImage;
    }
    public int getControllerID() {
        return controllerID;
    }

    public class SimpleImageLoadListener  extends ImageLoadListenerAdapter{

		@Override
		public void loding(int progress) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void fail(Message message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void loadded(Bitmap bitmap) {
			// TODO Auto-generated method stub
			imageView.setImageBitmap(bitmap);
		}
	}
	
	private ImageLoadInfo(){
		
	}


	public ImageLoadInfo(Controller controller,ImageView imageView, String url){
		this.controllerID = controller.hashCode();
		this.imageView = imageView;
		this.hasProgress = false;
		this.onImageLoadListener = new SimpleImageLoadListener();
		this.placeHoldImage = null;
		this.url = url;
	}

    public ImageLoadInfo(Controller controller,ImageView imageView, String url,Bitmap placeHoldImage){
        this.controllerID = controller.hashCode();
        this.imageView = imageView;
        this.hasProgress = false;
        this.onImageLoadListener = new SimpleImageLoadListener();
        this.placeHoldImage = placeHoldImage;
        this.url = url;
        imageView.setImageBitmap(placeHoldImage);
    }

	public static class Builder {
		private String url;
		private boolean hasProgress;
		private OnLoadListener<Bitmap> onImageLoadListener;
		private Bitmap placeHoldImage;
		private ImageView imageView;
		private int controllerID;
        private int cacheLevel = 2;  //缓冲等级0 不缓冲，1只缓冲到内存中，2缓冲到硬盘里.
        private boolean searchInCache = true;  //false不去缓存搜索,直接下载.true去缓冲搜索

        public Builder setControllerID(int controllerID) {
            this.controllerID = controllerID;
            return this;
        }

        public Builder setCacheLevel(int cacheLevel) {
            this.cacheLevel = cacheLevel;
            return this;
        }

        public void setSearchInCache(boolean searchInCache) {
            this.searchInCache = searchInCache;
        }

        public Builder setController(Controller controller){
			this.controllerID = controller.hashCode();
			return this;
		}
		
		public Builder setImageView(ImageView imageView) {
			this.imageView = imageView;
			return this;
		}
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		public Builder setHasProgress(boolean hasProgress) {
			this.hasProgress = hasProgress;
			return this;
		}
		public Builder setOnImageLoadListener( OnLoadListener<Bitmap>  onImageLoadListener) {
			this.onImageLoadListener =  onImageLoadListener ;
			return this;
		}
		public Builder setPlaceHoldImage(Bitmap placeHoldImage) {
			this.placeHoldImage = placeHoldImage;
			return this;
		}
		
		public ImageLoadInfo Build(){
			ImageLoadInfo info = new ImageLoadInfo();
			info.hasProgress = hasProgress;
			info.onImageLoadListener = onImageLoadListener;
			info.placeHoldImage = placeHoldImage;
			info.url = url;
			info.imageView = imageView;
			info.controllerID = controllerID;
            info.cacheLevel = cacheLevel;
            info.searchInCache = searchInCache;

            if(info.placeHoldImage != null && imageView != null){
                imageView.setImageBitmap(placeHoldImage);
            }
			return info;
		}
		  
	}


}
