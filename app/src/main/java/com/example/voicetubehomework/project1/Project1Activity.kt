package com.example.voicetubehomework.project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import com.example.voicetubehomework.MainActivity
import com.example.voicetubehomework.R
import com.example.voicetubehomework.databinding.ActivityProject1Binding
import com.example.voicetubehomework.project1.util.VideoPreviewAdapter
import com.example.voicetubehomework.util.CommonValues
import com.example.voicetubehomework.util.EndlessRecyclerOnScrollListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_project1.*

private const val TAG = "Project1Activity"

class Project1Activity : AppCompatActivity() {
    private lateinit var viewModel: Project1ViewModel
    private lateinit var binding: ActivityProject1Binding

    private lateinit var adapter: VideoPreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_project1)
        viewModel = ViewModelProvider(this).get(Project1ViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project1)
        //init back button
        imageView_project1_back.setOnClickListener { onBackPressed() }
        //init adapter and recyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView_project1.layoutManager = layoutManager

        viewModel.getAdapterVideos(CommonValues.REFRESH)
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY))
            .subscribe({ videoList ->
                adapter = VideoPreviewAdapter(videoList)
                recyclerView_project1.adapter = adapter
            }, { t -> t.printStackTrace() })
        //加載
        recyclerView_project1.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                adapter.loadState = adapter.LOADING
                val insertStartPosition = adapter._getItemCount()
                viewModel.getAdapterVideos(CommonValues.GET_NEXT)
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDispose(AndroidLifecycleScopeProvider.from(
                        this@Project1Activity, Lifecycle.Event.ON_DESTROY))
                    .subscribe({
                        adapter.insert(insertStartPosition)
                    }, { t -> t.printStackTrace() })
            }
        })
    }
}