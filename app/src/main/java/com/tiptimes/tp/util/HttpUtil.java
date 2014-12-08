package com.tiptimes.tp.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import haihemoive.Application;

public class HttpUtil {
	public static int REQUEST_TIMEOUT = 5000;
	public static int SO_TIMEOUT = 10000;
	static String EX_TIMEOUT = "服务器连接异常";
	static String EX_SERVER = "服务器端错误";
	static String EX_UNKNOW = "未知错误";
	static String EX_NETUNAVALIABLE = "网络不可用!";
	static DefaultHttpClient httpClient;

	static private DefaultHttpClient getHttpClient(){
		if(httpClient == null){
		    BasicHttpParams httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		    HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		    httpClient = new DefaultHttpClient(httpParams);
		}
		return httpClient;
	}


   static public String assemblyUrlByCommon(String url, Map<String,String> params) {
        if (params == null && params.size() == 0) {
            return url;
        } else {
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");

            Set<String> keys = params.keySet();
            int i = 0;
            for (String key : keys) {
                String value = params.get(key);
                sb.append(key).append("=").append(value);
                if (i != keys.size()-1) {
                    sb.append("&");
                }
                i++;
            }
            return sb.toString();
        }
    }

    /**
     * get请求
     * @param path
     * @param params
     * @return
     */
    public static HttpRespondInfo getRequest(String path,
                                              Map<String, String> params) {
        L.i(L.TAG, "get_url:"+path);

        if(!NetWorkUtil.isAvaliable(Application.getApplication())){
            return getExceptionJson(EX_NETUNAVALIABLE);
        }



        // 使用HttpPost对象设置发送的URL路径
        HttpGet  get = new HttpGet(assemblyUrlByCommon(path,params));
        String respondStr = null;

        // 创建一个浏览器对象，以把get对象向服务器发送，并返回响应消息
        DefaultHttpClient dhc = getHttpClient();
        HttpResponse response = null;
        try {
            response = dhc.execute(get);
            respondStr = EntityUtils.toString(response.getEntity(), "utf-8");
            L.i(L.TAG, "response_data:"+respondStr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return getExceptionJson(EX_TIMEOUT);
        }

        L.d(L.TAG, "服务器返回状态：----" + response.getStatusLine().getStatusCode());
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return new HttpRespondInfo(respondStr, true);
        } else{
            get.abort();
        }

        return getExceptionJson(EX_UNKNOW);
    }


    /**
     * post请求
     * 
     * @param path
     *            请求的地址
     * @param params
     *            参数列表
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     * @throws org.apache.http.client.ClientProtocolException
     */
    public static HttpRespondInfo postRequest(String path,
            Map<String, String> params) {
    	L.i(L.TAG, "post_url:"+path);
        // 封装请求参数
        List<NameValuePair> pair = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pair.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            	L.i(L.TAG, "key:"+entry.getKey()+"  value:"+entry.getValue());
            }
        }
        
        if(!NetWorkUtil.isAvaliable(Application.getApplication())){
        	return getExceptionJson(EX_NETUNAVALIABLE);
        }
        
        // 把请求参数变成请求体部分
        UrlEncodedFormEntity uee=null;
		try {
			uee = new UrlEncodedFormEntity(pair, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			return getExceptionJson(EX_UNKNOW);
		}        
        
        
        // 使用HttpPost对象设置发送的URL路径
        final HttpPost post = new HttpPost(path);
        String respondStr = null;
        // 发送请求体
        post.setEntity(uee);
        // 创建一个浏览器对象，以把POST对象向服务器发送，并返回响应消息
        DefaultHttpClient dhc = getHttpClient();
        HttpResponse response = null;
        try {
			response = dhc.execute(post);
			respondStr = EntityUtils.toString(response.getEntity(), "utf-8");
	    	L.i(L.TAG, "response_data:"+respondStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
	        return getExceptionJson(EX_TIMEOUT);
		}

        L.d(L.TAG, "服务器返回状态：----" + response.getStatusLine().getStatusCode());
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        	return new HttpRespondInfo(respondStr, true);
        } else{
            post.abort();
        }

        return getExceptionJson(EX_UNKNOW);
    }

    static  private HttpRespondInfo getExceptionJson(String msg){
		return new HttpRespondInfo(msg, false);
    }

    /*
     * 参数说明: uploadUrl: Servlet的url fileName: 上传图片的文件名(如: qq.png) fileUrl:
     * 上传文件在手机客户端的完整路径(如: /sdcard/qq.png)
     */
    public static InputStream upload(String uploadUrl, String fileName,
            String fileUrl) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        InputStream is = null;
        URL url = new URL(uploadUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        /* 允许Input、Output，不使用Cache */
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        /* 设置传送的method=POST */
        conn.setRequestMethod("POST");
        /* setRequestProperty */
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="
                + boundary);
        // 将phone放置到请求的头部

        if (fileName != null && fileUrl != null) {
            conn.setRequestProperty("picName", fileName);
            /* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + fileName + "\"" + end);
            ds.writeBytes(end);

            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(fileUrl);
            /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
                /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

            /* close streams */
            fStream.close();
            ds.flush();
            ds.close();
        }
        /* 取得Response内容 */
        is = conn.getInputStream();
        return is;
    }
  
}
