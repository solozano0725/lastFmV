package com.valid.lastfmclient.network.retrofit;

import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.valid.lastfmclient.BuildConfig;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppInterceptor implements Interceptor {

    public AppInterceptor() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(5)
                .methodOffset(10)
                .tag("lastFmV")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                if (BuildConfig.DEBUG) {
                    return true;
                } else if (priority == Logger.ERROR || priority == Logger.INFO) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=utf-8");
        if(request.body() != null){
            Log.v("BODY",request.body().toString());
        }
        request = builder.build();
        Response response = chain.proceed(request);
        return response;
    }
}