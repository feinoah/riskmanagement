package com.newdumai.dumai_data.dm_3rd_interface.special_interface.xiaoaiyouxin;

import com.alibaba.fastjson.JSON;
import com.newdumai.util.MapObjUtil;
import puhui.credit.common.AESUtils;
import puhui.credit.common.HttpRequstUtils;
import puhui.credit.common.PuhuiRsaUtils;
import puhui.credit.common.SignUtils;

import java.util.Map;

/**
 * 小爱有信输入接口(走独立接口)
 * <p>
 * Created by zhang on 17-8-1.
 */
public class XiaoaiyouxinIn_Interface {

    //用户名
    public static final String USERNAME = "test";
//    public static final String USERNAME = "aiqianbang";//生产
    //密码
    public static final String PASSWORD = "4557fe5acfef84d2b9ecb63bbcbdbf05";
//    public static final String PASSWORD = "a43935975b3ceeccdf5276f3cdbe50b";//生产
    //密钥
    public static final String STR_PRIK = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKo++i9J9dzAFtbxwowKDCo2mxi7MXxE8A8VvssaydWjjgmEz/HHMPLOhi1182a1si4pWL0/MizKnquD7T2Bu4jpQbAFnkNYEMEyq/kw904Xl0JCQHYFuvnI99RE8Q3KlTP6kEUGDjV34EL6vBGJcQvArLtj1xoP8y0nIfJ2Pw5TAgMBAAECgYAGGB8IllMwxceLhjf6n1l0IWRH7FuHIUieoZ6k0p6rASHSgWiYNRMxfecbtX8zDAoG0QAWNi7rn40ygpR5gS1fWDAKhmnhKgQIT6wW0VmD4hraaeyP78iy8BLhlvblri2nCPIhDH5+l96v7D47ZZi3ZSOzcj89s1eS/k7/N4peEQJBAPEtGGJY+lBoCxQMhGyzuzDmgcS1Un1ZE2pt+XNCVl2b+T8fxWJH3tRRR8wOY5uvtPiK1HM/IjT0T5qwQeH8Yk0CQQC0tcv3d/bDb7bOe9QzUFDQkUSpTdPWAgMX2OVPxjdq3Sls9oA5+fGNYEy0OgyqTjde0b4iRzlD1O0OhLqPSUMfAkEAh5FIvqezdRU2/PsYSR4yoAdCdLdT+h/jGRVefhqQ/6eYUJJkWp15tTFHQX3pIe9/s6IeT/XyHYAjaxmevxAmlQJBAKSdhvQjf9KAjZKDEsa7vyJ/coCXuQUWSCMNHbcR5aGfXgE4e45UtUoIE1eKGcd6AM6LWhx3rR6xdFDpb9je8BkCQB0SpevGfOQkMk5i8xkEt9eeYP0fi8nv6eOUcK96EXbzs4jV2SAoQJ9oJegPtPROHbhIvVUmNQTbuP10Yjg59+8=";

    /**
     * 风险信息查询
     *
     * @param dm_3rd_interfaceMap
     * @param in_para
     * @return
     */
    public static String riskSearch(Map<String, Object> dm_3rd_interfaceMap, Map<String, Object> in_para) {
        String result = "";
        Map<String, String> param = MapObjUtil.mapObjToString(in_para);

        try {
            String url = (String) dm_3rd_interfaceMap.get("url");
            url = url + "?userName=" + USERNAME + "&passWord=" + PASSWORD;

            String json = JSON.toJSONString(SignUtils.createLinkString(param));
            String sign_encode = PuhuiRsaUtils.sign(json.getBytes("UTF-8"), STR_PRIK);
            param.put("sign", sign_encode);
            System.out.println("数字签名：" + sign_encode);

            String paramJson = JSON.toJSONString(param);
            paramJson = AESUtils.encrypt(paramJson);
            System.out.println("paramJson:" + paramJson);

            result = AESUtils.decrypt(HttpRequstUtils.doPost(url, paramJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("风险信息查询(xayx)：" + result);
        return result;
    }
}
