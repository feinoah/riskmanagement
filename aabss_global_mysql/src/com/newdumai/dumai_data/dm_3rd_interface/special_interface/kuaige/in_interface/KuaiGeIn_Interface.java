package com.newdumai.dumai_data.dm_3rd_interface.special_interface.kuaige.in_interface;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.newdumai.dumai_data.dm_3rd_interface.util.MD5Util;

public class KuaiGeIn_Interface {
	
	private static final String ACCOUNT = "aqBang";
	private static final String PASSWORD = "kds@k_43Sp3";
	
	/**
	 * 
	 * @param dm_3rd_interfaceMap
	 * @param in_para
	 * @return
	 */
	public static Map<String, Object> getIn_para(Map<String, Object> dm_3rd_interfaceMap, Map<String, Object> in_para) {
		Map<String, Object> inPara = new HashMap<String, Object>();
		Date date = new Date();
		long timestamp = date.getTime();
		double random =  Math.random();
		String sign = MD5Util.toHex(PASSWORD+timestamp+random);
		
		inPara.put("account", ACCOUNT);
		inPara.put("timestamp", timestamp+"");
		inPara.put("random", random);
		inPara.put("sign",sign);
		inPara.put("data",new GsonBuilder().serializeNulls().create().toJson(in_para));
		
		return inPara;
	}
	
	
}
