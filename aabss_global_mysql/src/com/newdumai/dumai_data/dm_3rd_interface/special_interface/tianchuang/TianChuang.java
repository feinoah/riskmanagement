package com.newdumai.dumai_data.dm_3rd_interface.special_interface.tianchuang;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.newdumai.util.JsonToMap;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.junit.Test;

import com.google.gson.Gson;
import com.newdumai.dumai_data.dm_3rd_interface.util.CommonUtil;
import com.newdumai.global.dao.Dumai_sourceBaseDao;
import com.newdumai.setting.interface_source.in_interface.tianchuang.util.AESUtil;
import com.newdumai.setting.interface_source.in_interface.tianchuang.util.JsoupUtil;
import com.newdumai.setting.interface_source.in_interface.tianchuang.util.ParamUtil;
import com.newdumai.util.SpringApplicationContextHolder;

public class TianChuang {
	
	//授权码 appId
	//Rest代码 tokenId
	private static final String url = "http://api.tcredit.com/driver/carCheck";
	private static final String appId = "72c97740-6be0-412b-8362-1359dc3a3a57" ;
	private static final String tokenId = "036c8c16-9f6f-47fa-bd34-0bb051a80ecc";

	/**
	 * 需求1：调试“天创API - 车辆驾驶信息”接口，并根据附件样式展示查得结果，注意返回结果中，cllx hpzl jdczt等字段需要参照文档附件的字典解读。
	 * 通过输入的机动车牌号和号牌种类，获取机动车认证信息。
	 */
	public static String authMotorVehicleInfo(Map<String, Object> map){
		
		String interface_code = (String) map.get("interface_source_code");
		
		@SuppressWarnings("unchecked")
		Map<String,Object> in_para = (Map<String, Object>) map.get("in_para");
		String plate = (String) in_para.get("plate");
		String plateType =(String) in_para.get("plateType");
		
		Dumai_sourceBaseDao mysqlSpringJdbcBaseDao = (Dumai_sourceBaseDao) SpringApplicationContextHolder.getBean("dumai_sourceBaseDao");
		
		String result = null;
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("plate", AESUtil.encode(appId.replace("-", ""),plate));
			params.put("plateType", plateType);
			String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
			params.put("tokenKey", tokenKey);
			params.put("appId", appId);
			result = exec(params, url);
			//result = FileUtils.readFileToString(new java.io.File("d:/tc.txt"), "UTF-8");
			
			String insertSql = "INSERT INTO dm_3rd_interface_detail(code, dm_3rd_interface_code, in_para, result, base_condition) VALUES (?,?,?,?,?)";
			String base_condition = (String) map.get("base_condition");
			String dm_3rd_interface_detail_code = UUID.randomUUID().toString();
			mysqlSpringJdbcBaseDao.insert(insertSql, dm_3rd_interface_detail_code,interface_code,new Gson().toJson(in_para),result,base_condition);
			
			result = CommonUtil.checkBaseCondition_old(result, base_condition, dm_3rd_interface_detail_code, dm_3rd_interface_detail_code);
			return result;
		} catch (Exception e) {
			mysqlSpringJdbcBaseDao.update("update dm_3rd_interface set error = error + 1 where code = ? ", interface_code);
			e.printStackTrace();
			return null;
		}
	}
//	/**
//	 * 需求1：调试“天创API - 车辆驾驶信息”接口，并根据附件样式展示查得结果，注意返回结果中，cllx hpzl jdczt等字段需要参照文档附件的字典解读。
//	 * 通过输入的机动车牌号和号牌种类，获取机动车认证信息。
//	 * @param plate
//	 * @param plateType
//	 * @param url
//	 * @param appId
//	 * @param tokenId
//	 */

