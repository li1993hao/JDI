package com.tiptimes.tp.common;

import android.graphics.Bitmap;
import android.os.Environment;

import com.tiptimes.tp.constant.Constants;
import com.tiptimes.tp.controller.Application;
import com.tiptimes.tp.util.ACache;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haoli on 14-9-29.
 * 缓存管理类
 *
 *
 *
 *
 */
public class CacheManager {
    private static ACache imageAcache; //图片缓冲管理
    private static ACache fileAcache; //文件缓冲管理

    private static Map<String, SoftReference<Object>> iChache; //数据一级缓存
    private static Map<String, SoftReference<Bitmap>> imgCache;//图片一级缓存

    static CacheManager mCache;

    private static File tempDic;

    static private Object iCacheLock;
    static private Object imgCacheLock;
    static private Object fileAcacheLock;


    private CacheManager() {
        iChache = new HashMap<String, SoftReference<Object>>(); //初始化一级缓冲
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            //存在外部存储介质
            imageAcache = ACache.get(new File(Environment.getExternalStorageDirectory()
                    + Constants.IMAGECACHE_DIR));
            fileAcache = ACache.get(new File(Environment.getExternalStorageDirectory()
                    + Constants.FILE_DIR));
            tempDic = new File(Environment.getExternalStorageDirectory()+Constants.TEMP_DIR);
            if(!tempDic.exists()){
                tempDic.mkdirs();
            }

        } else {
            //不存在外部存在介质
            imageAcache = ACache.get(new File(Application.getApplication().getFilesDir()
                    + Constants.IMAGECACHE_DIR));
            fileAcache = ACache.get(new File(Application.getApplication().getFilesDir()
                    + Constants.FILE_DIR));
            tempDic = new File(Application.getApplication().getFilesDir()+Constants.TEMP_DIR);
            if(!tempDic.exists()){
                tempDic.mkdirs();
            }
        }
    }

    synchronized static public CacheManager getInstance() {
        if (mCache == null) {
            mCache = new CacheManager();
        }
        return mCache;
    }


    public static File getTempDic() {
        return tempDic;
    }


    /**
     * @param name
     * @param bitmap
     * @param saveTime 小时
     */
    static void putImage(String name, Bitmap bitmap, int saveTime) {
        synchronized (imgCacheLock) {
            imgCache.put(name, new SoftReference<Bitmap>(bitmap));
            imageAcache.put(name, bitmap, saveTime * 60 * 60);
        }
    }

    static Bitmap getImage(String name) {
        synchronized (imgCacheLock) {
            SoftReference<Bitmap> sb = imgCache.get(name);
            if (sb != null) {
                return sb.get();
            }
            return imageAcache.getAsBitmap(name);
        }
    }

    static File getFile(String name) {
        synchronized (fileAcacheLock) {
            return fileAcache.file(name);
        }
    }

    static boolean putFile(String name, File file, int saveTime) {
        RandomAccessFile RAFile = null;
        boolean isNormal = true;
        try {
            RAFile = new RandomAccessFile(file, "r");
            byte[] byteArray = new byte[(int) RAFile.length()];
            RAFile.read(byteArray);
            isNormal = true;

            synchronized (fileAcacheLock) {
                fileAcache.put(name, byteArray, saveTime * 60);
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

    static Object getData(String name) {
        synchronized (iCacheLock) {
            SoftReference<Object> o = iChache.get(name);
            if (o != null) {
                return o.get();
            } else {
                return null;
            }
        }

    }

    static void putData(String name, Object data) {
        synchronized (iCacheLock) {
            iChache.put(name, new SoftReference<Object>(data));
        }
    }
}
