package com.tiptimes.tp.common;

import android.graphics.Bitmap;
import android.os.Environment;

import com.tiptimes.tp.constant.Constants;
import haihemoive.Application;
import com.tiptimes.tp.util.ACache;
import com.tiptimes.tp.util.L;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haoli on 14-9-29.
 * 缓存管理类
 */
public class CacheManager {
    private static ACache imageAcache; //图片缓冲管理
    private static ACache fileAcache; //文件缓冲管理

    private static Map<String, SoftReference<Object>> iChache; //数据一级缓存
    private static Map<String, SoftReference<Bitmap>> imgCache;//图片一级缓存

    static CacheManager mCache = new CacheManager();

    private static File tempDic;

    static private Object iCacheLock = new Object();
    static private Object imgCacheLock = new Object();
    static private Object fileAcacheLock = new Object();

    private CacheManager() {
        iChache = new HashMap<String, SoftReference<Object>>(); //初始化数据一级缓冲
        imgCache = new HashMap<String, SoftReference<Bitmap>>();//初始化图片一级缓冲
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            //存在外部存储介质
            imageAcache = ACache.get(new File(Environment.getExternalStorageDirectory()
                    + Constants.IMAGECACHE_DIR));
            fileAcache = ACache.get(new File(Environment.getExternalStorageDirectory()
                    + Constants.FILE_DIR));
            tempDic = new File(Environment.getExternalStorageDirectory() + Constants.TEMP_DIR);
            if (!tempDic.exists()) {
                tempDic.mkdirs();
            }

        } else {
            //不存在外部存在介质
            imageAcache = ACache.get(new File(Application.getApplication().getFilesDir()
                    + Constants.IMAGECACHE_DIR));
            fileAcache = ACache.get(new File(Application.getApplication().getFilesDir()
                    + Constants.FILE_DIR));
            tempDic = new File(Application.getApplication().getFilesDir() + Constants.TEMP_DIR);
            if (!tempDic.exists()) {
                tempDic.mkdirs();
            }
        }
    }


    static public File getTempDic() {
        return tempDic;
    }


    /**
     * @param name
     * @param bitmap
     * @param saveTime 小时
     */
    static public void putImage(String name, Bitmap bitmap, int saveTime) {
        synchronized (imgCacheLock) {
            imgCache.put(name, new SoftReference<Bitmap>(bitmap));
            if (saveTime <= 0) {
                imageAcache.put(name, bitmap);
            } else {
                imageAcache.put(name, bitmap, saveTime * 60 * 60);
            }
        }
    }

    static public void putImageInMermory(String name, Bitmap bitmap) {
        synchronized (imgCacheLock) {
            imgCache.put(name, new SoftReference<Bitmap>(bitmap));
        }
    }

    static public Bitmap getImageInMermory(String name) {
        synchronized (imgCacheLock) {
            SoftReference<Bitmap> sb = imgCache.get(name);
            if (sb != null) {
                L.d(L.TAG, name + ":hit in memeroy");
                if (sb.get() != null) {
                    return sb.get();
                } else {
                    imgCache.remove(sb);
                    return null;
                }
            }
            return null;
        }
    }

    static public Bitmap getImage(String name) {
        synchronized (imgCacheLock) {
            SoftReference<Bitmap> sb = imgCache.get(name);
            if (sb != null) {
                L.d(L.TAG, name + ":hit in memeroy");
                if (sb.get() != null) {
                    return sb.get();
                } else {
                    imgCache.remove(sb);
                    return imageAcache.getAsBitmap(name);
                }
            }
            return imageAcache.getAsBitmap(name);
        }
    }

    static public File getFile(String name) {
        synchronized (fileAcacheLock) {
            return fileAcache.file(name);
        }
    }

    static public boolean putFile(String name, File file, int saveTime) {
        RandomAccessFile RAFile = null;
        boolean isNormal = true;
        try {
            RAFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) RAFile.length()];
            RAFile.read(byteArray);
            isNormal = true;

            synchronized (fileAcacheLock) {
                if (saveTime <= 0) {
                    fileAcache.put(name, byteArray);
                } else {
                    fileAcache.put(name, byteArray, saveTime * 60);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            isNormal = false;
        } finally {
            if (RAFile != null) {
                try {
                    RAFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    isNormal = false;
                }
            }

            return isNormal;
        }
    }


    static public Object getData(String name) {
        synchronized (iCacheLock) {
            SoftReference<Object> o = iChache.get(name);
            if (o != null) {
                return o.get();
            } else {
                return null;
            }
        }

    }

    static public void putData(String name, Object data) {
        synchronized (iCacheLock) {
            iChache.put(name, new SoftReference<Object>(data));
        }
    }

    /**
     * 获取图片二级缓存的大小
     * 内存大小mb
     *
     * @return
     */
    static public long getImageACacheSize() {
        synchronized (imgCacheLock) {
            return imageAcache.getCacheSize() / (1000 * 1000);
        }
    }

    /**
     * 获取文件二级缓存的大小
     *
     * @return
     */
    static public long getFileCacheSize() {
        synchronized (fileAcache) {
            return fileAcache.getCacheSize() / (1000 * 1000);
        }
    }


    /**
     * 清空图片的二级缓存
     */
    static public void clearImageACache() {
        synchronized (imgCacheLock) {
            imageAcache.clear();
        }
    }

    /**
     * 清空文件的二级缓存
     */
    static public void clearFileCache() {
        synchronized (fileAcache) {
            fileAcache.clear();
        }
    }

    /**
     * 清空图片的一级缓存
     */
    static public void clearImgCache() {
        synchronized (imgCacheLock) {
            imgCache.clear();
        }
    }

    /**
     * 清空数据的一级缓存
     */
    static public void clearDataCache() {
        synchronized (iCacheLock) {
            iChache.clear();
        }
    }
}