//	public static String authMotorVehicleInfo(Map<String, Object> map){
//		String plate = "";
//		String plateType ="";
//		if(map.containsKey("plate")){
//			plate = map.get("plate").toString();
//		}else{
//			return "plate 参数不正确";
//		}
//		if(map.containsKey("plateType")){
//			plateType = map.get("plateType").toString();
//		}else{
//			return "plateType 参数不正确";
//		}
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("plate", AESUtil.encode(appId.replace("-", ""),plate));
//		params.put("plateType", plateType);
//		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
//		params.put("tokenKey", tokenKey);
//		params.put("appId", appId);
//		return exec(params, url);
//	}

	/**
	 * 需求2：根据查得结果查询“天创API－车辆驾驶信息－车辆维修保养报告”接口，并根据附件样式展示查得结果。注意该接口需要使用第一个接口的返回结果
	 * 
	 */
//	public static String getVehicleRepairReport(Map<String,Object> map){
//		String interface_source_code = (String) map.remove("interface_source_code");
//		Map<String, Object> save_detail_info = new HashMap<String,Object>();
//		String in_para=new Gson().toJson(map);
//
//		//通过输入的机动车牌号和号牌种类，获取机动车认证信息。
//		String vehicleInfo =authMotorVehicleInfo(map);
//		String authStatus = getInfoByKey(vehicleInfo, "authStatus");
//		save_detail_info.put("result", vehicleInfo);
//		save_detail_info.put("interface_source_code", interface_source_code);
//		save_detail_info.put("in_para", in_para);
//
//		String detail_info_code = baseService.addAndRet(save_detail_info,"sys_interface_source_detail_info");
//		Map<String,Object> where = new HashMap<String,Object>();
//		where.put("code", detail_info_code);
//		if(!"0".equals(authStatus))return detail_info_code+"#detail_info_code#"+vehicleInfo;
//
//		//根据车辆识别代码vin查询是否支持车辆品牌
//		String vin = getInfoByKey(vehicleInfo, "clsbdh");
//		String fdjh = getInfoByKey(vehicleInfo, "fdjh");
//		String brandInfo = checkBrand(vin, appId, tokenId);
//		String checkBrand = getInfoByKey(brandInfo, "result");
//		update_detail_info("result2", brandInfo, where);
//		if(!"2".equals(checkBrand))return detail_info_code+"#detail_info_code#"+brandInfo;
//
//		//输⼊入vin码、车牌号、发动机号购买报告获取订单号
//		String orderInfo = getOrderId(map.get("plate").toString(), fdjh, vin, appId, tokenId);
//		String orderResult = getInfoByKey(orderInfo, "result");
//		update_detail_info("result3", orderInfo, where);
//		if(!"1".equals(orderResult))return detail_info_code+"#detail_info_code#"+orderInfo;
//
//		//查询订单状态
//		String orderId = getInfoByKey(orderInfo, "orderId");
//		String orderStatus = getOrderStatus(orderId, appId, tokenId);
//		String orderStatusResult = getInfoByKey(orderStatus, "result");
//		update_detail_info("result4", orderStatus, where);
//		if(!"2".equals(orderStatusResult))return detail_info_code+"#detail_info_code#"+orderStatus;
//
//		//通过订单号获取审核通过的报告
//		String orderReport = getReport(orderId, appId, tokenId);
//		update_detail_info("result5", orderReport, where);
//		return detail_info_code+"#detail_info_code#"+orderReport ;
//	}
	
	/**
	 * 获取个人公积金缴纳信息
	 */
	public static String getFundInfo(Map<String,Object> map){
		Map<String,String> params = new HashMap<String,String>();
		String name = "";
		String idcard ="";
		if(map.containsKey("name")){
			name = (String)map.get("name");
			params.put("name", name);
		}
		if(map.containsKey("idcard")){
			idcard = (String)map.get("idcard");
			params.put("idcard", idcard);
		}
		String url = "http://api.tcredit.com/identity/getFundInfo";
		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
		params.put("tokenKey", tokenKey);
		params.put("appId", appId);
		return exec(params,url);
	}
	
