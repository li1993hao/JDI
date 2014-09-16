package com.tiptimes.tp.util;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * 
 * @author tiptimes 
 *
 */
@SuppressLint("DefaultLocale")
@SuppressWarnings("deprecation")
public class Toolkit {

    /**
     * 
     * Role:Telecom service providers获取手机服务商信息 <BR>
     * 
     * 需要加入权限<uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/> <BR>
     * Date:2012-3-12 <BR>
     * 
     * @author CODYY)allen
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = "nosim";
        try {
            // 返回唯一的用户ID;就是这张卡的编号神马的
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String IMSI = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002"))
                ProvidersName = "中国移动";
            else if (IMSI.startsWith("46001"))
                ProvidersName = "中国联通";
            else if (IMSI.startsWith("46003"))
                ProvidersName = "中国电信";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ProvidersName;
        }
        return ProvidersName;
    }

    /**
     * 获取手机号
     * 
     * @param context
     * @return
     */
    public static String getPhone(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getLine1Number();
    }

    /**
     * 获取手机型号
     * 
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 获取sdk版本
     * 
     * @return
     */
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * 获取版本号
     * 
     * @return
     */
    public static String getVersion() {
        return Build.VERSION.RELEASE;
    }

    public static class TelephonyManagerInfo {
        /**
         * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动
         * 
         * 2.tm.CALL_STATE_RINGING=1 响铃
         * 
         * 3.tm.CALL_STATE_OFFHOOK=2 摘机
         */
        public  int CallState;
        /**
         * 
         * 电话方位：(需要权限：android.permission.ACCESS_COARSE_LOCATION)
         */
        public  String CellLocation;

        /**
         * 
         * 唯一的设备ID：
         * 
         * GSM手机的 IMEI 和 CDMA手机的 MEID.
         * 
         * Return null if device ID is not available.
         */
        public  String DeviceId;

        /**
         * 
         * 设备的软件版本号：
         * 
         * 例如：the IMEI/SV(software version) for GSM phones.
         * 
         * Return null if the software version is not available.
         */
        public  String DeviceSoftwareVersion;

        /**
         * 
         * 手机号：
         * 
         * GSM手机的 MSISDN.
         * 
         * Return null if it is unavailable.
         */
        public  String Line1Number;

        /**
         * 
         * 附近的电话的信息:
         * 
         * 类型：List<NeighboringCellInfo>
         * 
         * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
         */
        public  List<NeighboringCellInfo> NeighboringCellInfo;// List<NeighboringCellInfo>

        /**
         * 
         * 获取ISO标准的国家码，即国际长途区号。
         * 
         * 注意：仅当用户已在网络注册后有效。
         * 
         * 在CDMA网络中结果也许不可靠。
         */
        public  String NetworkCountryIso;

        /**
         * 
         * MCC+MNC(mobile country code + mobile network code)
         * 
         * 注意：仅当用户已在网络注册时有效。
         * 
         * 在CDMA网络中结果也许不可靠。
         */
        public  String NetworkOperator;

        /**
         * 
         * 按照字母次序的current registered operator(当前已注册的用户)的名字
         * 
         * 注意：仅当用户已在网络注册时有效。
         * 
         * 在CDMA网络中结果也许不可靠。
         */

        public  String NetworkOperatorName;// String

        /**
         * 当前使用的网络类型：
         * 
         * 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络 1
         * 
         * NETWORK_TYPE_EDGE EDGE网络 2
         * 
         * NETWORK_TYPE_UMTS UMTS网络 3
         * 
         * NETWORK_TYPE_HSDPA HSDPA网络 8
         * 
         * NETWORK_TYPE_HSUPA HSUPA网络 9
         * 
         * NETWORK_TYPE_HSPA HSPA网络 10
         * 
         * NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
         * 
         * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5
         * 
         * NETWORK_TYPE_EVDO_A EVDO网络, revision A. 6
         * 
         * NETWORK_TYPE_1xRTT 1xRTT网络 7
         */
        public  int NetworkType;// int

        /**
         * 
         * 手机类型：
         * 
         * 例如： PHONE_TYPE_NONE 无信号
         * 
         * PHONE_TYPE_GSM GSM信号
         * 
         * PHONE_TYPE_CDMA CDMA信号
         */

        public  int PhoneType;// int

        /**
         * 
         * Returns the ISO country code equivalent for the SIM provider's
         * country code.
         * 
         * 获取ISO国家码，相当于提供SIM卡的国家码。
         */
        public  String SimCountryIso;// String

