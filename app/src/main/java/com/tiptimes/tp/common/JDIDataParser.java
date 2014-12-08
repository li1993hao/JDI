package com.tiptimes.tp.common;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

/**网络数据的默认解析器
 * Created by haoli on 14/11/4.
 */
public class JDIDataParser implements DataParser {
    /**
     * 通用的json解析字段
     */
    private final static String SERVER_JSON_STAT_STATUS = "status";//状态值
    private final static String SERVER_JSON_STAT_DATA = "data"; //数据
    private final static String SERVER_JSON_STAT_MSG = "msg";  //信息

    @Override
    public void parse(String jsonStr, ActionBundle actionBundle,ActionInfo actionInfo) throws  Exception{
        JSONObject json = new JSONObject(jsonStr);
        int status = json.getInt(SERVER_JSON_STAT_STATUS);
        if(status == 1){
            //服务器返回数据正常,status=1表示服务器返回数据状态
            //具体取值根据业务需求变动
            //默认1是正常，其他为异常
            actionBundle.isNomal = true;

            if(actionInfo.isList()){
                //返回线性集合
                actionBundle.data= JSON.parseArray(json.get(SERVER_JSON_STAT_DATA).toString(), actionInfo.getDataClass());
            }else if(actionInfo.getDataClass().equals(NoData.class)){
                //没有返回数据
                actionBundle.data=new NoData();
            }else{
                //返回bto对象
                actionBundle.data=JSON.parseObject(json.get(SERVER_JSON_STAT_DATA).toString(), actionInfo.getDataClass());
            }

        }else{
            actionBundle.isNomal = false;
            actionBundle.msg = json.getString(SERVER_JSON_STAT_MSG);
        }
    }
}
