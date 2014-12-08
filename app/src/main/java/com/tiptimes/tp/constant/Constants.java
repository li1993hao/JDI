package com.tiptimes.tp.constant;

public class Constants {
	 public static String WAITTITLE = "正在加载..";
	 public static String ERROR_DATA ="数据解析错误！";
	 public static String ERROR_UNKNOW = "未知错误!";
	 public static String MSG_ONTASK = "请求正在执行，稍等一会吧！";




    /**
     * 控制器状态
     */
	 public static int CONTROLLER_STATUS_HOLDER = 1; //宿主控制器生命周前还没结束
	public static int CONTROLLER_STATUS_DESTROY = 0; //宿主控制器结束生命周前

    /**
     * 缓存和临时文件夹路径
     */
	 public final static String IMAGECACHE_DIR = "/tiptimes/img"; //图片缓冲路径
	 public final static String PRE_DIR = "/tiptimes/preference"; //配置文件或者轻量级数据缓冲路径
     public final static String FILE_DIR = "/tiptimes/file"; //文件存储路径
    public  final static String TEMP_DIR = "/tiptimes/temp";
}