        /**
         * 
         * Returns the MCC+MNC (mobile country code + mobile network code) of
         * the provider of the SIM. 5 or 6 decimal digits.
         * 
         * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
         * 
         * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
         */
        public  String SimOperator;// String

        /**
         * 
         * 服务商名称：
         * 
         * 例如：中国移动、联通
         * 
         * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
         */
        public  String SimOperatorName;// String

        /**
         * 
         * SIM卡的序列号：
         * 
         * 需要权限：READ_PHONE_STATE
         */
        public  String SimSerialNumber;// String

        /**
         * 
         * SIM的状态信息：
         * 
         * SIM_STATE_UNKNOWN 未知状态 0
         * 
         * SIM_STATE_ABSENT 没插卡 1
         * 
         * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2
         * 
         * SIM_STATE_PUK_REQUIRED 锁定状态，需要用户的PUK码解锁 3
         * 
         * SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
         * 
         * SIM_STATE_READY 就绪状态 5
         */
        public  int SimState;// int

        /**
         * 
         * 唯一的用户ID：
         * 
         * 例如：IMSI(国际移动用户识别码) for a GSM phone.
         * 
         * 需要权限：READ_PHONE_STATE
         */
        public  String SubscriberId;// String

        /**
         * 
         * 取得和语音邮件相关的标签，即为识别符
         * 
         * 需要权限：READ_PHONE_STATE
         */
        public  String VoiceMailAlphaTag;// String

        /**
         * 
         * 获取语音邮件号码：
         * 
         * 需要权限：READ_PHONE_STATE
         */
        public  String VoiceMailNumber;// String

        /**
         * 
         * ICC卡是否存在
         */
        public  boolean hasIccCard;// boolean

        /**
         * 
         * 是否漫游:
         * 
         * (在GSM用途下)
         */
        public  boolean isNetworkRoaming;
    }

    /**
     * 获取手机唯一ID
     * 
     * @param context
     * @return
     */
    public static String getPhoneUniqueId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机信息实体
     * 
     * @param context
     * @return
     */
    public static TelephonyManagerInfo getTelephonyInfo(Context context) {
        TelephonyManagerInfo info = new TelephonyManagerInfo();
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        info.CallState = tm.getCallState();
        info.CellLocation = tm.getCellLocation().toString();
        info.DeviceId = tm.getDeviceId();
        info.DeviceSoftwareVersion = tm.getDeviceSoftwareVersion();
        info.hasIccCard = tm.hasIccCard();
        info.isNetworkRoaming = tm.isNetworkRoaming();
        info.Line1Number = tm.getLine1Number();
        info.NeighboringCellInfo = tm.getNeighboringCellInfo();
        info.NetworkCountryIso = tm.getNetworkCountryIso();
        info.NetworkOperator = tm.getNetworkOperator();
        info.NetworkOperatorName = tm.getNetworkOperatorName();
        info.NetworkType = tm.getNetworkType();
        info.PhoneType = tm.getPhoneType();
        info.SimCountryIso = tm.getSimCountryIso();
        info.SimOperator = tm.getSimOperator();
        info.SimOperatorName = tm.getSimOperatorName();
        info.SimSerialNumber = tm.getSimSerialNumber();
        info.SimState = tm.getSimState();
        info.SubscriberId = tm.getSubscriberId();
        info.VoiceMailAlphaTag = tm.getVoiceMailAlphaTag();
        info.VoiceMailNumber = tm.getVoiceMailNumber();
        return info;
    }

    /**
     * 取得屏幕分辨率大小
     * 
     * @param context
     *            Activity上下文
     * @return第一个值为宽度
     */
    public static int[] getDisplayPixes(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new int[] { dm.widthPixels, dm.heightPixels };
    }

    /**
     * 取得屏幕分辨宽度和高度
     * 
     * @param context
     *            Activity上下文
     * @return第一个值为宽度
     */
    public static int[] getDisplayWidthHeight(Activity context) {
        Display dis = context.getWindowManager().getDefaultDisplay();
        return new int[] { dis.getWidth(), dis.getHeight() };
    }

    public static int dip2px(Context context, float dpValue) {
    	final float scale = context.getResources().getDisplayMetrics().density;
    	return (int) (dpValue * scale + 0.5f);
    	}
    	 
    	public static int px2dip(Context context, float pxValue) {
    	final float scale = context.getResources().getDisplayMetrics().density;
    	return (int) (pxValue / scale + 0.5f);
    	}

  

    // ---------------------------------------------------------------------------------------------------


    // -------------------------------------加密，验证，转换系列-----------------------------------------------------------
  


   


}