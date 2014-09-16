package com.tiptimes.tp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 
 * @author tiptimes LH
  *
 */
public class FileUtil {
	  /**
     * 读取文本，一次读取多个字节，默认为1024
     * 
     * @param file
     *            文件名称(在sd卡下面的data/data/应用程序包下面)
     * @param context
     *            上下文
     * @param encode
     *            编码方式
     * @return
     * @throws java.io.IOException
     */
    public static String readFromFileByChar(String file, Context context,
            String encode) throws IOException {
        FileInputStream fis = context.openFileInput(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,
                encode));
        // Log.i(TAG, br.readLine());
        int index = 0;
        char[] buffer = new char[1024];// 一次性读取1024个字符
        StringBuffer sb = new StringBuffer();
        while ((index = br.read(buffer)) != -1) {// 一次读多个字符
            // 同样屏蔽掉r不显示
            if ((index == buffer.length) && (buffer[buffer.length - 1] != 'r')) {
                sb.append(buffer);
            } else {
                for (int i = 0; i < index; i++) {
                    if (buffer[i] == 'r') {
                        continue;// 停止执行当前的迭代,然后退回循环开始处
                    } else {
                        sb.append(buffer[i]);
                    }
                }
            }
        }
        br.close();
        fis.close();
        return sb.toString();
        // return br.readLine();
    }

    /**
     * 按行读取文本
     * 
     * @param file
     *            文件名
     * @param context
     *            上下文
     * @param encode
     *            编码方式
     * @return
     * @throws java.io.IOException
     */
    public static String readFromFileByLine(String file, Context context,
            String encode) throws IOException {
        FileInputStream fis = context.openFileInput(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis,
                encode));
        // Log.i(TAG, br.readLine());
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) {// 一次读一行
            sb.append(temp);
        }
        br.close();
        fis.close();
        return sb.toString();
        // return br.readLine();
    }

    /**
     * 一次将string内容写入到文件
     * 
     * @param context
     *            上下文
     * @param file
     *            文件名
     * @param content
     *            写入的内容
     * @throws java.io.IOException
     */
    public static void writeToFile(Context context, String file, String content)
            throws IOException {
        FileOutputStream fos = context.openFileOutput(file,
        		Context.MODE_PRIVATE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(content);
        bw.flush();
        bw.close();
        fos.close();
    }

    /**
     * 将object序列化到filename文件中
     * 
     * @param fileName
     *            文件名,包括路径
     * @param object
     *            序列化的对象
     */
    public static boolean writeFileByObject(String fileName, Object object) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 反序列化
     * 
     * @param fileName
     *            文件名,包括详细路径
     * @return Object类型的对象
     */
    public static Object readFileByObject(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            ois.close();
            fis.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }

  
   

 

    /**
     * 向preferences写入数据
     * 
     * @param context
     *            上下文
     * @param key
     *            键
     * @param value
     *            写入的内容
     */
    public static void writeToPreferences(Context context, String filename,
            String key, String value) {
        // 得到preferences对象
        SharedPreferences.Editor editor = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE).edit();
        // editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 向preference中读取数据 data/data/package/shared_prefs
     * 
     * @param context
     *            上下文
     * @param filename
     *            文件名
     * @param key
     *            键
     * @param defaultValue
     *            默认值
     * @return
     */
    public static String readFromPreferences(Context context, String filename,
            String key, String defaultValue) {
        // 得到preferences对象
        SharedPreferences preferences = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE
                        | Context.MODE_APPEND);
        return preferences.getString(key, defaultValue);
    }

    /**
     * 加载properties文件
     * 
     * @param context
     * @param file
     * @return
     * @throws Exception
     */
    public static Properties loadProperties(Context context, String file,
            String encode) throws Exception {
        Properties properties = new Properties();
        FileInputStream s = new FileInputStream(file);
        properties.load(s);
        return properties;
    }

    /**
     * 保存到properties文件中
     * 
     * @param context
     * @param file
     * @param properties
     * @throws Exception
     */
    public static void saveProperties(Context context, String file,
            String encode, Properties properties) throws Exception {
        FileOutputStream s = new FileOutputStream(file, false);
        properties.store(s, "");
    }
}
