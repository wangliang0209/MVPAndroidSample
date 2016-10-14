package com.wl.demo.mvpsample.net;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

/**
 *
 * Created by wangliang on 16-9-28.
 */

public class CommonAPIManager {
    private static CommonAPIManager instance;

    private Retrofit mRetrofit;
    private CommonAPIService mToogooAPIService;

    private Map<String, List<Subscriber>> reqMap = new HashMap<>();

    private CommonAPIManager(){
        initToogooAPI();
    }

    public static CommonAPIManager getInstance() {
        if(instance == null) {
            synchronized (CommonAPIManager.class) {
                if(instance == null) {
                    instance = new CommonAPIManager();
                }
            }
        }

        return instance;
    }

    private void initToogooAPI() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        mToogooAPIService = mRetrofit.create(CommonAPIService.class);
    }

    private String getBaseUrl() {
        return "http://192.168.20.200:8080/web/";
    }

    public CommonAPIService getToogooAPIService() {
        return mToogooAPIService;
    }

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LogInterceptor())
            .retryOnConnectionFailure(true)
			.connectTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(5, TimeUnit.SECONDS)
			.readTimeout(5, TimeUnit.SECONDS)
            .build();

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.e("WLTest", "" + request.toString());//输出请求前整个url
//            Log.d("WLTest", "   cur thread:" + Thread.currentThread().getId() + ", main thread:" + Looper.getMainLooper().getThread().getId());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
//			Log.v(TAG,response.request().url()+response.headers());//输出一个请求的网络信息
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.e("WLTest", "response body:" + content);//输出返回信息
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    public void putReq(String tag, Subscriber subscriber) {
        List<Subscriber> reqs = reqMap.get(tag);
        if(reqs != null) {
            reqs.add(subscriber);
        } else {
            reqs = new LinkedList<>();
            reqs.add(subscriber);
            reqMap.put(tag, reqs);
        }
        Log.d("WLTest", "putReq after reqs:" + reqs.size());
    }

    public void removeReq(String tag, Subscriber subscriber) {
        List<Subscriber> reqs = reqMap.get(tag);
        if(reqs != null) {
            reqs.remove(subscriber);
            Log.d("WLTest", "removeReq after reqs:" + reqs.size());
            if(reqs.isEmpty()) {
                reqMap.remove(tag);
            }
        }
    }

    public void cancelPendingRequests(Context context) {
        cancelPendingRequests(context.getClass().getSimpleName());
    }

    public void cancelPendingRequests(String tag) {
        if(tag != null) {
            List<Subscriber> reqs = reqMap.get(tag);
            if (reqs != null) {
                for (Subscriber s : reqs) {
                    if (!s.isUnsubscribed()) {
                        s.unsubscribe();
                    }
                }
            }
        }
    }

    public void clear() {
        mToogooAPIService = null;
        mRetrofit = null;
    }
}
