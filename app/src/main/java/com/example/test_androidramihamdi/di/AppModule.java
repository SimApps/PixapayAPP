package com.example.test_androidramihamdi.di;


import static com.example.test_androidramihamdi.util.Constants.BASE_URL;
import static com.example.test_androidramihamdi.util.Constants.CONNECTION_TIMEOUT;
import static com.example.test_androidramihamdi.util.Constants.READ_TIMEOUT;
import static com.example.test_androidramihamdi.util.Constants.WRITE_TIMEOUT;

import androidx.annotation.NonNull;

import com.example.test_androidramihamdi.network.RetroServiceInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule  {





    @Singleton
    @Provides
    public RetroServiceInterface getRetroServiceInterface(@NonNull Retrofit retrofit){
        return retrofit.create(RetroServiceInterface.class);
    }

    @Singleton
    @Provides
    public Retrofit RetrofitgetRetroInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static final OkHttpClient client = new OkHttpClient.Builder()
            // establish connection to server
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            // time between each byte read from the server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            // time between each byte sent to server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl
                        .newBuilder()
                        .addQueryParameter("page", "1")

                        .addQueryParameter("image_type", "all")// "all", "photo", "illustration", "vector"
                        .addQueryParameter("pretty", "false")
                        .addQueryParameter("per_page", "90") //3 - 200
                        .addQueryParameter("order", "popular") //Accepted values: "popular", "latest"
                        .addQueryParameter("safesearch", "false") //Accepted values: "true", "false"
                        .build();
                Request.Builder requestBuilder = original
                        .newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            })
            .addInterceptor(loggingInterceptor())
            .build();


    private static HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