//	private static void update_detail_info(String step,String result,Map<String,Object>where) {
//		Map<String,Object> update_detail_info = new HashMap<String,Object>();
//		update_detail_info.put(step, result);
//		baseService.Update(update_detail_info, "sys_interface_source_detail_info", where);
//	}
//
//	private static void setBaseService() {
//		baseService = (BaseServiceImpl) SpringApplicationContextHolder.getBean(Dm_3rd_interfaceServiceImpl.class);
//	}

	@SuppressWarnings({ "unchecked" })
	private static String getInfoByKey(String data,String key){
		String result = "";
		try {
			result = ((Map<String,Object>) JsonToMap.gson2Map(data).get("data")).get(key).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static String checkBrand(String vin,String appId ,String tokenId){
		Map<String,String> params = new HashMap<String,String>();
		params.put("vin", vin);
		String url = "http://api.tcredit.com/driver/maintenance/checkBrand";
		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
		params.put("tokenKey", tokenKey);
		params.put("appId", appId);
		return exec(params,url);
	}

	/**
	 * “天创API－车辆驾驶信息－车辆维修保养报告”——下单购买报告
	 *
	 */
	public static String getOrderId(Map<String, Object> dm_3rd_interfaceMap, Map<String, Object> in_para){
		String vin =(String) in_para.get("vin");
		String licensePlate =(String) in_para.get("licensePlate");
		String engineNo =(String) in_para.get("engineNo");
		Map<String,String> params = new HashMap<String,String>();
		params.put("vin", vin);

		if(StringUtils.isNotEmpty(licensePlate)){
			params.put("licensePlate", licensePlate);
		}
		if(StringUtils.isNotEmpty(engineNo)){
			params.put("engineNo", engineNo);
		}
		String url = "http://api.tcredit.com/service/driver/maintenance/getOrderId";
		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
		params.put("tokenKey", tokenKey);
		params.put("appId", appId);
		String result = exec(params,url);
		System.out.println("tianchuan result :"+result);
		return result;
	}

//	private static String getOrderStatus(String orderId,String appId ,String tokenId){
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("orderId", orderId);
//		String url = "http://api.tcredit.com/driver/maintenance/getOrderStatus";
//		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
//		params.put("tokenKey", tokenKey);
//		params.put("appId", appId);
//		return exec(params,url);
//	}

	public static String getReport(String orderId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId);
		String url = "http://api.tcredit.com/service/driver/maintenance/getReport";
		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
		params.put("tokenKey", tokenKey);
		params.put("appId", appId);
		return exec(params, url);
	}
	
	private static String exec(Map<String, String> params,String url){
		String result = null;
		try {
			Response content = JsoupUtil.getContent(url, params, null, Method.POST);
			result = content.body();
		} catch (Exception e) {
		}
		return result;	
	}
	
	@Test
	public void doTest(){
		//{"data":{"authResult":"查询成功_有数据","authStatus":"0","ccdjrq":"2015/9/2 1","cllx":"K31","clsbdh":"LSVYB65L4E2014558","clxh":"SVW6452FGD","cphm":"晋A1709U","cpxh":"斯柯达牌SVW6452FGD","csys":"I","fdjh":"6811","fdjxh":"CSS","hpzl":"02","jdcsyr":"张学梅","jdczt":"G","jyyxqz":"2017/9/30","syxz":"A","zwpp":"斯柯达牌"},"message":"成功","seqNum":"0cbb0cf66d2d4c0283c691307e99cf52","status":0}
		Map<String, String> params = new HashMap<String,String>();
		String license = "晋A1709U";
		String licenseType = "02";
//		params.put("license", AESUtil.encode(appId.replace("-", ""),license));
		params.put("license",license);
		params.put("licenseType", licenseType);
		String tokenKey = ParamUtil.getTokenKey(url, tokenId, params);
		params.put("tokenKey", tokenKey);
		params.put("appId", appId);
		String result = null;
		try {
			Response content = JsoupUtil.getContent(url, params, null, Method.POST);
			result = content.body();
		} catch (Exception e) {
		}
		System.out.println(result);
	}
	/*@Test
	public void test(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "张继涛");
		map.put("idcard", "230119197505063913");
		System.out.println(TianChuang.getFundInfo(map));
	}*/
	
}
