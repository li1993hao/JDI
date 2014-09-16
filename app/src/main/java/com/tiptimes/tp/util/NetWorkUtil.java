package com.tiptimes.tp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

public class NetWorkUtil {

    /**
     * 检查是否有可用的网络
     * 
     * @param context
     *            上下文
     * @return true:有网络
     */
    public static boolean isAvaliable(Context context) {
        if (isWiFiActive(context) || isNetworkAvailable(context))
            return true;
        else
            return false;
    }

    /** 返回当前网速 */
    public static long getNetworkSpeed() {
        // TODO Auto-generated method stub
        ProcessBuilder cmd;
        long readBytes = 0;
        BufferedReader rd = null;
        try {
            String[] args = { "/system/bin/cat", "/proc/net/dev" };
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            rd = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.contains("wlan0") || line.contains("eth0")) {
                    // L.E("获取流量整条字符串",line);
                    String[] delim = line.split(":");
                    if (delim.length >= 2) {
                        String[] numbers = delim[1].trim().split(" ");// 提取数据
                        readBytes = Long.parseLong(numbers[0].trim());//
                        break;
                    }
                }
            }
            rd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return readBytes;
    }

    /**
     * 检查wifi是否可用
     * 
     * @param inContext
     * @return
     */
    public static boolean isWiFiActive(Context inContext) {
        WifiManager mWifiManager = (WifiManager) inContext
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            System.out.println("**** WIFI is on");
            return true;
        } else {
            System.out.println("**** WIFI is off");
            return false;
        }
    }

    /**
     * 检查手机网络(非wifi)是否有用
     * 
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info == null) {
                return false;
            } else {
                if (info.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否为wifi的连接ip
     * 
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        int ipAddress = getWifiIpInfo(context);
        if (ipAddress > 0)
            return true;
        else
            return false;
    }

    private static int getWifiIpInfo(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // return String.valueOf(wifiInfo.getIpAddress());
        int ipAddress = wifiInfo.getIpAddress();
        return ipAddress;
    }

    /**
     * 获取wifi ip
     * 
     * @return
     */
    public static String getWifiAddress(Context context) {
        int ipAddress = getWifiIpInfo(context);
        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取手机mac地址<br/>
     * 错误返回12个0
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 获取手机ip(此方法在android中使用获取3G网络ip地址)
     * 
     * @return
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     */
    public static String getLocalIpAddress() throws SocketException {
        for (Enumeration<NetworkInterface> en = NetworkInterface
                .getNetworkInterfaces(); en.hasMoreElements();) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                    .hasMoreElements();) {
                InetAddress inetAddress = enumIpAddr.nextElement();
                if (!inetAddress.isLoopbackAddress()) {
                    return inetAddress.getHostAddress().toString();
                }
            }
        }
        return null;
    }

    /**
     * 获取本机ip(此方法仅在java程序中)
     * 
     * @return
     * @throws java.net.UnknownHostException
     */
    public static String getHostAddress() throws UnknownHostException {
        InetAddress address = null;
        address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }
}
