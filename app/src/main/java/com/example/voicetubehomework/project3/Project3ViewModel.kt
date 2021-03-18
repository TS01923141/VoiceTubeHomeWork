package com.example.voicetubehomework.project3

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class Project3ViewModel(app: Application): AndroidViewModel(app) {
    //倒數數字
    var countDown = 0
    var isResume = false

    //倒數fun
    public fun startCountDown() : Flowable<Long> {
        return Flowable.interval(1, TimeUnit.SECONDS)
            .onBackpressureDrop()
            .takeWhile { countDown != 0 }
            .filter { isResume }
            .subscribeOn(Schedulers.computation())

    }
}