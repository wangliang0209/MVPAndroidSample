package com.wl.demo.mvpsample.net;

import com.wl.demo.mvpsample.common.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangliang on 16-10-19.
 */

public class HttpModule {
    public static HttpModule instance;
    private HttpModule() {
        mCommonAPIService = provideApiService(provideRetrofit(provideHttpLogging()));
    }

    public static HttpModule getInstance() {
        if(instance != null) {
            return instance;
        }
        synchronized (HttpModule.class) {
            if(instance == null) {
                instance = new HttpModule();
            }
            return instance;
        }
    }

    private CommonAPIService mCommonAPIService;

    public CommonAPIService getCommonAPIService() {
        return mCommonAPIService;
    }


    private OkHttpClient provideHttpLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Constant.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
//                .addInterceptor(new HttpIntercepter())
                .addInterceptor(logging)
                .build();
    }

    private Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private CommonAPIService provideApiService(Retrofit retrofit) {
        return retrofit.create(CommonAPIService.class);
    }

//    private static class HttpIntercepter implements Interceptor {
//        public static final int HTTP_CODE_AUTH_FAILED = 401;
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Response response = chain.proceed(chain.request());  //如果401了，会先执行TokenAuthenticator
//            if (response.code() == HTTP_CODE_AUTH_FAILED) {
//                Log.d("WLTest", "response msg:" + response.body().string());
//                com.wl.demo.mvpsample.net.resp.model.base.Response resp = new Gson().fromJson(response.body().string(), com.wl.demo.mvpsample.net.resp.model.base.Response.class);
//
//                ApiException apiException = new ApiException(resp);
//                throw  apiException;
//            }
//            return response;
//        }
//    }
}
