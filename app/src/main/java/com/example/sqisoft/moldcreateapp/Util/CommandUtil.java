package com.example.sqisoft.moldcreateapp.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.sqisoft.moldcreateapp.data.AbstractRequest;
import com.example.sqisoft.moldcreateapp.data.ResponseListener;
import com.example.sqisoft.moldcreateapp.domain.ResultSendingImage;
import com.example.sqisoft.moldcreateapp.manager.DataManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;


public class CommandUtil {
	//TODO : url 수정
	private static DataManager dataManager =  DataManager.getInstance();

	private static final String URL_SET_CUSTOMFILE = dataManager.API_SERVER_URL+"/api/custom";
//	private static final String URL_GET_SHOOT_START_API = dataManager.API_SERVER_URL+"/api/selfi/getshootcheck";
//	private static final String URL_SET_SHOOT_START_API = dataManager.API_SERVER_URL+"/api/selfi/setshootstart";
//	private static final String URL_SET_MY_PICTURE_API = dataManager.API_SERVER_URL+"/api/selfi/setmypicture";
//	private static final String URL_GET_MY_ALBUM_API = dataManager.API_SERVER_URL+"/api/selfi/getmyalbum";
//	private static final String URL_DEL_MYALBUM_API = dataManager.API_SERVER_URL+"/api/selfi/delmypicture";
//	private static final String URL_SET_MY_PICTURE_OPEN_API = dataManager.API_SERVER_URL+"/api/selfi/setmypictureopen";
//	private static final String URL_GET_GALLERY_API = dataManager.API_SERVER_URL+"/api/selfi/getselfigallery";
//	private static final String URL_SET_LIKE_PICTURE_API = dataManager.API_SERVER_URL+"/api/selfi/setlikepicture";
//	private static final String URL_SET_SHOOT_FINISH = dataManager.API_SERVER_URL+"/api/selfi/setshootfinish";
//	private static final String URL_GET_SELFIE_ZONE_API = dataManager.API_SERVER_URL+"/api/selfi/getselfiezone";

	private static final String PARAM_REUTRN_TYPE = "returnType";
	private static final String VALUE_RETURN_TYPE = "JSON";


	private static final String PARAM_DEVICECODE = "deviceCode";
	private static final String PARAM_ZONE_ID = "zoneid";
	private static final String PARAM_UUID = "uuid";
	private static final String PARM_SAVETIME = "savetime";
	private static final String PARAM_IMAGE_ID = "imageid";
	private static final String PARAM_OPEN_FLAG = "openflag";

	private static final String PARAM_LATITUDE = "latitude";
	private static final String PARAM_lONGITUDE = "longitude";

	public static void setMyPictureApi(String deviceCode , Bitmap bitmap, final Listener<Integer> progress, final ResponseListener<ResultSendingImage> listener) {
		Log.w("test","setMyPictureApi ");
		String url = URL_SET_CUSTOMFILE;
		HashMap<String, String > params = new HashMap<String, String>();
		params.put(PARAM_DEVICECODE, deviceCode);
		//params.put(PARAM_REUTRN_TYPE, VALUE_RETURN_TYPE);

		android.util.Log.w("[ Input Values ",""+params.values()+" ]" );
		multipart(url, params, "customFile", "aaa.png", bitmap, ResultSendingImage.class, progress, listener);

	}

	/*****************************************************************************************************************************************************************************************/


