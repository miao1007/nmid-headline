package cn.edu.cqupt.nmid.headline.support.db;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
	//传入id,分类，数量，接口分类
	public static String getJosn(int _id, int category, int limit, String type) {
		String jsonStr = "";
		URL url=null;
		try {
			url = new URL(Constant.API_URL + "/txtt/public/api/android/" + type
					+ "?id=" + _id + "&category=" + category + "&limit="
					+ limit);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			int respCode = httpCon.getResponseCode();
			if (respCode == 200) {
				InputStream inputStream = httpCon.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
					out.write(buffer, 0, len);
				}
				jsonStr = new String(out.toByteArray());
				inputStream.close();
				out.close();
				return jsonStr;
			}
			else{
				throw new IOException();
			}
		} catch (IOException e) {
			return "{\"status\":0,\"data\":null}";
		}
	}
}
