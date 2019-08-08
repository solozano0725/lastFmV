package com.valid.lastfmclient.network.retrofit;

import android.content.Context;
import com.valid.lastfmclient.BuildConfig;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RestApiAdapter {

    private int cacheSize;
    private Context context;

    public RestApiAdapter(Context c) {
        cacheSize = (5 * 1024 * 1024);
        this.context = c;
    }

    public RestClient restClient(){

        OkHttpClient.Builder okHttpbuilder = new OkHttpClient().newBuilder().cache(new Cache(context.getCacheDir(), cacheSize));
        okHttpbuilder.addInterceptor(new AppInterceptor());
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpbuilder.addInterceptor(loggingInterceptor);
        }
        okHttpbuilder.retryOnConnectionFailure(false);
        okHttpbuilder.readTimeout(48, TimeUnit.SECONDS);
        okHttpbuilder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = okHttpbuilder.build();

         Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASEURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(RestClient.class);
        }
    }
