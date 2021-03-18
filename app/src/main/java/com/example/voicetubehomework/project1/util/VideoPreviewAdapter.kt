package com.example.voicetubehomework.project1.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.voicetubehomework.R
import com.example.voicetubehomework.project1.util.service.appquiz.Video
import com.example.voicetubehomework.util.LoadMoreAdapter
import com.google.android.material.card.MaterialCardView

class VideoPreviewAdapter(videoList: MutableList<Video>): LoadMoreAdapter() {
    private lateinit var context : Context
    private var videoList = videoList

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val materialCardView_project1_video: MaterialCardView = itemView.findViewById(R.id.materialCardView_project1_video)
        var imageView_project1_video_image: ImageView = itemView.findViewById(R.id.imageView_project1_video_image)
        var textView_project1_video_text: TextView = itemView.findViewById(R.id.textView_project1_video_text)
    }

    override fun _onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder: ViewHolder = h as ViewHolder
        Glide.with(context)
            .load(videoList[position].imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(holder.imageView_project1_video_image)
        holder.textView_project1_video_text.text = videoList[position].title
    }

    override fun _getItemCount(): Int {
        return videoList.size
    }

    override fun _onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_project1_video, parent, false))
    }

    public fun insert(insertStartPosition: Int){
        notifyItemRangeInserted(insertStartPosition, videoList.size - insertStartPosition)
    }
}