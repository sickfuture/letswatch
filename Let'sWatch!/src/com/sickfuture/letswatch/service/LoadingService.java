package com.sickfuture.letswatch.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;

import com.android.sickfuture.sickcore.asynctask.CommonTask;
import com.android.sickfuture.sickcore.asynctask.ParamCallback;
import com.android.sickfuture.sickcore.exceptions.BadRequestException;
import com.android.sickfuture.sickcore.http.HttpManager;
import com.android.sickfuture.sickcore.http.HttpManager.RequestType;
import com.sickfuture.letswatch.processor.MovieProcessor;
import com.sickfuture.letswatch.request.LoadingRequest;

public class LoadingService extends Service implements ParamCallback<String> {

	private LoadingRequest request;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		processIntent(intent);
		return START_NOT_STICKY;
	}

	private void processIntent(Intent intent) {
		request = intent.getParcelableExtra("request");
		new CommonTask<LoadingRequest, String>(this) {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Object load(final LoadingRequest d) {
				try {
					final String source = HttpManager.getInstance(
							getApplicationContext()).loadAsString(d.getUrl(),
							RequestType.GET);
					Class<?> cl = MovieProcessor.class;
					final Object processor = cl.newInstance();
					//Method[] m = cl.getMethods();
					final Method method = cl.getMethod(d.getProcessMethod(),
							String.class, int.class);
					ContentValues[] values = AccessController
							.doPrivileged(new PrivilegedExceptionAction() {
								public Object run() throws Exception {
									if (!method.isAccessible()) {
										method.setAccessible(true);
									}
									return method.invoke(processor, source,
											d.getMarker());
								}
							});
					getContentResolver().bulkInsert(d.getContentUri(), values);
					return null;
				} catch (ClientProtocolException e) {
					this.setException(e);
				} catch (IOException e) {
					this.setException(e);
				} catch (JSONException e) {
					this.setException(e);
				} catch (BadRequestException e) {
					this.setException(e);
				} catch (PrivilegedActionException e) {
					this.setException(e);
				} catch (NoSuchMethodException e) {
					this.setException(e);
				} catch (InstantiationException e) {
					this.setException(e);
				} catch (IllegalAccessException e) {
					this.setException(e);
				}
				return null;
			}

		}.start(request);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSuccess(String c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Throwable e) {
		// TODO Auto-generated method stub

	}

}
