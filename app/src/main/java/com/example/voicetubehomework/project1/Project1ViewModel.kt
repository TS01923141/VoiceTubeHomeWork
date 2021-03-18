package com.example.voicetubehomework.project1

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.voicetubehomework.project1.util.RetrofitModel
import com.example.voicetubehomework.project1.util.service.appquiz.AppQuizResponse
import com.example.voicetubehomework.project1.util.service.appquiz.Video
import com.example.voicetubehomework.util.CommonValues
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

const val userName = "VoiceTube"
const val password = "interview"
private const val TAG = "Project1ViewModel"
class Project1ViewModel(app: Application) : AndroidViewModel(app) {
    //retrofit取得的原始資料
    private var videos: MutableList<Video> = arrayListOf()

    //給Adapter用的資料
    private var adapterVideos: MutableList<Video> = arrayListOf()
    //sharedPreferences
    private val sharedPreferences = app.getSharedPreferences(CommonValues.TEMP, Context.MODE_PRIVATE)

    //取得暫存的function
    //暫存的function

    //取得給adapter的videos
    public fun getAdapterVideos(type: Int): Single<MutableList<Video>> {
        when (type) {
            CommonValues.GET_CURRENT -> return Single.just(adapterVideos)
            CommonValues.REFRESH -> {
                adapterVideos.clear()
            }
        }
        return getVideos()
            .map { videos ->
                adapterVideos.addAll(videos)
                adapterVideos
            }
    }

    //取得原始videos資料
    private fun getVideos(): Single<MutableList<Video>> {
        if (videos.isNotEmpty()) return Single.just(videos)
        return appQuizRX()
    }

    //appQuiz
    //retrofit線上取得videos
    private fun appQuizRX(): Single<MutableList<Video>> {
        //第二次進app就直接透過sharedPreferences
        //判斷sharedPreferences是否有資料，沒有才執行retrofit
        var temp = sharedPreferences.getString(CommonValues.APP_QUIZ, "")?: ""
        if (temp != "") {
            Log.d(TAG, "appQuizRX: from sharedPreferences")
            val gson = Gson()
            val response = gson.fromJson(temp, AppQuizResponse::class.java)
            videos = response.videos
            return Single.just(videos)
        }

        val retrofitService = RetrofitModel.getmInstance().getService()
        return retrofitService.appQuizRX(userName, password)
            .subscribeOn(Schedulers.io())
            .map { response ->
                if (response.isSuccessful && response.code() == 200 && response.body() != null && response.body()?.status == "success") {
                    Log.d(TAG, "appQuizRX: from retrofit")
                    videos = response.body()!!.videos
                    //寫入暫存
                    val gson = Gson()
                    val videoJson = gson.toJson(response.body())
                    sharedPreferences.edit()
                        .putString(CommonValues.APP_QUIZ, videoJson)
                        .commit()
                }
                videos
            }
    }
}