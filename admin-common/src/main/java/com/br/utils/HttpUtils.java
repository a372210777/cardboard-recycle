package com.br.utils;

import com.br.exception.HttpInvokeFailException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @version 1.0
 * @author linwei
 * @data 2017年5月13日
 * @描述:
 */
public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private final static String ENCODE = "UTF-8";

	/**
	 * HTTP GET
	 */
	public static String get(String url, Map<String, String> params) {
		String resp = null;
		long timeStart = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);

		client.getParams().setContentCharset(ENCODE);
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		if (params != null) {
			List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
			for (Entry<String, String> each : params.entrySet()) {
				nameValueList.add(new NameValuePair(each.getKey(), each.getValue()));
			}
			method.setQueryString(nameValueList.toArray(new NameValuePair[] {}));
		}

		logger.info(">>>发起请求地址:{},参数:{}", url, method.getQueryString());
		try {
			client.executeMethod(method);
			resp = method.getResponseBodyAsString();
			long timeEnd = System.currentTimeMillis();
			logger.info("返回结果:{},耗时:{}", resp, (timeEnd - timeStart));
		} catch (Exception e) {
			logger.error(">>>请求异常", e);
		}
		return resp;
	}

	public static String post4Common(String url, Map<String, String> params) {
		String resp = null;
		try {
			long timeStart = System.currentTimeMillis();
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			if (params != null) {
				List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
				for (Entry<String, String> each : params.entrySet()) {
					nameValueList.add(new NameValuePair(each.getKey(), each.getValue()));
				}
				method.setRequestBody(nameValueList.toArray(new NameValuePair[] {}));
			}
			
			logger.info(">>>发起请求地址:{},参数:{}", url, method.getRequestEntity());
			client.executeMethod(method);
			resp = method.getResponseBodyAsString();
			long timeEnd = System.currentTimeMillis();
			logger.info("返回结果:{},耗时:{}", resp, (timeEnd - timeStart));
		} catch (Exception e) {
			logger.error(">>>请求异常", e);
			throw new HttpInvokeFailException(e);
		}
		return resp;
	}
	
	public static String post4Json(String url,String data, Map<String, String> headerMap) {
		return post4JsonWithHeader(url, data, null);
	}
	
	public static String post4JsonWithHeader(String url,String data, Map<String, String> headerMap) {
		String resp = null;
		try {
			long timeStart = System.currentTimeMillis();
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			
			if (!CollectionUtils.isEmpty(headerMap)) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					method.setRequestHeader(entry.getKey(), entry.getValue());
				}
			}
			
			StringRequestEntity entity = new StringRequestEntity(data,"application/json","UTF-8");
			method.setRequestEntity(entity);
			logger.info(">>>发起请求地址:{},参数:{}", url, data);
			client.executeMethod(method);
			resp = method.getResponseBodyAsString();
			long timeEnd = System.currentTimeMillis();
			logger.info("返回结果:{},耗时:{}", resp, (timeEnd - timeStart));
		} catch (Exception e) {
			logger.error(">>>请求异常", e);
			throw new HttpInvokeFailException(e);
		}
		return resp;
	}
}