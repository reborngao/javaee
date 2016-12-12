package com.visabao.machine.autoUpdate;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP请求
 */
public class HttpConnectionUtil {
	/**
	 * 由于不想太过于代码繁琐，不需要弹窗的使用HttpURLConnection请求数据
	 * 
	 * @param postUrl 请求全路径
	 * @return
	 */
	public  static String connect(final String postUrl) {
		URL url = null;
		HttpURLConnection urlconn = null;
		InputStream is = null;
		String content = "";
		try {
			url = new URL(postUrl);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setConnectTimeout(10000);
			urlconn.setReadTimeout(10000);
			urlconn.setUseCaches(false); // 设定缓存
			urlconn.setDoOutput(true); // 设定输出URLCONN
			urlconn.setDoInput(true); // 设定输入URLCONN
			is = urlconn.getInputStream();
			int i = -1;
			byte[] b = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((i = is.read(b)) != -1) {
				sb.append(new String(b, 0, i));
			}
			content = sb.toString();
			return content;
		} catch (Exception e) {
			return content;
		} finally {
			if (urlconn != null)
				urlconn.disconnect();
		}
	}
	
//	//线上通过百度在线反编译进行转换
//	public static String getAdressByLocation(double mlat,double mlot){
//		try {
//			String url = "http://api.map.baidu.com/geocoder?output=json";
//			url += "&location=";
//			url +=	mlat + "," + mlot;
//			url += "&amp;key=";
//			url += Constants.mDemoApp.mStrKey;
//			String params = connect(url);
//			Map<String, Object> map = JsonUtil.json2Map(params);
//			Map<String, Object> result = JsonUtil.json2Map(map.get("result").toString());
//			String addres =  result.get("formatted_address").toString();
//			return addres.replace("金湾区", "");
//		} catch (Exception e) {
//			DebugUtil.debug("在线,位置转换" + e.getMessage());
//			return "暂无地址信息";
//		}
//	}
}