	private static <U, V> void getArray(final String url, final Class<V> clazz, final ResponseListener<U> listener) {
//		 Log.d("test", url);
		Log.d("test","getArray (3)");
		 //TODO : 소스 정리.
		 try {
			VolleyUtil.getInstance().get(url, new Listener<String>() {
					@Override
					public void onResponse(String data) {
					Log.d("test","VolleyUtil get 메서드에서 successListener.onResponse 호출. (8)");
						if (listener != null) {
							try {
//								Log.d("test", data);
								Log.d("test","fromJson 파씽. (9)");
								U[] userCountList = (U[]) new Gson().fromJson(data, clazz);
								if (userCountList!= null) {
									Log.d("test","listener.response. 호출 (10)");
									listener.response(true, (U) userCountList);
								}else {
									listener.response(false, null);
								}
							} catch (Exception e) {
								e.printStackTrace();
								// Log.e(SFCore.TAG, "error " + e.getMessage());
								listener.response(false, null);
							}
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// Log.d(SFCore.TAG, arg0.getMessage());
						if (listener != null) {
							listener.response(false, null);
						}
					}
				});
		} catch (Exception e) {
			e.printStackTrace();
			if (listener != null) {
				listener.response(false, null);
			}
		}
	}
	
	private static <U, V> void get(final String url, final Class<V> clazz, final ResponseListener<U> listener) {
//		 Log.d("test", url);
		 VolleyUtil.getInstance().get(url, new Listener<String>() {
				@Override
				public void onResponse(String data) {
					if (listener != null) {
						try {
							Log.i("test AAA", data);
							U userCountList = new Gson().fromJson(data, new TypeToken<U>(){}.getType());
							if (userCountList!= null)
								listener.response(true, userCountList);
							else {
								listener.response(false, null);
							}
						} catch (Exception e) {
							e.printStackTrace();
							// Log.e(SFCore.TAG, "error " + e.getMessage());
							listener.response(false, null);
						}
					}
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					// Log.d(SFCore.TAG, arg0.getMessage());
					if (listener != null) {
						listener.response(false, null);
					}
				}
			});
	}


	private static <U, V extends AbstractRequest<?>> void get(final String url, final Listener<String> listener, final ErrorListener errorListener) {
		VolleyUtil.getInstance().get(url, listener, errorListener);
	}

	private static <U, V> void post(final String url, HashMap<String, String> param, final Class<V> clazz, final ResponseListener<U> listener) {
	//	android.util.Log.d("[ Called Api ",url+" ]");
		//
		// for (String key : param.keySet()) {
		// Log.d("test", key+ ":"+param.get(key));
		// }
		// }
		VolleyUtil.getInstance().post(url, param, new Listener<String>() {
			@Override
			public void onResponse(String data) {
				if (listener != null) {
					try {
						android.util.Log.d("[ Return Json  ", data+" ]");

						U returnData = (U) new Gson().fromJson(data, clazz);

						if (returnData != null) {
							listener.response(true, returnData);
						}else {
							listener.response(false, returnData);
						}
					} catch (Exception e) {
						e.printStackTrace();
						android.util.Log.d("test","Exception (1)"+e.getMessage());
						listener.response(false, null);
					}
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {

				android.util.Log.d("VolleyError = ",arg0.toString());
				if (listener != null) {

					android.util.Log.d("test","Exception (2)" );
					listener.response(false, null);
				}
			}
		});
	}

	private static <U, V> void multipart(String url, HashMap<String, String> param,
										 final String bitmapFieldName, final String fileName, final Bitmap bitmap, final Class<V> clazz, final Listener<Integer> progress, final ResponseListener<U> listener) {
		android.util.Log.d("test", url);
//    	for (String key : param.keySet()) {
//            Log.d("test", key+ ":"+param.get(key));
//        }
		VolleyUtil.getInstance().multipartBitmap(url, param, bitmapFieldName, fileName, bitmap, progress, new Listener<String>() {
			@Override
			public void onResponse(String data) {
//            	Log.d("test", data);
				if(listener != null) {
					try {
						android.util.Log.d("test", data);

						U returnData = (U) new Gson().fromJson(data, clazz);

						if (returnData != null) {
							listener.response(true, returnData);
						}else {
							listener.response(false, returnData);
						}
					} catch (Exception e) {
						e.printStackTrace();
						android.util.Log.d("test","Exception (1)"+e.getMessage());
//                        Log.e(SFCore.TAG, "error " + e.getMessage());
						listener.response(false, null);
					}
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				android.util.Log.d("에러 원인", arg0.getMessage()+" "+arg0.networkResponse);
				if(listener != null) {
					android.util.Log.d("test","Exception (2)" );
					listener.response(false, null);
				}
			}
		});
	}

}
