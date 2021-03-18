package com.example.voicetubehomework.project3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.example.voicetubehomework.R
import com.example.voicetubehomework.databinding.ActivityProject3Binding
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_project3.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

private const val TAG = "Project3Activity"
class Project3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityProject3Binding
    private lateinit var viewModel: Project3ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_project3)
        viewModel = ViewModelProvider(this).get(Project3ViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project3)

        materialButton_project3_start.clicks().throttleFirst(3, TimeUnit.SECONDS)
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({
                try {
                    //設定數字
                    viewModel.countDown = editText_project3_inputCountDown.text.toString().toInt()
                    textView_project3_countDownNumber.text = viewModel.countDown.toString()
                    //開始倒數
                    viewModel.startCountDown()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { materialButton_project3_start.isClickable = true }
                        .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
                        .subscribe({
                            viewModel.countDown -=1
//                            Log.d(TAG, "onCreate: countDown: " + viewModel.countDown)
                            textView_project3_countDownNumber.text = viewModel.countDown.toString()
                        },{t -> t.printStackTrace() })
                    materialButton_project3_start.isClickable = false
                } catch (e: Exception){
                    Toast.makeText(this, getString(R.string.notification_input_number), Toast.LENGTH_LONG).show()
                }
            },{t->t.printStackTrace()})
    }

    override fun onResume() {
        super.onResume()
        viewModel.isResume = true
    }

    override fun onPause() {
        viewModel.isResume = false
        super.onPause()
    }
}