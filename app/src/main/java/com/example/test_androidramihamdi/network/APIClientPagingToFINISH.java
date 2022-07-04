package com.example.test_androidramihamdi.network;

import static com.example.test_androidramihamdi.util.Constants.API_KEY;
import static com.example.test_androidramihamdi.util.Constants.BASE_URL;

import com.example.test_androidramihamdi.model.RecyclerList;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClientPagingToFINISH {
    // Define APIInterface
    static APIInterface apiInterface;
    // create retrofit instance
    public static APIInterface getAPIInterface() {
        if (apiInterface == null) {
            // Create OkHttp Client
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            // Add interceptor to add API key as query string parameter to each request
            client.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("image_type", "all")// "all", "photo", "illustration", "vector"
                        .addQueryParameter("pretty", "false")
                        .addQueryParameter("per_page", "90") //3 - 200
                        .addQueryParameter("order", "popular") //Accepted values: "popular", "latest"
                        .addQueryParameter("safesearch", "false") //Accepted values: "true", "false"
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });
            // Create retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    // Add Gson converter
                    .addConverterFactory(GsonConverterFactory.create())
                    // Add RxJava spport for Retrofit
                  //  .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            // Init APIInterface
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }
    //API service interface
    public interface APIInterface {


        @GET("/api/")
        Single<RecyclerList> getImagesFromPixabay(@Query("key") String key,
                                                  @Query("q") String query,

                                           @Query("page") int page /*,,
                                           @Query("per_page") int perPage*/);
    }
}
