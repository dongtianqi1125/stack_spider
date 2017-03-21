package com.esy.stack.util;

import com.esy.stack.exception.DownLoadException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * http请求工具类
 * @author guanjie
 *
 */
@Slf4j
public final class HttpClientUtil {

	private static final int BUF = 1024;
	private static final CloseableHttpClient HTTPCLIENT = HttpClients.createDefault();
	private static final RequestConfig REQUESTCONFIG = RequestConfig.custom().setSocketTimeout(Constants.SO_TIMEOUT).setConnectTimeout(Constants.CONNECTION_TIMEOUT).build();
	/**
	 * post请求
	 * @param url {@link String}
	 * @param param {@link Map}
	 * @return string
	 * @throws IOException
	 */
	public static String postRequest(String url, Map<String, Object> param) throws IOException {
		if(log.isInfoEnabled())
			log.info("postRequest from url : " + url);
		HttpPost method = new HttpPost(url);
		method.setConfig(REQUESTCONFIG);
		if (param != null && !param.isEmpty()) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> each : param.entrySet())
				formparams.add(new BasicNameValuePair(each.getKey(), each.getValue().toString()));

			UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formparams, "utf-8");
			method.setEntity(requestEntity);
		}
		try(CloseableHttpResponse response = HTTPCLIENT.execute(method);) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				return copyToString(response.getEntity().getContent(), "utf-8");
		}
		throw new IOException("下载异常！");
	}

	/**
	 * 下载url内容
	 * @param url
	 * @param charSet
	 * @return string
	 * @throws IOException
	 */
	public static String downloadToString(String url, String charSet) throws IOException {
		if(log.isInfoEnabled())
			log.info("downloadToString from url : " + url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUESTCONFIG);
		try(CloseableHttpResponse response = HTTPCLIENT.execute(httpGet)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return copyToString(entity.getContent(), charSet);
			} else {
				throw new DownLoadException("服务器返回异常！");
			}
		}
	}

	/**
	 * 下载url内容，以指定编码保存到文件
	 * @param url
	 * @param charSet
	 * @param aimfile
	 * @throws IOException
	 */
	public static void downloadToFile(String url, String charSet, File aimfile) throws IOException {
		if(log.isInfoEnabled())
			log.info("download from url : " + url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(REQUESTCONFIG);
		try(CloseableHttpResponse response = HTTPCLIENT.execute(httpGet);) {
			if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				copyToFile(entity.getContent(), aimfile, charSet);
			} else {
				throw new DownLoadException("服务器返回异常！");
			}
		}
	}

	private static void copyToFile(InputStream is, File descFile, String charset) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(descFile), charset))) {
			char[] cbuf = new char[BUF];
			int hasread = 0;
			while ((hasread = br.read(cbuf)) > 0) {
				bw.write(cbuf, 0, hasread);
			}
		}
	}

	private static String copyToString(InputStream is, String charset) throws IOException {



		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));) {
			char[] cbuf = new char[BUF];
			int hasread = 0;
			StringBuilder sb = new StringBuilder(4 * BUF);
			while ((hasread = br.read(cbuf)) > 0)
				sb.append(new String(cbuf, 0, hasread));

			return sb.toString();
		}
	}



	private HttpClientUtil() {
	}
}
