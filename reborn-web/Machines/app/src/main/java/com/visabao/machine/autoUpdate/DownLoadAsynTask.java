package com.visabao.machine.autoUpdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

import com.visabao.machine.util.Log;


/**
 * 提供文件异步下载的网络请求
 */
final public class DownLoadAsynTask extends AsyncTask<String, Integer, Boolean>{
	private OnDownLoadListener mDownLoadListener;
	private File mOutFile;
	
	public DownLoadAsynTask(OnDownLoadListener downLoadListener,File outFile){
		this.mDownLoadListener = downLoadListener;
		this.mOutFile = outFile;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		FileOutputStream fos = null;
		InputStream inputStream = null;
		 int currentProgress = 0;//当前进度
    	try {
    		fos = new FileOutputStream(mOutFile);
    		String url = params[0];
    		
    		int cnTimeout = 30000;//10秒
    		int timeoutSocket = 30000;//10秒
    		HttpParams httpParameters = new BasicHttpParams();
    		//设置超时时间 
    		HttpConnectionParams.setConnectionTimeout(httpParameters, cnTimeout);
    		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
    		HttpClient httpClient = new DefaultHttpClient(httpParameters);
//    		HttpPost httpPost = new HttpPost(url);
    		HttpGet httpPost = new HttpGet(url);
    		httpPost.addHeader("Connection", "close"); //请求完之后马上释放连接
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int stateCode = httpResponse.getStatusLine().getStatusCode();
			if (stateCode != HttpStatus.SC_OK){
				return false;
			}
			
	        byte[] buffer = new byte[2048];//这个值要设置合适，否则可能会导致下载很慢等情况
	        HttpEntity entity = httpResponse.getEntity();
	        long contentLength = entity.getContentLength();
	        Log.d("contentLength=" + contentLength);
	        if (contentLength < 0) {
	        	contentLength = 3845038;
	        }
	        if (contentLength <= 0){
	        	return false;
	        }
	        
	        int downdedLen= 0;//已下载的流量
	        int len = 0;
	        inputStream = entity.getContent();
	        while ((len = inputStream.read(buffer)) >= 0){
	        	int progress = (int)(downdedLen*100/contentLength);
	        	downdedLen += len;
	    		fos.write(buffer, 0, len);
        		fos.flush();
	        	if (progress > currentProgress){//算出的进度数大于当前进度数时才刷新通知栏，否则会频繁的发送更新通知而导致下拉通知栏很卡
	        		currentProgress = progress;
	        		publishProgress(progress);
	        	}
	        }
	        return true;
    	} catch (Exception e) {
    		Log.e(e.getMessage(), e);
		}finally{
			try {
				if (inputStream!=null){
					inputStream.close();
				}

				if (fos!=null){
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
    	return false;
	}
	
	@Override
	protected void onPreExecute() {
		if (mDownLoadListener != null){
			mDownLoadListener.onBegin();
		}
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if (mDownLoadListener != null){
			mDownLoadListener.onEnd(result, mOutFile);
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		if (mDownLoadListener != null){
			mDownLoadListener.onUpdateProgress(values[0]);
		}
	}
	/**
	 * 下载监听
	 */
	public static interface OnDownLoadListener {
		/**
		 * 下载开始
		 */
		public void onBegin();
		/**
		 * 下载中进行进度条更新
		 * @param progress
		 */
		public void onUpdateProgress(int progress);
		/**
		 * 完成下载
		 * @param result 下载的结果
		 * @param outFile 输出的文件
		 */
		public void onEnd(boolean result, File outFile);
	}

}
