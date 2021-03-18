package com.example.voicetubehomework.project1.util.service.appquiz

import com.google.gson.annotations.SerializedName

/*
        {
            "title": "IGN News - Kiefer Sutherland Playing Snake in Metal Gear Solid V",
            "img": "http://img.youtube.com/vi/S-FFgpUfVX8/maxresdefault.jpg"
        }
 */
data class Video(
    @SerializedName("title") val title: String,
    @SerializedName("img") val imageUrl: String
)