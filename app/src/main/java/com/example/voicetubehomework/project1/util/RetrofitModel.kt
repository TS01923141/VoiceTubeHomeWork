package com.example.voicetubehomework.project1.util

import com.example.voicetubehomework.project1.util.service.appquiz.AppQuizResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*
import java.util.concurrent.TimeUnit

public class RetrofitModel {
    companion object {
        private lateinit var mInstance: RetrofitModel
        private lateinit var dataService: DataService

        @JvmStatic
        fun getmInstance(): RetrofitModel {
            if (!this::mInstance.isInitialized) {
                mInstance = RetrofitModel()
            }
            return mInstance
        }
    }

    private val BASE_URL = "https://us-central1-lithe-window-713.cloudfunctions.net/"

    public interface DataService {
        @FormUrlEncoded
        @POST("appQuiz")
        fun appQuizRX(
            @Field("username") username: String,
            @Field("password") password: String
        ): Single<Response<AppQuizResponse>>
    }

    internal var okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .retryOnConnectionFailure(true)
        .build()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        dataService = retrofit.create<DataService>(DataService::class.java)
    }

    fun getService(): DataService {
        return dataService
    }
}