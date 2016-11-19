package com.android.app.tools;

import java.io.File;

import android.content.Context;
import android.widget.ImageView;

import edu.feicui.mnewsupdate.volley.RequestQueue;
import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.toolbox.ImageLoader;
import edu.feicui.mnewsupdate.volley.toolbox.MultiPosttRequest;
import edu.feicui.mnewsupdate.volley.toolbox.StringRequest;
import edu.feicui.mnewsupdate.volley.toolbox.Volley;
import zhuoxinzhiqu.news2.R;


public class VolleyHttp {

	public static RequestQueue mQueue;
	Context context;

	public VolleyHttp(Context context) {
		if (mQueue == null) {
			mQueue = Volley.newRequestQueue(context);
		}
		this.context = context;
	}

	public void getJSONObject(String url, Response.Listener<String> listener,
			Response.ErrorListener errorListener) {
		StringRequest request = new StringRequest(url, listener, errorListener);
		mQueue.add(request);
	}

	public void addImage(String url, ImageLoader.ImageCache imageCache, ImageView iv) {
		ImageLoader mImageLoader = new ImageLoader(mQueue, imageCache);
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv,
				R.drawable.defaultpic, android.R.drawable.ic_delete);
		mImageLoader.get(url, listener);
	}

	

	public void upLoadImage(String url, File file, Response.Listener<String> listener,
			Response.ErrorListener errorListener) {
		MultiPosttRequest request = new MultiPosttRequest(url, listener,
				errorListener);
		request.buildMultipartEntity("portrait", file);
		mQueue.add(request);
	}

	public void addUserString(String url, String token, String imei,
							  Response.Listener<String> listener, Response.ErrorListener errorListener) {
		MultiPosttRequest request = new MultiPosttRequest(url, listener,
				errorListener);
		request.buildMultipartEntity("token", token);
		request.buildMultipartEntity("imei", imei);
		request.buildMultipartEntity("ver", 1 + "");
		mQueue.add(request);
	}

}
