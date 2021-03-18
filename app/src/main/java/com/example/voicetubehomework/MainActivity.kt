package com.example.voicetubehomework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.example.voicetubehomework.project1.Project1Activity
import com.example.voicetubehomework.project2.Project2Activity
import com.example.voicetubehomework.project3.Project3Activity
import com.jakewharton.rxbinding4.view.clicks
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_main_startProject1.clicks().throttleFirst(1, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({
                startActivity(Intent(this, Project1Activity::class.java))
            },{t->t.printStackTrace()})

        button_main_startProject2.clicks().throttleFirst(1, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({
                startActivity(Intent(this, Project2Activity::class.java))
            },{t->t.printStackTrace()})

        button_main_startProject3.clicks().throttleFirst(1, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({
                startActivity(Intent(this, Project3Activity::class.java))
            },{t->t.printStackTrace()})
    }
}